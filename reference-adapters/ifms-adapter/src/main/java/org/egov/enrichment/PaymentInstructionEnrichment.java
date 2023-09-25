package org.egov.enrichment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.models.individual.Individual;
import org.egov.config.Constants;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.service.IfmsService;
import org.egov.utils.*;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.egov.web.models.organisation.Organisation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.config.Constants.*;

@Service
@Slf4j
public class PaymentInstructionEnrichment {
    @Autowired
    private BillUtils billUtils;
    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private HelperUtil util;
    @Autowired
    private SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    private IdgenUtil idgenUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IfmsAdapterConfig config;
    @Autowired
    private AuditLogUtils auditLogUtils;
    @Autowired
    private BankAccountUtils bankAccountUtils;

    public List<Beneficiary> getBeneficiariesFromBills(List<Bill> billList, PaymentRequest paymentRequest) {
        log.info("Started generating beneficiaries lists for PI");
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        JSONArray headCodesList = billUtils.getHeadCode(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId());

        Map<String, JsonNode> headCodeMap = getHeadCodeHashMap(headCodesList);
        log.info("Generating beneficiary list based on line item.");
        for (Bill bill: billList) {
            for (BillDetail billDetail: bill.getBillDetails()) {
                for (LineItem lineItem: billDetail.getPayableLineItems()) {
                    Beneficiary beneficiary =  getBeneficiariesFromLineItem(lineItem, billDetail.getPayee(), headCodeMap, paymentRequest.getPayment().getTenantId());
                    beneficiaryList.add(beneficiary);
                }
            }
        }
        log.info("Beneficiary list generated, combine them based on bank account number.");
        // Combine beneficiary by beneficiaryId
        beneficiaryList = combineBeneficiaryById(beneficiaryList);
        return beneficiaryList;
    }

