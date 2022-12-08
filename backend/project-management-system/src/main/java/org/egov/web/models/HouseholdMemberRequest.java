package org.egov.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.egov.web.models.ApiOperation;
import org.egov.web.models.HouseholdMember;
import org.egov.web.models.RequestInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * HouseholdMemberRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseholdMemberRequest   {
        @JsonProperty("RequestInfo")
        private RequestInfo requestInfo = null;

        @JsonProperty("HouseholdMember")
        @Valid
        private List<HouseholdMember> householdMember = new ArrayList<>();

        @JsonProperty("apiOperation")
        private ApiOperation apiOperation = null;


        public HouseholdMemberRequest addHouseholdMemberItem(HouseholdMember householdMemberItem) {
        this.householdMember.add(householdMemberItem);
        return this;
        }

}

