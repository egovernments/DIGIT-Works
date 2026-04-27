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

import java.util.Map;

import static org.egov.digit.expense.config.Constants.WORKFLOW_EMAIL_NOTIFICATION_MDMS_SCHEMA;

/**
 * Handles email template fetching from MDMS and HTML body rendering.
 *
 * <p>Fetch: Calls MDMS V2 with schemaCode EXPENSE.billEmailNotification and parses
 * the response into a {@link WorkflowEmailTemplate} with {@code subject} and {@code htmlBody}.
 *
 * <p>Render (two-pass):
 * <ol>
 *   <li>Replace every {@code {{LOCALIZATION_KEY}}} placeholder in the template with its
 *       resolved value from the localization map.</li>
 *   <li>Replace runtime tokens {@code {userName}} and {@code {billCount}}.</li>
 * </ol>
 */
@Component
@Slf4j
public class EmailTemplateUtil {

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
                    .htmlBody(data.path("htmlBody").asText(""))
                    .build();

        } catch (Exception e) {
            log.error("EmailTemplateUtil: failed to fetch template for type={} tenantId={}: {}", type, tenantId, e.getMessage());
            return null;
        }
    }

    /**
     * Renders the full HTML email body.
     *
     * <p>Pass 1 — replaces {@code {{KEY}}} placeholders with localized values from {@code locMap}.
     * Pass 2 — replaces {@code {userName}} and {@code {billCount}} runtime tokens.
     */
    public String renderHtmlBody(WorkflowEmailTemplate template,
                                 Map<String, String> locMap,
                                 String userName,
                                 int billCount) {
        String html = template.getHtmlBody();
        html = replacePlaceholders(html, locMap);
        if (userName != null) html = html.replace("{userName}", userName);
        html = html.replace("{billCount}", String.valueOf(billCount));
        return html;
    }

    /**
     * Renders the email subject line.
     *
     * <p>Pass 1 — replaces {@code {{KEY}}} placeholders. Pass 2 — replaces {@code {billCount}}.
     */
    public String renderSubject(WorkflowEmailTemplate template,
                                Map<String, String> locMap,
                                int billCount) {
        String subject = template.getSubject();
        subject = replacePlaceholders(subject, locMap);
        subject = subject.replace("{billCount}", String.valueOf(billCount));
        return subject;
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    /** Replaces every {@code {{KEY}}} occurrence in {@code text} with the value from {@code locMap}. */
    private String replacePlaceholders(String text, Map<String, String> locMap) {
        if (text == null || text.isBlank() || locMap == null || locMap.isEmpty()) return text;
        for (Map.Entry<String, String> entry : locMap.entrySet()) {
            text = text.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
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