    private List<Beneficiary> combineBeneficiaryById(List<Beneficiary> beneficiaryList) {
        log.info("Started executing combineBeneficiaryById");
        Map<String, Beneficiary> benfMap = new HashMap<>();
        for(Beneficiary beneficiary: beneficiaryList) {
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
            List<BenfLineItems> benefLineItemList = new ArrayList<>();
            BenfLineItems benfLineItems = BenfLineItems.builder().lineItemId(lineItem.getId()).build();
            benefLineItemList.add(benfLineItems);
            beneficiary = Beneficiary.builder()
                    .amount(lineItem.getAmount())
                    .beneficiaryId(beneficiaryId)
                    .benfLineItems(benefLineItemList).build();
        }
        log.info("Beneficiary generated and sending back.");
        return beneficiary;
    }
    public PaymentInstruction getEnrichedPaymentRequest(PaymentRequest paymentRequest, List<Beneficiary> beneficiaries, Map<String, Object> hoaSsuMap) {
        // Get the beneficiaries
        BigDecimal totalAmount = new BigDecimal(0);
        if (beneficiaries != null && !beneficiaries.isEmpty()) {
            for (Beneficiary piBeneficiary: beneficiaries) {
                totalAmount = totalAmount.add(piBeneficiary.getAmount());
            }
        }
        JsonNode ssuNode = null, hoaNode = null;
        SanctionDetail selectedSanction = null;
        Boolean hasFunds = true;
        ssuNode = objectMapper.valueToTree(hoaSsuMap.get("ssu"));
        hoaNode = objectMapper.valueToTree(hoaSsuMap.get("hoa"));
        selectedSanction = (SanctionDetail) hoaSsuMap.get("sanction");
        hasFunds = Boolean.parseBoolean(hoaSsuMap.get("hasFunds").toString());
        if (ssuNode == null || hoaNode == null || selectedSanction == null) {
            log.info("Value not find to generate pi request. ssuNode, hoaNode or selectedSanction is null");
            return null;
        }

        String ssuIaId = ssuNode.get("ssuId").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String granteeAgCode = ssuNode.get("granteeAgCode").asText();
        String hoaCode = hoaNode.get("code").asText();
        String schemeCode = hoaNode.get("schemeCode").asText();
        // Sort the list in descending order based on the value
        Collections.sort(selectedSanction.getAllotmentDetails(), Comparator.comparingInt(o -> ((Allotment) o).getAllotmentSerialNo()).reversed());
        String mstAllotmentDistId = selectedSanction.getAllotmentDetails().get(0).getMstAllotmentDistId();
        PIStatus piStatus = hasFunds ? PIStatus.INITIATED : PIStatus.FAILED;
        String jitBillNo = idgenUtil.getIdList(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId(), config.getPaymentInstructionNumberFormat(), null, 1).get(0);
        PaymentInstruction piRequest = PaymentInstruction.builder()
                .id(UUID.randomUUID().toString())
                .jitBillNo(jitBillNo)
//                .jitBillDate(util.getFormattedTimeFromTimestamp(paymentRequest.getPayment().getAuditDetails().getCreatedTime(), VA_REQUEST_TIME_FORMAT))
                .jitBillDdoCode(ddoCode)
                .granteeAgCode(granteeAgCode)
                .schemeCode(schemeCode)
                .hoa(hoaCode)
                .ssuIaId(ssuIaId)
                .mstAllotmentDistId(selectedSanction.getMasterAllotmentId())
                .ssuAllotmentId(selectedSanction.getAllotmentDetails().get(0).getSsuAllotmentId())
                .allotmentTxnSlNo(String.valueOf(selectedSanction.getAllotmentDetails().get(0).getAllotmentSerialNo()))
                .purpose(JIT_FD_EXT_APP_NAME)
                .billGrossAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                .billNetAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                .beneficiaryDetails(beneficiaries)
                .numBeneficiaries(beneficiaries.size())
                .billNumberOfBenf(String.valueOf(beneficiaries.size()))
                .tenantId(paymentRequest.getPayment().getTenantId())
                .grossAmount(totalAmount)
                .netAmount(totalAmount)
                .muktaReferenceId(paymentRequest.getPayment().getPaymentNumber())
                .piStatus(piStatus)
                .build();
        enrichPiRequestForInsert(piRequest, paymentRequest, hasFunds);
        // update piRequest for payment search indexer
        updateBillFieldsForIndexer(piRequest, paymentRequest);
        return piRequest;
    }

    private void enrichPiRequestForInsert(PaymentInstruction piRequest, PaymentRequest paymentRequest, Boolean hasFunds) {
        String userId = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        Long time = System.currentTimeMillis();
        String tenantId = paymentRequest.getPayment().getTenantId();
        String muktaReferenceId = paymentRequest.getPayment().getPaymentNumber();
        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();
        BeneficiaryPaymentStatus beneficiaryPaymentStatus = hasFunds ? BeneficiaryPaymentStatus.INITIATED : BeneficiaryPaymentStatus.PENDING;
        JsonNode emptyObject = objectMapper.createObjectNode();
        if (piRequest.getId() == null)
            piRequest.setId(UUID.randomUUID().toString());
        piRequest.setAdditionalDetails(emptyObject);
        piRequest.setAuditDetails(auditDetails);
        piRequest.setJitBillDate(util.getFormattedTimeFromTimestamp(auditDetails.getCreatedTime(), VA_REQUEST_TIME_FORMAT));

        // Update payment advice details
        PADetails paDetails = PADetails.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .piId(piRequest.getId())
                .muktaReferenceId(muktaReferenceId)
                .additionalDetails(emptyObject)
                .auditDetails(auditDetails)
                .build();
        piRequest.setPaDetails(Collections.singletonList(paDetails));

        // GET IDGEN id for beneficiary
        List<String> benefIdList = idgenUtil.getIdList(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId(), config.getPiBenefInstructionNumberFormat(), null, piRequest.getBeneficiaryDetails().size());

        int idx = 0;
        // Update beneficiary details
        for (Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
            beneficiary.setId(UUID.randomUUID().toString());
            beneficiary.setBeneficiaryNumber(benefIdList.get(idx));
            beneficiary.setTenantId(tenantId);
            beneficiary.setMuktaReferenceId(muktaReferenceId);
            beneficiary.setPiId(piRequest.getId());
            beneficiary.setPaymentStatus(beneficiaryPaymentStatus);
            beneficiary.setAdditionalDetails(emptyObject);
            beneficiary.setAuditDetails(auditDetails);
            for (BenfLineItems lineItem: beneficiary.getBenfLineItems()) {
                lineItem.setId(UUID.randomUUID().toString());
                lineItem.setBeneficiaryId(beneficiary.getId());
                lineItem.setAuditDetails(auditDetails);
            }
            // Increase idx
            idx = idx + 1;
        }
    }

