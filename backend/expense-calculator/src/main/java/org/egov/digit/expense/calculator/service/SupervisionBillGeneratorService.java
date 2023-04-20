package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ExpenseCalculatorRepository;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillDetail;
import org.egov.digit.expense.calculator.web.models.CalcDetail;
import org.egov.digit.expense.calculator.web.models.CalcEstimate;
import org.egov.digit.expense.calculator.web.models.Calculation;
import org.egov.digit.expense.calculator.web.models.Contract;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.digit.expense.calculator.web.models.LineItem;
import org.egov.digit.expense.calculator.web.models.Party;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.BILL_TYPE_WAGE;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.BUSINESS_SERVICE_PURCHASE;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.BUSINESS_SERVICE_SUPERVISION;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.CBO_IMPLEMENTATION_AGENCY;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.CBO_IMPLEMENTATION_PARTNER;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.HEAD_CODE_SUPERVISION;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.PAYEE_TYPE_SUPERVISIONBILL;

@Slf4j
@Component
public class SupervisionBillGeneratorService {

    @Autowired
    private ExpenseCalculatorRepository expenseCalculatorRepository;

    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    /**
     * Calculates estimate for supervision bill
     * @param requestInfo
     * @param criteria
     * @return
     */
    public Calculation calculateEstimate(RequestInfo requestInfo, Criteria criteria) {

        //If the bill is empty or null, return empty response
        List<Bill> bills = fetchBills(requestInfo, criteria.getTenantId(), criteria.getContractId());
        if (CollectionUtils.isEmpty(bills)) {
            log.info("SupervisionBillGeneratorService::calculateEstimate::Wage bill and purchase bill not created. " +
                    " So Supervision bill cannot be calculated. Returning empty response");
            return new Calculation();
        }

        // fetch the musterRolls of the contract
        List<String> contractMusterRollIds = expenseCalculatorUtil.fetchMusterByContractId(requestInfo, criteria.getTenantId() , criteria.getContractId());

        //Check if the supervision bill is already created for all musterRollIds of the contract
        boolean supervisionbillExists = checkSupervisionBillExists(bills, criteria.getContractId(), contractMusterRollIds);
        if (supervisionbillExists) {
            log.error("SupervisionBillGeneratorService::calculateEstimate::Supervision bill already exists for all the musters of the contract - "+criteria.getContractId());
            throw new CustomException("DUPLICATE_SUPERVISIONBILL","Supervision bill already exists for all the musters of the contract - "+criteria.getContractId());
        }

        //calculate supervision charge
        return calculateSupervisionCharge(bills, requestInfo, criteria.getTenantId(), criteria.getContractId());

    }


    /**
     *  Fetches the musterRolls for which wage bill is calculated
     * @param contractId
     * @return
     */
    private List<String> fetchWagebillMusters(String contractId, String billType, List<String> billIds) {
        List<String> musterRollIds = expenseCalculatorRepository.getMusterRoll(contractId, billType, billIds);
        if (CollectionUtils.isEmpty(musterRollIds)) {
            log.error("SupervisionBillGeneratorService::fetchWagebillMusters::Wage bill is not calculated for the contract id - "+contractId);
            throw new CustomException("NO_WAGE_BILL","Wage bill is not calculated for the contract id - "+contractId);
        }
        return musterRollIds;
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

    /**
     * Checks if the supervision bill already created for all the musterrolls ids
     * @param bills
     *
     * @param contractId
     * @param contractMusterRollIds
     * @return
     */
    private boolean checkSupervisionBillExists (List<Bill> bills, String contractId, List<String> contractMusterRollIds) {

        List<String> billIds = new ArrayList<>();

        /* Fetch the musterrollIds from supervisionBill to check if the supervision bill is already created for the musterrollIds
           for which wage bill is calculated */
        for (Bill bill : bills) {
            if (StringUtils.isNotBlank(bill.getBusinessService()) && BUSINESS_SERVICE_SUPERVISION.equalsIgnoreCase(bill.getBusinessService())
                   && StringUtils.isNotBlank(bill.getReferenceId()) && contractId.equalsIgnoreCase(bill.getReferenceId())) {
                List<BillDetail> billDetailList = bill.getBillDetails();
                List<String> ids = billDetailList.stream().map(billDetail -> billDetail.getId())
                                                 .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ids)) {
                    billIds.addAll(ids);
                }
            }
        }

