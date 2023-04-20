package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
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
            Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, criteria.getTenantId());
            // Fetch all the approved muster rolls for provided muster Ids
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo, criteria, false);
            return wageSeekerBillGeneratorService.calculateEstimates(requestInfo, criteria.getTenantId(), musterRolls, wageSeekerSkillCodeAmountMapping);
        } else {
            return supervisionBillGeneratorService.calculateEstimate(requestInfo, criteria);
        }
    }
    public Calculation calculate(CalculationRequest calculationRequest) {
        Calculation calculation = calculateEstimates(calculationRequest);
        expenseCalculatorProducer.push(config.getCalculatorCreateTopic(),calculation);
        return calculation;
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

        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            // Fetch wage seeker skills from MDMS
            Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, criteria.getTenantId());
            // Fetch musterRolls for given muster roll IDs
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo,criteria,true);
            // Contract project mapping
            Map<String, String> contractProjectMapping = getContractProjectMapping(musterRolls);

            Map<String, String> context = new HashMap<>();

            context.putAll(contractProjectMapping);

            List<Bill> wageSeekerBills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,musterRolls,wageSeekerSkillCodeAmountMapping);
            BillResponse billResponse = null;
            List<Bill> submittedBills = new ArrayList<>();
            for(Bill bill : wageSeekerBills) {
                billResponse = postBill(requestInfo, bill);
                if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
                {
                    List<Bill> bills = billResponse.getBills();
                    persistMeta(bills,context);
                    submittedBills.addAll(bills);
                }
            }
            return submittedBills;
        }
        else {
            //TODO
            // Supervision service implementation : for now returning empty list of bills
            return new ArrayList<Bill>();
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

    public void createAndPostWageSeekerBill(MusterRollRequest musterRollRequest){
        expenseCalculatorServiceValidator.validateWageBillCreateForMusterRollRequest(musterRollRequest);
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        // Contract project mapping
        Map<String, String> contractProjectMapping = getContractProjectMapping(Collections.singletonList(musterRoll));
        Map<String, String> context = new HashMap<>();
        context.putAll(contractProjectMapping);
        // Fetch wage seeker skills from MDMS
        Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, musterRoll.getTenantId());
        List<Bill> wageSeekerBills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,Collections.singletonList(musterRoll),wageSeekerSkillCodeAmountMapping);
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

    private BillResponse postBill(RequestInfo requestInfo, Bill bill){
        return billUtils.postBill(requestInfo, bill);
    }

    private Map<String,Double> fetchMDMSDataForWageSeekersSkills(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        log.info("Fetch wage seeker skills MDMS");
        Object mdmsData = mdmsUtils.fetchMDMSDataForWageSeekersSkills(requestInfo, rootTenantId);
        List<Object> wageSeekerSkillsJson = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_WAGE_SEEKERS_SKILLS);
        Map<String,Double> wageSeekerSkillCodeAmountMapping = new HashMap<>();
        for(Object obj : wageSeekerSkillsJson){
            WageSeekerSkill wageSeekerSkill = mapper.convertValue(obj, WageSeekerSkill.class);
            wageSeekerSkillCodeAmountMapping.put(wageSeekerSkill.getCode(),wageSeekerSkill.getAmount());
        }
        log.info("Wage seeker skills fetched from MDMS");
        return wageSeekerSkillCodeAmountMapping;
    }

    public List<MusterRoll> fetchApprovedMusterRolls(RequestInfo requestInfo, Criteria criteria, boolean onlyApproved) {
        List<String> musterRollIds = criteria.getMusterRollId();
        String tenantId = criteria.getTenantId();
        return expenseCalculatorUtil.fetchMusterRollByIds(requestInfo,tenantId,musterRollIds,onlyApproved);
    }

    private void persistMeta(List<Bill> bills,Map<String, String> context) {
        BillMetaRecords billMetaRecords = billToMetaMapper.map(bills,context);
        expenseCalculatorProducer.push(config.getCalculatorCreateBillTopic(),billMetaRecords);
    }
}
