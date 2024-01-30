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
public class MsgCallbackHeader {
    @JsonProperty("message_id")
    @NotNull
    private String messageId;
    @JsonProperty("message_ts")
    @NotNull
    private String messageTs;
    @JsonProperty("status")
    @NotNull
    private RequestStatus status;
    @JsonProperty("status_reason_code")
    private MsgHeaderStatusReasonCode statusReasonCode;
    @JsonProperty("status_reason_message")
    @Size(max = 999)
    private String statusReasonMessage;
    @JsonProperty("sender_id")
    private String senderId;
    @JsonProperty("receiver_id")
    private String receiverId;
    @JsonProperty("is_msg_encrypted")
    private Boolean isMsgEncrypted;
}
