package org.egov.service;

import org.egov.common.models.individual.Individual;
import org.egov.utils.BankAccountUtils;
import org.egov.utils.BillUtils;
import org.egov.utils.IndividualUtils;
import org.egov.utils.OrganisationUtils;
import org.egov.web.models.*;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.Status;
import org.egov.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public PIRequest getPaymentInstructionFromPayment(PaymentRequest paymentRequest) throws Exception {
        System.out.println("paymentRequest : " + paymentRequest);
        // Get the list of bills based on payment request
        List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);
        System.out.println(billList);
        // if bills are there then process bills
        PIRequest piRequest = null;
        if (billList != null && !billList.isEmpty()) {
            piRequest = getPiRequestByBill(billList);

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

        // Get bank account details by beneficiary ids
        List<BankAccount> bankAccounts = bankAccountUtils.getBankAccountsByIdentifier(paymentRequest.getRequestInfo(), beneficiaryIds, paymentRequest.getPayment().getTenantId());
        // Get organizations details
        List<Organisation> organizations = organisationUtils.getOrganisationsById(paymentRequest.getRequestInfo(), orgBeneficiaryIds, paymentRequest.getPayment().getTenantId());
        // Get bank account details by beneficiary ids
        List<Individual> individuals = individualUtils.getIndividualById(paymentRequest.getRequestInfo(), individualBeneficiaryIds, paymentRequest.getPayment().getTenantId());
        // Enrich PI request with beneficiary bankaccount details
        enrichBankaccountOnPI(piRequest, bankAccounts, individuals, organizations);
        return piRequest;
    }

    private PIRequest getPiRequestByBill(List<Bill> billList) {
        // Get the beneficiaries
        List<PiBeneficiary>  piBeneficiaries = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        for (Bill bill: billList) {
            List<PiBeneficiary>  beneficiaries = getBeneficiaryListFromBill(bill);
            if (beneficiaries != null && !beneficiaries.isEmpty()) {
                piBeneficiaries.addAll(beneficiaries);
                for (PiBeneficiary piBeneficiary: beneficiaries) {
                    BigDecimal amt = new BigDecimal(piBeneficiary.getBenfAmount());
                    totalAmount = totalAmount.add(amt);
                }
            }
        }

        // TODO: Enrich PI request with MDMS Data
        // TODO: Get total amount of payment request and get the hoa according to available balance
        PIRequest piRequest = PIRequest.builder()
                .jitBillNo(billList.get(0).getBillNumber())
                .jitBillDate("2023-05-25 16:26:42")
                .jitBillDdoCode("OLSHUD001")
                .granteeAgCode("GOHUDULBMPL0036")
                .schemeCode("13145")
                .hoa("132217058003586410459082112")
                .ssuIaId("1621")
                .mstAllotmentDistId("920")
                .ssuAllotmentId("937")
                .allotmentTxnSlNo("10")
                .purpose("Mukta")
                .build();
        piRequest.setBillGrossAmount(totalAmount.toString());
        piRequest.setBillNetAmount(totalAmount.toString());

        piRequest.setBeneficiaryDetails(piBeneficiaries);
        piRequest.setBillNumberOfBenf(String.valueOf(piBeneficiaries.size()));
        return piRequest;
    }

    private List<PiBeneficiary> getBeneficiaryListFromBill(Bill bill) {
        List<PiBeneficiary>  piBeneficiaryList = new ArrayList<>();
        for (BillDetail billDetail: bill.getBillDetails()) {
            for (LineItem lineItem: billDetail.getPayableLineItems()) {
                if (lineItem.getStatus().equals(Status.ACTIVE)) {
                    PiBeneficiary piBeneficiary = PiBeneficiary.builder()
                            .benefId(billDetail.getPayee().getIdentifier())
                            .benfAmount(lineItem.getAmount().toString())
                            .build();
                    piBeneficiaryList.add(piBeneficiary);
                }
            }
        }
        return piBeneficiaryList;
    }

    private void enrichBankaccountOnPI(PIRequest piRequest, List<BankAccount> bankAccounts, List<Individual> individuals, List<Organisation> organisations) {
        Map<String, BankAccount> bankAccountMap = new HashMap<>();
        if (bankAccounts != null && !bankAccounts.isEmpty()) {
            for(BankAccount bankAccount: bankAccounts) {
                bankAccountMap.put(bankAccount.getReferenceId(), bankAccount);
            }
        }
        Map<String, Individual> individualMap = new HashMap<>();
        if (individuals != null && !individuals.isEmpty()) {
            for(Individual individual: individuals) {
                individualMap.put(individual.getId(), individual);
            }
        }
        Map<String, Organisation> organisationMap = new HashMap<>();
        if (organisations != null && !organisations.isEmpty()) {
            for(Organisation organisation: organisations) {
                organisationMap.put(organisation.getId(), organisation);
            }
        }
        for(PiBeneficiary piBeneficiary: piRequest.getBeneficiaryDetails()) {
            BankAccount bankAccount = bankAccountMap.get(piBeneficiary.getBenefId());
            if (bankAccount != null) {
                piBeneficiary.setBenefName(bankAccount.getBankAccountDetails().get(0).getAccountHolderName());
                piBeneficiary.setBenfAcctNo(bankAccount.getBankAccountDetails().get(0).getAccountNumber());
                piBeneficiary.setBenfBankIfscCode(bankAccount.getBankAccountDetails().get(0).getBankBranchIdentifier().getCode());
                piBeneficiary.setBenfAccountType(bankAccount.getBankAccountDetails().get(0).getAccountType());
            }

            Individual individual = individualMap.get(piBeneficiary.getBenefId());
            Organisation organisation = organisationMap.get(piBeneficiary.getBenefId());

            if (individual != null) {
                piBeneficiary.setBenfMobileNo(individual.getMobileNumber());
                piBeneficiary.setBenfAddress(individual.getAddress().get(0).getWard().getCode() + " " +individual.getAddress().get(0).getLocality().getCode());
            } else if (organisation != null) {
                piBeneficiary.setBenfMobileNo(organisation.getContactDetails().get(0).getContactMobileNumber());
                piBeneficiary.setBenfAddress(organisation.getOrgAddress().get(0).getBoundaryCode() + " " +organisation.getOrgAddress().get(0).getCity());
            }
            piBeneficiary.setPurpose("Mukta Payment");
        }
    }

}
