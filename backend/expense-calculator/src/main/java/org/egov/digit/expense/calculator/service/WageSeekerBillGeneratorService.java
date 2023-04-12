package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.util.MusterRollUtils;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_WAGE_SEEKERS_SKILLS;

@Slf4j
@Component
public class WageSeekerBillGeneratorService {

    @Autowired
    private MusterRollUtils musterRollUtils;

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
        List<CalcEstimate> calcEstimates = prepareEstimatesForMusterRolls(musterRolls,wageSeekerSkillCodeAmountMapping);

        // Create Calculation
        return prepareCalculation(calcEstimates,criteria.getTenantId());
    }

    private Calculation prepareCalculation(List<CalcEstimate> calcEstimates, String tenantId) {
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

    public List<CalcEstimate> prepareEstimatesForMusterRolls(List<MusterRoll> musterRolls, Map<String, Double> wageSeekerSkillCodeAmountMapping) {
        List<CalcEstimate> calcEstimates = new ArrayList<>();
        for(MusterRoll musterRoll : musterRolls){
            List<CalcDetail> calcDetails = new ArrayList<>();
            List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
            String tenantId = musterRoll.getTenantId();
            BigDecimal netPayableAmount = BigDecimal.ZERO;
            for(IndividualEntry individualEntry : individualEntries){
                 String individualId = individualEntry.getIndividualId();

                 BigDecimal totalAttendance = null;

                 if(individualEntry.getModifiedTotalAttendance() == null){
                     totalAttendance = individualEntry.getActualTotalAttendance();
                 } else {
                     totalAttendance = individualEntry.getModifiedTotalAttendance();
                 }


                 Object additionalDetails = individualEntry.getAdditionalDetails();
                 Optional<String> skillCodeOptional = commonUtil.findValue(additionalDetails, "skillCode");
                 if(!skillCodeOptional.isPresent()){
                     log.error("SKILL_CODE_MISSING_FOR_INDIVIDUAL","Skill code is missing for individual ["+individualId+"]");
                     throw new CustomException("SKILL_CODE_MISSING_FOR_INDIVIDUAL","Skill code is missing for individual ["+individualId+"]");
                 }
                 Double skillAmount = wageSeekerSkillCodeAmountMapping.get(skillCodeOptional.get());
                 BigDecimal actualAmountToPay = totalAttendance.multiply(BigDecimal.valueOf(skillAmount));
                 netPayableAmount = netPayableAmount.add(actualAmountToPay);

                 LineItem lineItem = LineItem.builder()
                                             .amount(actualAmountToPay)
                                             .headCode(configs.getWageHeadCode())
                                             .tenantId(tenantId)
                                             .build();

                 Party payee = Party.builder()
                                    .identifier(individualId)
                                    .type(configs.getWagePayeeType())
                                    .build();

                 CalcDetail calcDetail = CalcDetail.builder()
                        .payee(payee)
                        .lineItems(Collections.singletonList(lineItem))
                        .payableLineItem(Collections.singletonList(lineItem))
                        .fromPeriod(musterRoll.getStartDate())
                        .toPeriod(musterRoll.getEndDate())
                        .referenceId(individualId).build();
                calcDetails.add(calcDetail);

            }
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
        return musterRollUtils.fetchMusterRollByIds(requestInfo,tenantId,musterRollIds);
    }
}
