package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.kafka.ContractProducer;
import org.egov.works.repository.ContractRepository;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.SMSRequest;
import org.egov.works.util.*;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ContractServiceConstants.*;

@Service
@Slf4j
public class NotificationService {

    private ContractProducer contractProducer;
    private ServiceRequestRepository repository;
    private RestTemplate restTemplate;
    private ContractServiceConfiguration config;
    private HRMSUtils hrmsUtils;
    private EstimateServiceUtil estimateServiceUtil;
    private ProjectServiceUtil projectServiceUtil;
    private LocationServiceUtil locationServiceUtil;
    private OrgUtils organisationServiceUtil;
    private final ContractRepository contractRepository;
    private static final String SMS_NOT_FOUND = "SMS content has not been configured for this case";
    private static final String MOBILE_NUMBERS = "mobileNumbers";
    private static final String MOBILE_NUMBER = "mobileNumber";
    private static final String PROJECT_NUMBER = "projectNumber";
    private static final String FETCH_LOG_STATEMENT = "get project number, location, userDetails";
    private static final String PROJECT_ID = "projectId";
    private static final String PROJECT_ID_REPLACEMENT_STRING = "{projectid}";
    private static final String WORK_ORDER_NO_REPLACEMENT_STRING = "{workorderno}";



    @Autowired
    public NotificationService(ContractRepository contractRepository, OrgUtils organisationServiceUtil, LocationServiceUtil locationServiceUtil, ProjectServiceUtil projectServiceUtil, EstimateServiceUtil estimateServiceUtil, HRMSUtils hrmsUtils, ContractServiceConfiguration config, RestTemplate restTemplate, ServiceRequestRepository repository, ContractProducer contractProducer) {
        this.contractRepository = contractRepository;
        this.organisationServiceUtil = organisationServiceUtil;
        this.locationServiceUtil = locationServiceUtil;
        this.projectServiceUtil = projectServiceUtil;
        this.estimateServiceUtil = estimateServiceUtil;
        this.hrmsUtils = hrmsUtils;
        this.config = config;
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.contractProducer = contractProducer;
    }

    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(ContractRequest request) {

        if (config.getIsSMSEnabled()) {
            log.info("Notification is enabled for this service");

            Workflow workflow = request.getWorkflow();

            if (request.getContract().getBusinessService() != null
                    && !request.getContract().getBusinessService().isEmpty()
                    && request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)) {
                pushNotificationForRevisedContract(request);

            } else {
                if (REJECT_ACTION.equalsIgnoreCase(workflow.getAction())) {
                    pushNotificationToCreatorForRejectAction(request);
                } else if (APPROVE_ACTION.equalsIgnoreCase(workflow.getAction())) {
                    //No template present for Creator Approve Action
                    pushNotificationToCreatorForApproveAction(request);
                    pushNotificationToCBOForApproveAction(request);
                } else if (ACCEPT_ACTION.equalsIgnoreCase(workflow.getAction())) {
                    pushNotificationToCreatorForAcceptAction(request);
                } else if ("DECLINE".equalsIgnoreCase(workflow.getAction())) {
                    pushNotificationToCreatorForDeclineAction(request);
                }
            }
        } else {
            log.info("Notification is not enabled for this service");
        }

    }

    private void pushNotificationForRevisedContract(ContractRequest request) {
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }
        Map<String, List<String>> orgDetails = getOrgDetailsForCBOAdmin(request);
        String cboMobileNumber = orgDetails.get(MOBILE_NUMBERS).get(0);
        log.info("Fetched CBO contact details and project ID :: " + orgDetails.get(PROJECT_NUMBER).get(0));

        Map<String, String> smsDetailsMap = new HashMap<>();
        smsDetailsMap.put(PROJECT_NUMBER, orgDetails.get(PROJECT_NUMBER).get(0));
        smsDetailsMap.put("supplementNumber", request.getContract().getSupplementNumber());

        Boolean isSendBack = (request.getWorkflow().getAction().equalsIgnoreCase("SEND_BACK") || request.getWorkflow().getAction().equalsIgnoreCase("SEND_BACK_TO_ORIGINATOR"));
        message = buildMessageForRevisedContract(smsDetailsMap, message, isSendBack);

        smsDetailsMap.put("mobileNumber", cboMobileNumber);

        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, ContractServiceConstants.CONTRACT_REVISION_SEND_BACK_LOCALIZATION_CODE, additionalField);
        }

        if (isSendBack) {
            log.info("Sending message to CBO");
            checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetailsMap);
        } else {
            log.info("Sending message to Originator:");
            pushNotificationToOriginator(request, message, cboMobileNumber);
        }

    }

    private void pushNotificationToOriginator(ContractRequest request, String message, String cboMobileNumber) {

        Pagination pagination = Pagination.builder()
                .limit(config.getContractMaxLimit())
                .offSet(config.getContractDefaultOffset())
                .build();
        ContractCriteria contractCriteria = ContractCriteria.builder()
                .contractNumber(request.getContract().getContractNumber())
                .tenantId(request.getContract().getTenantId())
                .requestInfo(request.getRequestInfo())
                .pagination(pagination)
                .build();

        List<Contract> contractsFromDB = contractRepository.getContracts(contractCriteria);
        Contract originalContractFromDB = contractsFromDB.stream().filter(contract -> (contract.getBusinessService() != null && contract.getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE))).collect(Collectors.toList()).get(0);
        log.info("Getting officer-in-charge for contract :: " + originalContractFromDB.getContractNumber());
        ObjectMapper objectMapper = new ObjectMapper();
        String officerInchargeCode = null;
        try {
            Map<String, Object> addtionalDetailsMap = objectMapper.convertValue(originalContractFromDB.getAdditionalDetails(), Map.class);
            if (addtionalDetailsMap.containsKey("officerInChargeName")) {
                Map<String, String> officerInChargeNameMap = (Map<String, String>) addtionalDetailsMap.get("officerInChargeName");
                officerInchargeCode = officerInChargeNameMap.get("code");
            }
        } catch (Exception e) {
            throw new CustomException("OFFICER_INCHARGE_NOT_FOUND", "Failed tp fetch officerInCharge details");
        }
        Map<String, String> officerInChargeMobileNumberMap = hrmsUtils.getEmployeeDetailsByCode(request.getRequestInfo(), request.getContract().getTenantId(), officerInchargeCode);
        String officerInChargeMobileNumber = officerInChargeMobileNumberMap.get(MOBILE_NUMBER);
        Map<String, String> smsDetailsMap = new HashMap<>();

        smsDetailsMap.put("mobileNumber", officerInChargeMobileNumber);
        smsDetailsMap.put("cboMobileNumber", cboMobileNumber);
        Workflow workflow = request.getWorkflow();
        String message1 = null;


        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
                setAdditionalFields(request, ContractServiceConstants.CONTRACT_REVISION_REJECT_LOCALIZATION_CODE, additionalField);
            } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
                setAdditionalFields(request, ContractServiceConstants.CONTRACT_REVISION_APPROVE_LOCALIZATION_CODE, additionalField);
            }

        }
        log.info("Sending message to Officer In charge");
        checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetailsMap);

    }

    private void pushNotificationToCreatorForRejectAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template for reject action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, ContractServiceConstants.CONTRACTS_REJECT_LOCALIZATION_CODE, additionalField);
        }


        log.info("build Message For Reject Action");
        message = buildMessageForRejectAction(contract, smsDetails, message);
        log.info("push message for REJECT Action");
        checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);

    }

    private void pushNotificationToCreatorForApproveAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for approve action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, ContractServiceConstants.CONTRACTS_APPROVE_CREATOR_LOCALIZATION_CODE, additionalField);
        }
        log.info("build Message For Approve Action for WO Creator");
        message = buildMessageForWOCreator(contract, smsDetails, message);

        log.info("push Message For Approve Action for WO Creator");
        checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);

    }

    private void pushNotificationToCreatorForDeclineAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for decline action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, ContractServiceConstants.CONTRACTS_DECLINE_CREATOR_LOCALIZATION_CODE, additionalField);
        }
        log.info("build Message For decline Action for WO Creator");
        message = buildMessageForDeclineAction_WOCreator(contract, smsDetails, message);
        log.info("push Message For decline Action for WO Creator");
        checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);


    }

    private void pushNotificationToCreatorForAcceptAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for Accept action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, ContractServiceConstants.CONTRACTS_ACCEPT_CREATOR_LOCALIZATION_CODE, additionalField);
        }
        log.info("build Message For Accept Action for WO Creator");
        message = buildMessageForAcceptAction_WOCreator(contract, smsDetails, message);
        log.info("push Message For Accept Action for WO Creator");
        checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);
    }

    private void pushNotificationToCBOForApproveAction(ContractRequest request) {
        Contract contract = request.getContract();
        String message = getMessage(request, true);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }

        //get org-details: orgName, contactPersonNames, mobileNumbers
