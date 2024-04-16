package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("documentType")
    private String documentType = null;

    @JsonProperty("fileStore")
    private String fileStore = null;

    @JsonProperty("documentUid")
    private String documentUid = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("status")
    @Builder.Default
    private Status status = Status.ACTIVE;
}