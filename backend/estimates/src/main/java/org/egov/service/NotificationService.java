package org.egov.service;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.producer.EstimateProducer;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.EstimateServiceConstant;
import org.egov.util.HRMSUtils;
import org.egov.util.LocationServiceUtil;
import org.egov.util.ProjectUtil;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.egov.works.services.common.models.estimate.SMSRequest;
import org.egov.web.models.WorksSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.EstimateServiceConstant.*;

@Service
@Slf4j
public class NotificationService {

    private final EstimateProducer producer;

    private final ServiceRequestRepository repository;

    private final EstimateServiceConfiguration config;

    private final HRMSUtils hrmsUtils;

    private final ProjectUtil projectServiceUtil;

    private final LocationServiceUtil locationServiceUtil;

    private static final String PROJECT_NAME = "projectName";
    private static final String PROJECT_NUMBER = "projectNumber";

    @Autowired
    public NotificationService(EstimateProducer producer, ServiceRequestRepository repository, EstimateServiceConfiguration config, HRMSUtils hrmsUtils, ProjectUtil projectServiceUtil, LocationServiceUtil locationServiceUtil) {
        this.producer = producer;
        this.repository = repository;
        this.config = config;
        this.hrmsUtils = hrmsUtils;
        this.projectServiceUtil = projectServiceUtil;
        this.locationServiceUtil = locationServiceUtil;
    }


    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(EstimateRequest request) {

        if(config.getIsSMSEnabled()) {
            log.info("Notification is enabled for this service");

            Workflow workflow = request.getWorkflow();

            if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
                pushNotificationToCreatorForRejectAction(request);
            } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
                pushNotificationToCreatorForApproveAction(request);
            }
        }else{
            log.info("Notification is not enabled for this service");
        }
    }

    private void pushNotificationToCreatorForRejectAction(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        String createdByUuid = request.getEstimate().getAuditDetails().getCreatedBy();

        log.info("get message template for reject action");
        String message = getMessage(request);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField=new HashMap<>();
        if(config.isAdditonalFieldRequired()){
            setAdditionalFields(request,ESTIMATE_REJECT_LOCALIZATION_CODE,additionalField);
        }

        log.info("build Message For Reject Action");
        message = buildMessageForRejectAction(estimate, smsDetails, message);
        log.info("push message for REJECT Action");
        checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);

    }

    private void pushNotificationToCreatorForApproveAction(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        String createdByUuid = request.getEstimate().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for approve action");
        String message = getMessage(request);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField=new HashMap<>();
        if(config.isAdditonalFieldRequired()){
            setAdditionalFields(request,ESTIMATE_APPROVE_LOCALIZATION_CODE,additionalField);
        }
        log.info("build Message For Approve Action for Estimate Creator");
        message = buildMessageForApproveActionCreator(estimate, smsDetails, message);
        checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);
    }



    private Map<String, String> getDetailsForSMS(EstimateRequest request, String uuid) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        String tenantId = estimate.getTenantId();

        Map<String, String> smsDetails = new HashMap<>();


        //fetch the logged in user's mobileNumber, username, designation
        Map<String, String> userDetailsForSMS = hrmsUtils.
                getEmployeeDetailsByUuid(requestInfo, tenantId, uuid);


        // fetch project details - project name and location
        Map<String, String> projectDetails = getProjectDetails(request);


        //get location name from boundary type
        String boundaryCode = projectDetails.get("boundary");
        String boundaryType = projectDetails.get("boundaryType");
        Map<String, String> locationName = locationServiceUtil.getLocationName(tenantId, requestInfo, boundaryCode,boundaryType);

        smsDetails.putAll(userDetailsForSMS);
        smsDetails.putAll(projectDetails);
        smsDetails.putAll(locationName);

        return smsDetails;
    }

    private Map<String, String> getProjectDetails(EstimateRequest request) {
        Object projectRes = projectServiceUtil.getProjectDetails(request);

        Map<String, String> projectDetails = new HashMap<>();
        List<String> projectNumber = new ArrayList<>();
        List<String> projectNames = new ArrayList<>();
        List<String> boundaries = new ArrayList<>();
        List<String> boundaryTypes = new ArrayList<>();
        try {
            projectNumber = JsonPath.read(projectRes, PROJECT_ID_CODE);
            projectNames = JsonPath.read(projectRes, PROJECT_NAME_CODE);
            boundaries = JsonPath.read(projectRes, PROJECT_BOUNDARY_CODE);
            boundaryTypes = JsonPath.read(projectRes, PROJECT_BOUNDARY_TYPE_CODE);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        projectDetails.put(PROJECT_NAME, projectNames.get(0));
        projectDetails.put("boundary", boundaries.get(0));
        projectDetails.put("boundaryType", boundaryTypes.get(0));
        projectDetails.put(PROJECT_NUMBER, projectNumber.get(0));

        return projectDetails;
    }

    private String getMessage(EstimateRequest request) {
        Workflow workflow = request.getWorkflow();
        String message = null;

        if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
            message = getMessage(request, ESTIMATE_REJECT_LOCALIZATION_CODE);
        } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
            message = getMessage(request, ESTIMATE_APPROVE_LOCALIZATION_CODE);
        }

        return message;
    }

    /**
     * Gets the message from localization
     *
     * @param request
     * @param msgCode
     * @return
     */
    public String getMessage(EstimateRequest request, String msgCode) {
        String rootTenantId = request.getEstimate().getTenantId().split("\\.")[0];
        RequestInfo requestInfo = request.getRequestInfo();
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(requestInfo, rootTenantId,
                locale, EstimateServiceConstant.ESTIMATE_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

    /**
     * Builds msg based on the format
     *
     * @param estimate
     * @param message
     * @param userDetailsForSMS
     * @return
     */
    public String buildMessageForRejectAction(Estimate estimate, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{estimteno}", estimate.getEstimateNumber())
                .replace("{projectid}",userDetailsForSMS.get(PROJECT_NUMBER))
                .replace("{project_name}", userDetailsForSMS.get(PROJECT_NAME))
                .replace("{location}", userDetailsForSMS.get("locationName"))
                .replace("{username}", userDetailsForSMS.get("userName"))
                .replace("{designation}", userDetailsForSMS.get("designation"));
        return message;
    }

    public String buildMessageForApproveActionCreator(Estimate estimate, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{estimteno}", estimate.getEstimateNumber())
                .replace("{projectid}", userDetailsForSMS.get(PROJECT_NUMBER))
                .replace("{project_name}", userDetailsForSMS.get(PROJECT_NAME))
                .replace("{location}", userDetailsForSMS.get("locationName"));
        return message;
    }

    /**
     * Creates a cache for localization that gets refreshed at every call.
     *
     * @param requestInfo
     * @param rootTenantId
     * @param locale
     * @param module
     * @return
     */
    public Map<String, Map<String, String>> getLocalisedMessages(RequestInfo requestInfo, String rootTenantId, String locale, String module) {
        Map<String, Map<String, String>> localizedMessageMap = new HashMap<>();
        Map<String, String> mapOfCodesAndMessages = new HashMap<>();
        StringBuilder uri = new StringBuilder();
        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(requestInfo);
        uri.append(config.getLocalizationHost()).append(config.getLocalizationContextPath())
                .append(config.getLocalizationSearchEndpoint()).append("?tenantId=" + rootTenantId)
                .append("&module=" + module).append("&locale=" + locale);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = repository.fetchResult(uri, requestInfoWrapper);
            codes = JsonPath.read(result, EstimateServiceConstant.ESTIMATE_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, EstimateServiceConstant.ESTIMATE_LOCALIZATION_MSGS_JSONPATH);
        } catch (Exception e) {
            log.error("Exception while fetching from localization: " + e);
        }
        if (null != result) {
            for (int i = 0; i < codes.size(); i++) {
                mapOfCodesAndMessages.put(codes.get(i), messages.get(i));
            }
            localizedMessageMap.put(locale + "|" + rootTenantId, mapOfCodesAndMessages);
        }

        return localizedMessageMap;
    }
    private void setAdditionalFields(EstimateRequest request, String localizationCode, Map<String, Object> additionalField){
            additionalField.put("templateCode",localizationCode);
            additionalField.put("requestInfo",request.getRequestInfo());
            additionalField.put("tenantId",request.getEstimate().getTenantId());
    }

    private void checkAdditionalFieldAndPushONSmsTopic( String customizedMessage , Map<String, Object> additionalField,Map<String,String> smsDetails){


        if(!additionalField.isEmpty()){
            WorksSmsRequest smsRequest=WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message with Additonal Fields:::::" + smsRequest.toString());
            producer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without additional fields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotifTopic(), smsRequest);
        }
    }

}

