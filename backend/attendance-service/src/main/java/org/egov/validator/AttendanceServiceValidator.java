package org.egov.validator;

import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AttendanceServiceValidator {

    public void validateSearchEstimate(RequestInfoWrapper requestInfoWrapper, AttendanceRegisterSearchCriteria searchCriteria) {
        if (searchCriteria == null || requestInfoWrapper == null || requestInfoWrapper.getRequestInfo() == null) {
            throw new CustomException("ATTENDANCE_REGISTER_SEARCH_CRITERIA_REQUEST", "Attendance register search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
    }

}
