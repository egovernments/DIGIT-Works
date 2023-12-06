package org.egov.digit.expense.web.validators;

import static org.egov.digit.expense.config.Constants.BUSINESS_SERVICE_MASTERNAME;
import static org.egov.digit.expense.config.Constants.CODE_FILTER;
import static org.egov.digit.expense.config.Constants.HEADCODE_MASTERNAME;
import static org.egov.digit.expense.config.Constants.TENANT_MASTERNAME;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.jayway.jsonpath.JsonPath;

import digit.models.coremodels.Workflow;
import net.minidev.json.JSONArray;

@Service
public class BillValidator {

    private final MdmsUtil mdmsUtil;

    private final Configuration configs;
    
    private final BillRepository billRepository;

	@Autowired
	public BillValidator(MdmsUtil mdmsUtil, Configuration configs, BillRepository billRepository) {
		this.mdmsUtil = mdmsUtil;
		this.configs = configs;
		this.billRepository = billRepository;
	}

	public void validateCreateRequest(BillRequest billRequest) {

    	Map<String, String> errorMap = new HashMap<>();
    	
        Bill bill = billRequest.getBill();
        
        List<Bill> billsFromSearch = getBillsForValidation(billRequest, true);
        if(!CollectionUtils.isEmpty(billsFromSearch))
        	throw new CustomException("EG_EXPENSE_DUPLICATE_BILL","Active bill exists for the given combination of "
        			+ " businessService : " + bill.getBusinessService() + " and refernceId : " + bill.getReferenceId());
        
        validateWorkflow(billRequest, errorMap);
        
		Map<String, Map<String, JSONArray>> mdmsData = getMasterDataForValidation(billRequest, bill);
        validateBillAmountAndDate(bill, errorMap);
        validateTenantId(billRequest,mdmsData);
        validateMasterData(billRequest, errorMap, mdmsData, true);

        if (!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);

    }

    public List<Bill> validateUpdateRequest(BillRequest billRequest) {

        Map<String, String> errorMap = new HashMap<>();
        Bill bill = billRequest.getBill();
        
        validateWorkflow(billRequest, errorMap);
        
        List<Bill> billsFromSearch = getBillsForValidation(billRequest, false);
        if(CollectionUtils.isEmpty(billsFromSearch))
        	throw new CustomException("EG_EXPENSE_INVALID_BILL","The bill does not exists for the given combination of "
        			+ " id : " + bill.getId() + " and refernceId : " + bill.getTenantId());
        
        validateFieldsForUpdate(bill, billsFromSearch.get(0), errorMap); 
        
		Map<String, Map<String, JSONArray>> mdmsData = getMasterDataForValidation(billRequest, bill);
        validateTenantId(billRequest,mdmsData);
        validateMasterData(billRequest, errorMap, mdmsData, false);

        if (!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);
        
        return billsFromSearch;
    }

	private void validateFieldsForUpdate(Bill bill, Bill billFromSearch, Map<String, String> errorMap) {

		List<String> invalidDetailIds = new ArrayList<>();
		List<String> invalidLineItemIds = new ArrayList<>();
		List<String> invalidPayableLineItemIds = new ArrayList<>();

		validateBillStatus(bill, billFromSearch);
		validatePayer(bill, billFromSearch, errorMap);

		List<BillDetail> details = bill.getBillDetails();
		List<BillDetail> detailsFromSearch = billFromSearch.getBillDetails();
		Map<String, BillDetail> searchDetailsMap = detailsFromSearch.stream()
				.collect(Collectors.toMap(BillDetail::getId, Function.identity()));

		Map<String, LineItem> searchLineItemMap = detailsFromSearch.stream()
				.map(BillDetail::getLineItems)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(LineItem::getId, Function.identity()));

