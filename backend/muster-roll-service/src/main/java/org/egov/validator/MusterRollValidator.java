package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
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

    @Autowired
    private MusterRollRepository musterRollRepository;


    /**
     * Validate muster roll in estimate service
     * @param musterRollRequest
     */
    public void validateEstimateMusterRoll(MusterRollRequest musterRollRequest){
        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateEstimateMusterRoll(musterRoll, errorMap);

        //split the tenantId and validate tenantId
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, rootTenantId);
        validateMDMSData(musterRoll, mdmsData, errorMap);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

    }

    /**
     * Validate muster roll in create service
     * @param musterRollRequest
     */
    public void validateCreateMusterRoll(MusterRollRequest musterRollRequest) {
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
        validateMDMSData(musterRoll, mdmsData, errorMap);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

    }

    /**
     * Validate muster roll in update service
     * @param musterRollRequest
     */
    public void validateUpdateMusterRoll(MusterRollRequest musterRollRequest) {
        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        Workflow workflow = musterRollRequest.getWorkflow();

        validateRequestInfo(requestInfo, errorMap);
        validateWorkFlow(workflow, errorMap);
        validateUpdateMusterRoll(musterRoll, requestInfo, workflow, errorMap);

        //split the tenantId and validate tenantId
        String rootTenantId = musterRoll.getTenantId().split("\\.")[0];
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, rootTenantId);
        validateMDMSData(musterRoll, mdmsData, errorMap);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

    }

    /**
     * Validate muster roll in search service
     * @param requestInfoWrapper
     * @param searchCriteria
     */
    public void validateSearchMuster(RequestInfoWrapper requestInfoWrapper, MusterRollSearchCriteria searchCriteria) {
        if (searchCriteria == null || requestInfoWrapper == null || requestInfoWrapper.getRequestInfo() == null) {
            throw new CustomException("MUSTER_ROLL_SEARCH_CRITERIA_REQUEST", " Muster roll search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
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
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate().longValue()).atZone(ZoneId.of(ZONE)).toLocalDate();
        if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new CustomException("START_DATE_MONDAY","StartDate should be Monday");
        }

        log.info("MusterRollValidator::validateCreateMusterRoll::startDate in epoch::"+musterRoll.getStartDate());
        log.info("MusterRollValidator::validateCreateMusterRoll::startDate::"+startDate);

        //Set the endDate as SUNDAY
        LocalDate endDate = startDate.plusDays(6);
        musterRoll.setEndDate(new BigDecimal(endDate.atStartOfDay(ZoneId.of(ZONE)).toInstant().toEpochMilli()));

    }

    private void validateUpdateMusterRoll(MusterRoll musterRoll, RequestInfo requestInfo, Workflow workflow, Map<String, String> errorMap) {
        if (musterRoll == null) {
            throw new CustomException("MUSTER_ROLL","Muster roll is mandatory");
        }
        if (musterRoll.getTenantId() == null) {
            throw new CustomException("TENANT_ID","TenantId is mandatory");
        }
        if (musterRoll.getId() == null) {
            throw new CustomException("MUSTER_ROLL_ID","MusterRollId is mandatory");
        }

        if (workflow.getAction().equalsIgnoreCase("VERIFY")) {
            List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
            //verify eligible role
            if (!isVerifyEligibleRole(requestInfo)) {
                throw new CustomException("VERIFY_INELIGIBLE","User ineligible for Verify action");
            }
            if (CollectionUtils.isEmpty(individualEntries)) {
                throw new CustomException("INDIVIDUAL_ENTRY","IndividualEntry is mandatory");
            }
            for (IndividualEntry individualEntry : individualEntries) {
                if (individualEntry.getId() == null) {
                    throw new CustomException("INDIVIDUAL_ENTRY_ID","Id for IndividualEntry is mandatory");
                }
                //totalAttendance is required for 'VERIFY'
                if (individualEntry.getTotalAttendance() == null) {
                    throw new CustomException("INDIVIDUAL_ENTRY_TOTAL_ATTENDANCE","Total Attendance for IndividualEntry is mandatory");
                }
            }

        }

    }

    /**
     * Validate the role for VERIFY ACTION
     * @param requestInfo
     */
    private boolean isVerifyEligibleRole(RequestInfo requestInfo) {
        User userInfo = requestInfo.getUserInfo();
        boolean rolePresent = false;
        if (userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()) {
            List<org.egov.common.contract.request.Role> roles = userInfo.getRoles();
            List<String> verifyEligibleRole = Arrays.asList(VERIFY_ELIGIBLE_ROLES.split(","));

            rolePresent = roles.stream().anyMatch(role -> {
                return verifyEligibleRole.contains(role.getCode());
            });

        }
        return rolePresent;
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

        log.info("MusterRollValidator::validateEstimateMusterRoll::startDate in epoch::"+musterRoll.getStartDate());
        log.info("MusterRollValidator::validateEstimateMusterRoll::endDate"+musterRoll.getEndDate());

    }

    private void validateWorkFlow(Workflow workflow, Map<String, String> errorMap) {
        if (workflow == null) {
            throw new CustomException("WORK_FLOW", "Work flow is mandatory");
        }
        if (StringUtils.isBlank(workflow.getAction())) {
            errorMap.put("WORK_FLOW.ACTION", "Work flow's action is mandatory");
        }

        if (StringUtils.isNotBlank(workflow.getAction()) && !(ACTION_REJECT.equalsIgnoreCase(workflow.getAction())
                || ACTION_APPROVE.equalsIgnoreCase(workflow.getAction()) || CLOSEAFTERREJECTION.equalsIgnoreCase(workflow.getAction()))
                && (workflow.getAssignees() == null || workflow.getAssignees().isEmpty() || workflow.getAssignees().size() != 1)) {
            throw new CustomException("WORK_FLOW.ASSIGNEE", "Work flow's assignee is mandatory");
        }
    }

    private void validateMDMSData(MusterRoll musterRoll, Object mdmsData, Map<String, String> errorMap) {

        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        List<Object> tenantRes = null;

        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes)) {
            errorMap.put("INVALID_TENANT", "The tenant: " + musterRoll.getTenantId() + " is not present in MDMS");
        }

    }
}
