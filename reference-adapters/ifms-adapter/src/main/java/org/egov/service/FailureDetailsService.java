package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import io.swagger.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Constants;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    private PIRepository piRepository;
    @Autowired
    private PIUtils piUtils;

    public void updateFailureDetails(RequestInfo requestInfo) {
        try {
            JITRequest jitFDRequest = getFailedPayload();
            JITResponse fdResponse = ifmsService.sendRequestToIFMS(jitFDRequest);
            if (fdResponse != null && fdResponse.getErrorMsg() == null) {
                for (Object failedPI: fdResponse.getData()) {
                    JsonNode node = objectMapper.valueToTree(failedPI);
                    JsonNode benef = node.get("benfDtls");
                    if (benef != null && benef.isArray() && !benef.isEmpty()) {
                        processPiForFailedTransaction(node, requestInfo, null);
                    }
                }
            }
        } catch (Exception e) {
            log.info("Exception in FailureDetailsService:updateFailureDetails : " + e.getMessage());
        }

    }

    private void processPiForFailedTransaction(JsonNode failedPiResponse, RequestInfo requestInfo, PaymentInstruction pi) {
        String piNumber = null;
        if (failedPiResponse.findValue("billNumber") != null) {
            piNumber = failedPiResponse.findValue("billNumber").asText();
        } else if (failedPiResponse.findValue("jitCorBillNo") != null) {
            piNumber = failedPiResponse.findValue("jitCorBillNo").asText();
        }

        if (pi == null) {
            pi = getCompletedPaymentInstructionByBill(piNumber);
        }

        if (pi != null) {
            Map<String, JsonNode> failedBeneficiariesMapById =  getFailedBeneficiaryMap(failedPiResponse);

            List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo, Collections.singleton(pi.getMuktaReferenceId()), pi.getTenantId());
            if (payments != null && !payments.isEmpty()) {
                Payment payment = payments.get(0);
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
            String benfNumber = beneficiary.getBeneficiaryNumber();
            if (failedBeneficiariesMapById.get(benfNumber) != null && !failedBeneficiariesMapById.get(benfNumber).isEmpty()) {
                JsonNode benfDtl = failedBeneficiariesMapById.get(benfNumber);
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
            sanctionDetail.getFundsSummary().setAvailableAmount(sanctionDetail.getFundsSummary().getAvailableAmount().add(amount));
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


    public void updateFTPSStatus(RequestInfo requestInfo) {

        PISearchCriteria revisedPiSearchCriteria = PISearchCriteria.builder().piType(PIType.REVISED).piStatus(PIStatus.INITIATED).build();
        List<PaymentInstruction> revisedPI =  paymentInstructionService.searchPi(PISearchRequest.builder().requestInfo(requestInfo).searchCriteria(revisedPiSearchCriteria).build());

        for (PaymentInstruction pi : revisedPI) {

            PISearchCriteria originalPiSearchCriteria = PISearchCriteria.builder().jitBillNo(pi.getJitBillNo()).build();
            PaymentInstruction originalPI =  paymentInstructionService.searchPi(PISearchRequest.builder().requestInfo(requestInfo).searchCriteria(originalPiSearchCriteria).build()).get(0);


            FTPSRequest ftpsRequest = FTPSRequest.builder()
                    .jitCorBillNo(pi.getJitBillNo())
                    .jitCorBillDate(helperUtil.getFormattedTimeFromTimestamp(pi.getAuditDetails().getCreatedTime(), Constants.VA_REQUEST_TIME_FORMAT))
                    .jitCorBillDeptCode(Constants.JIT_FD_EXT_APP_NAME)
                    .jitOrgBillNo(originalPI.getJitBillNo())
                    .jitOrgBillRefNo(originalPI.getPaDetails().get(0).getPaBillRefNumber())
                    .jitOrgBillDate(helperUtil.getFormattedTimeFromTimestamp(originalPI.getAuditDetails().getCreatedTime(), Constants.VA_REQUEST_TIME_FORMAT))
                    .build();

            JITResponse jitResponse = ifmsService.sendRequestToIFMS(JITRequest.builder()
                    .serviceId(JITServiceId.FTPS).params(ftpsRequest).build());


            if (jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
                continue;
            }
            for (Object data : jitResponse.getData()) {
                processFTPSResponse(data, pi, requestInfo);
            }
        }
    }

    private void processFTPSResponse(Object ftpsResponse, PaymentInstruction paymentInstruction, RequestInfo requestInfo) {
        try {
            Map<String, Object> dataObjMap = objectMapper.convertValue(ftpsResponse, Map.class);
            if (dataObjMap.get("jitCorBillNo") != null) {

                String voucherNo = dataObjMap.get("voucherNo").toString();
                String voucherDate = dataObjMap.get("voucherDate").toString();
                String paymentStatusMessage = dataObjMap.get("paymentStatus").toString();
                String billRefNo = dataObjMap.get("billRefNo").toString();
                String tokenNumber = dataObjMap.get("tokenNumber").toString();
                String tokenDate = dataObjMap.get("tokenDate").toString();
                JsonNode benfDtlsNode = objectMapper.valueToTree(dataObjMap.get("benfDtls"));

                // Set pa  details
                paymentInstruction.getPaDetails().get(0).setPaBillRefNumber(billRefNo);
                paymentInstruction.getPaDetails().get(0).setPaTokenNumber(tokenNumber);
                paymentInstruction.getPaDetails().get(0).setPaTokenDate(tokenDate);
                paymentInstruction.getPaDetails().get(0).getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getPaDetails().get(0).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());

                // Set beneficiaries  details
                for (Beneficiary beneficiaryFromDB : paymentInstruction.getBeneficiaryDetails()) {
                    if (benfDtlsNode.isArray() && !benfDtlsNode.isEmpty()) {
                        for (JsonNode benf : benfDtlsNode) {
                            if (beneficiaryFromDB.getBeneficiaryNumber().equalsIgnoreCase(benf.get("benfId").asText())) {
                                String dateFormat = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                                long voucherNumberTimestamp = 1;
                                try {
                                    Date date = sdf.parse(voucherDate);
                                    voucherNumberTimestamp = date.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                beneficiaryFromDB.setVoucherNumber(voucherNo);
                                beneficiaryFromDB.setVoucherDate(voucherNumberTimestamp);
                                beneficiaryFromDB.setPaymentStatus(BeneficiaryPaymentStatus.SUCCESS);
                                beneficiaryFromDB.setPaymentStatusMessage(paymentStatusMessage);
                                beneficiaryFromDB.setBenfAcctNo(benf.get("benfAcctNo").asText());
                                beneficiaryFromDB.setBenfBankIfscCode(benf.get("benfBankIfscCode").asText());
                                beneficiaryFromDB.setUtrNo(benf.get("utrNo").asText());
                                beneficiaryFromDB.setUtrDate(benf.get("utrDate").asText());
                                beneficiaryFromDB.setEndToEndId(benf.get("endToEndId").asText());
                                beneficiaryFromDB.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                                beneficiaryFromDB.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                            }
                        }
                    }
                }
                paymentInstruction.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                paymentInstruction.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                paymentInstruction.setPiStatus(PIStatus.SUCCESSFUL);
                piRepository.update(Collections.singletonList(paymentInstruction),null);
                piUtils.updatePiForIndexer(requestInfo, paymentInstruction);
                List<Payment> payments = billUtils.fetchPaymentDetails(requestInfo,
                        Collections.singleton(paymentInstruction.getMuktaReferenceId()),
                        paymentInstruction.getTenantId());

                for (Payment payment : payments) {
                    PaymentRequest paymentRequest = PaymentRequest.builder()
                            .requestInfo(requestInfo).payment(payment).build();

                    billUtils.updatePaymentForStatus(paymentRequest, PaymentStatus.SUCCESSFUL);
                }
            }
        } catch (Exception e) {
            log.info("Exception in FailedPaymentUpdateService:processFTPSResponse " + e);
        }

    }

    public void updateFTFPSStatus(RequestInfo requestInfo) {

        PISearchCriteria revisedPiSearchCriteria = PISearchCriteria.builder().piType(PIType.REVISED).piStatus(PIStatus.SUCCESSFUL).build();
        List<PaymentInstruction> revisedPIs =  paymentInstructionService.searchPi(PISearchRequest.builder().requestInfo(requestInfo).searchCriteria(revisedPiSearchCriteria).build());

        for (PaymentInstruction pi : revisedPIs) {

            FTFPSRequest ftfpsRequest = FTFPSRequest.builder()
                    .jitCorBillNo(pi.getJitBillNo())
                    .jitCorBillDate(helperUtil.getFormattedTimeFromTimestamp(pi.getAuditDetails().getCreatedTime(), Constants.VA_REQUEST_TIME_FORMAT))
                    .billRefNo(pi.getPaDetails().get(0).getPaBillRefNumber())
                    .tokenNumber(pi.getPaDetails().get(0).getPaTokenNumber())
                    .tokenDate(pi.getPaDetails().get(0).getPaTokenDate())
                    .build();

            JITResponse jitResponse = ifmsService.sendRequestToIFMS(JITRequest.builder()
                    .serviceId(JITServiceId.FTFPS).params(ftfpsRequest).build());
//            JITResponse jitResponse = ifmsService.loadCustomResponse();

            if (jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
                continue;
            }

            for (Object failedPI: jitResponse.getData()) {
                JsonNode node = objectMapper.valueToTree(failedPI);
                JsonNode benef = node.get("benfDtls");
                if (benef != null && benef.isArray() && !benef.isEmpty()) {
                    processPiForFailedTransaction(node, requestInfo, pi);
                }
            }
        }
    }

}
