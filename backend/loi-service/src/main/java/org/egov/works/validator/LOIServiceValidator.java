package org.egov.works.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.LOISearchCriteria;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class LOIServiceValidator {

       public void validateSearchLOI(LOISearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            throw new CustomException("LOI", "LOI is mandatory");
        }

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant Id is mandatory");
        }
    }
}
