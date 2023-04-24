package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.ContractUtils;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Slf4j
@Component
public class WageSeekerBillGeneratorService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;

    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ContractUtils contractUtils;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public Calculation calculateEstimates(RequestInfo requestInfo , String tenantId, List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
        // Calculate estimate for each muster roll
        List<CalcEstimate> calcEstimates = createEstimatesForMusterRolls(requestInfo, musterRolls,wageSeekerSkillCodeAmountMapping);
        // Create Calculation
        return makeCalculation(calcEstimates,tenantId);
    }
    public List<Bill> createWageSeekerBills(RequestInfo requestInfo, List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping){
        // Create bills for muster rolls
        return createBillForMusterRolls(requestInfo, musterRolls, wageSeekerSkillCodeAmountMapping);
    }


    private List<Bill> createBillForMusterRolls(RequestInfo requestInfo, List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
            List<Bill> bills = new ArrayList<>();
            List<String> musterRollNumbers = new ArrayList<>();
            for(MusterRoll musterRoll : musterRolls){
                musterRollNumbers.add(musterRoll.getMusterRollNumber());
                List<BillDetail> billDetails = new ArrayList<>();


                String tenantId = musterRoll.getTenantId();
                BigDecimal netPayableAmount = BigDecimal.ZERO;
                String referenceId = musterRoll.getReferenceId();

                List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();

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
                    Party payee = buildPayee(individualId,configs.getWagePayeeType(),tenantId);

                    String cboId = getCBOID(requestInfo,tenantId,referenceId);
                    // Build BillDetail
                    BillDetail billDetail = BillDetail.builder()
                                                //.referenceId(individualId)
                                                .billId(null)
                                                .referenceId(cboId)
                                                .tenantId(tenantId)
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
                Party payer = buildPayee(configs.getWagePayerId(),configs.getWagePayerType(),tenantId);

                // Build Bill
                Bill bill = Bill.builder()
                        .tenantId(tenantId)
                        .billDate(Instant.now().toEpochMilli())
                        .netPayableAmount(netPayableAmount)
                        .referenceId(referenceId +"_"+musterRoll.getMusterRollNumber())
                        .businessService(configs.getWageBusinessService())
                        .fromPeriod(musterRoll.getStartDate().longValue())
                        .toPeriod(musterRoll.getEndDate().longValue())
                        .payer(payer)
                        .paymentStatus("PENDING")
                        .status("ACTIVE")
                        .billDetails(billDetails)
                        //.additionalDetails(new Object())
                        .build();

                bills.add(bill);
            }

            log.info("Bills created for provided musterRolls : "+musterRollNumbers);
            return bills;
    }

    private String getCBOID(RequestInfo requestInfo, String tenantId, String referenceId) {
        ContractResponse contractResponse = contractUtils.fetchContract(requestInfo, tenantId, referenceId);
        Contract contract = contractResponse.getContracts().get(0);
        return contract.getOrgId();
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
//        final Object additionalDetails = musterRoll.getAdditionalDetails();
//        final Optional<String> contractId = commonUtil.findValue(additionalDetails, CONTRACT_ID_CONSTANT);
//        if(contractId.isPresent())
//            return contractId.get();
//
//        return null;
       return musterRoll.getReferenceId();
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

    private List<CalcEstimate> createEstimatesForMusterRolls(RequestInfo requestInfo,  List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
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
                Party payee = buildPayee(individualId,configs.getWagePayeeType(),tenantId);

                String cboId = getCBOID(requestInfo, tenantId, musterRoll.getReferenceId());
                // Build CalcDetail
                CalcDetail calcDetail = CalcDetail.builder()
                        .payee(payee)
                        .lineItems(Collections.singletonList(lineItem))
                        .payableLineItem(Collections.singletonList(lineItem))
                        .fromPeriod(musterRoll.getStartDate())
                        .toPeriod(musterRoll.getEndDate())
                        .referenceId(cboId).build();
                calcDetails.add(calcDetail);

            }
            // Build CalcEstimate
            CalcEstimate calcEstimate = CalcEstimate.builder()
                    .referenceId(musterRoll.getReferenceId() + "-"+ musterRoll.getMusterRollNumber())
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

    private Party buildPayee(String individualId, String type,String tenantId) {
       return Party.builder()
                .identifier(individualId)
                .type(type)
                .tenantId(tenantId)
                .status("STATUS")
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
