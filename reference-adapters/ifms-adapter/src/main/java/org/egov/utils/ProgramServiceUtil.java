package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.disburse.DisburseSearchRequest;
import org.egov.web.models.disburse.DisburseSearchResponse;
import org.egov.web.models.program.ProgramSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProgramServiceUtil {
    private final IfmsAdapterConfig config;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProgramServiceUtil(IfmsAdapterConfig config, ServiceRequestRepository serviceRequestRepository, ObjectMapper objectMapper){
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.objectMapper = objectMapper;
    }
    public void callProgramServiceOnSanctionOrAllocation(Object request, Boolean isSanction){
        log.info("ProgramServiceUtil::callProgramServiceOnSanctionOrAllocation");
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(config.getProgramServiceHost())
                .append(Boolean.TRUE.equals(isSanction)?config.getProgramServiceOnSanctionEndpoint():config.getProgramServiceOnAllocationEndpoint());
        log.info("ProgramServiceUtil::callProgramServiceOnSanctionOrAllocation::uri::"+ uriBuilder);
        Object result = serviceRequestRepository.fetchResult(uriBuilder,request);
        log.info("ProgramServiceUtil::callProgramServiceOnSanctionOrAllocation::result::"+result.toString());
    }
    public ProgramSearchResponse searchProgram(Object request){
        log.info("ProgramServiceUtil::searchProgram");
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(config.getProgramServiceHost())
                .append(config.getProgramServiceSearchEndpoint());
        log.info("ProgramServiceUtil::searchProgram::uri::"+ uriBuilder);
        Object result = serviceRequestRepository.fetchResult(uriBuilder,request);
        ProgramSearchResponse programSearchResponse = objectMapper.convertValue(result,ProgramSearchResponse.class);
        log.info("ProgramServiceUtil::searchProgram::result::"+result.toString());
        return programSearchResponse;
    }
    public DisburseSearchResponse searchDisbursements(DisburseSearchRequest request){
        log.info("ProgramServiceUtil::searchDisbursements");
        DisburseSearchResponse disburseSearchResponse = null;
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(config.getProgramServiceHost())
                .append(config.getProgramServiceDisburseSearchEndpoint());
        log.info("ProgramServiceUtil::searchDisbursements::uri::"+ uriBuilder);
        Object result = serviceRequestRepository.fetchResult(uriBuilder,request);
        log.info("ProgramServiceUtil::searchDisbursements::result::"+result.toString());
        try {
            disburseSearchResponse = objectMapper.convertValue(result, DisburseSearchResponse.class);
        }catch (Exception e){
            throw new CustomException("PARSING_ERROR","Failed to parse response from program service");
        }
        return disburseSearchResponse;
    }

    public void callOnDisburse(Object request){
        log.info("ProgramServiceUtil::callOnDisburse");
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(config.getProgramServiceHost())
                .append(config.getProgramServiceOnDisburseEndpoint());
        log.info("ProgramServiceUtil::callOnDisburse::uri::"+ uriBuilder);
        Object result = serviceRequestRepository.fetchResult(uriBuilder,request);
        log.info("ProgramServiceUtil::callOnDisburse::result::"+result.toString());
    }
}
