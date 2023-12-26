package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

/**
 * Message header
 */
@Schema(description = "Message header")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgCallbackHeaderV100 {
    @JsonProperty("version")

    private String version = "1.0.0";

    @JsonProperty("message_id")
    @NotNull

    private String messageId = null;

    @JsonProperty("message_ts")
    @NotNull

    @Valid
    private OffsetDateTime messageTs = null;

    @JsonProperty("action")
    @NotNull

    private String action = null;

    @JsonProperty("status")
    @NotNull

    @Valid
    private RequestStatus status = null;

    @JsonProperty("status_reason_code")

    @Valid
    private MsgHeaderStatusReasonCode statusReasonCode = null;

    @JsonProperty("status_reason_message")

    @Size(max = 999)
    private String statusReasonMessage = null;

    @JsonProperty("total_count")

    private Integer totalCount = null;

    @JsonProperty("completed_count")

    private Integer completedCount = null;

    @JsonProperty("sender_id")

    private String senderId = null;

    @JsonProperty("receiver_id")

    private String receiverId = null;

    @JsonProperty("is_msg_encrypted")

    private Boolean isMsgEncrypted = false;

    @JsonProperty("meta")

    @Valid
    private Meta meta = null;


}
