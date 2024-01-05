package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.exceptions.CustomException;

@Getter
@Setter
public class InboxRequest {
    
    @JsonProperty("receiver_id")
    private String receiverId;

    @JsonProperty("page")
    private int page;

    @JsonProperty("size")
    private int size;
    
    static public InboxRequest fromString(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
        return mapper.readValue(json, InboxRequest.class);
        } catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Address fromString", e);
	    }
    }
}
