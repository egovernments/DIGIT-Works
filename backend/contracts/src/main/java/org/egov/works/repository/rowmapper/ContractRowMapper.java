package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.*;
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

    private final ObjectMapper mapper;

    @Autowired
    public ContractRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Contract> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Contract> contractMap = new LinkedHashMap<>();

        while (rs.next()) {
            String contractId = rs.getString("contractId");

            Contract contract = contractMap.get(contractId);
            if (contract == null) {
                String contractNumber = rs.getString("contractNumber");
                String supplementNumber = rs.getString("supplementNumber");
                Long versionNumber = rs.getLong("versionNumber");
                String oldUuid = rs.getString("oldUuid");
                String businessService = rs.getString("businessService");
                String tenantId = rs.getString("contractTenantId");
                String wfStatus = rs.getString("wfStatus");
                String executingAuthority = rs.getString("executingAuthority");
                String contractType = rs.getString("contractType");
                BigDecimal totalContractedAmount = rs.getBigDecimal("totalContractedAmount");
                BigDecimal securityDeposit = rs.getBigDecimal("securityDeposit");
                BigDecimal agreementDate = rs.getBigDecimal("agreementDate");
                BigDecimal defectLiabilityPeriod = rs.getBigDecimal("defectLiabilityPeriod");
                String orgId = rs.getString("orgId");
                BigDecimal startDate = rs.getBigDecimal("startDate");
                BigDecimal endDate = rs.getBigDecimal("endDate");
                String status = rs.getString("contractStatus");
                String createdBy = rs.getString("contractCreatedBy");
                String lastModifiedBy = rs.getString("contractLastModifiedBy");
                Long createdTime = rs.getLong("contractCreatedTime");
                Long lastModifiedTime = rs.getLong("contractLastModifiedTime");
                BigDecimal issueDate = rs.getBigDecimal("issueDate");
                Integer completionPeriod = rs.getInt("completionPeriod");

                AuditDetails auditDetails = AuditDetails.builder()
                        .createdBy(createdBy)
                        .createdTime(createdTime)
                        .lastModifiedBy(lastModifiedBy)
                        .lastModifiedTime(lastModifiedTime)
                        .build();

                JsonNode additionalDetails = getAdditionalDetail("contractAdditionalDetails", rs);

                contract = Contract.builder()
                        .id(contractId)
                        .contractNumber(contractNumber)
                        .supplementNumber(supplementNumber)
                        .versionNumber(versionNumber)
                        .oldUuid(oldUuid)
                        .businessService(businessService)
                        .tenantId(tenantId)
                        .wfStatus(wfStatus)
                        .executingAuthority(executingAuthority)
                        .contractType(contractType)
                        .totalContractedAmount(totalContractedAmount)
                        .securityDeposit(securityDeposit)
                        .agreementDate(agreementDate)
                        .defectLiabilityPeriod(defectLiabilityPeriod)
                        .orgId(orgId)
                        .startDate(startDate)
                        .endDate(endDate)
                        .status(Status.fromValue(status))
                        .additionalDetails(additionalDetails)
                        .auditDetails(auditDetails)
                        .issueDate(issueDate)
                        .completionPeriod(completionPeriod)
                        .documents(new LinkedList<>())
                        .lineItems(new LinkedList<>())
                        .build();

                contractMap.put(contractId, contract);
            }

            // Add document details
            addDocumentsDetails(rs, contract);

            // Add line item details
            addLineItemsDetails(rs, contract);
        }
        return new ArrayList<>(contractMap.values());
    }

    private void addDocumentsDetails(ResultSet rs, Contract contract) throws SQLException {
        String documentId = rs.getString("docId");
        String contractId = rs.getString("docContractId");

        if (StringUtils.isNotBlank(documentId) && contractId.equalsIgnoreCase(contract.getId())) {
            Document document = Document.builder()
                    .id(documentId)
                    .documentType(rs.getString("docDocumentType"))
                    .fileStore(rs.getString("docFileStoreId"))
                    .documentUid(rs.getString("docDocumentUid"))
                    .status(Status.fromValue(rs.getString("docStatus")))
                    .contractId(contractId)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("docAdditionalDetails", rs);
            document.setAdditionalDetails(additionalDetails);

            contract.getDocuments().add(document);
        }
    }

    private void addLineItemsDetails(ResultSet rs, Contract contract) throws SQLException {
        String lineItemId = rs.getString("lineItemId");
        if (StringUtils.isNotBlank(lineItemId)) {
            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("lineItemCreatedBy"))
                    .lastModifiedBy(rs.getString("lineItemLastModifiedBy"))
                    .createdTime(rs.getLong("lineItemCreatedTime"))
                    .lastModifiedTime(rs.getLong("lineItemLastModifiedTime"))
                    .build();
            LineItems lineItem = LineItems.builder()
                    .id(lineItemId)
                    .estimateId(rs.getString("estimateId"))
                    .estimateLineItemId(rs.getString("estimateLineItemId"))
                    .contractLineItemRef(rs.getString("contractLineItemRef"))
                    .contractId(rs.getString("lineItemContractId"))
                    .tenantId(rs.getString("lineItemTenantId"))
                    .unitRate(rs.getDouble("unitRate"))
                    .noOfunit(rs.getDouble("noOfUnit"))
                    .status(Status.fromValue(rs.getString("lineItemStatus")))
                    .auditDetails(auditDetails)
                    .amountBreakups(new ArrayList<>())
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("lineItemAdditionalDetails", rs);
            lineItem.setAdditionalDetails(additionalDetails);

            addAmountBreakUps(rs, lineItem);

            contract.getLineItems().add(lineItem);
        }
    }

    private void addAmountBreakUps(ResultSet rs, LineItems lineItems) throws  SQLException {
        String amountBreakUpId = rs.getString("amtId");
        if (StringUtils.isNotBlank(amountBreakUpId)) {
            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("amtCreatedBy"))
                    .lastModifiedBy(rs.getString("amtLastModifiedBy"))
                    .createdTime(rs.getLong("amtCreatedTime"))
                    .lastModifiedTime(rs.getLong("amtLastModifiedTime"))
                    .build();
            AmountBreakup amountBreakup = AmountBreakup.builder()
                    .id(amountBreakUpId)
                    .estimateAmountBreakupId(rs.getString("amtEstimateAmountBreakupId"))
                    .lineItemId(rs.getString("amtLineItemId"))
                    .amount(rs.getDouble("amtAmount"))
                    .status(Status.fromValue(rs.getString("amtStatus")))
                    .auditDetails(auditDetails)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("amtAdditionalDetails", rs);
            amountBreakup.setAdditionalDetails(additionalDetails);

            lineItems.getAmountBreakups().add(amountBreakup);
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
        if (additionalDetails.isEmpty()) {
            additionalDetails = null;
        }
        return additionalDetails;
    }
}
