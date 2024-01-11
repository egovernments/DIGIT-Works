package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class Estimate extends ExchangeMessage {
    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private String parent;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("estimates")
    private List<Estimate> estimates;
    @JsonProperty("program")
    private Program program;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;

    public Estimate() {
        this.setAction(Action.CREATE);
    }

    public Estimate(Program program, ZonedDateTime startDate, ZonedDateTime endDate, BigDecimal netAmount, BigDecimal grossAmount) {
        super.copy(program);
        this.setProgram(program);
        this.startDate = startDate;
        this.endDate = endDate;
        this.setNetAmount(netAmount);
        this.setGrossAmount(netAmount);
        this.setAction(Action.CREATE);
    }

    static public Estimate fromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, Estimate.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Error parsing Estimate fromString", e.toString());
        }
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (estimates != null && !estimates.isEmpty()) {
            return estimates.stream()
                    .map(Estimate::getNetAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (estimates != null && !estimates.isEmpty()) {
            return estimates.stream()
                    .map(Estimate::getGrossAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }
}
