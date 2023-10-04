package org.egov.works.measurement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.config.ServiceConstants;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.Estimate;
import org.egov.works.measurement.web.models.EstimateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.measurement.config.ServiceConstants.HRMS_USER_MOBILE_NO;
import static org.egov.works.measurement.config.ServiceConstants.PROJECT_NUMBER_CODE;

@Component
@Slf4j
public class NotificationUtil {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private  MBServiceConfiguration config;
    @Autowired
    private ServiceRequestRepository restRepo;
    @Autowired
    private ObjectMapper mapper;

    public String getProjectId(RequestInfo requestInfo, String tenantId, String referenceId) {
        Estimate estimate = getEstimate(requestInfo, tenantId, referenceId).getEstimates().get(0);
        String projectId = estimate.getProjectId();
        String projectNumber = getProjectNumber(requestInfo, tenantId, projectId);

        return projectNumber;
    }
    private EstimateResponse getEstimate(RequestInfo requestInfo, String tenantId,String referenceId) {

        String estimateSearchUri = config.getEstimateHost()+config.getEstimatePath();
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(estimateSearchUri);
        uri.queryParam("tenantId", tenantId);
        uri.queryParam("referenceNumber", referenceId);

        String url = uri.toUriString();
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        EstimateResponse estimateResponse = restTemplate.postForObject(url, requestInfoWrapper, EstimateResponse.class);
        return estimateResponse;
    }

    private String getProjectNumber (RequestInfo requestInfo, String tenantId, String projectId) {
        Object projectRes = getProjectDetails(requestInfo, tenantId, projectId);

        List<String> projectNumber = new ArrayList<>();
        try {
            projectNumber = JsonPath.read(projectRes, PROJECT_NUMBER_CODE);
        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse project response");
        }
        return projectNumber.get(0);
    }

    public Object getProjectDetails(RequestInfo requestInfo, String tenantId, String projectId) {
        log.info("ProjectUtil::getProjectDetails");
        String projectSearchUri = config.getWorksProjectServiceHost()+config.getWorksProjectServicePath();
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(projectSearchUri);
        uri.queryParam("tenantId", tenantId);
        uri.queryParam("offset", "0");
        uri.queryParam("limit", "1");
        StringBuilder url = new StringBuilder(uri.toUriString());

        //created the project search request body
        ObjectNode projectSearchReqNode = mapper.createObjectNode();
        ArrayNode projectArrayNode = mapper.createArrayNode();

        ObjectNode projectObjNode = mapper.createObjectNode();
        projectObjNode.put("id", projectId);
        projectObjNode.put("tenantId", tenantId);

        projectArrayNode.add(projectObjNode);

        projectSearchReqNode.putPOJO("RequestInfo", requestInfo);
        projectSearchReqNode.putPOJO("Projects", projectArrayNode);

        log.info("ProjectUtil::search project request -> {}",projectSearchReqNode);
        Object projectRes = restRepo.fetchResult(url, projectSearchReqNode);

        return projectRes;
    }

    public String getEmployeeMobileNumber(RequestInfo requestInfo, String tenantId, String uuid) {
        StringBuilder url = getHRMSURIWithUUid(tenantId, uuid);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object res = restRepo.fetchResult(url, requestInfoWrapper);
        List<String> mobileNumber = null;
        try {
            mobileNumber = JsonPath.read(res, HRMS_USER_MOBILE_NO);
        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }
        return mobileNumber.get(0);
    }
    private StringBuilder getHRMSURIWithUUid(String tenantId, String employeeUuid) {

        StringBuilder builder = new StringBuilder(config.getHrmsHost());
        builder.append(config.getHrmsEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&uuids=");
        builder.append(employeeUuid);

        return builder;
    }

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
            result = restRepo.fetchResult(uri, requestInfoWrapper);
            codes = JsonPath.read(result, ServiceConstants.MEASUREMENT_SERVICE_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, ServiceConstants.MEASUREMENT_SERVICE_MSGS_JSONPATH);
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

}
