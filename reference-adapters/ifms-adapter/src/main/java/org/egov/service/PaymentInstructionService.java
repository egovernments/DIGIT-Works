package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.egov.common.models.individual.Individual;
import org.egov.config.Constants;
import org.egov.enrichment.PaymentInstructionEnrichment;
import org.egov.enrichment.VirtualAllotmentEnrichment;
import org.egov.repository.PIRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.*;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bill.*;
import org.egov.web.models.enums.*;
import org.egov.web.models.jit.*;
import org.egov.web.models.jit.PISearchCriteria;
import org.egov.web.models.organisation.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
    private BillUtils billUtils;
    @Autowired
    private BankAccountUtils bankAccountUtils;
    @Autowired
    private IndividualUtils individualUtils;
    @Autowired
    private OrganisationUtils organisationUtils;
    @Autowired
    private PaymentInstructionEnrichment piEnrichment;
    @Autowired
    private IfmsService ifmsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PIRepository piRepository;
    @Autowired
    private PIUtils piUtils;
    @Autowired
    private SanctionDetailsRepository sanctionDetailsRepository;

    @Autowired
    VirtualAllotmentEnrichment virtualAllotmentEnrichment;

    public PaymentInstruction processPaymentRequest(PaymentRequest paymentRequest) {
        PaymentInstruction paymentInstruction = null;
        if (paymentRequest.getReferenceId() != null && !paymentRequest.getReferenceId().isEmpty() && paymentRequest.getTenantId() != null && !paymentRequest.getTenantId().isEmpty()) {
            // GET payment details
            List<Payment> payments = billUtils.fetchPaymentDetails(paymentRequest.getRequestInfo(),
                    Collections.singleton(paymentRequest.getReferenceId()),
                    paymentRequest.getTenantId());
            if (!payments.isEmpty()) {
                boolean createNewPi = isPaymentValidForCreateNewPI(payments.get(0));
                if (createNewPi) {
                    paymentRequest.setPayment(payments.get(0));
                    paymentInstruction = processPaymentRequestForNewPI(paymentRequest);
                } else {
                    throw new RuntimeException("PI is already created for Payment Advice "+ paymentRequest.getReferenceId());
                }
            } else {
                throw new RuntimeException("No Payment Advice exists of payment number : "+ paymentRequest.getReferenceId() + " for tenantId : " + paymentRequest.getTenantId());
            }
        } else if (paymentRequest.getParentPI() != null) {
            Map<String, Object>  createRevisedPiMap = canCreateRevisedPi(paymentRequest);
            boolean createRevisedPi = Boolean.parseBoolean(createRevisedPiMap.get("createRevisedPi").toString());
            // Original PI
            PaymentInstruction originalPi = (PaymentInstruction) createRevisedPiMap.get("originalPi");
            // Revised PI
            PaymentInstruction lastRevisedPi = (PaymentInstruction) createRevisedPiMap.get("lastRevisedPi");
            if (createRevisedPi) {
                List<Payment> payments = billUtils.fetchPaymentDetails(paymentRequest.getRequestInfo(),
                        Collections.singleton(originalPi.getMuktaReferenceId()),
                        paymentRequest.getTenantId());
                if (payments != null && !payments.isEmpty()) {
                    paymentRequest.setPayment(payments.get(0));
                    paymentInstruction = processPaymentRequestForRevisedPI(paymentRequest, originalPi, lastRevisedPi);
                } else {
                    throw new RuntimeException("Failed to fetch payment details for PI : "+ paymentRequest.getParentPI());
                }
            } else {
                throw new RuntimeException("Revised PI can not be generated for PI : "+ paymentRequest.getParentPI());
            }
        } else if (paymentRequest.getPayment() != null) {
            paymentInstruction = processPaymentRequestForNewPI(paymentRequest);
        } else {
            throw new RuntimeException("Enter valid parameters : referenceId, tenantId, parentPI");
        }
        return paymentInstruction;
    }

    public PaymentInstruction processPaymentRequestForNewPI(PaymentRequest paymentRequest) {
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
                }
                // Set beneficiary and pi status to failed if payment is failed
                if (paymentStatus.equals(PaymentStatus.FAILED)) {
                    piRequest.setPiStatus(PIStatus.FAILED);
                    piRequest.setIsActive(false);
                    for(Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
                        beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.PENDING);
                    }
                }
                // IF pi is initiated then add transaction records
                if (paymentStatus.equals(PaymentStatus.INITIATED)) {
                    piEnrichment.addTransactionDetailsInPiRequest(piRequest, paymentRequest, selectedSanction);
                    // update fund summary amount
                    selectedSanction.getFundsSummary().setAvailableAmount(selectedSanction.getFundsSummary().getAvailableAmount().subtract(totalAmount));
                    selectedSanction.getFundsSummary().getAuditDetails().setLastModifiedTime(piRequest.getAuditDetails().getLastModifiedTime());
                    selectedSanction.getFundsSummary().getAuditDetails().setLastModifiedBy(piRequest.getAuditDetails().getLastModifiedBy());
                    piRequest.setIsActive(true);
                }
                piRepository.save(Collections.singletonList(piRequest), selectedSanction.getFundsSummary(), paymentStatus);
                piUtils.updatePiForIndexer(paymentRequest.getRequestInfo(), piRequest);
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


    public PaymentInstruction processPaymentRequestForRevisedPI(PaymentRequest paymentRequest, PaymentInstruction originalPi, PaymentInstruction lastRevisedPi) {
        PaymentInstruction paymentInstruction = null;
        PaymentStatus paymentStatus = null;
        try {
            // Get the beneficiaries
            List<Beneficiary> beneficiaries = getBeneficiariesForRevisedPayment(paymentRequest, originalPi, lastRevisedPi);
            BigDecimal totalAmount = new BigDecimal(0);
            if (beneficiaries != null && !beneficiaries.isEmpty()) {
                for (Beneficiary piBeneficiary: beneficiaries) {
                    totalAmount = totalAmount.add(piBeneficiary.getAmount());
                }
            }

            // Get enriched PI request to store on DB
            paymentInstruction = piEnrichment.getRevisedEnrichedPaymentRequest(paymentRequest, originalPi, beneficiaries);
            // GET failure pi JIT request data from PI
            JITRequest jitPiRequest = piEnrichment.getCorRequest(paymentRequest, paymentInstruction, originalPi, lastRevisedPi);
            try {
                JITResponse jitResponse = ifmsService.sendRequestToIFMS(jitPiRequest);
                // JITResponse jitResponse = ifmsService.loadCustomResponse();
                if (jitResponse.getErrorMsg() == null && !jitResponse.getData().isEmpty()) {
                    paymentStatus = PaymentStatus.INITIATED;
                    Object piResponseNode = jitResponse.getData().get(0);
                    JsonNode node = objectMapper.valueToTree(piResponseNode);
                    String piSuccessCode = node.get("successCode").asText();
                    String piSucessDescrp = node.get("sucessDescrp").asText();
                    paymentInstruction.setPiSuccessCode(piSuccessCode);
                    paymentInstruction.setPiSuccessDesc(piSucessDescrp);
                } else {
                    Object piResponseNode = jitResponse.getErrorMsgs().get(0);
                    JsonNode node = objectMapper.valueToTree(piResponseNode);
                    String piErrorCode = node.get("errorCode").asText();
                    String piErrorDescrp = node.get("errorMsg").asText();
                    paymentStatus = PaymentStatus.PARTIAL;
                    paymentInstruction.setPiErrorResp(piErrorDescrp);
                }
            } catch (Exception e) {
                paymentStatus = PaymentStatus.PARTIAL;
                String errorMessage = e.toString();
                errorMessage = e.getMessage();
                Throwable cause = e.getCause();
                if (cause instanceof HttpServerErrorException.InternalServerError) {
                    errorMessage = ((HttpServerErrorException.InternalServerError) cause).getResponseBodyAsString();
                } else {
                    errorMessage = e.getMessage();
                }
                log.error("Exception while calling request." + e);
                paymentInstruction.setPiErrorResp(errorMessage);
            }
            savePIData(paymentRequest, paymentInstruction, originalPi, lastRevisedPi, paymentStatus);

        } catch (Exception e) {
            log.info("Exception " + e);
            throw new RuntimeException("Exception while generating revised PI" + e);
        }

        return paymentInstruction;
    }

    private void savePIData(PaymentRequest paymentRequest, PaymentInstruction pi, PaymentInstruction originalPI, PaymentInstruction lastPI, PaymentStatus paymentStatus) {
        // IF pi is initiated then add transaction records
        SanctionDetailsSearchCriteria searchCriteria = SanctionDetailsSearchCriteria.builder()
                .ids(Collections.singletonList(originalPI.getTransactionDetails().get(0).getSanctionId()))
                .build();
        SanctionDetail sanctionDetail = sanctionDetailsRepository.getSanctionDetails(searchCriteria).get(0);

        if (paymentStatus.equals(PaymentStatus.INITIATED)) {
            piEnrichment.addTransactionDetailsInPiRequest(pi, paymentRequest, sanctionDetail);
            // update fund summary amount
            sanctionDetail.getFundsSummary().setAvailableAmount(sanctionDetail.getFundsSummary().getAvailableAmount().subtract(pi.getNetAmount()));
            sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedTime(pi.getAuditDetails().getLastModifiedTime());
            sanctionDetail.getFundsSummary().getAuditDetails().setLastModifiedBy(pi.getAuditDetails().getLastModifiedBy());
            pi.setIsActive(true);
        } else if (paymentStatus.equals(PaymentStatus.PARTIAL)) {
            // Set beneficiary and pi status to failed if payment is failed
            pi.setPiStatus(PIStatus.FAILED);
            pi.setIsActive(false);
            for(Beneficiary beneficiary: pi.getBeneficiaryDetails()) {
                beneficiary.setPaymentStatus(BeneficiaryPaymentStatus.FAILED);
            }
        }
        // If PI posted then update the existing PI status
        if (paymentStatus.equals(PaymentStatus.INITIATED)) {
            PaymentInstruction lastPiForUpdate = lastPI == null ? originalPI : lastPI;
            // Update last revised PI status to COMPLETED
            lastPiForUpdate.setPiStatus(PIStatus.COMPLETED);
            lastPiForUpdate.getAuditDetails().setLastModifiedTime(pi.getAuditDetails().getLastModifiedTime());
            lastPiForUpdate.getAuditDetails().setLastModifiedBy(pi.getAuditDetails().getLastModifiedBy());
            piRepository.update(Collections.singletonList(lastPiForUpdate), null);
            piUtils.updatePiForIndexer(paymentRequest.getRequestInfo(), lastPiForUpdate);
            updateLineItemsPaymentStatus(paymentRequest, PaymentStatus.FAILED, paymentStatus);
        }
        log.info(""+pi);
        piRepository.save(Collections.singletonList(pi), sanctionDetail.getFundsSummary(), paymentStatus);
        piUtils.updatePiForIndexer(paymentRequest.getRequestInfo(), pi);
    }

    private List<Beneficiary> getBeneficiariesFromPayment(PaymentRequest paymentRequest) throws Exception {

        // Get the list of bills based on payment request
        List<Bill> billList =  billUtils.fetchBillsFromPayment(paymentRequest);

        billList = filterBillsPayableLineItemByPayments(paymentRequest.getPayment(), billList);

        List<Beneficiary> beneficiaryList = piEnrichment.getBeneficiariesFromBills(billList, paymentRequest);

        // Get all beneficiary ids from pi request
        List<String> individualIds = new ArrayList<>();
        List<String> orgIds = new ArrayList<>();
        for(Bill bill: billList) {
            for (BillDetail billDetail: bill.getBillDetails()) {
                Party payee = billDetail.getPayee();
                if (payee != null && payee.getType().equals("INDIVIDUAL")) {
                    individualIds.add(billDetail.getPayee().getIdentifier());
                } else if (payee != null) {
                    orgIds.add(billDetail.getPayee().getIdentifier());
                }
            }
        }
        List<String> beneficiaryIds = new ArrayList<>();
        for (Beneficiary beneficiary :beneficiaryList) {
            beneficiaryIds.add(beneficiary.getBeneficiaryId());
        }
        return getBeneficiariesEnrichedData(paymentRequest, beneficiaryList, orgIds, orgIds);
    }

    private List<Beneficiary> getBeneficiariesForRevisedPayment(PaymentRequest paymentRequest, PaymentInstruction originalPi, PaymentInstruction lastRevisedPi) throws Exception {
        PaymentInstruction selectedPi = lastRevisedPi != null ? lastRevisedPi : originalPi;
        List<Beneficiary> beneficiaryList = piEnrichment.getFailedBeneficiariesFromExistingPI(selectedPi);

        // Get all beneficiary ids from pi request
        List<String> individualIds = new ArrayList<>();
        List<String> orgIds = new ArrayList<>();
        for(Beneficiary beneficiary: beneficiaryList) {
            if (beneficiary.getBeneficiaryType().equals(BeneficiaryType.IND)) {
                individualIds.add(beneficiary.getBeneficiaryId());
            } else if (beneficiary.getBeneficiaryType().equals(BeneficiaryType.ORG)) {
                orgIds.add(beneficiary.getBeneficiaryId());
            }
        }
        return getBeneficiariesEnrichedData(paymentRequest, beneficiaryList, orgIds, individualIds);
    }

    private List<Beneficiary> getBeneficiariesEnrichedData(PaymentRequest paymentRequest, List<Beneficiary> beneficiaryList, List<String> orgIds, List<String> individualIds) throws Exception {
        List<String> beneficiaryIds = new ArrayList<>();
        for (Beneficiary beneficiary :beneficiaryList) {
            beneficiaryIds.add(beneficiary.getBeneficiaryId());
        }

        List<Organisation> organizations = new ArrayList<>();
        List<Individual> individuals = new ArrayList<>();
        // Get bank account details by beneficiary ids
        List<BankAccount> bankAccounts = bankAccountUtils.getBankAccountsByIdentifier(paymentRequest.getRequestInfo(), beneficiaryIds, paymentRequest.getPayment().getTenantId());
        // Get organizations details
        if (orgIds != null && !orgIds.isEmpty()) {
            organizations = organisationUtils.getOrganisationsById(paymentRequest.getRequestInfo(), orgIds, paymentRequest.getPayment().getTenantId());
        }
        // Get bank account details by beneficiary ids
        if (individualIds != null && !individualIds.isEmpty()) {
            individuals = individualUtils.getIndividualById(paymentRequest.getRequestInfo(), individualIds, paymentRequest.getPayment().getTenantId());
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
                    if (lineItem != null && lineItem.getStatus().equals(Status.ACTIVE) && (payableLineItem.getStatus().equals(PaymentStatus.INITIATED) || payableLineItem.getStatus().equals(PaymentStatus.FAILED)))
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

    private void updateLineItemsPaymentStatus(PaymentRequest paymentRequest, PaymentStatus fromStatus, PaymentStatus paymentStatus) {
        for (PaymentBill bill: paymentRequest.getPayment().getBills()) {
            for (PaymentBillDetail billDetail: bill.getBillDetails()) {
                for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
                    if (lineItem.getStatus().equals(fromStatus)) {
                        lineItem.setStatus(paymentStatus);
                    }
                }
            }
        }
        PaymentRequest request = PaymentRequest.builder()
                .requestInfo(paymentRequest.getRequestInfo())
                .payment(paymentRequest.getPayment())
                .build();
        billUtils.updatePaymentsData(request);
    }

    public List<PaymentInstruction> searchPi(PISearchRequest piSearchRequest){
        searchValidator(piSearchRequest.getSearchCriteria());
        List<PaymentInstruction> paymentInstructions = piRepository.searchPi(piSearchRequest.getSearchCriteria());

        log.info("Sending search response");
        return paymentInstructions;
    }

    public void searchValidator(PISearchCriteria piSearchCriteria){

        if(CollectionUtils.isEmpty(piSearchCriteria.getIds()) && StringUtils.isEmpty(piSearchCriteria.getJitBillNo())
                && StringUtils.isEmpty(piSearchCriteria.getMuktaReferenceId()) && StringUtils.isEmpty(piSearchCriteria.getPiStatus())
                && StringUtils.isEmpty(piSearchCriteria.getTenantId()))
            throw new CustomException("SEARCH_CRITERIA_MANDATORY", "Atleast one search parameter should be provided");
    }

    /**
     * Check for the payment advice new PI can be created
     * @param payment - Payment advice object
     * @return boolean
     */
    private boolean isPaymentValidForCreateNewPI(Payment payment) {
        boolean createPI = true;
        PISearchCriteria searchCriteria = PISearchCriteria.builder()
                .muktaReferenceId(payment.getPaymentNumber())
                .tenantId(payment.getTenantId())
                .build();
        List<PaymentInstruction>  paymentInstructions = piRepository.searchPi(searchCriteria);
        if (paymentInstructions != null && !paymentInstructions.isEmpty()) {
            List<PIStatus> piStatus =   paymentInstructions.stream()
                    .map(PaymentInstruction::getPiStatus)
                    .distinct()
                    .collect(Collectors.toList());
            if (piStatus.contains(PIStatus.INITIATED) || piStatus.contains(PIStatus.APPROVED) || piStatus.contains(PIStatus.PARTIAL) || piStatus.contains(PIStatus.IN_PROCESS) || piStatus.contains(PIStatus.SUCCESSFUL)) {
                createPI = false;
            }
        }
        return createPI;
    }

    private Map<String, Object> canCreateRevisedPi(PaymentRequest paymentRequest) {
        boolean createRevisedPi = true;
        PaymentInstruction originalPi = null;
        PaymentInstruction lastRevisedPi = null;
        Map<String, Object> revisedPIInitialData = new HashMap<>();
        PISearchCriteria searchCriteria = PISearchCriteria.builder()
                .jitBillNo(paymentRequest.getParentPI())
                .tenantId(paymentRequest.getTenantId())
                .build();
        List<PaymentInstruction>  paymentInstructions = piRepository.searchPi(searchCriteria);

        if (paymentInstructions != null && !paymentInstructions.isEmpty() && paymentInstructions.get(0).getParentPiNumber() == null) {
            PIStatus piSt = paymentInstructions.get(0).getPiStatus();
            if (!(piSt.equals(PIStatus.PARTIAL) || piSt.equals(PIStatus.COMPLETED))) {
                createRevisedPi = false;
            }
            originalPi = paymentInstructions.get(0);
            PISearchCriteria revisedPiSearchCriteria = PISearchCriteria.builder()
                    .parentPiNumber(paymentRequest.getParentPI())
                    .tenantId(paymentRequest.getTenantId())
                    .sortBy(PISearchCriteria.SortBy.createdTime)
                    .sortOrder(PISearchCriteria.SortOrder.DESC)
                    .build();
            List<PaymentInstruction>  revisedPaymentInstructions = piRepository.searchPi(revisedPiSearchCriteria);
            if (revisedPaymentInstructions != null && !revisedPaymentInstructions.isEmpty()) {
                lastRevisedPi = revisedPaymentInstructions.get(0);
                List<PIStatus> piStatus = revisedPaymentInstructions.stream()
                        .map(PaymentInstruction::getPiStatus)
                        .distinct()
                        .collect(Collectors.toList());
                if (piStatus.contains(PIStatus.INITIATED) || piStatus.contains(PIStatus.SUCCESSFUL)) {
                    createRevisedPi = false;
                }
            }
        } else {
            createRevisedPi = false;
        }
        revisedPIInitialData.put("createRevisedPi", createRevisedPi);
        // Original PI
        revisedPIInitialData.put("originalPi", originalPi);
        // Revised PI
        revisedPIInitialData.put("lastRevisedPi", lastRevisedPi);
        return revisedPIInitialData;
    }

}
