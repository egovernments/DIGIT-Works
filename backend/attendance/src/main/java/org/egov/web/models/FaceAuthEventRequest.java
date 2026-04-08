package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaceAuthEventRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("faceAuthEvents")
    @Valid
    private List<FaceAuthEvent> faceAuthEvents;

    public FaceAuthEventRequest addFaceAuthEventItem(FaceAuthEvent item) {
        if (this.faceAuthEvents == null) {
            this.faceAuthEvents = new ArrayList<>();
        }
        this.faceAuthEvents.add(item);
        return this;
    }
}
