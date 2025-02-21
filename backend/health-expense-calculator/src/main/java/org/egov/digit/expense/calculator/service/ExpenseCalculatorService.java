package org.egov.digit.expense.calculator.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.models.project.Project;
import org.egov.common.models.project.ProjectResponse;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.mapper.BillToMetaMapper;
import org.egov.digit.expense.calculator.repository.ExpenseCalculatorRepository;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.validator.ExpenseCalculatorServiceValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.attendance.AttendanceRegister;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.expense.calculator.IndividualEntry;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.egov.works.services.common.models.musterroll.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Slf4j
@Service
public class ExpenseCalculatorService {
    private final ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator;

    private final WageSeekerBillGeneratorService wageSeekerBillGeneratorService;
    private final SupervisionBillGeneratorService supervisionBillGeneratorService;
    private final ExpenseCalculatorProducer expenseCalculatorProducer;
    private final ExpenseCalculatorConfiguration config;
    private final PurchaseBillGeneratorService purchaseBillGeneratorService;
    private final MdmsUtils mdmsUtils;
    private final BillUtils billUtils;
    private final ProjectUtil projectUtils;
    private final ObjectMapper mapper;
    private final CommonUtil commonUtil;
    private final ExpenseCalculatorUtil expenseCalculatorUtil;
    private final BillToMetaMapper billToMetaMapper;
    private final ExpenseCalculatorRepository expenseCalculatorRepository;

    private final ObjectMapper objectMapper;
    private final ContractUtils contractUtils;
    private final EstimateServiceUtil estimateServiceUtil;
    private final ProjectUtil projectUtil;
    private final AttendanceUtil attendanceUtil;
    private final BoundaryUtil boundaryUtil;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public ExpenseCalculatorService(ExpenseCalculatorProducer expenseCalculatorProducer, ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator, WageSeekerBillGeneratorService wageSeekerBillGeneratorService, SupervisionBillGeneratorService supervisionBillGeneratorService, BillToMetaMapper billToMetaMapper, ObjectMapper objectMapper, ExpenseCalculatorConfiguration config, PurchaseBillGeneratorService purchaseBillGeneratorService, MdmsUtils mdmsUtils, BillUtils billUtils, ProjectUtil projectUtils, ExpenseCalculatorUtil expenseCalculatorUtil, ExpenseCalculatorRepository expenseCalculatorRepository, ObjectMapper mapper, CommonUtil commonUtil, ContractUtils contractUtils, EstimateServiceUtil estimateServiceUtil, ProjectUtil projectUtil, AttendanceUtil attendanceUtil, BoundaryUtil boundaryUtil, ResponseInfoFactory responseInfoFactory) {
        this.expenseCalculatorProducer = expenseCalculatorProducer;
        this.expenseCalculatorServiceValidator = expenseCalculatorServiceValidator;
        this.wageSeekerBillGeneratorService = wageSeekerBillGeneratorService;
        this.supervisionBillGeneratorService = supervisionBillGeneratorService;
        this.billToMetaMapper = billToMetaMapper;
        this.objectMapper = objectMapper;
        this.config = config;
        this.purchaseBillGeneratorService = purchaseBillGeneratorService;
        this.mdmsUtils = mdmsUtils;
        this.billUtils = billUtils;
        this.projectUtils = projectUtils;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
        this.expenseCalculatorRepository = expenseCalculatorRepository;
        this.mapper = mapper;
        this.commonUtil = commonUtil;
        this.contractUtils = contractUtils;
        this.estimateServiceUtil = estimateServiceUtil;
        this.projectUtil = projectUtil;
        this.attendanceUtil = attendanceUtil;
        this.boundaryUtil = boundaryUtil;
        this.responseInfoFactory = responseInfoFactory;
    }

