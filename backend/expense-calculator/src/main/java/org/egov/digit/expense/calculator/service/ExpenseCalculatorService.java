package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.mapper.BillToMetaMapper;
import org.egov.digit.expense.calculator.repository.*;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.validator.ExpenseCalculatorServiceValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    private ObjectMapper mapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;
    @Autowired
    private BillToMetaMapper billToMetaMapper;
    @Autowired
    private ExpenseCalculatorRepository expenseCalculatorRepository;

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
            return supervisionBillGeneratorService.calculateEstimate(requestInfo, criteria, bills);
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
        //Fetch Payers from MDMS
        List<Payer> payers = fetchMDMSDataForPayers(requestInfo, tenantId);
        // Fetch HeadCodes from MDMS
        List<HeadCode> headCodes = fetchMDMSDataForHeadCode(requestInfo, tenantId);
        // Fetch Applicable Charges from MDMS
        List<ApplicableCharge> applicableCharges = fetchMDMSDataForApplicableCharges(requestInfo, tenantId);
        // Create the bill
        return purchaseBillGeneratorService.createPurchaseBill(requestInfo,providedPurchaseBill,payers,headCodes,applicableCharges,metaInfo);
    }

    private Bill updatePurchaseBill(PurchaseBillRequest purchaseBillRequest , Map<String, String> metaInfo){
        log.info("Update purchase bill");
        expenseCalculatorServiceValidator.validateUpdatePurchaseRequest(purchaseBillRequest);

        RequestInfo requestInfo = purchaseBillRequest.getRequestInfo();
        PurchaseBill providedPurchaseBill = purchaseBillRequest.getBill();
        String tenantId = providedPurchaseBill.getTenantId();
        //Fetch Payers from MDMS
        List<Payer> payers = fetchMDMSDataForPayers(requestInfo, tenantId);
        // Fetch HeadCodes from MDMS
        List<HeadCode> headCodes = fetchMDMSDataForHeadCode(requestInfo, tenantId);
        // Fetch Applicable Charges from MDMS
        List<ApplicableCharge> applicableCharges = fetchMDMSDataForApplicableCharges(requestInfo, tenantId);
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
            List<Bill> expenseBills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getContractId());
            Calculation calculation = supervisionBillGeneratorService.calculateEstimate(requestInfo, criteria, expenseBills);
            bills = supervisionBillGeneratorService.createSupervisionBill(requestInfo, criteria,calculation, expenseBills);
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
                List<Bill> respBills = billResponse.getBills();
                if(respBills != null && !respBills.isEmpty()) {
                    persistMeta(respBills,metaInfo);
                    submittedBills.addAll(respBills);
                }
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
        RequestInfo requestInfo=calculatorSearchRequest.getRequestInfo();
        String tenantId=calculatorSearchRequest.getSearchCriteria().getTenantId();

        Map<String,BillMapper> billMappers=expenseCalculatorRepository.getBillMappers(calculatorSearchRequest);
        List<Bill> bills=expenseCalculatorUtil.fetchBillsWithBillIds(requestInfo,tenantId,new ArrayList<>(billMappers.keySet()));

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

    private List<Payer> fetchMDMSDataForPayers(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch payer list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmodule(requestInfo, rootTenantId, MDMD_PAYER_LIST);
        List<Object> payerListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_PAYER);
        List<Payer> payers = new ArrayList<>();
        for(Object obj : payerListJson){
            Payer payer = mapper.convertValue(obj, Payer.class);
            payers.add(payer);
        }
        log.info("Payers fetched from MDMS");
        return payers;
    }

    private List<HeadCode> fetchMDMSDataForHeadCode(RequestInfo requestInfo, String tenantId) {
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch head code list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmodule(requestInfo, rootTenantId, MDMS_HEAD_CODES);
        List<Object> headCodeListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_HEAD_CODES);
        List<HeadCode> headCodes = new ArrayList<>();
        for(Object obj : headCodeListJson){
            HeadCode headCode = mapper.convertValue(obj, HeadCode.class);
            headCodes.add(headCode);
        }
        log.info("Head codes fetched from MDMS");
        return headCodes;
    }

    private List<ApplicableCharge> fetchMDMSDataForApplicableCharges(RequestInfo requestInfo, String tenantId) {
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch head code list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmodule(requestInfo, rootTenantId,MDMS_APPLICABLE_CHARGES);
        List<Object> applicableChargesListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_APPLICABLE_CHARGES);
        List<ApplicableCharge> applicableCharges = new ArrayList<>();
        for(Object obj : applicableChargesListJson){
            ApplicableCharge applicableCharge = mapper.convertValue(obj, ApplicableCharge.class);
            applicableCharges.add(applicableCharge);
        }
        log.info("Head codes fetched from MDMS");
        return applicableCharges;
    }
}
