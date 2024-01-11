package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.web.models.enums.Action;
import org.egov.works.mukta.adapter.web.models.enums.MessageType;

@Getter
@Setter
public class Program extends ExchangeMessage {

    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private Program parent;
    @JsonProperty("objectives")
    private String[] objectives;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;

    public Program() {
        this.setAction(Action.CREATE);
    }

    static public Program fromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, Program.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Error parsing Program fromString", e.toString());
        }
    }
}