		Map<String, LineItem> searchPayableLineItemMap = detailsFromSearch.stream()
				.map(BillDetail::getPayableLineItems)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(LineItem::getId, Function.identity()));

		if(CollectionUtils.isEmpty(details)) {
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_BILLDETAILS", "bill details cannot be empty for update request");
		} else {
			validateBillDetails(details, searchDetailsMap, searchLineItemMap, searchPayableLineItemMap, invalidDetailIds, invalidLineItemIds, invalidPayableLineItemIds);
		}

		handleInvalidIds(invalidDetailIds, invalidLineItemIds, invalidPayableLineItemIds, errorMap);
	}

	private void validateBillStatus(Bill bill, Bill billFromSearch) {
		if(null == bill.getStatus()){
			bill.setStatus(billFromSearch.getStatus());
		}
	}

	private void validatePayer(Bill bill, Bill billFromSearch, Map<String, String> errorMap) {
		Party payer = bill.getPayer();
		Party payerFromSearch = billFromSearch.getPayer();

		if(null == payer) {
			bill.setPayer(payerFromSearch);
		} else if(null == payer.getId()){
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_PAYER_ID", "Payer id is mandatory for update request");
		}
	}

	private void validateBillDetails(List<BillDetail> details, Map<String, BillDetail> searchDetailsMap, Map<String, LineItem> searchLineItemMap, Map<String, LineItem> searchPayableLineItemMap, List<String> invalidDetailIds, List<String> invalidLineItemIds, List<String> invalidPayableLineItemIds) {
		for (BillDetail currentDetail : details) {
			String currentDetailId = currentDetail.getId();
			BillDetail currentDetailFromSearch = searchDetailsMap.get(currentDetailId);

			if(null == currentDetailId) {
				currentDetail.setStatus(Status.ACTIVE);
			}
			if (null == currentDetailFromSearch) {
				invalidDetailIds.add(currentDetailId);
			} else {
				validateLineItems(currentDetail, currentDetailFromSearch, searchLineItemMap, searchPayableLineItemMap, invalidLineItemIds, invalidPayableLineItemIds);
			}
		}
	}

	private void validateLineItems(BillDetail currentDetail, BillDetail currentDetailFromSearch, Map<String, LineItem> searchLineItemMap, Map<String, LineItem> searchPayableLineItemMap, List<String> invalidLineItemIds, List<String> invalidPayableLineItemIds) {
		if(currentDetail.getStatus() == null)
			currentDetail.setStatus(currentDetailFromSearch.getStatus());

		List<LineItem> lineItems = currentDetail.getLineItems();
		List<LineItem> payableLineItems = currentDetail.getPayableLineItems();

		validateIndividualLineItems(lineItems, searchLineItemMap, invalidLineItemIds);
		validateIndividualLineItems(payableLineItems, searchPayableLineItemMap, invalidPayableLineItemIds);
	}

	private void validateIndividualLineItems(List<LineItem> lineItems, Map<String, LineItem> searchLineItemMap, List<String> invalidLineItemIds) {
		for (LineItem currentLineItem : lineItems) {
			String currentLineItemId = currentLineItem.getId();
			LineItem searchLineItem = searchLineItemMap.get(currentLineItemId);
			if(null == currentLineItemId) {
				currentLineItem.setStatus(Status.ACTIVE);
			} else if(null == searchLineItem) {
				invalidLineItemIds.add(currentLineItemId);
			} else {
				if(null == currentLineItem.getStatus())
					currentLineItem.setStatus(searchLineItem.getStatus());
			}
		}
	}

	private void handleInvalidIds(List<String> invalidDetailIds, List<String> invalidLineItemIds, List<String> invalidPayableLineItemIds, Map<String, String> errorMap) {
		String billDetailErrorMessage = "bill detail id is Invalid for the given ids of update request : " ;
		if(!CollectionUtils.isEmpty(invalidDetailIds)) {
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_BillDETAIL_ID",
					billDetailErrorMessage + invalidDetailIds);
		}

		if(!CollectionUtils.isEmpty(invalidLineItemIds)) {
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_LINEITEM_ID",
					billDetailErrorMessage + invalidLineItemIds);
		}

		if(!CollectionUtils.isEmpty(invalidPayableLineItemIds))
			errorMap.put("EG_EXPENSE_BILL_UPDATE_NOTNULL_PAYABLE_LINEITEM_ID",
					billDetailErrorMessage + invalidPayableLineItemIds);
	}

	public void validateSearchRequest(BillSearchRequest billSearchRequest) {
    	
    	BillCriteria billCriteria = billSearchRequest.getBillCriteria();
        if (StringUtils.isEmpty(billCriteria.getBusinessService())
                && CollectionUtils.isEmpty(billCriteria.getReferenceIds())
                && CollectionUtils.isEmpty(billCriteria.getIds())
                && CollectionUtils.isEmpty(billCriteria.getBillNumbers())
				&& billCriteria.getIsPaymentStatusNull() == null )
            throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
                    "One of ids OR (referenceIds & businessService) OR (billNumbers & businessService) should be provided for a bill search");
        boolean isRefIdOrBillNoProvided = (!CollectionUtils.isEmpty(billCriteria.getReferenceIds())
				|| !CollectionUtils.isEmpty(billCriteria.getBillNumbers()));
        boolean isBusinessServiceProvided = !StringUtils.isEmpty(billCriteria.getBusinessService());
		
		if ((isRefIdOrBillNoProvided && !isBusinessServiceProvided)
				|| (isBusinessServiceProvided && !isRefIdOrBillNoProvided))
            throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
                    "The values of referenceIds or billNumbers should be provided along with businessService for a bill search");
    }

	private void validateMasterData(BillRequest billRequest, Map<String, String> errorMap, Map<String, Map<String, JSONArray>> mdmsData, boolean isCreate) {
		Bill bill = billRequest.getBill();
		validateBusinessService(bill, mdmsData, errorMap);
		validateBillDetails(bill, mdmsData, errorMap, isCreate);
	}

	private void validateBusinessService(Bill bill, Map<String, Map<String, JSONArray>> mdmsData, Map<String, String> errorMap) {
		List<String> businessCodeList = JsonPath.read(mdmsData.get(Constants.EXPENSE_MODULE_NAME).get(BUSINESS_SERVICE_MASTERNAME),CODE_FILTER);
		if (!businessCodeList.contains(bill.getBusinessService())) {
			errorMap.put("EG_EXPENSE_INVALID_BUSINESSSERVICE", "The business service value : " + bill.getBusinessService() + " is invalid");
		}
	}

	private void validateBillDetails(Bill bill, Map<String, Map<String, JSONArray>> mdmsData, Map<String, String> errorMap, boolean isCreate) {
		List<String> headCodeList = JsonPath.read(mdmsData.get(Constants.EXPENSE_MODULE_NAME).get(HEADCODE_MASTERNAME),CODE_FILTER);
		Set<String> missingHeadCodes = new HashSet<>();
		BigDecimal billAmount = BigDecimal.ZERO;
		BigDecimal billPaidAmount = BigDecimal.ZERO;

		for (BillDetail billDetail : bill.getBillDetails()) {
			BigDecimal[] amounts = validateLineItems(billDetail, headCodeList, missingHeadCodes, errorMap, isCreate);
			billDetail.setTotalAmount(amounts[0]);
			billDetail.setTotalPaidAmount(amounts[1]);
			if (isCreate || billDetail.getStatus().equals(Status.ACTIVE)) {
				billAmount = billAmount.add(amounts[0]);
				billPaidAmount = billPaidAmount.add(amounts[1]);
			}
		}
		bill.setTotalAmount(billAmount);
		bill.setTotalPaidAmount(billPaidAmount);

		if (!CollectionUtils.isEmpty(missingHeadCodes))
			errorMap.put("EG_EXPENSE_INVALID_HEADCODES", "The following head codes are invalid : " + missingHeadCodes);
	}

	private BigDecimal[] validateLineItems(BillDetail billDetail, List<String> headCodeList, Set<String> missingHeadCodes, Map<String, String> errorMap, boolean isCreate) {
		BigDecimal billDetailAmount = BigDecimal.ZERO;
		BigDecimal billDetailPaidAmount = BigDecimal.ZERO;

		for (LineItem item : billDetail.getLineItems()) {
			validateLineItem(item, headCodeList, missingHeadCodes, errorMap);
			billDetailAmount = billDetailAmount.add(item.getAmount());
			billDetailPaidAmount = billDetailPaidAmount.add(item.getPaidAmount());
		}

		for (LineItem payableLineItem : billDetail.getPayableLineItems()) {
			validateLineItem(payableLineItem, headCodeList, missingHeadCodes, errorMap);
			if (isCreate || payableLineItem.getStatus().equals(Status.ACTIVE)) {
				billDetailAmount = billDetailAmount.add(payableLineItem.getAmount());
				billDetailPaidAmount = billDetailPaidAmount.add(payableLineItem.getPaidAmount());
			}
		}

		return new BigDecimal[]{billDetailAmount, billDetailPaidAmount};
	}

	private void validateLineItem(LineItem item, List<String> headCodeList, Set<String> missingHeadCodes, Map<String, String> errorMap) {
		BigDecimal amount = item.getAmount();
		BigDecimal paidAmount = item.getPaidAmount() != null ? item.getPaidAmount() : BigDecimal.ZERO;

		if (!headCodeList.contains(item.getHeadCode()))
			missingHeadCodes.add(item.getHeadCode());

		if (amount.compareTo(paidAmount) < 0) {
			errorMap.put("EG_EXPENSE_LINEITEM_INVALID_AMOUNT", "The tax amount : " + amount + " cannot be lesser than the paid amount : " + paidAmount);
		}
		item.setPaidAmount(paidAmount);
	}

    private void validateTenantId(BillRequest billRequest, Map<String, Map<String, JSONArray>> mdmsData) {

        Bill bill = billRequest.getBill();

        List<String> tenantIdList=null;
        try {
            /* validating head code master data */
            tenantIdList = JsonPath.read(mdmsData.get(Constants.TENANT_MODULE_NAME).get(TENANT_MASTERNAME), CODE_FILTER);
        } catch (Exception e) {
            throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + bill.getTenantId() + "]");
        }

        if (!tenantIdList.contains(bill.getTenantId())){
            throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + bill.getTenantId() + "]");
        }
    }

	private void validateBillAmountAndDate( Bill bill, Map<String, String> errorMap) {
		
		Long billDate = null != bill.getBillDate() ? bill.getBillDate() : 0l;
		Long dueDate = null != bill.getDueDate() ? bill.getDueDate() : Long.MAX_VALUE;
        
        if(dueDate.compareTo(billDate) < 0)
        	errorMap.put("EG_EXPENSE_BILL_INVALID_DATE",
					"The due Date : " + billDate + " cannot be greater than the due Date : " + dueDate);
	}
    
	/**
     * check whether the workflow is enabled for the given business type
     *
     * @param businessServiceName
     * @return
     */
    public boolean isWorkflowActiveForBusinessService(String businessServiceName) {
        Map<String, Boolean> workflowActiveMap = configs.getBusinessServiceWorkflowStatusMap();
		return null != workflowActiveMap.get(businessServiceName)
				&& workflowActiveMap.get(businessServiceName);
    }
    
    private List<Bill> getBillsForValidation(BillRequest billRequest, Boolean isCreate){

    	Bill bill = billRequest.getBill();
    	BillCriteria billCriteria = BillCriteria.builder()
    			.statusNot(Status.INACTIVE.toString())
				.tenantId(bill.getTenantId())
				.build();
    	
		if (Boolean.TRUE.equals(isCreate)) {

			billCriteria.setReferenceIds(Stream.of(bill.getReferenceId()).collect(Collectors.toSet()));
			billCriteria.setBusinessService(bill.getBusinessService());
		} else {
			
			billCriteria.setIds(Stream.of(bill.getId()).collect(Collectors.toSet()));
		}
		
		
		
		BillSearchRequest billSearchRequest = BillSearchRequest.builder()
				.requestInfo(billRequest.getRequestInfo())
				.billCriteria(billCriteria)
				.build();
		
		return billRepository.search(billSearchRequest);
    }
    
	private void validateWorkflow(BillRequest billRequest, Map<String, String> errorMap) {
		
		Bill bill = billRequest.getBill();
		boolean isWorkflowActiveForBusinessService = isWorkflowActiveForBusinessService(bill.getBusinessService());

        if (isWorkflowActiveForBusinessService) {

            Workflow workflow = billRequest.getWorkflow();

			if (null == workflow)
				throw new CustomException("EG_BILL_WF_ERROR", "workflow is mandatory when worflow is active");

            if (null == workflow.getAction())
                errorMap.put("EG_BILL_WF_FIELDS_ERROR",
                        "workflow action is mandatory when worflow is active");
        }
	}

	private Map<String, Map<String, JSONArray>> getMasterDataForValidation(BillRequest billRequest, Bill bill) {
		
		Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(billRequest.getRequestInfo(),
				bill.getTenantId());
        
		if(CollectionUtils.isEmpty(mdmsData)) {
			throw new CustomException("EG_EXPENSE_MDMS_ERROR", "MDMS Data not found for the tenantid : " + bill.getTenantId());
		}
		return mdmsData;
	}

}