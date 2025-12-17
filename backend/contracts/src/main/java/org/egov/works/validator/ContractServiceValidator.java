package org.egov.works.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.LineItemsRepository;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateDetail;
import org.egov.works.util.*;
import org.egov.works.repository.ContractRepository;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ContractServiceConstants.*;

@Component
@Slf4j
public class ContractServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private HRMSUtils hrmsUtils;

    @Autowired
    private EstimateServiceUtil estimateServiceUtil;

    @Autowired
    private ContractServiceConfiguration config;

    @Autowired
    private LineItemsRepository lineItemsRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MDMSDataParser mdmsDataParser;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OrgUtils orgUtils;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private MeasurementUtil measurementUtil;

    public void validateCreateContractRequest(ContractRequest contractRequest) {
        log.info("Validate contract create request");

        // Validate the Request Info object
        validateRequestInfo(contractRequest.getRequestInfo());

        // Validate required parameters
        validateRequiredParameters(contractRequest);

        // Validate contract and corresponding lineItems tenantId's
        validateMultipleTenantIds(contractRequest);

        // Validate request fields against MDMS data
        validateRequestFieldsAgainstMDMS(contractRequest);

        // Validate documentIds against document service
        validateDocumentIdsAgainstDocumentService(contractRequest);

        // Validate orgId against Organization service data
        validateOrganizationIdAgainstOrgService(contractRequest);

        if (contractRequest.getContract().getBusinessService() != null &&
                (contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                        || contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE))) {
            log.info("Validating contract revision request");
            // Validate if Time Extension Request
            validateContractRevisionRequestForCreate(contractRequest);
        } else {
            // Validate estimateIds against estimate service and DB
            validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB(contractRequest);
        }

        log.info("Contract create request validated");
    }

    public void validateUpdateContractRequest(ContractRequest contractRequest) {
        log.info("Validate contract update request");

        // Validate the Request Info object
        validateRequestInfo(contractRequest.getRequestInfo());

        // Validate required parameters
        validateRequiredParameters(contractRequest);

        // Validate contract and corresponding lineItems tenantId's
        validateMultipleTenantIds(contractRequest);

        // Validate request fields against MDMS data
        validateRequestFieldsAgainstMDMS(contractRequest);

        // Validate documentIds against document service
        validateDocumentIdsAgainstDocumentService(contractRequest);

        // Validate provided contract for update should exist in DB
        validateContractAgainstDB(contractRequest);

        // Validate orgId against Organization service data
        validateOrganizationIdAgainstOrgService(contractRequest);

        if (contractRequest.getContract().getBusinessService() != null &&
                (contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                        || contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE))) {
            // Validate Contract Revision Request for Update request
            validateContractRevisionRequestForUpdate(contractRequest);
        } else {
            // Validate estimateIds against estimate service and DB
            validateUpdateRequestedEstimateIdsAgainstEstimateServiceAndDB(contractRequest);
        }

        log.info("Contract create request validated : contractId [" + contractRequest.getContract().getId() + "]");
    }

    private void validateContractAgainstDB(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        String contractId = contract.getId();
        String tenantId = contract.getTenantId();

        List<String> contracts = new ArrayList<>();
        contracts.add(contractId);

        Pagination pagination = Pagination.builder()
                .limit(config.getContractMaxLimit())
                .offSet(config.getContractDefaultOffset())
                .build();
        ContractCriteria contractCriteria = ContractCriteria.builder()
                .ids(contracts)
                .tenantId(tenantId)
                .requestInfo(contractRequest.getRequestInfo())
                .pagination(pagination)
                .build();
        List<Contract> fetchedContracts = contractRepository.getContracts(contractCriteria);
        if (fetchedContracts.isEmpty()) {
            log.error("Update:: Provided contract [" + contractId + "] not found");
            throw new CustomException("CONTRACT_NOT_FOUND", "Provided contract [" + contractId + "] not found");
        }
        if (fetchedContracts.get(0).getWfStatus().equalsIgnoreCase(REJECTED_STATUS))
            throw new CustomException("CONTRACT_REJECTED", "Provided contract [" + contractId + "] is rejected");
        log.info("Update:: Provided contract [" + contractId + "] found in DB");

    }

    /**
     * Validate requested estimateIDs/estimateLineItemId/estimateAmountBreakupId against estimate service.
     * Also Make sure that provided estimateLineItemId are not associated with other contract.
     * and if they are associated it should be requested contract.
     *
     * @param contractRequest
     */
    private void validateUpdateRequestedEstimateIdsAgainstEstimateServiceAndDB(ContractRequest contractRequest) {
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = validateRequestedEstimateIdsAgainstEstimateService(contractRequest);
        validateUpdateEstimateLineItemAssociationWithOtherContracts(contractRequest, fetchedEstimateIdWithEstimateDetailIds);
    }

    /**
     * Validate requested estimateIDs/estimateLineItemId/estimateAmountBreakupId against estimate service.
     * Make sure that provided estimateLineItemId are not associated with other contract.
     *
     * @param contractRequest
     */
    private void validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB(ContractRequest contractRequest) {
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = validateRequestedEstimateIdsAgainstEstimateService(contractRequest);
        validateCreateEstimateLineItemAssociationWithOtherContracts(contractRequest, fetchedEstimateIdWithEstimateDetailIds);
    }

    private void validateRequestFieldsAgainstMDMS(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        RequestInfo requestInfo = contractRequest.getRequestInfo();

        //Fetch MDMS data
        Object mdmsData = fetchMDMSDataForValidation(requestInfo, tenantId);

        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(mdmsData, contract.getTenantId());

        // Validate executingAuthority against MDMS data
        validateExecutingAuthorityAgainstMDMS(mdmsData, contract.getExecutingAuthority());

        // Validate executingAuthority against MDMS data
        validateContractTypeAgainstMDMS(mdmsData, contract.getContractType());

        // Validate Officer In Charge role against MDMS data

        validateOfficerInChargeRoleAgainstMDMS(mdmsData, contractRequest);

        log.info("Request Fields validated against MDMS");
    }

    private void validateOfficerInChargeRoleAgainstMDMS(Object mdmsData, ContractRequest contractRequest) {

        String officerInChargeId = getOfficerInChargeIdFromAdditionalDetails(contractRequest.getContract().getAdditionalDetails());
        if (StringUtils.isEmpty(officerInChargeId)) {
            log.error("Officer In-charge is not present, skipping further validation");
            return;
        }
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<String> roles = fetchRolesOfOfficerInCharge(requestInfo, tenantId, officerInChargeId);
        Set<String> distinctRoles = roles.stream().collect(Collectors.toSet());
        List<Object> oicRolesRes = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_OIC_ROLES_VERIFICATION);
        for (Object allowedRole : oicRolesRes) {
            if (distinctRoles.contains(allowedRole.toString())) {
                log.error("Officer In-charge is associated with required role");
                return;
            }
        }
        log.error("Officer In-charge is not associated with required role");
        throw new CustomException("MISSING_REQUIRED_ROLE", "Officer In-charge is not associated with required role");

    }

    private List<String> fetchRolesOfOfficerInCharge(RequestInfo requestInfo, String tenantId, String officerInChargeId) {
        return hrmsUtils.getRoleCodesByEmployeeId(requestInfo, tenantId, Collections.singletonList(officerInChargeId));
    }

    private String getOfficerInChargeIdFromAdditionalDetails(Object additionalDetails) {
        try {
            Optional<String> value = commonUtil.findValue(additionalDetails, OFFICER_IN_CHARGE_ID_CONSTANT);
            if (value.isPresent())
                return value.get();
        } catch (Exception ignore) {
            log.error(ignore.toString());
        }
        return null;
    }

    private Object fetchMDMSDataForValidation(RequestInfo requestInfo, String tenantId) {
        return mdmsUtils.fetchMDMSForValidation(requestInfo, tenantId);
    }

    private void validateTenantIdAgainstMDMS(Object mdmsData, String tenantId) {
        List<Object> tenantRes = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_TENANTS_VERIFICATION);
        if (CollectionUtils.isEmpty(tenantRes) || !tenantRes.contains(tenantId)) {
            log.error("The tenant: " + tenantId + " is not present in MDMS");
            throw new CustomException("INVALID_TENANT", "Invalid tenantId [" + tenantId + "]");
        }
        log.info("TenantId data validated against MDMS");
    }

    private void validateContractTypeAgainstMDMS(Object mdmsData, String contractType) {
        List<Object> contractTypeRes = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_CONTRACT_TYPE_VERIFICATION);
        if (CollectionUtils.isEmpty(contractTypeRes) || !contractTypeRes.contains(contractType)) {
            log.error("The Contract Type [" + contractType + "] is not present in MDMS");
            throw new CustomException("INVALID_CONTRACT_TYPE", "Invalid Contract Type [" + contractType + "]");
        }

        log.info("Contract Type data validated against MDMS");
    }

    private void validateExecutingAuthorityAgainstMDMS(Object mdmsData, String executingAuthority) {
        List<Object> executingAuthorityRes = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_CBO_ROLES_VERIFICATION);
        if (CollectionUtils.isEmpty(executingAuthorityRes) || !executingAuthorityRes.contains(executingAuthority)) {
            log.error("The Executing Authority [" + executingAuthority + "] is not present in MDMS");
            throw new CustomException("INVALID_EXECUTING_AUTHORITY", "Invalid Executing Authority [" + executingAuthority + "]");
        }

        log.info("Executing Authority data validated against MDMS");
    }

    /**
     * Get fileStore from documents and send call to filestore service and
     * validate if all documents are present in response.
     *
     * @param contractRequest
     */

    private void validateDocumentIdsAgainstDocumentService(ContractRequest contractRequest) {
        if (CollectionUtils.isEmpty(contractRequest.getContract().getDocuments())) {
            return;
        }
        List<String> documentIds = contractRequest.getContract().getDocuments().stream().map(Document::getFileStore).collect(Collectors.toList());

        // Make API request to file store service
        String fileStoreResponse = getFileStoreResponse(documentIds, contractRequest.getContract().getTenantId());

        // Match documentIds against fileStoreResponse
        validateDocumentIdsAgainstFileStoreResponse(documentIds, fileStoreResponse);

    }

    /**
     * Api request to fileStore service
     */
    private String getFileStoreResponse(List<String> fileStoreIds, String tenantId) {
        StringBuilder fileStoreUrl = new StringBuilder(config.getFileStoreHost()).append(config.getFileStoreEndpoint());
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(fileStoreUrl.toString())
                .queryParam("tenantId", tenantId);
        for (String fileStoreId : fileStoreIds) {
            uriComponentsBuilder.queryParam("fileStoreIds", fileStoreId);
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.GET,
                null,
                String.class
        );
        if (responseEntity.getStatusCodeValue() == 200) {
            // Read and return the response content as a string
            return responseEntity.getBody();
        } else {
            // Handle non-200 status codes (e.g., by throwing an exception)
            throw new CustomException(FILE_STORE_API_FAILURE, FILE_STORE_API_REQUEST_FAIL_MSG + responseEntity);
        }
    }

    private void validateDocumentIdsAgainstFileStoreResponse(List<String> documentIds, String fileStoreResponse) {
        Map<String, String> fileStoreResponseMap = new HashMap<>();
        try {
            fileStoreResponseMap = mapper.readValue(fileStoreResponse, Map.class);
        } catch (IOException e) {
            throw new CustomException(PARSE_ERROR_CODE, PARSE_ERROR_MSG);
        }
        for (String documentId : documentIds) {
            if (!fileStoreResponseMap.containsKey(documentId)) {
                throw new CustomException(INVALID_DOCUMENTS_CODE, INVALID_DOCUMENTS_MSG);
            }
        }
    }

    /**
     * Make sure that provided estimateLineItemId are not associated with other contract.
     *
     * @param contractRequest
     * @param estimateIdWithEstimateDetailIds
     */
    private void validateCreateEstimateLineItemAssociationWithOtherContracts(ContractRequest contractRequest, Map<String, Set<String>> estimateIdWithEstimateDetailIds) {
        Contract contract = contractRequest.getContract();
        List<String> estimatedLineItemIdsList = new ArrayList<>();
        List<LineItems> lineItems = contract.getLineItems();

        for (LineItems lineItem : lineItems) {
            String estimateId = lineItem.getEstimateId();
            String estimateLineItemId = lineItem.getEstimateLineItemId();
            if (estimateLineItemId == null) {
                estimatedLineItemIdsList.addAll(estimateIdWithEstimateDetailIds.get(estimateId));
            } else {
                estimatedLineItemIdsList.add(estimateLineItemId);
            }
        }
        ContractCriteria contractCriteria = ContractCriteria.builder().estimateLineItemIds(estimatedLineItemIdsList).build();
        List<LineItems> fetchedLineItems = lineItemsRepository.getLineItems(contractCriteria);
        List<LineItems> filteredLineItems = fetchedLineItems.stream().filter(e -> e.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
        if (!filteredLineItems.isEmpty()) {
            Set<String> collect = filteredLineItems.stream().map(e -> e.getEstimateLineItemId()).collect(Collectors.toSet());
            log.error("Estimate Line Items " + collect + " are already associated with other contract");
            throw new CustomException("INVALID_ESTIMATELINEITEMID", "Estimate Line Items " + collect + " are already associated with other contract");
        }

        log.info("Create :: Estimate LineItem validation against other contracts done");
    }

    /**
     * Make sure that provided estimateLineItemId are not associated with other contract.
     * And if they are associated it should be requested contract.
     *
     * @param contractRequest
     * @param estimateIdWithEstimateDetailIds
     */
    private void validateUpdateEstimateLineItemAssociationWithOtherContracts(ContractRequest contractRequest, Map<String, Set<String>> estimateIdWithEstimateDetailIds) {
        Contract contract = contractRequest.getContract();
        List<String> estimatedLineItemIds = new ArrayList<>();
        List<LineItems> lineItems = contract.getLineItems();

        for (LineItems lineItem : lineItems) {
            String estimateId = lineItem.getEstimateId();
            String estimateLineItemId = lineItem.getEstimateLineItemId();
            if (estimateLineItemId == null) {
                estimatedLineItemIds.addAll(estimateIdWithEstimateDetailIds.get(estimateId));
            } else {
                estimatedLineItemIds.add(estimateLineItemId);
            }
        }

        ContractCriteria contractCriteria = ContractCriteria.builder().estimateLineItemIds(estimatedLineItemIds).build();
        List<LineItems> fetchedLineItems = lineItemsRepository.getLineItems(contractCriteria);

        if (!fetchedLineItems.isEmpty()) {
            Set<String> setOfContractIds = fetchedLineItems.stream().filter(e -> e.getStatus().equals(Status.ACTIVE)).map(e -> e.getContractId()).collect(Collectors.toSet());
            if (!(setOfContractIds.size() == 1 && setOfContractIds.contains(contract.getId()))) {
                log.error("Update :: Estimate Line Items are already associated with other contract");
                throw new CustomException("INVALID_ESTIMATE_LINE_ITEM_ID", "Estimate Line Items are already associated with other contract");
            }
        }

        log.error("Update :: Estimate Line Items are validated against other contract");
    }

    private void validateOrganizationIdAgainstOrgService(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        String orgId = contract.getOrgId();

        Object fetchedOrg = orgUtils.fetchOrg(requestInfo, tenantId, Collections.singletonList(orgId));
        List<Object> orgRes = commonUtil.readJSONPathValue(fetchedOrg, ORG_ORGANISATIONS_VALIDATION_PATH);

        if (CollectionUtils.isEmpty(orgRes)) {
            log.error("Org [" + orgId + "] is not present");
            throw new CustomException("INVALID_ORGID", "Org [" + orgId + "] is not present");
        }
        log.info("Org [" + orgId + "] is validated successfully");
    }

    private Map<String, Set<String>> validateRequestedEstimateIdsAgainstEstimateService(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<LineItems> lineItems = contract.getLineItems();
        Set<String> providedEstimateIds = lineItems.stream().map(e -> e.getEstimateId()).collect(Collectors.toSet());
        List<Estimate> fetchedActiveEstimates = fetchActiveEstimates(requestInfo, tenantId, providedEstimateIds);
        validateProvidedEstimateStatusAgainstFetchedActiveEstimates(providedEstimateIds, fetchedActiveEstimates);

        Map<String, List<LineItems>> providedLineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        Map<String, List<Estimate>> fetchedActiveEstimatesMap = fetchedActiveEstimates.stream().collect(Collectors.groupingBy(Estimate::getId));
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = getFetchedEstimateIdWithEstimateDetailIds(fetchedActiveEstimatesMap);
        Map<String, Set<String>> fetchedEstimateDetailIdWithAccountDetailIds = getFetchedEstimateDetailIdWithAccountDetailIds(fetchedActiveEstimatesMap);

        for (String estimateId : providedEstimateIds) {
            for (LineItems lineItem : providedLineItemsMap.get(estimateId)) {
                String estimateLineItemId = lineItem.getEstimateLineItemId();
                if (estimateLineItemId != null) {
                    if (fetchedEstimateIdWithEstimateDetailIds.get(estimateId) != null && !fetchedEstimateIdWithEstimateDetailIds.get(estimateId).contains(estimateLineItemId)) {
                        log.error("LineItemId [" + estimateLineItemId + "] is invalid for estimate [" + estimateId + "]");
                        throw new CustomException("INVALID_ESTIMATELINEITEMID", "LineItemId [" + estimateLineItemId + "] is invalid for estimate [" + estimateId + "]");
                    }
                    List<AmountBreakup> amountBreakups = lineItem.getAmountBreakups();
                    for (AmountBreakup amountBreakup : amountBreakups) {
                        String estimateAmountBreakupId = amountBreakup.getEstimateAmountBreakupId();
                        if (fetchedEstimateDetailIdWithAccountDetailIds.get(estimateLineItemId) != null && !fetchedEstimateDetailIdWithAccountDetailIds.get(estimateLineItemId).contains(estimateAmountBreakupId)) {
                            log.error("EstimateAmountBreakupId [" + estimateAmountBreakupId + "] is invalid for EstimateLineItemId [" + estimateLineItemId + "]");
                            throw new CustomException("INVALID_ESTIMATEAMOUNTBREAKUPID", "EstimateAmountBreakupId [" + estimateAmountBreakupId + "] is invalid for EstimateLineItemId [" + estimateLineItemId + "]");
                        }
                    }
                }
            }
        }

        log.info("EstimateIds validated against Estimate Service");
        return fetchedEstimateIdWithEstimateDetailIds;
    }

    private void validateProvidedEstimateStatusAgainstFetchedActiveEstimates(Set<String> providedEstimateIds, List<Estimate> fetchedActiveEstimates) {
        Set<String> fetchedEstimateIds = fetchedActiveEstimates.stream().map(e -> e.getId()).collect(Collectors.toSet());
        for (String providedEstimateId : providedEstimateIds) {
            if (!fetchedEstimateIds.contains(providedEstimateId)) {
                log.error("Provided estimate [" + providedEstimateId + "] is not active");
                throw new CustomException("ESTIMATE_NOT_ACTIVE", "Provided estimate [" + providedEstimateId + "] is not active");
            }
        }
    }

    private List<Estimate> fetchActiveEstimates(RequestInfo requestInfo, String tenantId, Set<String> providedEstimateIds) {
        List<Estimate> fetchedEstimates = estimateServiceUtil.fetchActiveEstimates(requestInfo, tenantId, providedEstimateIds);
        if (fetchedEstimates.isEmpty()) {
            log.error("Provided estimates are either inactive or not found");
            throw new CustomException("ACTIVE_ESTIMATE_NOT_FOUND", "Provided estimates are either inactive or not found");
        }
        return fetchedEstimates;
    }

    private Map<String, Set<String>> getFetchedEstimateDetailIdWithAccountDetailIds(Map<String, List<Estimate>> fetchedEstimatesMap) {
        Map<String, Set<String>> fetchedEstimateDetailIdWithAccountDetailIds = new HashMap<>();
        for (Map.Entry<String, List<Estimate>> fetchedEstimatesMapEntry : fetchedEstimatesMap.entrySet()) {
            for (Estimate estimate : fetchedEstimatesMapEntry.getValue()) {
                for (EstimateDetail estimateDetail : estimate.getEstimateDetails()) {
                    Set<String> collect = estimateDetail.getAmountDetail().stream().map(e -> e.getId()).collect(Collectors.toSet());
                    fetchedEstimateDetailIdWithAccountDetailIds.put(estimateDetail.getId(), collect);
                }
            }
        }
        return fetchedEstimateDetailIdWithAccountDetailIds;
    }

    private Map<String, Set<String>> getFetchedEstimateIdWithEstimateDetailIds(Map<String, List<Estimate>> fetchedEstimatesMap) {
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = new HashMap<>();
        for (Map.Entry<String, List<Estimate>> fetchedEstimatesMapEntry : fetchedEstimatesMap.entrySet()) {
            String fetchedEstimateId = fetchedEstimatesMapEntry.getKey();
            for (Estimate estimate : fetchedEstimatesMapEntry.getValue()) {
                Set<String> estimateDetailsIds = estimate.getEstimateDetails().stream().map(e -> e.getId()).collect(Collectors.toSet());
                fetchedEstimateIdWithEstimateDetailIds.put(fetchedEstimateId, estimateDetailsIds);
            }
        }
        return fetchedEstimateIdWithEstimateDetailIds;
    }

    /**
     * Validate the Request Info object.
     *
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

    private void validateRequiredParameters(ContractRequest contractRequest) {
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

        if (contract.getContractType() == null) {
            log.error("Contract Type is mandatory");
            errorMap.put("CONTRACT.CONTRACTTYPE", "Contract Type is mandatory");
        }

        if (StringUtils.isBlank(contract.getOrgId())) {
            log.error("OrgnisationId is mandatory");
            errorMap.put("CONTRACT.ORGNISATIONID", "OrgnisationId is mandatory");
        }

        Integer completionPeriod = contract.getCompletionPeriod();
        if (completionPeriod == null || completionPeriod <= 0) {
            log.error("Completion Period is mandatory and its min value is one day");
            errorMap.put("CONTRACT.COMPLETION_PERIOD", "Completion Period is mandatory and its min value is one day");
        }

        List<LineItems> lineItems = contract.getLineItems();

        if (lineItems == null || lineItems.isEmpty()) {
            log.error("LineItem is mandatory");
            errorMap.put("CONTRACT.LINE_ITEM", "LineItem is mandatory");
        }

        for (LineItems lineItem : lineItems) {
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
        if (!errorMap.isEmpty()) {
            log.error("Contract request validation failed");
            throw new CustomException(errorMap);
        }
        log.info("Required request parameter validation done");
    }

    private void validateMultipleTenantIds(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        Set<String> tenantIds = new HashSet<>();
        tenantIds.add(contract.getTenantId());
        for (LineItems lineItem : contract.getLineItems()) {
            String tenantId = lineItem.getTenantId();
            if (!tenantIds.contains(tenantId)) {
                log.error("Contract and corresponding lineItems should belong to same tenantId");
                throw new CustomException("MULTIPLE_TENANTIDS", "Contract and corresponding lineItems should belong to same tenantId");
            }
        }

        log.info("Multiple tenantId validation done");
    }

    public void validateSearchContractRequest(ContractCriteria contractCriteria) {

        if (contractCriteria == null || contractCriteria.getRequestInfo() == null) {
            log.error("Contract search criteria request is mandatory");
            throw new CustomException("CONTRACT_SEARCH_CRITERIA_REQUEST", "Contract search criteria request is mandatory");
        }

        RequestInfo requestInfo = contractCriteria.getRequestInfo();

        //validate request info
        log.info("Search :: validate request info");
        validateRequestInfo(requestInfo);

        //validate request parameters
        log.info("Search :: validate request parameters");
        validateSearchContractRequestParameters(contractCriteria);

        //validate tenantId with MDMS
        log.info("Search :: validate tenantId with MDMS");
        validateSearchTenantIdAgainstMDMS(requestInfo, contractCriteria.getTenantId());

        log.info("Search :: validation done");
    }

    private void validateSearchTenantIdAgainstMDMS(RequestInfo requestInfo, String tenantId) {
        Object mdmsData = fetchMDMSDataForValidation(requestInfo, tenantId);
        validateTenantIdAgainstMDMS(mdmsData, tenantId);
    }

    private void validateSearchContractRequestParameters(ContractCriteria contractCriteria) {

        if (StringUtils.isBlank(contractCriteria.getTenantId())) {
            log.error("Tenant is mandatory");
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
    }

    /**
     * Validating Time Extension Create Request
     *
     * @param contractRequest
     */
    public void validateContractRevisionRequestForCreate(ContractRequest contractRequest) {
        log.info("Validating revise contract create request");
        // Validate if contract number is present
        validateContractNumber(contractRequest);

        List<Contract> contractsFromDB = contractRepository.getActiveContractsFromDB(contractRequest);

        // Validate if contract is present in DB
        validateContractNumber(contractsFromDB);
        // Validate if contract revision limit is reached
        validateRevisionLimit(contractsFromDB);
        // Validate if org is same as previous contract
        validateOrganisation(contractRequest, contractsFromDB);
        // Validate if at least one muster-roll is created and approved (Validator removed as per sonarLint
        // Get from previous commits if required.
        // Validate if previous contract revision request is in workflow
        validateContractRevisionRequest(contractRequest);
        // Validate start date
        validateStartDate(contractRequest, contractsFromDB);
        // Validate if extended end date is not before active contract end date
        validateEndDateExtension(contractRequest, contractsFromDB);
        // Validate if revised estimate in approved state
        fetchActiveEstimates(contractRequest.getRequestInfo(), contractRequest.getContract().getTenantId(),
                Collections.singleton(contractRequest.getContract().getLineItems().get(0).getEstimateId()));

        log.info("Contract Revision Request Validated for contract number :: " + contractRequest.getContract().getContractNumber());
    }

    /**
     * Validate Time Extension Update Request
     *
     * @param contractRequest
     */
    private void validateContractRevisionRequestForUpdate(ContractRequest contractRequest) {
        log.info("Validating revise contract update request");
        // Validate if contract number is present
        validateContractNumber(contractRequest);

        List<Contract> contractsFromDB = contractRepository.getActiveContractsFromDB(contractRequest);

        // Validate if contract is present in DB
        validateContractNumber(contractsFromDB);
        // Validate if contract revision limit is reached
        validateRevisionLimit(contractsFromDB);
        // Validate if org is same as previous contract
        validateOrganisation(contractRequest, contractsFromDB);
        // Validate if at least one muster-roll is created and approved (Validator removed as per sonarLint
        // Get from previous commits if required
        // Validate Supplement Number
        validateSupplementNumber(contractRequest);
        // Validate if extended end date is not before active contract end date
        validateEndDateExtension(contractRequest, contractsFromDB);
        // Validate start date
        validateStartDate(contractRequest, contractsFromDB);
        // Validate if revised estimate in approved state
        List<Estimate> estimate = fetchActiveEstimates(contractRequest.getRequestInfo(), contractRequest.getContract().getTenantId(),
                Collections.singleton(contractRequest.getContract().getLineItems().get(0).getEstimateId()));
        // Validate if contractRequest estimate line item id exists in fetchActiveEstimates
        validateEstimateLineItemId(estimate, contractRequest.getContract().getLineItems());

        log.info("Contract Revision Request Validated for contract number :: " + contractRequest.getContract().getContractNumber());
    }

    private void validateContractNumber(ContractRequest contractRequest) {
        if (contractRequest.getContract().getContractNumber() == null || contractRequest.getContract().getContractNumber().isEmpty()) {
            throw new CustomException("CONTRACT_NUMBER_NOT_PRESENT_IN_REQUEST", "Contract number mandatory for revision contract");
        }
    }

    private void validateContractNumber(List<Contract> contractsFromDB) {
        if (contractsFromDB == null || contractsFromDB.isEmpty()) {
            throw new CustomException("CONTRACT_NUMBER_NOT_PRESENT_IN_DB", "Given contract number is not present in database");
        }
    }

    private void validateOrganisation(ContractRequest contractRequest, List<Contract> contractsFromDB) {
        if (contractRequest.getContract().getOrgId() == null || contractRequest.getContract().getOrgId().isEmpty()) {
            throw new CustomException("ORG_ID_MANDATORY", "Org id not present in extension request");
        }
        for (Contract contract : contractsFromDB) {
            if (!contract.getOrgId().equalsIgnoreCase(contractRequest.getContract().getOrgId())) {
                throw new CustomException("ORG_ID_MISMATCH", "Org id must be same for time extension request");
            }
        }
    }

    private void validateContractRevisionRequest(ContractRequest contractRequest) {
        Pagination pagination = Pagination.builder()
                .limit(config.getContractMaxLimit())
                .offSet(config.getContractDefaultOffset())
                .build();
        ContractCriteria contractCriteria = ContractCriteria.builder()
                .contractNumber(contractRequest.getContract().getContractNumber())
                .status("INWORKFLOW")
                .tenantId(contractRequest.getContract().getTenantId())
                .requestInfo(contractRequest.getRequestInfo())
                .pagination(pagination)
                .build();
        List<Contract> contractsFromDB = contractRepository.getContracts(contractCriteria);

        if (!contractsFromDB.isEmpty()) {
            throw new CustomException("DUPLICATE_CONTRACT_REVISION_REQUEST", "Duplicate contract revision request");
        }
    }

    private void validateEndDateExtension(ContractRequest contractRequest, List<Contract> contractsFromDB) {
        for (Contract contract : contractsFromDB) {
            int comparisonResult = contractRequest.getContract().getEndDate().compareTo(contract.getEndDate());
            if (comparisonResult < 0) {
                throw new CustomException("END_DATE_NOT_EXTENDED", "End date should not be earlier than previous end date");
            }
        }
    }

    private void validateStartDate(ContractRequest contractRequest, List<Contract> contractsFromDB) {
        for (Contract contract : contractsFromDB) {
            if (!Objects.equals(contractRequest.getContract().getStartDate(), contract.getStartDate())) {
                throw new CustomException("START_DATE_DIFFERENT", "Start date of contract revision cannot be different");
            }
        }
    }

    public void validateEstimateLineItemId(List<Estimate> estimate, List<LineItems> contractLineItems) {
        Set<String> estimateDetailId = estimate.get(0).getEstimateDetails().stream().map(EstimateDetail::getId).collect(Collectors.toSet());
        for (LineItems lineItem : contractLineItems) {
            if (!estimateDetailId.contains(lineItem.getEstimateLineItemId())) {
                throw new CustomException("ESTIMATE_LINE_ITEM_ID_MISMATCH", "Estimate line item id not present in estimate : " + lineItem.getEstimateLineItemId());
            }
        }
    }


    public void validateLineItemRef(ContractRequest contractRequest) {
        List<Contract> contractsFromDB = contractRepository.getActiveContractsFromDB(contractRequest);
        Set<String> contractLineItemRef = contractRequest.getContract().getLineItems().stream().map(LineItems::getContractLineItemRef).collect(Collectors.toSet());
        for (LineItems lineItems : contractsFromDB.get(0).getLineItems()) {
            if (!contractLineItemRef.contains(lineItems.getContractLineItemRef())) {
                throw new CustomException("LINE_ITEM_REF_MISMATCH", "Contract Line Item Ref not present in previous contract " + lineItems.getContractLineItemRef());
            }
        }
        log.info("Validated LineItemRef for revised contract");
    }

    private void validateSupplementNumber(ContractRequest contractRequest) {
        if (contractRequest.getContract().getSupplementNumber() == null || contractRequest.getContract().getSupplementNumber().isEmpty()) {
            throw new CustomException("SUPPLEMENT_NUMBER_EMPTY", "Supplement number must not be empty");
        }

        Pagination pagination = Pagination.builder()
                .limit(config.getContractMaxLimit())
                .offSet(config.getContractDefaultOffset())
                .build();
        ContractCriteria contractCriteria = ContractCriteria.builder()
                .supplementNumber(contractRequest.getContract().getSupplementNumber())
                .status("INWORKFLOW")
                .tenantId(contractRequest.getContract().getTenantId())
                .requestInfo(contractRequest.getRequestInfo())
                .pagination(pagination)
                .build();
        List<Contract> contractsFromDB = contractRepository.getContracts(contractCriteria);
        if (contractsFromDB.isEmpty()) {
            throw new CustomException("SUPPLEMENT_NUMBER_NOT_PRESENT", "Supplement Number not present in DB");
        }
    }

    public void validateMeasurement(ContractRequest contractRequest, Estimate estimate) {
        String jsonPathForMeasurementCumulativeValue = "$.measurements[*].measures[?(@.targetId=='{{yourDynamicValue}}')].cumulativeValue";
        String jsonPathForMeasurementCurrentValue = "$.measurements[*].measures[?(@.targetId=='{{yourDynamicValue}}')].currentValue";
        String jsonPathForMeasurementWfStatus = "$.measurements[*].wfStatus";
        Object measurementResponse = measurementUtil.getMeasurementDetails(contractRequest);
        if (null != measurementResponse) {

            Map<?, ?> map = (Map<?, ?>) measurementResponse;
            if (map.containsKey("measurement")) {
                log.info("The 'measurement' key contains a value.");

                List<String> wfStatus;
                try {
                    wfStatus = JsonPath.read(measurementResponse, jsonPathForMeasurementWfStatus);
                } catch (Exception e) {
                    throw new CustomException("JSONPATH_ERROR", "Failed to parse measurement search response");
                }


                Map<String, EstimateDetail> estimateLineItemIdToEstimateDetail = estimate.getEstimateDetails().stream().
                        collect(Collectors.toMap(EstimateDetail::getId, estimateDetail -> estimateDetail));
                Map<String, BigDecimal> sorIdToCumulativeValueMap = new HashMap<>();
                Map<String, BigDecimal> sorIdToCurrentValueMap = new HashMap<>();
                Map<String, BigDecimal> sorIdToContractNoOfUnitMap = new HashMap<>();
                Map<String, BigDecimal> sorIdToEstimateNoOfUnitMap = new HashMap<>();
                Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap = new HashMap<>();
                contractRequest.getContract().getLineItems().forEach(lineItems -> {
                    if (lineItems.getContractLineItemRef() != null) {
                        List<Double> measurementCumulativeValue = new ArrayList<Double>();
                        List<Object> cummulativeValue = null;
                        EstimateDetail estimateDetail = estimateLineItemIdToEstimateDetail.get(lineItems.getEstimateLineItemId());
                        log.info("Sor Id To Estimate Details List Map created");
                        sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                        try {
                            cummulativeValue = JsonPath.read(measurementResponse, jsonPathForMeasurementCumulativeValue.replace("{{yourDynamicValue}}", lineItems.getContractLineItemRef()));
                        } catch (Exception e) {
                            throw new CustomException("JSONPATH_ERROR", "Failed to parse measurement search response");
                        }
                        convertValueToDouble(measurementCumulativeValue, cummulativeValue);
                        if (measurementCumulativeValue == null || measurementCumulativeValue.isEmpty()) {
                            log.info("No measurement found for the given estimate");
                        } else {
                            Double cumulativeValue = measurementCumulativeValue.get(0);
                            Double noOfUnit = lineItems.getNoOfunit();
                            Double estimateNoOfunit = estimateDetail.getNoOfunit();
                            if (estimateDetail.getIsDeduction()) {
                                cumulativeValue = cumulativeValue * -1;
                                noOfUnit = noOfUnit * -1;
                                estimateNoOfunit = estimateNoOfunit * -1;
                            }
                            log.info("Sor Id To Cumulative Value Map created");
                            sorIdToCumulativeValueMap.merge(estimateDetail.getSorId(), BigDecimal.valueOf(cumulativeValue), BigDecimal::add);
                            sorIdToContractNoOfUnitMap.merge(estimateDetail.getSorId(), BigDecimal.valueOf(noOfUnit), BigDecimal::add);
                            sorIdToEstimateNoOfUnitMap.merge(estimateDetail.getSorId(), BigDecimal.valueOf(estimateNoOfunit), BigDecimal::add);

                            if (!wfStatus.get(0).equalsIgnoreCase("APPROVED")) {
                                List<Double> measurementCurrentValue = new ArrayList<Double>();
                                List<Object> currentValue = null;

                                try {
                                    currentValue = JsonPath.read(measurementResponse, jsonPathForMeasurementCurrentValue.replace("{{yourDynamicValue}}", lineItems.getContractLineItemRef()));
                                } catch (Exception e) {
                                    throw new CustomException("JSONPATH_ERROR", "Failed to parse measurement search response");
                                }
                                convertValueToDouble(measurementCurrentValue, currentValue);
                                Double currenValue = measurementCurrentValue.get(0);
                                if (estimateDetail.getIsDeduction()) {
                                    currenValue = currenValue * -1;
                                }
                                log.info("Sor Id To Current Value Map created");
                                sorIdToCurrentValueMap.merge(estimateDetail.getSorId(), BigDecimal.valueOf(currenValue), BigDecimal::add);


                            }
                        }

                    }
                });
                estimate.getEstimateDetails().forEach(estimateDetail -> {
                    String sorId = estimateDetail.getSorId();
                    if (!wfStatus.get(0).equalsIgnoreCase("APPROVED")) {
                        BigDecimal totalValue = sorIdToCumulativeValueMap.get(sorId).subtract(sorIdToCurrentValueMap.get(sorId));
                        sorIdToCumulativeValueMap.put(sorId, totalValue);

                    }
                    log.info("Measurement Book is in Workflow");
                    if ((!sorIdToCumulativeValueMap.isEmpty() && !sorIdToContractNoOfUnitMap.isEmpty())) {
                        if (sorIdToContractNoOfUnitMap.get(sorId).compareTo(sorIdToCumulativeValueMap.get(sorId)) < 0) {
                            throw new CustomException("CUMULATIVE_VALUE_GREATER_THAN_CONTRACT_UNITS", "No of Unit of contract" +
                                    " should be greater than or equal to measurement book cumulative value. Retry after changing the value provided for this sor : " + sorId);
                        } else {
                            log.info("Validation Passed for the check applied on contract total noOfUnit and MB total cummulative value for a SOR linked in the estimate");
                        }

                    }

                    if (sorIdToEstimateNoOfUnitMap.get(sorId).compareTo(sorIdToCumulativeValueMap.get(sorId)) < 0) {
                        throw new CustomException("CUMULATIVE_VALUE_GREATER_THAN_ESTIMATE_DETAIL_UNITS", "No of Unit of estimate " +
                                "should be greater than or equal to measurement book cumulative value. Retry after changing  value for this sor : " + sorId);

                    } else {
                        log.info("Validation Passed for the check applied on estimate total noOfUnit and MB total cummulative value for a SOR linked in the estimate");
                    }


                });


                log.info("Validated measurements");
            }
        } else {
            log.info("No Measurement Book Present ");
        }

    }

private void validateRevisionLimit(List<Contract> contractFromDB) {
    if (contractFromDB.get(0).getVersionNumber() != null &&
            (contractFromDB.get(0).getVersionNumber() > config.getContractRevisionMaxLimit())) {
        throw new CustomException("CONTRACT_REVISION_MAX_LIMIT_REACHED",
                "Contract cannot be revised more than max limit :: " + config.getContractRevisionMaxLimit());
    }
}

    private void convertValueToDouble(List<Double> convertedValue, List<Object> measurementValue) {
        if (measurementValue != null) {
            for (Object value : measurementValue) {
                if (value instanceof Integer) {
                    convertedValue.add(Double.valueOf(value.toString()));
                } else {
                    convertedValue.add((Double) value);
                }

            }
        }

    }

}
