package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLogSearchCriteria {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("registerId")
    private String registerId;

    @JsonProperty("fromTime")
    private Long fromTime;

    @JsonProperty("toTime")
    private Double toTime;

    @JsonProperty("individualIds")
    private List<String> individualIds;

    @JsonProperty("status")
    private Status status;

}
