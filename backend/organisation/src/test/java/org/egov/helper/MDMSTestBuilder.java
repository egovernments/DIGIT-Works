package org.egov.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MDMSTestBuilder {

    public static MDMSTestBuilder builder(){
        return new MDMSTestBuilder();
    }

//    public Object getMockMDMSData() {
//        Object mdmsResponse = null;
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            File file = new File("src/test/resources/ProjectMDMSData.json");
//            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
//        } catch (Exception exception) {
//            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
//        }
//        return  mdmsResponse;
//
//    }

    public Object getMockTenantMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/TenantMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }

    public Object getMockOrganisationTypeMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/OrganisationTypeMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }

    public Object getMockFunctionCategoryMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/FunctionCategoryMDMS.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }

    public Object getMockFunctionClassMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/FunctionClassMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }

    public Object getMockTaxIdentifierMDMSData() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/TaxIdentifierMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ProjectServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;

    }
}
