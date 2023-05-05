package org.egov.works.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.LineItemsRepository;
import org.egov.works.service.ContractService;
import org.egov.works.util.*;
import org.egov.works.repository.ContractRepository;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
    private ContractService contractService;

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

        // Validate estimateIds against estimate service and DB
        validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB(contractRequest);

        // Validate orgId against Organization service data
        validateOrganizationIdAgainstOrgService(contractRequest);

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

        // Validate provided contract for update should exist in DB
        validateContractAgainstDB(contractRequest);

        // Validate estimateIds against estimate service and DB
        validateUpdateRequestedEstimateIdsAgainstEstimateServiceAndDB(contractRequest);

        // Validate orgId against Organization service data
        validateOrganizationIdAgainstOrgService(contractRequest);

        log.info("Contract create request validated : contractId ["+contractRequest.getContract().getId()+"]");
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
        //List<Contract> fetchedContracts = contractService.searchContracts(contractCriteria);
        List<Contract> fetchedContracts = contractRepository.getContracts(contractCriteria);
        if(fetchedContracts.isEmpty()){
            log.error("Update:: Provided contract ["+contractId+"] not found");
            throw new CustomException("CONTRACT_NOT_FOUND","Provided contract ["+contractId+"] not found");
        }
        log.info("Update:: Provided contract ["+contractId+"] found in DB");

    }

    /**
     * Validate requested estimateIDs/estimateLineItemId/estimateAmountBreakupId against estimate service.
     * Also Make sure that provided estimateLineItemId are not associated with other contract.
     * and if they are associated it should be requested contract.
     * @param contractRequest
     */
    private void validateUpdateRequestedEstimateIdsAgainstEstimateServiceAndDB(ContractRequest contractRequest) {
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = validateRequestedEstimateIdsAgainstEstimateService(contractRequest);
        validateUpdateEstimateLineItemAssociationWithOtherContracts(contractRequest,fetchedEstimateIdWithEstimateDetailIds);
    }

    /**
     * Validate requested estimateIDs/estimateLineItemId/estimateAmountBreakupId against estimate service.
     * Make sure that provided estimateLineItemId are not associated with other contract.
     * @param contractRequest
     */
    private void validateCreateRequestedEstimateIdsAgainstEstimateServiceAndDB(ContractRequest contractRequest) {
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = validateRequestedEstimateIdsAgainstEstimateService(contractRequest);
        validateCreateEstimateLineItemAssociationWithOtherContracts(contractRequest,fetchedEstimateIdWithEstimateDetailIds);
    }

    private void validateRequestFieldsAgainstMDMS(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        RequestInfo requestInfo = contractRequest.getRequestInfo();

        //Fetch MDMS data
        Object mdmsData = fetchMDMSDataForValidation(requestInfo,tenantId);

        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(mdmsData, contract.getTenantId());

        // Validate executingAuthority against MDMS data
        validateExecutingAuthorityAgainstMDMS(mdmsData, contract.getExecutingAuthority());

        // Validate executingAuthority against MDMS data
        validateContractTypeAgainstMDMS(mdmsData, contract.getContractType());

        // Validate document type against MDMS date
        //validateDocumentTypeAgainstMDMS(mdmsData, contract.getDocuments());

        // Validate Officer In Charge role against MDMS data
        
        validateOfficerInChargeRoleAgainstMDMS(mdmsData, contractRequest);

        log.info("Request Fields validated against MDMS");
    }

    private void validateOfficerInChargeRoleAgainstMDMS(Object mdmsData, ContractRequest contractRequest) {

        String officerInChargeId = getOfficerInChargeIdFromAdditionalDetails(contractRequest.getContract().getAdditionalDetails());
        if(StringUtils.isEmpty(officerInChargeId)){
            log.error("Officer In-charge is not present, skipping further validation");
            return;
        }
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<String> roles = fetchRolesOfOfficerInCharge(requestInfo,tenantId,officerInChargeId);
        Set<String> distinctRoles = roles.stream().collect(Collectors.toSet());
        List<Object> oicRolesRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_OIC_ROLES_VERIFICATION);
        for(Object allowedRole : oicRolesRes){
            if(distinctRoles.contains(allowedRole.toString())){
                log.error("Officer In-charge is associated with required role");
                return;
            }
        }
        log.error("Officer In-charge is not associated with required role");
        throw new CustomException("MISSING_REQUIRED_ROLE", "Officer In-charge is not associated with required role");

    }

    private List<String> fetchRolesOfOfficerInCharge(RequestInfo requestInfo,String tenantId,String officerInChargeId) {
        return hrmsUtils.getRoleCodesByEmployeeId(requestInfo, tenantId, Collections.singletonList(officerInChargeId));
    }

    private String getOfficerInChargeIdFromAdditionalDetails( Object additionalDetails) {
        try {
            Optional<String> value = commonUtil.findValue(additionalDetails, OFFICER_IN_CHARGE_ID_CONSTANT);
            if( value.isPresent())
                return value.get();
        }
        catch (Exception ignore){ }
        return null;
    }

    private Object fetchMDMSDataForValidation(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        return mdmsUtils.fetchMDMSForValidation(requestInfo, rootTenantId);

    }
    private void validateTenantIdAgainstMDMS(Object mdmsData,String tenantId) {
        List<Object> tenantRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_TENANTS_VERIFICATION);
        if (CollectionUtils.isEmpty(tenantRes) || !tenantRes.contains(tenantId)){
            log.error("The tenant: " + tenantId + " is not present in MDMS");
            throw new CustomException("INVALID_TENANT","Invalid tenantId [" + tenantId + "]");
        }
        log.info("TenantId data validated against MDMS");
    }