    public void addTransactionDetailsInPiRequest(PaymentInstruction piRequest, PaymentRequest paymentRequest, SanctionDetail sanctionDetail) {
        // Create transaction details and push into piRequest
        String userId = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        String tenantId = paymentRequest.getPayment().getTenantId();
        JsonNode emptyObject = objectMapper.createObjectNode();
        Long time = System.currentTimeMillis();
        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();
        TransactionDetails transactionDetails = TransactionDetails.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .sanctionId(sanctionDetail.getId())
                .paymentInstId(piRequest.getId())
                .transactionAmount(piRequest.getNetAmount())
                .additionalDetails(emptyObject)
                .transactionType(TransactionType.DEBIT)
                .auditDetails(auditDetails)
                .build();
        piRequest.setTransactionDetails(Collections.singletonList(transactionDetails));

    }

    public Map<String, Object> getSanctionSsuAndHOA(PaymentRequest paymentRequest, BigDecimal amount) {
        // GET ssu details
        JSONArray ssuDetailList = ifmsService.getSSUDetails(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId());
        JSONArray hoaList = ifmsService.getHeadOfAccounts(paymentRequest.getRequestInfo());

        hoaList = sortHoaList(hoaList);

        JsonNode ssuNode = null, hoaNode = null;
        SanctionDetail selectedSanction = null;
        Boolean hasFunds = true;
        if (ssuDetailList!= null && !ssuDetailList.isEmpty() && hoaList != null && !hoaList.isEmpty()) {
            for(Object ssu:ssuDetailList) {
                for(Object hoa: hoaList) {
                    if (selectedSanction == null) {
                        ssuNode = objectMapper.valueToTree(ssu);
                        hoaNode = objectMapper.valueToTree(hoa);
                        String ddoCode = ssuNode.get("ddoCode").asText();;
                        String hoaCode = hoaNode.get("code").asText();
                        SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                                .tenantId(paymentRequest.getPayment().getTenantId())
                                .ddoCode(ddoCode)
                                .hoaCode(hoaCode)
                                .build();
                        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
                        for (SanctionDetail sanctionDetail: sanctionDetails) {
                            if (sanctionDetail.getFundsSummary().getAvailableAmount().compareTo(amount) >= 0) {
                                selectedSanction = sanctionDetail;
                                break;
                            }
                        }
                    }

                }
            }
            if (selectedSanction == null) {
                hasFunds = false;
                ssuNode = objectMapper.valueToTree(ssuDetailList.get(0));
                hoaNode = objectMapper.valueToTree(hoaList.get(0));
                String ddoCode = ssuNode.get("ddoCode").asText();;
                String hoaCode = hoaNode.get("code").asText();
                SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                        .tenantId(paymentRequest.getPayment().getTenantId())
                        .ddoCode(ddoCode)
                        .hoaCode(hoaCode)
                        .build();
                List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
                if (!sanctionDetails.isEmpty()) {
                    selectedSanction = sanctionDetails.get(0);
                }
            }
        }

        if (selectedSanction != null) {
            // Filter TRANSACTION TYPE WITHDRAWAL from selected sanction allotment
            List<Allotment> allotments = selectedSanction.getAllotmentDetails().stream()
                    .filter(allotment -> !allotment.getAllotmentTxnType().equalsIgnoreCase(VA_TRANSACTION_TYPE_WITHDRAWAL))
                    .collect(Collectors.toList());
            selectedSanction.setAllotmentDetails(allotments);
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("ssu", ssuNode);
        objectMap.put("hoa", hoaNode);
        objectMap.put("sanction", selectedSanction);
        objectMap.put("hasFunds", hasFunds);
        return objectMap;
    }

    private JSONArray sortHoaList(JSONArray hoaList) {
        try {
            if (hoaList != null && !hoaList.isEmpty()) {
                hoaList.sort((o1, o2) -> {
                    JsonNode node1 = objectMapper.valueToTree(o1);
                    JsonNode node2 = objectMapper.valueToTree(o2);

                    int key1 = node1.get("sequence").asInt();
                    int key2 = node2.get("sequence").asInt();
                    return Integer.compare(key1, key2);
                });
            }
        }catch (Exception e) {
            log.error("Exception in PaymentInstructionEnrichment:sortHoaList : " + e);
        }
        return hoaList;
    }

    private List<Beneficiary> getBeneficiaryListFromBill(Bill bill) {
        List<Beneficiary>  piBeneficiaryList = new ArrayList<>();
        for (BillDetail billDetail: bill.getBillDetails()) {
            for (LineItem lineItem: billDetail.getPayableLineItems()) {
                if (lineItem.getStatus().equals(Status.ACTIVE)) {
                    Beneficiary piBeneficiary = Beneficiary.builder()
                            .benefId(billDetail.getPayee().getIdentifier())
                            .benfAmount(lineItem.getAmount().toString())
                            .build();
                    piBeneficiaryList.add(piBeneficiary);
                }
            }
        }
        return piBeneficiaryList;
    }

    public void enrichBankaccountOnBeneficiary(List<Beneficiary> beneficiaryList, List<BankAccount> bankAccounts, List<Individual> individuals, List<Organisation> organisations) {
        log.info("Started executing enrichBankaccountOnBeneficiary");
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
        log.info("Created map of org, individual and bankaccount, started generating beneficiary.");
        for(Beneficiary piBeneficiary: beneficiaryList) {
            BankAccount bankAccount = bankAccountMap.get(piBeneficiary.getBeneficiaryId());
            if (bankAccount != null) {
                piBeneficiary.setBenefName(bankAccount.getBankAccountDetails().get(0).getAccountHolderName());
                piBeneficiary.setBenfAcctNo(bankAccount.getBankAccountDetails().get(0).getAccountNumber());
                piBeneficiary.setBenfBankIfscCode(bankAccount.getBankAccountDetails().get(0).getBankBranchIdentifier().getCode());
                piBeneficiary.setBenfAccountType(bankAccount.getBankAccountDetails().get(0).getAccountType());
                piBeneficiary.setBankAccountId(bankAccount.getBankAccountDetails().get(0).getId());
            }

            Individual individual = individualMap.get(piBeneficiary.getBeneficiaryId());
            Organisation organisation = organisationMap.get(piBeneficiary.getBeneficiaryId());

            if (individual != null) {
                piBeneficiary.setBeneficiaryType(BeneficiaryType.IND);
                piBeneficiary.setBenfMobileNo(individual.getMobileNumber());
                piBeneficiary.setBenfAddress(individual.getAddress().get(0).getWard().getCode() + " " +individual.getAddress().get(0).getLocality().getCode());
            } else if (organisation != null) {
                piBeneficiary.setBeneficiaryType(BeneficiaryType.ORG);
                piBeneficiary.setBenfMobileNo(organisation.getContactDetails().get(0).getContactMobileNumber());
                piBeneficiary.setBenfAddress(organisation.getOrgAddress().get(0).getBoundaryCode() + " " +organisation.getOrgAddress().get(0).getCity());
            } else {
                piBeneficiary.setBeneficiaryType(BeneficiaryType.DEPT);
                piBeneficiary.setBenfMobileNo("9999999999");
                piBeneficiary.setBenfAddress("Temp address");
            }
            piBeneficiary.setPurpose("Mukta Payment");
        }
        log.info("Beneficiary details enriched and sending back.");

    }

    public JITRequest getJitPaymentInstructionRequestForIFMS(PaymentInstruction existingPI) {
        log.info("Started executing getJitPaymentInstructionRequestForIFMS");
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        for(Beneficiary beneficiary: existingPI.getBeneficiaryDetails()) {
            Beneficiary reqBeneficiary = Beneficiary.builder()
                    .benefId(beneficiary.getBeneficiaryNumber())
                    .benefName(beneficiary.getBenefName())
                    .benfAcctNo(beneficiary.getBenfAcctNo())
                    .benfBankIfscCode(beneficiary.getBenfBankIfscCode())
                    .benfMobileNo(beneficiary.getBenfMobileNo())
                    .benfEmailId("")
                    .benfAddress(beneficiary.getBenfAddress())
                    .benfAccountType(beneficiary.getBenfAccountType())
                    .benfAmount(beneficiary.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                    .purpose("Mukta Payment")
                    .build();
            beneficiaryList.add(reqBeneficiary);
        }
        PaymentInstruction piRequestForIFMS = PaymentInstruction.builder()
                .jitBillNo(existingPI.getJitBillNo())
                .jitBillDate(existingPI.getJitBillDate())
                .jitBillDdoCode(existingPI.getJitBillDdoCode())
                .granteeAgCode(existingPI.getGranteeAgCode())
                .schemeCode(existingPI.getSchemeCode())
                .hoa(existingPI.getHoa())
                .ssuIaId(existingPI.getSsuIaId())
                .mstAllotmentDistId(existingPI.getMstAllotmentDistId())
                .ssuAllotmentId(existingPI.getSsuAllotmentId())
                .allotmentTxnSlNo(existingPI.getAllotmentTxnSlNo())
                .billGrossAmount(existingPI.getBillGrossAmount())
                .billNetAmount(existingPI.getBillNetAmount())
                .billNumberOfBenf(String.valueOf(beneficiaryList.size()))
                .purpose(existingPI.getPurpose())
                .beneficiaryDetails(beneficiaryList)
                .build();

        JITRequest jitPiRequest = JITRequest.builder()
                .serviceId(JITServiceId.PI)
                .params(piRequestForIFMS)
                .build();
        log.info("JIT PI request generated and sending back.");
        return jitPiRequest;
    }

    public JITRequest getCorRequest(PaymentRequest paymentRequest, PaymentInstruction paymentInstruction, PaymentInstruction originalPi, PaymentInstruction lastRevisedPi) throws Exception {
        List<CORBeneficiaryDetails> corBenfDetails = new ArrayList<>();
        List<String> beneficiaryIds = paymentInstruction.getBeneficiaryDetails().stream()
                .map(Beneficiary::getBeneficiaryId)
                .distinct()
                .collect(Collectors.toList());
        List<BankAccount> bankAccounts = bankAccountUtils.getBankAccountsByIdentifier(paymentRequest.getRequestInfo(), beneficiaryIds, paymentRequest.getPayment().getTenantId());
        Map<String, BankAccount> bankAccountMap = new HashMap<>();
        if (bankAccounts != null && !bankAccounts.isEmpty()) {
            for(BankAccount bankAccount: bankAccounts) {
                bankAccountMap.put(bankAccount.getReferenceId(), bankAccount);
            }
        }

        for (Beneficiary beneficiary: paymentInstruction.getBeneficiaryDetails()) {
            Map<String, String> originalPiBankDetails = auditLogUtils.getLastUpdatedBankAccountDetailsFromAuditLogFromTime(paymentRequest, bankAccountMap.get(beneficiary.getBeneficiaryId()), originalPi.getAuditDetails().getCreatedTime());
            String originalPiBankAccountNo = originalPiBankDetails.get("bankAccountNumber");
            String originalPiIFSC = originalPiBankDetails.get("bankIFSCCode");

            // Get bank account details by beneficiary ids
            CORBeneficiaryDetails corBeneficiaryDetails = CORBeneficiaryDetails.builder()
                    .benefId(beneficiary.getBeneficiaryNumber())
                    // Latest failed transaction bill ref number
                    .jitCurBillRefNo(lastRevisedPi != null ? lastRevisedPi.getPaDetails().get(0).getPaBillRefNumber() : originalPi.getPaDetails().get(0).getPaBillRefNumber())
                    // Original account number of first time failure.
                    .orgAccountNo(originalPiBankAccountNo)
                    // Original IFSC of first time failure.
                    .orgIfsc(originalPiIFSC)
                    // Recent corrected account number
                    .correctedAccountNo(beneficiary.getBenfAcctNo())
                    // Recent corrected IFSC
                    .correctedIfsc(beneficiary.getBenfBankIfscCode())
                    .build();

            if (lastRevisedPi != null) {
                Map<String, String> lastPiBankDetails = auditLogUtils.getLastUpdatedBankAccountDetailsFromAuditLogFromTime(paymentRequest, bankAccountMap.get(beneficiary.getBeneficiaryId()), lastRevisedPi.getAuditDetails().getCreatedTime());
                String lastPiBankAccountNo = lastPiBankDetails.get("bankAccountNumber");
                String lastPiIFSC = lastPiBankDetails.get("bankIFSCCode");
                // Latest failed transaction account number
                corBeneficiaryDetails.setCurAccountNo(lastPiBankAccountNo);
                // Latest failed transaction IFSC
                corBeneficiaryDetails.setCurIfsc(lastPiIFSC);
            } else {
                // Latest failed transaction account number
                corBeneficiaryDetails.setCurAccountNo(originalPiBankAccountNo);
                // Latest failed transaction IFSC
                corBeneficiaryDetails.setCurIfsc(originalPiIFSC);
            }
            corBenfDetails.add(corBeneficiaryDetails);
        }

        CORRequest corRequest = CORRequest.builder()
                .jitCorBillNo(paymentInstruction.getJitBillNo())
                .jitCorBillDate(util.getFormattedTimeFromTimestamp(paymentInstruction.getAuditDetails().getCreatedTime(), VA_REQUEST_TIME_FORMAT))
                // External department Application name for example ‘MUKTA’
                .jitCorBillDeptCode(JIT_FD_EXT_APP_NAME)
                // Original bill reference number while payment failed first time.
                .jitOrgBillRefNo(originalPi.getPaDetails().get(0).getPaBillRefNumber())
                // Payment Instruction bill no while payment failed first time.
                .jitOrgBillNo(originalPi.getJitBillNo())
                // Payment Instruction bill date while payment failed first time.
                .jitOrgBillDate(util.getFormattedTimeFromTimestamp(originalPi.getAuditDetails().getCreatedTime(), VA_REQUEST_TIME_FORMAT))
                .beneficiaryDtls(corBenfDetails)
                .build();
        JITRequest jitPiRequest = JITRequest.builder()
                .serviceId(JITServiceId.COR)
                .params(corRequest)
                .build();

        return jitPiRequest;
    }


    private Map<String, JsonNode> getHeadCodeHashMap(JSONArray headCodesList) {
        Map<String, JsonNode> headCodeMap = new HashMap<>();
        for (Object headcode: headCodesList) {
            JsonNode node = objectMapper.valueToTree(headcode);
            headCodeMap.put(node.get("code").asText(), node);
        }
        return headCodeMap;
    }

    private void updateBillFieldsForIndexer(PaymentInstruction paymentInstruction, PaymentRequest paymentRequest) {
        try {
            // Get the list of bills based on payment request
            List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);
            Map<String, Object>  additionalDetails = new HashMap<>();
            List<String> billNumber = new ArrayList<>();
            List<String> referenceId = new ArrayList<>();
            if (billList != null && !billList.isEmpty()) {
                for (Bill bill: billList) {
                    billNumber.add(bill.getBillNumber());
                }
            }
            billNumber = billNumber.stream().distinct().collect(Collectors.toList());
            Object billCalculatorResponse =  billUtils.fetchBillFromCalculator(paymentRequest, billNumber);
            JsonNode node = objectMapper.valueToTree(billCalculatorResponse);
            JsonNode billsNode= node.get("bills");
            if (billsNode.isArray()) {
                for (JsonNode bill : billsNode) {
                    referenceId.add(bill.get("contractNumber").asText());
                }
            }
            // Convert the array to a Set to get distinct elements
            referenceId = referenceId.stream().distinct().collect(Collectors.toList());
            additionalDetails.put("billNumber", billNumber);
            additionalDetails.put("referenceId", referenceId);
            paymentInstruction.setAdditionalDetails(additionalDetails);
        } catch (Exception e) {
            log.error("Exception in PaymentInstructionEnrichment:updateBillFieldsForIndexer : " + e);
        }
    }

    /**
     * Get failed beneficiary list from existing PI with line items
     * @param existingPI
     * @return list of beneficiaries
     */
    public List<Beneficiary> getFailedBeneficiariesFromExistingPI(PaymentInstruction existingPI) {
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        if (existingPI != null && !existingPI.getBeneficiaryDetails().isEmpty()) {
            for (Beneficiary beneficiary: existingPI.getBeneficiaryDetails()) {
                if (beneficiary.getPaymentStatus().equals(BeneficiaryPaymentStatus.FAILED)) {
                    List<BenfLineItems> benfLineItems = new ArrayList<>();
                    for (BenfLineItems lineItems: beneficiary.getBenfLineItems()) {
                        BenfLineItems benfLineItem = BenfLineItems.builder()
                                .lineItemId(lineItems.getLineItemId())
                                .build();
                        benfLineItems.add(benfLineItem);
                    }
                    Beneficiary newBenef = Beneficiary.builder()
                            .tenantId(beneficiary.getTenantId())
                            .muktaReferenceId(beneficiary.getMuktaReferenceId())
                            .beneficiaryId(beneficiary.getBeneficiaryId())
                            .beneficiaryNumber(beneficiary.getBeneficiaryNumber())
                            .beneficiaryType(beneficiary.getBeneficiaryType())
                            .amount(beneficiary.getAmount())
                            .benfLineItems(benfLineItems)
                            .build();
                    beneficiaryList.add(newBenef);
                }
            }
        }
        return beneficiaryList;
    }

    public PaymentInstruction getRevisedEnrichedPaymentRequest(PaymentRequest paymentRequest, PaymentInstruction existingPi, List<Beneficiary> beneficiaries) {

        BigDecimal totalAmount = new BigDecimal(0);
        if (beneficiaries != null && !beneficiaries.isEmpty()) {
            for (Beneficiary piBeneficiary: beneficiaries) {
                totalAmount = totalAmount.add(piBeneficiary.getAmount());
            }
        }

        String userId = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        Long time = System.currentTimeMillis();
        String tenantId = existingPi.getTenantId();
        String muktaReferenceId = existingPi.getMuktaReferenceId();
        JsonNode emptyObject = objectMapper.createObjectNode();
        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();

        String jitBillNo = idgenUtil.getIdList(paymentRequest.getRequestInfo(), tenantId, config.getPaymentInstructionNumberFormat(), null, 1).get(0);
        PaymentInstruction paymentInstruction = PaymentInstruction.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .jitBillNo(jitBillNo)
                .jitBillDate(util.getFormattedTimeFromTimestamp(time, JIT_BILL_DATE_FORMAT))
                .parentPiNumber(existingPi.getJitBillNo())
                .muktaReferenceId(muktaReferenceId)
                .numBeneficiaries(beneficiaries.size())
                .grossAmount(totalAmount)
                .netAmount(totalAmount)
                .piStatus(PIStatus.INITIATED)
                .auditDetails(auditDetails)
                .additionalDetails(emptyObject)
                .build();

        // Add payment advise details
        List<PADetails> paDetails = new ArrayList<>();
        PADetails paDetail = PADetails.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .muktaReferenceId(muktaReferenceId)
                .piId(paymentInstruction.getId())
                .additionalDetails(emptyObject)
                .auditDetails(auditDetails)
                .build();
        paDetails.add(paDetail);
        paymentInstruction.setPaDetails(paDetails);

        // GET IDGEN id for beneficiary
        // List<String> benefIdList = idgenUtil.getIdList(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId(), config.getPiBenefInstructionNumberFormat(), null, beneficiaries.size());
        int idx = 0;
        for (Beneficiary beneficiary: beneficiaries) {
            beneficiary.setId(UUID.randomUUID().toString());
            beneficiary.setBeneficiaryNumber(beneficiary.getBeneficiaryNumber());
            beneficiary.setTenantId(tenantId);
            beneficiary.setMuktaReferenceId(muktaReferenceId);
            beneficiary.setPiId(paymentInstruction.getId());
            beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.INITIATED);
            beneficiary.setAdditionalDetails(emptyObject);
            beneficiary.setAuditDetails(auditDetails);
            for (BenfLineItems lineItem: beneficiary.getBenfLineItems()) {
                lineItem.setId(UUID.randomUUID().toString());
                lineItem.setBeneficiaryId(beneficiary.getId());
                lineItem.setAuditDetails(auditDetails);
            }
            // Increase idx
            idx = idx + 1;
        }
        paymentInstruction.setBeneficiaryDetails(beneficiaries);
        // update piRequest for payment search indexer
        updateBillFieldsForIndexer(paymentInstruction, paymentRequest);
        return paymentInstruction;
    }

