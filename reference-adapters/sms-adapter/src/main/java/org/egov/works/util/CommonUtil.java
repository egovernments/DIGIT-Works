package org.egov.works.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@Component
@Slf4j
public class CommonUtil {

    @Autowired
    private ObjectMapper mapper;

    public List<Object> readJSONPathValue(Object jsonObj, String path){
        try {
            return JsonPath.read(jsonObj, path);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }
    }

    public Optional<String> findValue(Object object, String findValueOf){
        try {
            JsonNode node = mapper.readTree(mapper.writeValueAsString(object));
            if (node.findValue(findValueOf) != null  && StringUtils.isNotBlank(node.findValue(findValueOf).textValue())) {
                return Optional.of(node.findValue(findValueOf).textValue());
            }
        } catch (Exception ignore) {}
        return Optional.empty();
    }
}
