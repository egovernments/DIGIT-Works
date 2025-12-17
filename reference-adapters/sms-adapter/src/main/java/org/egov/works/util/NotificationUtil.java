package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;

import org.egov.tracer.model.CustomException;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.models.BillRequest;
import org.egov.works.models.MusterRollRequest;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.Constants.*;
import static org.egov.works.config.Constants.CONTACT_MOBILE_NUMBER;
import static org.egov.works.config.Constants.CONTRACT_NUMBER;

@Component
@Slf4j
public class NotificationUtil {

    @Autowired
    private NotificationServiceConfiguration config;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ObjectMapper mapper;


    public Map<String, String> getCBOContactPersonDetails(BillRequest billRequest){
        RequestInfo requestInfo = billRequest.getRequestInfo();
        String tenantId = billRequest.getBill().getTenantId();
        String contractNumber = CONTRACT_NUMBER;
        if(null != billRequest.getBill().getReferenceId())
            contractNumber = billRequest.getBill().getReferenceId().split("_")[0];
        String orgId = fetchOrgId(requestInfo, tenantId, contractNumber);
        Map<String, String> CBODetails = fetchCBODetails(requestInfo, tenantId, orgId);
        return CBODetails;
    }

    public String fetchOrgId(RequestInfo requestInfo, String tenantId, String contractNumber){
        StringBuilder url = getOrgIdWithContractIdUrl();
        Object contractSearchRequest = getOrgIdWithContractIdRequest(requestInfo, tenantId, contractNumber);
        final Object contractRes = restRepo.fetchResult(url, contractSearchRequest);
        String orgId;
        try{
            JSONArray jsonArray = JsonPath.read(contractRes, ORG_ID_PATH);
            orgId = jsonArray.get(0).toString();
        }catch (Exception e){
            throw new CustomException("PARSING_CONTRACT_ERROR", "Failed to parse response from contract");
        }

        return orgId;
    }

    private Object getOrgIdWithContractIdRequest(RequestInfo requestInfo, String tenantId, String contractNumber){
        ObjectNode contractSearchCriteriaNode = mapper.createObjectNode();

        contractSearchCriteriaNode.putPOJO(REQUEST_INFO, requestInfo);
        contractSearchCriteriaNode.put(CONTRACT_NUMBER, contractNumber);
        contractSearchCriteriaNode.put(TENANT_ID, tenantId);

        return contractSearchCriteriaNode;
    }

    private StringBuilder getOrgIdWithContractIdUrl(){
        StringBuilder builder = new StringBuilder(config.getContractHost());
        builder.append(config.getContractEndpoint());
        return builder;
    }

    private Map<String,String> fetchCBODetails(RequestInfo requestInfo, String tenantId, String orgId){
        StringBuilder url = getCBODetailsFromOrgUrl();
        Object orgSearchRequest = getCBODetailsRequest(requestInfo, tenantId, orgId);

        log.info("Organisation search request -> {}", orgSearchRequest);
        Object orgRes = restRepo.fetchResult(url, orgSearchRequest);

        Map<String, String> orgDetails = new HashMap<>();
        List<String> orgName = null;
        List<String> contactName = null;
        List<String> contactMobileNumber = null;

        try{
            orgName = JsonPath.read(orgRes, ORG_NAME_PATH);
            contactName = JsonPath.read(orgRes, CONTACT_NAME_PATH);
            contactMobileNumber = JsonPath.read(orgRes, CONTACT_MOBILE_NUMBER_PATH);
        } catch (Exception e){
            throw new CustomException("PARSING_ORG_ERROR", "Failed to parse response from organisation");
        }

        orgDetails.put(ORG_NAME, orgName.get(0));
        orgDetails.put(CONTACT_NAME, contactName.get(0));
        orgDetails.put(CONTACT_MOBILE_NUMBER,contactMobileNumber.get(0));

        return orgDetails;

    }

    private StringBuilder getCBODetailsFromOrgUrl(){
        StringBuilder builder = new StringBuilder(config.getOrganisationServiceHost());
        builder.append(config.getOrganisationServiceEndpoint());
        return builder;
    }

    private Object getCBODetailsRequest(RequestInfo requestInfo, String tenantId, String orgId){
        ObjectNode orgSearchRequestNode = mapper.createObjectNode();

        ObjectNode orgObjNode = mapper.createObjectNode();
        ArrayNode ids = mapper.createArrayNode();
        ids.add(orgId);

        orgObjNode.put(TENANT_ID, tenantId);
        orgObjNode.putPOJO(ID, ids);

        orgSearchRequestNode.putPOJO(REQUEST_INFO, requestInfo);
        orgSearchRequestNode.putPOJO(SEARCH_CRITERIA,orgObjNode);

        return orgSearchRequestNode;
    }

    public Map<String, String> getVendorContactPersonDetails(RequestInfo requestInfo, String tenantId, String orgId){
        Map<String, String> VendorDetails = fetchCBODetails(requestInfo, tenantId, orgId);
        return VendorDetails;
    }