    public List<Beneficiary> groupBeneficiariesByAccountNumberIfsc(List<Beneficiary> beneficiaries) {
        Map<String, Beneficiary> newBeneficiaryMap = new HashMap<>();
        for (Beneficiary beneficiary: beneficiaries) {
            String key = beneficiary.getBenfAcctNo() + "_" + beneficiary.getBenfBankIfscCode();
            if (newBeneficiaryMap.containsKey(key)){
                newBeneficiaryMap.get(key).getBenfLineItems().addAll(beneficiary.getBenfLineItems());
                newBeneficiaryMap.get(key).setAmount(newBeneficiaryMap.get(key).getAmount().add(beneficiary.getAmount()));
            } else {
                newBeneficiaryMap.put(key, beneficiary);
            }
        }
        List<Beneficiary> newBeneficiaryList = new ArrayList<>(newBeneficiaryMap.values());
        return newBeneficiaryList;
    }

    public PaymentInstruction getEnrichedPaymentInstructionForNoFunds(PaymentRequest paymentRequest, List<Beneficiary> beneficiaries) {
        // Get the beneficiaries
        BigDecimal totalAmount = new BigDecimal(0);
        if (beneficiaries != null && !beneficiaries.isEmpty()) {
            for (Beneficiary piBeneficiary: beneficiaries) {
                totalAmount = totalAmount.add(piBeneficiary.getAmount());
            }
        }
        SanctionDetail selectedSanction = null;
        Boolean hasFunds = true;

        // Sort the list in descending order based on the value
        PIStatus piStatus = PIStatus.FAILED;
        String jitBillNo = idgenUtil.getIdList(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId(), config.getPaymentInstructionNumberFormat(), null, 1).get(0);
        PaymentInstruction piRequest = PaymentInstruction.builder()
                .id(UUID.randomUUID().toString())
                .jitBillNo(jitBillNo)
                .purpose(JIT_FD_EXT_APP_NAME)
                .billGrossAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                .billNetAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                .beneficiaryDetails(beneficiaries)
                .numBeneficiaries(beneficiaries.size())
                .billNumberOfBenf(String.valueOf(beneficiaries.size()))
                .tenantId(paymentRequest.getPayment().getTenantId())
                .grossAmount(totalAmount)
                .netAmount(totalAmount)
                .muktaReferenceId(paymentRequest.getPayment().getPaymentNumber())
                .piStatus(piStatus)
                .isActive(false)
                .build();
        enrichPiRequestForInsert(piRequest, paymentRequest, false);
        // update piRequest for payment search indexer
        updateBillFieldsForIndexer(piRequest, paymentRequest);
        return piRequest;
    }

}
