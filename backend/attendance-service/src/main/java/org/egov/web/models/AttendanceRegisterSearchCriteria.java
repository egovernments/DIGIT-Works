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
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRegisterSearchCriteria {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("registerNumber")
    private String registerNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fromDate")
    private Long fromDate;

    @JsonProperty("toDate")
    private Double toDate;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("attendeeId")
    private String attendeeId;

}
