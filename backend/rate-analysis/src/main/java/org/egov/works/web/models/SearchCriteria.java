package org.egov.works.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * SearchCriteria
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria   {
        @JsonProperty("tenantId")

                private String tenantId = null;

        @JsonProperty("jobIds")

                private List<String> jobIds = null;

        @JsonProperty("ids")
          @Valid
                private List<UUID> ids = null;


        public SearchCriteria addJobIdsItem(String jobIdsItem) {
            if (this.jobIds == null) {
            this.jobIds = new ArrayList<>();
            }
        this.jobIds.add(jobIdsItem);
        return this;
        }

        public SearchCriteria addIdsItem(UUID idsItem) {
            if (this.ids == null) {
            this.ids = new ArrayList<>();
            }
        this.ids.add(idsItem);
        return this;
        }

}
