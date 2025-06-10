package org.egov.works.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.ContractRequest;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

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
            // Load the JSON file from the resources folder
            InputStream inputStream = ContractRequestTestBuilder.class.getResourceAsStream("/TenantMDMSData.json");
            if (inputStream != null) {
                // Read the content of the JSON file into a string
                String exampleRequest = new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .collect(Collectors.joining("\n"));
                // Parse the JSON string into an object (replace 'Object' with the appropriate type)
                // For example, if 'mdmsResponse' should be a JSONObject, you can use a JSON parsing library like Gson or Jackson to parse the string.
                // For simplicity, I'm assuming 'mdmsResponse' should be a String.
                mdmsResponse = exampleRequest;
            } else {
                log.error("ContractRequestTestBuilder::getMdmsResponse::Could not load TenantMDMSData.json");
            }
        } catch (Exception exception) {
            log.error("ContractRequestTestBuilder::getMdmsResponse::Exception while parsing mdms json", exception);
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
