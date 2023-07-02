package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.models.individual.Individual;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.repository.PIRepository;
import org.egov.utils.BankAccountUtils;
import org.egov.utils.BillUtils;
import org.egov.utils.IndividualUtils;
import org.egov.utils.OrganisationUtils;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.enums.Status;
import org.egov.web.models.jit.Beneficiary;
import org.egov.web.models.jit.JITRequest;
import org.egov.web.models.jit.PaymentInstruction;
import org.egov.web.models.jit.SanctionDetail;
import org.egov.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        try {
            List<Beneficiary> beneficiaries = getBeneficiariesFromPayment(paymentRequest);
            // Get the beneficiaries
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
            // Get enriched PI request to store on DB
            piRequest = piEnrichment.getEnrichedPaymentRequest(paymentRequest, beneficiaries, hoaSsuMap);

            // update fund summary amount
            selectedSanction.getFundsSummary().setAvailableAmount(selectedSanction.getFundsSummary().getAvailableAmount().subtract(totalAmount));

            if (hasFunds) {
                JITRequest jitPiRequest = piEnrichment.getJitPaymentInstructionRequestForIFMS(piRequest);
                try {
                    ifmsService.sendRequestToIFMS(jitPiRequest);
                } catch (Exception e) {
                    log.error("Exception while calling request.");
                }
            }
            piRepository.save(Collections.singletonList(piRequest));

        } catch (Exception e) {
            log.info("Exception ");
        }

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
    public List<PaymentInstruction> searchPaymentInstruction(PISearchRequest piSearchRequest){
        log.info("PaymentInstructionService::searchPaymentInstruction");
        piEnrichment.enrichPaymentInstructionOnSearch(piSearchRequest.getRequestInfo(), piSearchRequest);
        List<PaymentInstruction> paymentInstructions = piRepository.getPaymentInstructions(piSearchRequest);

        if(CollectionUtils.isEmpty(paymentInstructions)){
            return new ArrayList<>();
        }
        return paymentInstructions;
    }

}
