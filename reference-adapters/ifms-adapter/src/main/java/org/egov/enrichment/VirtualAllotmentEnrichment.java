package org.egov.enrichment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.utils.HelperUtil;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.jit.*;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.egov.config.Constants.*;

@Service
@Slf4j
public class VirtualAllotmentEnrichment {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HelperUtil util;

    public JITRequest constructVARequest(JsonNode hoaNode, JsonNode ssuNode, Long lastExecuted) {
        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String granteeCode = ssuNode.get("granteeAgCode").asText();
        String fromDate = util.getFormattedTimeFromTimestamp(lastExecuted, VA_REQUEST_TIME_FORMAT);

        VARequest vaRequest = VARequest.builder()
                .hoa(hoaCode)
                .ddoCode(ddoCode)
                .granteCode(granteeCode)
                .fromDate(fromDate)
                .build();

        JITRequest jitRequest = JITRequest.builder()
                .serviceId(JITServiceId.VA)
                .params(vaRequest)
                .build();
        return jitRequest;
    }


    public SanctionDetailsSearchCriteria getSanctionDetailsSearchCriteriaForVA(String tenantId, JsonNode hoaNode, JsonNode ssuNode) {
        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                .tenantId(tenantId)
                .hoaCode(hoaCode)
                .ddoCode(ddoCode)
                .build();
        return  searchCriteria;
    }

    public List<SanctionDetail> getCreateAndUpdateSanctionDetails(List<SanctionDetail> existingSanctionDetails, List<Allotment> allotments) {
        List<SanctionDetail> sanctionDetails = new ArrayList<>();
        if (existingSanctionDetails != null && !existingSanctionDetails.isEmpty()) {
            sanctionDetails.addAll(existingSanctionDetails);
        }
        Map<String, List<Allotment>> mstAllotmentIdMap = new HashMap<>();
        List<Allotment> initialAllotments = new ArrayList<>();
        for (Allotment allotment: allotments) {
            if (allotment.getAllotmentTxnType().equalsIgnoreCase(VA_TRANSACTION_TYPE_INITIAL_ALLOTMENT)) {
                initialAllotments.add(allotment);
            } else {
                if (mstAllotmentIdMap.get(allotment.getMstAllotmentDistId()) != null) {
                    mstAllotmentIdMap.get(allotment.getMstAllotmentDistId()).add(allotment);
                } else {
                    List<Allotment> list = new ArrayList<>();
                    list.add(allotment);
                    mstAllotmentIdMap.put(allotment.getMstAllotmentDistId(), list);
                }
            }
        }

        if (!initialAllotments.isEmpty()) {
            List<SanctionDetail> newSanctionDetails = getNewSanctionDetails(initialAllotments);
            sanctionDetails.addAll(newSanctionDetails);
        }

        for (SanctionDetail sanctionDetail: sanctionDetails) {
            if (mstAllotmentIdMap.get(sanctionDetail.getMasterAllotmentId()) != null) {
                List<Allotment> allotmentList = mstAllotmentIdMap.get(sanctionDetail.getMasterAllotmentId());
                updateSanctionDetailsFromAllotment(sanctionDetail, allotmentList);
            }
        }
        return sanctionDetails;

    }

    public void enrichAndUpdateSanctions (List<SanctionDetail> sanctionDetails, String tenantId, JsonNode hoaNode, JsonNode ssuNode, RequestInfo requestInfo) {
        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String userId = requestInfo.getUserInfo().getUuid();
        Long time = System.currentTimeMillis();

        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();
        for (SanctionDetail sanctionDetail:  sanctionDetails) {
            if (sanctionDetail.getAuditDetails() == null) {
                sanctionDetail.setHoaCode(hoaCode);
                sanctionDetail.setDdoCode(ddoCode);
                sanctionDetail.setTenantId(tenantId);
                sanctionDetail.setAuditDetails(auditDetails);
                sanctionDetail.getFundsSummary().setTenantId(tenantId);
                sanctionDetail.getFundsSummary().setAuditDetails(auditDetails);
            } else {
                sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedBy(userId);
                sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedTime(time);
            }
        }
    }

    private void updateSanctionDetailsFromAllotment(SanctionDetail sanctionDetail, List<Allotment> allotmentList) {
        if (allotmentList != null && !allotmentList.isEmpty()) {
            for (Allotment allotment: allotmentList) {
                BigDecimal allotmentAmount = new BigDecimal(allotment.getAllotmentAmount());
                if (allotment.getAllotmentTxnType().equalsIgnoreCase(VA_TRANSACTION_TYPE_ADDITIONAL_ALLOTMENT)) {
                    sanctionDetail.getFundsSummary().setAllottedAmount(sanctionDetail.getFundsSummary().getAllottedAmount().add(allotmentAmount));
                    sanctionDetail.getFundsSummary().setAvailableAmount(sanctionDetail.getFundsSummary().getAvailableAmount().add(allotmentAmount));
                } else if (allotment.getAllotmentTxnType().equalsIgnoreCase(VA_TRANSACTION_TYPE_WITHDRAWAL)) {
                    sanctionDetail.getFundsSummary().setAllottedAmount(sanctionDetail.getFundsSummary().getAllottedAmount().subtract(allotmentAmount));
                    sanctionDetail.getFundsSummary().setAvailableAmount(sanctionDetail.getFundsSummary().getAvailableAmount().subtract(allotmentAmount));
                }
            }
        }

    }

