package org.egov.validators;

import java.util.List;

import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.service.IfmsService;
import org.egov.service.PaymentInstructionService;
import org.egov.tracer.model.CustomException;
import org.egov.utils.BillUtils;
import org.egov.web.models.bill.Bill;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.enums.ReferenceStatus;
import org.egov.web.models.jit.Beneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;


/**
 * Class contains all validations for a payment instruction
 *
 */
@Component
@Slf4j
public class PaymentInstructionValidator {

    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private PaymentInstructionEnrichment paymentInstructionEnrichment;
    @Autowired
    private SanctionDetailsRepository sanctionDetailsRepository;

    public void validatePaymentInstructionRequest(PaymentRequest paymentRequest) {

        validateDdoSsuidHoaFunds(paymentRequest);
        validateBeneficiary(paymentRequest);

    }
    private void validateDdoSsuidHoaFunds(PaymentRequest paymentRequest) {
        JSONArray ssuDetailList = ifmsService.getSSUDetails(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId());
        JSONArray hoaList = ifmsService.getHeadOfAccounts(paymentRequest.getRequestInfo());
        JsonNode hoaNode = null;
        JsonNode ssuNode = null;
        Boolean hasFunds = true;
        if (ssuDetailList != null && !ssuDetailList.isEmpty() && hoaList != null && !hoaList.isEmpty()) {
            for (Object ssuDetails : ssuDetailList) {
                for (Object hoa : hoaList) {
                    hoaNode = objectMapper.valueToTree(hoa);
                    ssuNode = objectMapper.valueToTree(ssuDetails);
                    String hoaCode = hoaNode.get("code").asText();
                    String ddoCode = ssuNode.get("ddoCode").asText();
                    String ssuId = ssuNode.get("ssuId").asText();
                    if (ddoCode == null || ddoCode.isEmpty() || ssuId == null || ssuId.isEmpty() || hoaCode == null || hoaCode.isEmpty()) {
                        log.error("Unable to create payment because DDO code and SSU ID is missing");
                        billUtils.updatePaymentStatus(paymentRequest, PaymentStatus.FAILED, ReferenceStatus.PAYMENT_FAILED);
                        throw new CustomException("EG_IFMS_DDO_SSU_HOA_DETAILS_EMPTY", "DDO and SSUID or HOA configuration is missing");
                    }
                    // removing this as in case of insufficient funds we have to create a PI and it is already handled.
//                    else {
//                        SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
//                                .tenantId(paymentRequest.getPayment().getTenantId())
//                                .ddoCode(ddoCode)
//                                .hoaCode(hoaCode)
//                                .build();
//                        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
//                        for (SanctionDetail sanctionDetail: sanctionDetails) {
//                            if (sanctionDetail.getFundsSummary().getAvailableAmount().compareTo(paymentRequest.getPayment().getNetPayableAmount()) < 0) {
//                                billUtils.updatePaymentForStatus(paymentRequest, PaymentStatus.FAILED, ReferenceStatus.PAYMENT_FAILED);
//                                throw new CustomException("EG_IFMS_INSUFFICIENT_FUNDS","Insufficient fund");
//                            }
//                        }
//                    }
                }
            }
        }
        else {
            billUtils.updatePaymentStatus(paymentRequest, PaymentStatus.FAILED,ReferenceStatus.PAYMENT_FAILED);
            throw new CustomException("EG_IFMS_DDO_SSU_HOA_DETAILS_EMPTY", "DDO and SSUID or HOA configuration is missing");
        }
    }
    private void validateBeneficiary (PaymentRequest paymentRequest) {
        List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);
        billList = paymentInstructionService.filterBillsPayableLineItemByPayments(paymentRequest.getPayment(), billList);

        List<Beneficiary> beneficiaryList = paymentInstructionEnrichment.getBeneficiariesFromBills(billList, paymentRequest);
        if (beneficiaryList == null || beneficiaryList.isEmpty()) {
            billUtils.updatePaymentStatus(paymentRequest, PaymentStatus.FAILED, ReferenceStatus.PAYMENT_FAILED);
            throw new CustomException("EG_IFMS_BENF_DETAILS_EMPTY", "Beneficiary detail is missing");
        }
    }


}
