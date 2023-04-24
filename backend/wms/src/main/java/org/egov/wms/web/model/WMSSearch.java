package org.egov.wms.web.model;

import java.util.Map;

import org.egov.wms.web.model.workflow.ProcessInstance;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WMSSearch {

	@JsonProperty("ProcessInstance")
	private ProcessInstance ProcessInstance;
	
	@JsonProperty("businessObject")
	private Map<String,Object>	businessObject;
	
	@JsonProperty("serviceObject")
	private Map<String,Object>	serviceObject;
}
