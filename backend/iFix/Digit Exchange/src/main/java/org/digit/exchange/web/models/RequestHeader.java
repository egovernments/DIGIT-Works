package org.digit.exchange.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.ZonedDateTimeConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;


@Embeddable
@Getter
@Setter
public class RequestHeader{ 
    @Id
    private String id;
    @NotNull
    @JsonProperty("version")
    private String version;
    @NotNull
    @JsonProperty("message_id")
    @NotNull
    private String messageId;
    @JsonProperty("message_ts")
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime messageTs;
    @JsonProperty("message_type")
    @NotNull
    private MessageType messageType;
    @JsonProperty("sender_id")
    @NotNull
    private String senderId;
    @JsonProperty("senderUri")
    private String senderUri;
    @NotNull
    @JsonProperty("receiver_id")
    private String receiverId;
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("is_msg_encrypted")
    private boolean isMsgEncrypted;
//     // @OneToOne(cascade = CascadeType.ALL)
//     @JsonProperty("meta")
//     @Embedded
//     private ExchangeMessage exchangeMessage;

    public RequestHeader(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.version = "1.0.0";
    }

    public RequestHeader(String to, String from, ExchangeMessage message, MessageType messageType){
        this.senderId = from;
        this.receiverId = to;
        this.messageType = messageType;
        // this.exchangeMessage = message;
        //Set MessageID
        UUID uuid = UUID.randomUUID();
        this.messageId = uuid.toString();
        //Set Timestamp
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime now = LocalDateTime.now().atZone(zoneId);
        this.messageTs = now;
 
    }

    static public RequestHeader fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, RequestHeader.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing RequestHeader fromString", e);
		}
	}
}
