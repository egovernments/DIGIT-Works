package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Embeddable
public class Demand extends ExchangeMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("demands")
    private List<Demand> demands;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;


    public Demand(){
        this.setMessageType(MessageType.DEMAND);
    }

    public Demand(Estimate estimate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(estimate);
        this.setMessageType(MessageType.DEMAND);
        this.setNetAmount(netAmount);        
        this.setGrossAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (demands != null && !demands.isEmpty()) {
            return demands.stream()
                           .map(Demand::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (demands != null && !demands.isEmpty()) {
            return demands.stream()
                           .map(Demand::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }

    static public Demand fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Demand.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Demand fromString", e);
		}
	}
}
