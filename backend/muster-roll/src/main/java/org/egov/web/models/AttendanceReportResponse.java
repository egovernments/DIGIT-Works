package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportResponse {
    @JsonProperty("responseInfo")
    @Valid
    private ResponseInfo responseInfo;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("excelFileStoreId")
    private String excelFileStoreId;

    @JsonProperty("pdfFileStoreId")
    private String pdfFileStoreId;

    @JsonProperty("generatedAt")
    private Long generatedAt;

    @JsonProperty("errorMessage")
    private String errorMessage;
}
