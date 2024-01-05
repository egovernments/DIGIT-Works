package org.digit.exchange.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMessageWrapper {
    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private RequestMessage requestMessage;

    public RequestMessageWrapper(String type, RequestMessage requestMessage) {
        this.type = type;
        this.requestMessage = requestMessage;
    }

}
