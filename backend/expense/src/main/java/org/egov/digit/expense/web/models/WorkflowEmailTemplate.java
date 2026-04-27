package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the email template fetched from MDMS (EXPENSE.billEmailNotification).
 * Both fields may contain {{LOCALIZATION_KEY}} placeholders (resolved via localization service)
 * and runtime tokens {userName} / {billCount} (replaced at render time).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEmailTemplate {

    /** Email subject; may contain {{LOC_KEY}} and {billCount} placeholders. */
    private String subject;

    /** Full HTML email body; may contain {{LOC_KEY}}, {userName}, {billCount} placeholders. */
    private String htmlBody;
}
