package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
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
public class FaceAuthEventResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("faceAuthEvents")
    @Valid
    private List<FaceAuthEvent> faceAuthEvents;

    public FaceAuthEventResponse addFaceAuthEventItem(FaceAuthEvent item) {
        if (this.faceAuthEvents == null) {
            this.faceAuthEvents = new ArrayList<>();
        }
        this.faceAuthEvents.add(item);
        return this;
    }
}
