package org.egov.models;

import digit.models.coremodels.AuditDetails;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BillDemand {

    private String id;
    private String tenantId;
    private String billNumber;
    private Long billDate;
    private Long netAmount;
    private Long grossAmount;
    private String headOfAccount;
    private String ifmsSanctionNumber;
    private String purpose;
    private List<Beneficiary> beneficiaries;
    private AuditDetails auditDetails;

}
