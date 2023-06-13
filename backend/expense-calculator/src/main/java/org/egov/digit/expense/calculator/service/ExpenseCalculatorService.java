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
import org.egov.digit.expense.calculator.web.models.ApplicableCharge;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillMapper;
import org.egov.digit.expense.calculator.web.models.BillMetaRecords;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.BusinessService;
import org.egov.digit.expense.calculator.web.models.Calculation;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchCriteria;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.egov.digit.expense.calculator.web.models.Contract;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.digit.expense.calculator.web.models.HeadCode;
import org.egov.digit.expense.calculator.web.models.LabourCharge;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollRequest;
import org.egov.digit.expense.calculator.web.models.Payer;
import org.egov.digit.expense.calculator.web.models.PurchaseBill;
import org.egov.digit.expense.calculator.web.models.PurchaseBillRequest;
import org.egov.digit.expense.calculator.web.models.Workflow;
import org.egov.digit.expense.calculator.web.models.CalcEstimate;
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
    @Autowired
    private ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator;

    @Autowired
    private WageSeekerBillGeneratorService wageSeekerBillGeneratorService;
    @Autowired
    private SupervisionBillGeneratorService supervisionBillGeneratorService;
    @Autowired
    private ExpenseCalculatorProducer expenseCalculatorProducer;
    @Autowired
    private ExpenseCalculatorConfiguration config;
    @Autowired
    private PurchaseBillGeneratorService purchaseBillGeneratorService;
    @Autowired
    private MdmsUtils mdmsUtils;
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private ProjectUtil projectUtils;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;
    @Autowired
    private BillToMetaMapper billToMetaMapper;
    @Autowired
    private ExpenseCalculatorRepository expenseCalculatorRepository;
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private ObjectMapper objectMapper;

    public Calculation calculateEstimates(CalculationRequest calculationRequest) {
        expenseCalculatorServiceValidator.validateCalculatorEstimateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();

        if (criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            // Fetch wage seeker skills from MDMS
            List<LabourCharge> labourCharges = fetchMDMSDataForLabourCharges(requestInfo, criteria.getTenantId());
            // Fetch all the approved muster rolls for provided muster Ids
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo, criteria, false);
            return wageSeekerBillGeneratorService.calculateEstimates(requestInfo, criteria.getTenantId(), musterRolls, labourCharges);
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
               // persistMeta(respBills,metaInfo);
//                try {
//                    notificationService.sendNotificationForPurchaseBill(purchaseBillRequest);
//                }catch (Exception e){
//                    log.error("Exception while sending notification: " + e);
//                }

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
        // Fetch Payers from MDMS
        List<Payer> payers = fetchMDMSDataForPayers(requestInfo, tenantId);
        // Fetch HeadCodes from MDMS
        List<HeadCode> headCodes = expenseCalculatorUtil.fetchHeadCodesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Fetch Applicable Charges from MDMS
        List<ApplicableCharge> applicableCharges = expenseCalculatorUtil.fetchApplicableChargesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Create the bill
        return purchaseBillGeneratorService.createPurchaseBill(requestInfo,providedPurchaseBill,payers,headCodes,applicableCharges,metaInfo);
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
        //Fetch Payers from MDMS
        List<Payer> payers = fetchMDMSDataForPayers(requestInfo, tenantId);
        // Fetch HeadCodes from MDMS
        List<HeadCode> headCodes = expenseCalculatorUtil.fetchHeadCodesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Fetch Applicable Charges from MDMS
        List<ApplicableCharge> applicableCharges = expenseCalculatorUtil.fetchApplicableChargesFromMDMSForService(requestInfo, tenantId, businessServiceName);
        // Create the bill
        return purchaseBillGeneratorService.updatePurchaseBill(requestInfo,providedPurchaseBill,payers,headCodes,applicableCharges,metaInfo);
    }

    public List<Bill> createWageOrSupervisionBills(CalculationRequest calculationRequest){
        expenseCalculatorServiceValidator.validateCalculatorCalculateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();
        List<Bill> bills = null;
        Map<String, String> metaInfo = new HashMap<>();

        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            log.info("Create wage bill for musterRollIds :"+criteria.getMusterRollId() );
            // Fetch wage seeker skills from MDMS
            List<LabourCharge> labourCharges = fetchMDMSDataForLabourCharges(requestInfo, criteria.getTenantId());
            // Fetch musterRolls for given muster roll IDs
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo,criteria,true);
            // Contract project mapping
            Map<String, String> contractProjectMapping = getContractProjectMapping(musterRolls);
            metaInfo.putAll(contractProjectMapping);
            bills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,musterRolls,labourCharges,metaInfo);

        } else {
            log.info("Create supervision bill for contractId :"+criteria.getContractId() );
            List<Bill> expenseBills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getContractId().trim());
            if(expenseBills!=null && !expenseBills.isEmpty())
            	log.info(String.format("Fetched %s bills from the repository", expenseBills.size()));
            //No bills have been fetched for this contract. Therefore, throw an exception
            else {
            	log.info("SupervisionBillGeneratorService::calculateEstimate::Wage bill and purchase bill not created. "
    					+ " So Supervision bill cannot be calculated.");
    			throw new CustomException("NO_WAGE_PURCHASE_BILL",
    					String.format("No wage or purchase bills are found for this contract %s and tenant %s. So Supervision bill cannot be calculated.", criteria.getContractId(), criteria.getTenantId()));
            }
            //Continue with doing the calculation for supervision bill
            Calculation calculation = supervisionBillGeneratorService.estimateBill(requestInfo, criteria, expenseBills);
            // Check calculation have any calculation details or not
            Boolean hasCalcDetail = false;
            for (CalcEstimate estimate: calculation.getEstimates()) {
                if (estimate.getCalcDetails() != null && !estimate.getCalcDetails().isEmpty()) {
                    hasCalcDetail = true;
                }
            }
            // If no calculation details are there then throw an exception
            if (hasCalcDetail == false) {
                log.info("ExpenseCalculatorService::createWageOrSupervisionBills::Supervision bill will not created because there are no calculation details in estimate.");
                throw new CustomException("NO_CALCULATION_DETAIL",
                        String.format("No calculation details found for bills of contract %s and tenant %s. So Supervision bill cannot be generated.", criteria.getContractId(), criteria.getTenantId()));
            }
            //Create the supervision bill
            bills = supervisionBillGeneratorService.createSupervisionBill(requestInfo, criteria,calculation, expenseBills);
    		
            //Construct meta object to persist in calculator db
            Contract contract = expenseCalculatorUtil.fetchContract(requestInfo, criteria.getTenantId(),criteria.getContractId()).get(0);
			Map<String, String> contractProjectMapping = new HashMap<>();
			Object additionalDetails = contract.getAdditionalDetails();
			Optional<String> projectIdOptional = commonUtil.findValue(additionalDetails, PROJECT_ID_CONSTANT);
			if (contract.getContractNumber()!=null && projectIdOptional.isPresent()) {
				contractProjectMapping.put(PROJECT_ID_OF_CONSTANT + contract.getContractNumber(), projectIdOptional.get());
			}
            // Put OrgId in meta
            contractProjectMapping.put(ORG_ID_CONSTANT,contract.getOrgId());
			metaInfo.putAll(contractProjectMapping);

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
        List<LabourCharge> labourCharges = fetchMDMSDataForLabourCharges(requestInfo, musterRoll.getTenantId());
        List<Bill> wageSeekerBills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,Collections.singletonList(musterRoll),labourCharges,context);
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

    private List<LabourCharge> fetchMDMSDataForLabourCharges(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch wage seeker skills MDMS");
        Object mdmsData = mdmsUtils.fetchMDMSDataForLabourCharges(requestInfo, rootTenantId);
        List<Object> labourChargesJson = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_LABOUR_CHARGES);
        List<LabourCharge> labourCharges = new ArrayList<>();
        for(Object obj : labourChargesJson){
            LabourCharge labourCharge = mapper.convertValue(obj, LabourCharge.class);
            labourCharges.add(labourCharge);
        }
        log.info("Wage seeker skills fetched from MDMS");
        return labourCharges;
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
        	//fetch all unique project numbers in the repo first
        	List<String> projectNumbers = expenseCalculatorRepository.getUniqueProjectNumbers(tenantId);
        	//Add the other search criteria and fetch the project numbers that match the criteria
        	Object projectResults = projectUtils.getProjectDetails(calculatorSearchRequest, projectNumbers);
        	
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
        List<Bill> bills = expenseCalculatorUtil.fetchBills(requestInfo, tenantId, contractId);
        return bills;
    }

    private List<BusinessService> fetchMDMSDataForBusinessService(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch business service list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmodule(requestInfo, rootTenantId, MDMS_BUSINESS_SERVICE);
        List<Object> payerListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_BUSINESS_SERVICE_VERIFICATION);
        List<BusinessService> businessServices = new ArrayList<>();
        for(Object obj : payerListJson){
            BusinessService payer = mapper.convertValue(obj, BusinessService.class);
            businessServices.add(payer);
        }
        log.info("Business Service fetched from MDMS");
        return businessServices;
    }

    private List<Payer> fetchMDMSDataForPayers(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch payer list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmoduleWithFilter(requestInfo, rootTenantId, MDMS_PAYER_LIST);
        List<Object> payerListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_PAYER);
        List<Payer> payers = new ArrayList<>();
        for(Object obj : payerListJson){
            Payer payer = mapper.convertValue(obj, Payer.class);
            payers.add(payer);
        }
        log.info("Payers fetched from MDMS");
        return payers;
    }

    
}
