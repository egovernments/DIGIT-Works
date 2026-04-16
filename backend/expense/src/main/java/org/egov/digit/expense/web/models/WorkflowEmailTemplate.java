package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the email template structure fetched from MDMS (EXPENSE.workflowEmailNotification).
 * Each field holds a localization key that is resolved before rendering the email.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEmailTemplate {

    /** Localization key for the email subject line. */
    private String subject;

    /** Localization key for the header title. */
    private String headerTitle;

    /** Localization key for the greeting line in the email body. */
    private String greeting;

    /** Localization key for the main instruction paragraph. */
    private String instruction;

    /** Localization key for the footer text. */
    private String footerContent;
}
