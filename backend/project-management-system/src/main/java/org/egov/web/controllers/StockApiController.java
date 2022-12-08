package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.web.models.StockTransactionRequest;
import org.egov.web.models.StockTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Controller
@RequestMapping("/rushang.dhanesha/Project-Service/1.0.0")
public class StockApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public StockApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/stock/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<StockTransactionResponse> stockV1CreatePost(@ApiParam(value = "Capture details of stock transaction.", required = true) @Valid @RequestBody StockTransactionRequest stockTransaction) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StockTransactionResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"StockTransaction\" : [ {    \"facilityId\" : \"FacilityA\",    \"quantity\" : 0,    \"productVariantId\" : \"productVariantId\",    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"rowVersion\" : { },    \"clientReferenceId\" : \"clientReferenceId\",    \"referenceId\" : \"C-1\",    \"transactionReason\" : \"RECEIVED\",    \"transactionType\" : \"RECEIVED\",    \"isDeleted\" : { },    \"transactingPartyId\" : \"transactingPartyId\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"tenantId\" : \"tenantA\",    \"id\" : \"id\",    \"transactingPartyType\" : \"WAREHOUSE\",    \"referenceIdType\" : \"PROJECT\"  }, {    \"facilityId\" : \"FacilityA\",    \"quantity\" : 0,    \"productVariantId\" : \"productVariantId\",    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"rowVersion\" : { },    \"clientReferenceId\" : \"clientReferenceId\",    \"referenceId\" : \"C-1\",    \"transactionReason\" : \"RECEIVED\",    \"transactionType\" : \"RECEIVED\",    \"isDeleted\" : { },    \"transactingPartyId\" : \"transactingPartyId\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"tenantId\" : \"tenantA\",    \"id\" : \"id\",    \"transactingPartyType\" : \"WAREHOUSE\",    \"referenceIdType\" : \"PROJECT\"  } ]}", StockTransactionResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StockTransactionResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StockTransactionResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/stock/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<StockTransactionResponse> stockV1SearchPost(@ApiParam(value = "Capture details of Stock Transfer.", required = true) @Valid @RequestBody StockTransactionRequest stockTransaction, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StockTransactionResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"StockTransaction\" : [ {    \"facilityId\" : \"FacilityA\",    \"quantity\" : 0,    \"productVariantId\" : \"productVariantId\",    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"rowVersion\" : { },    \"clientReferenceId\" : \"clientReferenceId\",    \"referenceId\" : \"C-1\",    \"transactionReason\" : \"RECEIVED\",    \"transactionType\" : \"RECEIVED\",    \"isDeleted\" : { },    \"transactingPartyId\" : \"transactingPartyId\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"tenantId\" : \"tenantA\",    \"id\" : \"id\",    \"transactingPartyType\" : \"WAREHOUSE\",    \"referenceIdType\" : \"PROJECT\"  }, {    \"facilityId\" : \"FacilityA\",    \"quantity\" : 0,    \"productVariantId\" : \"productVariantId\",    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"rowVersion\" : { },    \"clientReferenceId\" : \"clientReferenceId\",    \"referenceId\" : \"C-1\",    \"transactionReason\" : \"RECEIVED\",    \"transactionType\" : \"RECEIVED\",    \"isDeleted\" : { },    \"transactingPartyId\" : \"transactingPartyId\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"tenantId\" : \"tenantA\",    \"id\" : \"id\",    \"transactingPartyType\" : \"WAREHOUSE\",    \"referenceIdType\" : \"PROJECT\"  } ]}", StockTransactionResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StockTransactionResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StockTransactionResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/stock/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<StockTransactionResponse> stockV1UpdatePost(@ApiParam(value = "Capture details of stock transaction", required = true) @Valid @RequestBody StockTransactionRequest stockTransaction) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StockTransactionResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"StockTransaction\" : [ {    \"facilityId\" : \"FacilityA\",    \"quantity\" : 0,    \"productVariantId\" : \"productVariantId\",    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"rowVersion\" : { },    \"clientReferenceId\" : \"clientReferenceId\",    \"referenceId\" : \"C-1\",    \"transactionReason\" : \"RECEIVED\",    \"transactionType\" : \"RECEIVED\",    \"isDeleted\" : { },    \"transactingPartyId\" : \"transactingPartyId\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"tenantId\" : \"tenantA\",    \"id\" : \"id\",    \"transactingPartyType\" : \"WAREHOUSE\",    \"referenceIdType\" : \"PROJECT\"  }, {    \"facilityId\" : \"FacilityA\",    \"quantity\" : 0,    \"productVariantId\" : \"productVariantId\",    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"rowVersion\" : { },    \"clientReferenceId\" : \"clientReferenceId\",    \"referenceId\" : \"C-1\",    \"transactionReason\" : \"RECEIVED\",    \"transactionType\" : \"RECEIVED\",    \"isDeleted\" : { },    \"transactingPartyId\" : \"transactingPartyId\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"tenantId\" : \"tenantA\",    \"id\" : \"id\",    \"transactingPartyType\" : \"WAREHOUSE\",    \"referenceIdType\" : \"PROJECT\"  } ]}", StockTransactionResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StockTransactionResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StockTransactionResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
