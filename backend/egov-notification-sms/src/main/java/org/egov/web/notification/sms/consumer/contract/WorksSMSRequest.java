package org.egov.web.notification.sms.consumer.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.egov.web.notification.sms.models.Category;
import org.egov.web.notification.sms.models.Sms;
import org.egov.web.notification.sms.models.WorksSms;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorksSMSRequest {

    @Pattern(regexp = "^[0-9]{10}$", message = "MobileNumber should be 10 digit number")
    private String mobileNumber;

    @Size(max = 1000)
    private String message;

    private Category category;
    private Long expiryTime;

    // Need to pass template code, request info and tenant id;
    private Map<String,Object> additionalFields;

    public WorksSms toDomain() {

        if(additionalFields==null){
            return new WorksSms(mobileNumber, message,new HashMap<>());
        }else {
            return new WorksSms(mobileNumber, message, additionalFields);
        }

    }
}

