package org.egov.digit.expense.web.validators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.egov.digit.expense.config.BillConstants;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.jayway.jsonpath.JsonPath;

import digit.models.coremodels.ProcessInstance;
import net.minidev.json.JSONArray;

@Service
public class BillValidator {
	
	@Autowired
	private MdmsUtil mdmsUtil;
	
	@Autowired
	private Configuration configs;
	
	// TODO FIXME amount validation between amount and paid amount relation ship if negative values not involved

	public void validateCreateRequest(BillRequest billRequest) {
		
		Bill bill = billRequest.getBill();
		Map<String, String> errorMap = new HashMap<>();
		
		Map<String, Boolean> workflowActiveMap = configs.getBusinessServiceWorkflowStatusMap();
		boolean isWorkflowActiveForBusinessService = null != workflowActiveMap.get(bill.getBusinessService()) 
				? workflowActiveMap.get(bill.getBusinessService()) : false; 
		
		if(isWorkflowActiveForBusinessService){
			
			ProcessInstance workflow = bill.getWorkflow();
			
			if (null == workflow.getBusinessService())
				errorMap.put("EG_BILL_WF_FIELDS_ERROR",
						"workflow business name is mandatory when worflow is active");
			
			if (null == workflow.getModuleName())
				errorMap.put("EG_BILL_WF_FIELDS_ERROR",
						"workflow module name is mandatory when worflow is active");
			
		}
		
		validateMasterData(billRequest, errorMap);
		
		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);
		
	}
	
	public void validateUpdateRequest(BillRequest billRequest) {
		
		Map<String, String> errorMap = new HashMap<>();
		validateMasterData(billRequest, errorMap);
		
		if (!CollectionUtils.isEmpty(errorMap))
			throw new CustomException(errorMap);
	}
	
	public void validateSearchRequest(BillCriteria billCriteria) {
		
		if (StringUtils.isEmpty(billCriteria.getBusinessService())
				&& CollectionUtils.isEmpty(billCriteria.getReferenceIds())
				&& CollectionUtils.isEmpty(billCriteria.getIds()))
			throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
					"One of referenceIds or ids or businessService should be provided for a bill search");
		
	}
	
	
	private void validateMasterData(BillRequest billRequest, Map<String, String> errorMap) {

		Bill bill = billRequest.getBill();
		Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(billRequest.getRequestInfo(),
				bill.getTenantId(), BillConstants.MODULE_NAME, BillConstants.MDMS_MASTER_NAMES);

		/* validating head code master data */
		List<String> headCodeList = JsonPath.read(mdmsData, BillConstants.HEADCODE_CODE_FILTER);
		
		Set<String> missingHeadCodes = new HashSet<>();

		for (BillDetail billDetail : bill.getBillDetails()) {

			for (LineItem item : billDetail.getLineItems()) {

				if(!headCodeList.contains(item.getHeadCode()))
					missingHeadCodes.add(item.getHeadCode());
			}

			for (LineItem item : billDetail.getPayableLineItems()) {
				
				if(!headCodeList.contains(item.getHeadCode()))
					missingHeadCodes.add(item.getHeadCode());
			}
		}

		if (!CollectionUtils.isEmpty(missingHeadCodes))
			errorMap.put("EG_EXPENSE_INVALID_HEADCODES", "The following head codes are invalid : " + missingHeadCodes);
	}
}
