package org.egov.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.IndividualEntry;
import org.egov.web.models.MusterRoll;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.egov.util.MusterRollServiceConstants.MASTER_MUSTER_ROLL;
import static org.egov.util.MusterRollServiceConstants.MDMS_COMMON_MASTERS_MODULE_NAME;

@Component
@Slf4j
public class MusterRollServiceUtil {

    @Autowired
    private ObjectMapper mapper;

    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, MusterRoll musterRoll, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(musterRoll.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(musterRoll.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }

    /**
     * Fetch the individual skill level from MDMS
     * @param mdmsData
     * @param individualEntry
     * @param skillCode
     *
     */
    public void populateAdditionalDetails(Object mdmsData, IndividualEntry individualEntry, String skillCode) {
        final String jsonPathForWorksMuster = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_MUSTER_ROLL + ".*";
        List<LinkedHashMap<String,String>> musterRes = null;

        try {
            musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("MusterRollServiceUtil::populateAdditionalDetails::JSONPATH_ERROR", "Failed to parse mdms response");
        }

        String skillValue = "";
        if (!CollectionUtils.isEmpty(musterRes)) {
            for (Object object : musterRes) {
                LinkedHashMap<String, String> codeValueMap = (LinkedHashMap<String, String>) object;
                if (codeValueMap.get("code").equalsIgnoreCase(skillCode)) {
                    skillValue = codeValueMap.get("value");
                    break;
                }
            }
        }
        if (StringUtils.isNotBlank(skillValue)) {
            log.info("MusterRollServiceUtil::populateAdditionalDetails::start");

            JSONObject additionalDetails = new JSONObject();
            additionalDetails.put("skillCode",skillCode);
            additionalDetails.put("skillValue",skillValue);
            //TODO dummy value --- will be replaced with actual values from individual service
            additionalDetails.put("userName","Piyush HarjitPal");
            additionalDetails.put("fatherName","Harijitpal");
            additionalDetails.put("aadharNumber","9099-1234-1234");
            additionalDetails.put("bankDetails","880182873839-SBIN0001237");

            try {
               individualEntry.setAdditionalDetails(mapper.readValue(additionalDetails.toString(), Object.class));
            } catch (IOException e) {
                throw new CustomException("MusterRollServiceUtil::populateAdditionalDetails::PARSING ERROR", "Failed to set additionalDetail object");
            }
        }

    }
}
