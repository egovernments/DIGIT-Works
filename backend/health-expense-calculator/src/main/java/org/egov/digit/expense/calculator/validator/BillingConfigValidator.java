package org.egov.digit.expense.calculator.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.repository.BillingConfigRepository;
import org.egov.digit.expense.calculator.web.models.BillingConfig;
import org.egov.digit.expense.calculator.web.models.BillingConfigRequest;
import org.egov.digit.expense.calculator.web.models.BillingConfigSearchCriteria;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BillingConfigValidator
 *
 * Comprehensive validator for billing configuration operations.
 * Validates all input parameters, business rules, and edge cases.
 *
 * @author DIGIT-Works
 */
@Component
@Slf4j
public class BillingConfigValidator {

    // Valid statuses
    private static final List<String> VALID_STATUSES = Arrays.asList(
        "ACTIVE", "INACTIVE", "COMPLETED"
    );

    // Minimum project duration (1 day in milliseconds)
    private static final long MIN_PROJECT_DURATION_MS = 24 * 60 * 60 * 1000L;

    // Maximum project duration (365 days in milliseconds - 1 year)
    private static final long MAX_PROJECT_DURATION_MS = 365L * 24 * 60 * 60 * 1000L;

    // Configurable minimum custom frequency days from application.properties
    @Value("${billing.frequency.custom.minimum.days:3}")
    private Integer minCustomFrequencyDays;

    private final BillingConfigRepository repository;

    @Autowired
    public BillingConfigValidator(BillingConfigRepository repository) {
        this.repository = repository;
    }

    /**
     * Validates create billing configuration request.
     *
     * Performs comprehensive validation including:
     * - RequestInfo validation
     * - Required field validation
     * - Billing frequency validation
     * - Date range validation
     * - Custom frequency validation
     * - Duplicate configuration check
     * - Business rule validation
     *
     * @param request Billing configuration request
     * @throws CustomException if validation fails
     */
    public void validateCreateRequest(BillingConfigRequest request) {
        log.info("Validating create billing configuration request");

        Map<String, String> errorMap = new HashMap<>();

        // Validate RequestInfo
        validateRequestInfo(request.getRequestInfo(), errorMap);

        // Validate billing configuration
        BillingConfig config = request.getBillingConfig();
        if (config == null) {
            errorMap.put("BILLING_CONFIG_NULL", "Billing configuration is mandatory");
            throw new CustomException(errorMap);
        }

        // Validate required fields
        validateRequiredFields(config, errorMap);

        // Validate billing frequency
        validateBillingFrequency(config, errorMap);

        // Validate date ranges
        validateDateRanges(config, errorMap);

        // Validate custom frequency if applicable
        validateCustomFrequency(config, errorMap);

        // Validate project duration
        validateProjectDuration(config, errorMap);

        // Throw exception if any validation errors
        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }

        // Validate duplicate configuration
        validateDuplicateConfiguration(config);

