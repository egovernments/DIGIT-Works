package org.egov.works.mukta.adapter.enrichment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.models.individual.Individual;
import org.egov.works.mukta.adapter.config.Constants;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.util.EncryptionDecryptionUtil;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.PaymentRequest;
import org.egov.works.mukta.adapter.web.models.Status;
import org.egov.works.mukta.adapter.web.models.enums.*;
import org.egov.works.mukta.adapter.web.models.jit.Beneficiary;
import org.egov.works.mukta.adapter.web.models.jit.BenfLineItems;
import org.egov.works.mukta.adapter.web.models.jit.PADetails;
import org.egov.works.mukta.adapter.web.models.jit.PaymentInstruction;
import org.egov.works.services.common.models.bankaccounts.BankAccount;
import org.egov.works.services.common.models.expense.Bill;
import org.egov.works.services.common.models.expense.BillDetail;
import org.egov.works.services.common.models.expense.LineItem;
import org.egov.works.services.common.models.expense.Party;
import org.egov.works.services.common.models.expense.enums.PaymentStatus;
import org.egov.works.services.common.models.organization.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
public class PaymentInstructionEnrichment {

    private final ObjectMapper objectMapper;
    private final EncryptionDecryptionUtil encryptionDecryptionUtil;
    private final MuktaAdaptorConfig muktaAdaptorConfig;

