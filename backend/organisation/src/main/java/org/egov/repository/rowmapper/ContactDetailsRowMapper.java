package org.egov.repository.rowmapper;

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

            String contactDetailId = rs.getString("contactDetail_Id");
            String contactDetailTenantId = rs.getString("contactDetail_tenantId");
            String contactDetailOrgId = rs.getString("contactDetail_orgId");
            String contactDetailContactName = rs.getString("contactDetail_contactName");
            String contactDetailContactMobileNumber = rs.getString("contactDetail_contactMobileNumber");
            String contactDetailContactEmail = rs.getString("contactDetail_contactEmail");
            String contactDetailIndividualId = rs.getString("contactDetail_contactIndividualId");

            ContactDetails contactDetails = ContactDetails.builder()
                    .id(contactDetailId)
                    .tenantId(contactDetailTenantId)
                    .orgId(contactDetailOrgId)
                    .contactName(contactDetailContactName)
                    .contactMobileNumber(contactDetailContactMobileNumber)
                    .contactEmail(contactDetailContactEmail)
                    .individualId(contactDetailIndividualId)
                    .build();

            contactDetailsMap.computeIfAbsent(contactDetailId, k -> contactDetails);
        }

        return new ArrayList<>(contactDetailsMap.values());
    }
}