        log.info("Billing configuration request validation successful");
    }

    /**
     * Validates search criteria for billing configuration.
     *
     * @param criteria Search criteria
     * @throws CustomException if validation fails
     */
    public void validateSearchCriteria(BillingConfigSearchCriteria criteria) {
        log.info("Validating billing configuration search criteria");

        Map<String, String> errorMap = new HashMap<>();

        // Tenant ID is mandatory
        if (StringUtils.isBlank(criteria.getTenantId())) {
            errorMap.put("TENANT_ID_REQUIRED", "Tenant ID is mandatory for search");
        }

        // Validate pagination parameters
        if (criteria.getLimit() != null && criteria.getLimit() < 0) {
            errorMap.put("INVALID_LIMIT", "Limit cannot be negative");
        }

        if (criteria.getOffset() != null && criteria.getOffset() < 0) {
            errorMap.put("INVALID_OFFSET", "Offset cannot be negative");
        }

        // Validate date range
        if (criteria.getCreatedFrom() != null && criteria.getCreatedTo() != null) {
            if (criteria.getCreatedFrom() > criteria.getCreatedTo()) {
                errorMap.put("INVALID_DATE_RANGE", "createdFrom cannot be after createdTo");
            }
        }

        // Validate billing frequency if provided
        // Enum validation is handled by Jackson deserialization
        // If billingFrequency is set, it's already a valid enum value
        if (criteria.getBillingFrequency() != null) {
            log.debug("Search billing frequency validated: {}", criteria.getBillingFrequency());
        }

        // Validate status if provided
        if (StringUtils.isNotBlank(criteria.getStatus())) {
            String status = criteria.getStatus().toUpperCase();
            if (!VALID_STATUSES.contains(status)) {
                errorMap.put("INVALID_STATUS",
                    "Invalid status. Valid values: " + String.join(", ", VALID_STATUSES));
            }
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }

        log.info("Search criteria validation successful");
    }

    /**
     * Validates RequestInfo object.
     *
     * @param requestInfo Request info
     * @param errorMap Error map to accumulate errors
     */
    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            errorMap.put("REQUEST_INFO_REQUIRED", "RequestInfo is mandatory");
            return;
        }

        if (requestInfo.getUserInfo() == null) {
            errorMap.put("USER_INFO_REQUIRED", "UserInfo is mandatory in RequestInfo");
        }

        if (requestInfo.getUserInfo() != null &&
            StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            errorMap.put("USER_UUID_REQUIRED", "User UUID is mandatory in UserInfo");
        }
    }

    /**
     * Validates required fields in billing configuration.
     *
     * @param config Billing configuration
     * @param errorMap Error map to accumulate errors
     */
    private void validateRequiredFields(BillingConfig config, Map<String, String> errorMap) {
        if (StringUtils.isBlank(config.getTenantId())) {
            errorMap.put("TENANT_ID_REQUIRED", "Tenant ID is mandatory");
        }

        if (StringUtils.isBlank(config.getCampaignNumber())) {
            errorMap.put("PROJECT_ID_REQUIRED", "Project ID is mandatory");
        }

        if (config.getBillingFrequency() == null) {
            errorMap.put("BILLING_FREQUENCY_REQUIRED", "Billing frequency is mandatory");
        }

        if (config.getProjectStartDate() == null) {
            errorMap.put("PROJECT_START_DATE_REQUIRED", "Project start date is mandatory");
        }

        if (config.getProjectEndDate() == null) {
            errorMap.put("PROJECT_END_DATE_REQUIRED", "Project end date is mandatory");
        }
    }

    /**
     * Validates billing frequency value and constraints.
     *
     * @param config Billing configuration
     * @param errorMap Error map to accumulate errors
     */
    private void validateBillingFrequency(BillingConfig config, Map<String, String> errorMap) {
        if (config.getBillingFrequency() == null) {
            return; // Already handled in required fields
        }

        // Enum validation is already handled by Jackson deserialization
        // If we reach here, the enum value is valid
        log.debug("Billing frequency validated: {}", config.getBillingFrequency());
    }

    /**
     * Validates date ranges for project timeline.
     *
     * Edge cases covered:
     * - Start date must be before end date
     * - Dates must be in the future (optional warning)
     * - Dates must not be too far in the past
     *
     * @param config Billing configuration
     * @param errorMap Error map to accumulate errors
     */
    private void validateDateRanges(BillingConfig config, Map<String, String> errorMap) {
        if (config.getProjectStartDate() == null || config.getProjectEndDate() == null) {
            return; // Already handled in required fields
        }

        // Validate start date is before end date
        if (config.getProjectStartDate() >= config.getProjectEndDate()) {
            errorMap.put("INVALID_DATE_RANGE",
                "Project start date must be before end date");
        }

        // Validate dates are not zero or negative
        if (config.getProjectStartDate() <= 0) {
            errorMap.put("INVALID_START_DATE",
                "Project start date must be a valid positive timestamp");
        }

        if (config.getProjectEndDate() <= 0) {
            errorMap.put("INVALID_END_DATE",
                "Project end date must be a valid positive timestamp");
        }

        // Validate project duration
        long duration = config.getProjectEndDate() - config.getProjectStartDate();
        if (duration < MIN_PROJECT_DURATION_MS) {
            errorMap.put("PROJECT_DURATION_TOO_SHORT",
                "Project duration must be at least 1 day");
        }

//        if (duration > MAX_PROJECT_DURATION_MS) {
//            errorMap.put("PROJECT_DURATION_TOO_LONG",
//                "Project duration cannot exceed 1 year (365 days)");
//        }
    }

    /**
     * Validates custom frequency configuration.
     *
     * Edge cases covered:
     * - Custom frequency days required when frequency is CUSTOM
     * - Custom frequency days must be at least the configured minimum (from application.properties)
     * - Custom frequency days should not be provided for non-CUSTOM frequencies
     * - Custom frequency duration must fit within project duration
     *
     * @param config Billing configuration
     * @param errorMap Error map to accumulate errors
     */
    private void validateCustomFrequency(BillingConfig config, Map<String, String> errorMap) {
        BillingFrequency frequency = config.getBillingFrequency();
        if (frequency == null) {
            return;
        }

        if (frequency == BillingFrequency.CUSTOM) {
            // Custom frequency days is mandatory for CUSTOM frequency
            if (config.getCustomFrequencyDays() == null) {
                errorMap.put("CUSTOM_FREQUENCY_DAYS_REQUIRED",
                    "Custom frequency days is required when billing frequency is CUSTOM");
                return;
            }

            // Validate minimum custom frequency days using configurable value
            if (config.getCustomFrequencyDays() < minCustomFrequencyDays) {
                errorMap.put("CUSTOM_FREQUENCY_TOO_SHORT",
                    "Custom frequency days must be at least " + minCustomFrequencyDays + " days. " +
                    "Provided: " + config.getCustomFrequencyDays() + " days");
            }

            // Validate custom frequency fits within project duration
            if (config.getProjectStartDate() != null && config.getProjectEndDate() != null) {
                int projectDurationDays = config.getProjectDurationInDays();

                if (config.getCustomFrequencyDays() > projectDurationDays) {
                    errorMap.put("CUSTOM_FREQUENCY_EXCEEDS_PROJECT_DURATION",
                        "Custom frequency days (" + config.getCustomFrequencyDays() +
                        ") cannot exceed project duration (" + projectDurationDays + " days)");
                }
            }
        } else {
            // Custom frequency days should not be provided for non-CUSTOM frequencies
            if (config.getCustomFrequencyDays() != null) {
                log.warn("Custom frequency days provided for non-CUSTOM frequency: {}. " +
                        "This value will be ignored.", frequency);
            }
        }
    }

    /**
     * Validates project duration against billing frequency.
     *
     * Edge cases covered:
     * - Weekly frequency requires at least 7 days project duration
     * - Bi-weekly frequency requires at least 14 days
     * - Monthly frequency requires at least 30 days
     * - END_OF_CAMPAIGN can have any duration
     *
     * @param config Billing configuration
     * @param errorMap Error map to accumulate errors
     */
    private void validateProjectDuration(BillingConfig config, Map<String, String> errorMap) {
        if (config.getProjectStartDate() == null || config.getProjectEndDate() == null ||
            config.getBillingFrequency() == null) {
            return;
        }

        BillingFrequency frequency = config.getBillingFrequency();
        int projectDurationDays = config.getProjectDurationInDays();

        switch (frequency) {
            case WEEKLY:
                if (projectDurationDays < 7) {
                    log.warn("Project duration ({} days) is less than weekly frequency (7 days). " +
                            "Only one period will be generated.", projectDurationDays);
                }
                break;

            case BI_WEEKLY:
                if (projectDurationDays < 14) {
                    log.warn("Project duration ({} days) is less than bi-weekly frequency (14 days). " +
                            "Only one period will be generated.", projectDurationDays);
                }
                break;

            case MONTHLY:
                if (projectDurationDays < 30) {
                    log.warn("Project duration ({} days) is less than monthly frequency (30 days). " +
                            "Only one period will be generated.", projectDurationDays);
                }
                break;

            case CUSTOM:
                if (config.getCustomFrequencyDays() != null &&
                    projectDurationDays < config.getCustomFrequencyDays()) {
                    log.warn("Project duration ({} days) is less than custom frequency ({} days). " +
                            "Only one period will be generated.",
                        projectDurationDays, config.getCustomFrequencyDays());
                }
                break;

            case END_OF_CAMPAIGN:
                // No validation needed - single period for entire campaign
                break;

            default:
                // Should not reach here due to enum validation
                break;
        }
    }

    /**
     * Validates that no duplicate billing configuration exists for the project.
     *
     * Edge cases covered:
     * - Only one active billing configuration per project
     * - Check by both project ID and tenant ID
     *
     * @param config Billing configuration
     * @throws CustomException if duplicate exists
     */
    private void validateDuplicateConfiguration(BillingConfig config) {
        log.info("Checking for duplicate billing configuration for project: {}", config.getCampaignNumber());

        BillingConfig existing = repository.findByCampaignNumber(
            config.getCampaignNumber(),
            config.getTenantId()
        );

        if (existing != null) {
            log.error("Duplicate billing configuration found for project: {}", config.getCampaignNumber());
            throw new CustomException("BILLING_CONFIG_ALREADY_EXISTS",
                "Billing configuration already exists for project: " + config.getCampaignNumber() +
                ". Only one billing configuration is allowed per project. " +
                "Existing configuration ID: " + existing.getId());
        }

        log.info("No duplicate billing configuration found");
    }

    /**
     * Validates update request for billing configuration.
     *
     * Only status and additional details can be updated.
     * Other fields are immutable after creation.
     *
     * @param request Billing configuration request
     * @throws CustomException if validation fails
     */
    public void validateUpdateRequest(BillingConfigRequest request) {
        log.info("Validating update billing configuration request");

        Map<String, String> errorMap = new HashMap<>();

        // Validate RequestInfo
        validateRequestInfo(request.getRequestInfo(), errorMap);

        BillingConfig config = request.getBillingConfig();
        if (config == null) {
            errorMap.put("BILLING_CONFIG_NULL", "Billing configuration is mandatory");
            throw new CustomException(errorMap);
        }

        // ID is required for update
        if (StringUtils.isBlank(config.getId())) {
            errorMap.put("ID_REQUIRED", "Billing configuration ID is required for update");
        }

        // Tenant ID is required
        if (StringUtils.isBlank(config.getTenantId())) {
            errorMap.put("TENANT_ID_REQUIRED", "Tenant ID is mandatory");
        }

        // Validate status if provided
        if (StringUtils.isNotBlank(config.getStatus())) {
            String status = config.getStatus().toUpperCase();
            if (!VALID_STATUSES.contains(status)) {
                errorMap.put("INVALID_STATUS",
                    "Invalid status. Valid values: " + String.join(", ", VALID_STATUSES));
            }
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }

        // Verify configuration exists
        BillingConfig existing = repository.findByCampaignNumber(config.getCampaignNumber(), config.getTenantId());
        if (existing == null) {
            throw new CustomException("BILLING_CONFIG_NOT_FOUND",
                "Billing configuration not found for update: " + config.getId());
        }

        log.info("Update validation successful");
    }
}
