package org.egov.digit.expense.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.egov.digit.expense.config.Constants.*;
import static org.egov.digit.expense.util.BillUpdateMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillUpdateModeTest {

    private static final Set<String> EDITOR_ROLE   = Set.of(ROLE_PAYMENT_EDITOR);
    private static final Set<String> REVIEWER_ROLE = Set.of(ROLE_PAYMENT_REVIEWER);
    private static final Set<String> BOTH_ROLES    = Set.of(ROLE_PAYMENT_EDITOR, ROLE_PAYMENT_REVIEWER);

    @Test
    void editorRoleGetsEditorModeAtItsOwnStages() {
        assertEquals(EDITOR, resolve(EDITOR_ROLE, STATUS_PENDING_VERIFICATION));
        assertEquals(EDITOR, resolve(EDITOR_ROLE, STATUS_PARTIALLY_VERIFIED));
    }

    @Test
    void reviewerRoleGetsReviewerModeOnlyUnderReview() {
        assertEquals(REVIEWER, resolve(REVIEWER_ROLE, STATUS_UNDER_REVIEW));
    }

    @Test
    void dualRoleFollowsTheBillStage() {
        assertEquals(EDITOR,   resolve(BOTH_ROLES, STATUS_PENDING_VERIFICATION));
        assertEquals(EDITOR,   resolve(BOTH_ROLES, STATUS_PARTIALLY_VERIFIED));
        assertEquals(REVIEWER, resolve(BOTH_ROLES, STATUS_UNDER_REVIEW));
    }

    @Test
    void roleWithoutAMatchingStageGetsNoMode() {
        assertEquals(NONE, resolve(EDITOR_ROLE, STATUS_UNDER_REVIEW));
        assertEquals(NONE, resolve(REVIEWER_ROLE, STATUS_PENDING_VERIFICATION));
        assertEquals(NONE, resolve(BOTH_ROLES, "FULLY_VERIFIED"));
    }

    @Test
    void missingRolesOrStatusGetNoMode() {
        assertEquals(NONE, resolve(Set.of(), STATUS_UNDER_REVIEW));
        assertEquals(NONE, resolve(BOTH_ROLES, null));
        assertEquals(NONE, resolve(null, STATUS_UNDER_REVIEW));
    }
}
