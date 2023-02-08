package org.egov.works.enrichment;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class ContractEnrichment {

    @Autowired
    private ContractServiceConfiguration config;

    public void enrichSearchContractRequest(RequestInfo requestInfo, ContractCriteria contractCriteria) {

        Pagination pagination=contractCriteria.getPagination();

        if (pagination.getLimit() == null)
            pagination.setLimit(config.getContractDefaultLimit());

        if (pagination.getOffSet() == null)
            pagination.setOffSet(config.getContractDefaultOffset());

        if (pagination.getLimit() != null && pagination.getLimit().compareTo(config.getContractMaxLimit())>0)
            pagination.setLimit(config.getContractMaxLimit());
    }
}
