package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.web.models.ProcessInstance;
import org.egov.works.web.models.ProcessInstanceResponse;
import org.egov.works.web.models.RequestInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class WorkflowUtil {
    private static final String TENANT_ID = "?tenantId=";
    private final StatementConfiguration serviceConfiguration;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ObjectMapper mapper;

    @Autowired
    public WorkflowUtil(StatementConfiguration serviceConfiguration, ServiceRequestRepository serviceRequestRepository, ObjectMapper mapper) {
        this.serviceConfiguration = serviceConfiguration;
        this.serviceRequestRepository = serviceRequestRepository;
        this.mapper = mapper;
    }

    public StringBuilder getprocessInstanceSearchURL(String tenantId, String estimateNumber) {
        log.info("WorkflowService::getprocessInstanceSearchURL");
        StringBuilder url = new StringBuilder(serviceConfiguration.getWfHost());
        url.append(serviceConfiguration.getWfProcessInstanceSearchPath());
        url.append(TENANT_ID);
        url.append(tenantId);
        url.append("&businessIds=");
        url.append(estimateNumber);
        url.append("&history=true");
        return url;
    }

    public Long getProcessInstance(RequestInfo requestInfo, Estimate estimate) {
        log.info("WorkflowService::getProcessInstance");
        StringBuilder uri = null;
        if(estimate.getRevisionNumber() != null) {
            uri = getprocessInstanceSearchURL(estimate.getTenantId(), estimate.getRevisionNumber());
        } else {
            uri = getprocessInstanceSearchURL(estimate.getTenantId(), estimate.getEstimateNumber());
        }
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = null;
        ProcessInstanceResponse processInstanceResponse = null;
        try {
            result = serviceRequestRepository.fetchResult(uri, requestInfoWrapper);
            processInstanceResponse = mapper.convertValue(result, ProcessInstanceResponse.class);
        } catch (Exception e) {
            log.error("Exception while fetching process instance: ", e);
        }
        List<ProcessInstance> processInstance = processInstanceResponse.getProcessInstances();
        Long time = null;
        for(ProcessInstance instance : processInstance) {
            if(instance.getAction().equalsIgnoreCase("SUBMIT") || instance.getAction().equalsIgnoreCase("RE-SUBMIT") || instance.getAction().equalsIgnoreCase("DRAFT")) {
                time = instance.getAuditDetails().getLastModifiedTime();
                break;
            }
        }
        return time;
    }
}