//    private void validateDocumentTypeAgainstMDMS(Object mdmsData, List<Document> documents) {
//        List<Object> documentTypeAuthorityRes = parseMDMSData(mdmsData,JSON_PATH_FOR_DOCUMENT_TYPE_VERIFICATION);
//        for(Document document: documents){
//            String documentType = document.getDocumentType();
//            if (CollectionUtils.isEmpty(documentTypeAuthorityRes) || !documentTypeAuthorityRes.contains(documentType)){
//                log.error("The Document Type [" + documentType + "] is not present in MDMS");
//                throw new CustomException("INVALID_DOCUMENT_TYPE","The Document Type [" + documentType + "] is not present in MDMS");
//            }
//        }
//        log.info("Document Type data validated against MDMS");
//    }

    private void validateContractTypeAgainstMDMS(Object mdmsData, String contractType) {
        List<Object> contractTypeRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_CONTRACT_TYPE_VERIFICATION);
        if (CollectionUtils.isEmpty(contractTypeRes) || !contractTypeRes.contains(contractType)){
            log.error("The Contract Type [" + contractType + "] is not present in MDMS");
            throw new CustomException("INVALID_CONTRACT_TYPE","Invalid Contract Type [" + contractType + "]");
        }

        log.info("Contract Type data validated against MDMS");
    }

    private void validateExecutingAuthorityAgainstMDMS(Object mdmsData, String executingAuthority) {
        List<Object> executingAuthorityRes = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_CBO_ROLES_VERIFICATION);
        if (CollectionUtils.isEmpty(executingAuthorityRes) || !executingAuthorityRes.contains(executingAuthority)){
            log.error("The Executing Authority [" + executingAuthority + "] is not present in MDMS");
            throw new CustomException("INVALID_EXECUTING_AUTHORITY","Invalid Executing Authority [" + executingAuthority + "]");
        }

        log.info("Executing Authority data validated against MDMS");
    }

    /**
     * Make sure that provided estimateLineItemId are not associated with other contract.
     * @param contractRequest
     * @param estimateIdWithEstimateDetailIds
     */
    private void validateCreateEstimateLineItemAssociationWithOtherContracts(ContractRequest contractRequest, Map<String, Set<String>> estimateIdWithEstimateDetailIds) {
        Contract contract = contractRequest.getContract();
        List<String> estimatedLineItemIdsList = new ArrayList<>();
        List<LineItems> lineItems = contract.getLineItems();

        for(LineItems lineItem : lineItems){
            String estimateId = lineItem.getEstimateId();
            String estimateLineItemId = lineItem.getEstimateLineItemId();
            if(estimateLineItemId == null){
                estimatedLineItemIdsList.addAll(estimateIdWithEstimateDetailIds.get(estimateId));
            } else {
                estimatedLineItemIdsList.add(estimateLineItemId);
            }
        }
        ContractCriteria contractCriteria = ContractCriteria.builder().estimateLineItemIds(estimatedLineItemIdsList).build();
        List<LineItems> fetchedLineItems = lineItemsRepository.getLineItems(contractCriteria);
        List<LineItems> filteredLineItems = fetchedLineItems.stream().filter(e -> e.getStatus().equals(Status.ACTIVE)).collect(Collectors.toList());
        if(!filteredLineItems.isEmpty()){
            Set<String> collect = filteredLineItems.stream().map(e -> e.getEstimateLineItemId()).collect(Collectors.toSet());
            log.error("Estimate Line Items "+collect+" are already associated with other contract");
            throw new CustomException("INVALID_ESTIMATELINEITEMID","Estimate Line Items "+collect+" are already associated with other contract");
        }

        log.info("Create :: Estimate LineItem validation against other contracts done");
    }

    /**
     * Make sure that provided estimateLineItemId are not associated with other contract.
     * And if they are associated it should be requested contract.
     * @param contractRequest
     * @param estimateIdWithEstimateDetailIds
     */
    private void validateUpdateEstimateLineItemAssociationWithOtherContracts(ContractRequest contractRequest, Map<String, Set<String>> estimateIdWithEstimateDetailIds) {
        Contract contract = contractRequest.getContract();
        List<String> estimatedLineItemIds =  new ArrayList<>();;
        List<LineItems> lineItems = contract.getLineItems();

        for(LineItems lineItem : lineItems){
            String estimateId = lineItem.getEstimateId();
            String estimateLineItemId = lineItem.getEstimateLineItemId();
            if(estimateLineItemId == null){
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

        Object fetchedOrg = orgUtils.fetchOrg(requestInfo,tenantId,Collections.singletonList(orgId));
        List<Object> orgRes = commonUtil.readJSONPathValue(fetchedOrg,ORG_ORGANISATIONS_VALIDATION_PATH);

        if (CollectionUtils.isEmpty(orgRes) ){
            log.error("Org ["+orgId+"] is not present");
            throw new CustomException("INVALID_ORGID","Org ["+orgId+"] is not present");
        }
        log.info("Org ["+orgId+"] is validated successfully");
    }

    private Map<String, Set<String>> validateRequestedEstimateIdsAgainstEstimateService(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<LineItems> lineItems = contract.getLineItems();
        Set<String> providedEstimateIds = lineItems.stream().map(e -> e.getEstimateId()).collect(Collectors.toSet());
        List<Estimate> fetchedActiveEstimates = fetchActiveEstimates(requestInfo,tenantId,providedEstimateIds);
        validateProvidedEstimateStatusAgainstFetchedActiveEstimates(providedEstimateIds,fetchedActiveEstimates);

        Map<String, List<LineItems>> providedLineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        Map<String, List<Estimate>> fetchedActiveEstimatesMap = fetchedActiveEstimates.stream().collect(Collectors.groupingBy(Estimate::getId));
        Map<String, Set<String>> fetchedEstimateIdWithEstimateDetailIds = getFetchedEstimateIdWithEstimateDetailIds(fetchedActiveEstimatesMap);
        Map<String, Set<String>> fetchedEstimateDetailIdWithAccountDetailIds = getFetchedEstimateDetailIdWithAccountDetailIds(fetchedActiveEstimatesMap);

        for(String estimateId : providedEstimateIds){
            for(LineItems lineItem :providedLineItemsMap.get(estimateId)) {
                String estimateLineItemId = lineItem.getEstimateLineItemId();
                if (estimateLineItemId != null) {
                    if (fetchedEstimateIdWithEstimateDetailIds.get(estimateId)!=null && !fetchedEstimateIdWithEstimateDetailIds.get(estimateId).contains(estimateLineItemId)) {
                        log.error("LineItemId [" + estimateLineItemId + "] is invalid for estimate ["+estimateId+"]");
                        throw new CustomException("INVALID_ESTIMATELINEITEMID", "LineItemId [" + estimateLineItemId + "] is invalid for estimate ["+estimateId+"]");
                    }
                    List<AmountBreakup> amountBreakups = lineItem.getAmountBreakups();
                    for(AmountBreakup amountBreakup: amountBreakups){
                        String estimateAmountBreakupId = amountBreakup.getEstimateAmountBreakupId();
                        if (fetchedEstimateDetailIdWithAccountDetailIds.get(estimateLineItemId)!=null && !fetchedEstimateDetailIdWithAccountDetailIds.get(estimateLineItemId).contains(estimateAmountBreakupId)) {
                            log.error("EstimateAmountBreakupId [" + estimateAmountBreakupId + "] is invalid for EstimateLineItemId ["+estimateLineItemId+"]");
                            throw new CustomException("INVALID_ESTIMATEAMOUNTBREAKUPID", "EstimateAmountBreakupId [" + estimateAmountBreakupId + "] is invalid for EstimateLineItemId ["+estimateLineItemId+"]");
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
        for(String providedEstimateId : providedEstimateIds){
            if(!fetchedEstimateIds.contains(providedEstimateId)){
                log.error("Provided estimate ["+ providedEstimateId +"] is not active");
                throw new CustomException("ESTIMATE_NOT_ACTIVE", "Provided estimate ["+ providedEstimateId +"] is not active");
            }
        }
    }

    private List<Estimate> fetchActiveEstimates(RequestInfo requestInfo, String tenantId, Set<String> providedEstimateIds) {
        List<Estimate> fetchedEstimates = estimateServiceUtil.fetchActiveEstimates(requestInfo, tenantId, providedEstimateIds);
        if(fetchedEstimates.isEmpty()){
            log.error("Provided estimates are either inactive or not found");
            throw new CustomException("ACTIVE_ESTIMATE_NOT_FOUND", "Provided estimates are either inactive or not found");
        }
        return fetchedEstimates;
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

//        Object additionalDetails = contract.getAdditionalDetails();
//        if(additionalDetails == null){
//            log.error("Additional Details object is mandatory");
//            errorMap.put("CONTRACT.ADDITIONAL_DETAILS", "Additional Details object is mandatory");
//        }
//
//        Optional<String> projectName = commonUtil.findValue(additionalDetails, PROJECT_NAME_CONSTANT);
//        if (!projectName.isPresent()) {
//            log.error("Project Name is mandatory");
//            errorMap.put("CONTRACT.ADDITIONAL_DETAILS.PROJECT_NAME", "Project Name is mandatory");
//        }
//
//        Optional<String> projectType = commonUtil.findValue(additionalDetails, PROJECT_TYPE_CONSTANT);
//        if (!projectType.isPresent()) {
//            log.error("Project Type is mandatory");
//            errorMap.put("CONTRACT.ADDITIONAL_DETAILS.PROJECT_TYPE", "Project Type is mandatory");
//        }
//
//        Optional<String> projectId = commonUtil.findValue(additionalDetails, PROJECT_ID_CONSTANT);
//        if (!projectId.isPresent()) {
//            log.error("Project Id is mandatory");
//            errorMap.put("CONTRACT.ADDITIONAL_DETAILS.PROJECT_ID", "Project ID is mandatory");
//        }
//
//        Optional<String> ward = commonUtil.findValue(additionalDetails, WARD_CONSTANT);
//        if (!ward.isPresent()) {
//            log.error("Ward is mandatory");
//            errorMap.put("CONTRACT.ADDITIONAL_DETAILS.WARD", "Ward is mandatory");
//        }
//
//        Optional<String> orgName = commonUtil.findValue(additionalDetails, ORG_NAME_CONSTANT);
//        if (!orgName.isPresent()) {
//            log.error("Org Name is mandatory");
//            errorMap.put("CONTRACT.ADDITIONAL_DETAILS.ORG_NAME", "Org Name is mandatory");
//        }

        List<LineItems> lineItems = contract.getLineItems();

        if(lineItems == null || lineItems.isEmpty()){
            log.error("LineItem is mandatory");
            errorMap.put("CONTRACT.LINE_ITEM", "LineItem is mandatory");
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

        log.info("Multiple tenantId validation done");
    }

    public void validateSearchContractRequest(ContractCriteria contractCriteria) {

        if (contractCriteria == null || contractCriteria.getRequestInfo() == null) {
            log.error("Contract search criteria request is mandatory");
            throw new CustomException("CONTRACT_SEARCH_CRITERIA_REQUEST", "Contract search criteria request is mandatory");
        }

        RequestInfo requestInfo=contractCriteria.getRequestInfo();

        //validate request info
        log.info("Search :: validate request info");
        validateRequestInfo(requestInfo);

        //validate request parameters
        log.info("Search :: validate request parameters");
        validateSearchContractRequestParameters(contractCriteria);

        //validate tenantId with MDMS
        log.info("Search :: validate tenantId with MDMS");
        validateSearchTenantIdAgainstMDMS(requestInfo,contractCriteria.getTenantId());

        log.info("Search :: validation done");
    }

    private void validateSearchTenantIdAgainstMDMS(RequestInfo requestInfo, String tenantId) {
        Object mdmsData = fetchMDMSDataForValidation(requestInfo, tenantId);
        validateTenantIdAgainstMDMS(mdmsData,tenantId);
    }

    private void validateSearchContractRequestParameters(ContractCriteria contractCriteria){

        if (StringUtils.isBlank(contractCriteria.getTenantId())) {
            log.error("Tenant is mandatory");
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
    }
}
