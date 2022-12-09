package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.web.models.AttendanceTime;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.MusterRollServiceConstants.*;

@Component
@Slf4j
public class MusterRollValidator {

    @Autowired
    private MusterRollServiceConfiguration serviceConfiguration;

    @Autowired
    private MdmsUtil mdmsUtils;

    public AttendanceTime validateEstimateMusterRoll(MusterRollRequest musterRollRequest){
        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateEstimateMusterRoll(musterRoll, errorMap);

        //split the tenantId and validate tenantId
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, rootTenantId);
        AttendanceTime attendanceTime = validateMDMSData(musterRoll, mdmsData, errorMap);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

        return attendanceTime;

    }

    public AttendanceTime validateCreateMusterRoll(MusterRollRequest musterRollRequest) {
        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        Workflow workflow = musterRollRequest.getWorkflow();

        validateRequestInfo(requestInfo, errorMap);
        validateCreateMusterRoll(musterRoll, errorMap);
        validateWorkFlow(workflow, errorMap);

        //split the tenantId and validate tenantId
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, rootTenantId);
        AttendanceTime attendanceTime = validateMDMSData(musterRoll, mdmsData, errorMap);

        //TODO Check if the musterRoll is already created for the same registerId , startDate and endDate to avoid duplicate submission

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

        return attendanceTime;

    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateCreateMusterRoll(MusterRoll musterRoll, Map<String, String> errorMap) {
        if (musterRoll == null) {
            throw new CustomException("MUSTER_ROLL","Muster roll is mandatory");
        }
        if (musterRoll.getTenantId() == null) {
            throw new CustomException("TENANT_ID","TenantId is mandatory");
        }
        if (musterRoll.getRegisterId() == null) {
            throw new CustomException("REGISTER_ID","RegisterId is mandatory");
        }
        if (musterRoll.getStartDate() == null) {
            throw new CustomException("START_DATE_EMPTY","StartDate is mandatory");
        }

        //Check if the startDate is Monday - UI sends the epoch time in IST
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate()).atZone(ZoneId.of(/*serviceConfiguration.getTimeZone()*/"Asia/Kolkata")).toLocalDate();
        if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new CustomException("START_DATE_MONDAY","StartDate should be Monday");
        }

        log.info("MusterRollValidator::validateCreateMusterRoll::startDate in epoch::"+musterRoll.getStartDate());
        log.info("MusterRollValidator::validateCreateMusterRoll::startDate::"+startDate);

        //Set the endDate as SUNDAY
        LocalDate endDate = startDate.plusDays(6);
        musterRoll.setEndDate(endDate.atStartOfDay(ZoneId.of(/*serviceConfiguration.getTimeZone()*/"Asia/Kolkata")).toInstant().toEpochMilli());

    }

    private void validateEstimateMusterRoll(MusterRoll musterRoll, Map<String, String> errorMap) {
        if (musterRoll == null) {
            throw new CustomException("MUSTER_ROLL","Muster roll is mandatory");
        }
        if (musterRoll.getTenantId() == null) {
            throw new CustomException("TENANT_ID","TenantId is mandatory");
        }
        if (musterRoll.getRegisterId() == null) {
            throw new CustomException("REGISTER_ID","RegisterId is mandatory");
        }
        if (musterRoll.getStartDate() == null) {
            throw new CustomException("START_DATE_EMPTY","StartDate is mandatory");
        }
        //endDate is required for /_estimate musterroll - The estimate will be shown for last few days in the week in view/edit screen
        if (musterRoll.getEndDate() == null) {
            throw new CustomException("END_DATE_EMPTY","EndDate is mandatory");
        }

        //Check if the startDate is Monday - UI sends the epoch time in IST
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate()).atZone(ZoneId.of(/*serviceConfiguration.getTimeZone()*/"Asia/Kolkata")).toLocalDate();
        if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new CustomException("START_DATE_MONDAY","StartDate should be Monday");
        }

        log.info("MusterRollValidator::validateEstimateMusterRoll::startDate in epoch::"+musterRoll.getStartDate());
        log.info("MusterRollValidator::validateEstimateMusterRoll::startDate::"+startDate);
        log.info("MusterRollValidator::validateEstimateMusterRoll::endDate"+musterRoll.getEndDate());

    }

    private void validateWorkFlow(Workflow workflow, Map<String, String> errorMap) {
        if (workflow == null) {
            throw new CustomException("WORK_FLOW", "Work flow is mandatory");
        }
        if (StringUtils.isBlank(workflow.getAction())) {
            errorMap.put("WORK_FLOW.ACTION", "Work flow's action is mandatory");
        }
        //TODO - Which actions require assignee ?
        /*if (StringUtils.isNotBlank(workflow.getAction()) && !(ACTION_REJECT.equalsIgnoreCase(workflow.getAction()) || ACTION_APPROVE.equalsIgnoreCase(workflow.getAction()))
                && (workflow.getAssignees() == null || workflow.getAssignees().isEmpty())) {
            throw new CustomException("WORK_FLOW.ASSIGNEE", "Work flow's assignee is mandatory");
        }
        if (workflow.getAssignees().size() != 1) {
            throw new CustomException("WORK_FLOW.ASSIGNEE.LENGTH", "Work flow's assignee should be one");
        }*/
    }

    private AttendanceTime validateMDMSData(MusterRoll musterRoll, Object mdmsData, Map<String, String> errorMap) {

        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        final String jsonPathForWorksMuster = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_MUSTER_ROLL + ".*";
        List<Object> tenantRes = null;
        List<Object> musterRes = null;

        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
            musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes)) {
            errorMap.put("INVALID_TENANT", "The tenant: " + musterRoll.getTenantId() + " is not present in MDMS");
        }
        if (CollectionUtils.isEmpty(musterRes)) {
            errorMap.put("ENTRY_EXIT_UNAVAILABLE", "The entry and exit time are not present in MDMS");
        }

        //Fetch entryHour and exitHour from MDMS
        AttendanceTime attendanceTime = new AttendanceTime();
        for (Object object : musterRes) {
            LinkedHashMap<String,String> codeValueMap = (LinkedHashMap<String, String>) object;
            String code = codeValueMap.get("code");
            String value = codeValueMap.get("value");
            switch (code) {
                case ENTRY_HOUR :
                    attendanceTime.setEntryHour(Integer.parseInt(value));
                    break;
                case EXIT_HOUR_HALF_DAY :
                    attendanceTime.setExitHourHalfDay(Integer.parseInt(value));
                    break;
                case EXIT_HOUR_FULL_DAY :
                    attendanceTime.setExitHourFullDay(Integer.parseInt(value));
                    break;
            }

        }

        return attendanceTime;
    }
}
