package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.LOISearchCriteria;
import org.egov.works.web.models.LOISearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
