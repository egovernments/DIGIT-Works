package org.egov.digit.expense.repository.rowmapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.BillApproval;
import org.egov.digit.expense.web.models.enums.ApprovalStatus;
import org.egov.digit.expense.web.models.enums.SignatureMethod;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BillApprovalRowMapper implements ResultSetExtractor<List<BillApproval>> {

    @Override
    public List<BillApproval> extractData(ResultSet rs) throws SQLException {
        List<BillApproval> approvals = new ArrayList<>();

        while (rs.next()) {
            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("created_by"))
                    .createdTime(rs.getLong("created_time"))
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .lastModifiedTime(rs.getLong("last_modified_time"))
                    .build();

            BillApproval approval = BillApproval.builder()
                    .id(rs.getString("id"))
                    .tenantId(rs.getString("tenant_id"))
                    .billId(rs.getString("bill_id"))
                    .billNumber(rs.getString("bill_number"))
                    .businessService(rs.getString("business_service"))
                    .approverUuid(rs.getString("approver_uuid"))
                    .approverName(rs.getString("approver_name"))
                    .role(rs.getString("role"))
                    .signatureMethod(SignatureMethod.fromValue(rs.getString("signature_method")))
                    .signatureFileStoreId(rs.getString("signature_file_store_id"))
                    .approvalStatus(ApprovalStatus.fromValue(rs.getString("approval_status")))
                    .approvedTime(rs.getLong("approved_time"))
                    .auditDetails(auditDetails)
                    .build();

            approvals.add(approval);
        }

        return approvals;
    }
}
