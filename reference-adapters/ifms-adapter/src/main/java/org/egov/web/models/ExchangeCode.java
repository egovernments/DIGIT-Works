package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.enums.MessageType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCode {
    @JsonProperty("type")
    private MessageType type;
    @JsonProperty("administration_code")
    private String administrationCode;
    @JsonProperty("function_code")
    private String functionCode;
    @JsonProperty("recipient_segment_code")
    private String recipientSegmentCode;
    @JsonProperty("economic_segment_code")
    private String economicSegmentCode;
    @JsonProperty("source_of_fund_code")
    private String sourceOfFundCode;
    @JsonProperty("target_segment_code")
    private String targetSegmentCode;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("locale_code")
    private String localeCode;
    @JsonProperty("status")
    private Status status;

    static public ExchangeCode fromString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, ExchangeCode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Error parsing ExchangeMessage fromString", e.toString());
        }
    }

    public void copy(ExchangeCode other) {
        this.functionCode = other.functionCode;
        this.administrationCode = other.administrationCode;
        this.recipientSegmentCode = other.recipientSegmentCode;
        this.economicSegmentCode = other.economicSegmentCode;
        this.sourceOfFundCode = other.sourceOfFundCode;
        this.targetSegmentCode = other.targetSegmentCode;
        this.currencyCode = other.currencyCode;
    }
}