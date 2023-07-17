package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import io.swagger.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Constants;
import org.egov.enrichment.VirtualAllotmentEnrichment;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.HelperUtil;
import org.egov.utils.PIUtils;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.egov.web.models.jit.PISearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FailureDetailsService {
    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HelperUtil helperUtil;
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private VirtualAllotmentEnrichment virtualAllotmentEnrichment;
    @Autowired
    SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    PIRepository piRepository;
    @Autowired
    PIUtils piUtils;

    public void updateFailureDetails(RequestInfo requestInfo) {
        try {
            JITRequest jitFDRequest = getFailedPayload();
            JITResponse fdResponse = ifmsService.sendRequestToIFMS(jitFDRequest);
//            JITResponse fdResponse = virtualAllotmentEnrichment.vaResponse();
            if (fdResponse != null && fdResponse.getErrorMsg() == null) {
                System.out.println(fdResponse);
                for (Object failedPI: fdResponse.getData()) {
                    JsonNode node = objectMapper.valueToTree(failedPI);
                    JsonNode benef = node.get("benfDtls");
                    if (benef != null && benef.isArray() && !benef.isEmpty()) {
                        processPiForFailedTransaction(node, requestInfo);
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception in FailureDetailsService:updateFailureDetails : " + e.getMessage());
        }

    }

    private void processPiForFailedTransaction(JsonNode failedPi, RequestInfo requestInfo) {
        System.out.println(failedPi);
        String piNumber = failedPi.findValue("billNumber").asText();
        PaymentInstruction pi = getCompletedPaymentInstructionByBill(piNumber);
        if (pi != null) {
            Map<String, JsonNode> failedBeneficiariesMapById =  getFailedBeneficiaryMap(failedPi);

            List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo, Collections.singleton(pi.getMuktaReferenceId()), pi.getTenantId());
            if (payments != null && !payments.isEmpty()) {
                Payment payment = payments.get(0);
                System.out.println(payment);
                updatePiAndPaymentForFailedBenef(pi, payment, failedBeneficiariesMapById);
                addReversalTransactionAndUpdatePIPa(pi, payment, requestInfo);
            } else {
                log.info("Payment data not found for paymentNumber : "+ pi.getMuktaReferenceId());
            }
        }

    }

    private JITRequest getFailedPayload() {

        Map<String, String> failedRequestParams = new HashMap<>();
        Long subtractedTimeMillis = System.currentTimeMillis() - (120 * 60L * 60L * 1000L);
        //subtractedTimeMillis = 1683755974760L;
        String finYear = helperUtil.getFormattedTimeFromTimestamp(subtractedTimeMillis, "yyyy");
        String voucherDate = helperUtil.getFormattedTimeFromTimestamp(subtractedTimeMillis, "yyyy-MM-dd");
        failedRequestParams.put("finYear", finYear);
        failedRequestParams.put("voucherDate", voucherDate);
        failedRequestParams.put("extAppName", Constants.JIT_FD_EXT_APP_NAME);
        JITRequest jitRequest = JITRequest.builder()
                .serviceId(JITServiceId.FD)
                .params(failedRequestParams)
                .build();
        return jitRequest;
    }

    private PaymentInstruction getCompletedPaymentInstructionByBill(String billNo){
        PISearchRequest piSearchRequest = PISearchRequest.builder().requestInfo(RequestInfo.builder().build())
                .searchCriteria(PISearchCriteria.builder().jitBillNo(billNo).piStatus(PIStatus.SUCCESSFUL).build()).build();
        List<PaymentInstruction> paymentInstructions = paymentInstructionService.searchPi(piSearchRequest);
        if (!paymentInstructions.isEmpty()) {
            return paymentInstructions.get(0);
        } else {
            return null;
        }
    }

    private Map<String, JsonNode> getFailedBeneficiaryMap(JsonNode failedPi) {
        Map<String, JsonNode> failedPiBenfMap = new HashMap<>();
        JsonNode benfDtlsNode = failedPi.get("benfDtls");
        if (benfDtlsNode.isArray() && !benfDtlsNode.isEmpty()) {
            for (JsonNode benf: benfDtlsNode) {
                System.out.println(benf);
                String benfId = benf.get("benfId").asText();
                failedPiBenfMap.put(benfId, benf);
            }
        }
        return failedPiBenfMap;
    }

    private void updatePiAndPaymentForFailedBenef(PaymentInstruction pi, Payment payment, Map<String, JsonNode> failedBeneficiariesMapById) {
        Map<String, PaymentLineItem> paymentPayableLineItemMap = payment.getBills().stream()
                .map(PaymentBill::getBillDetails)
                .flatMap(Collection::stream)
                .map(PaymentBillDetail::getPayableLineItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(PaymentLineItem::getLineItemId, Function.identity()));

        for (Beneficiary beneficiary: pi.getBeneficiaryDetails()) {
            String benfId = beneficiary.getId();
            if (failedBeneficiariesMapById.get(benfId) != null && !failedBeneficiariesMapById.get(benfId).isEmpty()) {
                JsonNode benfDtl = failedBeneficiariesMapById.get(benfId);
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
                beneficiary.setPaymentStatusMessage(benfDtl.get("failedReason").asText());
                beneficiary.setChallanNumber(benfDtl.get("challanNumber").asText());
                beneficiary.setChallanDate(benfDtl.get("challanDate").asText());
                for (BenfLineItems lineItem: beneficiary.getBenfLineItems()) {
                    if (paymentPayableLineItemMap.containsKey(lineItem.getLineItemId())) {
                        paymentPayableLineItemMap.get(lineItem.getLineItemId()).setStatus(PaymentStatus.FAILED);
                    }
                }
            }
        }
    }

    private void addReversalTransactionAndUpdatePIPa(PaymentInstruction pi, Payment payment, RequestInfo requestInfo) {
        try {
            Long currentTime = System.currentTimeMillis();
            String userId = requestInfo.getUserInfo().getUuid();
            BigDecimal amount = new BigDecimal(0);
            pi.setPiStatus(PIStatus.PARTIAL);
            for (Beneficiary beneficiary: pi.getBeneficiaryDetails()) {
                if (beneficiary.getPaymentStatus().equals(BeneficiaryPaymentStatus.FAILED)) {
                    amount = amount.add(beneficiary.getAmount());
                    beneficiary.getAuditDetails().setLastModifiedBy(userId);
                    beneficiary.getAuditDetails().setLastModifiedTime(currentTime);
                }
            }
            // Generate new transaction details
            SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                    .ids(Collections.singletonList(pi.getTransactionDetails().get(0).getSanctionId()))
                    .build();
            List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
            SanctionDetail sanctionDetail = sanctionDetails.get(0);
            sanctionDetail.getFundsSummary().getAvailableAmount().add(amount);
            sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedTime(currentTime);
            sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedBy(userId);
            AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(currentTime).lastModifiedBy(userId).lastModifiedTime(currentTime).build();
            TransactionDetails transactionDetails = TransactionDetails.builder()
                    .tenantId(pi.getTenantId())
                    .sanctionId(sanctionDetail.getId())
                    .paymentInstId(pi.getId())
                    .transactionAmount(amount)
                    .transactionDate(currentTime)
                    .transactionType(TransactionType.REVERSAL)
                    .additionalDetails(objectMapper.createObjectNode())
                    .auditDetails(auditDetails)
                    .build();
            pi.getTransactionDetails().add(transactionDetails);
            piRepository.update(Collections.singletonList(pi), sanctionDetail.getFundsSummary());
            updatePaymentStatusForPartial(payment, requestInfo);
            // Update PI indexer based on updated PI
            piUtils.updatePiForIndexer(requestInfo, pi);

        } catch (Exception e) {
            log.error("Failed in FailureDetailsService:addReversalTransactionAndUpdatePIPa " + e);
        }
    }

    private void updatePaymentStatusForPartial(Payment payment, RequestInfo requestInfo) {
        System.out.println("Payment "+ payment);
        try {
            boolean updatePaymentStatus = false;
            for (PaymentBill bill: payment.getBills()) {
                boolean updateBillStatus = false;
                for (PaymentBillDetail billDetail: bill.getBillDetails()) {
                    boolean updateBillDetailsStatus = false;
                    for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
                        if (lineItem.getStatus().equals(PaymentStatus.FAILED)) {
                            updateBillDetailsStatus = true;
                        }
                    }
                    if (updateBillDetailsStatus) {
                        billDetail.setStatus(PaymentStatus.PARTIAL);
                        updateBillStatus = true;
                    }
                }
                if (updateBillStatus) {
                    bill.setStatus(PaymentStatus.PARTIAL);
                    updatePaymentStatus = true;
                }
            }
            if (updatePaymentStatus) {
                payment.setStatus(PaymentStatus.PARTIAL);
                PaymentRequest paymentRequest = PaymentRequest.builder().requestInfo(requestInfo).payment(payment).build();
                billUtils.updatePaymentsData(paymentRequest);
            }
        }catch (Exception e) {
            log.error("Exception while updating the payment status FailureDetailsService:updatePaymentStatusForPartial : " + e);
        }
    }

}