    public Map<String, String> getCBOContactPersonDetails(MusterRollRequest musterRollRequest){
        String orgId = fetchOrgId(musterRollRequest);
        Map<String, String> CBODetails = fetchCBODetails(musterRollRequest, orgId);
        return CBODetails;
    }
    public String fetchOrgId(MusterRollRequest musterRollRequest){
        StringBuilder url = getOrgIdWithContractIdUrl();
        Object contractSearchRequest = getOrgIdWithContractIdRequest(musterRollRequest);
        final Object contractRes = restRepo.fetchResult(url, contractSearchRequest);
        String orgId;
        try{
            JSONArray jsonArray = JsonPath.read(contractRes, ORG_ID_PATH);
            orgId = jsonArray.get(0).toString();
        }catch (Exception e){
            throw new CustomException("PARSING_CONTRACT_ERROR", "Failed to parse response from contract");
        }

        return orgId;
    }

    private Object getOrgIdWithContractIdRequest(MusterRollRequest musterRollRequest){
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        String contractNumber = musterRollRequest.getMusterRoll().getReferenceId();
        String tenantId = musterRollRequest.getMusterRoll().getTenantId();

        // Create request object
        ObjectNode contractSearchCriteriaNode = mapper.createObjectNode();

        contractSearchCriteriaNode.putPOJO(REQUEST_INFO, requestInfo);
        contractSearchCriteriaNode.put(CONTRACT_NUMBER, contractNumber);
        contractSearchCriteriaNode.put(TENANT_ID, tenantId);

        return contractSearchCriteriaNode;
    }

    private Map<String,String> fetchCBODetails(MusterRollRequest musterRollRequest, String orgId){
        StringBuilder url = getCBODetailsFromOrgUrl();
        Object orgSearchRequest = getCBODetailsRequest(musterRollRequest, orgId);

        log.info("Organisation search request -> {}", orgSearchRequest);
        Object orgRes = restRepo.fetchResult(url, orgSearchRequest);

        Map<String, String> orgDetails = new HashMap<>();
        List<String> orgName = null;
        List<String> contactName = null;
        List<String> contactMobileNumber = null;

        try{
            orgName = JsonPath.read(orgRes, ORG_NAME_PATH);
            contactName = JsonPath.read(orgRes, CONTACT_NAME_PATH);
            contactMobileNumber = JsonPath.read(orgRes, CONTACT_MOBILE_NUMBER_PATH);
        } catch (Exception e){
            throw new CustomException("PARSING_ORG_ERROR", "Failed to parse response from organisation");
        }

        orgDetails.put(ORG_NAME, orgName.get(0));
        orgDetails.put(CONTACT_NAME, contactName.get(0));
        orgDetails.put(CONTACT_MOBILE_NUMBER,contactMobileNumber.get(0));

        return orgDetails;
    }
    private Object getCBODetailsRequest(MusterRollRequest musterRollRequest, String orgId){
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        String tenantId = musterRollRequest.getMusterRoll().getTenantId();

        ObjectNode orgSearchRequestNode = mapper.createObjectNode();
        ArrayNode ids = mapper.createArrayNode();
        ids.add(orgId);

        ObjectNode orgObjNode = mapper.createObjectNode();
        orgObjNode.put(TENANT_ID, tenantId);
        orgObjNode.putPOJO(ID, ids);

        orgSearchRequestNode.putPOJO(REQUEST_INFO, requestInfo);
        orgSearchRequestNode.putPOJO(SEARCH_CRITERIA,orgObjNode);

        return orgSearchRequestNode;
    }
    public String getExpenseAmount(MusterRollRequest musterRollRequest){
        StringBuilder url = getExpenseUrl();
        Object expenseSearchRequest = getExpenseRequest(musterRollRequest);
        final Object expenseRes = restRepo.fetchResult(url, expenseSearchRequest);
        Integer amount = null;
        try {
            amount = JsonPath.read(expenseRes, "$.calculation.totalAmount");
        }catch (Exception e){
            throw new CustomException("EXPENSE_PARSING_ERROR", "Error while parsing expense object");
        }
        String totalAmount = amount.toString();
        return totalAmount;
    }
    public StringBuilder getExpenseUrl(){
        StringBuilder url = new StringBuilder(config.getExpenseCalculatorServiceHost())
                .append(config.getExpenseCalculatorServiceEndpoint());
        return url;
    }

    public Object getExpenseRequest(MusterRollRequest musterRollRequest){
        ObjectNode expenseRequestObjNode = mapper.createObjectNode();
        ArrayNode musterRollIds = mapper.createArrayNode();
        ObjectNode criteriaObjNode = mapper.createObjectNode();

        musterRollIds.add(musterRollRequest.getMusterRoll().getId());

        criteriaObjNode.putPOJO("musterRollId", musterRollIds);
        criteriaObjNode.put("tenantId", musterRollRequest.getMusterRoll().getTenantId());

        expenseRequestObjNode.putPOJO(REQUEST_INFO, musterRollRequest.getRequestInfo());
        expenseRequestObjNode.putPOJO("criteria", criteriaObjNode);
        return expenseRequestObjNode;
    }

}
