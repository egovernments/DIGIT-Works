package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MuktaPayment {
    @JsonProperty("id")
    private String id;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("paymentId")
    private String paymentId;
    @JsonProperty("paymentStatus")
    private String paymentStatus;
    @JsonProperty("paymentType")
    private String paymentType;
    @JsonProperty("disburseData")
    Object disburseData;
    @JsonProperty("additionalDetails")
    private Object additionalDetails;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdTime")
    private Long createdTime;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("lastModifiedTime")
    private Long lastModifiedTime;
}