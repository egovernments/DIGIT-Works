package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.works.mukta.adapter.web.models.enums.MsgHeaderStatusReasonCode;
import org.egov.works.mukta.adapter.web.models.enums.RequestStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private String messageTs;
    @JsonProperty("sender_id")
    @NotNull
    private String senderId;
    @JsonProperty("receiver_id")
    @NotNull
    private String receiverId;
    @JsonProperty("sender_uri")
    private String senderUri;
}
