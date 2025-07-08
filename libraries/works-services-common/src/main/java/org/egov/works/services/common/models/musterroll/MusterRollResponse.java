package org.egov.works.services.common.models.musterroll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRollResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("musterRolls")
    @Valid
    private List<MusterRoll> musterRolls = null;

    @JsonProperty("count")
    private Integer count;


    public MusterRollResponse addMusterRollsItem(MusterRoll musterRollsItem) {
        if (this.musterRolls == null) {
            this.musterRolls = new ArrayList<>();
        }
        this.musterRolls.add(musterRollsItem);
        return this;
    }

}

