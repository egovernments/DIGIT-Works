package org.egov.digit.expense.calculator.service;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.CBO_IMPLEMENTATION_AGENCY;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.CBO_IMPLEMENTATION_PARTNER;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.HEAD_CODE_SUPERVISION;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_PAYER;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.PAYEE_TYPE_SUPERVISIONBILL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.IdGenRepository;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillDetail;
import org.egov.digit.expense.calculator.web.models.CalcDetail;
import org.egov.digit.expense.calculator.web.models.CalcEstimate;
import org.egov.digit.expense.calculator.web.models.Calculation;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.digit.expense.calculator.web.models.LineItem;
import org.egov.digit.expense.calculator.web.models.Party;
import org.egov.digit.expense.calculator.web.models.Payer;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Slf4j
@Component
public class SupervisionBillGeneratorService {

	private final ObjectMapper mapper;


	private final ExpenseCalculatorUtil expenseCalculatorUtil;

	private final IdGenRepository idGenRepository;

	private final ExpenseCalculatorConfiguration config;

	private final MdmsUtils mdmsUtils;

	private final CommonUtil commonUtil;

	@Autowired
	public SupervisionBillGeneratorService(ObjectMapper mapper, ExpenseCalculatorUtil expenseCalculatorUtil, IdGenRepository idGenRepository, ExpenseCalculatorConfiguration config, MdmsUtils mdmsUtils, CommonUtil commonUtil) {
		this.mapper = mapper;
		this.expenseCalculatorUtil = expenseCalculatorUtil;
		this.idGenRepository = idGenRepository;
		this.config = config;
		this.mdmsUtils = mdmsUtils;
		this.commonUtil = commonUtil;
	}


	private Map<String, Bill> createMap(List<Bill> bills) {
		Map<String, Bill> map = new HashMap<>();
		for (Bill b : bills) {
			map.put(b.getBillNumber(),b);
		}
		return map;
	}

	/**
	 * Calculates estimate for supervision bill
	 * 
	 * @param requestInfo
	 * @param criteria
	 * @param bills
	 * @return
	 */
	public Calculation estimateBill(RequestInfo requestInfo, Criteria criteria, List<Bill> bills) {

		// If the bill is empty or null, return empty response
		if (bills == null || CollectionUtils.isEmpty(bills)) {
			log.info("SupervisionBillGeneratorService::calculateEstimate::Wage bill and purchase bill not created. "
					+ " So Supervision bill cannot be calculated.");
			throw new CustomException("NO_WAGE_PURCHASE_BILL",
					String.format("No wage or purchase bills are found for this contract %s and tenant %s. So Supervision bill cannot be calculated.", criteria.getContractId(), criteria.getTenantId()));
		}
		
	
        // Create a hashmap of bill ID to Bill
		Map<String, Bill> billMap = createMap(bills);

		// A set to hold all existing wage/purchase bill Ids included in supervision
		// bills
		Set<String> existingBills = new HashSet<>();

		Set<String> wageAndPurchaseBills = new HashSet<>();
		List<String> supervisionBills = new ArrayList<>();

		// TODO: Account for partially paid bills esp in Wage bills. Check bill status.
		// Loop through all bills. Separate out wage, purchase and supervision bills.
		// And stream the referenceids (bill ids)
		// in existing supervision bills.
		for (Bill bill : bills) {
			if (bill.getBusinessService().equals(config.getWageBusinessService())
					|| bill.getBusinessService().equals(config.getPurchaseBusinessService())) {
				wageAndPurchaseBills.add(bill.getBillNumber());
			} else {
				supervisionBills.add(bill.getId());
				// Fetch all referenceIds (existing bill Ids) and store in a list
				List<BillDetail> billDetailList = bill.getBillDetails();
				List<String> existing = billDetailList.stream().map(BillDetail::getReferenceId)
						.collect(Collectors.toList());
				if (!existing.isEmpty()) {
					existingBills.addAll(existing);
				}
			}

		}
		log.info("Printing all existing bills for which supervision bill is created");
		for(String s: existingBills) {
			log.info("Bill numbers: " + s);
		}
		
		// There are n purchase and wage bills for a contract. n-2 of them are already
		// accounted for in a supervision bill in
		// existingBills. The remainder are the bills for which a supervision bill needs
		// to be created. Find the diff by using set operations.
		wageAndPurchaseBills.removeAll(existingBills);


			// If the bill is empty or null, return empty response
			if (wageAndPurchaseBills == null || CollectionUtils.isEmpty(wageAndPurchaseBills)) {
				log.info("There are no wage and purchase bills for which supervision needs to be calculated.");
				throw new CustomException("NO_WAGE_PURCHASE_BILL",
					String.format("Supervision bills have been created for all existing wage and purchase bills for contract %s", criteria.getContractId()));

			}

		
		log.info(String.format("There are %s bills for contract %s for which a supervision bill needs to be raised", wageAndPurchaseBills.size(), criteria.getContractId()));
		for(String s: wageAndPurchaseBills) {
			log.info("Bill number: " + s);
		}
		
		// Create bills for what's remaining
		
		List<Bill> filteredBills = new ArrayList<>();
		for (String s : wageAndPurchaseBills) {
			filteredBills.add(billMap.get(s));
		}

		// calculate supervision charge
		return calculateSupervisionCharge(filteredBills, requestInfo, criteria.getTenantId(), criteria.getContractId());

	}

