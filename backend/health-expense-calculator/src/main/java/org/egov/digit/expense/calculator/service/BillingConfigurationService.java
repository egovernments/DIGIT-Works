package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.repository.BillingConfigRepository;
import org.egov.digit.expense.calculator.repository.ExpenseCalculatorRepository;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.validator.BillingConfigValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * BillingConfigurationService
 *
 * Main service for managing campaign-level billing configuration lifecycle.
 * Handles create, search, and update operations with proper validation.
 *
 * Key responsibilities:
 * - Create billing configuration with period generation for campaigns
 * - Search billing configurations with flexible criteria
 * - Update billing configuration (status, frequency, dates)
 * - Recalculate periods when configuration changes
 * - Retrieve billing periods for a campaign
 * - Prevent critical updates after campaign start date
 *
 * @author DIGIT-Works
 */
@Service
@Slf4j
public class BillingConfigurationService {

    private static final DateTimeFormatter DT_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

    private final BillingConfigRepository repository;
    private final PeriodGenerationService periodGenerationService;
    private final BillingConfigValidator validator;
    private final ResponseInfoFactory responseInfoFactory;
    private final ExpenseCalculatorRepository expenseCalculatorRepository;

    @Autowired
    public BillingConfigurationService(BillingConfigRepository repository,
                                      PeriodGenerationService periodGenerationService,
                                      BillingConfigValidator validator,
                                      ResponseInfoFactory responseInfoFactory,
                                      ExpenseCalculatorRepository expenseCalculatorRepository) {
        this.repository = repository;
        this.periodGenerationService = periodGenerationService;
        this.validator = validator;
        this.responseInfoFactory = responseInfoFactory;
        this.expenseCalculatorRepository = expenseCalculatorRepository;
    }

    /**
     * Creates billing configuration for a campaign.
     *
     * This enables intermediate billing for the campaign by:
     * 1. Validating the configuration
     * 2. Checking for duplicates
     * 3. Enriching with audit details
     * 4. Saving configuration
     * 5. Generating billing periods
     * 6. Saving billing periods
     *
     * @param request Billing configuration request
     * @return Created configuration with generated periods
     * @throws CustomException if validation fails or duplicate exists
     */
    @Transactional
    public BillingConfigResponse createBillingConfig(BillingConfigRequest request) {
        BillingConfig config = request.getBillingConfig();

        log.info("Creating billing configuration for campaign: {} with frequency: {}",
            config.getCampaignNumber(), config.getBillingFrequency());

        // Step 1: Validate configuration
        validator.validateCreateRequest(request);
        log.info("Validation successful for billing configuration");

        // Step 2: Enrich with audit details and system-generated fields
        enrichForCreate(config, request.getRequestInfo());
        log.info("Enriched billing configuration with ID: {}", config.getId());

        // Step 3: Save billing configuration
        try {
            repository.save(config);
            log.info("Billing configuration saved successfully: {}", config.getId());
        } catch (Exception e) {
            log.error("Error saving billing configuration", e);
            throw new CustomException("DB_ERROR", "Error saving billing configuration: " + e.getMessage());
        }

        // Step 4: Generate billing periods
        List<BillingPeriod> periods;
        try {
            periods = periodGenerationService.generatePeriods(config);
            log.info("Generated {} billing periods", periods.size());
        } catch (Exception e) {
            log.error("Error generating billing periods", e);
            throw new CustomException("PERIOD_GENERATION_ERROR",
                "Error generating billing periods: " + e.getMessage());
        }

        // Step 5: Save billing periods
        try {
            repository.savePeriods(periods);
            log.info("Billing periods saved successfully");
        } catch (Exception e) {
            log.error("Error saving billing periods", e);
            throw new CustomException("DB_ERROR", "Error saving billing periods: " + e.getMessage());
        }

        // Step 6: Build response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(
            request.getRequestInfo(), true);

        BillingConfigResponse response = BillingConfigResponse.builder()
            .responseInfo(responseInfo)
            .billingConfig(config)
            .periods(periods)
            .build()
            .withTotalPeriods();

        log.info("Billing configuration created successfully for campaign: {} with {} periods",
            config.getCampaignNumber(), periods.size());

