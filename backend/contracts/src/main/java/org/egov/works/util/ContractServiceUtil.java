package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.service.ContractService;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class ContractServiceUtil {

    private final ContractServiceConfiguration config;

    private final ContractService contractService;

    @Autowired
    public ContractServiceUtil(ContractServiceConfiguration config, ContractService contractService) {
        this.config = config;
        this.contractService = contractService;
    }

}
