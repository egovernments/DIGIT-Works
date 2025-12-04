package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.MusterRoll;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MusterRollVersionHelper
 *
 * Utility class to help with V1/V2 backward compatibility.
 * Provides methods to detect and differentiate V1 vs V2 muster rolls.
 */
@Component
@Slf4j
public class MusterRollVersionHelper {

    /**
     * Check if a muster roll is V2 (period-aware)
     *
     * @param musterRoll Muster roll to check
     * @return true if V2 (has non-blank billingPeriodId), false if V1 (null or blank billingPeriodId)
     */
    public boolean isV2MusterRoll(MusterRoll musterRoll) {
        if (musterRoll == null) {
            return false;
        }
        return StringUtils.isNotBlank(musterRoll.getBillingPeriodId());
    }

    /**
     * Check if a muster roll is V1 (legacy)
     *
     * @param musterRoll Muster roll to check
     * @return true if V1 (null or blank billingPeriodId), false if V2
     */
    public boolean isV1MusterRoll(MusterRoll musterRoll) {
        if (musterRoll == null) {
            return false;
        }
        return StringUtils.isBlank(musterRoll.getBillingPeriodId());
    }

    /**
     * Filter V1 muster rolls from a list
     *
     * @param musterRolls List of muster rolls
     * @return List containing only V1 muster rolls (null or blank billingPeriodId)
     */
    public List<MusterRoll> filterV1MusterRolls(List<MusterRoll> musterRolls) {
        if (musterRolls == null || musterRolls.isEmpty()) {
            return List.of();
        }

        List<MusterRoll> v1MusterRolls = musterRolls.stream()
                .filter(this::isV1MusterRoll)
                .collect(Collectors.toList());

        log.debug("Filtered {} V1 muster rolls out of {} total",
                  v1MusterRolls.size(), musterRolls.size());

        return v1MusterRolls;
    }

    /**
     * Filter V2 muster rolls from a list
     *
     * @param musterRolls List of muster rolls
     * @return List containing only V2 muster rolls (billingPeriodId populated)
     */
    public List<MusterRoll> filterV2MusterRolls(List<MusterRoll> musterRolls) {
        if (musterRolls == null || musterRolls.isEmpty()) {
            return List.of();
        }

        List<MusterRoll> v2MusterRolls = musterRolls.stream()
                .filter(this::isV2MusterRoll)
                .collect(Collectors.toList());

        log.debug("Filtered {} V2 muster rolls out of {} total",
                  v2MusterRolls.size(), musterRolls.size());

        return v2MusterRolls;
    }

    /**
     * Count V1 muster rolls in a list
     *
     * @param musterRolls List of muster rolls
     * @return Number of V1 muster rolls
     */
    public long countV1MusterRolls(List<MusterRoll> musterRolls) {
        if (musterRolls == null || musterRolls.isEmpty()) {
            return 0;
        }
        return musterRolls.stream()
                .filter(this::isV1MusterRoll)
                .count();
    }

    /**
     * Count V2 muster rolls in a list
     *
     * @param musterRolls List of muster rolls
     * @return Number of V2 muster rolls
     */
    public long countV2MusterRolls(List<MusterRoll> musterRolls) {
        if (musterRolls == null || musterRolls.isEmpty()) {
            return 0;
        }
        return musterRolls.stream()
                .filter(this::isV2MusterRoll)
                .count();
    }

    /**
     * Get muster roll version string for logging
     *
     * @param musterRoll Muster roll
     * @return "V2" if has billingPeriodId, "V1" if not, "UNKNOWN" if null
     */
    public String getMusterRollVersion(MusterRoll musterRoll) {
        if (musterRoll == null) {
            return "UNKNOWN";
        }
        return isV2MusterRoll(musterRoll) ? "V2" : "V1";
    }

    /**
     * Validate backward compatibility: ensure V1 fields are not broken
     *
     * @param musterRoll Muster roll to validate
     * @return true if valid (has required V1 fields)
     */
    public boolean isV1Compatible(MusterRoll musterRoll) {
        if (musterRoll == null) {
            return false;
        }

        // V1 Required fields that must always be present
        boolean hasId = StringUtils.isNotBlank(musterRoll.getId());
        boolean hasTenantId = StringUtils.isNotBlank(musterRoll.getTenantId());
        boolean hasRegisterId = StringUtils.isNotBlank(musterRoll.getRegisterId());
        boolean hasStartDate = musterRoll.getStartDate() != null;

        boolean hasRequiredFields = hasId && hasTenantId && hasRegisterId && hasStartDate;

        if (!hasRequiredFields) {
            log.warn(
                "Muster roll {} missing required V1 fields (idPresent={}, tenantIdPresent={}, registerIdPresent={}, startDatePresent={})",
                musterRoll.getId(),
                hasId,
                hasTenantId,
                hasRegisterId,
                hasStartDate
            );
        }

        return hasRequiredFields;
    }

    /**
     * Log muster roll version information
     *
     * @param musterRoll Muster roll
     * @param operation Operation being performed
     */
    public void logMusterRollVersion(MusterRoll musterRoll, String operation) {
        if (musterRoll == null) {
            return;
        }

        String version = getMusterRollVersion(musterRoll);
        String periodInfo = isV2MusterRoll(musterRoll)
            ? " (Period: " + musterRoll.getBillingPeriodId() + ")"
            : " (No period linkage)";

        log.info("{} - Tenant: {} | Muster Roll: {} | Version: {} | Register: {}{}",
                operation,
                musterRoll.getTenantId(),
                musterRoll.getId(),
                version,
                musterRoll.getRegisterId(),
                periodInfo);
    }
}
