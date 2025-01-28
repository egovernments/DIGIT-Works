package org.egov.repository.rowmapper;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.BankAccount;
import org.egov.web.models.BankAccountDetails;
import org.egov.web.models.BankBranchIdentifier;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
public class BankAccountRowMapper implements ResultSetExtractor<List<BankAccount>> {

    private final ObjectMapper mapper;

    @Autowired
    public BankAccountRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public List<BankAccount> extractData(ResultSet rs) throws SQLException, DataAccessException {
        log.info("BankAccountRowMapper::extractData");
        Map<String, BankAccount> bankAccountMap = new LinkedHashMap<>();
        Map<String, BankAccountDetails> bankAccountDetailMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("bankAcctId");
            String tenantId = rs.getString("tenant_id");
            String serviceCode = rs.getString("service_code");
            String referenceId = rs.getString("reference_id");

            String createdby = rs.getString("created_by");
            String lastmodifiedby = rs.getString("last_modified_by");
            Long createdtime = rs.getLong("created_time");
            Long lastmodifiedtime = rs.getLong("last_modified_time");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additional_details", rs);

            BankAccount bankAccount = BankAccount.builder().id(id)
                    .tenantId(tenantId).serviceCode(serviceCode).referenceId(referenceId).additionalFields(additionalDetails)
                    .auditDetails(auditDetails).build();

            if (!bankAccountMap.containsKey(id)) {
                bankAccountMap.put(id, bankAccount);
            }
            //one-to-many mapping- add bank account details
            addBankAccountDetails(rs, bankAccountDetailMap, bankAccountMap.get(id));
        }
        return new ArrayList<>(bankAccountMap.values());
    }

    private void addBankAccountDetails(ResultSet rs, Map<String, BankAccountDetails> bankAccountDetailMap, BankAccount bankAccount) throws SQLException {
        log.info("BankAccountRowMapper::addBankAccountDetails");
        String bankAcctDetailId = rs.getString("bankAcctDetailId");
        BankAccountDetails bankAccountDetails = BankAccountDetails.builder()
                .id(bankAcctDetailId)
                .accountNumber(rs.getString("account_number"))
                .accountHolderName(rs.getString("account_holder_name"))
                .accountType(rs.getString("account_type"))
                .tenantId(bankAccount.getTenantId())
                .isActive(rs.getBoolean("is_active"))
                .isPrimary(rs.getBoolean("is_primary"))
                .build();

        AuditDetails auditDetails = AuditDetails.builder()
                .createdBy(rs.getString("bankAcctDetailCreatedBy"))
                .lastModifiedBy(rs.getString("bankAcctDetailLastModifiedBy"))
                .createdTime(rs.getLong("bankAcctDetailCreatedTime"))
                .lastModifiedTime(rs.getLong("bankAcctDetailLastModifiedTime"))
                .build();

        bankAccountDetails.setAuditDetails(auditDetails);

        //Bank branch identifier
        BankBranchIdentifier bankBranchIdentifier = getBankIdentifier(bankAcctDetailId, rs);
        bankAccountDetails.setBankBranchIdentifier(bankBranchIdentifier);

        JsonNode additionalDetails = getAdditionalDetail("bankAcctDetailAdditional", rs);
        bankAccountDetails.setAdditionalFields(additionalDetails);

        if (!bankAccountDetailMap.containsKey(bankAcctDetailId)) {
            bankAccountDetailMap.put(bankAcctDetailId, bankAccountDetails);
        } else {
            bankAccountDetails = bankAccountDetailMap.get(bankAcctDetailId);
        }

        //set the Document detail -> Document <one-to-many mapping> : Start
        String bankAccountDetailId = rs.getString("bankAcctDocAcctId");

        Document bankDocument = Document.builder()
                .id(rs.getString("bankAcctDocId"))
                .documentType(rs.getString("document_type"))
                .fileStore(rs.getString("file_store"))
                .documentUid(rs.getString("document_uid"))
                .build();

        JsonNode bankDocAdditionalDetails = getAdditionalDetail("bankAcctDocAdditional", rs);
        bankDocument.setAdditionalDetails(bankDocAdditionalDetails);

        if (StringUtils.isNotBlank(bankAccountDetailId) && bankAccountDetailId.equals(bankAcctDetailId)) {
            if (bankAccountDetails.getDocuments() == null || bankAccountDetails.getDocuments().isEmpty()) {
                List<Document> bankDocumentList = new LinkedList<>();
                bankDocumentList.add(bankDocument);
                bankAccountDetails.setDocuments(bankDocumentList);
            } else {
                bankAccountDetails.getDocuments().add(bankDocument);
            }
        }
        //set the document detail -> amount details : End

        //set the bank account detail : Start
        if (bankAccount.getBankAccountDetails() == null || bankAccount.getBankAccountDetails().isEmpty()) {
            List<BankAccountDetails> bankAcctDetailList = new LinkedList<>();
            bankAcctDetailList.add(bankAccountDetails);
            bankAccount.setBankAccountDetails(bankAcctDetailList);
        } else {
            bankAccount.getBankAccountDetails().add(bankAccountDetails);
        }
        //set the bank account detail : Start
    }

    /**
     * @param bankAcctDetailId
     * @param rs
     * @return
     */
    private BankBranchIdentifier getBankIdentifier(String bankAcctDetailId, ResultSet rs) throws SQLException {
        log.info("BankAccountRowMapper::getBankIdentifier");
        BankBranchIdentifier bankBranchIdentifier = BankBranchIdentifier.builder()
                .id(rs.getString("bankAcctIdntfrId"))
                .code(rs.getString("code"))
                .type(rs.getString("type"))
                .build();

        JsonNode bankBranchIdentifierAdditionalDetails = getAdditionalDetail("bankAcctIdntfrAdditional", rs);
        bankBranchIdentifier.setAdditionalDetails(bankBranchIdentifierAdditionalDetails);

        return bankBranchIdentifier;
    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        log.info("BankAccountRowMapper::getAdditionalDetail");
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