//        Map<String, List<String>> orgDetails = getOrgDetailsForCBOAdmin(request);

        Map<String, List<String>> projDetails = getProjectNumber(request);
        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, ContractServiceConstants.CONTRACTS_APPROVE_CBO_LOCALIZATION_CODE, additionalField);
        }
        for (int i = 0; i < projDetails.get(MOBILE_NUMBERS).size(); i++) {
            Map<String, String> smsDetails = new HashMap<>();
            smsDetails.put(PROJECT_ID, projDetails.get(PROJECT_NUMBER).get(0));
            smsDetails.put(MOBILE_NUMBER, projDetails.get(MOBILE_NUMBERS).get(i));
            String customizedMessage = buildMessageForApproveActionWOCBO(contract, smsDetails, message);
            checkAdditionalFieldAndPushONSmsTopic(customizedMessage, additionalField, smsDetails);

        }
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

        //get org name
        Map<String, List<String>> orgDetails = organisationServiceUtil.getOrganisationInfo(request);

        smsDetails.put("orgName", orgDetails.get("orgName").get(0));
        smsDetails.putAll(userDetailsForSMS);
        smsDetails.put(PROJECT_ID, projectDetails.get(PROJECT_NUMBER));

       /* smsDetails.putAll(projectDetails);
        smsDetails.putAll(locationName);*/

        return smsDetails;
    }

    private Map<String, List<String>> getProjectNumber(ContractRequest request) {

        RequestInfo requestInfo = request.getRequestInfo();
        Contract contract = request.getContract();
        String tenantId = contract.getTenantId();

        // fetch project details - project name and location
        List<LineItems> lineItems = request.getContract().getLineItems();
        Map<String, List<LineItems>> lineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        List<Estimate> estimates = estimateServiceUtil.fetchActiveEstimates(requestInfo, tenantId, lineItemsMap.keySet());

        //As the new template only requires the project id so fetching it in this class only rather than calling the util method
        String projectId = estimates.get(0).getProjectId();
        Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(requestInfo, estimates.get(0));

        // Fetching org mobile number and maintaining in the map
        Map<String, List<String>> projectAndOrgDetails = organisationServiceUtil.getOrganisationInfo(request);

        projectAndOrgDetails.put(PROJECT_ID, Collections.singletonList(projectId));
        projectAndOrgDetails.put(PROJECT_NUMBER, Collections.singletonList(projectDetails.get(PROJECT_NUMBER)));

        return projectAndOrgDetails;
    }

    private Map<String, List<String>> getOrgDetailsForCBOAdmin(ContractRequest request) {

        RequestInfo requestInfo = request.getRequestInfo();
        Contract contract = request.getContract();
        String tenantId = contract.getTenantId();

        // fetch project details - project name and location
        List<LineItems> lineItems = request.getContract().getLineItems();
        Map<String, List<LineItems>> lineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        List<Estimate> estimates = estimateServiceUtil.fetchActiveEstimates(requestInfo, tenantId, lineItemsMap.keySet());
        Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(requestInfo, estimates.get(0));

        Map<String, List<String>> orgDetails = organisationServiceUtil.getOrganisationInfo(request);
        orgDetails.put("projectName", Collections.singletonList(projectDetails.get("projectName")));
        orgDetails.put(PROJECT_NUMBER, Collections.singletonList(projectDetails.get(PROJECT_NUMBER)));

        return orgDetails;
    }


    private String getMessage(ContractRequest request, boolean isCBORole) {

        Workflow workflow = request.getWorkflow();
        String message = null;

        if (request.getContract().getBusinessService() != null
                && !request.getContract().getBusinessService().isEmpty()
                && request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)) {
            if (REJECT_ACTION.equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_REJECT_LOCALIZATION_CODE);
            } else if (APPROVE_ACTION.equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_APPROVE_LOCALIZATION_CODE);
            } else if ("SEND_BACK_TO_ORIGINATOR".equalsIgnoreCase(workflow.getAction()) || "SEND_BACK".equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_SEND_BACK_LOCALIZATION_CODE);
            }
        } else {

            if (REJECT_ACTION.equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_REJECT_LOCALIZATION_CODE);
            } else if (APPROVE_ACTION.equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_APPROVE_CREATOR_LOCALIZATION_CODE);
            } else if (APPROVE_ACTION.equalsIgnoreCase(workflow.getAction()) && isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_APPROVE_CBO_LOCALIZATION_CODE);
            } else if (ACCEPT_ACTION.equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_ACCEPT_CREATOR_LOCALIZATION_CODE);
            } else if ("DECLINE".equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_DECLINE_CREATOR_LOCALIZATION_CODE);
            }
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
        String locale = "en_IN";
        String rootTenantId = request.getContract().getTenantId().split("\\.")[0];
        if (request.getRequestInfo().getMsgId().split("\\|").length > 1)
            locale = request.getRequestInfo().getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(request.getRequestInfo(), rootTenantId,
                locale, ContractServiceConstants.CONTRACTS_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

    private String buildMessageForRevisedContract(Map<String, String> userDetailsForSMS, String message, Boolean isSendBack) {
        if (Boolean.FALSE.equals(isSendBack)) {
            message = message.replace(PROJECT_ID_REPLACEMENT_STRING, userDetailsForSMS.get(PROJECT_NUMBER));
        }
        message = message.replace("{timeextensionrequestid}", userDetailsForSMS.get("supplementNumber"));
        return message;
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
        message = message.replace("{workorderno} ", contract.getContractNumber())
                .replace(PROJECT_ID_REPLACEMENT_STRING, userDetailsForSMS.get(PROJECT_ID));
        return message;
    }

    public String buildMessageForApproveActionWOCBO(Contract contract, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace(PROJECT_ID_REPLACEMENT_STRING, userDetailsForSMS.get(PROJECT_ID))
                .replace("{cborole}", contract.getExecutingAuthority());
        return message;
    }

    public String buildMessageForWOCreator(Contract contract, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace(WORK_ORDER_NO_REPLACEMENT_STRING, contract.getContractNumber())
                .replace(PROJECT_ID_REPLACEMENT_STRING, userDetailsForSMS.get(PROJECT_ID));
        return message;
    }

    public String buildMessageForDeclineAction_WOCreator(Contract contract, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{workorderno}", contract.getContractNumber())
                .replace("{projectid}", userDetailsForSMS.get("projectId"));
        return message;
    }

    public String buildMessageForAcceptAction_WOCreator(Contract contract, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{workorderno}", contract.getContractNumber())
                .replace("{projectid}", userDetailsForSMS.get("projectId"));
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
     * @param actualURL Actual URL
     * @return Shortened URL
     */
    public String getShortnerURL(String actualURL) {
        HashMap<String, String> body = new HashMap<>();
        body.put("url", actualURL);
        StringBuilder builder = new StringBuilder(config.getUrlShortnerHost());
        builder.append(config.getUrlShortnerEndpoint());
        String res = restTemplate.postForObject(builder.toString(), body, String.class);

        if (StringUtils.isEmpty(res)) {
            log.error("URL_SHORTENING_ERROR", "Unable to shorten url: " + actualURL);
            return actualURL;
        } else return res;
    }

    private void setAdditionalFields(ContractRequest request, String localizationCode, Map<String, Object> additionalField) {
        additionalField.put("templateCode", localizationCode);
        additionalField.put("requestInfo", request.getRequestInfo());
        additionalField.put("tenantId", request.getContract().getTenantId());

    }

    private void checkAdditionalFieldAndPushONSmsTopic(String customizedMessage, Map<String, Object> additionalField, Map<String, String> smsDetails) {


        if (!additionalField.isEmpty()) {
            WorksSmsRequest smsRequest = new WorksSmsRequest();
            if (null != smsDetails.get("cboMobileNumber")) {
                smsRequest = WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                        .mobileNumber(smsDetails.get("cboMobileNumber")).build();
                log.info("SMS message with additonal fields to CBO:::::" + smsRequest.toString());
                contractProducer.push(config.getMuktaNotificationTopic(), smsRequest);
            }
            smsRequest = WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message with additonal fields:::::" + smsRequest.toString());
            contractProducer.push(config.getMuktaNotificationTopic(), smsRequest);

        } else {
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without Additonal Fields:::::" + smsRequest.toString());
            contractProducer.push(config.getSmsNotifTopic(), smsRequest);
        }
    }


}
