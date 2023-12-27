package org.digit.exchange.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import org.digit.exchange.constants.Action;
import org.digit.exchange.constants.Status;
import org.digit.exchange.models.fiscal.FiscalMessage;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
public class ResponseHeader {
    @JsonProperty("version")
    private String version;
    @Id
    @JsonProperty("message_id")
    private String messageId;
    @JsonProperty("message_ts")
    private String messageTs;
    @JsonProperty("action")
    private Action action;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("status_reason_code")
    private String statusReasonCode;
    @JsonProperty("status_reasons_message")
    private String statusReasonMessage;
    @JsonProperty("sender_id")
    private String senderId;
    @JsonProperty("senderUri")
    private String senderUri;
    @JsonProperty("receiver_id")
    private String receiverId;
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("is_msg_encrypted")
    private boolean isMsgEncrypted;
    // @JsonProperty("meta")
    // private FiscalMessage meta;   
}
