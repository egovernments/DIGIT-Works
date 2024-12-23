package org.egov.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.kafka.OrganizationProducer;
import org.egov.repository.OrganisationRepository;
import org.egov.repository.ServiceRequestRepository;
import org.egov.util.OrganisationConstant;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.egov.util.OrganisationConstant.ORGANISATION_CREATE_LOCALIZATION_CODE;
import static org.egov.util.OrganisationConstant.ORGANISATION_UPDATE_LOCALIZATION_CODE;

@Service
@Slf4j
public class NotificationService {

    private final OrganizationProducer organizationProducer;

    private final ServiceRequestRepository repository;

    private final RestTemplate restTemplate;

    private final Configuration config;

    private final OrganisationRepository organisationRepository;
    private static final String PERSON_NAMES = "personNames";
    private static final String ORG_NAME = "orgName";

    @Autowired
    public NotificationService(OrganizationProducer organizationProducer, ServiceRequestRepository repository, RestTemplate restTemplate, Configuration config, OrganisationRepository organisationRepository) {
        this.organizationProducer = organizationProducer;
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.config = config;
        this.organisationRepository = organisationRepository;
    }

    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(OrgRequest request, boolean isCreateOperation) {

        if (isCreateOperation) {
            pushNotificationForCreate(request);
        } else {
            pushNotificationForUpdate(request);
        }
    }

    private void pushNotificationForCreate(OrgRequest request) {
        List<Organisation> organisations = request.getOrganisations();

        log.info("get message template for create action");
        String message = getMessage(request, true);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        for (Organisation organisation : organisations) {

            //get orgName, ID, contactPerson, mobileNumber, cbo-url
            log.info("get orgName, ID, contactPerson, mobileNumber, cbo-url");
            Map<String, List<String>> orgDetails = getDetailsForSMS(organisation);

            for (int i = 0; i < orgDetails.get(PERSON_NAMES).size(); i++) {

                Map<String, String> smsDetails = new HashMap<>();

                smsDetails.put(ORG_NAME, orgDetails.get("orgNames").get(0));
                smsDetails.put("personName", orgDetails.get(PERSON_NAMES).get(i));
                smsDetails.put("mobileNumber", orgDetails.get("mobileNumbers").get(i));
                smsDetails.put("orgId", organisation.getOrgNumber());


                log.info("build Message For create Action for " + smsDetails.get(ORG_NAME));
                String customizedMessage = buildMessageForCreateAction(smsDetails, message);
                SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();

                log.info("push message for create Action");
                organizationProducer.push(config.getSmsNotifTopic(), smsRequest);
            }
        }
    }

    private void pushNotificationForUpdate(OrgRequest request) {
        log.info("get message template for update action");
        String message = getMessage(request, false);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        for(Organisation organisation : request.getOrganisations()){
            OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder().requestInfo(request.getRequestInfo())
                    .searchCriteria(OrgSearchCriteria.builder().orgNumber(organisation.getOrgNumber()).build()).build();
            ContactDetails oldContactDetails = organisationRepository.getOrganisations(orgSearchRequest).get(0).getContactDetails().get(0);


            Map<String, String> smsDetails = new HashMap<>();
            smsDetails.put("orgNumber", organisation.getOrgNumber());
            smsDetails.put("oldMobileNumber",oldContactDetails.getContactMobileNumber());
            smsDetails.put("newMobileNumber", organisation.getContactDetails().get(0).getContactMobileNumber());
            smsDetails.put(ORG_NAME,organisation.getName());
            log.info("build Message For update Action for " + smsDetails.get(ORG_NAME));
            String customizedMessage = buildMessageForUpdateAction(smsDetails, message);
            SMSRequest smsRequestForOldMobileNumber = SMSRequest.builder().mobileNumber(smsDetails.get("oldMobileNumber")).message(customizedMessage).build();

            log.info("push message for update Action");
            organizationProducer.push(config.getSmsNotifTopic(), smsRequestForOldMobileNumber);
            if(!organisation.getContactDetails().get(0).getContactMobileNumber().equalsIgnoreCase(oldContactDetails.getContactMobileNumber())){
                SMSRequest smsRequestForNewMobileNumber = SMSRequest.builder().mobileNumber(smsDetails.get("newMobileNumber")).message(customizedMessage).build();
                organizationProducer.push(config.getSmsNotifTopic(), smsRequestForNewMobileNumber);
            }
        }
    }

    private Map<String, List<String>> getDetailsForSMS(Organisation organisation) {

        String orgName = organisation.getName();
        List<String> personNames = organisation.getContactDetails().stream().map(ContactDetails::getContactName).collect(Collectors.toList());
        List<String> mobileNumbers = organisation.getContactDetails().stream().map(ContactDetails::getContactMobileNumber).collect(Collectors.toList());

        Map<String, List<String>> smsDetails = new HashMap<>();

        smsDetails.put("orgNames", Collections.singletonList(orgName));
        smsDetails.put(PERSON_NAMES, personNames);
        smsDetails.put("mobileNumbers", mobileNumbers);


        return smsDetails;
    }

    private String getMessage(OrgRequest request, boolean isCreateOperation) {
        String message = null;

        if (isCreateOperation) {
            message = getMessage(request, ORGANISATION_CREATE_LOCALIZATION_CODE);
        } else {
            message = getMessage(request, ORGANISATION_UPDATE_LOCALIZATION_CODE);
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
    public String getMessage(OrgRequest request, String msgCode) {
        String tenantId = request.getOrganisations().get(0).getTenantId();
        RequestInfo requestInfo = request.getRequestInfo();
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(requestInfo, tenantId,
                locale, OrganisationConstant.ORGANISATION_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + tenantId).get(msgCode);
    }

    /**
     * Builds msg based on the format
     *
     * @param message
     * @param userDetailsForSMS
     * @return
     */
    public String buildMessageForCreateAction(Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{individualName}", userDetailsForSMS.get("personName"))
                .replace("{organisationName}", userDetailsForSMS.get(ORG_NAME))
                .replace("{ID}", userDetailsForSMS.get("orgId"));
        return message;
    }

    public String buildMessageForUpdateAction(Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{orgID}", userDetailsForSMS.get("orgNumber"));
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
            codes = JsonPath.read(result, OrganisationConstant.ORGANISATION_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, OrganisationConstant.ORGANISATION_LOCALIZATION_MSGS_JSONPATH);
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

