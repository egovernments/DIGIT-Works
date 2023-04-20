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

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorConstants.SUCCESSFUL_CONSTANT;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_WAGE_SEEKERS_SKILLS;

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
    private MusterRollUtils musterRollUtils;

    @Autowired
    private BillToMetaMapper billToMetaMapper;

    @Autowired
    private ExpenseCalculatorProducer producer;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public Calculation calculateEstimates(CalculationRequest calculationRequest) {
        expenseCalculatorServiceValidator.validateCalculatorEstimateRequest(calculationRequest);
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();

        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty()) {
            // Fetch wage seeker skills from MDMS
            Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, criteria.getTenantId());
            // Fetch all the approved muster rolls for provided muster Ids
            List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo,criteria,false);
            return wageSeekerBillGeneratorService.calculateEstimates(requestInfo , criteria.getTenantId(), musterRolls, wageSeekerSkillCodeAmountMapping);
        }
       else {
            //TODO
            // Supervision service implementation : for now returning empty calculation
            return Calculation.builder().build();
        }

    }

    public List<Bill> createPurchaseBill(PurchaseBillRequest purchaseBillRequest){
        expenseCalculatorServiceValidator.validatePurchaseRequest(purchaseBillRequest);
        purchaseBillRequest.getDocuments();
        Bill purchaseBill = purchaseBillGeneratorService.createPurchaseBill(purchaseBillRequest);
        BillResponse billResponse = postBill(purchaseBillRequest.getRequestInfo(), purchaseBill);
        List<Bill> bills = billResponse.getBills();
        persistMeta(bills);
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
            List<Bill> wageSeekerBills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,musterRolls,wageSeekerSkillCodeAmountMapping);
            BillResponse billResponse = null;
            List<Bill> submittedBills = new ArrayList<>();
            for(Bill bill : wageSeekerBills) {
                billResponse = postBill(requestInfo, bill);
                if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
                {
                    List<Bill> bills = billResponse.getBills();
                    persistMeta(bills);
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

    public void createAndPostWageSeekerBill(MusterRollRequest musterRollRequest){
        expenseCalculatorServiceValidator.validateWageBillCreateForMusterRollRequest(musterRollRequest);
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        // Fetch wage seeker skills from MDMS
        Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, musterRoll.getTenantId());
        List<Bill> wageSeekerBills = wageSeekerBillGeneratorService.createWageSeekerBills(requestInfo,Collections.singletonList(musterRoll),wageSeekerSkillCodeAmountMapping);
        BillResponse billResponse = null;
        for(Bill bill : wageSeekerBills) {
            billResponse = postBill(requestInfo, bill);
            if(SUCCESSFUL_CONSTANT.equalsIgnoreCase( billResponse.getResponseInfo().getStatus()))
            {
                List<Bill> bills = billResponse.getBills();
                persistMeta(bills);
            }
        }
    }

    private BillResponse postBill(RequestInfo requestInfo, Bill bill){
        BillResponse billResponse = billUtils.postBill(requestInfo, bill);
        log.info("billResponse =====> "+billResponse);
        return billResponse;
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
        return musterRollUtils.fetchMusterRollByIds(requestInfo,tenantId,musterRollIds,onlyApproved);
    }

    private void persistMeta(List<Bill> bills) {
        BillMetaRecords billMetaRecords = billToMetaMapper.map(bills);
        producer.push(configs.getCalculatorCreateBillTopic(),billMetaRecords);
    }
}
