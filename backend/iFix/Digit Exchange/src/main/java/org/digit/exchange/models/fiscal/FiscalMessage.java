package org.digit.exchange.models.fiscal;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.digit.exchange.utils.CurrencyConverter;
import org.digit.exchange.utils.ZonedDateTimeConverter;


@Entity
@Table(name="fiscal_message")
@Getter
@Setter
@Embeddable
public class FiscalMessage {
    @NotNull
    @JsonProperty("id")
    @Id
    private String id;
    @NotNull
    @JsonProperty("schema_version")
    private String schema_version;
    @JsonProperty("type")
    private String type;
    @JsonProperty("account_code")
    private String accountCode;
    @JsonProperty("function")
    private String function;
    @JsonProperty("administration")
    private String administration;
    @JsonProperty("location")
    private String location;
    @JsonProperty("program")
    private String program;
    @JsonProperty("recipient_segment")
    private String recipientSegment;
    @JsonProperty("economic_segment")
    private String economicSegment;
    @JsonProperty("source_of_found")
    private String sourceOfFund;
    @JsonProperty("target_segment")
    private String targetSegment;
    @JsonProperty("start_date")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime startDate;
    @JsonProperty("end_date")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime endDate;
    @JsonProperty("net_amount")
    private BigDecimal netAmount;
    @JsonProperty("gross_amount")
    private BigDecimal grossAmount;
    @JsonProperty("currency")
    @Convert(converter = CurrencyConverter.class)
    private Currency currency;
    @JsonProperty("locale")
    private String locale;

    public FiscalMessage(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.schema_version = "1.0.0";
    }

    public void copy(FiscalMessage other){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.schema_version = other.schema_version;
        this.type = other.type;
        this.function = other.function;
        this.administration = other.administration;
        this.location = other.location;
        this.program = other.program;
        this.recipientSegment = other.recipientSegment;
        this.economicSegment = other.economicSegment;
        this.sourceOfFund = other.sourceOfFund;
        this.targetSegment = other.targetSegment;
        this.startDate = other.startDate;
        this.netAmount=other.netAmount;
        this.grossAmount=other.grossAmount;
        this.currency=other.currency;
    }
}
