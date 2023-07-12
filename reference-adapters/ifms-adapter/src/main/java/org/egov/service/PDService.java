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

            //JITResponse jitResponse = ifmsService.sendRequestToIFMS(JITRequest.builder()
            //        .serviceId(JITServiceId.PD).params(pdRequest).build());

            Map<String, Object> testMap = new HashMap<>();
            testMap.put("billNumber","MUKTA/2022-23/05/25/105");
            testMap.put("voucherNo","22171");
            testMap.put("voucherDate","2023-06-01");
            testMap.put("billRefNo","202311171279");
            testMap.put("paymentStatus","Paid by RBI eKuber,please check with your bank");
            testMap.put("tokenNumber","4");
            testMap.put("tokenDate","2023-05-29");
            Map<String, String > obj1 = new HashMap<>();
            obj1.put("benfAcctNo","389201502452");
            obj1.put("benfBankIfscCode","ICIC0003892");
            obj1.put("utrNo","RBI504");
            obj1.put("utrDate","2023-06-01");
            obj1.put("endToEndId","EP112202320000003231520002");
            obj1.put("benfId","1");
            Map<String, String > obj2 = new HashMap<>();
            obj2.put("benfAcctNo","389201502453");
            obj2.put("benfBankIfscCode","ICIC0003892");
            obj2.put("utrNo","RBI504");
            obj2.put("utrDate","2023-06-01");
            obj2.put("endToEndId","EP112202320000002231520002");
            obj2.put("benfId","0f1e7c2b-2e1e-41e9-9008-091049030269");
            Object[] objects = new Object[2];
            objects[0] = obj1;
            objects[1] = obj2;
            testMap.put("benfDtls",objects);
            JITResponse jitResponse = JITResponse.builder().data(Collections.singletonList(testMap)).build();

            if (jitResponse.getErrorMsg() != null) {
                //TODO
            }
            if (jitResponse.getData().isEmpty()) {
                continue;
            }
            for (Object data : jitResponse.getData()) {
                Map<String, Object> dataObjMap = objectMapper.convertValue(data, Map.class);
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

                            }
                        }
                    }
                }
                paymentInstruction.setPiStatus(PIStatus.SUCCESSFUL);
                piRepository.update(Collections.singletonList(paymentInstruction),null);
                //piRepository.updateBeneficiaryByPD(Collections.singletonList(paymentInstruction));
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