package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.repository.BillingConfigRepository;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.validator.BillingConfigValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * BillingConfigurationService
 *
 * Main service for managing billing configuration lifecycle.
 * Handles create, search, and update operations with proper validation.
 *
 * Key responsibilities:
 * - Create billing configuration with period generation
 * - Search billing configurations with flexible criteria
 * - Update billing configuration status
 * - Retrieve billing periods for a project
 *
 * @author DIGIT-Works
 */
@Service
@Slf4j
public class BillingConfigurationService {

    private final BillingConfigRepository repository;
    private final PeriodGenerationService periodGenerationService;
    private final BillingConfigValidator validator;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public BillingConfigurationService(BillingConfigRepository repository,
                                      PeriodGenerationService periodGenerationService,
                                      BillingConfigValidator validator,
                                      ResponseInfoFactory responseInfoFactory) {
        this.repository = repository;
        this.periodGenerationService = periodGenerationService;
        this.validator = validator;
        this.responseInfoFactory = responseInfoFactory;
    }

    /**
     * Creates billing configuration for a project.
     *
     * This enables intermediate billing for the project by:
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

        log.info("Creating billing configuration for project: {} with frequency: {}",
            config.getProjectId(), config.getBillingFrequency());

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

        log.info("Billing configuration created successfully for project: {} with {} periods",
            config.getProjectId(), periods.size());

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
                List<BillingPeriod> periods = repository.findPeriodsByConfigId(config.getId());
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
     * Gets billing configuration for a specific project.
     *
     * Returns null if project doesn't have billing configuration (V1 mode).
     *
     * @param projectId Project identifier
     * @param tenantId Tenant identifier
     * @return Billing configuration or null if not found
     */
    public BillingConfig getBillingConfig(String projectId, String tenantId) {
        log.info("Getting billing configuration for project: {} in tenant: {}", projectId, tenantId);

        BillingConfig config = repository.findByProjectId(projectId, tenantId);

        if (config != null) {
            log.info("Found billing configuration: {} with frequency: {}",
                config.getId(), config.getBillingFrequency());
        } else {
            log.info("No billing configuration found for project: {} (V1 mode)", projectId);
        }

        return config;
    }

    /**
     * Gets billing periods for a specific project.
     *
     * Returns periods ordered by period number.
     *
     * @param projectId Project identifier
     * @param tenantId Tenant identifier
     * @return List of billing periods or empty list if not found
     */
    public List<BillingPeriod> getBillingPeriods(String projectId, String tenantId) {
        log.info("Getting billing periods for project: {} in tenant: {}", projectId, tenantId);

        List<BillingPeriod> periods = repository.findPeriodsByProjectId(projectId, tenantId);

        if (periods != null && !periods.isEmpty()) {
            log.info("Found {} billing periods for project: {}", periods.size(), projectId);
        } else {
            log.info("No billing periods found for project: {}", projectId);
            periods = new ArrayList<>();
        }

        return periods;
    }

    /**
     * Gets a specific billing period by period number.
     *
     * @param projectId Project identifier
     * @param tenantId Tenant identifier
     * @param periodNumber Period number
     * @return Billing period or null if not found
     */
    public BillingPeriod getBillingPeriod(String projectId, String tenantId, Integer periodNumber) {
        log.info("Getting billing period {} for project: {}", periodNumber, projectId);

        List<BillingPeriod> periods = repository.findPeriodsByProjectId(projectId, tenantId);

        if (periods == null || periods.isEmpty()) {
            log.warn("No billing periods found for project: {}", projectId);
            return null;
        }

        for (BillingPeriod period : periods) {
            if (period.getPeriodNumber().equals(periodNumber)) {
                log.info("Found billing period: {} for period number: {}", period.getId(), periodNumber);
                return period;
            }
        }

        log.warn("Billing period {} not found for project: {}", periodNumber, projectId);
        return null;
    }

    /**
     * Updates billing configuration status and metadata.
     *
     * Only status and additional details can be updated.
     * Other fields are immutable after creation.
     *
     * @param request Update request
     * @return Updated configuration
     * @throws CustomException if validation fails or configuration not found
     */
    @Transactional
    public BillingConfigResponse updateBillingConfig(BillingConfigRequest request) {
        BillingConfig config = request.getBillingConfig();

        log.info("Updating billing configuration: {}", config.getId());

        // Validate update request
        validator.validateUpdateRequest(request);

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
        BillingConfig updated = repository.findByProjectId(config.getProjectId(), config.getTenantId());

        if (updated == null) {
            throw new CustomException("CONFIG_NOT_FOUND",
                "Billing configuration not found after update: " + config.getId());
        }

        // Fetch periods
        List<BillingPeriod> periods = repository.findPeriodsByConfigId(updated.getId());

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
     * Checks if a project has billing configuration enabled.
     *
     * @param projectId Project identifier
     * @param tenantId Tenant identifier
     * @return true if project has active billing configuration
     */
    public boolean hasActiveBillingConfig(String projectId, String tenantId) {
        BillingConfig config = getBillingConfig(projectId, tenantId);
        return config != null && config.isActive();
    }

    /**
     * Gets billing frequency for a project.
     *
     * @param projectId Project identifier
     * @param tenantId Tenant identifier
     * @return Billing frequency or null if no config exists
     */
    public BillingFrequency getBillingFrequency(String projectId, String tenantId) {
        BillingConfig config = getBillingConfig(projectId, tenantId);
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
}
