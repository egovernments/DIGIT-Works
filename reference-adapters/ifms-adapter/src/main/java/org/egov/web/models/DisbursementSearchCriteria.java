package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementSearchCriteria {
    @JsonProperty("ids")
    List<String> ids;

    @JsonProperty("paymentNumber")
    String paymentNumber;

    @JsonProperty("status")
    String status;

    @JsonProperty("type")
    String type;
}
