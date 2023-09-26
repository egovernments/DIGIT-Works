package org.egov.works.measurement.validator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class MeasurementValidator {

    @Autowired
    private MdmsUtil mdmsUtil;
    @Autowired
    private ObjectMapper objectMapper;
    public void validateTenantId(MeasurementRequest measurementRequest){
        List<String> masterList = new ArrayList<>();
        masterList.add("tenants");
        Map<String, Map<String, JSONArray>> response = mdmsUtil.fetchMdmsData(measurementRequest.getRequestInfo(),"pg","tenant",masterList);
        JSONArray jsonArray = response.get("tenant").get("tenants");
        String node = jsonArray.toString();
        Set<String> validTenantSet = new HashSet<>();
        try {
                JsonNode currNode = objectMapper.readTree(node);
                for (JsonNode tenantNode : currNode) {
                    // Assuming each item in the array has a "code" field
                    String tenantId = tenantNode.get("code").asText();
                    validTenantSet.add(tenantId);
                }
            }
        catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        List<Measurement> measurementList = measurementRequest.getMeasurements();
        for(int i=0;i<measurementList.size();i++){
            if(!validTenantSet.contains(measurementList.get(i).getTenantId())){
                 throw new CustomException("","Tenant Id is Not found");
            }
        }
    }
}
