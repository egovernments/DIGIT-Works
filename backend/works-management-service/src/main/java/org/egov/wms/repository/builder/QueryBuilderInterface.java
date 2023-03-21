package org.egov.wms.repository.builder;


import org.egov.wms.web.model.WMSSearchRequest;

import java.util.Map;

public interface QueryBuilderInterface{

    /**
     * This method will take the incoming request and create
     * elasticsearch query based on input search criteria
     * @param wmsSearchRequest
     * @param module
     * @return
     */
    public Map<String, Object> getESQuery(WMSSearchRequest wmsSearchRequest, Boolean isPaginationRequired, String module);


    /**
     * This method will take the incoming request and create
     * elasticsearch query based on input search criteria
     * to get status count map
     * @param wmsSearchRequest
     * @return
     */
    public Map<String, Object> getStatusCountQuery(WMSSearchRequest wmsSearchRequest);

    /**
     * This method will take the incoming request and create
     * elasticsearch query based on input search criteria
     * to get nearing sla count
     * @param wmsSearchRequest
     * @return
     */
    public Map<String, Object> getNearingSlaCountQuery(WMSSearchRequest wmsSearchRequest, Long businessServiceSla, String module);

}