    private List<SanctionDetail> getNewSanctionDetails(List<Allotment> allotments) {
        List<SanctionDetail> sanctionDetailList = new ArrayList<>();
        for (Allotment allotment: allotments) {
            BigDecimal availableAmount = new BigDecimal(allotment.getAvailableBalance());
            BigDecimal allotmentAmount = new BigDecimal(allotment.getAllotmentAmount());
            BigDecimal sanctionedAmount = availableAmount.add(allotmentAmount);
            JsonNode emptyObject = objectMapper.createObjectNode();

            SanctionDetail sanctionDetail = SanctionDetail.builder()
                    .id(UUID.randomUUID().toString())
                    .masterAllotmentId(allotment.getMstAllotmentDistId())
                    .financialYear(util.getFinancialYear(allotment.getAllotmentDate()))
                    .sanctionedAmount(sanctionedAmount)
                    .additionalDetails(emptyObject)
                    .build();
            FundsSummary fundsSummary = FundsSummary.builder()
                    .id(UUID.randomUUID().toString())
                    .sanctionId(sanctionDetail.getId())
                    .allottedAmount(allotmentAmount)
                    .availableAmount(allotmentAmount)
                    .additionalDetails(emptyObject)
                    .build();
            sanctionDetail.setFundsSummary(fundsSummary);
            sanctionDetailList.add(sanctionDetail);
        }
        return sanctionDetailList;
    }

    public Map<String, List<SanctionDetail>> getCreateUpdateSanctionMap(List<SanctionDetail> oldSanctions, List<SanctionDetail> updatedSanctions) {
        Map<String, List<SanctionDetail>> map = new HashMap<>();
        Map<String, SanctionDetail> oldSanctionDetailMap = new HashMap<>();
        List<SanctionDetail> createList = new ArrayList<>();
        List<SanctionDetail> updateList = new ArrayList<>();

        for (SanctionDetail sanctionDetail: oldSanctions) {
            oldSanctionDetailMap.put(sanctionDetail.getMasterAllotmentId(), sanctionDetail);
        }
        for (SanctionDetail sanctionDetail: updatedSanctions) {
            if (oldSanctionDetailMap.get(sanctionDetail.getMasterAllotmentId()) == null) {
                createList.add(sanctionDetail);
            } else {
                updateList.add(sanctionDetail);
            }
        }
        map.put("create", createList);
        map.put("update", updateList);
        return map;
    }

    public List<FundsSummary> getFundsSummariesFromSanctions(List<SanctionDetail> sanctionDetails) {
        List<FundsSummary> fundsSummaries = new ArrayList<>();
        if (sanctionDetails != null && !sanctionDetails.isEmpty()) {
            for (SanctionDetail sanctionDetail: sanctionDetails) {
                fundsSummaries.add(sanctionDetail.getFundsSummary());
            }
        }
        return fundsSummaries;
    }

    public List<Allotment> getAllotmentsForCreate(List<SanctionDetail> sanctionDetails, List<Allotment> allotments, String tenantId, RequestInfo requestInfo) {
        List<Allotment> createAllotments = new ArrayList<>();
        Map<String, SanctionDetail> sanctionDetailMap = new HashMap<>();
        for (SanctionDetail sanctionDetail :sanctionDetails) {
            sanctionDetailMap.put(sanctionDetail.getMasterAllotmentId(), sanctionDetail);
        }

        String userId = requestInfo.getUserInfo().getUuid();
        Long time = System.currentTimeMillis();
        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();
        JsonNode emptyObject = objectMapper.createObjectNode();

        for (Allotment allotment: allotments) {
            if (sanctionDetailMap.containsKey(allotment.getMstAllotmentDistId())) {
                SanctionDetail sanctionDetail = sanctionDetailMap.get(allotment.getMstAllotmentDistId());
                Allotment crAllotment = Allotment.builder()
                        .id(UUID.randomUUID().toString())
                        .tenantId(tenantId)
                        .sanctionId(sanctionDetail.getId())
                        .allotmentSerialNo(Integer.parseInt(allotment.getAllotmentTxnSlNo()))
                        .ssuAllotmentId(allotment.getSsuAllotmentId())
                        .decimalAllottedAmount(new BigDecimal(allotment.getAllotmentAmount()))
                        .allotmentTxnType(allotment.getAllotmentTxnType())
                        .decimalSanctionBalance(new BigDecimal(allotment.getAvailableBalance()))
                        .allotmentDateTimeStamp(util.getEpochTimeSeconds(allotment.getAllotmentDate()))
                        .additionalDetails(emptyObject)
                        .auditDetails(auditDetails)
                        .build();
                createAllotments.add(crAllotment);
            }
        }
        return createAllotments;
    }

    public ExecutedVALog getExecutedVALogForCreate (String tenantId, JsonNode hoaNode, JsonNode ssuNode, RequestInfo requestInfo) {
        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String granteeCode = ssuNode.get("granteeAgCode").asText();
        String userId = requestInfo.getUserInfo().getUuid();
        Long time = System.currentTimeMillis();
        JsonNode emptyObject = objectMapper.createObjectNode();

        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();
        ExecutedVALog executedVALog = ExecutedVALog.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .hoaCode(hoaCode)
                .ddoCode(ddoCode)
                .granteeCode(granteeCode)
                .lastExecuted(time)
                .additionalDetails(emptyObject)
                .auditDetails(auditDetails)
                .build();
        return executedVALog;
    }

    public ExecutedVALog enrichExecutedVaLogForUpdate (ExecutedVALog executedVALog, RequestInfo requestInfo) {
        String userId = requestInfo.getUserInfo().getUuid();
        Long time = System.currentTimeMillis();
        executedVALog.setLastExecuted(time);
        executedVALog.getAuditDetails().setLastModifiedBy(userId);
        executedVALog.getAuditDetails().setLastModifiedTime(time);
        return executedVALog;
    }

}