    @Autowired
    public PaymentInstructionEnrichment(ObjectMapper objectMapper, EncryptionDecryptionUtil encryptionDecryptionUtil, MuktaAdaptorConfig muktaAdaptorConfig) {
        this.objectMapper = objectMapper;
        this.encryptionDecryptionUtil = encryptionDecryptionUtil;
        this.muktaAdaptorConfig = muktaAdaptorConfig;
    }
    /**
     * The function enriches the beneficiary based on the bills and payment request.
     * @param billList The bill list
     * @param paymentRequest The payment request
     * @param mdmsData The mdms data
     * @return The list of beneficiaries
     */
    public List<Beneficiary> getBeneficiariesFromBills(List<Bill> billList, PaymentRequest paymentRequest, Map<String, Map<String, JSONArray>> mdmsData) {
        log.info("Started generating beneficiaries lists for PI");
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        JSONArray headCodesList = mdmsData.get(Constants.MDMS_EXPENSE_MODULE_NAME).get(Constants.MDMS_HEAD_CODES_MASTER);

        Map<String, JsonNode> headCodeMap = getHeadCodeHashMap(headCodesList);
        log.info("Generating beneficiary list based on line item.");
        for (Bill bill : billList) {
            for (BillDetail billDetail : bill.getBillDetails()) {
                for (LineItem lineItem : billDetail.getPayableLineItems()) {
                    Beneficiary beneficiary = getBeneficiariesFromLineItem(lineItem, billDetail.getPayee(), headCodeMap, paymentRequest.getPayment().getTenantId(),bill);
                    beneficiaryList.add(beneficiary);
                }
            }
        }
        log.info("Beneficiary list generated, combine them based on bank account number.");
        // Combine beneficiary by beneficiaryId
        beneficiaryList = combineBeneficiaryById(beneficiaryList);
        return beneficiaryList;
    }
    /**
     * The function generates the beneficiary based on the line item and payee details.
     * @param lineItem The line item
     * @param payee The payee
     * @param headCodeMap The head code map
     * @param tenantId The tenant id
     * @return The beneficiary
     */
    private Beneficiary getBeneficiariesFromLineItem(LineItem lineItem, Party payee, Map<String, JsonNode> headCodeMap, String tenantId, Bill bill) {
        log.info("Started executing getBeneficiariesFromLineItem");
        String beneficiaryId = payee.getIdentifier();
        ObjectNode additionalDetails = objectMapper.createObjectNode();
        String headCode = lineItem.getHeadCode();
        Beneficiary beneficiary = null;
        BeneficiaryType beneficiaryType = BeneficiaryType.IND;
        if (beneficiaryId != null && headCode != null) {
            JsonNode headCodeNode = headCodeMap.get(headCode);
            if (headCodeNode != null) {
                String headCodeCategory = headCodeNode.get(Constants.HEAD_CODE_CATEGORY_KEY).asText();
                if (headCodeCategory != null && headCodeCategory.equalsIgnoreCase(Constants.HEAD_CODE_DEDUCTION_CATEGORY)) {
                    beneficiaryId = Constants.DEDUCTION_BENEFICIARY_BY_HEADCODE.replace("{tanentId}", tenantId).replace("{headcode}", headCode);
                    beneficiaryType = BeneficiaryType.DEPT;
                }
            }
            List<LineItem> benefLineItemList = new ArrayList<>();
            benefLineItemList.add(lineItem);
            additionalDetails.put("billNumber",bill.getBillNumber());
            additionalDetails.put("referenceId", bill.getReferenceId());
            beneficiary = Beneficiary.builder()
                    .amount(lineItem.getAmount())
                    .beneficiaryId(beneficiaryId)
                    .beneficiaryType(beneficiaryType)
                    .additionalDetails(additionalDetails)
                    .lineItems(benefLineItemList).build();
        }
        log.info("Beneficiary generated and sending back.");
        return beneficiary;
    }
    /**
     * The function combines the beneficiary based on the beneficiary id.
     * @param beneficiaryList The beneficiary list
     * @return The combined beneficiary list
     */
    private List<Beneficiary> combineBeneficiaryById(List<Beneficiary> beneficiaryList) {
        log.info("Started executing combineBeneficiaryById");
        Map<String, Beneficiary> benfMap = new HashMap<>();
        for (Beneficiary beneficiary : beneficiaryList) {
            if (benfMap.containsKey(beneficiary.getBeneficiaryId())) {
                benfMap.get(beneficiary.getBeneficiaryId()).getLineItems().addAll(beneficiary.getLineItems());
                benfMap.get(beneficiary.getBeneficiaryId()).setAmount(benfMap.get(beneficiary.getBeneficiaryId()).getAmount().add(beneficiary.getAmount()));
            } else {
                benfMap.put(beneficiary.getBeneficiaryId(), beneficiary);
            }
        }
        List<Beneficiary> beneficiaries = new ArrayList<>(benfMap.values());
        log.info("Beneficiary details are combined based on account details, sending back.");
        return beneficiaries;
    }
    /**
     * The function generates the head code map based on the head codes list.
     * @param headCodesList The head codes list
     * @return The head code map
     */
    private Map<String, JsonNode> getHeadCodeHashMap(JSONArray headCodesList) {
        Map<String, JsonNode> headCodeMap = new HashMap<>();
        for (Object headcode : headCodesList) {
            JsonNode node = objectMapper.valueToTree(headcode);
            headCodeMap.put(node.get("code").asText(), node);
        }
        return headCodeMap;
    }
    /**
     * The function enriches the bank account on the beneficiary.
     * @param beneficiaryList The beneficiary list
     * @param bankAccounts The bank accounts
     * @param individuals The individuals
     * @param organisations The organisations
     * @param paymentRequest The payment request
     * @param ssuNode The ssu node
     * @param headCodeCategoryMap The head code category map
     * @return The disbursement
     */
    public Disbursement enrichBankaccountOnBeneficiary(List<Beneficiary> beneficiaryList, List<BankAccount> bankAccounts, List<Individual> individuals, List<Organisation> organisations, PaymentRequest paymentRequest, JsonNode ssuNode, Map<String,String> headCodeCategoryMap, Boolean isRevised) {
        log.info("Started executing enrichBankaccountOnBeneficiary");
        String programCode = ssuNode.get("programCode").asText();
        boolean isAnyDisbursementFailed = false;
        AuditDetails auditDetails = AuditDetails.builder().createdBy(paymentRequest.getRequestInfo().getUserInfo().getUuid())
                .createdTime(System.currentTimeMillis())
                .lastModifiedBy(paymentRequest.getRequestInfo().getUserInfo().getUuid())
                .lastModifiedTime(System.currentTimeMillis()).build();
        Set<String> billNumbers = new HashSet<>();
        Set<String> referenceIds = new HashSet<>();
        ObjectNode additionalDetailsForDisbursement = objectMapper.createObjectNode();
        // Creating map of bank account, individual and organisation based on the beneficiary id.git
        Map<String, BankAccount> bankAccountMap = createBankAccountMap(bankAccounts);
        Map<String, Individual> individualMap = createIndividualMap(individuals);
        Map<String, Organisation> organisationMap = createOrganisationMap(organisations);
        log.info("Created map of org, individual and bankaccount, started generating beneficiary.");
        Disbursement disbursement = new Disbursement();
        List<Disbursement> disbursements = new ArrayList<>();
        // Enriching the disbursement for each line line item.
        for (Beneficiary piBeneficiary : beneficiaryList) {
            BankAccount bankAccount = bankAccountMap.get(piBeneficiary.getBeneficiaryId());
            Individual individual = individualMap.get(piBeneficiary.getBeneficiaryId());
            Organisation organisation = organisationMap.get(piBeneficiary.getBeneficiaryId());
            ObjectNode additionalDetails = (ObjectNode) piBeneficiary.getAdditionalDetails();
            if(piBeneficiary.getBeneficiaryType().equals(BeneficiaryType.DEPT) && bankAccount == null){
                isAnyDisbursementFailed = true;
                log.error("Bank account not found for department beneficiary. -> "+piBeneficiary.getBeneficiaryId());
            }
            if(!piBeneficiary.getBeneficiaryType().equals(BeneficiaryType.DEPT) && (bankAccount == null || (individual == null && organisation == null))){
                isAnyDisbursementFailed = true;
                log.error("Bank account or individual or organisation not found for individual/organisation beneficiary. -> "+piBeneficiary.getBeneficiaryId());
            }
            for (LineItem lineItem : piBeneficiary.getLineItems()) {
                if (lineItem.getStatus().equals(org.egov.works.services.common.models.expense.enums.Status.ACTIVE) &&
                        (lineItem.getPaymentStatus() == null || !lineItem.getPaymentStatus().equals(PaymentStatus.SUCCESSFUL))) {
                    Disbursement disbursementForLineItem = enrichDisbursementForEachLineItem(bankAccount, individual, organisation, lineItem, auditDetails,programCode,headCodeCategoryMap, piBeneficiary.getBeneficiaryId());
                    disbursements.add(disbursementForLineItem);
                }
            }
            billNumbers.add(additionalDetails.get("billNumber").asText());
            referenceIds.add(additionalDetails.get("referenceId").asText());
        }
        // Enriching the parent disbursement.
        enrichParentDisbursement(disbursement, paymentRequest, disbursements, auditDetails, programCode, isAnyDisbursementFailed, additionalDetailsForDisbursement, billNumbers, referenceIds,isRevised);
        log.info("Beneficiary details enriched and sending back.");
        return disbursement;
    }

