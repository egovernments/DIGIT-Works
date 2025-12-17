package org.egov.web.controllers;

import org.egov.service.BulkUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class BulkUploadController {

    @Autowired
    private BulkUploadService bulkUploadService;


    @PostMapping("/_bulkUpload")
    public List<Map<String, Object>> bulkUpload(@RequestPart("file")MultipartFile file
            ,@RequestParam String schemaCode,@RequestParam String tenantId ) throws Exception{
        return bulkUploadService.bulkUpload(file,schemaCode,tenantId);
    }


    @PostMapping("/_bulkUpdate")
    public List<Map<String, Object>> bulkUpdate(@RequestParam("file") MultipartFile file, @Valid @RequestParam("mdmsSearchCriteria") String mdmsSearchCriteria, @RequestParam("newValidToDate") String newValidToDate) throws Exception {
        return bulkUploadService.bulkUpdate(file, mdmsSearchCriteria, newValidToDate);
    }



}
