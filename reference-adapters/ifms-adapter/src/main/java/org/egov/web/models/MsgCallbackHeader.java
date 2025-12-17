package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.web.models.enums.Action;
import org.egov.web.models.enums.MessageType;
import org.egov.web.models.enums.MsgHeaderStatusReasonCode;
import org.egov.web.models.enums.RequestStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgCallbackHeader {
    @JsonProperty("message_id")
    @NotNull
    private String messageId;

    @JsonProperty("message_ts")
    @NotNull
    private long messageTs;

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

}
