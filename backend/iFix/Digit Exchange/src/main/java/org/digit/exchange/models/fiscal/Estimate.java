package org.digit.exchange.models.fiscal;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@Getter
@Setter
public class Estimate extends FiscalMessage {
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
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;

    public Estimate(){}

    public Estimate(Program program, ZonedDateTime startDate, ZonedDateTime endDate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(program);
        this.setType("estimate");
        this.startDate = startDate;
        this.endDate = endDate;
        this.setNetAmount(netAmount);        
        this.setGrossAmount(netAmount);        
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
