package org.egov.digit.expense.calculator.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.service.BillingConfigurationService;
import org.egov.digit.expense.calculator.web.models.BillingConfigAuditResponse;
import org.egov.digit.expense.calculator.web.models.BillingConfigRequest;
import org.egov.digit.expense.calculator.web.models.BillingConfigResponse;
import org.egov.digit.expense.calculator.web.models.BillingConfigSearchRequest;
import org.egov.digit.expense.calculator.web.models.BillingPeriodSearchRequest;
import org.egov.digit.expense.calculator.web.models.BillingPeriodSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * BillingConfigApiController
 *
 * REST API controller for billing configuration operations.
 * Provides endpoints for creating, searching, and updating billing configurations.
 *
 * Endpoints:
 * - POST /billing-config/v1/_create: Create billing configuration
 * - POST /billing-config/v1/_search: Search billing configurations
 * - POST /billing-config/v1/_update: Update billing configuration
 *
 * @author DIGIT-Works
 */
@Controller
@RequestMapping("")
@Slf4j
@Tag(name = "Billing Configuration", description = "APIs for managing billing configurations and periods for intermediate billing")
public class BillingConfigApiController {

    private final BillingConfigurationService billingConfigurationService;

    @Autowired
    public BillingConfigApiController(BillingConfigurationService billingConfigurationService) {
        this.billingConfigurationService = billingConfigurationService;
    }

