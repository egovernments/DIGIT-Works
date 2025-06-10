package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.IfmsAdapterConfig;
import org.egov.tracer.model.CustomException;
import org.egov.utils.MdmsUtils;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.jit.JITRequest;
import org.egov.web.models.jit.JITResponse;
import org.egov.web.models.mdmsV2.MdmsResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Slf4j
public class MockTestService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private IfmsAdapterConfig config;
    @Autowired
    private MdmsUtils mdmsUtils;


    public JITResponse getMockResponse(String tenantId, JITRequest jitRequest) {
        try {
            JITResponse jitResponse = null;
            jitResponse = processRequestForMDMSV2(tenantId, jitRequest);
            return jitResponse;
        } catch(Exception e){
            log.error("Error while generating JIT Response", e);
            throw new RuntimeException(e);
        }
    }

    private JITResponse processRequestForMDMSV2(String tenantId, JITRequest jitRequest) {
        if (jitRequest != null && jitRequest.getParams() != null) {
            ObjectNode mdmsV2SearchRequest = objectMapper.createObjectNode();
            mdmsV2SearchRequest.put("MdmsCriteria", prepareMDMSV2SearchRequest(tenantId, jitRequest));
            MdmsResponseV2 mdmsResponseV2 = mdmsUtils.fetchFromMDMSV2V2(mdmsV2SearchRequest);
            JITResponse jitResponse = new JITResponse();
            jitResponse = processMDMSToJITResponse(mdmsResponseV2, jitRequest);
            return jitResponse;
        } else {
            throw new RuntimeException("Invalid Request");
        }
    }

    private JITResponse processMDMSToJITResponse(MdmsResponseV2 mdmsResponseV2, JITRequest jitRequest) {
        if (mdmsResponseV2 != null && mdmsResponseV2.getMdms() != null && mdmsResponseV2.getMdms().size() > 0) {
            JITResponse jitResponse = new JITResponse();
            if (jitRequest.getServiceId().equals(JITServiceId.FD)) {
                List<Object> fdDetails = new ArrayList<>();
                for (int i = 0; i < mdmsResponseV2.getMdms().size(); i++) {
                    JITResponse jitFDResponse = new JITResponse();
                    JsonNode mdmsMockData = mdmsResponseV2.getMdms().get(i).getData();
                    jitFDResponse = objectMapper.convertValue(mdmsMockData, JITResponse.class);
                    fdDetails.addAll(jitFDResponse.getData());
                }
                jitResponse = JITResponse.builder().serviceId(jitRequest.getServiceId()).data(fdDetails).build();
            } else {
                JsonNode mdmsMockData = mdmsResponseV2.getMdms().get(0).getData();
                jitResponse = objectMapper.convertValue(mdmsMockData, JITResponse.class);
            }
            return jitResponse;
        }
        return JITResponse.builder().serviceId(jitRequest.getServiceId()).data(new ArrayList<>()).build();
    }

    private ObjectNode prepareMDMSV2SearchRequest(String tenantId, JITRequest jitRequest) {

        // Create root node
        ObjectNode mdmsCriteriaNode = objectMapper.createObjectNode();
        mdmsCriteriaNode.put("tenantId", tenantId);
        mdmsCriteriaNode.put("schemaCode", config.getIfmsJitMockMdmsMasterName());
        mdmsCriteriaNode.put("limit", 10000);
        mdmsCriteriaNode.put("offset", 0);

        Map<?, ?> jitRequestMap = new HashMap<>();
        if (jitRequest.getParams() instanceof Map) {
            jitRequestMap = (Map<?, ?>) jitRequest.getParams();
        } else if (jitRequest.getParams() instanceof Object) {
            jitRequestMap = convertObjectToMap(jitRequest.getParams());
        } else {
            throw new RuntimeException("Invalid Request");
        }
        // Switch case is used to direct request to the respective services.
        switch (jitRequest.getServiceId()) {
            case VA:
                applyVAFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case PI:
                applyPIFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case PIS:
                applyPISFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case PAG:
                applyPAGFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case PD:
                applyPDFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case FD:
                applyFDFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case COR:
                applyCORFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case FTPS:
                applyFTPSFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            case FTFPS:
                applyFTFPSFilter(jitRequestMap, mdmsCriteriaNode);
                break;
            default:
                throw new CustomException("INVALID_SERVICE_ID", "Invalid Service Id.");
        }

        return mdmsCriteriaNode;
    }

    public static Map<String, Object> convertObjectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();

        // Get all fields of the class, including private ones
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // to access private fields
            try {
                // Put field name and value in the map
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        }

        return map;
    }

    private void applyVAFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            String hoaCode = null;
            String ddoCode = null;
            if (jitRequestMap.containsKey("hoa") && jitRequestMap.get("hoa") instanceof String) {
                hoaCode = jitRequestMap.get("hoa").toString().trim();
            } else {
                throw new CustomException("INVALID_HOA_CODE", "Invalid HOA Code.");
            }
            if (jitRequestMap.containsKey("ddoCode") && jitRequestMap.get("ddoCode") instanceof String) {
                ddoCode = jitRequestMap.get("ddoCode").toString().trim();
            } else {
                throw new CustomException("INVALID_DDO_CODE", "Invalid DDO Code.");
            }
            mdmsCriteriaNode.set("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.VA.toString(), hoaCode, ddoCode))));
        } else {
            log.info("The object is not a Map.");
        }
    }


    private void applyPIFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        mdmsCriteriaNode.put("uniqueIdentifiers",  objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.PI.toString(), JITServiceId.PI.toString()))));
    }

    private void applyPISFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            String jitBillNo = null;
            if (jitRequestMap.containsKey("jitBillNo") && jitRequestMap.get("jitBillNo") instanceof String) {
                jitBillNo = jitRequestMap.get("jitBillNo").toString().trim();
            } else {
                throw new CustomException("INVALID_JIT_BILL_NO", "Invalid JIT Bill No.");
            }
            mdmsCriteriaNode.put("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.PIS.toString(), jitBillNo))));
        } else {
            log.info("The object is not a Map.");
        }

    }
    private void applyPAGFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            String jitBillNo = null;
            if (jitRequestMap.containsKey("billNo") && jitRequestMap.get("billNo") instanceof String) {
                jitBillNo = jitRequestMap.get("billNo").toString().trim();
            } else {
                throw new CustomException("INVALID_JIT_BILL_NO", "Invalid Bill No.");
            }
            mdmsCriteriaNode.put("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.PAG.toString(), jitBillNo))));
        } else {
            log.info("The object is not a Map.");
        }
    }
    private void applyPDFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            String billRefNo = null;
            if (jitRequestMap.containsKey("billRefNo") && jitRequestMap.get("billRefNo") instanceof String) {
                billRefNo = jitRequestMap.get("billRefNo").toString().trim();
            } else {
                throw new CustomException("INVALID_JIT_BILL_NO", "Invalid Bill Ref No.");
            }
            mdmsCriteriaNode.put("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.PD.toString(), billRefNo))));
        } else {
            log.info("The object is not a Map.");
        }

    }
    private void applyFDFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        ObjectNode filter = objectMapper.createObjectNode();
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            filter.put("serviceId", JITServiceId.FD.toString());
            mdmsCriteriaNode.put("filters", filter);
        } else {
            log.info("The object is not a Map.");
        }

    }
    private void applyCORFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        mdmsCriteriaNode.put("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.COR.toString(), JITServiceId.COR.toString()))));

    }
    private void applyFTPSFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        ObjectNode filter = objectMapper.createObjectNode();;
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            String jitCorBillNo = null;
            if (jitRequestMap.containsKey("jitCorBillNo") && jitRequestMap.get("jitCorBillNo") instanceof String) {
                jitCorBillNo = jitRequestMap.get("jitCorBillNo").toString().trim();
            } else {
                throw new CustomException("INVALID_JIT_BILL_NO", "Invalid Bill Ref No.");
            }
            mdmsCriteriaNode.put("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.FTPS.toString(), jitCorBillNo))));
        } else {
            log.info("The object is not a Map.");
        }
    }
    private void applyFTFPSFilter(Map<?, ?> jitRequestMap, ObjectNode mdmsCriteriaNode) {
        ObjectNode filter = objectMapper.createObjectNode();;
        if (jitRequestMap != null && jitRequestMap instanceof Map) {
            String jitCorBillNo = null;
            if (jitRequestMap.containsKey("jitCorBillNo") && jitRequestMap.get("jitCorBillNo") instanceof String) {
                jitCorBillNo = jitRequestMap.get("jitCorBillNo").toString().trim();
            } else {
                throw new CustomException("INVALID_JIT_BILL_NO", "Invalid Bill Ref No.");
            }
            mdmsCriteriaNode.put("uniqueIdentifiers", objectMapper.valueToTree(Collections.singletonList(String.join(".", JITServiceId.FTFPS.toString(), jitCorBillNo))));
        } else {
            log.info("The object is not a Map.");
        }
    }

}
