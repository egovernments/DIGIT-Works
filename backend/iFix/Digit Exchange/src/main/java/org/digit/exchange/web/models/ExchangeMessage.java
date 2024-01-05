package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.CurrencyConverter;
import org.digit.exchange.utils.ZonedDateTimeConverter;
import org.digit.exchange.constants.Error;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ExchangeMessage {
    @JsonProperty("id")
    @Id
    @NotBlank(message = Error.INVALID_ID)
    private String id;
    @NotNull
    @JsonProperty("schema_version")
    private String schemaVersion;
    @JsonProperty("message_type")
    private MessageType messageType;
    @JsonProperty("account_code")
    private String accountCode;
    @JsonProperty("function_code")
    private String functionCode;
    @JsonProperty("administration_code")
    private String administrationCode;
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("program_code")
    private String programCode;
    @JsonProperty("recipient_segment_code")
    private String recipientSegmentCode;
    @JsonProperty("economic_segment_code")
    private String economicSegmentCode;
    @JsonProperty("source_of_found_code")
    private String sourceOfFundCode;
    @JsonProperty("target_segment_code")
    private String targetSegmentCode;
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
    @JsonProperty("currency_code")
    @Convert(converter = CurrencyConverter.class)
    private Currency currencyCode;
    @JsonProperty("locale_code")
    private String localeCode;

    public ExchangeMessage(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.schemaVersion = "1.0.0";
    }

    public void copy(ExchangeMessage other){
        this.schemaVersion = other.schemaVersion;
        this.functionCode= other.functionCode;
        this.administrationCode = other.administrationCode;
        this.locationCode = other.locationCode;
        this.programCode = other.programCode;
        this.recipientSegmentCode = other.recipientSegmentCode;
        this.economicSegmentCode = other.economicSegmentCode;
        this.sourceOfFundCode = other.sourceOfFundCode;
        this.targetSegmentCode = other.targetSegmentCode;
        this.startDate = other.startDate;
        this.netAmount=other.netAmount;
        this.grossAmount=other.grossAmount;
        this.currencyCode=other.currencyCode;
    }

    static public ExchangeMessage fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, ExchangeMessage.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing ExchangeMessage fromString", e);
		}
	}
}
