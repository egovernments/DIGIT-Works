package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Disbursement {
    @JsonProperty("reference_id")
    private String referenceId;
    @JsonProperty("payer_fa")
    private String payerFa;
    @JsonProperty("payee_fa")
    private String payeeFa;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("scheduled_timestamp")
    private String scheduledTimestamp;
    @JsonProperty("payer_name")
    private String payerName;
    @JsonProperty("payee_name")
    private String payeeName;
    @JsonProperty("note")
    private String note;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("instruction")
    private String instruction;
    @JsonProperty("meta")
    private String meta;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("locale")
    private String locale;

}
