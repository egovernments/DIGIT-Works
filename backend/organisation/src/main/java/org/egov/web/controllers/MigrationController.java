package org.egov.web.controllers;


import io.swagger.annotations.ApiParam;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.util.ResponseInfoFactory;
import org.egov.util.UserIndividualMigrationUtil;
import org.egov.util.UserMigrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/organisation/v1")
public class MigrationController {

    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private UserIndividualMigrationUtil userIndividualMigrationUtil;

    @RequestMapping(value = "/_migrate", method = RequestMethod.POST)
    public ResponseEntity<ResponseInfo> orgServicesOrganisationV1CreatePOST(
            @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody RequestInfoWrapper requestInfoWrapper) {

        userIndividualMigrationUtil.migrate(requestInfoWrapper.getRequestInfo());
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        return new ResponseEntity<ResponseInfo>(responseInfo, HttpStatus.OK);

    }

}