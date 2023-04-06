package org.egov.works.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MDMSDataParser {
    public List<Object> parseMDMSData(Object mdmsData, String path){
        try {
            return JsonPath.read(mdmsData, path);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }
    }
}
