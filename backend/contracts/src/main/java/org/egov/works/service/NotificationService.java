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

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ContractServiceConstants.CONTRACT_REVISION_BUSINESS_SERVICE;

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

   @Autowired
   private OrgUtils organisationServiceUtil;
   @Autowired
   private ContractService contractService;


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
            if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
                pushNotificationToCreatorForRejectAction(request);
            } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
                //No template present for Creator Approve Action
                pushNotificationToCreatorForApproveAction(request);
                pushNotificationToCBOForApproveAction(request);
            } else if ("ACCEPT".equalsIgnoreCase(workflow.getAction())) {
                pushNotificationToCreatorForAcceptAction(request);
            } else if ("DECLINE".equalsIgnoreCase(workflow.getAction())) {
                pushNotificationToCreatorForDeclineAction(request);
            }
        }

    }
    private void pushNotificationForRevisedContract(ContractRequest request) {
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }
        Map<String, List<String>> orgDetails = getOrgDetailsForCBOAdmin(request);
        String cboMobileNumber = orgDetails.get("mobileNumbers").get(0);
        log.info("Fetched CBO contact details and project ID :: " + orgDetails.get("projectNumber").get(0));

        Map<String, String> smsDetailsMap = new HashMap<>();
        smsDetailsMap.put("projectNumber", orgDetails.get("projectNumber").get(0));
        smsDetailsMap.put("supplementNumber", request.getContract().getSupplementNumber());

        Boolean isSendBack = (request.getWorkflow().getAction().equalsIgnoreCase("SEND_BACK") || request.getWorkflow().getAction().equalsIgnoreCase("SEND_BACK_TO_ORIGINATOR"));
        message = buildMessageForRevisedContract(smsDetailsMap, message, isSendBack);

        SMSRequest smsRequestCBO = SMSRequest.builder().mobileNumber(cboMobileNumber).message(message).build();
        log.info("Sending message to CBO");
        producer.push(config.getSmsNotifTopic(), smsRequestCBO);

        if (!isSendBack) {
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
        String officerInChargeMobileNumber = officerInChargeMobileNumberMap.get("mobileNumber");

        SMSRequest smsRequestOfficerInCharge = SMSRequest.builder().mobileNumber(officerInChargeMobileNumber).message(message).build();
        log.info("Sending message to Officer In charge");
        producer.push(config.getSmsNotifTopic(), smsRequestOfficerInCharge);

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

    private void pushNotificationToCreatorForDeclineAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for decline action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For decline Action for WO Creator");
        message = buildMessageForDeclineAction_WOCreator(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(message).build();

        log.info("push Message For decline Action for WO Creator");
        producer.push(config.getSmsNotifTopic(), smsRequest);
    }

    private void pushNotificationToCreatorForAcceptAction(ContractRequest request) {
        Contract contract = request.getContract();
        String createdByUuid = request.getContract().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for Accept action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);

        log.info("build Message For Accept Action for WO Creator");
        message = buildMessageForAcceptAction_WOCreator(contract, smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(message).build();

        log.info("push Message For Accept Action for WO Creator");
        producer.push(config.getSmsNotifTopic(), smsRequest);
    }

    private void pushNotificationToCBOForApproveAction(ContractRequest request) {
        Contract contract = request.getContract();
        String message = getMessage(request, true);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get org-details: orgName, contactPersonNames, mobileNumbers
//        Map<String, List<String>> orgDetails = getOrgDetailsForCBOAdmin(request);

        Map<String, List<String>> projDetails = getProjectName(request);

        for (int i = 0; i < projDetails.get("mobileNumbers").size(); i++) {
            Map<String,String> smsDetails=new HashMap<>();

            smsDetails.put("projectId",projDetails.get("projectNumber").get(0));
//            smsDetails.put("projectName",orgDetails.get("projectName").get(0));
//            smsDetails.put("personName",orgDetails.get("contactPersonNames").get(i));
            smsDetails.put("mobileNumber",projDetails.get("mobileNumbers").get(i));


            String customizedMessage = buildMessageForApproveAction_WO_CBO(contract, smsDetails, message);
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            producer.push(config.getSmsNotifTopic(), smsRequest);
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
//        Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(requestInfo, estimates.get(0));

        //As the new template only requires the project id so fetching it in this class only rather than calling the util method
//        String projectId = estimates.get(0).getProjectId();Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(requestInfo, estimates.get(0));

        Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(requestInfo, estimates.get(0));
        //get location name from boundary type
/*        String boundaryCode = projectDetails.get("boundary");
        String boundaryType=projectDetails.get("boundaryType");
        Map<String, String> locationName = locationServiceUtil.getLocationName(tenantId, requestInfo, boundaryCode, boundaryType);*/

        //get org name
        Map<String, List<String>> orgDetails = organisationServiceUtil.getOrganisationInfo(request);

        smsDetails.put("orgName",orgDetails.get("orgName").get(0));
        smsDetails.putAll(userDetailsForSMS);
        smsDetails.put("projectId",projectDetails.get("projectNumber"));

       /* smsDetails.putAll(projectDetails);
        smsDetails.putAll(locationName);*/

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

//        orgDetails.put("projectName", Collections.singletonList(projectDetails.get("projectName")));

        projectAndOrgDetails.put("projectId",Collections.singletonList(projectId));
        projectAndOrgDetails.put("projectNumber", Collections.singletonList(projectDetails.get("projectNumber")));

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
        orgDetails.put("projectNumber", Collections.singletonList(projectDetails.get("projectNumber")));

        return orgDetails;
    }


    private String getMessage(ContractRequest request, boolean isCBORole) {

        Workflow workflow = request.getWorkflow();
        String message = null;

        if (request.getContract().getBusinessService() != null
                && !request.getContract().getBusinessService().isEmpty()
                && request.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_BUSINESS_SERVICE)) {
            if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_REJECT_LOCALIZATION_CODE);
            } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_APPROVE_LOCALIZATION_CODE);
            } else if ("SEND_BACK_TO_ORIGINATOR".equalsIgnoreCase(workflow.getAction()) || "SEND_BACK".equalsIgnoreCase(workflow.getAction())) {
                message = getMessage(request, ContractServiceConstants.CONTRACT_REVISION_SEND_BACK_LOCALIZATION_CODE);
            }
        }else {

            if ("REJECT".equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_REJECT_LOCALIZATION_CODE);
            } else if ("APPROVE".equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_APPROVE_CREATOR_LOCALIZATION_CODE);
            } else if ("APPROVE".equalsIgnoreCase(workflow.getAction()) && isCBORole) {
                message = getMessage(request, ContractServiceConstants.CONTRACTS_APPROVE_CBO_LOCALIZATION_CODE);
            } else if ("ACCEPT".equalsIgnoreCase(workflow.getAction()) && !isCBORole) {
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
//        String rootTenantId = request.getContract().getTenantId().split("\\.")[0];
        String locale = "en_IN";
        if(request.getRequestInfo().getMsgId().split("\\|").length > 1)
            locale = request.getRequestInfo().getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(request.getRequestInfo(), request.getContract().getTenantId(),
                locale, ContractServiceConstants.CONTRACTS_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + request.getContract().getTenantId()).get(msgCode);
    }
    private String buildMessageForRevisedContract(Map<String, String> userDetailsForSMS, String message, Boolean isSendBack) {
        if (!isSendBack) {
            message = message.replace("{projectid}", userDetailsForSMS.get("projectNumber"));
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
        /*message = message.replace("{CONTRACT_NUMBER}", contract.getContractNumber())
                .replace("{PROJECT_NAME}", userDetailsForSMS.get("projectName"))
                .replace("{LOCATION}", userDetailsForSMS.get("locationName"))
                .replace("{USERNAME}", userDetailsForSMS.get("userName"))
                .replace("{DESIGNATION}", userDetailsForSMS.get("designation"));*/
        message = message.replace("{workorderno} ", contract.getContractNumber())
                .replace("{projectid}", userDetailsForSMS.get("projectId"));
        return message;
    }

    public String buildMessageForApproveAction_WO_CBO(Contract contract, Map<String, String> userDetailsForSMS, String message) {

        /* long dueDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(new Integer(config.getContractDueDatePeriod()));

       StringBuilder CBOUrl= new StringBuilder(config.getCboUrlHost()).append(config.getCboUrlEndpoint());
       String shortendURL = getShortnerURL(CBOUrl.toString());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dueDate);
        String date = formatter.format(calendar.getTime());*/

        message = message.replace("{projectid}", userDetailsForSMS.get("projectId"))
                .replace("{cborole}", contract.getExecutingAuthority());
        return message;
    }

    public String buildMessageForApproveAction_WOCreator(Contract contract, Map<String, String> userDetailsForSMS, String message) {
//        message = message.replace("{CONTRACT_NUMBER}", contract.getContractNumber())
//                .replace("{PROJECT_NAME}", userDetailsForSMS.get("projectName"))
//                .replace("{LOCATION}", userDetailsForSMS.get("locationName"))
//                .replace("{organisationName}", userDetailsForSMS.get("orgName"));
        message = message.replace("{workorderno}", contract.getContractNumber())
                .replace("{projectid}", userDetailsForSMS.get("projectId"));
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
            log.error("URL_SHORTENING_ERROR","Unable to shorten url: "+actualURL); ;
            return actualURL;
        }
        else return res;
    }

}
