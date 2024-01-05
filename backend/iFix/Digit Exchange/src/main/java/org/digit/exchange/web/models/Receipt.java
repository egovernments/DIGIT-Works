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
public class Receipt extends ExchangeMessage {
    @JsonProperty("name")
    private String name;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("reciepts")
    private List<Receipt> reciepts;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;


    public Receipt(){
        this.setMessageType( MessageType.RECEIPT);
    }

    public Receipt(Demand demand, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(demand);
        this.setMessageType( MessageType.RECEIPT);
        this.setNetAmount(netAmount);        
        this.setGrossAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (reciepts != null && !reciepts.isEmpty()) {
            return reciepts.stream()
                           .map(Receipt::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (reciepts != null && !reciepts.isEmpty()) {
            return reciepts.stream()
                           .map(Receipt::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    static public Receipt fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Receipt.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Receipt fromString", e);
		}
	}
}