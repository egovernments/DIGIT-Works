package org.digit.exchange.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.digit.exchange.constants.Action;
import org.digit.exchange.models.fiscal.FiscalMessage;
import org.digit.exchange.utils.ZonedDateTimeConverter;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Embeddable
@Entity
@Getter
@Setter
@Table(name="request_header")
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
    @JsonProperty("action")
    @NotNull
    private Action action;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("meta")
    @Embedded
    private FiscalMessage fiscalMessage;    

    public RequestHeader(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.version = "1.0.0";
    }

    public RequestHeader(String to, String from, FiscalMessage message, Action action){
        this.senderId = from;
        this.receiverId = to;
        this.action = action;
        this.fiscalMessage = message;
        //Set MessageID
        UUID uuid = UUID.randomUUID();
        this.messageId = uuid.toString();
        //Set Timestamp
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime now = LocalDateTime.now().atZone(zoneId);
        this.messageTs = now;
 
    }
}
