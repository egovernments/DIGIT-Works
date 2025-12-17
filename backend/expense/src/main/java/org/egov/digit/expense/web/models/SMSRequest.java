package org.egov.digit.expense.web.models;

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

