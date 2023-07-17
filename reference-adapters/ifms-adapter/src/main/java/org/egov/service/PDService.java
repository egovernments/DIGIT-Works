package org.egov.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.PIRepository;
import org.egov.utils.BillUtils;
import org.egov.utils.PIUtils;
import org.egov.web.models.bill.Payment;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.enums.BeneficiaryPaymentStatus;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class PDService {

    @Autowired
    private PaymentInstructionService paymentInstructionService;

    @Autowired
    private IfmsService ifmsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PIRepository piRepository;

    @Autowired
    private BillUtils billUtils;

    @Autowired
    private PIUtils piUtils;

    public void updatePDStatus(RequestInfo requestInfo) {

        List<PaymentInstruction> inProcessPaymentInstructions =  paymentInstructionService.searchPi(PISearchRequest.builder()
                .requestInfo(RequestInfo.builder().build())
                .searchCriteria(PISearchCriteria.builder().piStatus(PIStatus.IN_PROCESS).build()).build());

        for (PaymentInstruction paymentInstruction : inProcessPaymentInstructions) {

            PDRequest pdRequest = PDRequest.builder().finYear(paymentInstruction.getPaDetails().get(0).getPaFinYear())
                    .extAppName("MUKTA")
                    .billRefNo(paymentInstruction.getPaDetails().get(0).getPaBillRefNumber())
                    .tokenNumber(paymentInstruction.getPaDetails().get(0).getPaTokenNumber())
                    .tokenDate(paymentInstruction.getPaDetails().get(0).getPaTokenDate())
                    .build();

            JITResponse jitResponse = ifmsService.sendRequestToIFMS(JITRequest.builder()
                    .serviceId(JITServiceId.PD).params(pdRequest).build());


            if (jitResponse.getErrorMsg() != null || jitResponse.getData().isEmpty()) {
                continue;
            }
            for (Object data : jitResponse.getData()) {
                Map<String, Object> dataObjMap = objectMapper.convertValue(data, Map.class);
                String billNumber = dataObjMap.get("billNumber").toString();
                if (billNumber == null)
                    continue;
                String voucherNo = dataObjMap.get("voucherNo").toString();
                String voucherDate = dataObjMap.get("voucherDate").toString();
                String paymentStatusMessage = dataObjMap.get("paymentStatus").toString();
                JsonNode benfDtlsNode = objectMapper.valueToTree(dataObjMap.get("benfDtls"));



                for (Beneficiary beneficiaryFromDB : paymentInstruction.getBeneficiaryDetails()) {
                    if (benfDtlsNode.isArray() && !benfDtlsNode.isEmpty()) {
                        for (JsonNode benf : benfDtlsNode) {
                            if (beneficiaryFromDB.getId().equalsIgnoreCase(benf.get("benfId").asText())) {
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

        }

    }

}