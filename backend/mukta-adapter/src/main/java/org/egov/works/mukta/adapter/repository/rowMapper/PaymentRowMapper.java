package org.egov.works.mukta.adapter.repository.rowMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.web.models.MuktaPayment;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PaymentRowMapper implements ResultSetExtractor<List<MuktaPayment>> {
    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentRowMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MuktaPayment> extractData(ResultSet rs) throws SQLException, DataAccessException{
        Map<String, MuktaPayment> paymentMap = new HashMap<>();
        while (rs.next()){
            String id = rs.getString("Id");
            MuktaPayment payment = paymentMap.get(id);
            if(payment == null){
                String tenantId = rs.getString("tenantId");
                String paymentId = rs.getString("paymentId");
                String paymentStatus = rs.getString("paymentStatus");
                String paymentType = rs.getString("paymentType");
                Object disburseData = rs.getObject("disburseData");
                Object additionalDetails = getAdditionalDetail(rs,"additionalDetails");
                String createdBy = rs.getString("createdBy");
                Long createdTime = rs.getLong("createdTime");
                String lastModifiedBy = rs.getString("lastModifiedBy");
                Long lastModifiedTime = rs.getLong("lastModifiedTime");

                payment = MuktaPayment.builder().id(id).tenantId(tenantId).paymentId(paymentId).paymentStatus(paymentStatus)
                        .paymentType(paymentType).additionalDetails(additionalDetails).createdBy(createdBy)
                        .disburseData(disburseData)
                        .createdTime(createdTime).lastModifiedBy(lastModifiedBy).lastModifiedTime(lastModifiedTime).build();
                paymentMap.put(id, payment);
            }
        }

        log.debug("converting map to list object ::: " + paymentMap.values());
        return new ArrayList<>(paymentMap.values());
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