	/**
	 * Create supervision bill - invoke bill create api
	 * 
	 * @param requestInfo
	 * @param calculation
	 * @param expenseBills
	 * @return
	 */
	public List<Bill> createSupervisionBill(RequestInfo requestInfo, Criteria criteria, Calculation calculation) {
		log.info("Preparing supervision bill payload");
		List<Bill> bills = new ArrayList<>();
		if (null != calculation && calculation.getEstimates()!=null){

			CalcEstimate calcEstimate = calculation.getEstimates().get(0);
			// Transfer calculation details onto bill details
			List<BillDetail> billDetails = new ArrayList<>();
			for (CalcDetail calc : calcEstimate.getCalcDetails()) {
				// if payable line item of a bill is not greater than zero, do not generate supervision bill for that bill
				if (calc.getPayableLineItem().get(0).getAmount().compareTo(BigDecimal.ZERO) <= 0) {
					log.info("Supervision bill amount is not grater then ZERO, so supervision bill can not be generated for bill [" + calc.getReferenceId() + "]");
					continue;
				}

				BillDetail billDetail = null;
				// Build BillDetail
				billDetail = BillDetail.builder().billId(null).referenceId(calc.getReferenceId())// wage billId or

						.totalAmount(new BigDecimal(0))
						.totalPaidAmount(new BigDecimal(0))
						.netLineItemAmount(new BigDecimal(0))
						.tenantId(criteria.getTenantId()).payee(calc.getPayee())
						.lineItems(calc.getLineItems()).payableLineItems(calc.getPayableLineItem()).build();
				billDetails.add(billDetail);

			}
			if (billDetails.isEmpty()) {
				log.error("SUPERVISION_BILL_ZERO_AMOUNT",
						"Supervision bill can not be generated, because amount is not grater then ZERO for contract " + calcEstimate.getReferenceId());
				throw new CustomException("SUPERVISION_BILL_ZERO_AMOUNT",
						"Supervision bill can not be generated, because amount is not grater then ZERO for contract " + calcEstimate.getReferenceId());
			}

			Party payer = this.buildParty(requestInfo, config.getPayerType(), criteria.getTenantId());

			// Supervision - Idgen
			String rootTenantId = criteria.getTenantId().split("\\.")[0];
			String supervisionBillNumber;
			log.info("Generating ID for supervision bill referenceId");	
			List<String> supervisionBillNumbers = getIdList(requestInfo, rootTenantId,
					config.getIdGenSupervisionBillFormat(), "", 1); // idformat will be fetched by idGen service
			if (supervisionBillNumbers != null && !supervisionBillNumbers.isEmpty()) {
				supervisionBillNumber = supervisionBillNumbers.get(0);
			} else {
				throw new CustomException("SUPERVISION_BILL_NUMBER_NOT_GENERATED",
						"Error occurred while generating supervision bill number from IdGen service");
			}

			//Fetch Contract additional details and pass onto Bill for the indexer
			Object additionalDetails = expenseCalculatorUtil.getContractAdditionalDetails(requestInfo, criteria.getTenantId(), criteria.getContractId());
			// Build Bill
			Bill bill = Bill.builder().tenantId(criteria.getTenantId()).billDate(Instant.now().toEpochMilli())
					.referenceId(criteria.getContractId() + "_" + supervisionBillNumber)
					.businessService(config.getSupervisionBusinessService()).payer(payer).billDetails(billDetails)
					.totalAmount(new BigDecimal(0))
					.totalPaidAmount(new BigDecimal(0))
					.additionalDetails(additionalDetails)			
					.build();
			//Add all the details from contract
			if(additionalDetails!=null) bill.setAdditionalDetails(additionalDetails);
			bills.add(bill);
			log.info("Bill created:" + bill.toString());
		}
		return bills;
	}


