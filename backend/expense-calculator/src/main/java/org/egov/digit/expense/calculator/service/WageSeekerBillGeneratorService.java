package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

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

    public Calculation calculateEstimates(RequestInfo requestInfo , String tenantId, List<MusterRoll> musterRolls, List<LabourCharge> labourCharges) {
        // Calculate estimate for each muster roll
        List<CalcEstimate> calcEstimates = createEstimatesForMusterRolls(requestInfo, musterRolls,labourCharges);
        // Create Calculation
        return makeCalculation(calcEstimates,tenantId);
    }
    public List<Bill> createWageSeekerBills(RequestInfo requestInfo, List<MusterRoll> musterRolls, List<LabourCharge> labourCharges, Map<String, String> metaInfo){
        // Create bills for muster rolls
        return createBillForMusterRolls(requestInfo, musterRolls, labourCharges,metaInfo);
    }


    private List<Bill> createBillForMusterRolls(RequestInfo requestInfo, List<MusterRoll> musterRolls, List<LabourCharge> labourCharges,Map<String, String> metaInfo) {
            List<Bill> bills = new ArrayList<>();
            List<String> musterRollNumbers = new ArrayList<>();

            // For each muster-roll create one wage bill
            for(MusterRoll musterRoll : musterRolls){
                musterRollNumbers.add(musterRoll.getMusterRollNumber());
                List<BillDetail> billDetails = new ArrayList<>();


                String tenantId = musterRoll.getTenantId();
                BigDecimal netPayableAmount = BigDecimal.ZERO;
                // Muster roll reference id is contractNumber
                String referenceId = musterRoll.getReferenceId();
                if(referenceId == null) {
                    log.error("MUSTER_ROLL_REFERENCE_ID_MISSING", "Reference Id is missing for muster roll ["+musterRoll.getMusterRollNumber()+"]");
                    throw new CustomException("MUSTER_ROLL_REFERENCE_ID_MISSING", "Reference Id is missing for muster roll ["+musterRoll.getMusterRollNumber()+"]");
                }
                // Get orgId for contractNumber
                String cboId = getCBOID(requestInfo,tenantId,referenceId);
                // Put orgId into meta
                metaInfo.put(ORG_ID_CONSTANT,cboId);
                List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();

                for(IndividualEntry individualEntry : individualEntries){
                    String individualId = individualEntry.getIndividualId();
                    // Calculate net amount to pay to wage seeker
                    Double skillAmount = getWageSeekerSkillAmount(individualEntry,labourCharges);
                    BigDecimal actualAmountToPay = calculateAmount(individualEntry, BigDecimal.valueOf(skillAmount));
                    // Calculate net payable amount
                    netPayableAmount = netPayableAmount.add(actualAmountToPay);
                    // Build lineItem
                    LineItem lineItem = buildLineItem(tenantId,actualAmountToPay);
                    // Build payee
                    Party payee = buildParty(individualId,configs.getWagePayeeType(),tenantId);
                    metaInfo.put(individualId,String.valueOf(getWageSeekerSkillCodeId(individualEntry,labourCharges)));
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
                Party payer = buildParty(requestInfo, configs.getPayerType(), tenantId);

                // Build Bill
                Bill bill = Bill.builder()
                        .tenantId(tenantId)
                        .billDate(Instant.now().toEpochMilli())
                      //  .netPayableAmount(netPayableAmount)
                        .referenceId(referenceId +CONCAT_CHAR_CONSTANT+musterRoll.getMusterRollNumber())
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

    private List<CalcEstimate> createEstimatesForMusterRolls(RequestInfo requestInfo,  List<MusterRoll> musterRolls, List<LabourCharge> labourCharges) {
        List<CalcEstimate> calcEstimates = new ArrayList<>();
        for(MusterRoll musterRoll : musterRolls){
            List<CalcDetail> calcDetails = new ArrayList<>();
            List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
            String tenantId = musterRoll.getTenantId();
            BigDecimal netPayableAmount = BigDecimal.ZERO;
            if(musterRoll.getReferenceId() == null) {
                log.error("MUSTER_ROLL_REFERENCE_ID_MISSING", "Reference Id is missing for muster roll ["+musterRoll.getMusterRollNumber()+"]");
                throw new CustomException("MUSTER_ROLL_REFERENCE_ID_MISSING", "Reference Id is missing for muster roll ["+musterRoll.getMusterRollNumber()+"]");
            }

            String cboId = getCBOID(requestInfo, tenantId, musterRoll.getReferenceId());
            for(IndividualEntry individualEntry : individualEntries){
                String individualId = individualEntry.getIndividualId();
                // Calculate net amount to pay to wage seeker
                Double skillAmount = getWageSeekerSkillAmount(individualEntry,labourCharges);
                BigDecimal actualAmountToPay = calculateAmount(individualEntry, BigDecimal.valueOf(skillAmount));
                // Calculate net payable amount
                netPayableAmount = netPayableAmount.add(actualAmountToPay);
                // Build lineItem
                LineItem lineItem = buildLineItem(tenantId,actualAmountToPay);
                // Build payee
                Party payee = buildParty(individualId,configs.getWagePayeeType(),tenantId);

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
                    .referenceId(musterRoll.getReferenceId() + CONCAT_CHAR_CONSTANT+ musterRoll.getMusterRollNumber())
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
                .paidAmount(BigDecimal.ZERO)
                .headCode(configs.getWageHeadCode())
                .type(LineItem.TypeEnum.PAYABLE)
                .tenantId(tenantId)
                .build();
    }

    private Party buildParty(String individualId, String type, String tenantId) {
       return Party.builder()
                .identifier(individualId)
                .type(type)
                .tenantId(tenantId)
                .status("ACTIVE")
                .build();
    }

    private Party buildParty(RequestInfo requestInfo, String type, String tenantId) {
        String rootTenantId = tenantId.split("\\.")[0];
        Object mdmsResp = mdmsUtils.getPayersForTypeFromMDMS(requestInfo, type, rootTenantId);
        List<Object> payerList = commonUtil.readJSONPathValue(mdmsResp,JSON_PATH_FOR_PAYER);
        for(Object obj : payerList){
            Payer payer = mapper.convertValue(obj, Payer.class);
            if(tenantId.equals(payer.getTenantId())) {
                return buildParty(payer.getId(),payer.getCode(),tenantId);
            }
        }
        log.error("PAYER_MISSING_IN_MDMS","Payer is missing in MDMS for type : "+type + " and tenantId : "+tenantId);
        throw new CustomException("PAYER_MISSING_IN_MDMS","Payer is missing in MDMS for type : "+type + " and tenantId : "+tenantId);
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

    private Double getWageSeekerSkillAmount(IndividualEntry individualEntry, List<LabourCharge> labourCharges) {

   //     return new Double(150);
        String skill =  getWageSeekerSkill(individualEntry);
        String wageLabourChargeUnit = configs.getWageLabourChargeUnit();
        for(LabourCharge labourCharge : labourCharges){
            if(labourCharge.getCode().equalsIgnoreCase(skill)
                    && wageLabourChargeUnit.equalsIgnoreCase(labourCharge.getUnit())) {
                return labourCharge.getAmount();
            }
        }

        log.error("SKILL_CODE_MISSING_IN_MDMS","Skill code "+ skill+" is missing in MDMS");
        throw new CustomException("SKILL_CODE_MISSING_IN_MDMS","Skill code "+ skill+" is missing in MDMS");
    }

    private Integer getWageSeekerSkillCodeId(IndividualEntry individualEntry, List<LabourCharge> labourCharges) {
        String skill =  getWageSeekerSkill(individualEntry);
        String wageLabourChargeUnit = configs.getWageLabourChargeUnit();
        for(LabourCharge labourCharge : labourCharges){
            if(labourCharge.getCode().equalsIgnoreCase(skill)
                    && wageLabourChargeUnit.equalsIgnoreCase(labourCharge.getUnit())) {
                return labourCharge.getId();
            }
        }

        return null;
    }

    private String getWageSeekerSkill(IndividualEntry individualEntry) {
        String individualId = individualEntry.getIndividualId();
        Object additionalDetails = individualEntry.getAdditionalDetails();
        Optional<String> skillCodeOptional = commonUtil.findValue(additionalDetails, SKILL_CODE_CONSTANT);
        if(!skillCodeOptional.isPresent()){
            log.error("SKILL_CODE_MISSING_FOR_INDIVIDUAL","Skill code is missing for individual ["+individualId+"]");
            throw new CustomException("SKILL_CODE_MISSING_FOR_INDIVIDUAL","Skill code is missing for individual ["+individualId+"]");
        }
        return skillCodeOptional.get();
    }
}
