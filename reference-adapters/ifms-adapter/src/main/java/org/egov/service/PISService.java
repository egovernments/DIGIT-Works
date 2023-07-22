package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.HelperUtil;
import org.egov.utils.MdmsUtils;
import org.egov.utils.PIUtils;
import org.egov.web.models.bill.Payment;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.egov.config.Constants.JIT_BILL_DATE_FORMAT;


@Service
@Slf4j
public class PISService {

    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private PIRepository piRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private HelperUtil helperUtil;
    @Autowired
    private PIUtils piUtils;
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    private ObjectMapper objectMapper;
    public void updatePIStatus(RequestInfo requestInfo){
        List<PaymentInstruction> paymentInstructions = getInitiatedPaymentInstructions();

        for(PaymentInstruction paymentInstruction : paymentInstructions){
            JSONArray ssuIaDetails = ifmsService.getSSUDetails(RequestInfo.builder().build(), paymentInstruction.getTenantId());
            Map<String,String> ssuIaDetailsMap = (Map<String, String>) ssuIaDetails.get(0);
            String ssuId = ssuIaDetailsMap.get("ssuId");
            PISRequest pisRequest = PISRequest.builder()
                    .jitBillDate(helperUtil.getFormattedTimeFromTimestamp(paymentInstruction.getAuditDetails().getCreatedTime(), JIT_BILL_DATE_FORMAT))
                    .jitBillNo(paymentInstruction.getJitBillNo())
                    .ssuIaId(ssuId)
                    .build();
            JITRequest jitRequest = JITRequest.builder().serviceId(JITServiceId.PIS).params(pisRequest).build();

            JITResponse pisResponse = ifmsService.sendRequestToIFMS(jitRequest);
            if (pisResponse.getErrorMsg() != null
                    && pisResponse.getErrorMsg().contains(paymentInstruction.getMuktaReferenceId())
                    && pisResponse.getErrorMsg().contains("rejected")) {
                updateStatusToFailed(requestInfo, paymentInstruction);
                updateFundsSummary(requestInfo, paymentInstruction);
                // Update PI indexer based on updated PI
                piUtils.updatePiForIndexer(requestInfo, paymentInstruction);
                continue;
            }
            if(CollectionUtils.isEmpty(pisResponse.getData())){
                continue;
            }
            for(Object data : pisResponse.getData()){
                Map<String, String> dataMap = mapper.convertValue(data, Map.class);
                String piApprovedId = dataMap.get("pmtInstId");
                String piApprovalDate = dataMap.get("payInstDate");

                paymentInstruction.setPiApprovedId(piApprovedId);
                paymentInstruction.setPiApprovalDate(piApprovalDate);
                paymentInstruction.setPiStatus(PIStatus.APPROVED);
                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());

                piRepository.update(Collections.singletonList(paymentInstruction),null);
                piUtils.updatePiForIndexer(requestInfo, paymentInstruction);
            }

        }
    }

    public List<PaymentInstruction> getInitiatedPaymentInstructions(){
        PISearchRequest piSearchRequest = PISearchRequest.builder().requestInfo(RequestInfo.builder().build())
                .searchCriteria(PISearchCriteria.builder().piStatus(PIStatus.INITIATED).build()).build();
        List<PaymentInstruction> paymentInstructions = paymentInstructionService.searchPi(piSearchRequest);
        return paymentInstructions;
    }
    private void updateStatusToFailed(RequestInfo requestInfo, PaymentInstruction paymentInstruction) {
        for (Beneficiary beneficiary : paymentInstruction.getBeneficiaryDetails()) {
            beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
        }
        paymentInstruction.setPiStatus(PIStatus.FAILED);

        List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo,
                Collections.singleton(paymentInstruction.getMuktaReferenceId()),
                paymentInstruction.getTenantId());
        for (Payment payment : payments) {
            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .requestInfo(requestInfo).payment(payment).build();

            billUtils.updatePaymentForStatus(paymentRequest, PaymentStatus.FAILED);
        }
    }
    private void updateFundsSummary(RequestInfo requestInfo, PaymentInstruction paymentInstruction) {
        SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                .ids(Collections.singletonList(paymentInstruction.getTransactionDetails().get(0).getSanctionId()))
                .build();
        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
        SanctionDetail sanctionDetail = sanctionDetails.get(0);
        BigDecimal totalAmount = new BigDecimal(0);
        List<Beneficiary> beneficiaries = paymentInstruction.getBeneficiaryDetails();
        if (beneficiaries != null && !beneficiaries.isEmpty()) {
            for (Beneficiary piBeneficiary: beneficiaries) {
                totalAmount = totalAmount.add(piBeneficiary.getAmount());
            }
        }
        sanctionDetail.getFundsSummary().getAvailableAmount().add(totalAmount);
        sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
        sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
        AuditDetails auditDetails = AuditDetails.builder()
                .createdBy(requestInfo.getUserInfo().getUuid()).createdTime(System.currentTimeMillis())
                .lastModifiedBy(requestInfo.getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis())
                .build();
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .tenantId(paymentInstruction.getTenantId())
                .sanctionId(sanctionDetail.getId())
                .paymentInstId(paymentInstruction.getId())
                .transactionAmount(totalAmount)
                .transactionDate(System.currentTimeMillis())
                .transactionType(TransactionType.REVERSAL)
                .additionalDetails(objectMapper.createObjectNode())
                .auditDetails(auditDetails)
                .build();
        paymentInstruction.getTransactionDetails().add(transactionDetails);
        piRepository.update(Collections.singletonList(paymentInstruction), sanctionDetail.getFundsSummary());
    }

}
