package org.egov.digit.expense.web.validators;

import java.util.Map;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import digit.models.coremodels.ProcessInstance;

@Service
public class BillValidator {
	
	@Autowired
	private Configuration configs;

	public void validateCreateRequest(BillRequest billRequest) {
		
		Bill bill = billRequest.getBill();
		Map<String, Boolean> workflowActiveMap = configs.getBusinessServiceWorkflowStatusMap();
		boolean isWorkflowActiveForBusinessService = null != workflowActiveMap.get(bill.getBusinessService()) 
				? workflowActiveMap.get(bill.getBusinessService()) : false; 
		
		if(isWorkflowActiveForBusinessService){
			
			ProcessInstance workflow = bill.getWorkflow();
			if (null == workflow.getBusinessService())
				throw new CustomException("EG_BILL_WF_ERROR",
						"workflow business name is mandatory when worflow is active");
			
		}
		
	}
	
	public void validateUpdateRequest(BillRequest billRequest) {
		
		// mdms integration of head codes 
		
	}
	
	public void validateSearchRequest(BillCriteria billCriteria) {
		
		if (StringUtils.isEmpty(billCriteria.getBusinessService())
				&& CollectionUtils.isEmpty(billCriteria.getReferenceIds())
				&& CollectionUtils.isEmpty(billCriteria.getIds()))
			throw new CustomException("EG_EXPENSE_BILL_SEARCH_ERROR",
					"One of referenceIds or ids or businessService should be provided for a bill search");
		
	}
}
