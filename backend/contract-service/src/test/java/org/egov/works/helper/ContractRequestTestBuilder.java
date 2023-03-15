package org.egov.works.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.LineItems;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ContractRequestTestBuilder {
    private ContractRequest.ContractRequestBuilder builder;

    public ContractRequestTestBuilder() {
        this.builder = ContractRequest.builder();
    }

    public static ContractRequestTestBuilder builder() {
        return new ContractRequestTestBuilder();
    }

    public static Object getMdmsResponseForValidTenant() {

        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/TenantMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("ContractRequestTestBuilder::getMdmsResponse::Exception while parsing mdms json");
        }
        return mdmsResponse;
    }
//    public static JsonNode getJsonResponse() throws JsonProcessingException {
//        LineItems lineItems=LineItems.builder().additionalDetails(AdditionalFields.builder().("some-officerInChargeId").build()).build();
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readTree(objectMapper.writeValueAsString(lineItems.getAdditionalDetails()));
//    }

    public ContractRequest build() {
        return this.builder.build();
    }

    public ContractRequestTestBuilder withRequestInfo() {
        this.builder.requestInfo(RequestInfoTestBuilder.builder().withCompleteRequestInfo().build());
        return this;
    }

    public ContractRequestTestBuilder withoutRequestInfo() {
        this.builder.requestInfo(null);
        return this;
    }

    public ContractRequestTestBuilder withRequestInfoButWithoutUserInfo() {
        this.builder.requestInfo(RequestInfoTestBuilder.builder().requestInfoWithoutUserInfo().build());
        return this;
    }

    public ContractRequestTestBuilder withRequestInfoWithUserInfoButWithoutUUID() {
        this.builder.requestInfo(RequestInfoTestBuilder.builder().requestInfoWithUserInfoButWithOutUUID().build());
        return this;
    }

    public ContractRequestTestBuilder withContract() {
        this.builder.contract(ContractTestBuilder.builder().withValidContract().build());
        return this;
    }

    public ContractRequestTestBuilder withContractButWithOutAgreementDateStartDateEndDate() {
        this.builder.contract(ContractTestBuilder.builder().contractWithOutStartingDateAgreementDateEndDate().build());
        return this;
    }

    public ContractRequestTestBuilder withWorkflow() {
        this.builder.workflow(WorkflowTestBuilder.builder().withWorkflow().build());
        return this;

    }

}
