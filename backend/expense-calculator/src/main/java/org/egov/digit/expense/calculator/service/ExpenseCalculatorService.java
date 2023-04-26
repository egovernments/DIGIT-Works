package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.enrichment.ExpenseCalculatorEnrichment;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.mapper.BillToMetaMapper;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.validator.ExpenseCalculatorServiceValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Slf4j
@Service
public class ExpenseCalculatorService {

    @Autowired
    private ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator;

    @Autowired
    private ExpenseCalculatorEnrichment expenseCalculatorEnrichment;

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
        expenseCalculatorServiceValidator.validatePurchaseRequest(purchaseBillRequest);
        purchaseBillRequest.getDocuments();
        Bill purchaseBill = purchaseBillGeneratorService.createPurchaseBill(purchaseBillRequest);
        BillResponse billResponse = postBill(purchaseBillRequest.getRequestInfo(), purchaseBill);
        List<Bill> bills = billResponse.getBills();
        persistMeta(bills,new HashMap<>());
        return bills;
    }

    public List<Bill> createBills(CalculationRequest calculationRequest){
        expenseCalculatorServiceValidator.validateCalculatorCalculateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();
        List<Bill> bills = null;
        Map<String, String> metaInfo = new HashMap<>();

        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            // Fetch wage seeker skills from MDMS
            List<LabourCharge> labourCharges = fetchMDMSDataForLabourCharges(requestInfo, criteria.getTenantId());
            // Fetch musterRolls for given muster roll IDs
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo,criteria,true);
            // Contract project mapping
            Map<String, String> contractProjectMapping = getContractProjectMapping(musterRolls);
            metaInfo.putAll(contractProjectMapping);
            bills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,musterRolls,labourCharges,metaInfo);

        } else {
            List<Bill> expenseBills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getContractId());
            Calculation calculation = supervisionBillGeneratorService.calculateEstimate(requestInfo, criteria, expenseBills);
            bills = supervisionBillGeneratorService.createSupervisionBill(requestInfo, criteria,calculation, expenseBills);
            //expenseCalculatorProducer.push(config.getCalculatorCreateTopic(),calculation);
        }

        BillResponse billResponse = null;
        List<Bill> submittedBills = new ArrayList<>();
        for(Bill bill : bills) {
            billResponse = postBill(requestInfo, bill);
            if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
            {
                submittedBills = billResponse.getBills();
                persistMeta(submittedBills,metaInfo);
            }
        }
        return submittedBills;
    }

    public void createAndPostWageSeekerBill(MusterRollRequest musterRollRequest){
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
        for(Bill bill : wageSeekerBills) {
            billResponse = postBill(requestInfo, bill);
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
    private BillResponse postBill(RequestInfo requestInfo, Bill bill){
        return billUtils.postBill(requestInfo, bill);
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
}
