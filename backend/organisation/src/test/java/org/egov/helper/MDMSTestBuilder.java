package org.egov.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MDMSTestBuilder {

    public static MDMSTestBuilder builder(){
        return new MDMSTestBuilder();
    }

    public Object getMockMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("src/test/resources/MDMSData.json");
            String absoluteFilePath = resource.getPath();
            byte[] bytes = Files.readAllBytes(Paths.get(absoluteFilePath));
            String exampleRequest = new String(bytes, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }
}
