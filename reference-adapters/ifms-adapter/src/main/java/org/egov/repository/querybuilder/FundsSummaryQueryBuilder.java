package org.egov.repository.querybuilder;

import org.springframework.stereotype.Component;

@Component
public class FundsSummaryQueryBuilder {
    public static final String FUNDS_SUMMARY_INSERT_QUERY = "INSERT INTO jit_funds_summary (id, tenantId, sanctionId,"
            + " allottedAmount, availableAmount, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :sanctionId, :allottedAmount, :availableAmount, :additionalDetails,"
            + " :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";

    public static final String FUNDS_SUMMARY_UPDATE_QUERY = "UPDATE jit_funds_summary SET allottedAmount=:allottedAmount, availableAmount=:availableAmount,"
            + "additionalDetails=:additionalDetails, lastmodifiedby=:lastmodifiedby,lastmodifiedtime=:lastmodifiedtime "
            + " WHERE id=:id";


}
