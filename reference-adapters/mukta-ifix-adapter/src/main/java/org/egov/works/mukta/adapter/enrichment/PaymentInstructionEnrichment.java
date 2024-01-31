package org.egov.works.mukta.adapter.enrichment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.models.individual.Individual;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.Status;
import org.egov.works.mukta.adapter.web.models.bankaccount.BankAccount;
import org.egov.works.mukta.adapter.web.models.bill.*;
import org.egov.works.mukta.adapter.web.models.enums.PaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.egov.works.mukta.adapter.web.models.jit.Beneficiary;
import org.egov.works.mukta.adapter.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
public class PaymentInstructionEnrichment {

    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentInstructionEnrichment(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Beneficiary> getBeneficiariesFromBills(List<Bill> billList, PaymentRequest paymentRequest, Map<String, Map<String, JSONArray>> mdmsData) {
        log.info("Started generating beneficiaries lists for PI");
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        JSONArray headCodesList = mdmsData.get(Constants.MDMS_EXPENSE_MODULE_NAME).get(Constants.MDMS_HEAD_CODES_MASTER);

        Map<String, JsonNode> headCodeMap = getHeadCodeHashMap(headCodesList);
        log.info("Generating beneficiary list based on line item.");
        for (Bill bill : billList) {
            for (BillDetail billDetail : bill.getBillDetails()) {
                for (LineItem lineItem : billDetail.getPayableLineItems()) {
                    Beneficiary beneficiary = getBeneficiariesFromLineItem(lineItem, billDetail.getPayee(), headCodeMap, paymentRequest.getPayment().getTenantId());
                    beneficiaryList.add(beneficiary);
                }
            }
        }
        log.info("Beneficiary list generated, combine them based on bank account number.");
        // Combine beneficiary by beneficiaryId
        beneficiaryList = combineBeneficiaryById(beneficiaryList);
        return beneficiaryList;
    }

    private Beneficiary getBeneficiariesFromLineItem(LineItem lineItem, Party payee, Map<String, JsonNode> headCodeMap, String tenantId) {
        log.info("Started executing getBeneficiariesFromLineItem");
        String beneficiaryId = payee.getIdentifier();
        String headCode = lineItem.getHeadCode();
        Beneficiary beneficiary = null;
        if (beneficiaryId != null && headCode != null) {
            JsonNode headCodeNode = headCodeMap.get(headCode);
            if (headCodeNode != null) {
                String headCodeCategory = headCodeNode.get(Constants.HEAD_CODE_CATEGORY_KEY).asText();
                if (headCodeCategory != null && headCodeCategory.equalsIgnoreCase(Constants.HEAD_CODE_DEDUCTION_CATEGORY)) {
                    beneficiaryId = Constants.DEDUCTION_BENEFICIARY_BY_HEADCODE.replace("{tanentId}", tenantId).replace("{headcode}", headCode);
                }
            }
            List<LineItem> benefLineItemList = new ArrayList<>();
            benefLineItemList.add(lineItem);
            beneficiary = Beneficiary.builder()
                    .amount(lineItem.getAmount())
                    .beneficiaryId(beneficiaryId)
                    .benfLineItems(benefLineItemList).build();
        }
        log.info("Beneficiary generated and sending back.");
        return beneficiary;
    }

    private List<Beneficiary> combineBeneficiaryById(List<Beneficiary> beneficiaryList) {
        log.info("Started executing combineBeneficiaryById");
        Map<String, Beneficiary> benfMap = new HashMap<>();
        for (Beneficiary beneficiary : beneficiaryList) {
            if (benfMap.containsKey(beneficiary.getBeneficiaryId())) {
                benfMap.get(beneficiary.getBeneficiaryId()).getBenfLineItems().addAll(beneficiary.getBenfLineItems());
                benfMap.get(beneficiary.getBeneficiaryId()).setAmount(benfMap.get(beneficiary.getBeneficiaryId()).getAmount().add(beneficiary.getAmount()));
            } else {
                benfMap.put(beneficiary.getBeneficiaryId(), beneficiary);
            }
        }
        List<Beneficiary> beneficiaries = new ArrayList<>(benfMap.values());
        log.info("Beneficiary details are combined based on account details, sending back.");
        return beneficiaries;
    }

    private Map<String, JsonNode> getHeadCodeHashMap(JSONArray headCodesList) {
        Map<String, JsonNode> headCodeMap = new HashMap<>();
        for (Object headcode : headCodesList) {
            JsonNode node = objectMapper.valueToTree(headcode);
            headCodeMap.put(node.get("code").asText(), node);
        }
        return headCodeMap;
    }

    public Disbursement enrichBankaccountOnBeneficiary(List<Beneficiary> beneficiaryList, List<BankAccount> bankAccounts, List<Individual> individuals, List<Organisation> organisations, PaymentRequest paymentRequest) {
        log.info("Started executing enrichBankaccountOnBeneficiary");
        Map<String, BankAccount> bankAccountMap = new HashMap<>();
        if (bankAccounts != null && !bankAccounts.isEmpty()) {
            for (BankAccount bankAccount : bankAccounts) {
                bankAccountMap.put(bankAccount.getReferenceId(), bankAccount);
            }
        }
        Map<String, Individual> individualMap = new HashMap<>();
        if (individuals != null && !individuals.isEmpty()) {
            for (Individual individual : individuals) {
                individualMap.put(individual.getId(), individual);
            }
        }
        Map<String, Organisation> organisationMap = new HashMap<>();
        if (organisations != null && !organisations.isEmpty()) {
            for (Organisation organisation : organisations) {
                organisationMap.put(organisation.getId(), organisation);
            }
        }
        log.info("Created map of org, individual and bankaccount, started generating beneficiary.");
        Disbursement disbursement = new Disbursement();
        List<Disbursement> disbursements = new ArrayList<>();
        for (Beneficiary piBeneficiary : beneficiaryList) {
            BankAccount bankAccount = bankAccountMap.get(piBeneficiary.getBeneficiaryId());
            Individual individual = individualMap.get(piBeneficiary.getBeneficiaryId());
            Organisation organisation = organisationMap.get(piBeneficiary.getBeneficiaryId());
            for (LineItem lineItem : piBeneficiary.getBenfLineItems()) {
                if(lineItem.getStatus().equals(org.egov.works.mukta.adapter.web.models.enums.Status.ACTIVE) && !lineItem.getPaymentStatus().equals(PaymentStatus.SUCCESSFUL)){
                    Disbursement disbursementForLineItem = enrichDisbursementForEachLineItem(bankAccount, individual, organisation, lineItem, paymentRequest.getRequestInfo().getUserInfo().getUuid());
                    disbursements.add(disbursementForLineItem);
                }
            }
        }
        UUID uuid = UUID.randomUUID();
        disbursement.setId(uuid.toString());
        disbursement.setTargetId(paymentRequest.getPayment().getPaymentNumber());
        disbursement.setDisbursements(disbursements);
        disbursement.setDisbursementDate(ZonedDateTime.now().toEpochSecond());
        disbursement.setAuditDetails(setAuditDetails(paymentRequest.getRequestInfo().getUserInfo().getUuid(), paymentRequest.getRequestInfo().getUserInfo().getUuid()));
        disbursement.setLocationCode(paymentRequest.getPayment().getTenantId());
        setAmountForParentDisbursement(disbursement);
        log.info("Beneficiary details enriched and sending back.");
        return disbursement;
    }

    private void setAmountForParentDisbursement(Disbursement disbursement) {
        BigDecimal netAmount = BigDecimal.ZERO;
        BigDecimal grossAmount = BigDecimal.ZERO;
        for (Disbursement disbursement1 : disbursement.getDisbursements()) {
            netAmount = netAmount.add(disbursement1.getNetAmount());
            grossAmount = grossAmount.add(disbursement1.getGrossAmount());
        }
        disbursement.setNetAmount(netAmount);
        disbursement.setGrossAmount(grossAmount);
    }

    private Disbursement enrichDisbursementForEachLineItem(BankAccount bankAccount, Individual individual, Organisation organisation, LineItem lineItem,String userId) {
        log.info("Started executing enrichDisbursement");
        String accountCode = "{ACCOUNT_NO}@{IFSC_CODE}";
        Disbursement disbursement = new Disbursement();
        UUID uuid = UUID.randomUUID();
        disbursement.setId(uuid.toString());
        org.egov.works.mukta.adapter.web.models.Individual piIndividual = new org.egov.works.mukta.adapter.web.models.Individual();
        if (bankAccount != null && !bankAccount.getBankAccountDetails().isEmpty()) {
            accountCode = accountCode.replace("{ACCOUNT_NO}", bankAccount.getBankAccountDetails().get(0).getAccountNumber());
            accountCode = accountCode.replace("{IFSC_CODE}", bankAccount.getBankAccountDetails().get(0).getBankBranchIdentifier().getCode());
            disbursement.setAccountCode(accountCode);
        }
        disbursement.setNetAmount(lineItem.getAmount());
        disbursement.setTargetId(lineItem.getId());
        disbursement.setGrossAmount(lineItem.getAmount());
        disbursement.setCurrencyCode(Currency.getInstance("INR"));
        disbursement.setLocaleCode("en_IN");
        disbursement.setProgramCode("PROG");
        disbursement.setLocationCode(lineItem.getTenantId());

        if (individual != null) {
            piIndividual.setAddress(individual.getAddress().get(0).getAddressLine1());
            piIndividual.setGender(individual.getGender());
            piIndividual.setPhone(individual.getMobileNumber());
            piIndividual.setEmail(individual.getEmail());
            piIndividual.setPin(individual.getAddress().get(0).getPincode());
            piIndividual.setName(individual.getName().getGivenName());
        }
        if (organisation != null) {
            piIndividual.setAddress(organisation.getOrgAddress().get(0).getAddressLine1());
            piIndividual.setPhone(organisation.getContactDetails().get(0).getContactMobileNumber());
            piIndividual.setEmail(organisation.getContactDetails().get(0).getContactEmail());
            piIndividual.setPin(organisation.getOrgAddress().get(0).getPincode());
            piIndividual.setName(organisation.getName());
        }
        disbursement.setIndividual(piIndividual);
        disbursement.setAuditDetails(setAuditDetails(userId, userId));

        return disbursement;
    }

    private AuditDetails setAuditDetails(String createdBy, String lastModifiedBy) {
        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedBy(createdBy);
        auditDetails.setCreatedTime(ZonedDateTime.now().toEpochSecond());
        auditDetails.setLastModifiedBy(lastModifiedBy);
        auditDetails.setLastModifiedTime(ZonedDateTime.now().toEpochSecond());
        return auditDetails;
    }

    public void enrichDisbursementStatus(Disbursement disbursement) {
        Status status = Status.builder().statusCode(StatusCode.INITIATED).statusMessage("Initiated").build();
        disbursement.setStatus(status);
        disbursement.getDisbursements().forEach(disbursement1 -> {
            disbursement1.setStatus(status);
        });
    }
}
