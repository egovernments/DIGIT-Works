package org.egov.repository.rowmapper;

import digit.models.coremodels.AuditDetails;
import org.egov.web.models.Address;
import org.egov.web.models.ContactDetails;
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
public class ContactDetailsRowMapper implements ResultSetExtractor<List<ContactDetails>> {
    @Override
    public List<ContactDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, ContactDetails> contactDetailsMap = new LinkedHashMap<>();

        while (rs.next()) {

            String contactDetail_Id = rs.getString("contactDetail_Id");
            String contactDetail_tenantId = rs.getString("contactDetail_tenantId");
            String contactDetail_orgId = rs.getString("contactDetail_orgId");
            String contactDetail_contactName = rs.getString("contactDetail_contactName");
            String contactDetail_contactMobileNumber = rs.getString("contactDetail_contactMobileNumber");
            String contactDetail_contactEmail = rs.getString("contactDetail_contactEmail");
            String contactDetail_individualId = rs.getString("contactDetail_contactIndividualId");

            ContactDetails contactDetails = ContactDetails.builder()
                    .id(contactDetail_Id)
                    .tenantId(contactDetail_tenantId)
                    .orgId(contactDetail_orgId)
                    .contactName(contactDetail_contactName)
                    .contactMobileNumber(contactDetail_contactMobileNumber)
                    .contactEmail(contactDetail_contactEmail)
                    .individualId(contactDetail_individualId)
                    .build();

            if (!contactDetailsMap.containsKey(contactDetail_Id)) {
                contactDetailsMap.put(contactDetail_Id, contactDetails);
            }
        }

        return new ArrayList<>(contactDetailsMap.values());
    }
}
