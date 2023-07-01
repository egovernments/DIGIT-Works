package org.egov.repository.querybuilder;

import org.springframework.stereotype.Component;

@Component
public class AllotmentDetailsQueryBuilder {
    public static final String ALLOTMENT_DETAILS_INSERT_QUERY = "INSERT INTO jit_allotment_details (id, tenantId, sanctionId, allotmentSerialNo, ssuAllotmentId,"
            + " allotmentAmount, allotmentTransactionType, sanctionBalance, allotmentDate, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :sanctionId, :allotmentSerialNo, :ssuAllotmentId, :allotmentAmount, :allotmentTransactionType, :sanctionBalance, :allotmentDate,"
            + " :additionalDetails, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";


}
