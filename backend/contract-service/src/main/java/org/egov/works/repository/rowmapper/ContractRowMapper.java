package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.Document;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class ContractRowMapper implements ResultSetExtractor<List<Contract>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Contract> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, Contract> contractMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String contractNumber = rs.getString("contractNumber");
            String tenantId = rs.getString("tenantId");
            String wfStatus = rs.getString("wfStatus");
            String executingAuthority = rs.getString("executingAuthority");
            String contractType = rs.getString("contractType");
            BigDecimal totalContractedAmount = rs.getBigDecimal("totalContractedamount");
            BigDecimal securityDeposit = rs.getBigDecimal("securityDeposit");
            BigDecimal agreementDate = rs.getBigDecimal("agreementDate");
            BigDecimal defectLiabilityPeriod = rs.getBigDecimal("defectLiabilityPeriod");
            String orgId = rs.getString("orgId");
            BigDecimal startDate = rs.getBigDecimal("startDate");
            BigDecimal endDate = rs.getBigDecimal("endDate");
            String status = rs.getString("status");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastModifiedBy");
            Long createdtime = rs.getLong("createdTime");
            Long lastmodifiedtime = rs.getLong("lastModifiedTime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);

            Contract contract = Contract.builder()
                    .id(id)
                    .contractNumber(contractNumber)
                    .tenantId(tenantId)
                    .wfStatus(wfStatus)
                    .executingAuthority(Contract.ExecutingAuthorityEnum.fromValue(executingAuthority))
                    .contractType(Contract.ContractTypeEnum.fromValue(contractType))
                    .totalContractedamount(totalContractedAmount)
                    .securityDeposit(securityDeposit)
                    .agreementDate(agreementDate)
                    .defectLiabilityPeriod(defectLiabilityPeriod)
                    .orgId(orgId)
                    .startDate(startDate)
                    .endDate(endDate)
                    .status(status)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!contractMap.containsKey(id)) {
                contractMap.put(id, contract);
            }
            addDocumentsDetails(rs, contractMap.get(id));
        }
        return new ArrayList<>(contractMap.values());
    }


    private void addDocumentsDetails(ResultSet rs, Contract contract) throws SQLException {
        String documentId = rs.getString("docId");
        String contractId = rs.getString("id");
        if (StringUtils.isNotBlank(documentId) && contractId.equalsIgnoreCase(contract.getId())) {
            Document document = Document.builder()
                    .id(documentId)
                    .documentType(rs.getString("docDocumentType"))
                    .fileStore(rs.getString("docFileStoreId"))
                    .documentUid(rs.getString("docDocumentUid"))
                    .status(rs.getString("docStatus"))
                    .contractId(rs.getString("docContractIid"))
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("docAdditionalDetails", rs);
            document.setAdditionalDetails(additionalDetails);

            if (contract.getDocuments() == null || contract.getDocuments().isEmpty()) {
                List<Document> documentList = new LinkedList<>();
                documentList.add(document);
                contract.setDocuments(documentList);
            } else {
                contract.getDocuments().add(document);
            }
        }
    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            log.error("Failed to parse additionalDetail object");
            throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
