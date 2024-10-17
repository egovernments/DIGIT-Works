package org.egov.works.services.common.models.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountDetail {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;
}

