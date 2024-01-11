package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.web.models.enums.Action;
import org.egov.works.mukta.adapter.web.models.enums.MessageType;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
public class ExchangeMessage {
    @JsonProperty("id")
    @Id
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("schema_version")
    private String schemaVersion;
    @JsonProperty("action")
    private Action action;
    @JsonProperty("account_code")
    private String accountCode;
    @JsonProperty("administration_code")
    private String administrationCode;
    @JsonProperty("function_code")
    private String functionCode;
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
    private ZonedDateTime startDate;
    @JsonProperty("end_date")
    private ZonedDateTime endDate;
    @JsonProperty("net_amount")
    private BigDecimal netAmount;
    @JsonProperty("gross_amount")
    private BigDecimal grossAmount;
    @JsonProperty("currency_code")
    private Currency currencyCode;
    @JsonProperty("locale_code")
    private String localeCode;
    @JsonProperty("status")
    private Status status;

    public ExchangeMessage() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.schemaVersion = "1.0.0";
    }

    static public ExchangeMessage fromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, ExchangeMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Error parsing ExchangeMessage fromString", e.toString());
        }
    }

    public void copy(ExchangeMessage other) {
        this.schemaVersion = other.schemaVersion;
        this.functionCode = other.functionCode;
        this.locationCode = other.locationCode;
        this.programCode = other.programCode;
        this.administrationCode = other.administrationCode;
        this.recipientSegmentCode = other.recipientSegmentCode;
        this.economicSegmentCode = other.economicSegmentCode;
        this.sourceOfFundCode = other.sourceOfFundCode;
        this.targetSegmentCode = other.targetSegmentCode;
        this.startDate = other.startDate;
        this.netAmount = other.netAmount;
        this.grossAmount = other.grossAmount;
        this.currencyCode = other.currencyCode;
    }
}
