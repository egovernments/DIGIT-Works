package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.util.EmailTemplateUtil;
import org.egov.digit.expense.util.IndividualUtil;
import org.egov.digit.expense.util.LocalizationUtil;
import org.egov.digit.expense.util.ProjectStaffUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    private final ProjectStaffUtil projectStaffUtil;
    private final EmailTemplateUtil emailTemplateUtil;
    private final LocalizationUtil localizationUtil;
    private final ExpenseProducer expenseProducer;
    private final Configuration config;

    @Autowired
    public WorkflowEmailNotificationService(IndividualUtil individualUtil,
                                             ProjectStaffUtil projectStaffUtil,
                                             EmailTemplateUtil emailTemplateUtil,
                                             LocalizationUtil localizationUtil,
                                             ExpenseProducer expenseProducer,
                                             Configuration config) {
        this.individualUtil = individualUtil;
        this.projectStaffUtil = projectStaffUtil;
        this.emailTemplateUtil = emailTemplateUtil;
        this.localizationUtil = localizationUtil;
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

        WorkflowEmailTemplate template = emailTemplateUtil.fetchTemplate(requestInfo, tenantId, type);
        if (template == null) {
            log.warn("WorkflowEmailNotificationService: email template not found for type={} tenantId={}", type, tenantId);
            return;
        }

        String locale = resolveLocale(requestInfo);
        Map<String, String> locMap = resolveLocalizationMap(requestInfo, tenantId, locale);
        int billCount = 1;

        List<IndividualDetails> recipients = resolveRecipientsForBillRoles(bill, tenantId, requestInfo, type);
        if (CollectionUtils.isEmpty(recipients)) {
            log.warn("WorkflowEmailNotificationService: no recipients found for bill={} type={}", bill.getId(), type);
            return;
        }

        for (IndividualDetails recipient : recipients) {
            sendEmailDirect(recipient.getEmail(), recipient.getName(), tenantId, requestInfo, template, locMap, billCount, type);
        }
    }

    private void sendEmailDirect(String email, String name,
                                  String tenantId,
                                  RequestInfo requestInfo,
                                  WorkflowEmailTemplate template,
                                  Map<String, String> locMap,
                                  int billCount,
                                  WorkflowNotificationType type) {
        String subject = emailTemplateUtil.renderSubject(template, locMap, billCount);
        String body    = emailTemplateUtil.renderHtmlBody(template, locMap, name, billCount);

        EmailRequest emailRequest = EmailRequest.builder()
                .requestInfo(requestInfo)
                .email(EmailRequest.EmailMessage.builder()
                        .emailTo(Collections.singletonList(email))
                        .subject(subject)
                        .body(body)
                        .tenantId(tenantId)
                        .isHTML(true)
                        .build())
                .build();

        expenseProducer.push(config.getEmailNotificationTopic(), emailRequest);
        log.info("WorkflowEmailNotificationService: email sent to={} type={}", email, type);
    }

    /**
     * Finds individuals with the role required for this notification type,
     * then filters to those who are project staff for the bill's projects.
     */
    private List<IndividualDetails> resolveRecipientsForBillRoles(Bill bill, String tenantId,
                                                                    RequestInfo requestInfo,
                                                                    WorkflowNotificationType type) {
        List<IndividualDetails> candidates = individualUtil.searchByRoleCodes(
                requestInfo, tenantId, Collections.singletonList(type.getTargetRole()));
        if (CollectionUtils.isEmpty(candidates)) return Collections.emptyList();

        if (!StringUtils.hasText(bill.getReferenceId())) return Collections.emptyList();

        List<String> projectIds = Arrays.asList(bill.getReferenceId().split("\\."));
        List<String> staffIds   = candidates.stream().map(IndividualDetails::getId).collect(Collectors.toList());

        Set<String> matched = projectStaffUtil.filterByProjectStaff(requestInfo, tenantId, staffIds, projectIds);
        if (matched.isEmpty()) return Collections.emptyList();

        return candidates.stream()
                .filter(i -> matched.contains(i.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Sends one batch email to all recipients that are project staff for ANY bill in the batch.
     * Safe to call unconditionally — all failures are logged, never thrown.
     *
     * @param bills     bills in the batch (used for project ID resolution)
     * @param tenantId  tenant
     * @param requestInfo original actor's RequestInfo
     * @param type      REVIEW or APPROVAL
     * @param billCount number of bills that successfully transitioned (shown in email body)
     */
    public void notifyBatch(List<Bill> bills, String tenantId, RequestInfo requestInfo,
                             WorkflowNotificationType type, int billCount) {
        try {
            doNotifyBatch(bills, tenantId, requestInfo, type, billCount);
        } catch (Exception e) {
            log.error("WorkflowEmailNotificationService.notifyBatch: unexpected error type={} billCount={}: {}",
                    type, billCount, e.getMessage(), e);
        }
    }

    private void doNotifyBatch(List<Bill> bills, String tenantId, RequestInfo requestInfo,
                                WorkflowNotificationType type, int billCount) {
        WorkflowEmailTemplate template = emailTemplateUtil.fetchTemplate(requestInfo, tenantId, type);
        if (template == null) {
            log.warn("WorkflowEmailNotificationService.notifyBatch: template not found type={} tenantId={}", type, tenantId);
            return;
        }

        String locale = resolveLocale(requestInfo);
        Map<String, String> locMap = resolveLocalizationMap(requestInfo, tenantId, locale);

        // Union all project IDs across every bill in the batch
        List<String> allProjectIds = bills.stream()
                .filter(b -> StringUtils.hasText(b.getReferenceId()))
                .flatMap(b -> Stream.of(b.getReferenceId().split("\\.")))
                .distinct()
                .collect(Collectors.toList());
        if (allProjectIds.isEmpty()) {
            log.warn("WorkflowEmailNotificationService.notifyBatch: no projectIds found for batch type={}", type);
            return;
        }

        List<IndividualDetails> candidates = individualUtil.searchByRoleCodes(
                requestInfo, tenantId, Collections.singletonList(type.getTargetRole()));
        if (CollectionUtils.isEmpty(candidates)) return;

        List<String> staffIds = candidates.stream().map(IndividualDetails::getId).collect(Collectors.toList());
        Set<String> matched = projectStaffUtil.filterByProjectStaff(requestInfo, tenantId, staffIds, allProjectIds);
        if (matched.isEmpty()) {
            log.warn("WorkflowEmailNotificationService.notifyBatch: no project-staff recipients for batch type={}", type);
            return;
        }

        candidates.stream()
                .filter(i -> matched.contains(i.getId()))
                .forEach(r -> sendEmailDirect(r.getEmail(), r.getName(), tenantId, requestInfo,
                        template, locMap, billCount, type));
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
