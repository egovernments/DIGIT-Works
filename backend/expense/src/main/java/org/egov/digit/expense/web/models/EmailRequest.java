package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import java.util.List;

/**
 * Kafka message shape for the egov.core.notification.email topic.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private RequestInfo requestInfo;

    private EmailMessage email;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailMessage {

        private List<String> emailTo;

        private String subject;

        private String body;

        private String tenantId;

        @JsonProperty("isHTML")
        private boolean isHTML;
    }
}
