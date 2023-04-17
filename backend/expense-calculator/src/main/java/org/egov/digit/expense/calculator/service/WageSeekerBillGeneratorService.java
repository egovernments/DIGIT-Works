package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MusterRollUtils;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorConstants.CONTRACT_ID_CONSTANT;

@Slf4j
@Component
public class WageSeekerBillGeneratorService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MusterRollUtils musterRollUtils;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public Calculation calculateEstimates(String tenantId, List<MusterRoll> musterRolls,Map<String, Double> wageSeekerSkillCodeAmountMapping) {
        // Calculate estimate for each muster roll
        List<CalcEstimate> calcEstimates = createEstimatesForMusterRolls(musterRolls,wageSeekerSkillCodeAmountMapping);
        // Create Calculation
        return makeCalculation(calcEstimates,tenantId);
    }
    public List<Bill> createWageSeekerBills(List<MusterRoll> musterRolls,Map<String, Double> wageSeekerSkillCodeAmountMapping){
        // Create bills for muster rolls
        return createBillForMusterRolls(musterRolls, wageSeekerSkillCodeAmountMapping);
    }


    private List<Bill> createBillForMusterRolls(List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
            List<Bill> bills = new ArrayList<>();
            List<String> musterRollNumbers = new ArrayList<>();
            for(MusterRoll musterRoll : musterRolls){
                musterRollNumbers.add(musterRoll.getMusterRollNumber());
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
                                                .fromPeriod(musterRoll.getStartDate())
                                                .toPeriod(musterRoll.getEndDate())
                                                .payee(payee)
                                                .lineItems(Collections.singletonList(lineItem))
                                                .payableLineItems(Collections.singletonList(lineItem))
                                                .netLineItemAmount(actualAmountToPay)
                                                .build();

                    billDetails.add(billDetail);

                }
                Party payer = buildPayee(configs.getWagePayerId(),configs.getWagePayerType());
                // Get and populate contractId
                String contractId = getContractId(musterRoll);
                // Build Bill
                Bill bill = Bill.builder()
                        .tenantId(tenantId)
                        .billDate(BigDecimal.valueOf(Instant.now().toEpochMilli()))
                        .netPayableAmount(netPayableAmount)
                        .referenceId(musterRoll.getMusterRollNumber())
                        .businessService(configs.getWageBusinessService())
                        .fromPeriod(musterRoll.getStartDate())
                        .toPeriod(musterRoll.getEndDate())
                        .paymentStatus("PENDING")
                        .status("ACTIVE")
                        .billDetails(billDetails)
                        .additionalDetails(new Object())
                        .build();

                populateBillAdditionalDetails(bill, CONTRACT_ID_CONSTANT, contractId);
                bills.add(bill);
            }

            log.info("Bills created for provided musterRolls : "+musterRollNumbers);
            return bills;
    }

    private void populateBillAdditionalDetails(Bill bill, String key , String value) {
        Object additionalDetails = bill.getAdditionalDetails();
        try {
            JsonNode node = mapper.readTree(mapper.writeValueAsString(additionalDetails));
            ((ObjectNode)node).put(key,value);
            bill.setAdditionalDetails(mapper.readValue(node.toString(), Object.class));
        }
        catch (Exception e){
            log.error("Error while parsing additionalDetails object.");
            throw new CustomException("PARSE_ERROR","Error while parsing additionalDetails object.");
        }
    }

    private String getContractId(MusterRoll musterRoll) {
        final Object additionalDetails = musterRoll.getAdditionalDetails();
        final Optional<String> contractId = commonUtil.findValue(additionalDetails, CONTRACT_ID_CONSTANT);
        if(contractId.isPresent())
            return contractId.get();

        return null;
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

    private List<CalcEstimate> createEstimatesForMusterRolls(List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
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
}
