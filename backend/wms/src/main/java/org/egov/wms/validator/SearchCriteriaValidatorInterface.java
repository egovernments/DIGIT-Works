package org.egov.wms.validator;

import org.egov.tracer.model.CustomException;
import org.egov.wms.web.model.WMSSearchRequest;

public interface SearchCriteriaValidatorInterface {

    /**
     * This method will validate if the input search criteria is
     * valid or not for the given module. It will return true if
     * the criteria is valid else it will return false
     * @param wmsSearchRequest
     * @param module
     * @return
     */
    public void validateSearchCriteria(WMSSearchRequest wmsSearchRequest, String module) throws CustomException;

}
