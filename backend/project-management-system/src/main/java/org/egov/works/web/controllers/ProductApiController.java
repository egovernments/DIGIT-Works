package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.works.web.models.ProductRequest;
import org.egov.works.web.models.ProductResponse;
import org.egov.works.web.models.ProductVariantRequest;
import org.egov.works.web.models.ProductVariantResponse;
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
@RequestMapping("/product")
public class ProductApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public ProductApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<ProductResponse> productV1CreatePost(@ApiParam(value = "Capture details of Product.", required = true) @Valid @RequestBody ProductRequest product) {
        return new ResponseEntity<ProductResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<ProductResponse> productV1SearchPost(@ApiParam(value = "Capture details of Product.", required = true) @Valid @RequestBody ProductRequest product, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<ProductResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<ProductResponse> productV1UpdatePost(@ApiParam(value = "Capture details of Product.", required = true) @Valid @RequestBody ProductRequest product) {
        return new ResponseEntity<ProductResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/variant/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<ProductVariantResponse> productVariantV1CreatePost(@ApiParam(value = "Capture details of Product Variant.", required = true) @Valid @RequestBody ProductVariantRequest productVariant) {
        return new ResponseEntity<ProductVariantResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/variant/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<ProductVariantResponse> productVariantV1SearchPost(@ApiParam(value = "Capture details of Product variant.", required = true) @Valid @RequestBody ProductVariantRequest productVariant, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        return new ResponseEntity<ProductVariantResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/variant/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<ProductVariantResponse> productVariantV1UpdatePost(@ApiParam(value = "Capture details of Product Variant.", required = true) @Valid @RequestBody ProductVariantRequest productVariant) {
        return new ResponseEntity<ProductVariantResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
