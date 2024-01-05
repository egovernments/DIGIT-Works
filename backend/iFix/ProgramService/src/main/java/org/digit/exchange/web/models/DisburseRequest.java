package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Embeddable
public class DisburseRequest {

    @JsonProperty("transaction_id")
    @NonNull
    private String transactionId;

    @NonNull
    @JsonProperty("disbursements")
    private List<Disbursement> disbursements;

}

