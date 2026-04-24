package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.ProcessInstance;
import org.egov.common.contract.workflow.ProcessInstanceResponse;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.util.EmailTemplateUtil;
import org.egov.digit.expense.util.IndividualUtil;
import org.egov.digit.expense.util.LocalizationUtil;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Orchestrates email notifications for PAYMENTS.BILL workflow transitions.
 *
 * <p>Sends an email to each assignee (reviewer or approver) after the bill transitions.
 * All exceptions are caught and logged — notification failures never break the workflow.
 */
@Service
@Slf4j
public class WorkflowEmailNotificationService {

    private final IndividualUtil individualUtil;
    private final EmailTemplateUtil emailTemplateUtil;
    private final LocalizationUtil localizationUtil;
    private final WorkflowUtil workflowUtil;
    private final ExpenseProducer expenseProducer;
    private final Configuration config;

    @Autowired
    public WorkflowEmailNotificationService(IndividualUtil individualUtil,
                                             EmailTemplateUtil emailTemplateUtil,
                                             LocalizationUtil localizationUtil,
                                             WorkflowUtil workflowUtil,
                                             ExpenseProducer expenseProducer,
                                             Configuration config) {
        this.individualUtil = individualUtil;
        this.emailTemplateUtil = emailTemplateUtil;
        this.localizationUtil = localizationUtil;
        this.workflowUtil = workflowUtil;
        this.expenseProducer = expenseProducer;
        this.config = config;
    }

    /**
     * Sends email notifications to all assignees for the given workflow transition.
     * Safe to call unconditionally — all failures are logged, never thrown.
     *
     * @param billRequest the bill request after workflow transition (processInstance must be set)
     * @param type        REVIEW or APPROVAL
     */
    public void notify(BillRequest billRequest, WorkflowNotificationType type) {
        try {
            doNotify(billRequest, type);
        } catch (Exception e) {
            log.error("WorkflowEmailNotificationService: unexpected error during {} notification for bill={}: {}",
                    type, billRequest.getBill().getId(), e.getMessage(), e);
        }
    }

    // ── Core logic ───────────────────────────────────────────────────────────

    private void doNotify(BillRequest billRequest, WorkflowNotificationType type) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();
        String tenantId = bill.getTenantId();

        List<User> assignees = resolveAssignees(bill, tenantId, requestInfo);
        if (CollectionUtils.isEmpty(assignees)) {
            log.warn("WorkflowEmailNotificationService: no assignees found for bill={} type={}", bill.getId(), type);
            return;
        }

        WorkflowEmailTemplate template = emailTemplateUtil.fetchTemplate(requestInfo, tenantId, type);
        if (template == null) {
            log.warn("WorkflowEmailNotificationService: email template not found for type={} tenantId={}", type, tenantId);
            return;
        }

        String locale = resolveLocale(requestInfo);
        Map<String, String> locMap = resolveLocalizationMap(requestInfo, tenantId, locale);

        int billCount = 1; // currently 1 bill per request; extend to List<Bill> for future batch support

        // Deduplicate assignees by UUID to avoid sending duplicate emails
        List<String> uniqueUuids = assignees.stream()
                .map(User::getUuid)
                .filter(uuid -> uuid != null && !uuid.isBlank())
                .distinct()
                .collect(Collectors.toList());

        for (String uuid : uniqueUuids) {
            sendEmailToAssignee(uuid, tenantId, requestInfo, template, locMap, billCount, type);
        }
    }

    private void sendEmailToAssignee(String assigneeUuid,
                                     String tenantId,
                                     RequestInfo requestInfo,
                                     WorkflowEmailTemplate template,
                                     Map<String, String> locMap,
                                     int billCount,
                                     WorkflowNotificationType type) {
        IndividualDetails details = individualUtil.getIndividualDetails(requestInfo, tenantId, assigneeUuid);
        if (details == null || !StringUtils.hasText(details.getEmail())) {
            log.info("WorkflowEmailNotificationService: no email for assignee uuid={}, skipping {} notification", assigneeUuid, type);
            return;
        }

        String subject = emailTemplateUtil.renderSubject(template, locMap, billCount);
        String body    = emailTemplateUtil.renderHtmlBody(template, locMap, details.getName(), billCount);

        EmailRequest emailRequest = EmailRequest.builder()
                .requestInfo(requestInfo)
                .email(EmailRequest.EmailMessage.builder()
                        .emailTo(Collections.singletonList(details.getEmail()))
                        .subject(subject)
                        .body(body)
                        .tenantId(tenantId)
                        .isHTML(true)
                        .build())
                .build();

        expenseProducer.push(config.getEmailNotificationTopic(), emailRequest);
        log.info("WorkflowEmailNotificationService: email notification pushed for assignee={} type={}", details.getEmail(), type);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Returns assignees from the bill's process instance.
     * Falls back to a workflow search if the process instance is absent or has no assignees.
     */
    private List<User> resolveAssignees(Bill bill, String tenantId, RequestInfo requestInfo) {
        if (bill.getProcessInstance() != null
                && !CollectionUtils.isEmpty(bill.getProcessInstance().getAssignes())) {
            return bill.getProcessInstance().getAssignes();
        }

        log.info("WorkflowEmailNotificationService: no assignees on processInstance for bill={}, searching workflow", bill.getId());
        try {
            ProcessInstanceResponse wfResponse = workflowUtil.searchWorkflowForBusinessIds(
                    Collections.singletonList(bill.getBillNumber()), tenantId, requestInfo);
            if (wfResponse != null && !CollectionUtils.isEmpty(wfResponse.getProcessInstances())) {
                ProcessInstance pi = wfResponse.getProcessInstances().get(0);
                return pi.getAssignes() != null ? pi.getAssignes() : Collections.emptyList();
            }
        } catch (Exception e) {
            log.warn("WorkflowEmailNotificationService: workflow search failed for bill={}: {}", bill.getId(), e.getMessage());
        }
        return Collections.emptyList();
    }

    /** Extracts locale from RequestInfo msgId (format: "msgId|locale"), defaults to configured locale. */
    private String resolveLocale(RequestInfo requestInfo) {
        try {
            String msgId = requestInfo.getMsgId();
            if (msgId != null && msgId.contains("|")) {
                String locale = msgId.split("\\|")[1];
                if (StringUtils.hasText(locale)) return locale;
            }
        } catch (Exception ignored) {
            // fall through to default
        }
        return config.getLocalizationDefaultLocale();
    }

    /** Fetches and flattens the localization map for the email notification module. */
    private Map<String, String> resolveLocalizationMap(RequestInfo requestInfo, String tenantId, String locale) {
        try {
            Map<String, Map<String, String>> locData = localizationUtil.getLocalisedMessages(
                    requestInfo, tenantId, locale, config.getEmailNotificationLocalizationModule());
            Map<String, String> locMap = locData.get(locale + "|" + tenantId);
            return locMap != null ? locMap : Collections.emptyMap();
        } catch (Exception e) {
            log.warn("WorkflowEmailNotificationService: failed to fetch localization for module={} tenantId={}: {}",
                    config.getEmailNotificationLocalizationModule(), tenantId, e.getMessage());
            return Collections.emptyMap();
        }
    }
}
