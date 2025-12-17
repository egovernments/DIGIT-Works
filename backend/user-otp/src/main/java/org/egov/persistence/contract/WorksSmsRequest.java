package org.egov.persistence.contract;

import lombok.*;
import org.egov.domain.model.Category;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WorksSmsRequest {

    private String mobileNumber;
    private String message;
    private Category category;
    private long expiryTime;

    private Map<String,Object> additionalFields;


}
