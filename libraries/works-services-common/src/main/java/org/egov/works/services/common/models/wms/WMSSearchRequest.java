package org.egov.works.services.common.models.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;

import javax.validation.Valid;

;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WMSSearchRequest {
  @JsonProperty("RequestInfo")
  private RequestInfo RequestInfo;

  @Valid
  @JsonProperty("inbox")
  private WMSSearchCriteria inbox ;



}