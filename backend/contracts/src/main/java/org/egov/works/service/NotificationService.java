package org.egov.works.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.kafka.ContractProducer;
import org.egov.works.repository.ServiceRequestRepository;
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

    @Autowired
    private ContractProducer contractProducer;

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

   @Autowired
   private OrgUtils organisationServiceUtil;
   @Autowired
   private ContractService contractService;

   private static final String SMS_NOT_FOUND = "SMS content has not been configured for this case";
   private static final String MOBILE_NUMBERS = "mobileNumbers";
   private static final String MOBILE_NUMBER = "mobileNumber";
   private static final String PROJECT_NUMBER = "projectNumber";
   private static final String FETCH_LOG_STATEMENT = "get project number, location, userDetails";
   private static final String PROJECT_ID = "projectId";
   private static final String PROJECT_ID_REPLACEMENT_STRING =  "{projectid}";
   private static final String WORK_ORDER_NO_REPLACEMENT_STRING = "{workorderno}";

    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(ContractRequest request) {
        Workflow workflow = request.getWorkflow();

        if (request.getContract().getBusinessService() != null
                && !request.getContract().getBusinessService().isEmpty()
                && request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_BUSINESS_SERVICE)) {
            pushNotificationForRevisedContract (request);

        }else {
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

        SMSRequest smsRequestCBO = SMSRequest.builder().mobileNumber(cboMobileNumber).message(message).build();
        log.info("Sending message to CBO");
        contractProducer.push(config.getSmsNotifTopic(), smsRequestCBO);

        if (Boolean.FALSE.equals(isSendBack)) {
            pushNotificationToOriginator(request, message);
        }

    }
    private void pushNotificationToOriginator (ContractRequest request, String message) {

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

        List<Contract> contractsFromDB = contractService.getContracts(contractCriteria);
        Contract originalContractFromDB = contractsFromDB.stream().filter(contract -> (contract.getBusinessService() != null && contract.getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_BUSINESS_SERVICE))).collect(Collectors.toList()).get(0);
        log.info("Getting officer-in-charge for contract :: " + originalContractFromDB.getContractNumber());
        String officerInChargeUuid = originalContractFromDB.getAuditDetails().getCreatedBy();
        Map<String,String> officerInChargeMobileNumberMap =hrmsUtils.getEmployeeDetailsByUuid(request.getRequestInfo(), request.getContract().getTenantId(),officerInChargeUuid);
        String officerInChargeMobileNumber = officerInChargeMobileNumberMap.get(MOBILE_NUMBER);

        SMSRequest smsRequestOfficerInCharge = SMSRequest.builder().mobileNumber(officerInChargeMobileNumber).message(message).build();
        log.info("Sending message to Officer In charge");
        contractProducer.push(config.getSmsNotifTopic(), smsRequestOfficerInCharge);

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
        log.info(FETCH_LOG_STATEMENT);
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For Reject Action");
        message = buildMessageForRejectAction(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get(MOBILE_NUMBER)).message(message).build();

        log.info("push message for REJECT Action");
        contractProducer.push(config.getSmsNotifTopic(), smsRequest);
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
        log.info(FETCH_LOG_STATEMENT);
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For Approve Action for WO Creator");
        message = buildMessageForWOCreator(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get(MOBILE_NUMBER)).message(message).build();

        log.info("push Message For Approve Action for WO Creator");
        contractProducer.push(config.getSmsNotifTopic(), smsRequest);
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
        log.info(FETCH_LOG_STATEMENT);
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For decline Action for WO Creator");
        message = buildMessageForWOCreator(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get(MOBILE_NUMBER)).message(message).build();

        log.info("push Message For decline Action for WO Creator");
        contractProducer.push(config.getSmsNotifTopic(), smsRequest);
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
        log.info(FETCH_LOG_STATEMENT);
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For Accept Action for WO Creator");
        message = buildMessageForWOCreator(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get(MOBILE_NUMBER)).message(message).build();

        log.info("push Message For Accept Action for WO Creator");
        contractProducer.push(config.getSmsNotifTopic(), smsRequest);
    }

    private void pushNotificationToCBOForApproveAction(ContractRequest request) {
        Contract contract = request.getContract();
        String message = getMessage(request, true);

        if (StringUtils.isEmpty(message)) {
            log.info(SMS_NOT_FOUND);
            return;
        }

        Map<String, List<String>> projDetails = getProjectName(request);

        for (int i = 0; i < projDetails.get(MOBILE_NUMBERS).size(); i++) {
            Map<String,String> smsDetails=new HashMap<>();

            smsDetails.put(PROJECT_ID,projDetails.get(PROJECT_NUMBER).get(0));
            smsDetails.put(MOBILE_NUMBER,projDetails.get(MOBILE_NUMBERS).get(i));


            String customizedMessage = buildMessageForApproveActionWOCBO(contract, smsDetails, message);
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get(MOBILE_NUMBER)).message(customizedMessage).build();
            contractProducer.push(config.getSmsNotifTopic(), smsRequest);
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

        smsDetails.put("orgName",orgDetails.get("orgName").get(0));
        smsDetails.putAll(userDetailsForSMS);
        smsDetails.put(PROJECT_ID,projectDetails.get(PROJECT_NUMBER));

        return smsDetails;
    }
    private Map<String, List<String>> getProjectName(ContractRequest request) {

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
        Map<String,List<String>> projectAndOrgDetails= organisationServiceUtil.getOrganisationInfo(request);

        projectAndOrgDetails.put(PROJECT_ID,Collections.singletonList(projectId));
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

        Map<String,List<String>> orgDetails=organisationServiceUtil.getOrganisationInfo(request);
        orgDetails.put("projectName", Collections.singletonList(projectDetails.get("projectName")));
        orgDetails.put(PROJECT_NUMBER, Collections.singletonList(projectDetails.get(PROJECT_NUMBER)));

        return orgDetails;
    }


    private String getMessage(ContractRequest request, boolean isCBORole) {

        Workflow workflow = request.getWorkflow();
        String message = null;

        if (request.getContract().getBusinessService() != null
                && !request.getContract().getBusinessService().isEmpty()
                && request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_BUSINESS_SERVICE)) {
            if (REJECT_ACTION.equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_REJECT_LOCALIZATION_CODE);
            } else if (APPROVE_ACTION.equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_APPROVE_LOCALIZATION_CODE);
            } else if ("SEND_BACK_TO_ORIGINATOR".equalsIgnoreCase(workflow.getAction()) || "SEND_BACK".equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_SEND_BACK_LOCALIZATION_CODE);
            }
        }else {

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
        if(request.getRequestInfo().getMsgId().split("\\|").length > 1)
            locale = request.getRequestInfo().getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(request.getRequestInfo(), request.getContract().getTenantId(),
                locale, ContractServiceConstants.CONTRACTS_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + request.getContract().getTenantId()).get(msgCode);
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

    /**
     * Creates a cache for localization that gets refreshed at every call.
     *
     * @param requestInfo
     * @param rootTenantId
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
            log.error("URL_SHORTENING_ERROR","Unable to shorten url: "+actualURL);
            return actualURL;
        }
        else return res;
    }

}