        return response;
    }

    /**
     * Searches for billing configurations based on criteria.
     *
     * Supports flexible search with:
     * - Single or multiple project IDs
     * - Billing frequency filter
     * - Status filter
     * - Date range filter
     * - Optional period inclusion
     * - Pagination
     *
     * @param request Search request with criteria
     * @return List of matching configurations with optional periods
     * @throws CustomException if validation fails
     */
    public BillingConfigResponse searchBillingConfigs(BillingConfigSearchRequest request) {
        BillingConfigSearchCriteria criteria = request.getSearchCriteria();

        log.info("Searching billing configurations with criteria: {}", criteria);

        // Validate search criteria
        validator.validateSearchCriteria(criteria);

        // Search configurations
        List<BillingConfig> configs = repository.search(criteria);

        log.info("Found {} billing configurations", configs != null ? configs.size() : 0);

        // Load periods if requested
        List<BillingPeriod> allPeriods = new ArrayList<>();
        if (criteria.shouldIncludePeriods() && configs != null && !configs.isEmpty()) {
            log.info("Loading billing periods for {} configurations", configs.size());

            for (BillingConfig config : configs) {
                List<BillingPeriod> periods = repository.findPeriodsByConfigId(config.getId(), config.getTenantId());
                if (periods != null) {
                    allPeriods.addAll(periods);
                }
            }

            log.info("Loaded {} billing periods", allPeriods.size());
        }

        // Build response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(
            request.getRequestInfo(), true);

        // For search, we return the first config with its periods
        // (In most cases, search will return one config per project)
        BillingConfig firstConfig = (configs != null && !configs.isEmpty()) ? configs.get(0) : null;

        BillingConfigResponse response = BillingConfigResponse.builder()
            .responseInfo(responseInfo)
            .billingConfig(firstConfig)
            .periods(allPeriods)
            .build()
            .withTotalPeriods();

        return response;
    }

    /**
     * Gets billing configuration for a specific campaign.
     *
     * Returns null if campaign doesn't have billing configuration (V1 mode).
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return Billing configuration or null if not found
     */
    public BillingConfig getBillingConfig(String campaignNumber, String tenantId) {
        log.info("Getting billing configuration for campaign: {} in tenant: {}", campaignNumber, tenantId);

        BillingConfig config = repository.findByCampaignNumber(campaignNumber, tenantId);

        if (config != null) {
            log.info("Found billing configuration: {} with frequency: {}",
                config.getId(), config.getBillingFrequency());
        } else {
            log.info("No billing configuration found for campaign: {} (V1 mode)", campaignNumber);
        }

        return config;
    }

    /**
     * Gets billing configuration by campaign number.
     *
     * Alias method for getBillingConfig for better clarity.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return Billing configuration or null if not found
     */
    public BillingConfig getBillingConfigByCampaignNumber(String campaignNumber, String tenantId) {
        return getBillingConfig(campaignNumber, tenantId);
    }

    /**
     * Gets billing periods for a specific campaign.
     *
     * Returns periods ordered by period number.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return List of billing periods or empty list if not found
     */
    public List<BillingPeriod> getBillingPeriods(String campaignNumber, String tenantId) {
        log.info("Getting billing periods for campaign: {} in tenant: {}", campaignNumber, tenantId);

        List<BillingPeriod> periods = repository.findPeriodsByCampaignNumber(campaignNumber, tenantId);

        if (periods != null && !periods.isEmpty()) {
            log.info("Found {} billing periods for campaign: {}", periods.size(), campaignNumber);
        } else {
            log.info("No billing periods found for campaign: {}", campaignNumber);
            periods = new ArrayList<>();
        }

        return periods;
    }

    /**
     * Gets billing periods by campaign number.
     *
     * Alias method for getBillingPeriods for better clarity.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return List of billing periods or empty list if not found
     */
    public List<BillingPeriod> getBillingPeriodsByCampaignNumber(String campaignNumber, String tenantId) {
        return getBillingPeriods(campaignNumber, tenantId);
    }

    /**
     * Gets a specific billing period by period number.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @param periodNumber Period number
     * @return Billing period or null if not found
     */
    public BillingPeriod getBillingPeriod(String campaignNumber, String tenantId, Integer periodNumber) {
        log.info("Getting billing period {} for campaign: {}", periodNumber, campaignNumber);

        List<BillingPeriod> periods = repository.findPeriodsByCampaignNumber(campaignNumber, tenantId);

        if (periods == null || periods.isEmpty()) {
            log.warn("No billing periods found for campaign: {}", campaignNumber);
            return null;
        }

        for (BillingPeriod period : periods) {
            if (period.getPeriodNumber().equals(periodNumber)) {
                log.info("Found billing period: {} for period number: {}", period.getId(), periodNumber);
                return period;
            }
        }

        log.warn("Billing period {} not found for campaign: {}", periodNumber, campaignNumber);
        return null;
    }

    /**
     * Get billing period by ID
     * Used for UI-driven period selection in V2 billing
     *
     * @param periodId Billing period ID
     * @param tenantId Tenant ID
     * @return BillingPeriod if found, null otherwise
     */
    public BillingPeriod getBillingPeriodById(String periodId, String tenantId) {
        log.info("Getting billing period by ID: {}", periodId);

        BillingPeriodSearchCriteria criteria = BillingPeriodSearchCriteria.builder()
                .tenantId(tenantId)
                .ids(java.util.Collections.singletonList(periodId))
                .build();

        List<BillingPeriod> periods = repository.searchPeriods(criteria);

        if (periods == null || periods.isEmpty()) {
            log.warn("Billing period not found for ID: {}", periodId);
            return null;
        }

        log.info("Found billing period: {} (period number: {})", periodId, periods.get(0).getPeriodNumber());
        return periods.get(0);
    }

    /**
     * Searches for billing periods based on flexible criteria.
     *
     * This method provides comprehensive period search capabilities supporting:
     * - Filter by IDs, billing config, campaign, project
     * - Filter by status, period numbers
     * - Filter by date range
     * - Filter by bill existence
     * - Pagination support
     *
     * @param request Billing period search request
     * @return Search response with matching periods
     */
    public BillingPeriodSearchResponse searchBillingPeriods(BillingPeriodSearchRequest request) {
        BillingPeriodSearchCriteria criteria = request.getSearchCriteria();

        log.info("Searching billing periods with criteria - tenant: {}, campaign: {}, status: {}",
            criteria.getTenantId(),
            criteria.getCampaignNumber(),
            criteria.getStatus());

        // Search periods with criteria
        List<BillingPeriod> periods = repository.searchPeriods(criteria);

        // Count total periods (without pagination)
        int totalCount = repository.countPeriods(criteria);

        // Create response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(
            request.getRequestInfo(), true);

        String message = String.format("Found %d billing periods (total: %d matching criteria)",
            periods.size(), totalCount);

        BillingPeriodSearchResponse response = BillingPeriodSearchResponse.builder()
            .responseInfo(responseInfo)
            .billingPeriods(periods)
            .totalCount(totalCount)
            .message(message)
            .build();

        log.info("Billing period search completed - returned: {}, total: {}",
            periods.size(), totalCount);

        return response;
    }

    /**
     * Updates billing configuration status and metadata.
     *
     * Supports updating:
     * - Status and additional details (always allowed)
     * - Billing frequency and dates (only before campaign start)
     *
     * If frequency or dates are updated, periods are recalculated automatically.
     *
     * @param request Update request
     * @return Updated configuration with new periods if recalculated
     * @throws CustomException if validation fails or configuration not found
     */
    @Transactional
    public BillingConfigResponse updateBillingConfig(BillingConfigRequest request) {
        BillingConfig config = request.getBillingConfig();

        log.info("Updating billing configuration: {}", config.getId());

        // Validate update request
        validator.validateUpdateRequest(request);

        // Fetch existing configuration
        BillingConfig existingConfig = repository.findByCampaignNumber(config.getCampaignNumber(), config.getTenantId());

        if (existingConfig == null) {
            throw new CustomException("CONFIG_NOT_FOUND",
                "Billing configuration not found. campaignNumber: " + config.getCampaignNumber() +
                ", tenantId: " + config.getTenantId());
        }

        // Updates cannot modify project timeline. Force incoming dates to existing values.
        config.setProjectStartDate(existingConfig.getProjectStartDate());
        config.setProjectEndDate(existingConfig.getProjectEndDate());

        // Check if campaign has already started - prevent critical updates after start
        long currentTime = System.currentTimeMillis();
        boolean campaignStarted = currentTime >= existingConfig.getProjectStartDate();

        // Check if frequency or dates are being updated
        boolean frequencyChanged = config.getBillingFrequency() != null &&
            !config.getBillingFrequency().equals(existingConfig.getBillingFrequency());
        boolean customFrequencyChanged = config.getCustomFrequencyDays() != null &&
            !config.getCustomFrequencyDays().equals(existingConfig.getCustomFrequencyDays());
        boolean startDateChanged = false;
        boolean endDateChanged = false;

        boolean configStructureChanged = frequencyChanged || customFrequencyChanged ||
            startDateChanged || endDateChanged;

        // Ensure we carry forward fields that are not present in the update request
        mergeWithExistingConfig(existingConfig, config);

        // Validate: Cannot update config structure after campaign start
        if (campaignStarted && configStructureChanged) {
            log.error("Cannot update billing configuration structure after campaign start date. Campaign: {}",
                config.getCampaignNumber());
            throw new CustomException("CONFIG_UPDATE_NOT_ALLOWED",
                "Cannot update billing frequency or dates after campaign has started. " +
                "Campaign start date: " + existingConfig.getProjectStartDate() +
                ", Current time: " + currentTime);
        }

        // Enrich with update audit details
        enrichForUpdate(config, request.getRequestInfo());

        // Update configuration
        try {
            repository.update(config);
            log.info("Billing configuration updated successfully: {}", config.getId());
        } catch (Exception e) {
            log.error("Error updating billing configuration", e);
            throw new CustomException("DB_ERROR", "Error updating billing configuration: " + e.getMessage());
        }

        // Fetch updated configuration
        BillingConfig updated = repository.findByCampaignNumber(config.getCampaignNumber(), config.getTenantId());

        if (updated == null) {
            throw new CustomException("CONFIG_NOT_FOUND",
                "Billing configuration not found after update. configId: " + config.getId() +
                ", campaignNumber: " + config.getCampaignNumber() +
                ", tenantId: " + config.getTenantId());
        }

        // If config structure changed, recalculate periods
        List<BillingPeriod> periods;
        if (configStructureChanged) {
            log.info("Configuration structure changed - recalculating billing periods");
            periods = recalculatePeriods(existingConfig, updated, request.getRequestInfo());
        } else {
            // Just fetch existing periods
            periods = repository.findPeriodsByConfigId(updated.getId(), updated.getTenantId());
        }

        // Build response
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(
            request.getRequestInfo(), true);

        BillingConfigResponse response = BillingConfigResponse.builder()
            .responseInfo(responseInfo)
            .billingConfig(updated)
            .periods(periods)
            .build()
            .withTotalPeriods();

        log.info("Billing configuration update completed for: {}", updated.getId());

        return response;
    }

    /**
     * Updates billing period status and metrics.
     *
     * Used during bill generation to track period progress.
     *
     * @param period Billing period to update
     */
    @Transactional
    public void updateBillingPeriod(BillingPeriod period) {
        log.info("Updating billing period: {} with status: {}", period.getId(), period.getStatus());

        try {
            repository.updatePeriod(period);
            log.info("Billing period updated successfully: {}", period.getId());
        } catch (Exception e) {
            log.error("Error updating billing period", e);
            throw new CustomException("DB_ERROR", "Error updating billing period: " + e.getMessage());
        }
    }

    /**
     * Recalculates billing periods when configuration is updated.
     *
     * This method:
     * 1. Deprecates old periods (marks as DEPRECATED)
     * 2. Generates new periods based on updated configuration
     * 3. Saves new periods to database
     *
     * Note: Old periods are not deleted, just marked as deprecated for audit trail.
     *
     * @param oldConfig Previous billing configuration
     * @param newConfig Updated billing configuration
     * @param requestInfo Request info for audit details
     * @return List of newly generated billing periods
     * @throws CustomException if period recalculation fails
     */
    @Transactional
    public List<BillingPeriod> recalculatePeriods(BillingConfig oldConfig,
                                                  BillingConfig newConfig,
                                                  RequestInfo requestInfo) {
        log.info("Recalculating billing periods for config: {} (campaign: {})",
            newConfig.getId(), newConfig.getCampaignNumber());

        // Step 1: Deprecate old periods
        List<BillingPeriod> oldPeriods = repository.findPeriodsByConfigId(oldConfig.getId(), oldConfig.getTenantId());

        if (oldPeriods != null && !oldPeriods.isEmpty()) {
            log.info("Deprecating {} old billing periods", oldPeriods.size());

            for (BillingPeriod oldPeriod : oldPeriods) {
                oldPeriod.setIsDeprecated(true);
                oldPeriod.setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                oldPeriod.setLastModifiedTime(System.currentTimeMillis());

                try {
                    repository.updatePeriod(oldPeriod);
                } catch (Exception e) {
                    log.error("Error deprecating period: {}", oldPeriod.getId(), e);
                    throw new CustomException("PERIOD_UPDATE_ERROR",
                        "Error deprecating old period: " + e.getMessage());
                }
            }

            log.info("Successfully deprecated {} old periods", oldPeriods.size());
        } else {
            log.info("No old periods to deprecate");
        }

        // Determine starting period number for regenerated periods
        int nextPeriodNumber = oldPeriods != null && !oldPeriods.isEmpty()
            ? oldPeriods.stream()
                .map(BillingPeriod::getPeriodNumber)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1
            : 1;

        // Step 2: Generate new periods
        List<BillingPeriod> newPeriods;
        try {
            newPeriods = periodGenerationService.generatePeriods(newConfig, nextPeriodNumber);
            log.info("Generated {} new billing periods", newPeriods.size());
        } catch (Exception e) {
            log.error("Error generating new billing periods", e);
            throw new CustomException("PERIOD_GENERATION_ERROR",
                "Error generating new billing periods: " + e.getMessage());
        }

        // Step 3: Save new periods
        try {
            repository.savePeriods(newPeriods);
            log.info("Successfully saved {} new billing periods", newPeriods.size());
        } catch (Exception e) {
            log.error("Error saving new billing periods", e);
            throw new CustomException("DB_ERROR",
                "Error saving new billing periods: " + e.getMessage());
        }

        log.info("Period recalculation completed. Old periods: {}, New periods: {}",
            oldPeriods != null ? oldPeriods.size() : 0, newPeriods.size());

        return newPeriods;
    }

    /**
     * Checks if a campaign has billing configuration enabled.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return true if campaign has active billing configuration
     */
    public boolean hasActiveBillingConfig(String campaignNumber, String tenantId) {
        BillingConfig config = getBillingConfig(campaignNumber, tenantId);
        return config != null && config.isActive();
    }

    /**
     * Gets billing frequency for a campaign.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return Billing frequency or null if no config exists
     */
    public BillingFrequency getBillingFrequency(String campaignNumber, String tenantId) {
        BillingConfig config = getBillingConfig(campaignNumber, tenantId);
        return config != null ? config.getBillingFrequency() : null;
    }

    /**
     * Enriches billing configuration for create operation.
     *
     * Adds system-generated fields:
     * - Unique ID
     * - Status (ACTIVE)
     * - Created by user
     * - Created timestamp
     *
     * @param config Billing configuration
     * @param requestInfo Request info containing user details
     */
    private void enrichForCreate(BillingConfig config, RequestInfo requestInfo) {
        // Generate unique ID
        config.setId(UUID.randomUUID().toString());

        // Set status to ACTIVE
        config.setStatus("ACTIVE");

        // Set audit fields
        String userId = requestInfo.getUserInfo().getUuid();
        long currentTime = System.currentTimeMillis();

        config.setCreatedBy(userId);
        config.setCreatedTime(currentTime);
        config.setLastModifiedBy(userId);
        config.setLastModifiedTime(currentTime);

        log.debug("Enriched billing configuration - ID: {}, Status: {}, CreatedBy: {}",
            config.getId(), config.getStatus(), config.getCreatedBy());
    }

    /**
     * Enriches billing configuration for update operation.
     *
     * Updates audit fields:
     * - Last modified by user
     * - Last modified timestamp
     *
     * @param config Billing configuration
     * @param requestInfo Request info containing user details
     */
    private void enrichForUpdate(BillingConfig config, RequestInfo requestInfo) {
        String userId = requestInfo.getUserInfo().getUuid();
        long currentTime = System.currentTimeMillis();

        config.setLastModifiedBy(userId);
        config.setLastModifiedTime(currentTime);

        log.debug("Enriched billing configuration for update - ID: {}, LastModifiedBy: {}",
            config.getId(), config.getLastModifiedBy());
    }

    /**
     * Merges existing configuration values into the update request wherever the request omits fields.
     *
     * @param existing Existing billing configuration from DB
     * @param incoming Incoming update payload
     */
    private void mergeWithExistingConfig(BillingConfig existing, BillingConfig incoming) {
        incoming.setId(existing.getId());
        incoming.setTenantId(existing.getTenantId());
        incoming.setProjectId(existing.getProjectId());
        incoming.setCampaignNumber(existing.getCampaignNumber());

        if (incoming.getBillingFrequency() == null) {
            incoming.setBillingFrequency(existing.getBillingFrequency());
        }

        if (incoming.getCustomFrequencyDays() == null) {
            incoming.setCustomFrequencyDays(existing.getCustomFrequencyDays());
        }

        incoming.setProjectStartDate(existing.getProjectStartDate());
        incoming.setProjectEndDate(existing.getProjectEndDate());

        if (incoming.getStatus() == null) {
            incoming.setStatus(existing.getStatus());
        }

        if (incoming.getAdditionalDetails() == null) {
            incoming.setAdditionalDetails(existing.getAdditionalDetails());
        }

        if (incoming.getCreatedBy() == null) {
            incoming.setCreatedBy(existing.getCreatedBy());
        }

        if (incoming.getCreatedTime() == null) {
            incoming.setCreatedTime(existing.getCreatedTime());
        }
    }

    // -------------------------------------------------------------------------
    // Campaign date update — triggered by update-project-health Kafka event
    // -------------------------------------------------------------------------

    /**
     * Handles campaign start/end date changes received from the project update Kafka event.
     *
     * Guards:
     *  - Null dates, missing config, inactive config → skip silently
     *  - FINAL_AGGREGATE bill exists → block (campaign fully finalized)
     *  - No actual change → skip
     *
     * Routing:
     *  - Start date changed → deprecate all periods, regenerate with new dates
     *  - End date changed only → find concerned period and trim/extend/append
     */
    @Transactional
    public void handleCampaignDateUpdate(Project rootProject, String userId) {
        String projectId = rootProject.getId();
        String tenantId = rootProject.getTenantId();
        Long newStart = rootProject.getStartDate();
        Long newEnd = rootProject.getEndDate();

        // Guard: null dates
        if (newStart == null || newEnd == null) {
            log.warn("Skipping campaign date update — null dates for project: {}", projectId);
            return;
        }

        // Guard: no billing config
        BillingConfig config = repository.findByProjectId(projectId, tenantId);
        if (config == null) {
            log.info("No billing config found for project: {}, skipping campaign date update", projectId);
            return;
        }

        // Guard: inactive/completed config
        if (!config.isActive()) {
            log.info("BillingConfig {} is not ACTIVE, skipping campaign date update", config.getId());
            return;
        }

        // Guard: campaign fully finalized (FINAL_AGGREGATE bill exists)
        if (expenseCalculatorRepository.checkAggregateBillExists(projectId, tenantId)) {
            throw new CustomException("CAMPAIGN_ALREADY_FINALIZED",
                "Cannot update campaign dates — a FINAL_AGGREGATE bill already exists for project: " + projectId);
        }

        Long existingStart = config.getProjectStartDate();
        Long existingEnd = config.getProjectEndDate();

        // Guard: no change
        if (newStart.equals(existingStart) && newEnd.equals(existingEnd)) {
            log.info("No date change detected for project: {}, skipping", projectId);
            return;
        }

        // Guard: invalid date range
        if (newStart >= newEnd) {
            throw new CustomException("INVALID_DATE_RANGE",
                "New start date must be before new end date for project: " + projectId);
        }

        boolean startDateChanged = !newStart.equals(existingStart);
        boolean endDateChanged = !newEnd.equals(existingEnd);

        if (startDateChanged) {
            handleStartDateChange(config, newStart, newEnd, userId);
        } else if (endDateChanged) {
            handleEndDateChange(config, newEnd, userId);
        }
    }

    /**
     * Handles start date change (covers "start only" and "start + end" together).
     * Blocks if campaign has already started. Otherwise deprecates all periods and regenerates.
     */
    private void handleStartDateChange(BillingConfig config, Long newStart, Long newEnd, String userId) {
        long currentTime = System.currentTimeMillis();

        if (currentTime >= config.getProjectStartDate()) {
            throw new CustomException("START_DATE_UPDATE_NOT_ALLOWED",
                "Cannot update start date after campaign has started. " +
                "Campaign: " + config.getCampaignNumber() +
                ", projectStartDate: " + config.getProjectStartDate());
        }

        log.info("Start date changed for campaign: {} — deprecating all periods and regenerating", config.getCampaignNumber());

        // Deprecate all existing active periods
        repository.deprecatePeriodsByConfigId(config.getId(), userId, currentTime);

        // Determine next period number (continues from max deprecated number)
        int nextPeriodNumber = repository.getMaxPeriodNumber(config.getId(), config.getTenantId()) + 1;

        // Update config with both new dates
        config.setProjectStartDate(newStart);
        config.setProjectEndDate(newEnd);
        config.setLastModifiedBy(userId);
        config.setLastModifiedTime(currentTime);
        repository.update(config);

        // Regenerate all periods for the new date range
        List<BillingPeriod> newPeriods = periodGenerationService.generatePeriods(config, nextPeriodNumber);
        repository.savePeriods(newPeriods);

        log.info("Regenerated {} periods for campaign: {} with new start: {}, end: {}",
            newPeriods.size(), config.getCampaignNumber(), newStart, newEnd);
    }

    /**
     * Handles end date change only (start date unchanged).
     *
     * Finds the period whose range contains the new end date:
     *  - Concerned period found + PENDING → trim its end date, deprecate periods after it
     *  - Concerned period found + BILLED/COMPLETED/PROCESSING → block
     *  - No concerned period → extension; append new periods after the last one
     */
    private void handleEndDateChange(BillingConfig config, Long newEnd, String userId) {
        long currentTime = System.currentTimeMillis();
        String configId = config.getId();
        String tenantId = config.getTenantId();

        List<BillingPeriod> activePeriods = repository.findPeriodsByConfigId(configId, tenantId);

        // Edge case: no active periods — regenerate from existing start to new end
        if (activePeriods.isEmpty()) {
            log.info("No active periods for config: {} — regenerating from existing start to new end", configId);
            int nextPeriodNumber = repository.getMaxPeriodNumber(configId, tenantId) + 1;
            config.setProjectEndDate(newEnd);
            config.setLastModifiedBy(userId);
            config.setLastModifiedTime(currentTime);
            repository.update(config);
            List<BillingPeriod> newPeriods = periodGenerationService.generatePeriods(config, nextPeriodNumber);
            repository.savePeriods(newPeriods);
            return;
        }

        // Find the period whose range contains newEnd
        Optional<BillingPeriod> concernedOpt = activePeriods.stream()
            .filter(p -> p.getPeriodStartDate() <= newEnd && p.getPeriodEndDate() >= newEnd)
            .findFirst();

        if (concernedOpt.isPresent()) {
            BillingPeriod concerned = concernedOpt.get();

            if (concerned.isBilled() || concerned.isCompleted() || concerned.isProcessing()) {
                throw new CustomException("END_DATE_UPDATE_BLOCKED",
                    "Cannot update end date — it falls within an immutable period. " +
                    "periodId: " + concerned.getId() +
                    ", periodNumber: " + concerned.getPeriodNumber() +
                    ", status: " + concerned.getStatus());
            }

            // PENDING — safe to trim or deprecate
            if (newEnd.equals(concerned.getPeriodStartDate())) {
                // newEnd falls exactly on this period's start — trimming to zero length would violate
                // chk_period_dates (period_start_date < period_end_date). Deprecate this period entirely.
                log.info("End date equals period {} start date — deprecating period entirely", concerned.getPeriodNumber());
                repository.deprecatePeriodsAfterNumber(configId, concerned.getPeriodNumber() - 1, userId, currentTime);
            } else {
                log.info("End date falls within PENDING period {} — trimming and deprecating tail", concerned.getPeriodNumber());
                repository.updatePeriodEndDate(concerned.getId(), newEnd, userId, currentTime);
                repository.deprecatePeriodsAfterNumber(configId, concerned.getPeriodNumber(), userId, currentTime);
            }

        } else {
            // newEnd is beyond all existing periods — extension
            BillingPeriod lastPeriod = activePeriods.stream()
                .max(Comparator.comparingInt(BillingPeriod::getPeriodNumber))
                .orElseThrow();

            long extensionStart = lastPeriod.getPeriodEndDate() + 1;

            // If the last period is still PENDING and its end date has not yet passed,
            // and it was trimmed short (natural frequency boundary is later than its current end),
            // extend it to the natural boundary before appending new periods.
            long frequencyDurationMs = periodGenerationService.getFrequencyDurationMs(config);
            log.info("Last period [{}] — status: {}, start: {}, end: {}, currentTime: {}, frequencyDuration: {} days",
                lastPeriod.getPeriodNumber(), lastPeriod.getStatus(),
                fmt(lastPeriod.getPeriodStartDate()), fmt(lastPeriod.getPeriodEndDate()),
                fmt(currentTime), frequencyDurationMs / 86_400_000L);
            if (lastPeriod.isPending()
                    && lastPeriod.getPeriodEndDate() >= currentTime
                    && frequencyDurationMs > 0) {
                long naturalEnd = lastPeriod.getPeriodStartDate() + frequencyDurationMs - 1;
                log.info("Natural end of last period [{}]: {}, willExtend: {}",
                    lastPeriod.getPeriodNumber(), fmt(naturalEnd), naturalEnd > lastPeriod.getPeriodEndDate());
                if (naturalEnd > lastPeriod.getPeriodEndDate()) {
                    long updatedEnd = Math.min(naturalEnd, newEnd);
                    repository.updatePeriodEndDate(lastPeriod.getId(), updatedEnd, userId, currentTime);
                    log.info("Extended period [{}] — start: {}, previousEnd: {} → updatedEnd: {} (natural {} boundary)",
                        lastPeriod.getPeriodNumber(),
                        fmt(lastPeriod.getPeriodStartDate()), fmt(lastPeriod.getPeriodEndDate()),
                        fmt(updatedEnd), config.getBillingFrequency());
                    extensionStart = updatedEnd + 1;
                }
            }

            if (extensionStart >= newEnd) {
                log.info("No new periods to append — extensionStart: {} >= newEnd: {} for config: {}",
                    fmt(extensionStart), fmt(newEnd), configId);
            } else {
                log.info("Appending new periods from {} to {} for campaign: {}",
                    fmt(extensionStart), fmt(newEnd), config.getCampaignNumber());

                BillingConfig tailConfig = BillingConfig.builder()
                    .id(config.getId())
                    .tenantId(config.getTenantId())
                    .projectId(config.getProjectId())
                    .campaignNumber(config.getCampaignNumber())
                    .billingFrequency(config.getBillingFrequency())
                    .customFrequencyDays(config.getCustomFrequencyDays())
                    .projectStartDate(extensionStart)
                    .projectEndDate(newEnd)
                    .createdBy(config.getCreatedBy())
                    .build();

                List<BillingPeriod> newPeriods = periodGenerationService.generatePeriods(
                    tailConfig, lastPeriod.getPeriodNumber() + 1);
                repository.savePeriods(newPeriods);
                log.info("Appended {} new periods for campaign: {} ({} to {})",
                    newPeriods.size(), config.getCampaignNumber(), fmt(extensionStart), fmt(newEnd));
            }
        }

        // Always update config end date
        config.setProjectEndDate(newEnd);
        config.setLastModifiedBy(userId);
        config.setLastModifiedTime(currentTime);
        repository.update(config);
    }

    private String fmt(long epochMs) {
        return DT_FORMAT.format(Instant.ofEpochMilli(epochMs));
    }
}
