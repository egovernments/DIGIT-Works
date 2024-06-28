package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * JobSchedulerResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSchedulerResponse {
    @JsonProperty("ResponseInfo")

    @Valid
    private ResponseInfo responseInfo = null;

    @JsonProperty("ScheduledJobs")
    @Valid
    private List<ScheduledJob> scheduledJobs = null;


    public JobSchedulerResponse addScheduledJobsItem(ScheduledJob scheduledJobsItem) {
        if (this.scheduledJobs == null) {
            this.scheduledJobs = new ArrayList<>();
        }
        this.scheduledJobs.add(scheduledJobsItem);
        return this;
    }

}
