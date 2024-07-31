package org.egov.works.mukta.adapter.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.web.models.*;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DisbursementRowMapper implements ResultSetExtractor<List<Disbursement>> {
    private final ObjectMapper objectMapper;

    @Autowired
    public DisbursementRowMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Disbursement> extractData(ResultSet rs) throws SQLException, DataAccessException{
        Map<String, Disbursement> disbursementMap = new HashMap<>();
        while (rs.next()){
            String id = rs.getString("disburseId");
            Disbursement disbursement = disbursementMap.get(id);
            if(disbursement == null){
                String disburseProgramCode = rs.getString("disburseProgramCode");
                String disburseTargetId = rs.getString("disburseTargetId");
                String disburseAccountCode = rs.getString("disburseAccountCode");
                String disburseParentId = rs.getString("disburseParentId");
                String disburseTransactionId = rs.getString("disburseTransactionId");
                String disburseStatus = rs.getString("disburseStatus");
                String disburseStatusMessage = rs.getString("disburseStatusMessage");
                JsonNode disburseIndividual = getAdditionalDetail(rs, "disburseIndividual");
                BigDecimal disburseNetAmount = rs.getBigDecimal("disburseNetAmount");
                BigDecimal disburseGrossAmount = rs.getBigDecimal("disburseGrossAmount");
                Long disburseCreatedTime = rs.getLong("disbursecreatedtime");
                String disburseCreatedBy = rs.getString("disburseCreatedBy");
                Long disburseLastModifiedTime = rs.getLong("disburseLastModifiedTime");
                String disburseLastModifiedBy = rs.getString("disburseLastModifiedBy");
                String codeLocationCode = rs.getString("codeLocationCode");
                String codeFunctionCode = rs.getString("codeFunctionCode");
                String codeAdministrationCode = rs.getString("codeAdministrationCode");
                String codeProgramCode = rs.getString("codeProgramCode");
                String codeRecipientSegmentCode = rs.getString("codeRecipientSegmentCode");
                String codeEconomicSegmentCode = rs.getString("codeEconomicSegmentCode");
                String codeSourceOfFundCode = rs.getString("codeSourceOfFundCode");
                String codeTargetSegmentCode = rs.getString("codeTargetSegmentCode");
                JsonNode codeAdditionalDetails = getAdditionalDetail(rs, "codeAdditionalDetails");

                Status status = Status.builder().statusCode(StatusCode.fromValue(disburseStatus)).statusMessage(disburseStatusMessage).build();
                AuditDetails auditDetails = AuditDetails.builder().createdBy(disburseCreatedBy).createdTime(disburseCreatedTime).lastModifiedBy(disburseLastModifiedBy).lastModifiedTime(disburseLastModifiedTime).build();
                Individual individual = null;
                try {
                    individual = objectMapper.treeToValue(disburseIndividual, Individual.class);
                } catch (Exception e){
                    System.out.println(e.fillInStackTrace());
                }
                disbursement = new Disbursement();
                disbursement.setId(id);
                disbursement.setProgramCode(disburseProgramCode);
                disbursement.setTargetId(disburseTargetId);
                disbursement.setTransactionId(disburseTransactionId);
                disbursement.setAccountCode(disburseAccountCode);
                disbursement.setParentId(disburseParentId);
                disbursement.setStatus(status);
                disbursement.setIndividual(individual);
                disbursement.setNetAmount(disburseNetAmount);
                disbursement.setGrossAmount(disburseGrossAmount);
                disbursement.setAuditDetails(auditDetails);
                disbursement.setLocationCode(codeLocationCode);
                disbursement.setFunctionCode(codeFunctionCode);
                disbursement.setAdministrationCode(codeAdministrationCode);
                disbursement.setProgramCode(codeProgramCode);
                disbursement.setRecipientSegmentCode(codeRecipientSegmentCode);
                disbursement.setEconomicSegmentCode(codeEconomicSegmentCode);
                disbursement.setSourceOfFundCode(codeSourceOfFundCode);
                disbursement.setTargetSegmentCode(codeTargetSegmentCode);
                disbursement.setAdditionalDetails(codeAdditionalDetails);

                disbursementMap.put(id, disbursement);
            }
        }

        log.debug("converting map to list object ::: " + disbursementMap.values());
        return new ArrayList<>(disbursementMap.values());
    }

    private JsonNode getAdditionalDetail(ResultSet rs, String key) throws SQLException {

        JsonNode additionalDetails = null;

        try {

            PGobject obj = (PGobject) rs.getObject(key);
            if (obj != null) {
                additionalDetails = objectMapper.readTree(obj.getValue());
            }

        } catch (IOException e) {
            throw new CustomException(Error.PARSING_ERROR, Error.PARSE_ERROR_MESSAGE);
        }

        if(additionalDetails.isEmpty())
            additionalDetails = null;

        return additionalDetails;

    }
}
