package org.egov.wms.validator;


import org.egov.tracer.model.CustomException;
import org.egov.wms.util.MDMSUtil;
import org.egov.wms.web.model.WMSSearchRequest;
import org.egov.wms.web.model.V2.SearchQueryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

import static org.egov.wms.util.WMSConstants.SORT_BY_CONSTANT;
import static org.egov.wms.util.WMSConstants.SORT_ORDER_CONSTANT;

@Component
public class ValidatorDefaultImplementation implements SearchCriteriaValidatorInterface {


    @Autowired
    private MDMSUtil mdmsUtil;

    @Override
    public void validateSearchCriteria(WMSSearchRequest wmsSearchRequest, String module) {
        SearchQueryConfiguration config = mdmsUtil.getConfigFromMDMS(wmsSearchRequest,module);
        
        Map<String, Boolean> isMandatoryMap = new HashMap<>();
        
        config.getAllowedSearchCriteria().forEach(
                searchParam -> {
                    isMandatoryMap.put(searchParam.getName(), ObjectUtils.isEmpty(searchParam.getIsMandatory()) ? Boolean.FALSE : searchParam.getIsMandatory());
                }
        );

        HashMap<String,Object> moduleSearchCriteria = wmsSearchRequest.getInbox().getModuleSearchCriteria();

        Map<String, String> errorMap  = new HashMap<>();
        for(Map.Entry<String, Object> entry: moduleSearchCriteria.entrySet()){

            String key = entry.getKey();
            Object value = entry.getValue();

            if(!(key.equals(SORT_ORDER_CONSTANT) || key.equals(SORT_BY_CONSTANT) || key.equals("moduleName"))) {

                if (isMandatoryMap.get(key)) {
                    if (ObjectUtils.isEmpty(value)) {
                        errorMap.put("INVALID_SEARCH_CRITERIA", "Field cannot be null or empty: " + key);
                    }
                }
            }
        }

        if(!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);

    }





}