	private Map<String, Map<String, JSONArray>> getMasterDataForCalculator(RequestInfo reqInfo, String tenantId) {
		
		Map<String, Map<String, JSONArray>> mdmsData = mdmsUtils.fetchMdmsData(reqInfo,
				tenantId.split("\\.")[0], ExpenseCalculatorServiceConstants.EXPENSE_MODULE, ExpenseCalculatorServiceConstants.SUPERVISION_MASTER_NAMES);
        
		if(CollectionUtils.isEmpty(mdmsData)) {
			throw new CustomException("EG_EXPENSE_MDMS_ERROR", "MDMS Data not found for the tenantid : " + tenantId);
		}
		return mdmsData;
	}

	/**
	 * Calculates the supervision charge. Used by both _estimate and _calculate
	 * APIs.
	 * 
	 * @param bills
	 * @return
	 */
	private Calculation calculateSupervisionCharge(List<Bill> bills, RequestInfo requestInfo, String tenantId,
			String contractId) {
		log.info("Calculating supervision charge for " + bills.size() + " bills");
		// Search by contractId.
		Contract contract = expenseCalculatorUtil.fetchContract(requestInfo, tenantId, contractId).get(0);

		//For purchase bill, this is not true. Payee is the vendor itself
		String orgId = contract.getOrgId();

		// Payee for supervision bill. Since one contract has only one vendor assigned
		// to it, we can build the payee
		// here.
		Party payee = buildParty(orgId, PAYEE_TYPE_SUPERVISIONBILL, tenantId);

		// Fetch the supervision charge rate from MDMS
		Map<String, Map<String, JSONArray>> mdmsData = getMasterDataForCalculator(requestInfo, tenantId);
		String jsonFilter = String.format("$[?(@.code==\"%s\")].value",HEAD_CODE_SUPERVISION);
        List<Object> superCharge = JsonPath.read(mdmsData.get(ExpenseCalculatorServiceConstants.EXPENSE_MODULE)
        		.get(ExpenseCalculatorServiceConstants.MDMS_APPLICABLE_CHARGES),jsonFilter);
        double temp = Double.parseDouble((String)superCharge.get(0));
        BigDecimal supervisionRate = BigDecimal.valueOf(temp);

		// Calculate supervision charge
		List<CalcDetail> calcDetails = new ArrayList<>();
		// One lineItem per bill and one calcDetail per Bill for supervision bills
		for (Bill bill : bills) {
			if (shouldIncludeBill(contract, bill)) {
				log.info("Computing supervision for bill ID: " + bill.getBillNumber());
				// Build lineItem. Single line item per bill detail
				LineItem lineItem = buildLineItem(tenantId, bill, supervisionRate);
				ArrayList<LineItem> items = new ArrayList<>();
				items.add(lineItem);
				// Build CalcDetails. This will be one per bill
				CalcDetail calcDetail = CalcDetail.builder().payee(payee).lineItems(items)
						.payableLineItem(items)
						.fromPeriod(new BigDecimal(bill.getBillDate())).toPeriod(new BigDecimal(bill.getBillDate()))
						.referenceId(bill.getBillNumber()).build();
				calcDetails.add(calcDetail);
			}
			else {
				log.info("Not including bill " + bill.getBillNumber()+ "in bill due to executing authority scope");
			}

		}

		// Build CalcEstimates. Equivalent to the outer Bill object.
		List<CalcEstimate> calcEstimates = new ArrayList<>();
		CalcEstimate calcEstimate = CalcEstimate.builder().referenceId(contractId).fromPeriod(contract.getStartDate())
				.toPeriod(contract.getEndDate()).tenantId(tenantId).calcDetails(calcDetails)
				.businessService(config.getSupervisionBusinessService()).build();

		calcEstimates.add(calcEstimate);

		// Build Calculation
		return Calculation.builder().tenantId(tenantId).estimates(calcEstimates).build();
	}