    private void enrichParentDisbursement(Disbursement disbursement, PaymentRequest paymentRequest, List<Disbursement> disbursements, AuditDetails auditDetails, String programCode, boolean isAnyDisbursementFailed, ObjectNode additionalDetailsForDisbursement, Set<String> billNumbers, Set<String> referenceIds,Boolean isRevised) {
        UUID uuid = UUID.randomUUID();
        disbursement.setId(uuid.toString());
        disbursement.setTargetId(paymentRequest.getPayment().getPaymentNumber());
        disbursement.setDisbursements(disbursements);
        disbursement.setDisbursementDate(ZonedDateTime.now().toEpochSecond());
        disbursement.setAuditDetails(auditDetails);
        disbursement.setLocationCode(paymentRequest.getPayment().getTenantId());
        disbursement.setProgramCode(programCode);
        if(Boolean.TRUE.equals(isAnyDisbursementFailed)){
            enrichDisbursementStatus(disbursement,StatusCode.FAILED, Error.DISBURSEMENT_ENRICHMENT_FAILED_MESSAGE);
        }else{
            enrichDisbursementStatus(disbursement,StatusCode.INITIATED, StatusCode.INITIATED.toString());
        }
        setAmountForParentDisbursement(disbursement);

        additionalDetailsForDisbursement.set("billNumber", objectMapper.valueToTree(billNumbers));
        additionalDetailsForDisbursement.set("referenceId", objectMapper.valueToTree(referenceIds));
        additionalDetailsForDisbursement.set("isRevised", objectMapper.valueToTree(isRevised));
        disbursement.setAdditionalDetails(additionalDetailsForDisbursement);
    }


    private Map<String, BankAccount> createBankAccountMap(List<BankAccount> bankAccounts) {
        Map<String, BankAccount> bankAccountMap = new HashMap<>();
        if (bankAccounts != null && !bankAccounts.isEmpty()) {
            for (BankAccount bankAccount : bankAccounts) {
                bankAccountMap.put(bankAccount.getReferenceId(), bankAccount);
            }
        }
        return bankAccountMap;
    }

