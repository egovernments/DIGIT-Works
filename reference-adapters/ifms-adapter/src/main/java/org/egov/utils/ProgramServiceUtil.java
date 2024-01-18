package org.egov.utils;

import lombok.extern.slf4j.Slf4j;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProgramServiceUtil {
    private final IfmsAdapterConfig config;
    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public ProgramServiceUtil(IfmsAdapterConfig config, ServiceRequestRepository serviceRequestRepository){
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
    }
    public void callProgramServiceOnSanctionOrAllocation(Object request, Boolean isSanction){
        log.info("ProgramServiceUtil::callProgramServiceOnSanctionOrAllocation");
        StringBuilder uriBuilder = getProgramServiceUrl(isSanction);
        log.info("ProgramServiceUtil::callProgramServiceOnSanctionOrAllocation::uri::"+ uriBuilder);
        Object result = serviceRequestRepository.fetchResult(uriBuilder,request);
        log.info("ProgramServiceUtil::callProgramServiceOnSanctionOrAllocation::result::"+result.toString());
    }
    private StringBuilder getProgramServiceUrl(Boolean isSanction){
        StringBuilder uriBuilder = new StringBuilder();
        return uriBuilder.append(config.getProgramServiceHost())
                .append(Boolean.TRUE.equals(isSanction)?config.getProgramServiceOnSanctionEndpoint():config.getProgramServiceOnAllocationEndpoint());
    }
}