	/**
	 * This method figures out whether a bill should be included for supervision
	 * bill calculation in a contract.If IA, then include purchase & wage bills. If
	 * IP, then include only wage bills.
	 * 
	 * @param contract
	 * @param bill
	 * @return
	 */
	private boolean shouldIncludeBill(Contract contract, Bill bill) {
		String executingAuthority = contract.getExecutingAuthority();
		boolean isIncluded = false;
		if (StringUtils.isBlank(executingAuthority)) {
			// If EA is blank for whatever reason, include all bills by default for
			// inclusion.
			// We should never hit this spot in code.
			isIncluded = true;
		} else {
			if (CBO_IMPLEMENTATION_AGENCY.equalsIgnoreCase(executingAuthority)) {
				// If bill is of type purchase or wage, then incldue this.
				if (bill.getBusinessService().equals(config.getPurchaseBusinessService())
						|| bill.getBusinessService().equals(config.getWageBusinessService()))
					isIncluded = true;
			} else if (CBO_IMPLEMENTATION_PARTNER.equalsIgnoreCase(executingAuthority)
					&& bill.getBusinessService().equals(config.getWageBusinessService())) {
				isIncluded = true;
			}
		}

		return isIncluded;
	}

	private Party buildParty(String orgId, String type, String tenantId) {
		return Party.builder().identifier(orgId).type(type).tenantId(tenantId).status("ACTIVE").build();
	}

	private Party buildParty(RequestInfo requestInfo, String type, String tenantId) {
		Object mdmsResp = mdmsUtils.getPayersForTypeFromMDMS(requestInfo, type, tenantId);
		List<Object> payerList = commonUtil.readJSONPathValue(mdmsResp, JSON_PATH_FOR_PAYER);
		for (Object obj : payerList) {
			Payer payer = mapper.convertValue(obj, Payer.class);
			if (tenantId.equals(payer.getTenantId())) {
				return buildParty(payer.getId(), payer.getCode(), tenantId);
			}
		}
		log.error("PAYER_MISSING_IN_MDMS",
				"Payer is missing in MDMS for type : " + type + " and tenantId : " + tenantId);
		throw new CustomException("PAYER_MISSING_IN_MDMS",
				"Payer is missing in MDMS for type : " + type + " and tenantId : " + tenantId);
	}

	private LineItem buildLineItem(String tenantId, Bill bill, BigDecimal supervisionRate) {
		BigDecimal billAmount = bill.getTotalAmount();
		BigDecimal supervisionAmt = billAmount.multiply(supervisionRate).divide(new BigDecimal(100));
		// Round the supervision amount to 0 decimal place
		BigDecimal roundedNumber = supervisionAmt.setScale(0, RoundingMode.HALF_UP);
		return LineItem.builder().amount(roundedNumber).paidAmount(new BigDecimal(0)).headCode(HEAD_CODE_SUPERVISION)
				.tenantId(tenantId).type(LineItem.TypeEnum.PAYABLE).build();
	}

	/**
	 * Returns a list of numbers generated from idgen
	 *
	 * @param requestInfo RequestInfo from the request
	 * @param tenantId    tenantId of the city
	 * @param idKey       code of the field defined in application properties for
	 *                    which ids are generated for
	 * @param idformat    format in which ids are to be generated
	 * @param count       Number of ids to be generated
	 * @return List of ids generated using idGen service
	 */
	private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey, String idformat, int count) {
		List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count)
				.getIdResponses();

		if (CollectionUtils.isEmpty(idResponses))
			throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

		return idResponses.stream().map(IdResponse::getId).collect(Collectors.toList());
	}

}