    private Map<String, Individual> createIndividualMap(List<Individual> individuals) {
        Map<String, Individual> individualMap = new HashMap<>();
        if (individuals != null && !individuals.isEmpty()) {
            for (Individual individual : individuals) {
                individualMap.put(individual.getId(), individual);
            }
        }
        return individualMap;
    }

    private Map<String, Organisation> createOrganisationMap(List<Organisation> organisations) {
        Map<String, Organisation> organisationMap = new HashMap<>();
        if (organisations != null && !organisations.isEmpty()) {
            for (Organisation organisation : organisations) {
                organisationMap.put(organisation.getId(), organisation);
            }
        }
        return organisationMap;
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
    /**
     * The function enriches the disbursement for each line item.
     * @param bankAccount The bank account
     * @param individual The individual
     * @param organisation The organisation
     * @param lineItem The line item
     * @param auditDetails The audit details
     * @param programCode The program code
     * @param headCodeCategoryMap The head code category map
     * @return The disbursement
     */
    private Disbursement enrichDisbursementForEachLineItem(BankAccount bankAccount, Individual individual, Organisation organisation, LineItem lineItem,AuditDetails auditDetails,String programCode,Map<String,String> headCodeCategoryMap,String beneficiaryId) {
        log.info("Started executing enrichDisbursement");
        String accountCode = "{ACCOUNT_NO}@{IFSC_CODE}";
        String accountType = null;
        ObjectNode additionalDetails = objectMapper.createObjectNode();
        Disbursement disbursement = new Disbursement();
        UUID uuid = UUID.randomUUID();
        disbursement.setId(uuid.toString());
        org.egov.works.mukta.adapter.web.models.Individual piIndividual = new org.egov.works.mukta.adapter.web.models.Individual();
        if (bankAccount != null && !bankAccount.getBankAccountDetails().isEmpty()) {
            accountCode = accountCode.replace("{ACCOUNT_NO}", bankAccount.getBankAccountDetails().get(0).getAccountNumber());
            accountCode = accountCode.replace("{IFSC_CODE}", bankAccount.getBankAccountDetails().get(0).getBankBranchIdentifier().getCode());
            accountType = bankAccount.getBankAccountDetails().get(0).getAccountType();
            disbursement.setAccountCode(accountCode);
        }
        disbursement.setNetAmount(lineItem.getAmount());
        disbursement.setTargetId(lineItem.getId());
        disbursement.setGrossAmount(lineItem.getAmount());
        disbursement.setCurrencyCode("INR");
        disbursement.setLocaleCode("en_IN");
        disbursement.setProgramCode(programCode);
        disbursement.setLocationCode(lineItem.getTenantId());

        if(headCodeCategoryMap.get(lineItem.getHeadCode()).equals(Constants.HEAD_CODE_DEDUCTION_CATEGORY)){
            piIndividual.setName(lineItem.getHeadCode());
            piIndividual.setPhone("9999999999");
            piIndividual.setAddress(lineItem.getTenantId());
            additionalDetails.put("beneficiaryType",BeneficiaryType.DEPT.toString());
        }else{
            if (individual != null) {
                piIndividual.setAddress(individual.getAddress().get(0).getAddressLine1() == null ? individual.getAddress().get(0).getCity() : individual.getAddress().get(0).getAddressLine1());
                piIndividual.setGender(individual.getGender());
                piIndividual.setPhone(individual.getMobileNumber());
                piIndividual.setEmail(individual.getEmail());
                piIndividual.setPin(individual.getAddress().get(0).getPincode());
                piIndividual.setName(individual.getName().getGivenName());
                additionalDetails.put("beneficiaryType",BeneficiaryType.IND.toString());
            }
            if (organisation != null) {
                piIndividual.setAddress(organisation.getOrgAddress().get(0).getBuildingName() == null ? organisation.getOrgAddress().get(0).getCity() : organisation.getOrgAddress().get(0).getBuildingName());
                piIndividual.setPhone(organisation.getContactDetails().get(0).getContactMobileNumber());
                piIndividual.setEmail(organisation.getContactDetails().get(0).getContactEmail());
                piIndividual.setPin(organisation.getOrgAddress().get(0).getPincode());
                piIndividual.setName(organisation.getName());
                additionalDetails.put("beneficiaryType",BeneficiaryType.ORG.toString());
            }
        }
        disbursement.setIndividual(piIndividual);
        disbursement.setAuditDetails(auditDetails);
        additionalDetails.put(Constants.ACCOUNT_TYPE, accountType);
        additionalDetails.put("beneficiaryId",beneficiaryId);
        disbursement.setAdditionalDetails(additionalDetails);
        return disbursement;
    }
    /**
     * The function enriches the disbursement status.
     *
     * @param disbursement The disbursement
     * @param statusCode   The status code
     * @param message
     */
    public void enrichDisbursementStatus(Disbursement disbursement, StatusCode statusCode, String message) {
        Status status = Status.builder().statusCode(statusCode).statusMessage(message).build();
        disbursement.setStatus(status);
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            disbursement1.setStatus(status);
        }
    }

