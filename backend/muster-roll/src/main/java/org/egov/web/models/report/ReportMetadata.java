package org.egov.web.models.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportMetadata {

    @JsonProperty("reportStatus")
    private String reportStatus;  // INITIATED | COMPLETED | FAILED

    @JsonProperty("fileStoreId")
    private String fileStoreId;

    @JsonProperty("generatedAt")
    private Long generatedAt;

    @JsonProperty("errorMessage")
    private String errorMessage;

    /**
     * Convert from MusterRollReport database model to metadata
     */
    public static ReportMetadata from(MusterRollReport report) {
        return ReportMetadata.builder()
                .reportStatus(report.getReportStatus())
                .fileStoreId(report.getFileStoreId())
                .generatedAt(report.getGeneratedAt())
                .errorMessage(report.getErrorMessage())
                .build();
    }
}
