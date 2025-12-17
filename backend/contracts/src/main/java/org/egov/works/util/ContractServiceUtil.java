package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.ServiceCallException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
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

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ContractServiceConfiguration config;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ContractService contractService;

    private StringBuilder getURLWithParams() {
        StringBuilder url = new StringBuilder(config.getContractHost());
        url.append(config.getContractEndpoint());
        return url;
    }

    public ContractResponse fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        ContractResponse response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, ContractResponse.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
        }

        return response;
    }

    public ContractResponse fetchContractResponse(ContractRequest contractRequest) {
        StringBuilder url = getURLWithParams();
        return fetchResult(url, contractRequest);
    }
}
