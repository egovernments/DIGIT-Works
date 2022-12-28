package org.egov.works.repository.rowmapper;

import digit.models.coremodels.AuditDetails;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.Target;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TargetRowMapper  implements ResultSetExtractor<List<Target>> {

    @Override
    public List<Target> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Target> targetMap = new LinkedHashMap<>();

        while (rs.next()) {

            String target_id = rs.getString("target_id");
            String target_projectid = rs.getString("target_projectid");
            String target_beneficiarytype = rs.getString("target_beneficiarytype");
            Integer target_totalno = rs.getInt("target_totalno");
            Integer target_targetno = rs.getInt("target_targetno");
            Boolean target_isdeleted = rs.getBoolean("target_isdeleted");
            String target_createdby = rs.getString("target_createdby");
            String target_lastmodifiedby = rs.getString("target_lastmodifiedby");
            Long target_createdtime = rs.getLong("target_createdtime");
            Long target_lastmodifiedtime = rs.getLong("target_lastmodifiedtime");

            AuditDetails targetAuditDetails = AuditDetails.builder().createdBy(target_createdby).createdTime(target_createdtime)
                    .lastModifiedBy(target_lastmodifiedby).lastModifiedTime(target_lastmodifiedtime)
                    .build();

            Target target = Target.builder()
                    .id(target_id)
                    .projectid(target_projectid)
                    .beneficiaryType(target_beneficiarytype)
                    .totalNo(target_totalno)
                    .targetNo(target_targetno)
                    .isDeleted(target_isdeleted)
                    .auditDetails(targetAuditDetails)
                    .build();

            if (!targetMap.containsKey(target_id)) {
                targetMap.put(target_id, target);
            }
        }

        return new ArrayList<>(targetMap.values());
    }
}
