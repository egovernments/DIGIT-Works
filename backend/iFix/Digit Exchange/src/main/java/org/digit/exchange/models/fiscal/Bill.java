package org.digit.exchange.models.fiscal;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotNull;

import org.digit.exchange.utils.ZonedDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@Getter
@Setter
public class Bill extends FiscalMessage {
    @JsonProperty("name")
    private String name;
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    @JsonProperty("start_date")
    private ZonedDateTime startDate;
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    @JsonProperty("end_date")
    private ZonedDateTime endDate;
    @JsonProperty("bills")
    private List<Bill> bills;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;


    public Bill(){}

    public Bill(Allocation allocation, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(allocation);
        this.setType("bill");
        this.setNetAmount(netAmount);        
        this.setNetAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (bills != null && !bills.isEmpty()) {
            return bills.stream()
                           .map(Bill::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (bills != null && !bills.isEmpty()) {
            return bills.stream()
                           .map(Bill::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }
}
