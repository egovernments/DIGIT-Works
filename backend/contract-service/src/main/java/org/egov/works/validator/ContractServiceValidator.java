package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.util.EstimateServiceUtil;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


import static org.egov.works.util.ContractServiceConstants.MASTER_TENANTS;
import static org.egov.works.util.ContractServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class ContractServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private EstimateServiceUtil estimateServiceUtil;

    @Autowired
    private ContractServiceConfiguration config;

    public void validateCreateContractRequest(ContractRequest contractRequest) {
        log.info("Validate contract create request");

        // Validate the Request Info object
        validateRequestInfo(contractRequest.getRequestInfo());

        // Validate required parameters
        validateRequestedContractRequiredParameters(contractRequest);

        // Validate contract and corresponding lineItems tenantId's
        validateMultipleTenantIds(contractRequest);

        // Validate Contract dates
        validateContractDates(contractRequest);

        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(contractRequest.getRequestInfo(),contractRequest.getContract().getTenantId());

        // Validate estimateIds against estimate service
        validateRequestedEstimateIdsAgainstEstimateService(contractRequest);

        // Validate executingAuthority against MDMS data
        validateExecutingAuthorityAgainstMDMS(contractRequest);

        // Validate orgId against Organization service data
        validateOrganizationIdAgainstOrgService(contractRequest);

        // Validate all documentUid against the Document Service
        validateDocumentIds(contractRequest);
        /**
         * Make sure estimateLineItemId should be associated with one contract only.
         * This will be a query across all contracts to search for this line item ID.
         */

        validateEstimateLineItemAssociationWithOtherContracts(contractRequest);
    }

    private void validateEstimateLineItemAssociationWithOtherContracts(ContractRequest contractRequest) {

    }

    private void validateExecutingAuthorityAgainstMDMS(ContractRequest contractRequest) {
        if(contractRequest.getContract().getExecutingAuthority() == null){
            log.error("Executing Authority is mandatory");
            throw new CustomException("CONTRACT.EXECUTINGAUTHORITY", "Executing Authority is mandatory");
        }
    }

    private void validateOrganizationIdAgainstOrgService(ContractRequest contractRequest) {
        if ("TRUE".equalsIgnoreCase(config.getOrgIdVerificationRequired())) {
            //TODO
            // For now throwing exception. Later implementation will be done
            log.error("Org service not integrated yet");
            throw new CustomException("SERVICE_UNAVAILABLE", "Org service not integrated yet");
        }
    }

    private void validateDocumentIds(ContractRequest contractRequest) {
        if ("TRUE".equalsIgnoreCase(config.getDocumentIdVerificationRequired())) {
            //TODO
            // For now throwing exception. Later implementation will be done
            log.error("Document service not integrated yet");
            throw new CustomException("SERVICE_UNAVAILABLE", "Service not integrated yet");
        }
    }

    /**
     * StartDate should be greater than or equal to agreementDate.
     * endDate should be greater than startDate
     */
    private void validateContractDates(ContractRequest contractRequest) {
        Map<String, String> errorMap = new HashMap<>();

        BigDecimal agreementDate = contractRequest.getContract().getAgreementDate();
        BigDecimal startDate = contractRequest.getContract().getStartDate();
        BigDecimal endDate = contractRequest.getContract().getEndDate();

        log.info("agreementDate "+agreementDate + " startDate "+startDate+" endDate "+endDate);

        if(agreementDate != null && startDate != null && startDate.compareTo(agreementDate)<0)
        {
            log.error("Contract start date should be greater than or equal to agreementDate");
            errorMap.put("INVALID_STARTDATE","Invalid contract start date");
        }

        if(startDate != null && endDate != null && endDate.compareTo(startDate)<0){
            log.error("Contract end date should be greater than to start date");
            errorMap.put("INVALID_ENDDATE","Invalid contract end date");
        }

        if (!errorMap.isEmpty()){
            log.error("Contract date validation failed");
            throw new CustomException(errorMap);
        }

    }

    private void validateRequestedEstimateIdsAgainstEstimateService(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<LineItems> lineItems = contract.getLineItems();
        Set<String> providedEstimateIds = lineItems.stream().map(e -> e.getEstimateId()).collect(Collectors.toSet());

        List<Estimate> fetchedEstimates = estimateServiceUtil.fetchEstimates(requestInfo,tenantId,providedEstimateIds);

        Map<String, List<LineItems>> providedLineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        Map<String, List<Estimate>> fetchedEstimatesMap = fetchedEstimates.stream().collect(Collectors.groupingBy(Estimate::getId));
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = getFetchedEstimateIdWithEstimateDetailIds(fetchedEstimatesMap);
        Map<String, Set<String>> fetchedEstimateDetailIdWithAccountDetailIds = getFetchedEstimateDetailIdWithAccountDetailIds(fetchedEstimatesMap);

        for(String estimateId : providedEstimateIds){
            if(fetchedEstimatesMap.containsKey(estimateId)){
                for(LineItems lineItem :providedLineItemsMap.get(estimateId)) {
                    String estimateLineItemId = lineItem.getEstimateLineItemId();
                    if (estimateLineItemId != null) {
                        if (!fetchedEstimateIdWithEstimateDetailIds.get(estimateId).contains(estimateLineItemId)) {
                            log.error("LineItemId [" + estimateLineItemId + "] is invalid for estimate ["+estimateId+"]");
                            throw new CustomException("INVALID_ESTIMATELINEITEMID", "LineItemId [" + estimateLineItemId + "] is invalid for estimate ["+estimateId+"]");
                        }
                        List<AmountBreakup> amountBreakups = lineItem.getAmountBreakups();
                        for(AmountBreakup amountBreakup: amountBreakups){
                            String estimateAmountBreakupId = amountBreakup.getEstimateAmountBreakupId();
                            if (!fetchedEstimateDetailIdWithAccountDetailIds.get(estimateLineItemId).contains(estimateAmountBreakupId)) {
                                log.error("EstimateAmountBreakupId [" + estimateAmountBreakupId + "] is invalid for EstimateLineItemId ["+estimateLineItemId+"]");
                                throw new CustomException("INVALID_ESTIMATEAMOUNTBREAKUPID", "EstimateAmountBreakupId [" + estimateAmountBreakupId + "] is invalid for EstimateLineItemId ["+estimateLineItemId+"]");
                            }
                        }
                    }
                }
            } else {
                log.error("EstimateId is invalid");
                throw new CustomException("INVALID_ESTIMATEID","EstimateId ["+estimateId + "] is invalid");
            }
        }
    }

    private Map<String, Set<String>> getFetchedEstimateDetailIdWithAccountDetailIds(Map<String, List<Estimate>> fetchedEstimatesMap) {
        Map<String, Set<String>> fetchedEstimateDetailIdWithAccountDetailIds = new HashMap<>();
        for(String fetchedEstimateId : fetchedEstimatesMap.keySet()){
            for(Estimate estimate : fetchedEstimatesMap.get(fetchedEstimateId)){
                for(EstimateDetail estimateDetail: estimate.getEstimateDetails()){
                    Set<String> collect = estimateDetail.getAmountDetail().stream().map(e -> e.getId()).collect(Collectors.toSet());
                    fetchedEstimateDetailIdWithAccountDetailIds.put(estimateDetail.getId(),collect);
                }
            }
        }
        return fetchedEstimateDetailIdWithAccountDetailIds;

    }

    private Map<String, Set<String>> getFetchedEstimateIdWithEstimateDetailIds(Map<String, List<Estimate>> fetchedEstimatesMap) {
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = new HashMap<>();
        for(String fetchedEstimateId : fetchedEstimatesMap.keySet()){
            for(Estimate estimate : fetchedEstimatesMap.get(fetchedEstimateId)){
                Set<String> estimateDetailsIds = estimate.getEstimateDetails().stream().map(e -> e.getId()).collect(Collectors.toSet());
                fetchedEstimateIdWithEstimateDetailIds.put(fetchedEstimateId,estimateDetailsIds);
            }
        }
        return fetchedEstimateIdWithEstimateDetailIds;
    }

    private void validateTenantIdAgainstMDMS(RequestInfo requestInfo,String tenantId) {
        String rootTenantId = tenantId.split("\\.")[0];

        //Get MDMS data using create attendance register request and tenantId
        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> tenantRes = null;
        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes)){
            log.error("The tenant: " + tenantId + " is not present in MDMS");
            throw new CustomException("INVALID_TENANT","The tenant: " + tenantId + " is not present in MDMS");
        }

        log.info("TenantId data validated against MDMS");
    }

    /**
     * Validate the Request Info object.
     * @param requestInfo
     */

    private void validateRequestInfo(RequestInfo requestInfo) {
        if (requestInfo == null) {
            log.error("Request info is mandatory");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is mandatory");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("UUID is mandatory");
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }

        log.info("Request Info object validation done");
    }

    private void validateRequestedContractRequiredParameters(ContractRequest contractRequest) {
        Map<String, String> errorMap = new HashMap<>();
        Contract contract = contractRequest.getContract();

        if (contract == null) {
            log.error("Contract is mandatory");
            throw new CustomException("CONTRACT", "Contract is mandatory");
        }

        if (StringUtils.isBlank(contract.getTenantId())) {
            log.error("TenantId is mandatory");
            errorMap.put("CONTRACT.TENANTID", "TenantId is mandatory");
        }

        if (contract.getExecutingAuthority() == null) {
            log.error("Executing Authority is mandatory");
            errorMap.put("CONTRACT.EXECUTINGAUTHORITY", "Executing Authority is mandatory");
        }

        if (StringUtils.isBlank(contract.getOrgId())) {
            log.error("OrgnisationId is mandatory");
            errorMap.put("CONTRACT.ORGNISATIONID", "OrgnisationId is mandatory");
        }

        List<LineItems> lineItems = contract.getLineItems();

        if(lineItems == null || lineItems.isEmpty()){
            log.error("LineItem is mandatory");
            errorMap.put("CONTRACT.LINEITEM", "LineItem is mandatory");
        }

        for(LineItems lineItem : lineItems) {
            if (StringUtils.isBlank(lineItem.getEstimateId())) {
                log.error("LineItems, EstimateId is mandatory");
                errorMap.put("CONTRACT.LINEITEMS.ESTIMATEID", "LineItems.EstimateId is mandatory");
            }
            if (StringUtils.isBlank(lineItem.getTenantId())) {
                log.error("LineItems.TenantId is mandatory");
                errorMap.put("CONTRACT.LINEITEMS.TENANTID", "LineItems.TenantId is mandatory");
            }
            List<AmountBreakup> amountBreakups = lineItem.getAmountBreakups();
            if (amountBreakups != null && !amountBreakups.isEmpty()) {
                for (AmountBreakup amountBreakup : amountBreakups) {
                    if (StringUtils.isBlank(amountBreakup.getEstimateAmountBreakupId())) {
                        log.error("Estimate Amount BreakupId is mandatory");
                        errorMap.put("CONTRACT.LINEITEMS.AMOUNTBREAKUPS.ESTIMATEAMOUNTBREAKUPID", "Estimate Amount BreakupId is mandatory");
                    }

                    if (amountBreakup.getAmount() == null) {
                        log.error("Breakup Amount is mandatory");
                        errorMap.put("CONTRACT.LINEITEMS.AMOUNTBREAKUPS.AMOUNT", "Breakup Amount is mandatory");
                    }
                }
            }
        }
        if (!errorMap.isEmpty()){
            log.error("Contract request validation failed");
            throw new CustomException(errorMap);
        }
        log.info("Required request parameter validation done");
    }

    private void validateMultipleTenantIds(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        Set<String> tenantIds = new HashSet<>();
        tenantIds.add(contract.getTenantId());
        for(LineItems lineItem : contract.getLineItems()){
            String tenantId = lineItem.getTenantId();
            if(!tenantIds.contains(tenantId)){
                log.error("Contract and corresponding lineItems should belong to same tenantId");
                throw new CustomException("MULTIPLE_TENANTIDS","Contract and corresponding lineItems should belong to same tenantId");
            }
        }
    }

    public void validateSearchContractRequest(RequestInfo requestInfo, ContractCriteria contractCriteria) {

        if (contractCriteria == null || requestInfo == null) {
            log.error("Contract search criteria request is mandatory");
            throw new CustomException("CONTRACT_SEARCH_CRITERIA_REQUEST", "Contract search criteria request is mandatory");
        }

        //validate request info
        log.info("validate request info");
        validateRequestInfo(requestInfo);

        //validate request parameters
        log.info("validate request parameters");
        validateSearchContractRequestParameters(contractCriteria);

        //validate tenantId with MDMS
        log.info("validate tenantId with MDMS");
        validateTenantIdAgainstMDMS(requestInfo,contractCriteria.getTenantId());

    }

    private void validateSearchContractRequestParameters(ContractCriteria contractCriteria){

        if (StringUtils.isBlank(contractCriteria.getTenantId())) {
            log.error("Tenant is mandatory");
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
    }
}
