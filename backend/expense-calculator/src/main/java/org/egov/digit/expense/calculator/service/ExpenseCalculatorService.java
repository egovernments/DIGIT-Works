package org.egov.digit.expense.calculator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.mapper.BillToMetaMapper;
import org.egov.digit.expense.calculator.repository.ExpenseCalculatorRepository;
import org.egov.digit.expense.calculator.util.BillUtils;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.util.ProjectUtil;
import org.egov.digit.expense.calculator.validator.ExpenseCalculatorServiceValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
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

    @Autowired
    public ExpenseCalculatorService(ExpenseCalculatorProducer expenseCalculatorProducer, ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator, WageSeekerBillGeneratorService wageSeekerBillGeneratorService, SupervisionBillGeneratorService supervisionBillGeneratorService, BillToMetaMapper billToMetaMapper, ObjectMapper objectMapper, ExpenseCalculatorConfiguration config, PurchaseBillGeneratorService purchaseBillGeneratorService, MdmsUtils mdmsUtils, BillUtils billUtils, ProjectUtil projectUtils, ExpenseCalculatorUtil expenseCalculatorUtil, ExpenseCalculatorRepository expenseCalculatorRepository, ObjectMapper mapper, CommonUtil commonUtil) {
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
            List<Bill> bills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getContractId());
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

    public List<Bill> createWageOrSupervisionBills(CalculationRequest calculationRequest){
        expenseCalculatorServiceValidator.validateCalculatorCalculateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();
        List<Bill> bills = null;
        Map<String, String> metaInfo = new HashMap<>();

        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            bills = createWageBill(requestInfo, criteria, metaInfo);
        } else {
            bills = createSupervisionBill(requestInfo, criteria, metaInfo);
        }

        BillResponse billResponse = null;
        List<Bill> submittedBills = new ArrayList<>();
        Workflow workflow = Workflow.builder()
                .action(WF_SUBMIT_ACTION_CONSTANT)
                .build();
        for(Bill bill : bills) {
            billResponse = postCreateBill(requestInfo, bill,workflow);
            if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
            {
                log.info("Bill successfully posted to expense service. Reference ID " + bill.getReferenceId());
                List<Bill> respBills = billResponse.getBills();
                if(respBills != null && !respBills.isEmpty()) {
                    log.info("Persisting meta for bill reference ID: " + bill.getReferenceId());
                    persistMeta(respBills,metaInfo);
                    submittedBills.addAll(respBills);
                }
            }
            else {
                log.info("Bill posting failed for bill " + bill.getBusinessService() + " reference ID " + bill.getReferenceId());
            }
        }
        return submittedBills;
    }

    private List<Bill> createWageBill(RequestInfo requestInfo, Criteria criteria, Map<String, String> metaInfo) {
        log.info("Create wage bill for musterRollIds :"+criteria.getMusterRollId() );
        // Fetch musterRolls for given muster roll IDs
        List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo,criteria,true);
        // Fetch wage seeker skills from MDMS
//        List<LabourCharge> labourCharges = fetchMDMSDataForLabourCharges(requestInfo, criteria.getTenantId(), musterRolls);
        List<SorDetail> sorDetails = fetchMDMSDataForLabourCharges(requestInfo, criteria.getTenantId(), musterRolls);
        // Contract project mapping
        Map<String, String> contractProjectMapping = getContractProjectMapping(musterRolls);
        metaInfo.putAll(contractProjectMapping);
        return wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,musterRolls,sorDetails,metaInfo);
    }

    private List<Bill> createSupervisionBill(RequestInfo requestInfo, Criteria criteria, Map<String, String> metaInfo) {
        log.info("Create supervision bill for contractId :"+criteria.getContractId() );
        List<Bill> expenseBills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getContractId().trim());
        validateExpenseBills(expenseBills, criteria);
        Calculation calculation = supervisionBillGeneratorService.estimateBill(requestInfo, criteria, expenseBills);
        validateCalculationDetails(calculation,criteria);
        //Construct meta object to persist in calculator db
        Contract contract = expenseCalculatorUtil.fetchContract(requestInfo, criteria.getTenantId(),criteria.getContractId()).get(0);
        Map<String, String> contractProjectMapping = buildContractProjectMapping(contract);
        metaInfo.putAll(contractProjectMapping);
        return supervisionBillGeneratorService.createSupervisionBill(requestInfo, criteria,calculation);
    }

    private void validateExpenseBills(List<Bill> expenseBills, Criteria criteria) {
        if(expenseBills == null || expenseBills.isEmpty()) {
            log.info("SupervisionBillGeneratorService::calculateEstimate::Wage bill and purchase bill not created. "
                    + " So Supervision bill cannot be calculated.");
            throw new CustomException("NO_WAGE_PURCHASE_BILL",
                    String.format("No wage or purchase bills are found for this contract %s and tenant %s. So Supervision bill cannot be calculated.", criteria.getContractId(), criteria.getTenantId()));
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
                    String.format("No calculation details found for bills of contract %s and tenant %s. So Supervision bill cannot be generated.", criteria.getContractId(), criteria.getTenantId()));
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
    
}