    public PaymentInstruction getPaymentInstructionFromDisbursement(Disbursement disbursement) {
        log.info("Started executing getPaymentInstructionFromDisbursement");
        List<Beneficiary> beneficiaries = new ArrayList<>();
        AuditDetails auditDetails = disbursement.getAuditDetails();
        ObjectNode additionalDetailsOfDisbursement = objectMapper.valueToTree(disbursement.getAdditionalDetails());
        HashMap<String,List<Disbursement>> beneficiaryDisbursementMap = getBeneficiaryDisbursementMap(disbursement);
        for(Map.Entry<String,List<Disbursement>> entry: beneficiaryDisbursementMap.entrySet()){
            String beneficiaryId = null;
            BigDecimal totalAmountForBenef = BigDecimal.ZERO;
            String beneficiaryDetailsId = UUID.randomUUID().toString();
            JsonNode beneficiaryTypeNode = null;
            JsonNode beneficiaryPaymentStatusNode = null;
            List<Disbursement> disbursements = entry.getValue();
            Disbursement disbursementLineItem = disbursements.get(0);
            List<BenfLineItems> benfLineItems = new ArrayList<>();
            for(Disbursement disbursement1: disbursements){
                BenfLineItems benfLineItem = BenfLineItems.builder().id(UUID.randomUUID().toString())
                        .lineItemId(disbursement1.getTargetId())
                        .beneficiaryId(beneficiaryDetailsId)
                        .auditDetails(auditDetails)
                        .build();

                totalAmountForBenef = totalAmountForBenef.add(disbursement1.getGrossAmount());
                if(disbursement1.getAdditionalDetails() != null){
                    ObjectNode additionalDetails = objectMapper.valueToTree(disbursement1.getAdditionalDetails());
                    JsonNode benfIdNode = additionalDetails.get("beneficiaryId");
                    beneficiaryId = benfIdNode == null? beneficiaryId: benfIdNode.asText();
                    beneficiaryTypeNode = additionalDetails.get("beneficiaryType");
                    beneficiaryPaymentStatusNode = additionalDetails.get("beneficiaryStatus");
                }
                benfLineItems.add(benfLineItem);
            }
            Beneficiary beneficiary = Beneficiary.builder()
                    .id(beneficiaryDetailsId)
                    .tenantId(disbursementLineItem.getLocationCode())
                    .muktaReferenceId(disbursementLineItem.getTargetId())
                    .beneficiaryNumber(disbursementLineItem.getTransactionId() == null? "NA": disbursementLineItem.getTransactionId())
                    .bankAccountId(disbursementLineItem.getAccountCode())
                    .amount(totalAmountForBenef)
                    .beneficiaryId(beneficiaryId)
                    .beneficiaryType(BeneficiaryType.valueOf(beneficiaryTypeNode == null? "IND": beneficiaryTypeNode.asText()))
                    .paymentStatus(getBenefStatus(beneficiaryPaymentStatusNode,disbursementLineItem.getStatus().getStatusCode()))
                    .benfLineItems(benfLineItems)
                    .auditDetails(auditDetails)
                    .build();
            beneficiaries.add(beneficiary);
        }
        ObjectNode additionalDetailsForPI = objectMapper.createObjectNode();
        additionalDetailsForPI.set("billNumber", additionalDetailsOfDisbursement.get("billNumber"));
        additionalDetailsForPI.set("referenceId", additionalDetailsOfDisbursement.get("referenceId"));
        additionalDetailsForPI.set("mstAllotmentId", additionalDetailsOfDisbursement.get("mstAllotmentId"));
        additionalDetailsForPI.set("hoaCode", additionalDetailsOfDisbursement.get("hoaCode"));
        JsonNode paDetailsNode = additionalDetailsOfDisbursement.get("paDetails");
        PADetails paDetails = objectMapper.convertValue(
                paDetailsNode,
                PADetails.class
        );
        JsonNode piStatus = additionalDetailsOfDisbursement.get("piStatus");
        JsonNode numBeneficiaries = additionalDetailsOfDisbursement.get("numBeneficiaries");
        JsonNode parentPiNumber = additionalDetailsOfDisbursement.get("parentPiNumber");
        return PaymentInstruction.builder()
                .id(disbursement.getId())
                .tenantId(disbursement.getLocationCode())
                .jitBillNo(disbursement.getTransactionId() == null? "NA": disbursement.getTransactionId())
                .muktaReferenceId(disbursement.getTargetId())
                .netAmount(disbursement.getNetAmount())
                .grossAmount(disbursement.getGrossAmount())
                .piStatus(getPiStatus(piStatus,disbursement.getStatus().getStatusCode()))
                .piErrorResp(disbursement.getStatus().getStatusMessage())
                .additionalDetails(additionalDetailsForPI)
                .numBeneficiaries((numBeneficiaries == null || numBeneficiaries.isNull())? disbursement.getDisbursements().size(): numBeneficiaries.asInt())
                .paDetails(Collections.singletonList(paDetails))
                .parentPiNumber((parentPiNumber == null || parentPiNumber.isNull())? null: parentPiNumber.asText())
                .isActive(true)
                .beneficiaryDetails(beneficiaries)
                .auditDetails(auditDetails)
                .build();
    }