    /**
     * Creates billing configuration for a project.
     *
     * This endpoint enables intermediate billing for a project by:
     * - Creating billing configuration with specified frequency
     * - Automatically generating billing periods
     * - Returning configuration with generated periods
     *
     * @param billingConfigRequest Billing configuration request
     * @return Created billing configuration with periods
     */
    @Operation(
        summary = "Create billing configuration",
        description = "Creates billing configuration for a project to enable intermediate billing. " +
                     "Automatically generates billing periods based on specified frequency. " +
                     "Supported frequencies: WEEKLY, BI_WEEKLY, MONTHLY, CUSTOM, END_OF_CAMPAIGN",
        tags = {"Billing Configuration"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Billing configuration created successfully",
            content = @Content(schema = @Schema(implementation = BillingConfigResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request - Validation failed or duplicate configuration exists"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @RequestMapping(value = "/billing-config/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<BillingConfigResponse> createBillingConfig(
            @Parameter(
                in = ParameterIn.DEFAULT,
                description = "Billing configuration request with project details and frequency",
                required = true,
                schema = @Schema(implementation = BillingConfigRequest.class)
            )
            @Valid @RequestBody BillingConfigRequest billingConfigRequest) {

        log.info("Received create billing configuration request for project: {}",
            billingConfigRequest.getBillingConfig().getCampaignNumber());

        BillingConfigResponse response = billingConfigurationService.createBillingConfig(billingConfigRequest);

        log.info("Successfully created billing configuration: {} with {} periods",
            response.getBillingConfig().getId(),
            response.getTotalPeriods());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Searches for billing configurations based on criteria.
     *
     * Supports flexible search with:
     * - Project ID filter (single or multiple)
     * - Billing frequency filter
     * - Status filter
     * - Date range filter
     * - Optional period inclusion
     * - Pagination
     *
     * @param searchRequest Search request with criteria
     * @return Matching billing configurations with optional periods
     */
    @Operation(
        summary = "Search billing configurations",
        description = "Searches for billing configurations with flexible criteria. " +
                     "Supports filtering by project ID, frequency, status, and date range. " +
                     "Can optionally include generated billing periods in response.",
        tags = {"Billing Configuration"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = BillingConfigResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid search criteria"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @RequestMapping(value = "/billing-config/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<BillingConfigResponse> searchBillingConfigs(
            @Parameter(
                in = ParameterIn.DEFAULT,
                description = "Search criteria for billing configurations",
                required = true,
                schema = @Schema(implementation = BillingConfigSearchRequest.class)
            )
            @Valid @RequestBody BillingConfigSearchRequest searchRequest) {

        log.info("Received search billing configuration request with criteria: {}",
            searchRequest.getSearchCriteria());

        BillingConfigResponse response = billingConfigurationService.searchBillingConfigs(searchRequest);

        log.info("Search completed - Found billing configuration: {}",
            response.getBillingConfig() != null ? response.getBillingConfig().getId() : "none");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates billing configuration status and metadata.
     *
     * Only status and additional details can be updated.
     * Other fields (frequency, dates) are immutable after creation.
     *
     * @param updateRequest Update request
     * @return Updated billing configuration
     */
    @Operation(
        summary = "Update billing configuration",
        description = "Updates billing configuration status and additional details. " +
                     "Note: Billing frequency and project dates cannot be modified after creation. " +
                     "Valid statuses: ACTIVE, INACTIVE, COMPLETED",
        tags = {"Billing Configuration"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Billing configuration updated successfully",
            content = @Content(schema = @Schema(implementation = BillingConfigResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid update request or configuration not found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @RequestMapping(value = "/billing-config/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<BillingConfigResponse> updateBillingConfig(
            @Parameter(
                in = ParameterIn.DEFAULT,
                description = "Update request with billing configuration ID and updated fields",
                required = true,
                schema = @Schema(implementation = BillingConfigRequest.class)
            )
            @Valid @RequestBody BillingConfigRequest updateRequest) {

        log.info("Received update billing configuration request for ID: {}",
            updateRequest.getBillingConfig().getId());

        BillingConfigResponse response = billingConfigurationService.updateBillingConfig(updateRequest);

        log.info("Successfully updated billing configuration: {}",
            response.getBillingConfig().getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Searches for billing periods based on flexible criteria.
     *
     * Supports comprehensive filtering by:
     * - IDs, billing config, campaign, project
     * - Status, period numbers
     * - Date range, bill existence
     * - Pagination
     *
     * @param searchRequest Search request with criteria
     * @return Matching billing periods with total count
     */
    @Operation(
        summary = "Search billing periods",
        description = "Searches for billing periods with flexible criteria. " +
                     "Supports filtering by campaign, project, status, period numbers, and more. " +
                     "Returns periods with pagination and total count.",
        tags = {"Billing Configuration"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = BillingPeriodSearchResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid search criteria"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @RequestMapping(value = "/billing-config/v1/periods/_search", method = RequestMethod.POST)
    public ResponseEntity<BillingPeriodSearchResponse> searchBillingPeriods(
            @Parameter(
                in = ParameterIn.DEFAULT,
                description = "Search criteria for billing periods",
                required = true,
                schema = @Schema(implementation = BillingPeriodSearchRequest.class)
            )
            @Valid @RequestBody BillingPeriodSearchRequest searchRequest) {

        log.info("Received search billing periods request with criteria: {}",
            searchRequest.getSearchCriteria());

        BillingPeriodSearchResponse response = billingConfigurationService.searchBillingPeriods(searchRequest);

        log.info("Search completed - Found {} periods (total: {})",
            response.getReturnedCount(),
            response.getTotalCount());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Gets audit details for billing configuration by campaign number.
     *
     * Returns audit information including:
     * - Configuration ID and identifiers
     * - Created by user and timestamp
     * - Last modified by user and timestamp
     * - Current status
     *
     * @param searchRequest Search request with campaign number
     * @return Audit details for the billing configuration
     */
    @Operation(
        summary = "Get audit details for billing configuration",
        description = "Retrieves audit details for a billing configuration by campaign number. " +
                     "Returns creation and modification metadata for tracking purposes.",
        tags = {"Billing Configuration"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Audit details retrieved successfully",
            content = @Content(schema = @Schema(implementation = BillingConfigAuditResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request - Campaign number is required"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Billing configuration not found for the given campaign number"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    @RequestMapping(value = "/billing-config/v1/_auditDetails", method = RequestMethod.POST)
    public ResponseEntity<BillingConfigAuditResponse> getAuditDetails(
            @Parameter(
                in = ParameterIn.DEFAULT,
                description = "Search request with campaign number and tenant ID",
                required = true,
                schema = @Schema(implementation = BillingConfigSearchRequest.class)
            )
            @Valid @RequestBody BillingConfigSearchRequest searchRequest) {

        log.info("Received audit details request for campaign: {}",
            searchRequest.getSearchCriteria().getCampaignNumber());

        BillingConfigAuditResponse response = billingConfigurationService.getAuditDetails(searchRequest);

        log.info("Successfully retrieved audit details for campaign: {}",
            searchRequest.getSearchCriteria().getCampaignNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
