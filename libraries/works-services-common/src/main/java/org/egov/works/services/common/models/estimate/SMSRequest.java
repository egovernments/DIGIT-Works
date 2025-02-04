package org.egov.works.services.common.models.estimate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SMSRequest {
    private String mobileNumber;
    private String message;
}