        //If billIds is empty , supervision bill is not created for any musterrollId return false
        if (CollectionUtils.isEmpty(billIds)) {
            return false;
        }

        //Fetch the musterrollIds of the corresponding billIds from calculator DB
        List<String> wagebillMusterIds = fetchWagebillMusters(contractId, BILL_TYPE_WAGE, billIds);

        for (String contractMusterRollId : contractMusterRollIds) {
            if (!wagebillMusterIds.contains(contractMusterRollId)) {
                return  false;
            }

        }
        return true;
    }

    /** Calculates the supervision charge
     * @param bills
     * @return
     */
    private Calculation calculateSupervisionCharge (List<Bill> bills, RequestInfo requestInfo, String tenantId, String contractId) {

        //Search by contractId.
        Contract contract = expenseCalculatorUtil.fetchContract(requestInfo, tenantId, contractId).get(0);
        String orgId = contract.getOrgId();
        String executingAuthority = contract.getExecutingAuthority();

        //payee for supervision bill
        Party payee = buildPayee(orgId,PAYEE_TYPE_SUPERVISIONBILL);

        //calculate supervision charge
        BigDecimal supervisionRate = new BigDecimal(7.5); //TODO fetch from MDMS
        BigDecimal totalWageBillAmount = calculateTotalBillAmount(bills,configs.getWageBusinessService());
        BigDecimal supervisionCharge = null;

        if (StringUtils.isNotBlank(executingAuthority) && CBO_IMPLEMENTATION_AGENCY.equalsIgnoreCase(executingAuthority)) {
           BigDecimal totalPurchaseBillAmount = calculateTotalBillAmount(bills, BUSINESS_SERVICE_PURCHASE);
            supervisionCharge = (totalWageBillAmount.add(totalPurchaseBillAmount)).multiply(supervisionRate).divide(new BigDecimal(100));
        } else if (StringUtils.isNotBlank(executingAuthority) && CBO_IMPLEMENTATION_PARTNER.equalsIgnoreCase(executingAuthority)) {
            supervisionCharge = totalWageBillAmount.multiply(supervisionRate).divide(new BigDecimal(100));
        }

        // Build lineItem
        LineItem lineItem = buildLineItem(tenantId,supervisionCharge);

        // Build CalcDetails
        List<CalcDetail> calcDetails = new ArrayList<>();
        CalcDetail calcDetail = CalcDetail.builder()
                .payee(payee)
                .lineItems(Collections.singletonList(lineItem))
                .payableLineItem(Collections.singletonList(lineItem))
                .fromPeriod(contract.getStartDate())
                .toPeriod(contract.getEndDate())
                .referenceId(contractId).build();
        calcDetails.add(calcDetail);

        // Build CalcEstimates
        List<CalcEstimate> calcEstimates = new ArrayList<>();
        CalcEstimate calcEstimate = CalcEstimate.builder()
                .referenceId(contractId)
                .fromPeriod(contract.getStartDate())
                .toPeriod(contract.getEndDate())
                .netPayableAmount(supervisionCharge)
                .tenantId(tenantId)
                .calcDetails(calcDetails)
                .businessService(BUSINESS_SERVICE_SUPERVISION)
                .build();

        calcEstimates.add(calcEstimate);

        // Build Calculation
        Calculation calculation = Calculation.builder()
                .tenantId(tenantId)
                .estimates(calcEstimates)
                .totalAmount(supervisionCharge)
                .build();

        return calculation;
    }

    /**
     * Calculates the total bill amount
     * @param bills
     * @param businessService
     * @return
     */
    private BigDecimal calculateTotalBillAmount (List<Bill> bills, String businessService) {
        BigDecimal totalBillAmount = BigDecimal.ZERO;
        List<BigDecimal> billAmountList = bills.stream()
                .filter(bill -> bill.getBusinessService().equalsIgnoreCase(businessService))
                .map(bill -> bill.getNetPayableAmount())
                .collect(Collectors.toList());

        for (BigDecimal billAmount : billAmountList) {
            totalBillAmount = totalBillAmount.add(billAmount);
        }
        return totalBillAmount;
    }

    private Party buildPayee(String orgId, String type) {
        return Party.builder()
                .identifier(orgId)
                .type(type)
                .build();
    }

    private LineItem buildLineItem(String tenantId, BigDecimal actualAmountToPay) {
        return LineItem.builder()
                .amount(actualAmountToPay)
                .headCode(HEAD_CODE_SUPERVISION)
                .tenantId(tenantId)
                .build();
    }

}
