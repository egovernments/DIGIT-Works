package org.egov.digit.expense.calculator.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class LocalizationUtil {

    private final ExpenseCalculatorConfiguration config;
    private final ServiceRequestRepository restRepo;

    public static final String EXPENSE_CALCULATOR_LOCALIZATION_CODE_JSONPATH = "$.messages.*.code";
    public static final String EXPENSE_CALCULATOR_LOCALIZATION_MESSAGE_JSONPATH = "$.messages.*.message";

    @Autowired
    public LocalizationUtil(ExpenseCalculatorConfiguration config, ServiceRequestRepository restRepo) {
        this.config = config;
        this.restRepo = restRepo;
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
        uri.append(config.getLocalizationServiceHost()).append(config.getLocalizationServiceContextPath())
                .append(config.getLocalizationServiceEndpoint()).append("?tenantId=" + rootTenantId)
                .append("&module=" + module).append("&locale=" + locale);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = restRepo.fetchResult(uri, requestInfoWrapper);
            codes = JsonPath.read(result, EXPENSE_CALCULATOR_LOCALIZATION_CODE_JSONPATH);
            messages = JsonPath.read(result, EXPENSE_CALCULATOR_LOCALIZATION_MESSAGE_JSONPATH);
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

    // Get local code from request info, if it's not there then return from config
    public String getLocalCode(RequestInfo requestInfo) {
        String msgId = requestInfo.getMsgId();
        if (StringUtils.hasLength(msgId) && msgId.contains("|")) {
            // Split the string by the pipe symbol
            String[] parts = msgId.split("\\|", 2); // Limit to 2 parts
            if (parts.length > 1 && StringUtils.hasLength(parts[1])) {
                return parts[1];
            }
        }
        return config.getReportLocalizationLocaleCode();
    }


}
