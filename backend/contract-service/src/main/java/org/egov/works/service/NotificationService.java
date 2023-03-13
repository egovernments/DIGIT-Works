package org.egov.works.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.*;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private Producer producer;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ContractServiceConfiguration config;

    @Autowired
    private HRMSUtils hrmsUtils;

    @Autowired
    private EstimateServiceUtil estimateServiceUtil;

    @Autowired
    private ProjectServiceUtil projectServiceUtil;

    @Autowired
    private LocationServiceUtil locationServiceUtil;


    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(ContractRequest request) {
        Workflow workflow = request.getWorkflow();

        if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
            pushNotificationToCreatorForRejectAction(request);
        } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
            pushNotificationToCreatorForApproveAction(request);
            pushNotificationToCBOForApproveAction(request);
        }

    }

    private void pushNotificationToCreatorForRejectAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template for reject action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For Reject Action");
        message = buildMessageForRejectAction(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(message).build();

        log.info("push message for REJECT Action");
        producer.push(config.getSmsNotifTopic(), smsRequest);
    }

    private void pushNotificationToCreatorForApproveAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for approve action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For Approve Action for WO Creator");
        message = buildMessageForApproveAction_WOCreator(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(message).build();

        log.info("push Message For Approve Action for WO Creator");
        producer.push(config.getSmsNotifTopic(), smsRequest);
    }

    private void pushNotificationToCBOForApproveAction(ContractRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Contract contract = request.getContract();
        String tenantId = contract.getTenantId();

        //TODO Get UUID and user details (cboName, contractPersonName, phoneNumber) of CBO implementation -- needs to hit CBO service instead
        String CBOMemberUuid = request.getContract().getAuditDetails().getCreatedBy();

        String message = getMessage(request, true);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails -- needs to be changed for CBO-org message
        Map<String, String> smsDetails = getDetailsForSMS(request, CBOMemberUuid);

        message = buildMessageForApproveAction_WO_CBO(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(message).build();
        producer.push(config.getSmsNotifTopic(), smsRequest);
    }

    private Map<String, String> getDetailsForSMS(ContractRequest request, String uuid) {
        RequestInfo requestInfo = request.getRequestInfo();
        Contract contract = request.getContract();
        String tenantId = contract.getTenantId();

        Map<String, String> smsDetails = new HashMap<>();


        //fetch the logged in user's mobileNumber, username, designation
        Map<String, String> userDetailsForSMS = hrmsUtils.
                getEmployeeDetailsByUuid(requestInfo, tenantId, uuid);


        // fetch project details - project name and location
        List<LineItems> lineItems = request.getContract().getLineItems();
        Map<String, List<LineItems>> lineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        List<Estimate> estimates = estimateServiceUtil.fetchActiveEstimates(requestInfo, tenantId, lineItemsMap.keySet());
        Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(requestInfo, estimates.get(0));

        //get location name from boundary type
        String boundaryCode = projectDetails.get("boundary");
        Map<String, String> locationName = locationServiceUtil.getLocationName(tenantId, requestInfo, boundaryCode);

        smsDetails.putAll(userDetailsForSMS);
        smsDetails.putAll(projectDetails);
        smsDetails.putAll(locationName);

        return smsDetails;
    }

    private String getMessage(ContractRequest request, boolean isCBORole) {
        //RETHINK-- NEED TO SEND TWO MESSAGES
        Workflow workflow = request.getWorkflow();
        String message = null;

        if ("REJECT".equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
            message = getMessage(request, ContractServiceConstants.CONTRACTS_REJECT_LOCALIZATION_CODE);
        } else if ("APPROVE".equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
            message = getMessage(request, ContractServiceConstants.CONTRACTS_APPROVE_CREATOR_LOCALIZATION_CODE);
        } else if ("APPROVE".equalsIgnoreCase(workflow.getAction()) && isCBORole) {
            message = getMessage(request, ContractServiceConstants.CONTRACTS_APPROVE_CBO_LOCALIZATION_CODE);

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
    public String getMessage(ContractRequest request, String msgCode) {
        String tenantId = request.getContract().getTenantId().split("\\.")[0];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(request.getRequestInfo(), tenantId,
                ContractServiceConstants.CONTRACTS_NOTIFICATION_ENG_LOCALE_CODE, ContractServiceConstants.CONTRACTS_MODULE_CODE);
        return localizedMessageMap.get(ContractServiceConstants.CONTRACTS_NOTIFICATION_ENG_LOCALE_CODE + "|" + tenantId).get(msgCode);
    }

    /**
     * Builds msg based on the format
     *
     * @param contract
     * @param message
     * @param userDetailsForSMS
     * @return
     */
    public String buildMessageForRejectAction(Contract contract, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{CONTRACT_NUMBER}", contract.getContractNumber())
                .replace("{PROJECT_NAME}", userDetailsForSMS.get("projectName"))
                .replace("{LOCATION}", userDetailsForSMS.get("locationName"))
                .replace("{USERNAME}", userDetailsForSMS.get("userName"))
                .replace("{DESIGNATION}", userDetailsForSMS.get("designation"));
        return message;
    }

    public String buildMessageForApproveAction_WO_CBO(Contract contract, Map<String, String> userDetailsForSMS, String message) {

        long dueDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);

        StringBuilder CBOUrl= new StringBuilder(config.getCboUrlHost()).append(config.getCboUrlEndpoint());
        String shortendURL = getShortnerURL(CBOUrl.toString());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dueDate);
        String date = formatter.format(calendar.getTime());

        message = message.replace("{contactPersonName}", "CBO-contactPersonName-placeholder")
                .replace("{organisationName}", "CBO-organisationName-placeholder")
                .replace("{IA/IP}", contract.getExecutingAuthority())
                .replace("{PROJECT_NAME}", userDetailsForSMS.get("projectName"))
                .replace("{CONTRACT_NUMBER}", contract.getContractNumber())
                .replace("{dueDate}", formatter.format(calendar.getTime()))
                .replace("{Organization_Login_URL}", shortendURL);
        return message;
    }

    public String buildMessageForApproveAction_WOCreator(Contract contract, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{CONTRACT_NUMBER}", contract.getContractNumber())
                .replace("{PROJECT_NAME}", userDetailsForSMS.get("projectName"))
                .replace("{LOCATION}", userDetailsForSMS.get("locationName"))
                .replace("{organisationName}", "organisationName-PLACEHOLDER");
        return message;
    }

    /**
     * Creates a cache for localization that gets refreshed at every call.
     *
     * @param requestInfo
     * @param tenantId
     * @param locale
     * @param module
     * @return
     */
    public Map<String, Map<String, String>> getLocalisedMessages(RequestInfo requestInfo, String tenantId, String locale, String module) {
        Map<String, Map<String, String>> localizedMessageMap = new HashMap<>();
        Map<String, String> mapOfCodesAndMessages = new HashMap<>();
        StringBuilder uri = new StringBuilder();
        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(requestInfo);
        tenantId = tenantId.split("\\.")[0];
        uri.append(config.getLocalizationHost()).append(config.getLocalizationContextPath())
                .append(config.getLocalizationSearchEndpoint()).append("?tenantId=" + tenantId)
                .append("&module=" + module).append("&locale=" + locale);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = repository.fetchResult(uri, requestInfoWrapper);
            codes = JsonPath.read(result, ContractServiceConstants.CONTRACTS_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, ContractServiceConstants.CONTRACTS_LOCALIZATION_MSGS_JSONPATH);
        } catch (Exception e) {
            log.error("Exception while fetching from localization: " + e);
        }
        if (null != result) {
            for (int i = 0; i < codes.size(); i++) {
                mapOfCodesAndMessages.put(codes.get(i), messages.get(i));
            }
            localizedMessageMap.put(locale + "|" + tenantId, mapOfCodesAndMessages);
        }

        return localizedMessageMap;
    }

    /**
     *
     * @param actualURL Actual URL
     * @return Shortened URL
     */
    public String getShortnerURL(String actualURL) {
        HashMap<String,String> body = new HashMap<>();
        body.put("url",actualURL);
        StringBuilder builder = new StringBuilder(config.getUrlShortnerHost());
        builder.append(config.getUrlShortnerEndpoint());
        String res = restTemplate.postForObject(builder.toString(), body, String.class);

        if(StringUtils.isEmpty(res)){
            log.error("URL_SHORTENING_ERROR","Unable to shorten url: "+actualURL); ;
            return actualURL;
        }
        else return res;
    }

}
