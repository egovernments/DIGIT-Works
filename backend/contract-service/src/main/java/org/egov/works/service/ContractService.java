package org.egov.works.service;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.enrichment.ContractEnrichment;
import org.egov.works.kafka.Producer;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.validator.ContractServiceValidator;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.ContractCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContractService {

    @Autowired
    private ContractServiceValidator contractServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private Producer producer;

    @Autowired
    private ContractServiceConfiguration contractServiceConfiguration;

    @Autowired
    private ContractEnrichment contractEnrichment;



    public List<Contract> searchContracts(RequestInfoWrapper requestInfoWrapper, ContractCriteria contractCriteria){

        //Validate the requested parameters
        contractServiceValidator.validateSearchContractRequest(requestInfoWrapper, contractCriteria);

        //Enrich requested search criteria
        contractEnrichment.enrichSearchContractRequest(requestInfoWrapper.getRequestInfo(),contractCriteria);

        //get contracts from






        return null;
    }

}
