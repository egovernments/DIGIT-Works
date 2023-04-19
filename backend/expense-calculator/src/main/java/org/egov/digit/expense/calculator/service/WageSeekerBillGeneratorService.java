package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_WAGE_SEEKERS_SKILLS;

@Slf4j
@Component
public class WageSeekerBillGeneratorService {

    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;

    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public Calculation calculateEstimates(RequestInfo requestInfo, Criteria criteria) {

        // Fetch all the approved muster rolls for provided muster Ids
        List<MusterRoll> musterRolls = fetchApprovedMusterRolls(requestInfo,criteria);

        // Fetch wage seeker skills from MDMS
        Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, criteria.getTenantId());

        // Calculate estimate for each muster roll
        List<CalcEstimate> calcEstimates = makeEstimatesForMusterRolls(musterRolls,wageSeekerSkillCodeAmountMapping);

        // Create Calculation
        return makeCalculation(calcEstimates,criteria.getTenantId());
    }



    public void createAndPostWageSeekerBill(RequestInfo requestInfo, MusterRoll musterRoll){
        // Fetch wage seeker skills from MDMS
        Map<String, Double> wageSeekerSkillCodeAmountMapping = fetchMDMSDataForWageSeekersSkills(requestInfo, musterRoll.getTenantId());
        List<Bill> bills = createBillForMusterRolls(Collections.singletonList(musterRoll), wageSeekerSkillCodeAmountMapping);
    }

    private List<Bill> createBillForMusterRolls(List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
            List<Bill> bills = new ArrayList<>();
            for(MusterRoll musterRoll : musterRolls){
                List<BillDetail> billDetails = new ArrayList<>();
                List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
                String tenantId = musterRoll.getTenantId();
                BigDecimal netPayableAmount = BigDecimal.ZERO;
                for(IndividualEntry individualEntry : individualEntries){
                    String individualId = individualEntry.getIndividualId();
                    // Calculate net amount to pay to wage seeker
                    Double skillAmount = getWageSeekerSkillAmount(individualEntry,wageSeekerSkillCodeAmountMapping);
                    BigDecimal actualAmountToPay = calculateAmount(individualEntry, BigDecimal.valueOf(skillAmount));
                    // Calculate net payable amount
                    netPayableAmount = netPayableAmount.add(actualAmountToPay);
                    // Build lineItem
                    LineItem lineItem = buildLineItem(tenantId,actualAmountToPay);
                    // Build payee
                    Party payee = buildPayee(individualId,configs.getWagePayeeType());
                    // Build BillDetail
                    BillDetail billDetail = BillDetail.builder()
                                                .referenceId(individualId)
                                                .paymentStatus("PENDING")
                                                .fromPeriod(musterRoll.getStartDate().longValue())
                                                .toPeriod(musterRoll.getEndDate().longValue())
                                                .payee(payee)
                                                .lineItems(Collections.singletonList(lineItem))
                                                .payableLineItems(Collections.singletonList(lineItem))
                                                .netLineItemAmount(actualAmountToPay)
                                                .build();

                    billDetails.add(billDetail);

                }
                Party payer = buildPayee(configs.getWagePayerId(),configs.getWagePayerType());
                // Build Bill
                Bill bill = Bill.builder()
                        .tenantId(tenantId)
                        .billDate(Instant.now().toEpochMilli())
                        .netPayableAmount(netPayableAmount)
                        .referenceId(musterRoll.getMusterRollNumber())
                        .businessService(configs.getWageBusinessService())
                        .fromPeriod(musterRoll.getStartDate().longValue())
                        .toPeriod(musterRoll.getEndDate().longValue())
                        .paymentStatus("PENDING")
                        .status("ACTIVE")
                        .billDetails(billDetails)
                        .build();

                bills.add(bill);
            }
            return bills;
    }

    private Calculation makeCalculation(List<CalcEstimate> calcEstimates, String tenantId) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(CalcEstimate estimate : calcEstimates){
            totalAmount = totalAmount.add(estimate.getNetPayableAmount());
        }
        return Calculation.builder()
                          .tenantId(tenantId)
                          .estimates(calcEstimates)
                          .totalAmount(totalAmount)
                          .build();
    }

    public List<CalcEstimate> makeEstimatesForMusterRolls(List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
        List<CalcEstimate> calcEstimates = new ArrayList<>();
        for(MusterRoll musterRoll : musterRolls){
            List<CalcDetail> calcDetails = new ArrayList<>();
            List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
            String tenantId = musterRoll.getTenantId();
            BigDecimal netPayableAmount = BigDecimal.ZERO;
            for(IndividualEntry individualEntry : individualEntries){
                String individualId = individualEntry.getIndividualId();
                // Calculate net amount to pay to wage seeker
                Double skillAmount = getWageSeekerSkillAmount(individualEntry,wageSeekerSkillCodeAmountMapping);
                BigDecimal actualAmountToPay = calculateAmount(individualEntry, BigDecimal.valueOf(skillAmount));
                // Calculate net payable amount
                netPayableAmount = netPayableAmount.add(actualAmountToPay);
                // Build lineItem
                LineItem lineItem = buildLineItem(tenantId,actualAmountToPay);
                // Build payee
                Party payee = buildPayee(individualId,configs.getWagePayeeType());
                // Build CalcDetail
                CalcDetail calcDetail = CalcDetail.builder()
                        .payee(payee)
                        .lineItems(Collections.singletonList(lineItem))
                        .payableLineItem(Collections.singletonList(lineItem))
                        .fromPeriod(musterRoll.getStartDate())
                        .toPeriod(musterRoll.getEndDate())
                        .referenceId(individualId).build();
                calcDetails.add(calcDetail);

            }
            // Build CalcEstimate
            CalcEstimate calcEstimate = CalcEstimate.builder()
                    .referenceId(musterRoll.getMusterRollNumber())
                    .fromPeriod(musterRoll.getStartDate())
                    .toPeriod(musterRoll.getEndDate())
                    .netPayableAmount(netPayableAmount)
                    .tenantId(tenantId)
                    .calcDetails(calcDetails)
                    .businessService(configs.getWageBusinessService())
                    .build();

            calcEstimates.add(calcEstimate);
        }
      return calcEstimates;
    }

    private LineItem buildLineItem(String tenantId, BigDecimal actualAmountToPay) {
       return LineItem.builder()
                .amount(actualAmountToPay)
                .headCode(configs.getWageHeadCode())
                .tenantId(tenantId)
                .build();
    }

    private Party buildPayee(String individualId, String type) {
       return Party.builder()
                .identifier(individualId)
                .type(type)
                .build();
    }

    private BigDecimal calculateAmount(IndividualEntry individualEntry, BigDecimal skillAmount) {
        BigDecimal totalAttendance = null;
        if(individualEntry.getModifiedTotalAttendance() != null){
            totalAttendance = individualEntry.getModifiedTotalAttendance();
        } else {
            totalAttendance = individualEntry.getActualTotalAttendance();
        }
        return totalAttendance.multiply(skillAmount);
    }

    private Double getWageSeekerSkillAmount(IndividualEntry individualEntry, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
        String individualId = individualEntry.getIndividualId();
        Object additionalDetails = individualEntry.getAdditionalDetails();
        Optional<String> skillCodeOptional = commonUtil.findValue(additionalDetails, "skillCode");
        if(!skillCodeOptional.isPresent()){
            log.error("SKILL_CODE_MISSING_FOR_INDIVIDUAL","Skill code is missing for individual ["+individualId+"]");
            throw new CustomException("SKILL_CODE_MISSING_FOR_INDIVIDUAL","Skill code is missing for individual ["+individualId+"]");
        }
        return wageSeekerSkillCodeAmountMapping.get(skillCodeOptional.get());
    }


    private Map<String,Double> fetchMDMSDataForWageSeekersSkills(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        Object mdmsData = mdmsUtils.fetchMDMSDataForWageSeekersSkills(requestInfo, rootTenantId);
        List<Object> wageSeekerSkillsJson = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_WAGE_SEEKERS_SKILLS);
        Map<String,Double> wageSeekerSkillCodeAmountMapping = new HashMap<>();
        for(Object obj : wageSeekerSkillsJson){
            WageSeekerSkill wageSeekerSkill = mapper.convertValue(obj, WageSeekerSkill.class);
            wageSeekerSkillCodeAmountMapping.put(wageSeekerSkill.getCode(),wageSeekerSkill.getAmount());
        }

        return wageSeekerSkillCodeAmountMapping;
    }
    public List<MusterRoll> fetchApprovedMusterRolls(RequestInfo requestInfo, Criteria criteria) {
        List<String> musterRollIds = criteria.getMusterRollId();
        String tenantId = criteria.getTenantId();
        return expenseCalculatorUtil.fetchMusterRollByIds(requestInfo,tenantId,musterRollIds);
    }
}
