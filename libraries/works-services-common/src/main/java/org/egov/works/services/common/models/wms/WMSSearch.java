package org.egov.works.services.common.models.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.wms.web.model.workflow.ProcessInstance;

import java.util.Map;

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
