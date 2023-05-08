package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.*;
import org.egov.digit.expense.calculator.repository.IdGenRepository;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Slf4j
@Component
public class SupervisionBillGeneratorService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ExpenseCalculatorRepository expenseCalculatorRepository;

    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private ExpenseCalculatorConfiguration config;

    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * Calculates estimate for supervision bill
     * @param requestInfo
     * @param criteria
     * @param bills
     * @return
     */
    public Calculation calculateEstimate(RequestInfo requestInfo, Criteria criteria, List<Bill> bills) {

        //If the bill is empty or null, return empty response
        if (CollectionUtils.isEmpty(bills)) {
            log.info("SupervisionBillGeneratorService::calculateEstimate::Wage bill and purchase bill not created. " +
                    " So Supervision bill cannot be calculated.");
            throw new CustomException("NO_WAGE_PURCHASE_BILL","Wage and purchase bill is not created. So Supervision bill cannot be calculated.");
        }

        // fetch the musterRolls of the contract
        List<String> contractMusterRollIds = expenseCalculatorUtil.fetchMusterByContractId(requestInfo, criteria.getTenantId() , criteria.getContractId());

        //Check if the supervision bill is already created for all musterRollIds of the contract
        List<String> filteredMusters = new ArrayList<>();
        boolean supervisionbillExists = checkSupervisionBillExists(bills, criteria.getContractId(), criteria.getTenantId(), contractMusterRollIds, filteredMusters);
        if (supervisionbillExists) {
            log.error("SupervisionBillGeneratorService::calculateEstimate::Supervision bill already exists for all the musters of the contract - "+criteria.getContractId());
            throw new CustomException("DUPLICATE_SUPERVISIONBILL","Supervision bill already exists for all the musters of the contract - "+criteria.getContractId());
        }

        //fetch purchase bill(s) and wage bill(s) for which supervision bill need to be created
        List<Bill> filteredBills = filterBills(bills,filteredMusters);

        //calculate supervision charge
        return calculateSupervisionCharge(filteredBills, requestInfo, criteria.getTenantId(), criteria.getContractId());

    }

    /**
     * Create supervision bill - invoke bill create api
     * @param requestInfo
     * @param calculation
     * @param expenseBills
     * @return
     */
    public List<Bill> createSupervisionBill (RequestInfo requestInfo, Criteria criteria,Calculation calculation, List<Bill> expenseBills) {

        List<Bill> bills = new ArrayList<>();
        if (null != calculation ) {
        	
            CalcEstimate calcEstimate = calculation.getEstimates().get(0);
            CalcDetail calcDetail = calcEstimate.getCalcDetails().get(0);
            //Transfer calculation details onto bill details
            List<BillDetail> billDetails = new ArrayList<>();
            for(CalcDetail calc: calcEstimate.getCalcDetails()) {
				BillDetail billDetail = null;
				// Build BillDetail
				billDetail = BillDetail.builder().billId(null).referenceId(calc.getBillId()) // wage billId or
						.tenantId(criteria.getTenantId())
						.payee(calcDetail.getPayee())
						.lineItems(calcDetail.getLineItems())
						.payableLineItems(calcDetail.getPayableLineItem()).build();
				billDetails.add(billDetail);

            }

           // Party payer = buildPayee(config.getWagePayerId(),config.getWagePayerType(),criteria.getTenantId());
            
            Party payer = this.buildParty(requestInfo, config.getPayerType(), criteria.getTenantId());

            //Supervision - Idgen
            String rootTenantId = criteria.getTenantId().split("\\.")[0];
            String supervisionBillNumber;
            List<String> supervisionBillNumbers = getIdList(requestInfo, rootTenantId
                    , config.getIdGenSupervisionBillFormat(), "", 1); //idformat will be fetched by idGen service
            if (supervisionBillNumbers != null && !supervisionBillNumbers.isEmpty()) {
                supervisionBillNumber = supervisionBillNumbers.get(0);
            } else {
                throw new CustomException("SUPERVISION_BILL_NUMBER_NOT_GENERATED","Error occurred while generating supervision bill number from IdGen service");
            }

            // Build Bill
            Bill bill = Bill.builder()
                    .tenantId(criteria.getTenantId())
                    .billDate(Instant.now().toEpochMilli())
                    .referenceId(criteria.getContractId() +"_"+supervisionBillNumber)
                    .businessService(config.getSupervisionBusinessService())
                    .payer(payer)
                    .billDetails(billDetails)
                    .build();

            bills.add(bill);
            log.info("Bill created:" + bill.toString());
        }

        return bills;
    }


    /**
     *  Fetches the musterRolls for which wage bill is calculated
     * @param contractId
     * @return
     */
    private List<String> fetchWagebillMusters(String contractId, String billType, String tenantId, List<String> billIds) {
        List<String> musterRollIds = expenseCalculatorRepository.getMusterRoll(contractId, billType, tenantId, billIds);
        if (CollectionUtils.isEmpty(musterRollIds)) {
            log.error("SupervisionBillGeneratorService::fetchWagebillMusters::Wage bill is not calculated for the contract id - "+contractId);
            throw new CustomException("NO_WAGE_BILL","Wage bill is not calculated for the contract id - "+contractId);
        }
        return musterRollIds;
    }

    /**
     * Checks if the supervision bill already created for all the musterrolls ids
     * @param bills
     *
     * @param contractId
     * @param contractMusterRollIds
     * @param filteredMusters
     * @return
     */
    private boolean checkSupervisionBillExists (List<Bill> bills, String contractId, String tenantId, List<String> contractMusterRollIds, List<String> filteredMusters)  {

        List<String> billIds = new ArrayList<>();

        /* Fetch the musterrollIds from supervisionBill to check if the supervision bill is already created for the musterrollIds
           for which wage bill is calculated */
        for (Bill bill : bills) {
            if (StringUtils.isNotBlank(bill.getBusinessService()) && config.getSupervisionBusinessService().equalsIgnoreCase(bill.getBusinessService())) {
                List<BillDetail> billDetailList = bill.getBillDetails();
                List<String> ids = billDetailList.stream().map(billDetail -> billDetail.getReferenceId())
                                                 .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ids)) {
                    billIds.addAll(ids); //wage billId and purchase billId of the existing supervision bill
                }
            }
        }

        //If billIds is empty , supervision bill is not created for any musterrollId return false
        if (CollectionUtils.isEmpty(billIds)) {
            return false;
        }

        //Fetch the musterrollIds of the corresponding billIds from calculator DB
        List<String> wagebillMusterIds = fetchWagebillMusters(contractId, config.getWageBusinessService(), tenantId, billIds);

        for (String contractMusterRollId : contractMusterRollIds) {
            if (!wagebillMusterIds.contains(contractMusterRollId)) {
                filteredMusters.add(contractMusterRollId);
            }
        }

        if (!CollectionUtils.isEmpty(filteredMusters)) { // there are musters in wage bill for which supervision bill need to be created
            return false;
        }

        return true;
    }

    /**
     * Fetch the wageBill(s) and purchaseBill(s) for which supervision bill need to be created
     * @param bills
     * @param filteredMusters
     * @return
     */
    private List<Bill> filterBills(List<Bill> bills, List<String> filteredMusters) {
       List<Bill> filteredBills = new ArrayList<>();

       //fetch the wage bill(s)
       for (Bill bill : bills) {
          if (StringUtils.isNotBlank(bill.getBusinessService()) && bill.getBusinessService().equalsIgnoreCase(config.getWageBusinessService())) {
              if (!CollectionUtils.isEmpty(filteredMusters)) {
                  String musterNumBill = bill.getReferenceId().split("\\_")[1];
                  if (filteredMusters.contains(musterNumBill)) {
                      filteredBills.add(bill);
                  }
              } else {
                  filteredBills.add(bill);
              }

          }
       }

       //fetch the purchase bill(s)
        List<String> billIds = new ArrayList<>();
        for (Bill bill : bills) {
            if (StringUtils.isNotBlank(bill.getBusinessService()) && config.getSupervisionBusinessService().equalsIgnoreCase(bill.getBusinessService())) {
                List<BillDetail> billDetailList = bill.getBillDetails();
                List<String> ids = billDetailList.stream().map(billDetail -> billDetail.getReferenceId())
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ids)) {
                    billIds.addAll(ids); //wage billId and purchase billId of the existing supervision bill
                }
            }
        }
        for (Bill bill : bills) {
           if (StringUtils.isNotBlank(bill.getBusinessService()) && bill.getBusinessService().equalsIgnoreCase(config.getPurchaseBusinessService())
                 && !billIds.contains(bill.getId())) {
               filteredBills.add(bill);
           }
        }

       return  filteredBills;
    }

    /** Calculates the supervision charge. Used by both _estimate and _calculate APIs.
     * @param bills
     * @return
     */
    private Calculation calculateSupervisionCharge (List<Bill> bills, RequestInfo requestInfo, String tenantId, String contractId) {

        //Search by contractId.
        Contract contract = expenseCalculatorUtil.fetchContract(requestInfo, tenantId, contractId).get(0);
        String orgId = contract.getOrgId();

        //payee for supervision bill
        Party payee = buildParty(orgId,PAYEE_TYPE_SUPERVISIONBILL,tenantId);

        //calculate supervision charge
        BigDecimal supervisionRate = new BigDecimal(7.5); //TODO fetch from MDMS

    
//        if (StringUtils.isNotBlank(executingAuthority) && CBO_IMPLEMENTATION_AGENCY.equalsIgnoreCase(executingAuthority)) {
//            BigDecimal totalPurchaseBillAmount = calculateTotalBillAmount(bills, config.getPurchaseBusinessService());
//             supervisionCharge = (totalWageBillAmount.add(totalPurchaseBillAmount)).multiply(supervisionRate).divide(new BigDecimal(100));
//         } else if (StringUtils.isNotBlank(executingAuthority) && CBO_IMPLEMENTATION_PARTNER.equalsIgnoreCase(executingAuthority)) {
//             supervisionCharge = totalWageBillAmount.multiply(supervisionRate).divide(new BigDecimal(100));
//         }
        List<CalcDetail> calcDetails = new ArrayList<>();

        //One lineItem per bill and one calcDetail per Bill for supervision bills
        for(Bill bill: bills)
        {
        	
        	if(shouldIncludeBill(contract,bill))
        	{	
        		// Build lineItem. Single line item per bill detail
        		LineItem lineItem = buildLineItem(tenantId,bill, supervisionRate);

        		// Build CalcDetails. This will be one per bill
        		CalcDetail calcDetail = CalcDetail.builder()
        				.payee(payee)
        				.lineItems(Collections.singletonList(lineItem))
        				.payableLineItem(Collections.singletonList(lineItem))
        				.fromPeriod(new BigDecimal(bill.getBillDate()))
        				.toPeriod(new BigDecimal(bill.getBillDate()))
        				.referenceId(bill.getId()).build();
        		calcDetails.add(calcDetail);
        	}

        }
     
        // Build CalcEstimates. Equivalent to the outer Bill object.
        List<CalcEstimate> calcEstimates = new ArrayList<>();
        CalcEstimate calcEstimate = CalcEstimate.builder()
                .referenceId(contractId)
                .fromPeriod(contract.getStartDate())
                .toPeriod(contract.getEndDate())
                .tenantId(tenantId)
                .calcDetails(calcDetails)
                .businessService(config.getSupervisionBusinessService())
                .build();

        calcEstimates.add(calcEstimate);

        // Build Calculation
        Calculation calculation = Calculation.builder()
                .tenantId(tenantId)
                .estimates(calcEstimates)
                .build();

        return calculation;
    }
    
    /**
     * This method figures out whether a bill should be included for supervision
     * bill calculation in a contract.If IA, then include purchase & wage bills. If IP,
     * then include only wage bills.
     * @param contract
     * @param bill
     * @return
     */
	private boolean shouldIncludeBill(Contract contract, Bill bill) {
		String executingAuthority = contract.getExecutingAuthority();
		boolean isIncluded = false;
		// Check whether this bill should be included in calculation or not
		if (StringUtils.isNotBlank(executingAuthority)
				&& CBO_IMPLEMENTATION_AGENCY.equalsIgnoreCase(executingAuthority)) {
			// If bill is of type purchase or wage, then incldue this.
			if (bill.getBusinessService().equals(config.getPurchaseBusinessService())
					|| bill.getBusinessService().equals(config.getWageBusinessService()))
				isIncluded = true;
		} else if (StringUtils.isNotBlank(executingAuthority)
				&& CBO_IMPLEMENTATION_PARTNER.equalsIgnoreCase(executingAuthority)
				&& bill.getBusinessService() == config.getWageBusinessService()) {
			 isIncluded = true;
		}
		return true;
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
                .map(bill -> bill.getTotalAmount())
                .collect(Collectors.toList());

        for (BigDecimal billAmount : billAmountList) {
            totalBillAmount = totalBillAmount.add(billAmount);
        }
        return totalBillAmount;
    }

    private Party buildParty(String orgId, String type, String tenantId) {
        return Party.builder()
                .identifier(orgId)
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

    private LineItem buildLineItem(String tenantId, Bill bill, BigDecimal supervisionRate) {
       BigDecimal billAmount = bill.getTotalAmount();
       BigDecimal supervisionAmt = billAmount.multiply(supervisionRate).divide(new BigDecimal(100));
       LineItem lineItem =  LineItem.builder()
                .amount(supervisionAmt)
                .headCode(HEAD_CODE_SUPERVISION) // TODO fetch from mdms
                .tenantId(tenantId)
                .type(LineItem.TypeEnum.PAYABLE)
                .build();
        return lineItem;
    }

    /**
     * Returns a list of numbers generated from idgen
     *
     * @param requestInfo RequestInfo from the request
     * @param tenantId    tenantId of the city
     * @param idKey       code of the field defined in application properties for which ids are generated for
     * @param idformat    format in which ids are to be generated
     * @param count       Number of ids to be generated
     * @return List of ids generated using idGen service
     */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }

}
