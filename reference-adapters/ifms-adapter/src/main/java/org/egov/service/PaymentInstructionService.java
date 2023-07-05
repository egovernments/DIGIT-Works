package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.models.individual.Individual;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.repository.PIRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.BankAccountUtils;
import org.egov.utils.BillUtils;
import org.egov.utils.IndividualUtils;
import org.egov.utils.OrganisationUtils;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.BeneficiaryPaymentStatus;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.enums.Status;
import org.egov.web.models.jit.*;
import org.egov.web.models.jit.PISearchCriteria;
import org.egov.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentInstructionService {

    @Autowired
    BillUtils billUtils;

    @Autowired
    BankAccountUtils bankAccountUtils;

    @Autowired
    IndividualUtils individualUtils;

    @Autowired
    OrganisationUtils organisationUtils;
    @Autowired
    PaymentInstructionEnrichment piEnrichment;

    @Autowired
    IfmsService ifmsService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PIRepository piRepository;

    public PaymentInstruction processPaymentRequestForPI(PaymentRequest paymentRequest) {
        PaymentInstruction piRequest = null;
        PaymentStatus paymentStatus = null;
        try {
            // Get the beneficiaries
            List<Beneficiary> beneficiaries = getBeneficiariesFromPayment(paymentRequest);
            BigDecimal totalAmount = new BigDecimal(0);
            if (beneficiaries != null && !beneficiaries.isEmpty()) {
                for (Beneficiary piBeneficiary: beneficiaries) {
                    totalAmount = totalAmount.add(piBeneficiary.getAmount());
                }
            }
            SanctionDetail selectedSanction = null;
            Boolean hasFunds = true;
            Map<String, Object> hoaSsuMap = piEnrichment.getSanctionSsuAndHOA(paymentRequest, totalAmount);
            selectedSanction = (SanctionDetail) hoaSsuMap.get("sanction");
            hasFunds = Boolean.parseBoolean(hoaSsuMap.get("hasFunds").toString());

            if (hasFunds) {
                // Get enriched PI request to store on DB
                piRequest = piEnrichment.getEnrichedPaymentRequest(paymentRequest, beneficiaries, hoaSsuMap);

                JITRequest jitPiRequest = piEnrichment.getJitPaymentInstructionRequestForIFMS(piRequest);
                try {
                    JITResponse jitResponse = ifmsService.sendRequestToIFMS(jitPiRequest);
                    if (jitResponse.getErrorMsg() == null && !jitResponse.getData().isEmpty()) {
                        paymentStatus = PaymentStatus.INITIATED;
                        Object piResponseNode = jitResponse.getData().get(0);
                        JsonNode node = objectMapper.valueToTree(piResponseNode);
                        String piSuccessCode = node.get("successCode").asText();
                        String piSucessDescrp = node.get("sucessDescrp").asText();
                        piRequest.setPiSuccessCode(piSuccessCode);
                        piRequest.setPiSuccessDesc(piSucessDescrp);
                    } else {
                        paymentStatus = PaymentStatus.FAILED;
                        piRequest.setPiErrorResp(jitResponse.getErrorMsg());
                    }
                } catch (Exception e) {
                    paymentStatus = PaymentStatus.FAILED;
                    String errorMessage = e.toString();
                    errorMessage = e.getMessage();
                    Throwable cause = e.getCause();
                    if (cause instanceof HttpServerErrorException.InternalServerError) {
                        errorMessage = ((HttpServerErrorException.InternalServerError) cause).getResponseBodyAsString();
                    } else {
                        errorMessage = e.getMessage();
                    }
                    log.error("Exception while calling request." + e);
                    piRequest.setPiErrorResp(errorMessage);
                    piRequest.setPiStatus(PIStatus.FAILED);
                    for(Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
                        beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
                    }
                }
                // IF pi is initiated then add transaction records
                if (paymentStatus.equals(PaymentStatus.INITIATED)) {
                    piEnrichment.addTransactionDetailsInPiRequest(piRequest, paymentRequest, selectedSanction);
                    // update fund summary amount
                    selectedSanction.getFundsSummary().setAvailableAmount(selectedSanction.getFundsSummary().getAvailableAmount().subtract(totalAmount));
                    selectedSanction.getFundsSummary().getAuditDetails().setLastModifiedTime(piRequest.getAuditDetails().getLastModifiedTime());
                    selectedSanction.getFundsSummary().getAuditDetails().setLastModifiedBy(piRequest.getAuditDetails().getLastModifiedBy());
                }
                piRepository.save(Collections.singletonList(piRequest), selectedSanction.getFundsSummary(), paymentStatus);
            } else {
                paymentStatus = PaymentStatus.FAILED;
            }
        } catch (Exception e) {
            log.info("Exception " + e);
            paymentStatus = PaymentStatus.FAILED;
        }

        updatePaymentForStatus(paymentRequest, paymentStatus);

        return piRequest;
    }



    private List<Beneficiary> getBeneficiariesFromPayment(PaymentRequest paymentRequest) throws Exception {
        log.info("paymentRequest : " + paymentRequest);

        // Get the list of bills based on payment request
        List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);

        billList = filterBillsPayableLineItemByPayments(paymentRequest.getPayment(), billList);

        List<Beneficiary> beneficiaryList = piEnrichment.getBeneficiariesFromBills(billList, paymentRequest);

        // Get all beneficiary ids from pi request
        List<String> individualBeneficiaryIds = new ArrayList<>();
        List<String> orgBeneficiaryIds = new ArrayList<>();
        for(Bill bill: billList) {
            for (BillDetail billDetail: bill.getBillDetails()) {
                Party payee = billDetail.getPayee();
                if (payee != null && payee.getType().equals("INDIVIDUAL")) {
                    individualBeneficiaryIds.add(billDetail.getPayee().getIdentifier());
                } else if (payee != null) {
                    orgBeneficiaryIds.add(billDetail.getPayee().getIdentifier());
                }
            }
        }
        List<String> beneficiaryIds = new ArrayList<>();
        for (Beneficiary beneficiary :beneficiaryList) {
            beneficiaryIds.add(beneficiary.getBeneficiaryId());
        }

        List<Organisation> organizations = new ArrayList<>();
        List<Individual> individuals = new ArrayList<>();
        // Get bank account details by beneficiary ids
        List<BankAccount> bankAccounts = bankAccountUtils.getBankAccountsByIdentifier(paymentRequest.getRequestInfo(), beneficiaryIds, paymentRequest.getPayment().getTenantId());
        // Get organizations details
        if (orgBeneficiaryIds != null && !orgBeneficiaryIds.isEmpty()) {
            organizations = organisationUtils.getOrganisationsById(paymentRequest.getRequestInfo(), orgBeneficiaryIds, paymentRequest.getPayment().getTenantId());
        }
        // Get bank account details by beneficiary ids
        if (individualBeneficiaryIds != null && !individualBeneficiaryIds.isEmpty()) {
            individuals = individualUtils.getIndividualById(paymentRequest.getRequestInfo(), individualBeneficiaryIds, paymentRequest.getPayment().getTenantId());
        }
        // Enrich PI request with beneficiary bankaccount details
        piEnrichment.enrichBankaccountOnBeneficiary(beneficiaryList, bankAccounts, individuals, organizations);
        return beneficiaryList;
    }


    private List<Bill> filterBillsPayableLineItemByPayments(Payment payment, List<Bill> billList) {
        Map<String, Bill> billMap = billList.stream()
                .collect(Collectors.toMap(Bill::getId, Function.identity()));

        Map<String, BillDetail> billDetailMap = billList.stream()
                .map(Bill::getBillDetails)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(BillDetail::getId, Function.identity()));
        Map<String, LineItem> billPayableLineItemMap = billList.stream()
                .map(Bill::getBillDetails)
                .flatMap(Collection::stream)
                .map(BillDetail::getPayableLineItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(LineItem::getId, Function.identity()));
        for (PaymentBill paymentBill: payment.getBills()) {
            for (PaymentBillDetail paymentBillDetail : paymentBill.getBillDetails()) {
                List<LineItem> lineItems = new ArrayList<>();
                for (PaymentLineItem payableLineItem : paymentBillDetail.getPayableLineItems()) {
                    LineItem lineItem = billPayableLineItemMap.get(payableLineItem.getLineItemId());
                    if (lineItem != null && lineItem.getStatus().equals(Status.ACTIVE) && payableLineItem.getStatus().equals(PaymentStatus.INITIATED))
                        lineItems.add(lineItem);
                }
                billDetailMap.get(paymentBillDetail.getBillDetailId()).setPayableLineItems(lineItems);
            }
        }
        return billList;
    }

    private void updatePaymentForStatus(PaymentRequest paymentRequest, PaymentStatus paymentStatus) {
        paymentRequest.getPayment().setStatus(paymentStatus);
        for (PaymentBill bill: paymentRequest.getPayment().getBills()) {
            bill.setStatus(paymentStatus);
            for (PaymentBillDetail billDetail: bill.getBillDetails()) {
                billDetail.setStatus(paymentStatus);
                for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
                    lineItem.setStatus(paymentStatus);
                }
            }
        }
        billUtils.updatePaymentsData(paymentRequest);
    }

    public List<PaymentInstruction> searchPi(PISearchRequest piSearchRequest){

        searchValidator(piSearchRequest.getSearchCriteria());
        List<PaymentInstruction> paymentInstructions = piRepository.searchPi(piSearchRequest);

        log.info("Sending search response");
        return paymentInstructions;
    }
    public void searchValidator(PISearchCriteria piSearchCriteria){
        if(CollectionUtils.isEmpty(piSearchCriteria.getIds()) && StringUtils.isEmpty(piSearchCriteria.getJitBillNo())
            && StringUtils.isEmpty(piSearchCriteria.getMuktaReferenceId()) && StringUtils.isEmpty(piSearchCriteria.getPiStatus())){
            throw new CustomException("SEARCH_CRITERIA_MANDATORY", "Atleast one search parameter should be provided");
        }
    }

}
