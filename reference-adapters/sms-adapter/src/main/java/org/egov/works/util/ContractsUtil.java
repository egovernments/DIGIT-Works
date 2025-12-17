package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.models.Contract;
import org.egov.works.models.ContractCriteria;
import org.egov.works.models.ContractResponse;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ContractsUtil {

    @Autowired
    private NotificationServiceConfiguration notificationServiceConfiguration;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public Object getContracts(ContractCriteria contractCriteria) {

        StringBuilder uri = new StringBuilder(notificationServiceConfiguration.getContractHost()).append(notificationServiceConfiguration.getContractEndpoint());

        return serviceRequestRepository.fetchResult(uri, contractCriteria);

    }

}
