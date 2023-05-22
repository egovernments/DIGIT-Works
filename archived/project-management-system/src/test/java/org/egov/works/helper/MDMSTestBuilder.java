package org.egov.works.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Slf4j
public class MDMSTestBuilder {

    public static MDMSTestBuilder builder(){
        return new MDMSTestBuilder();
    }

    public Object getMockMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/ProjectMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }
}
