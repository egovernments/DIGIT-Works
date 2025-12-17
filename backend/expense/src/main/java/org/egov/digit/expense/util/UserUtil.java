package org.egov.digit.expense.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.egov.common.contract.user.UserDetailResponse;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class UserUtil {

    private final ObjectMapper mapper;

    private final ServiceRequestRepository serviceRequestRepository;

    private final Configuration configs;

    private static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    private static final String PWD_EXPIRY_DATE = "pwdExpiryDate";


    @Autowired
    public UserUtil(ObjectMapper mapper, ServiceRequestRepository serviceRequestRepository, Configuration configs) {
        this.mapper = mapper;
        this.serviceRequestRepository = serviceRequestRepository;
        this.configs = configs;
    }

    /**
     * Returns UserDetailResponse by calling user service with given uri and object
     * @param userRequest Request object for user service
     * @param uri The address of the end point
     * @return Response from user service as parsed as userDetailResponse
     */

    public UserDetailResponse userCall(Object userRequest, StringBuilder uri) {
    	
        String dobFormat = null;
        if(uri.toString().contains(configs.getUserSearchEndpoint())  || uri.toString().contains(configs.getUserUpdateEndpoint()))
            dobFormat="yyyy-MM-dd";
        else if(uri.toString().contains(configs.getUserCreateEndpoint()))
            dobFormat = "dd/MM/yyyy";
        try{
            LinkedHashMap responseMap = (LinkedHashMap)serviceRequestRepository.fetchResult(uri, userRequest);
            parseResponse(responseMap,dobFormat);
            return mapper.convertValue(responseMap,UserDetailResponse.class);
        }
        catch(IllegalArgumentException  e)
        {
            throw new CustomException("IllegalArgumentException","ObjectMapper not able to convertValue in userCall");
        }
    }


    /**
     * Parses date formats to long for all users in responseMap
     * @param responseMap LinkedHashMap got from user api response
     */

    public void parseResponse(LinkedHashMap responseMap, String dobFormat){
        List<LinkedHashMap> users = (List<LinkedHashMap>)responseMap.get("user");
        String format1 = "dd-MM-yyyy HH:mm:ss";
        if(users!=null){
            users.forEach( map -> {
                        map.put("createdDate",dateTolong((String)map.get("createdDate"),format1));
                        if((String)map.get(LAST_MODIFIED_DATE)!=null)
                            map.put(LAST_MODIFIED_DATE,dateTolong((String)map.get(LAST_MODIFIED_DATE),format1));
                        if((String)map.get("dob")!=null)
                            map.put("dob",dateTolong((String)map.get("dob"),dobFormat));
                        if((String)map.get(PWD_EXPIRY_DATE)!=null)
                            map.put(PWD_EXPIRY_DATE,dateTolong((String)map.get(PWD_EXPIRY_DATE),format1));
                    }
            );
        }
    }

    /**
     * Converts date to long
     * @param date date to be parsed
     * @param format Format of the date
     * @return Long value of date
     */
    private Long dateTolong(String date,String format){
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date d;
        try {
            d = f.parse(date);
        } catch (ParseException e) {
            throw new CustomException("INVALID_DATE_FORMAT","Failed to parse date format in user");
        }
        return  d.getTime();
    }

}