    private PIStatus getPiStatus(JsonNode piStatus, StatusCode statusCode) {
        if(piStatus == null){
            switch (statusCode){
                case INPROCESS:
                    return PIStatus.IN_PROCESS;
                case INITIATED:
                    return PIStatus.INITIATED;
                case SUCCESSFUL:
                    return PIStatus.SUCCESSFUL;
                case FAILED:
                case ERROR:
                    return PIStatus.FAILED;
                case COMPLETED:
                    return PIStatus.COMPLETED;
                default:
                    return null;
            }
        }
        return PIStatus.fromValue(piStatus.asText());
    }

    private BeneficiaryPaymentStatus getBenefStatus(JsonNode beneficiaryPaymentStatus,StatusCode statusCode) {
        if(beneficiaryPaymentStatus == null){
            switch (statusCode){
                case INITIATED:
                    return BeneficiaryPaymentStatus.INITIATED;
                case INPROCESS:
                    return BeneficiaryPaymentStatus.IN_PROCESS;
                case SUCCESSFUL:
                    return BeneficiaryPaymentStatus.SUCCESS;
                case FAILED:
                case ERROR:
                    return BeneficiaryPaymentStatus.FAILED;
                default:
                    return null;
            }
        }else{
            switch (beneficiaryPaymentStatus.asText()){
                case "Payment Pending":
                    return BeneficiaryPaymentStatus.PENDING;
                case "Payment Initiated":
                    return BeneficiaryPaymentStatus.INITIATED;
                case "Payment In Process":
                    return BeneficiaryPaymentStatus.IN_PROCESS;
                case "Payment Successful":
                    return BeneficiaryPaymentStatus.SUCCESS;
                case "Payment Failed":
                    return BeneficiaryPaymentStatus.FAILED;
                default:
                    return null;
            }
        }
    }

    private HashMap<String, List<Disbursement>> getBeneficiaryDisbursementMap(Disbursement disbursement) {
        HashMap<String,List<Disbursement>> beneficiaryDisbursementMap = new HashMap<>();
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            if(beneficiaryDisbursementMap.containsKey(disbursement1.getTransactionId() == null? disbursement1.getAccountCode(): disbursement1.getTransactionId())){
                beneficiaryDisbursementMap.get(disbursement1.getTransactionId() == null? disbursement1.getAccountCode(): disbursement1.getTransactionId()).add(disbursement1);
            }else {
                List<Disbursement> disbursements = new ArrayList<>();
                disbursements.add(disbursement1);
                beneficiaryDisbursementMap.put(disbursement1.getTransactionId() == null? disbursement1.getAccountCode(): disbursement1.getTransactionId(), disbursements);
            }
        }

