package org.egov.works.service;

import org.egov.works.config.LOIConfiguration;
import org.egov.works.web.models.LOISearchCriteria;
import org.egov.works.web.models.LOISearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrichmentService {


    @Autowired
    private LOIConfiguration config;

    /**
     * @param searchCriteria
     */
    public void enrichSearchLOI(LOISearchCriteria searchCriteria) {

        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(config.getDefaultLimit());

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(config.getDefaultOffset());

        if (searchCriteria.getLimit() != null && searchCriteria.getLimit() > config.getMaxLimit())
            searchCriteria.setLimit(config.getMaxLimit());
    }
}
