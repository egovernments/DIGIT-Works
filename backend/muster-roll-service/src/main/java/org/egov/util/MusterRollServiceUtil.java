package org.egov.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.egov.util.MusterRollServiceConstants.*;

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
    public void populateAdditionalDetails(Object mdmsData, IndividualEntry individualEntry, String skillCode, Individual matchedIndividual, boolean isCreate) {
        final String jsonPathForWorksMuster = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_WAGER_SEEKER_SKILLS + ".*";
        List<LinkedHashMap<String,String>> musterRes = null;

        try {
            musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("MusterRollServiceUtil::populateAdditionalDetails::JSONPATH_ERROR", "Failed to parse mdms response");
        }

        String skillValue = "";
        if (skillCode != null && !CollectionUtils.isEmpty(musterRes)) {
            for (Object object : musterRes) {
                LinkedHashMap<String, String> codeValueMap = (LinkedHashMap<String, String>) object;
                if (codeValueMap.get("code").equalsIgnoreCase(skillCode)) {
                    skillValue = codeValueMap.get("name");
                    break;
                }
            }
        }

        // populate individual details for estimate and create
        log.info("MusterRollServiceUtil::populateAdditionalDetails::start");
        JSONObject additionalDetails = new JSONObject();

        additionalDetails.put("userId",matchedIndividual.getIndividualId());
        additionalDetails.put("userName",matchedIndividual.getName().getGivenName());
        additionalDetails.put("fatherName",matchedIndividual.getFatherName());
        Identifier aadhaar = matchedIndividual.getIdentifiers().stream()
                            .filter(identifier -> identifier.getIdentifierType().contains("AADHAAR"))
                                    .findFirst().orElse(null);
        if (aadhaar != null) {
            additionalDetails.put("aadharNumber",aadhaar.getIdentifierId());
        }



        //populate individual's skill details in create and update (user selected skill will be set in additionalDetails)
        if (isCreate) {
            additionalDetails.put("skillCode",skillCode);
            additionalDetails.put("skillValue",skillValue);
        }

        //populate list of skills of the individual in estimate additionalDetails
        if (!isCreate && !CollectionUtils.isEmpty(matchedIndividual.getSkills())) {
            List<String> skillList = new ArrayList<>();
            for (Skill skill : matchedIndividual.getSkills()) {
                skillList.add(skill.getLevel()+"."+skill.getType());
            }
            additionalDetails.put("skillCode",skillList);
        }

        //TODO dummy value --- will be replaced with actual values from banking service
        additionalDetails.put("bankDetails","880182873839-SBIN0001237");

        try {
            individualEntry.setAdditionalDetails(mapper.readValue(additionalDetails.toString(), Object.class));
        } catch (IOException e) {
            throw new CustomException("MusterRollServiceUtil::populateAdditionalDetails::PARSING ERROR", "Failed to set additionalDetail object");
        }


    }


    public void updateAdditionalDetails(Object mdmsData, IndividualEntry individualEntry, String skillCode) {
        final String jsonPathForWorksMuster = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_WAGER_SEEKER_SKILLS + ".*";
        List<LinkedHashMap<String,String>> musterRes = null;

        try {
            musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("MusterRollServiceUtil::updateAdditionalDetails::JSONPATH_ERROR", "Failed to parse mdms response");
        }

        String skillValue = "";
        if (skillCode != null && !CollectionUtils.isEmpty(musterRes)) {
            for (Object object : musterRes) {
                LinkedHashMap<String, String> codeValueMap = (LinkedHashMap<String, String>) object;
                if (codeValueMap.get("code").equalsIgnoreCase(skillCode)) {
                    skillValue = codeValueMap.get("name");
                    break;
                }
            }
        }

        try {
            JsonNode node = mapper.readTree(mapper.writeValueAsString(individualEntry.getAdditionalDetails()));
            ((ObjectNode)node).put("skillCode", skillCode);
            ((ObjectNode)node).put("skillValue", skillValue);
            individualEntry.setAdditionalDetails(mapper.readValue(node.toString(), Object.class));

        } catch (IOException e) {
            log.info("MusterRollServiceUtil::updateAdditionalDetails::Failed to parse additionalDetail object from request"+e);
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object from request on update");
        }

    }

    /**
     *  Sets the attendanceLogId in additionalDetails of the attendanceEntry
     * @param attendanceEntry
     * @param entryAttendanceLogId
     * @param exitAttendanceLogId
     */
    public void populateAdditionalDetailsAttendanceEntry (AttendanceEntry attendanceEntry, String entryAttendanceLogId, String exitAttendanceLogId) {
        JSONObject additionalDetails = new JSONObject();
        additionalDetails.put("entryAttendanceLogId",entryAttendanceLogId);
        additionalDetails.put("exitAttendanceLogId",exitAttendanceLogId);
        try {
            attendanceEntry.setAdditionalDetails(mapper.readValue(additionalDetails.toString(), Object.class));
        } catch (IOException e) {
            throw new CustomException("MusterRollServiceUtil::populateAdditionalDetailsAttendanceEntry::PARSING ERROR", "Failed to set additionalDetail object");
        }
    }

    /**
     * Checks if the search is based only on tenantId
     * @param searchCriteria
     * @return
     */
    public boolean isTenantBasedSearch(MusterRollSearchCriteria searchCriteria) {
        if ((searchCriteria.getIds() == null || searchCriteria.getIds().isEmpty()) && StringUtils.isBlank(searchCriteria.getMusterRollNumber())
                && StringUtils.isBlank(searchCriteria.getRegisterId()) &&  searchCriteria.getFromDate() == null  && searchCriteria.getToDate() == null
                && searchCriteria.getStatus() == null && StringUtils.isBlank(searchCriteria.getMusterRollStatus())
                && StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            return true;
        }
        return false;
    }
}
