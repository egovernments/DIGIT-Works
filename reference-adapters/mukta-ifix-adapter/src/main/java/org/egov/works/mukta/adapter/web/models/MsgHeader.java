package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.works.mukta.adapter.web.models.enums.Action;
import org.egov.works.mukta.adapter.web.models.enums.MessageType;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgHeader {
    @JsonProperty("message_id")
    @NotNull
    private String messageId;

    @JsonProperty("message_ts")
    @NotNull
    private Long messageTs;

    @JsonProperty("message_type")
    @NotNull
    private MessageType messageType;

    @JsonProperty("action")
    @NotNull
    private Action action;

    @JsonProperty("sender_id")
    @NotNull
    private String senderId;

    @JsonProperty("sender_uri")
    private String senderUri;

    @NotNull
    @JsonProperty("receiver_id")
    private String receiverId;

    @JsonProperty("is_msg_encrypted")
    private boolean isMsgEncrypted;
}
