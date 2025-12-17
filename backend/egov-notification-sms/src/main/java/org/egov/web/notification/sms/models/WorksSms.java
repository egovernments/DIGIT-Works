package org.egov.web.notification.sms.models;

import lombok.*;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class WorksSms {

    private String mobileNumber;
    private String message;
    private Map<String,Object> additionalFields;

    public boolean isValid() {

        return isNotEmpty(mobileNumber) && isNotEmpty(message);
    }
}
