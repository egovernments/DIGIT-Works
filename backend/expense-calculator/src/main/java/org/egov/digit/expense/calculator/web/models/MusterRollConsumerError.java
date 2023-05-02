package org.egov.digit.expense.calculator.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRollConsumerError {

    @JsonProperty("exception")
    private Exception exception;

    @JsonProperty("musterRollRequest")
    private MusterRollRequest musterRollRequest;
}
