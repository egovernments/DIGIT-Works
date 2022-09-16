package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.egov.works.web.models.LetterOfIndent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class LOIRowMapper implements ResultSetExtractor<List<LetterOfIndent>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<LetterOfIndent> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, LetterOfIndent> loiMap = new LinkedHashMap<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenantId");
            String loiNumber = rs.getString("loi_number");
            String workPkgNumber = rs.getString("work_pkg_number");
            String workIdentificationNumber = rs.getString("work_identification_number");
            String fileNumber = rs.getString("file_number");
            BigDecimal negotiatedPercentage = rs.getBigDecimal("negotiated_percentage");
            BigDecimal agreementDate = rs.getBigDecimal("agreement_date");
            String contractorId = rs.getString("contractor_id");
            BigDecimal securityDeposit = rs.getBigDecimal("security_deposit");
            String bankGaurante = rs.getString("bank_gaurante");
            BigDecimal emdAmount = rs.getBigDecimal("emd_amount");
            BigDecimal contractPeriod = rs.getBigDecimal("contract_period");
            BigDecimal defectLiabilityPeriod = rs.getBigDecimal("defect_liability_period");
            String oicId = rs.getString("oic_id");
            String status = rs.getString("status");
            String letterStatus = rs.getString("letter_status");
            String additionalDetails = rs.getString("additionaldetails");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastmodifiedby");
            Long createdtime = rs.getLong("createdtime");
            Long lastmodifiedtime = rs.getLong("lastmodifiedtime");


            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();


            LetterOfIndent estimate = LetterOfIndent.builder().id(UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)))
                    .tenantId(tenantId)
                    .letterOfIndentNumber(loiNumber)
                    .workPackageNumber(workPkgNumber)
                    .workIdentificationNumber(workIdentificationNumber)
                    .status(LetterOfIndent.StatusEnum.fromValue(status))
                    .fileNumber(fileNumber)
                    .negotiatedPercentage(negotiatedPercentage)
                    .agreementDate(agreementDate)
                    .contractorId(contractorId)
                    .securityDeposit(securityDeposit)
                    .bankGaurante(bankGaurante)
                    .emdAmount(emdAmount)
                    .contractPeriod(contractPeriod)
                    .defectLiabilityPeriod(defectLiabilityPeriod)
                    .oicId(UUID.nameUUIDFromBytes(oicId.getBytes(StandardCharsets.UTF_8)))
                    .letterStatus(letterStatus)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();
            loiMap.put(id, estimate);
        }
        return new ArrayList<>(loiMap.values());
    }


}
