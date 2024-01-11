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
public class Allocation extends ExchangeMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("sanction_id")
    private String sanctionId;
    @JsonProperty("allotment_type")
    private String allotmentType;
    @JsonProperty("parent")
    private String parent;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("sanction")
    private Sanction sanction;
    @JsonProperty("allocations")
    private List<Allocation> allocations;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;


    public Allocation() {
        this.setAction(Action.CREATE);
    }

    public Allocation(Sanction sanction, BigDecimal netAmount, BigDecimal grossAmount) {
        super.copy(sanction);
        this.setAction(Action.CREATE);
        this.setSanction(sanction);
        this.setNetAmount(netAmount);
        this.setGrossAmount(grossAmount);
    }

    static public Allocation fromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, Allocation.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Error parsing Allocation fromString", e.toString());
        }
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (allocations != null && !allocations.isEmpty()) {
            return allocations.stream()
                    .map(Allocation::getNetAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (allocations != null && !allocations.isEmpty()) {
            return allocations.stream()
                    .map(Allocation::getGrossAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }
}