        return beneficiaryDisbursementMap;
    }

    /**
     * Enrich Exchange Codes for disbursement
     * @param disbursement
     * @param mdmsData
     */
    public void enrichExchangeCodes(Disbursement disbursement, Map<String, Map<String, JSONArray>> mdmsData) {
        log.info("Started executing enrichExchangeCodes");
        String targetSegmentCode = extractCode(mdmsData, Constants.MDMS_SEGMENT_CODES_MODULE, Constants.MDMS_TARGET_SEGMENT_CODES_MASTER);
        String sourceOfFundsCode = extractCode(mdmsData, Constants.MDMS_SEGMENT_CODES_MODULE, Constants.MDMS_SOURCE_OF_FUNDS_CODE_MASTER);
        String recipientSegmentCode = extractCode(mdmsData, Constants.MDMS_SEGMENT_CODES_MODULE, Constants.MDMS_RECIPIENT_SEGMENT_CODES_MASTER);
        String functionCode = extractCode(mdmsData, Constants.MDMS_SEGMENT_CODES_MODULE, Constants.MDMS_FUNCTION_CODES_MASTER);
        String economicSegmentCode = extractCode(mdmsData, Constants.MDMS_SEGMENT_CODES_MODULE, Constants.MDMS_ECONOMIC_SEGMENT_CODES_MASTER);
        String administrativeCode = extractCode(mdmsData, Constants.MDMS_SEGMENT_CODES_MODULE, Constants.MDMS_ADMINISTRATIVE_CODES_MASTER);
        String geographicSegmentCode = extractCode(mdmsData,Constants.MDMS_SEGMENT_CODES_MODULE,Constants.MDMS_GEOGRAPHIC_CODES_MASTER);
        disbursement.setTargetSegmentCode(targetSegmentCode);
        disbursement.setSourceOfFundCode(sourceOfFundsCode);
        disbursement.setRecipientSegmentCode(recipientSegmentCode);
        disbursement.setFunctionCode(functionCode);
        disbursement.setEconomicSegmentCode(economicSegmentCode);
        disbursement.setAdministrationCode(administrativeCode);
        disbursement.setLocaleCode(geographicSegmentCode);
        for(Disbursement disbursement1: disbursement.getDisbursements()){
            disbursement1.setTargetSegmentCode(targetSegmentCode);
            disbursement1.setSourceOfFundCode(sourceOfFundsCode);
            disbursement1.setRecipientSegmentCode(recipientSegmentCode);
            disbursement1.setFunctionCode(functionCode);
            disbursement1.setEconomicSegmentCode(economicSegmentCode);
            disbursement1.setAdministrationCode(administrativeCode);
            disbursement1.setLocaleCode(geographicSegmentCode);
        }
    }

    private String extractCode(Map<String, Map<String, JSONArray>> mdmsData, String moduleName, String masterName) {
        JSONArray data = mdmsData.get(moduleName).get(masterName);
        Random random = new Random();
        int randomIndex = random.nextInt(data.size());
        Map<String, String> segmentCode = (Map<String, String>) data.get(randomIndex);
        return segmentCode.get("code");
    }

    public Disbursement encriptDisbursement(Disbursement disbursement) {
        Disbursement disbursement1 = disbursement;
        disbursement1 = encryptionDecryptionUtil.encryptObject(disbursement1, muktaAdaptorConfig.getStateLevelTenantId(), muktaAdaptorConfig.getMuktaAdapterEncryptionKey(), Disbursement.class);
        HashMap<String,BigDecimal> childIdToNetAmountMap = new HashMap<>();
        HashMap<String, BigDecimal> childIdToGrossAmountMap = new HashMap<>();
        disbursement.getDisbursements().forEach(childDisbursement -> {
            childIdToNetAmountMap.put(childDisbursement.getId(), childDisbursement.getNetAmount());
            childIdToGrossAmountMap.put(childDisbursement.getId(), childDisbursement.getGrossAmount());
        });
        for(Disbursement childDisbursement: disbursement1.getDisbursements()){
            BigDecimal netAmount = childIdToNetAmountMap.get(childDisbursement.getId());
            BigDecimal grossAmount = childIdToGrossAmountMap.get(childDisbursement.getId());
            childDisbursement.setNetAmount(netAmount);
            childDisbursement.setGrossAmount(grossAmount);
        }
        disbursement1.setNetAmount(disbursement.getNetAmount());
        disbursement1.setGrossAmount(disbursement.getGrossAmount());
        return disbursement1;
    }
}
