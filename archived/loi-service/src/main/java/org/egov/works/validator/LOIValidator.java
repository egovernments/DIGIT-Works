package org.egov.works.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.LOISearchCriteria;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.egov.works.web.models.LetterOfIndentRequestWorkflow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LOIValidator {



    public void validateCreateLOI(LetterOfIndentRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        LetterOfIndent letterOfIndent = request.getLetterOfIndent();
        RequestInfo requestInfo = request.getRequestInfo();
        LetterOfIndentRequestWorkflow workflow = request.getWorkflow();
        validateRequestInfo(requestInfo, errorMap);
        validateLetterOfIndent(letterOfIndent, errorMap);

        validateWorkFlow(workflow, errorMap);

//       TODO:  3. Check workPackageNumber
//       TODO:  4. Check contractorId
//       TODO:  5. (If present) validate oicId

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);

    }

    public void validateUpdateLOI(LetterOfIndentRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        LetterOfIndent letterOfIndent = request.getLetterOfIndent();
        RequestInfo requestInfo = request.getRequestInfo();
        LetterOfIndentRequestWorkflow workflow = request.getWorkflow();
        validateRequestInfo(requestInfo, errorMap);
        validateUpdateLetterOfIndent(letterOfIndent, errorMap);

        validateWorkFlow(workflow, errorMap);

//       TODO:  3. Check workPackageNumber
//       TODO:  4. Check contractorId
//       TODO:  5. (If present) validate oicId
         if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            errorMap.put("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            errorMap.put("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateLetterOfIndent(LetterOfIndent letterOfIndent, Map<String, String> errorMap) {
        if (letterOfIndent == null) {
            throw new CustomException("LOI", "Letter of Indent is mandatory");
        }
        if (StringUtils.isBlank(letterOfIndent.getTenantId())) {
            errorMap.put("TENANT_ID", "Tenant is is mandatory");
        }
        if (letterOfIndent.getFileNumber() == null || StringUtils.isBlank(letterOfIndent.getFileNumber())) {
            errorMap.put("FILE_NUMBER", "File Number is mandatory");
        }
        if (letterOfIndent.getFileDate() == null) {
            errorMap.put("FILE_DATE", "File Date is mandatory");
        }
        if (letterOfIndent.getNegotiatedPercentage() == null) {
            errorMap.put("PERCENTAGE_NEGOTIATED", "Percentage Negotiated is mandatory");
        }
        if (letterOfIndent.getNegotiatedPercentage().compareTo(new BigDecimal(-100)) == -1 || letterOfIndent.getNegotiatedPercentage().compareTo(new BigDecimal(100)) == 1) {
            errorMap.put("PERCENTAGE_NEGOTIATED", "Percentage Negotiated value is incorrect.");
        }
        if (letterOfIndent.getAgreementDate() == null) {
            errorMap.put("AGREEMENT_DATE", "Agreement Date is mandatory");
        }
        if (letterOfIndent.getEmdAmount() == null) {
            errorMap.put("EMO_AMOUNT", "EMD Amount is mandatory");
        }
        if (letterOfIndent.getDefectLiabilityPeriod() == null) {
            errorMap.put("DEFECT_LIABILITY_PERIOD", "Defect Liability Period is mandatory");
        }
        if (letterOfIndent.getOicId() == null) {
            errorMap.put("OIC_ID", "OIC Id is mandatory");
        }
        if (letterOfIndent.getStatus() == null || !EnumUtils.isValidEnum(LetterOfIndent.StatusEnum.class, letterOfIndent.getStatus().toString())) {
            errorMap.put("STATUS", "Status is mandatory");
        }
    }

    private void validateUpdateLetterOfIndent(LetterOfIndent letterOfIndent, Map<String, String> errorMap) {
        if (letterOfIndent == null) {
            throw new CustomException("LOI", "Letter of Indent is mandatory");
        }
        if (StringUtils.isBlank(letterOfIndent.getTenantId())) {
            errorMap.put("TENANT_ID", "Tenant is is mandatory");
        }
        if (letterOfIndent.getFileNumber() == null || StringUtils.isBlank(letterOfIndent.getFileNumber())) {
            errorMap.put("FILE_NUMBER", "File Number is mandatory");
        }
        if (letterOfIndent.getFileDate() == null) {
            errorMap.put("FILE_DATE", "File Date is mandatory");
        }
        if (letterOfIndent.getNegotiatedPercentage() == null) {
            errorMap.put("PERCENTAGE_NEGOTIATED", "Percentage Negotiated is mandatory");
        }
        if (letterOfIndent.getNegotiatedPercentage().compareTo(new BigDecimal(-100)) == -1 || letterOfIndent.getNegotiatedPercentage().compareTo(new BigDecimal(100)) == 1) {
            errorMap.put("PERCENTAGE_NEGOTIATED", "Percentage Negotiated value is incorrect.");
        }
        if (letterOfIndent.getAgreementDate() == null) {
            errorMap.put("AGREEMENT_DATE", "Agreement Date is mandatory");
        }
        if (letterOfIndent.getEmdAmount() == null) {
            errorMap.put("EMD_AMOUNT", "EMD Amount is mandatory");
        }
        if (letterOfIndent.getDefectLiabilityPeriod() == null) {
            errorMap.put("DEFECT_LIABILITY_PERIOD", "Defect Liability Period is mandatory");
        }
        if (letterOfIndent.getOicId() == null) {
            errorMap.put("OIC_ID", "OIC Id is mandatory");
        }
        if (letterOfIndent.getStatus() == null || !EnumUtils.isValidEnum(LetterOfIndent.StatusEnum.class, letterOfIndent.getStatus().toString())) {
            errorMap.put("STATUS", "Status is mandatory");
        }
    }

    private void validateWorkFlow(LetterOfIndentRequestWorkflow workflow, Map<String, String> errorMap) {
        if (workflow == null) {
            throw new CustomException("WORK_FLOW", "Work flow is mandatory");
        }
        if (StringUtils.isBlank(workflow.getAction())) {
            errorMap.put("WORK_FLOW.ACTION", "Work flow's action is mandatory");
        }
    }

    public void validateSearchLOI(LOISearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            throw new CustomException("LOI", "LOI is mandatory");
        }

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant Id is mandatory");
        }
    }


}
