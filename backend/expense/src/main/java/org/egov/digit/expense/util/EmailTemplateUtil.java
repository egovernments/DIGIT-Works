package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.WorkflowEmailTemplate;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

import static org.egov.digit.expense.config.Constants.WORKFLOW_EMAIL_NOTIFICATION_MDMS_SCHEMA;

/**
 * Handles email template fetching from MDMS and HTML body rendering.
 *
 * <p>Fetch: Calls MDMS V2 with schemaCode EXPENSE.workflowEmailNotification and parses
 * the response into a {@link WorkflowEmailTemplate} containing localization keys.
 *
 * <p>Render: Resolves localization keys from the provided map, replaces runtime tokens
 * ({userName}, {billCount}), and injects the resolved text into a fixed HTML skeleton.
 */
@Component
@Slf4j
public class EmailTemplateUtil {

    private static final String HTML_SKELETON =
            "<!DOCTYPE html>" +
            "<html><head>" +
            "<meta charset='UTF-8'/>" +
            "<style>" +
            "body{font-family:Arial,sans-serif;background:#f4f4f4;margin:0;padding:20px}" +
            ".container{background:#ffffff;max-width:600px;margin:0 auto;border-radius:8px;overflow:hidden;box-shadow:0 2px 6px rgba(0,0,0,0.1)}" +
            ".header{background:#244b8f;color:#ffffff;padding:18px 24px;font-size:18px;font-weight:bold}" +
            ".body{padding:24px;color:#333333;line-height:1.6}" +
            ".body p{margin:0 0 14px}" +
            ".count-box{background:#f0f4ff;border-left:4px solid #244b8f;padding:14px 18px;margin:18px 0;border-radius:4px;font-size:16px;color:#244b8f;font-weight:bold}" +
            ".footer{background:#f0f0f0;padding:12px 24px;font-size:12px;color:#888888;text-align:center}" +
            "</style>" +
            "</head><body>" +
            "<div class='container'>" +
            "<div class='header'>%s</div>" +
            "<div class='body'>" +
            "<p>%s</p>" +
            "<div class='count-box'>%s</div>" +
            "</div>" +
            "<div class='footer'>%s</div>" +
            "</div>" +
            "</body></html>";

    private final Configuration config;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmailTemplateUtil(Configuration config,
                             ServiceRequestRepository serviceRequestRepository,
                             ObjectMapper objectMapper) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Fetches the email template from MDMS V2 for the given notification type.
     *
     * @return parsed {@link WorkflowEmailTemplate}, or {@code null} if not found
     */
    public WorkflowEmailTemplate fetchTemplate(RequestInfo requestInfo,
                                               String tenantId,
                                               WorkflowNotificationType type) {
        try {
            StringBuilder url = new StringBuilder()
                    .append(config.getMdmsV2Host())
                    .append(config.getMdmsV2EndPointV2());

            Object requestBody = buildMdmsV2Request(requestInfo, tenantId, type.getMdmsIdentifier());
            Object response = serviceRequestRepository.fetchResult(url, requestBody);

            JsonNode root = objectMapper.valueToTree(response);
            JsonNode mdmsArray = root.path("mdms");
            if (mdmsArray.isMissingNode() || !mdmsArray.isArray() || mdmsArray.isEmpty()) {
                log.warn("EmailTemplateUtil: no MDMS template found for type={} tenantId={}", type, tenantId);
                return null;
            }

            JsonNode data = mdmsArray.get(0).path("data");
            return WorkflowEmailTemplate.builder()
                    .subject(data.path("subject").asText(""))
                    .headerTitle(data.path("header").path("title").asText(""))
                    .greeting(data.path("body").path("greeting").asText(""))
                    .instruction(data.path("body").path("instruction").asText(""))
                    .footerContent(data.path("footer").path("content").asText(""))
                    .build();

        } catch (Exception e) {
            log.error("EmailTemplateUtil: failed to fetch template for type={} tenantId={}: {}", type, tenantId, e.getMessage());
            return null;
        }
    }

    /**
     * Resolves localization keys in the template, replaces runtime tokens, and
     * renders the full HTML email body.
     *
     * @param template     parsed template containing localization keys
     * @param locMap       key → resolved text map from the localization service
     * @param userName     assignee's name for {userName} token replacement
     * @param billCount    number of bills for {billCount} token replacement
     * @return rendered HTML string
     */
    public String renderHtmlBody(WorkflowEmailTemplate template,
                                 Map<String, String> locMap,
                                 String userName,
                                 int billCount) {
        String headerTitle  = resolveAndReplace(template.getHeaderTitle(),  locMap, userName, billCount);
        String greeting     = resolveAndReplace(template.getGreeting(),     locMap, userName, billCount);
        String instruction  = resolveAndReplace(template.getInstruction(),  locMap, userName, billCount);
        String footerContent = resolveAndReplace(template.getFooterContent(), locMap, userName, billCount);

        return String.format(HTML_SKELETON, headerTitle, greeting, instruction, footerContent);
    }

    /**
     * Resolves and renders the email subject with token replacement.
     */
    public String renderSubject(WorkflowEmailTemplate template,
                                Map<String, String> locMap,
                                int billCount) {
        return resolveAndReplace(template.getSubject(), locMap, null, billCount);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    /** Looks up a localization key, then replaces {userName} and {billCount} tokens. */
    private String resolveAndReplace(String key, Map<String, String> locMap,
                                     String userName, int billCount) {
        if (key == null || key.isBlank()) return "";
        String text = locMap.getOrDefault(key, key); // fall back to key itself if not resolved
        if (userName != null) text = text.replace("{userName}", userName);
        text = text.replace("{billCount}", String.valueOf(billCount));
        return text;
    }

    /** Builds the MDMS V2 search request body. */
    private Object buildMdmsV2Request(RequestInfo requestInfo, String tenantId, String uniqueIdentifier) {
        ObjectNode root = objectMapper.createObjectNode();
        root.putPOJO("RequestInfo", requestInfo);

        ObjectNode criteria = objectMapper.createObjectNode();
        criteria.put("tenantId", tenantId);
        criteria.put("schemaCode", WORKFLOW_EMAIL_NOTIFICATION_MDMS_SCHEMA);

        ArrayNode ids = objectMapper.createArrayNode();
        ids.add(uniqueIdentifier);
        criteria.set("uniqueIdentifiers", ids);

        root.set("MdmsCriteria", criteria);
        return root;
    }
}
