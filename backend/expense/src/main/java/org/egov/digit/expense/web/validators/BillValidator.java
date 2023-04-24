package org.egov.digit.expense.web.validators;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.ProcessInstance;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.egov.digit.expense.config.Constants.*;

@Service
@Slf4j
public class BillValidator {

    @Autowired
    private MdmsUtil mdmsUtil;

    @Autowired
    private Configuration configs;

    // TODO FIXME amount validation between amount and paid amount relation ship if negative values not involved

    public void validateCreateRequest(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        Map<String, String> errorMap = new HashMap<>();

        boolean isWorkflowActiveForBusinessService = isWorkflowActiveForBusinessService(bill.getBusinessService());

        if (isWorkflowActiveForBusinessService) {

            ProcessInstance workflow = bill.getWorkflow();

            if (null == workflow.getBusinessService())
                errorMap.put("EG_BILL_WF_FIELDS_ERROR",
                        "workflow business name is mandatory when worflow is active");

            if (null == workflow.getModuleName())
                errorMap.put("EG_BILL_WF_FIELDS_ERROR",
                        "workflow module name is mandatory when worflow is active");

        }

        validateTenantId(billRequest);
        validateMasterData(billRequest, errorMap);

        if (!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);

    }

    public void validateUpdateRequest(BillRequest billRequest) {

        Map<String, String> errorMap = new HashMap<>();
        validateTenantId(billRequest);
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
		String rootTenantId=bill.getTenantId().split("\\.")[0];
        Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(billRequest.getRequestInfo(),
                rootTenantId, Constants.HEADCODES_MODULE_NAME, Constants.MDMS_MASTER_NAMES);

        /* validating head code master data */
		List<String> headCodeList = JsonPath.read(mdmsData.get(Constants.HEADCODES_MODULE_NAME).get(HEADCODE_MASTERNAME),HEADCODE_CODE_FILTER);

        Set<String> missingHeadCodes = new HashSet<>();

        for (BillDetail billDetail : bill.getBillDetails()) {

            for (LineItem item : billDetail.getLineItems()) {

                if (!headCodeList.contains(item.getHeadCode()))
                    missingHeadCodes.add(item.getHeadCode());
            }

            for (LineItem item : billDetail.getPayableLineItems()) {

                if (!headCodeList.contains(item.getHeadCode()))
                    missingHeadCodes.add(item.getHeadCode());
            }
        }

        if (!CollectionUtils.isEmpty(missingHeadCodes))
            errorMap.put("EG_EXPENSE_INVALID_HEADCODES", "The following head codes are invalid : " + missingHeadCodes);
    }

    private void validateTenantId(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String rootTenantId = bill.getTenantId().split("\\.")[0];
        Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(billRequest.getRequestInfo(),
                rootTenantId, Constants.TENANT_MODULE_NAME, Constants.TENANT_MDMS_MASTER_NAMES);


        List<String> tenantIdList=null;
        try {
            /* validating head code master data */
            tenantIdList = JsonPath.read(mdmsData.get(Constants.TENANT_MODULE_NAME).get(TENANT_MASTERNAME), HEADCODE_CODE_FILTER);
        } catch (Exception e) {
            throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + bill.getTenantId() + "]");
        }

        if (!tenantIdList.contains(bill.getTenantId())){
            throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + bill.getTenantId() + "]");
        }
    }


    /**
     * check whether the workflow is enabled for the given business type
     *
     * @param businessServiceName
     * @return
     */
    public boolean isWorkflowActiveForBusinessService(String businessServiceName) {
        Map<String, Boolean> workflowActiveMap = configs.getBusinessServiceWorkflowStatusMap();
        boolean isWorkflowActiveForBusinessService = null != workflowActiveMap.get(businessServiceName)
                ? workflowActiveMap.get(businessServiceName)
                : false;
        return isWorkflowActiveForBusinessService;
    }
}
