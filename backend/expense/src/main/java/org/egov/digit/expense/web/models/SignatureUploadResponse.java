package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

@Schema(description = "Response object for the signature upload API")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignatureUploadResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("signatureFileStoreId")
    private String signatureFileStoreId;
}
