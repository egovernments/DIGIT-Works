package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * MusterRollResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

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

