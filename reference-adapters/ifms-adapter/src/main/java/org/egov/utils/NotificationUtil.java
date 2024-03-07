package org.egov.utils;


import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.egov.common.contract.models.RequestInfoWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.JIT_SERVICE_LOCALIZATION_CODES_JSONPATH;
import static org.egov.config.Constants.JIT_SERVICE_MSGS_JSONPATH;

@Component
@Slf4j
public class NotificationUtil {

    @Autowired
    private IfmsAdapterConfig ifmsAdapterConfig;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public Map<String, Map<String, String>> getLocalisedMessages(RequestInfo requestInfo, String rootTenantId, String locale, String module) {
        Map<String, Map<String, String>> localizedMessageMap = new HashMap<>();
        Map<String, String> mapOfCodesAndMessages = new HashMap<>();
        StringBuilder uri = new StringBuilder();
        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(requestInfo);
        uri.append(ifmsAdapterConfig.getLocalizationHost()).append(ifmsAdapterConfig.getLocalizationContextPath())
                .append(ifmsAdapterConfig.getLocalizationSearchEndpoint()).append("?tenantId=" + rootTenantId)
                .append("&module=" + module).append("&locale=" + locale);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = serviceRequestRepository.fetchResult(uri, requestInfoWrapper);
            codes = JsonPath.read(result, JIT_SERVICE_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, JIT_SERVICE_MSGS_JSONPATH);
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