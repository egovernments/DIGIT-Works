package org.egov.service;

import org.egov.common.models.individual.Individual;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.utils.BankAccountUtils;
import org.egov.utils.BillUtils;
import org.egov.utils.IndividualUtils;
import org.egov.utils.OrganisationUtils;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bill.*;
import org.egov.web.models.jit.PIRequest;
import org.egov.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentInstruction {

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

    public PIRequest getPaymentInstructionFromPayment(PaymentRequest paymentRequest) throws Exception {
        System.out.println("paymentRequest : " + paymentRequest);
        // Get the list of bills based on payment request
        List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);
        System.out.println(billList);
        // if bills are there then process bills
        PIRequest piRequest = null;
        if (billList != null && !billList.isEmpty()) {
            piRequest = piEnrichment.getPiRequestByBill(billList);

        }
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
        beneficiaryIds.addAll(individualBeneficiaryIds);
        beneficiaryIds.addAll(orgBeneficiaryIds);

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
        piEnrichment.enrichBankaccountOnPI(piRequest, bankAccounts, individuals, organizations);
        return piRequest;
    }

}
