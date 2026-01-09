package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.MusterRollService;
import org.egov.service.PeriodAwareMusterRollService;
import org.egov.util.ResponseInfoCreator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")
@Slf4j

@Controller
@RequestMapping("/v1")
public class MusterRollApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MusterRollService musterRollService;

    @Autowired
    private PeriodAwareMusterRollService periodAwareMusterRollService;

    @Autowired
    private ResponseInfoCreator responseInfoCreator;

    @RequestMapping(value = "/_estimate", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV1EstimatePost(@ApiParam(value = "Request object to provide the estimate of the muster roll", required = true) @Valid @RequestBody MusterRollRequest body) {
        MusterRollRequest musterRollRequest = musterRollService.estimateMusterRoll(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MusterRollResponse musterRollResponse = MusterRollResponse.builder().responseInfo(responseInfo).musterRolls(Collections.singletonList(musterRollRequest.getMusterRoll())).build();
        return new ResponseEntity<MusterRollResponse>(musterRollResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV1CreatePost(@ApiParam(value = "Request object to create the muster roll", required = true) @Valid @RequestBody MusterRollRequest body) {
        MusterRollRequest musterRollRequest = musterRollService.createMusterRoll(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MusterRollResponse musterRollResponse = MusterRollResponse.builder().responseInfo(responseInfo).musterRolls(Collections.singletonList(musterRollRequest.getMusterRoll())).build();
        return new ResponseEntity<MusterRollResponse>(musterRollResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV1SearchPOST(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper, @Valid @ModelAttribute MusterRollSearchCriteria searchCriteria) {
        MusterRollResponse musterRollResponse = musterRollService.searchMusterRolls(requestInfoWrapper, searchCriteria);
        return new ResponseEntity<MusterRollResponse>(musterRollResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/v2/_search", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV2SearchPOST(@Valid @RequestBody MusterRollSearchRequest request) {
        MusterRollResponse musterRollResponse = musterRollService.searchMusterRolls(request, request.getMusterRoll());
        return new ResponseEntity<MusterRollResponse>(musterRollResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV1UpdatePost(@ApiParam(value = "Request object to update the muster roll", required = true) @Valid @RequestBody MusterRollRequest body) {
        MusterRollRequest musterRollRequest = musterRollService.updateMusterRoll(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        MusterRollResponse musterRollResponse = MusterRollResponse.builder().responseInfo(responseInfo).musterRolls(Collections.singletonList(musterRollRequest.getMusterRoll())).build();
        return new ResponseEntity<MusterRollResponse>(musterRollResponse, HttpStatus.OK);
    }

    /**
     * V2 API Endpoint: Period-aware muster roll creation
     *
     * This endpoint is called by the health-expense-calculator service during V2 intermediate billing.
     * It creates or fetches muster rolls for specific billing periods.
     *
     * Request Body Format:
     * {
     *   "RequestInfo": {...},
     *   "tenantId": "pg.citya",
     *   "registerIds": ["register-1", "register-2"],
     *   "campaignNumber": "CAMP-2024-001",
     *   "billingPeriod": {
     *     "id": "period-uuid",
     *     "periodNumber": 1,
     *     "periodStartDate": 1704067200000,
     *     "periodEndDate": 1704671999000,
     *     "campaignNumber": "CAMP-2024-001"
     *   }
     * }
     *
     * Features:
     * - Prevents duplicate muster rolls (checks for existing register+period combination)
     * - Calculates attendance aggregation within period boundaries
     * - Links muster rolls to billing periods via billingPeriodId
     *
     * @param body Request containing register IDs and billing period details
     * @return Response with list of muster rolls for the period
     */
    @RequestMapping(value = "/_periodicCreate", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> createPeriodicMusterRolls(
            @ApiParam(value = "Request object to create period-specific muster rolls for V2 intermediate billing", required = true)
            @Valid @RequestBody PeriodicMusterRollRequest body) {

        log.info("MusterRollApiController::createPeriodicMusterRolls - Received request for {} registers in period {} (campaign: {})",
                body.getRegisterIds() != null ? body.getRegisterIds().size() : 0,
                body.getBillingPeriod() != null ? body.getBillingPeriod().getPeriodNumber() : "null",
                body.getCampaignNumber());

        // Call PeriodAwareMusterRollService to create/fetch muster rolls
        List<MusterRoll> musterRolls = periodAwareMusterRollService.getOrCreatePeriodicMusterRolls(
                body.getRequestInfo(),
                body.getTenantId(),
                body.getRegisterIds(),
                body.getBillingPeriod()
        );

        // Create response
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(
                body.getRequestInfo(), true
        );

        MusterRollResponse musterRollResponse = MusterRollResponse.builder()
                .responseInfo(responseInfo)
                .musterRolls(musterRolls)
                .build();

        log.info("MusterRollApiController::createPeriodicMusterRolls - Returning {} muster rolls for period {}",
                musterRolls.size(),
                body.getBillingPeriod() != null ? body.getBillingPeriod().getPeriodNumber() : "null");

        return new ResponseEntity<>(musterRollResponse, HttpStatus.OK);
    }

}
