package org.digit.exchange.models;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ResponseMessage {
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    private ResponseHeader header;
    @JsonProperty("message")
    private String message; 
}
