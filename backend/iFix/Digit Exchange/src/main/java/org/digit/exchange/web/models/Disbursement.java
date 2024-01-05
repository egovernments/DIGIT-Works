package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.ZonedDateTimeConverter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Embeddable
public class Disbursement extends ExchangeMessage {
    @JsonProperty("individual")
    private Individual individual;
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    @JsonProperty("bill_date")
    private ZonedDateTime billDate;
    @Convert(converter = ZonedDateTimeConverter.class)
    @JsonProperty("start_date")
    private ZonedDateTime startDate;
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    @JsonProperty("end_date")
    private ZonedDateTime endDate;
    @JsonProperty("allocation")
    private Allocation allocation;
    @JsonProperty("bill_count")
    private int billCount;
    @JsonProperty("bills")
    private List<Disbursement> bills;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;


    public Disbursement(){
        this.setMessageType(MessageType.DISBURSEMENT);
    }

    public Disbursement(Allocation allocation, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(allocation);
        this.setMessageType(MessageType.DISBURSEMENT);
        this.setBillCount(0);
        this.setNetAmount(netAmount);        
        this.setNetAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (bills != null && !bills.isEmpty()) {
            return bills.stream()
                           .map(Disbursement::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (bills != null && !bills.isEmpty()) {
            return bills.stream()
                           .map(Disbursement::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }

    @JsonIgnore
    public int getBillCount() {
        if (bills != null && !bills.isEmpty()) {
            return 1;//getBills().length;
        } else {
            return billCount;
        }
    }

    static public Disbursement fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Disbursement.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Bill fromString", e);
		}
	}
}
