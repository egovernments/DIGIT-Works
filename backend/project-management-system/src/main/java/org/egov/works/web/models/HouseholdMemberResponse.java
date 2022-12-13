package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * HouseholdMemberResponse
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseholdMemberResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("HouseholdMember")
    @Valid
    private List<HouseholdMember> householdMember = null;


    public HouseholdMemberResponse addHouseholdMemberItem(HouseholdMember householdMemberItem) {
        if (this.householdMember == null) {
            this.householdMember = new ArrayList<>();
        }
        this.householdMember.add(householdMemberItem);
        return this;
    }

}

