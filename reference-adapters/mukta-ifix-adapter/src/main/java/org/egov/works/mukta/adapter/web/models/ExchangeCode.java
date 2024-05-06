package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.egov.tracer.model.CustomException;

import java.util.Currency;

@Getter
@Setter
public class ExchangeCode {
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
}
