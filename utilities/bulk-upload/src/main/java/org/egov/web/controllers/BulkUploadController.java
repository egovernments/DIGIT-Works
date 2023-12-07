package org.egov.web.controllers;

import org.egov.common.contract.request.RequestInfo;
import org.egov.service.BulkUploadService;
import org.egov.web.models.MdmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sor")
public class BulkUploadController {

    @Autowired
    private BulkUploadService bulkUploadService;

    @PostMapping("/_bulkUpload")
    public List<Map<String, Object>> bulkUpload(@RequestPart("file")MultipartFile file
                                                ,@RequestParam String schemaCode,@RequestParam String tenantId ) throws Exception{
       return bulkUploadService.bulkUpload(file,schemaCode,tenantId);

    }

}