    public Calculation calculateEstimates(CalculationRequest calculationRequest) {
        expenseCalculatorServiceValidator.validateCalculatorEstimateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();

        if (criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            // Fetch all the approved muster rolls for provided muster Ids
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo, criteria, false);
            // Fetch wage seeker skills from MDMS
            List<SorDetail> sorDetails = fetchMDMSDataForLabourCharges(requestInfo, criteria.getTenantId(), musterRolls);
            return wageSeekerBillGeneratorService.calculateEstimates(requestInfo, criteria.getTenantId(), musterRolls, sorDetails);
        } else {
            List<Bill> bills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getReferenceId());
            //TODO: Add check for empty bill list here and send back a response
            return supervisionBillGeneratorService.estimateBill(requestInfo, criteria, bills);
        }
    }

    public List<Bill> createPurchaseBill(PurchaseBillRequest purchaseBillRequest){
        // Initialize meta map
        Map<String, String> metaInfo = new HashMap<>();
        // Create purchase bill
        Bill purchaseBill = createPurchaseBill(purchaseBillRequest,metaInfo);
        // Post the newly created bill to expense service
        BillResponse billResponse = postCreateBill(purchaseBillRequest.getRequestInfo(), purchaseBill, purchaseBillRequest.getWorkflow());

        List<Bill> submittedBills = new ArrayList<>();
        if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
        {
            List<Bill> respBills = billResponse.getBills();
            if(respBills != null && !respBills.isEmpty()) {
                persistMeta(respBills,metaInfo);
                submittedBills.addAll(respBills);
            }
        }
        return submittedBills;
    }

    public List<Bill> updatePurchaseBill(PurchaseBillRequest purchaseBillRequest) {
        // Initialize meta map
        Map<String, String> metaInfo = new HashMap<>();
        // Create purchase bill
        Bill purchaseBill = updatePurchaseBill(purchaseBillRequest,metaInfo);
        // Post the newly created bill to expense service
        BillResponse billResponse = postUpdateBill(purchaseBillRequest.getRequestInfo(), purchaseBill, purchaseBillRequest.getWorkflow());

        List<Bill> submittedBills = new ArrayList<>();
        if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
        {
            List<Bill> respBills = billResponse.getBills();
            if(respBills != null && !respBills.isEmpty()) {
                submittedBills.addAll(respBills);
            }
        }
        return submittedBills;
    }

    private Bill createPurchaseBill(PurchaseBillRequest purchaseBillRequest , Map<String, String> metaInfo){
        log.info("Create purchase bill");
        expenseCalculatorServiceValidator.validateCreatePurchaseRequest(purchaseBillRequest);

        RequestInfo requestInfo = purchaseBillRequest.getRequestInfo();
        PurchaseBill providedPurchaseBill = purchaseBillRequest.getBill();
        String tenantId = providedPurchaseBill.getTenantId();

        String businessServiceName = fetchBusinessServiceName(requestInfo, tenantId, config.getPurchaseBusinessService());
        // Fetch HeadCodes from MDMS
        List<HeadCode> headCodes = expenseCalculatorUtil.fetchHeadCodesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Fetch Applicable Charges from MDMS
        List<ApplicableCharge> applicableCharges = expenseCalculatorUtil.fetchApplicableChargesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Create the bill
        return purchaseBillGeneratorService.createPurchaseBill(requestInfo,providedPurchaseBill,headCodes,applicableCharges,metaInfo);
    }

    private String fetchBusinessServiceName(RequestInfo requestInfo, String tenantId, String businessServiceCode) {
        // Fetch business service from MDMS
        List<BusinessService> businessServices = fetchMDMSDataForBusinessService(requestInfo, tenantId);
        Map<String, String> businessServiceToCodeMapping = businessServices.stream()
                .collect(Collectors.toMap(BusinessService::getCode,BusinessService::getBusinessService));
        String businessServiceName = businessServiceToCodeMapping.get(businessServiceCode);
        log.info("Business Service code "+ businessServiceCode+" name is ["+businessServiceName+"]");
        return businessServiceName;
    }


    private Bill updatePurchaseBill(PurchaseBillRequest purchaseBillRequest , Map<String, String> metaInfo){
        log.info("Update purchase bill");
        expenseCalculatorServiceValidator.validateUpdatePurchaseRequest(purchaseBillRequest);

        RequestInfo requestInfo = purchaseBillRequest.getRequestInfo();
        PurchaseBill providedPurchaseBill = purchaseBillRequest.getBill();
        String tenantId = providedPurchaseBill.getTenantId();

        String businessServiceName = fetchBusinessServiceName(requestInfo, tenantId, config.getPurchaseBusinessService());
        // Fetch HeadCodes from MDMS
        List<HeadCode> headCodes = expenseCalculatorUtil.fetchHeadCodesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Fetch Applicable Charges from MDMS
        List<ApplicableCharge> applicableCharges = expenseCalculatorUtil.fetchApplicableChargesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Create the bill
        return purchaseBillGeneratorService.updatePurchaseBill(requestInfo,providedPurchaseBill,headCodes,applicableCharges,metaInfo);
    }
    public List<Bill> createWageOrSupervisionBillTest(CalculationRequest calculationRequest){
        Long startTime = System.currentTimeMillis();
        log.info("Start time :: " + startTime);
        Bill bill = Bill.builder().build();
        String projectId = UUID.randomUUID().toString();
        for (int i = 0; i < Integer.valueOf(String.valueOf(calculationRequest.getCriteria().getFromPeriod())); i++) {

            LineItem payableLineItem = wageSeekerBillGeneratorService
                    .buildLineItem("mz", BigDecimal.TEN, "FOOD", LineItem.TypeEnum.PAYABLE);
            LineItem payableLineItem2 = wageSeekerBillGeneratorService
                    .buildLineItem("mz", BigDecimal.TEN, "TRAVEL", LineItem.TypeEnum.PAYABLE);
            LineItem payableLineItem3 = wageSeekerBillGeneratorService
                    .buildLineItem("mz", BigDecimal.TEN, "PER_DAY", LineItem.TypeEnum.PAYABLE);
            List<LineItem> lineItems = new ArrayList<>();
            lineItems.add(payableLineItem);
            lineItems.add(payableLineItem2);
            lineItems.add(payableLineItem3);
            BillDetail billDetail = BillDetail.builder()
                    .payableLineItems(lineItems)
                    .lineItems(new ArrayList<>())
                    .totalAmount(BigDecimal.valueOf(10000))
                    .referenceId(projectId)
                    .tenantId("mz")
                    .payee(Party.builder()
                            .tenantId("mz")
                            .identifier(UUID.randomUUID().toString())
                            .type("IND")
                            .build())
                    .build();
            bill.addBillDetailsItem(billDetail);
        }
        bill.setFromPeriod(1734373800000L);
        bill.setBillDate(System.currentTimeMillis());
        bill.setToPeriod(1736101799000L);
        bill.setTenantId("mz");
        bill.setReferenceId(projectId);
        bill.setBusinessService("EXPENSE.WAGES");
        bill.setStatus("ACTIVE");
        bill.setLocalityCode("MICROPLAN_MO_13_05_TCHIEN_TEST");
        bill.setPayer(Party.builder().identifier(UUID.randomUUID().toString()).tenantId("mz").type("ORG").build());
        Set<String> distinctRegisters = bill.getBillDetails().stream().map(BillDetail::getReferenceId).collect(Collectors.toSet());
        // If additional details object is null add number of distinct registers in new object, else take additional details object and add number of distinct registers
        ObjectNode additionalDetails = mapper.createObjectNode();
        if(bill.getAdditionalDetails() != null){
            additionalDetails = objectMapper.convertValue(bill.getAdditionalDetails(), ObjectNode.class);
        }
        additionalDetails.put("noOfRegisters", distinctRegisters.size());
        bill.setAdditionalDetails(additionalDetails);

        Workflow workflow = Workflow.builder()
                .action(WF_SUBMIT_ACTION_CONSTANT)
                .build();
        BillResponse billResponse = postCreateBill(calculationRequest.getRequestInfo(), bill,workflow);

        log.info("Time taken to create bill is " + (System.currentTimeMillis() - startTime)/1000 + " seconds");
        return billResponse.getBills();
    }

    public BillResponse createWageBillHealth(CalculationRequest calculationRequest){
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(calculationRequest.getRequestInfo(), true);
        if (config.isBillGenerationAsyncEnabled()) {
            // Fetch Project Details
            ProjectResponse projectResponse = projectUtil.getProjectDetails(calculationRequest.getRequestInfo(),
                    calculationRequest.getCriteria().getTenantId(), calculationRequest.getCriteria().getReferenceId(),
                    calculationRequest.getCriteria().getLocalityCode());

            if (projectResponse == null || projectResponse.getProject() == null) {
                log.error("Project Response null");
                throw new CustomException("PROJECT_RESPONSE_NULL", "Project response null");
            }
            if (projectResponse.getProject().isEmpty()) {
                log.error("Project not found");
                throw new CustomException("PROJECT_NOT_FOUND", "Project not found");
            }

            String referenceId = null;
            if (projectResponse.getProject().get(0).getProjectHierarchy() == null) {
                referenceId = projectResponse.getProject().get(0).getId();
            } else {
                referenceId = projectResponse.getProject().get(0).getProjectHierarchy();
            }

            // Fetch Bill Status from DB
            List<BillStatus> billStatuses = expenseCalculatorRepository.getBillStatusByReferenceId(referenceId);

            if (!billStatuses.isEmpty()) {
                if (billStatuses.stream().map(BillStatus::getStatus).collect(Collectors.toList()).contains(SUCCESSFUL_STATUS)) {
                    // If already successful return success response
                    return BillResponse.builder().responseInfo(responseInfo).statusCode(SUCCESSFUL_STATUS).build();
                } else if (billStatuses.stream().map(BillStatus::getStatus).collect(Collectors.toList()).contains(INITIATED_STATUS)) {
                    BillResponse billResponse = billUtils.searchBills(calculationRequest, referenceId);
                    BillResponse billResponse1 = BillResponse.builder()
                            .responseInfo(responseInfo)
                            .bills(new ArrayList<>())
                            .build();
                    if (billResponse == null || billResponse.getBills().isEmpty()) {
                        //If not found in expense db return inprogress status
                        billResponse1.setStatusCode(INPROGRESS_STATUS);
                        return billResponse1;
                    } else {
                        //If found in expense db return success status
                        BillStatus billStatus = billStatuses.stream().filter(billStatus1 -> billStatus1.getStatus().equals(INITIATED_STATUS)).findFirst().get();
                        expenseCalculatorRepository.updateBillStatus(billStatus.getId(), SUCCESSFUL_STATUS, null);
                        billResponse1.setStatusCode(SUCCESSFUL_STATUS);
                        return billResponse1;
                        }
                }
            }

            // Create entry for bill status
            expenseCalculatorRepository.createBillStatus(UUID.randomUUID().toString(),
                    calculationRequest.getCriteria().getTenantId(), referenceId,
                    INITIATED_STATUS, null);

            // Push to async topic for bill generation
            expenseCalculatorProducer.push(config.getBillGenerationAsyncTopic(), calculationRequest);
            return BillResponse.builder().responseInfo(responseInfo).statusCode(INITIATED_STATUS).build();
        } else {
            return BillResponse.builder().responseInfo(responseInfo)
                    .bills(createWageOrSupervisionBills(calculationRequest)).statusCode(INITIATED_STATUS).build();

        }
    }

    public List<Bill> createWageOrSupervisionBills(CalculationRequest calculationRequest){
        Long startTime = System.currentTimeMillis();
        log.info("Processing bill started at time :: " + startTime);
        // Fetch Project Details based on referenceId and localityCode
        ProjectResponse projectResponse = projectUtil.getProjectDetails(calculationRequest.getRequestInfo(),
                calculationRequest.getCriteria().getTenantId(), calculationRequest.getCriteria().getReferenceId(),
                calculationRequest.getCriteria().getLocalityCode());

        if (projectResponse == null || projectResponse.getProject() == null) {
            log.error("Project Response null");
            throw new CustomException("PROJECT_RESPONSE_NULL", "Project response null");
        }
        if (projectResponse.getProject().isEmpty()) {
            log.error("Project not found");
            throw new CustomException("PROJECT_NOT_FOUND", "Project not found");
        }

        try {
            // Fetch all boundaries for that particular locality and projectId with children
            List<TenantBoundary> boundaries = boundaryUtil.fetchBoundary(RequestInfoWrapper.builder()
                            .requestInfo(calculationRequest.getRequestInfo()).build(), calculationRequest.getCriteria().getLocalityCode(),
                    calculationRequest.getCriteria().getTenantId(), false);

            if (boundaries.isEmpty()) {
                log.error("Boundary not found");
                throw new CustomException("BOUNDARY_NOT_FOUND", "Boundary not found");
            }

            // Check if boundary is of district level
            boolean isBoundaryDistrict = isBoundaryDistrictLevel(boundaries.get(0));

            expenseCalculatorServiceValidator.validateCalculatorCalculateRequest(calculationRequest, projectResponse.getProject().get(0));
            RequestInfo requestInfo = calculationRequest.getRequestInfo();
            Criteria criteria = calculationRequest.getCriteria();
            List<Bill> bills = null;
            Map<String, String> metaInfo = new HashMap<>();

            if ((criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) || config.isHealthIntegrationEnabled()) {
                bills = createWageBill(requestInfo, criteria, metaInfo, projectResponse.getProject().get(0), isBoundaryDistrict);
            } else {
                bills = createSupervisionBill(requestInfo, criteria, metaInfo);
            }

            BillResponse billResponse = null;
            List<Bill> submittedBills = new ArrayList<>();
            Workflow workflow = Workflow.builder()
                    .action(WF_SUBMIT_ACTION_CONSTANT)
                    .build();
            for (Bill bill : bills) {
                billResponse = postCreateBill(requestInfo, bill, workflow);
                if (SUCCESSFUL_CONSTANT.equalsIgnoreCase(billResponse.getResponseInfo().getStatus())) {
                    log.info("Bill successfully posted to expense service. Reference ID " + bill.getReferenceId());
                    List<Bill> respBills = billResponse.getBills();
                    submittedBills.addAll(respBills);
//                if(respBills != null && !respBills.isEmpty()) {
//                    log.info("Persisting meta for bill reference ID: " + bill.getReferenceId());
//                    persistMeta(respBills,metaInfo);
//                    submittedBills.addAll(respBills);
//                }
                } else {
                    log.info("Bill posting failed for bill " + bill.getBusinessService() + " reference ID " + bill.getReferenceId());
                }
                if (config.isReportGenerationAuto()) {
                    ReportGenerationTrigger reportGenerationTrigger = ReportGenerationTrigger.builder()
                            .requestInfo(requestInfo)
                            .billId(billResponse.getBills().get(0).getId())
                            .tenantId(billResponse.getBills().get(0).getTenantId())
                            .createdTime(System.currentTimeMillis())
                            .numberOfBillDetails(billResponse.getBills().get(0).getBillDetails().size())
                            .build();
                    expenseCalculatorProducer.push(config.getReportGenerationTriggerTopic(), reportGenerationTrigger);
                }
            }
            log.info("Processing bill completed; time taken :: " + (System.currentTimeMillis() - startTime)/1000 + " seconds");
            return submittedBills;
        } catch (CustomException customException) {
            String referenceId = null;
            if (projectResponse.getProject().get(0).getProjectHierarchy() == null) {
                referenceId = projectResponse.getProject().get(0).getId();
            } else {
                referenceId = projectResponse.getProject().get(0).getProjectHierarchy();
            }
            List<BillStatus> billStatuses = expenseCalculatorRepository.getBillStatusByReferenceId(referenceId);
            if (!billStatuses.isEmpty()) {
                BillStatus billStatus = billStatuses.stream().filter(billStatus1 -> billStatus1.getStatus().equals(INITIATED_STATUS)).findFirst().get();
                if (billStatus != null)
                    expenseCalculatorRepository.updateBillStatus(billStatus.getId(), FAILED_STATUS, customException.getCode() + " " + customException.getMessage());
            }
            throw customException;

        } catch (Exception e) {
            String referenceId = projectResponse.getProject().get(0).getProjectHierarchy();
            List<BillStatus> billStatuses = expenseCalculatorRepository.getBillStatusByReferenceId(referenceId);
            if (!billStatuses.isEmpty()) {
                BillStatus billStatus = billStatuses.stream().filter(billStatus1 -> billStatus1.getStatus().equals(INITIATED_STATUS)).findFirst().get();
                if (billStatus != null)
                    expenseCalculatorRepository.updateBillStatus(billStatus.getId(), FAILED_STATUS,  e.getMessage());
            }
            throw new CustomException("EXCEPTION", e.getMessage());
        }
    }

    private List<Bill> createWageBill(RequestInfo requestInfo, Criteria criteria, Map<String, String> metaInfo,
                                      Project project, boolean isDistrictLevel) {
        log.info("Create wage bill for musterRollIds :"+criteria.getMusterRollId());
        Bill bill = Bill.builder().totalAmount(BigDecimal.ZERO).billDetails(new ArrayList<>()).build();
        String parentProjectId = project.getProjectHierarchy();
        if (project.getProjectHierarchy() == null) {
            parentProjectId = project.getId();
        } else if (project.getProjectHierarchy().contains(".")) {
            parentProjectId = parentProjectId.split("\\.")[0];
        }
        // Fetching mdms data for campaign id
        List<WorkerMdms> workerMdms = fetchMDMSDataForWorker(requestInfo, criteria.getTenantId(), parentProjectId);
        List<AttendanceRegister> attendanceRegisters = new ArrayList<>();
        Integer offset = 0;
            // fetch approved attendance registers
        do {
            // Fetch children attendance registers if district level else just fetch current level registers
            attendanceRegisters = attendanceUtil
                    .fetchAttendanceRegister(criteria.getReferenceId(), criteria.getTenantId(), requestInfo,
                            criteria.getLocalityCode(), isDistrictLevel, offset);
            if (attendanceRegisters.isEmpty())
                break;
            offset = offset + config.getRegisterBatchSize();
            if (config.isAttendanceApprovalRequired())
                expenseCalculatorServiceValidator.validateAttendanceRegisterApproval(attendanceRegisters);

            List<String> regIds = attendanceRegisters.stream().map(AttendanceRegister::getId).collect(Collectors.toList());
            // Fetch musterRolls for given muster roll IDs
            List<MusterRoll> musterRolls =  expenseCalculatorUtil.fetchMusterRollByRegIds(requestInfo,criteria.getTenantId(),regIds);

            wageSeekerBillGeneratorService.createWageSeekerBillsHealth(requestInfo,musterRolls,workerMdms, bill);
        } while(attendanceRegisters.size() > 0);

        //Enrich bill common fields
        enrichBill(bill, criteria, project);
        return Collections.singletonList(bill);
    }

    private List<Bill> createSupervisionBill(RequestInfo requestInfo, Criteria criteria, Map<String, String> metaInfo) {
        log.info("Create supervision bill for contractId :"+criteria.getReferenceId() );
        List<Bill> expenseBills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getReferenceId().trim());
        validateExpenseBills(expenseBills, criteria);
        Calculation calculation = supervisionBillGeneratorService.estimateBill(requestInfo, criteria, expenseBills);
        validateCalculationDetails(calculation,criteria);
        //Construct meta object to persist in calculator db
        Contract contract = expenseCalculatorUtil.fetchContract(requestInfo, criteria.getTenantId(),criteria.getReferenceId()).get(0);
        Map<String, String> contractProjectMapping = buildContractProjectMapping(contract);
        metaInfo.putAll(contractProjectMapping);
        return supervisionBillGeneratorService.createSupervisionBill(requestInfo, criteria,calculation);
    }

    private void validateExpenseBills(List<Bill> expenseBills, Criteria criteria) {
        if(expenseBills == null || expenseBills.isEmpty()) {
            log.info("SupervisionBillGeneratorService::calculateEstimate::Wage bill and purchase bill not created. "
                    + " So Supervision bill cannot be calculated.");
            throw new CustomException("NO_WAGE_PURCHASE_BILL",
                    String.format("No wage or purchase bills are found for this contract %s and tenant %s. So Supervision bill cannot be calculated.", criteria.getReferenceId(), criteria.getTenantId()));
        }
    }

    private void validateCalculationDetails(Calculation calculation, Criteria criteria) {

        Boolean hasCalcDetail = false;
        for (CalcEstimate estimate: calculation.getEstimates()) {
            if (estimate.getCalcDetails() != null && !estimate.getCalcDetails().isEmpty()) {
                hasCalcDetail = true;
            }
        }
        if (!Boolean.TRUE.equals(hasCalcDetail)) {
            log.info("ExpenseCalculatorService::createWageOrSupervisionBills::Supervision bill will not created because there are no calculation details in estimate.");
            throw new CustomException("NO_CALCULATION_DETAIL",
                    String.format("No calculation details found for bills of contract %s and tenant %s. So Supervision bill cannot be generated.", criteria.getReferenceId(), criteria.getTenantId()));
        }
    }

    private Map<String, String> buildContractProjectMapping(Contract contract) {
        Map<String, String> contractProjectMapping = new HashMap<>();
        Object additionalDetails = contract.getAdditionalDetails();
        Optional<String> projectIdOptional = commonUtil.findValue(additionalDetails, PROJECT_ID_CONSTANT);
        if (contract.getContractNumber()!=null && projectIdOptional.isPresent()) {
            contractProjectMapping.put(PROJECT_ID_OF_CONSTANT + contract.getContractNumber(), projectIdOptional.get());
        }
        // Put OrgId in meta
        contractProjectMapping.put(ORG_ID_CONSTANT,contract.getOrgId());
        return contractProjectMapping;
    }

    public void createAndPostWageSeekerBill(MusterRollRequest musterRollRequest){
        log.info("Create and post the wage bill for consumed msg");
        expenseCalculatorServiceValidator.validateWageBillCreateForMusterRollRequest(musterRollRequest);
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        // Contract project mapping
        Map<String, String> contractProjectMapping = getContractProjectMapping(Collections.singletonList(musterRoll));
        Map<String, String> context = new HashMap<>();
        context.putAll(contractProjectMapping);
        // Fetch wage seeker skills from MDMS
        List<SorDetail> sorDetails = fetchMDMSDataForLabourCharges(requestInfo, musterRoll.getTenantId(), Collections.singletonList(musterRoll));
        List<Bill> wageSeekerBills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,Collections.singletonList(musterRoll),sorDetails,context);
        BillResponse billResponse = null;
        Workflow workflow = Workflow.builder()
                            .action(WF_SUBMIT_ACTION_CONSTANT)
                            .build();
        for(Bill bill : wageSeekerBills) {
            billResponse = postCreateBill(requestInfo, bill,workflow);
            if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
            {
                List<Bill> bills = billResponse.getBills();
                persistMeta(bills,context);
            }
        }
    }

    private Map<String, String> getContractProjectMapping(List<MusterRoll> musterRolls) {
        Map<String,String> contractProjectMapping = new HashMap<>();

        for(MusterRoll musterRoll : musterRolls) {
            Object additionalDetails = musterRoll.getAdditionalDetails();
            Optional<String> projectIdOptional = commonUtil.findValue(additionalDetails, PROJECT_ID_CONSTANT);
            Optional<String> contractIdOptional = commonUtil.findValue(additionalDetails, CONTRACT_ID_CONSTANT);
            if(contractIdOptional.isPresent() && projectIdOptional.isPresent()){
                contractProjectMapping.put(PROJECT_ID_OF_CONSTANT+contractIdOptional.get(),projectIdOptional.get());
            }
        }
        return contractProjectMapping;
    }
    private BillResponse postCreateBill(RequestInfo requestInfo, Bill bill, Workflow workflow){
        log.info("Post bill for create");
        return billUtils.postCreateBill(requestInfo, bill, workflow);
    }

    private BillResponse postUpdateBill(RequestInfo requestInfo, Bill bill, Workflow workflow){
        log.info("Post bill for update");
        return billUtils.postUpdateBill(requestInfo, bill, workflow);
    }

    public List<WorkerMdms> fetchMDMSDataForWorker(RequestInfo requestInfo, String tenantId, String campaignId){
        log.info("Fetch worker MDMS");
        Object mdmsData = mdmsUtils.getWorkerRateFromMDMSV2(requestInfo, tenantId, campaignId);
        List<Object> workerListJson = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_HCM);
        List<WorkerMdms> workerMdmsList = new ArrayList<>();
        for(Object obj : workerListJson){
            WorkerMdms workerMdms = mapper.convertValue(obj, WorkerMdms.class);
            workerMdmsList.add(workerMdms);
        }
        if (workerMdmsList.isEmpty()) {
            throw new CustomException("RATES_NOT_CONFIGURED_IN_MDMS", "rates are not configured in mdms for campaign id:: " + campaignId);
        }
        return workerMdmsList;
    }

    private List<SorDetail> fetchMDMSDataForLabourCharges(RequestInfo requestInfo, String tenantId, List<MusterRoll> musterRolls){
        log.info("Fetch wage seeker skills MDMS");
//        Object mdmsData = mdmsUtils.fetchMDMSDataForLabourCharges(requestInfo, tenantId);
        List<String> sorList = getLabourSorFromMusterRolls(musterRolls);
        if (sorList.isEmpty()) {
            throw new CustomException("SOR_NOT_FOUND", "No sor found in additional details of muster roll");
        }
        Object sorFromMDMSV2 = mdmsUtils.getLabourSorFromMDMSV2(requestInfo, tenantId, sorList, false);
        List<Object> sorListJson = commonUtil.readJSONPathValue(sorFromMDMSV2, JSON_PATH_FOR_SOR);
        List<SorDetail> sorDetails = new ArrayList<>();
        for(Object obj : sorListJson){
            SorDetail sorDetail = mapper.convertValue(obj, SorDetail.class);
            sorDetails.add(sorDetail);
        }
        List<String> sorIds = sorDetails.stream().map(SorDetail::getId).collect(Collectors.toList());
        if (sorIds.isEmpty()) {
            throw new CustomException("NO_SOR_FOUND", "No sor found in mdms");
        }
        Object ratesFromMDMV2 = mdmsUtils.getLabourSorFromMDMSV2(requestInfo, tenantId, sorIds, true);
        List<Object> rateListJson = commonUtil.readJSONPathValue(ratesFromMDMV2, JSON_PATH_FOR_RATES);
        List<RateDetail> rateDetails = new ArrayList<>();
        for(Object obj : rateListJson){
            RateDetail rateDetail = mapper.convertValue(obj, RateDetail.class);
            rateDetails.add(rateDetail);
        }
        for (RateDetail rateDetail : rateDetails) {
            for (SorDetail sorDetail : sorDetails) {
                if (rateDetail.getSorId().equalsIgnoreCase(sorDetail.getId())) {
                    if (sorDetail.getRateDetails() == null) {
                        sorDetail.setRateDetails(new ArrayList<>());
                        sorDetail.getRateDetails().add(rateDetail);
                    } else {
                        sorDetail.getRateDetails().add(rateDetail);
                    }
                }
            }
        }
//        List<Object> labourChargesJson = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_LABOUR_CHARGES);
//        List<LabourCharge> labourCharges = new ArrayList<>();
//        for(Object obj : labourChargesJson){
//            LabourCharge labourCharge = mapper.convertValue(obj, LabourCharge.class);
//            labourCharges.add(labourCharge);
//        }
        log.info("Wage seeker skills fetched from MDMS");
        return sorDetails;
    }

    public List<MusterRoll> fetchApprovedMusterRolls(RequestInfo requestInfo, Criteria criteria, boolean onlyApproved) {
        List<String> musterRollIds = criteria.getMusterRollId();
        String tenantId = criteria.getTenantId();
        return expenseCalculatorUtil.fetchMusterRollByIds(requestInfo,tenantId,musterRollIds,onlyApproved);
    }

    private void persistMeta(List<Bill> bills,Map<String, String> metaInfo) {
        BillMetaRecords billMetaRecords = billToMetaMapper.map(bills,metaInfo);
        expenseCalculatorProducer.push(config.getCalculatorCreateBillTopic(),billMetaRecords);
        log.info("Meta records pushed into topic ["+config.getCalculatorCreateBillTopic()+"]");
    }

    public List<BillMapper> search(CalculatorSearchRequest calculatorSearchRequest) {
        log.info("Validate calculatorSearchRequest");
        expenseCalculatorServiceValidator.validateCalculatorSearchRequest(calculatorSearchRequest);

        RequestInfo requestInfo=calculatorSearchRequest.getRequestInfo();
        
        CalculatorSearchCriteria searchCriteria = calculatorSearchRequest.getSearchCriteria();

        String tenantId=searchCriteria.getTenantId();
        
        //If we've got a project name or ward search, do this step first
        if(searchCriteria.getProjectName()!=null || searchCriteria.getBoundary()!=null) {
        	//Add the other search criteria and fetch the project numbers that match the criteria
        	Object projectResults = projectUtils.getProjectDetails(calculatorSearchRequest);
        	
        	 //If project payload changes, this key needs to be modified!
            List<Project> projects = objectMapper.convertValue(((LinkedHashMap) projectResults).get("Project"), new TypeReference<List<Project>>() {
            })  ;
            
            List<String> list = projects.stream()
                    .map(t->t.getProjectNumber())
                    .collect(Collectors.toList());
            
        	//Now go back and fetch the bill Ids that satisfy this criteria.
        	searchCriteria.setProjectNumbers(list);
        }
        
        Map<String,BillMapper> billMappers=expenseCalculatorRepository.getBillMappers(calculatorSearchRequest);
        List<String> billIds = billMappers.values().stream().map(m->m.getBillId()).collect(Collectors.toList());
        //set total count
        Integer totalCount= expenseCalculatorRepository.getBillCount(calculatorSearchRequest);
        calculatorSearchRequest.getPagination().setTotalCount(totalCount);


        //checks if billIds are present
        List<Bill> bills=new ArrayList<>();
        if(!CollectionUtils.isEmpty(billMappers.keySet())) {
             bills= expenseCalculatorUtil.fetchBillsWithBillIds(requestInfo, tenantId, billIds);
        }

        //set bills in billMapper
        for(Bill bill:bills){
            if(billMappers.containsKey(bill.getId())){
                billMappers.get(bill.getId()).setBill(bill);
            }
        }
        return new ArrayList<>(billMappers.values());
    }


  


    /**
     * Fetches the bills for the provided contract
     * @param requestInfo
     * @param tenantId
     * @param contractId
     * @return
     */
    private List<Bill> fetchBills(RequestInfo requestInfo, String tenantId, String contractId) {
        return expenseCalculatorUtil.fetchBills(requestInfo, tenantId, contractId);
    }

    private List<BusinessService> fetchMDMSDataForBusinessService(RequestInfo requestInfo, String tenantId){
        log.info("Fetch business service list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmodule(requestInfo, tenantId, MDMS_BUSINESS_SERVICE);
        List<Object> payerListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_BUSINESS_SERVICE_VERIFICATION);
        List<BusinessService> businessServices = new ArrayList<>();
        for(Object obj : payerListJson){
            BusinessService payer = mapper.convertValue(obj, BusinessService.class);
            businessServices.add(payer);
        }
        log.info("Business Service fetched from MDMS");
        return businessServices;
    }

    private List<String> getLabourSorFromMusterRolls (List<MusterRoll> musterRolls) {
        List<IndividualEntry> individualEntries = musterRolls.stream().map(musterRoll -> musterRoll.getIndividualEntries()).flatMap(List::stream).collect(Collectors.toList());
//        List<String> sorList = individualEntries.stream().filter(individualEntries -> individualEntries.getAdditionalDetails() != null && individualEntries.getAdditionalDetails().get("sor") != null).map(individualEntries -> (String) individualEntries.getAdditionalDetails().get("sor")).collect(Collectors.toList());
        return individualEntries.stream() // Stream<IndividualEntry>
                        .filter(entry -> {
                            Map<String, Object> additionalDetails = (Map<String, Object>) entry.getAdditionalDetails(); // Cast to Map<String, Object>
                            return additionalDetails != null && additionalDetails.get("skillCode") != null;
                        })
                        .map(entry -> {
                            Map<String, Object> additionalDetails = (Map<String, Object>) entry.getAdditionalDetails(); // Cast to Map<String, Object>
                            return (String) additionalDetails.get("skillCode");
                        })
                        .collect(Collectors.toList());
    }

    public void processBillForAdditionalDetailsEnrichment(BillRequest request) {
        log.info("Process bill for additional details enrichment");
        RequestInfo requestInfo = request.getRequestInfo();
        Bill bill = request.getBill();
        String referenceId = bill.getReferenceId();
        String workOrderNumber = referenceId.split("_")[0];
        ContractResponse contractResponse = contractUtils.fetchContract(requestInfo, bill.getTenantId(), workOrderNumber);
        if(contractResponse == null || contractResponse.getContracts() == null || contractResponse.getContracts().isEmpty()) {
            log.error("No contract found for work order number " + workOrderNumber);
            return;
        }
        String estimateId = contractResponse.getContracts().get(0).getLineItems().get(0).getEstimateId();
        List<Estimate> estimates = estimateServiceUtil.fetchEstimates(bill.getTenantId(), Collections.singleton(estimateId), requestInfo);
        if(estimates == null || estimates.isEmpty()) {
            log.error("No estimate found for estimate id " + estimateId);
            return;
        }
        Estimate estimate = estimates.get(0);
        String projectId = estimate.getProjectId();
        ProjectResponse projectResponse = projectUtils.getProjectDetails(requestInfo, bill.getTenantId(), projectId, "Remove");
        if(projectResponse == null || projectResponse.getProject() == null) {
            log.error("No project found for project id " + projectId);
            return;
        }
        Project project = projectResponse.getProject().get(0);
        enrichAdditionalDetails(bill, project);
        expenseCalculatorProducer.push(config.getBillIndexTopic(), request);
    }

    private void enrichAdditionalDetails(Bill bill, Project project) {
        ObjectNode additionalDetails = mapper.createObjectNode();
        if(bill.getAdditionalDetails() != null){
            additionalDetails = objectMapper.convertValue(bill.getAdditionalDetails(), ObjectNode.class);
        }

        additionalDetails.put("projectName", project.getName());
        additionalDetails.put("projectNumber", project.getProjectNumber());
        additionalDetails.put("ward", project.getAddress().getBoundary());
        additionalDetails.put("projectDescription", project.getDescription());
        additionalDetails.put("projectCreatedDate", project.getAuditDetails().getCreatedTime());

        bill.setAdditionalDetails(additionalDetails);
    }

    private boolean isBoundaryDistrictLevel(TenantBoundary boundary) {
        return boundary.getBoundary().get(0).getBoundaryType().equals("DISTRICT");
    }

    private void enrichBill(Bill bill, Criteria criteria,  Project project) {
        bill.setFromPeriod(project.getStartDate());
        bill.setBillDate(System.currentTimeMillis());
        bill.setToPeriod(project.getEndDate());
        bill.setTenantId(criteria.getTenantId());
        bill.setReferenceId(project.getProjectHierarchy() == null ? project.getId() : project.getProjectHierarchy());
        bill.setBusinessService("EXPENSE.WAGES");
        bill.setStatus("ACTIVE");
        bill.setLocalityCode(criteria.getLocalityCode());
        bill.setPayer(Party.builder().identifier(project.getId()).tenantId(criteria.getTenantId()).type("ORG").build());
        Set<String> distinctRegisters = bill.getBillDetails().stream().map(BillDetail::getReferenceId).collect(Collectors.toSet());
        // If additional details object is null add number of distinct registers in new object, else take additional details object and add number of distinct registers
        ObjectNode additionalDetails = mapper.createObjectNode();
        if(bill.getAdditionalDetails() != null){
            additionalDetails = objectMapper.convertValue(bill.getAdditionalDetails(), ObjectNode.class);
        }
        additionalDetails.put(NO_OF_REGISTERS, distinctRegisters.size());
        additionalDetails.put(NO_OF_BILL_DETAILS, bill.getBillDetails().size());
        bill.setAdditionalDetails(additionalDetails);
    }
}
