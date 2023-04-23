package org.egov.wms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.wms.repository.builder.WMSSearchQueryBuilder;
import org.egov.wms.service.WMSSearchService;
import org.egov.wms.util.ResponseInfoFactory;
import org.egov.wms.web.model.WMSSearchRequest;
import org.egov.wms.web.model.WMSSearchResponse;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/{module}")
@Slf4j
@Import({TracerConfiguration.class, MultiStateInstanceUtil.class})
public class WMSSearchController {

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private WMSSearchQueryBuilder inboxQueryBuilder;

    @Autowired
    private WMSSearchService wmsSearchService;


    @PostMapping(value = "/_search")
    public ResponseEntity<WMSSearchResponse> searchNewInbox(@PathVariable("module") String module, @Valid @RequestBody WMSSearchRequest wmsSearchRequest) {
        WMSSearchResponse wmsSearchResponse = wmsSearchService.getInboxResponse(wmsSearchRequest,module);
        return new ResponseEntity<>(wmsSearchResponse, HttpStatus.OK);
    }

}

