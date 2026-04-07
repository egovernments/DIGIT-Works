package org.egov.web.models.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.egov.common.contract.models.AuditDetails;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualWorker {

    @JsonProperty("id")
    @Size(min = 2, max = 64)
    protected String id;

    @JsonProperty("individualIds")
    private List<String> individualIds;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 1000)
    protected String tenantId;

    @JsonProperty("source")
    protected String source;

    @JsonProperty("rowVersion")
    protected Integer rowVersion;

    @JsonProperty("applicationId") //needs comments
    protected String applicationId;

    @JsonProperty("hasErrors")
    @Builder.Default
    protected Boolean hasErrors = Boolean.FALSE;

    @JsonProperty("auditDetails")
    @Valid
    protected AuditDetails auditDetails;

    @JsonProperty("name")
    private String name;

    @JsonProperty("payeePhoneNumber")
    private String payeePhoneNumber;

    @JsonProperty("paymentProvider")
    private String paymentProvider;

    @JsonProperty("payeeName")
    private String payeeName;

    @JsonProperty("bankAccount")
    private String bankAccount;

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("photoId")
    private String photoId;

    @JsonProperty("signatureId")
    private String signatureId;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("isDeleted")
    @Builder.Default
    private Boolean isDeleted = false;
}
