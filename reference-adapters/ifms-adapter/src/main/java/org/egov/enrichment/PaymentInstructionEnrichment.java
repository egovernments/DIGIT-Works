package org.egov.enrichment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.models.individual.Individual;
import org.egov.config.Constants;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.service.IfmsService;
import org.egov.utils.BillUtils;
import org.egov.utils.HelperUtil;
import org.egov.utils.IdgenUtil;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.egov.config.Constants.VA_REQUEST_TIME_FORMAT;

@Service
@Slf4j
public class PaymentInstructionEnrichment {
    @Autowired
    BillUtils billUtils;
    @Autowired
    IfmsService ifmsService;
    @Autowired
    HelperUtil util;
    @Autowired
    SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    IdgenUtil idgenUtil;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    IfmsAdapterConfig config;

    public List<Beneficiary> getBeneficiariesFromBills(List<Bill> billList, PaymentRequest paymentRequest) {
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        JSONArray headCodesList = billUtils.getHeadCode(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId());

        Map<String, JsonNode> headCodeMap = getHeadCodeHashMap(headCodesList);
        for (Bill bill: billList) {
            for (BillDetail billDetail: bill.getBillDetails()) {
                for (LineItem lineItem: billDetail.getPayableLineItems()) {
                    Beneficiary beneficiary =  getBeneficiariesFromLineItem(lineItem, billDetail.getPayee(), headCodeMap, paymentRequest.getPayment().getTenantId());
                    beneficiaryList.add(beneficiary);
                }
            }
        }
        // Combine beneficiary by beneficiaryId
        combineBeneficiaryById(beneficiaryList);
        return beneficiaryList;
    }
    private void combineBeneficiaryById(List<Beneficiary> beneficiaryList) {
        Map<String, Beneficiary> benfMap = new HashMap<>();
        for(Beneficiary beneficiary: beneficiaryList) {
            if (benfMap.containsKey(beneficiary.getBeneficiaryId())) {
                Beneficiary benf = benfMap.get(beneficiary.getBeneficiaryId());
                List<BenfLineItems> combinedList = new ArrayList<>();
                combinedList.addAll(beneficiary.getBenfLineItems());
                combinedList.addAll(benf.getBenfLineItems());
                benf.setBenfLineItems(combinedList);
            } else {
                benfMap.put(beneficiary.getBeneficiaryId(), beneficiary);
            }
        }
    }

    private Beneficiary getBeneficiariesFromLineItem(LineItem lineItem, Party payee, Map<String, JsonNode> headCodeMap, String tenantId) {
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
        String piStatus = String.valueOf(hasFunds ? PIStatus.INITIATED : PaymentStatus.PENDING);
//        String jitBillNo = idgenUtil.getIdList(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId(), config.getPaymentInstructionNumberFormat(), null, 1).get(0);
        String jitBillNo = "Demo";
        PaymentInstruction piRequest = PaymentInstruction.builder()
                .id(UUID.randomUUID().toString())
                .jitBillNo(jitBillNo)
                .jitBillDate(util.getFormattedTimeFromTimestamp(paymentRequest.getPayment().getAuditDetails().getCreatedTime(), VA_REQUEST_TIME_FORMAT))
                .jitBillDdoCode(ddoCode)
                .granteeAgCode(granteeAgCode)
                .schemeCode(schemeCode)
                .hoa(hoaCode)
                .ssuIaId(ssuIaId)
                .mstAllotmentDistId(selectedSanction.getMasterAllotmentId())
                .ssuAllotmentId(selectedSanction.getAllotmentDetails().get(0).getSsuAllotmentId())
                .allotmentTxnSlNo(String.valueOf(selectedSanction.getAllotmentDetails().get(0).getAllotmentSerialNo()))
                .purpose("Mukta")
                .billGrossAmount(totalAmount.toString())
                .billNetAmount(totalAmount.toString())
                .beneficiaryDetails(beneficiaries)
                .numBeneficiaries(beneficiaries.size())
                .billNumberOfBenf(String.valueOf(beneficiaries.size()))
                .tenantId(paymentRequest.getPayment().getTenantId())
                .grossAmount(totalAmount)
                .netAmount(totalAmount)
                .muktaReferenceId(paymentRequest.getPayment().getPaymentNumber())
                .piStatus(piStatus)
                .build();
        enrichPiRequestForInsert(piRequest, paymentRequest, selectedSanction, hasFunds);
        return piRequest;
    }

    private void enrichPiRequestForInsert(PaymentInstruction piRequest, PaymentRequest paymentRequest, SanctionDetail sanctionDetail, Boolean hasFunds) {
        String userId = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        Long time = System.currentTimeMillis();
        String tenantId = paymentRequest.getPayment().getTenantId();
        String muktaReferenceId = paymentRequest.getPayment().getPaymentNumber();
        AuditDetails auditDetails = AuditDetails.builder().createdBy(userId).createdTime(time).lastModifiedBy(userId).lastModifiedTime(time).build();
        PaymentStatus beneficiaryPaymentStatus = hasFunds ? PaymentStatus.INITIATED : PaymentStatus.PENDING;
        JsonNode emptyObject = objectMapper.createObjectNode();
        if (piRequest.getId() == null)
            piRequest.setId(UUID.randomUUID().toString());
        piRequest.setAdditionalDetails(emptyObject);
        piRequest.setAuditDetails(auditDetails);
        // Create transaction details and push i
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

        // Update beneficiary details
        for (Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
            beneficiary.setId(UUID.randomUUID().toString());
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
        }

    }

    public Map<String, Object> getSanctionSsuAndHOA(PaymentRequest paymentRequest, BigDecimal amount) {
        // GET ssu details
        JSONArray ssuDetailList = ifmsService.getSSUDetails(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId());
        JSONArray hoaList = ifmsService.getHeadOfAccounts(paymentRequest.getRequestInfo());
        // TODO: Sort hoa list based on sequence
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
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("ssu", ssuNode);
        objectMap.put("hoa", hoaNode);
        objectMap.put("sanction", selectedSanction);
        objectMap.put("sanction", selectedSanction);
        objectMap.put("hasFunds", hasFunds);
        return objectMap;
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
        for(Beneficiary piBeneficiary: beneficiaryList) {
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
            } else {
                piBeneficiary.setBenfMobileNo("9999999999");
                piBeneficiary.setBenfAddress("Temp address");
            }
            piBeneficiary.setPurpose("Mukta Payment");
        }
    }

    public JITRequest getJitPaymentInstructionRequestForIFMS(PaymentInstruction existingPI) {

        List<Beneficiary> beneficiaryList = new ArrayList<>();
        for(Beneficiary beneficiary: existingPI.getBeneficiaryDetails()) {
            Beneficiary reqBeneficiary = Beneficiary.builder()
                    .benefId(beneficiary.getId())
                    .benefName(beneficiary.getBenefName())
                    .benfAcctNo(beneficiary.getBenfAcctNo())
                    .benfBankIfscCode(beneficiary.getBenfBankIfscCode())
                    .benfMobileNo(beneficiary.getBenfMobileNo())
                    .benfEmailId("")
                    .benfAddress(beneficiary.getBenfAddress())
                    .benfAccountType(beneficiary.getBenfAccountType())
                    .benfAmount(beneficiary.getAmount().toString())
                    .panNo("")
                    .adhaarNumber("")
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


}
