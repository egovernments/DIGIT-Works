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

    public Estimate(){
        this.setMessageType(MessageType.ESTIMATE);
    }

    public Estimate(Program program, ZonedDateTime startDate, ZonedDateTime endDate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(program);
        this.setProgram(program);
        this.startDate = startDate;
        this.endDate = endDate;
        this.setNetAmount(netAmount);        
        this.setGrossAmount(netAmount);        
        this.setMessageType( MessageType.ESTIMATE);
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

    static public Estimate fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Estimate.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Estimate fromString", e);
		}
	}
}
