package org.egov.web.models;

import digit.models.coremodels.user.enums.UserType;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserSearchCriteria {

    private List<Long> id;
    private List<String> uuid;
    private String userName;
    private String name;
    private String mobileNumber;
    private String emailId;
    private boolean fuzzyLogic;
    private Boolean active;
    private Integer offset;
    private Integer limit;
    private List<String> sort;
    private UserType type;
    private String tenantId;
    private List<String> roleCodes;
    private String alternatemobilenumber;

}
