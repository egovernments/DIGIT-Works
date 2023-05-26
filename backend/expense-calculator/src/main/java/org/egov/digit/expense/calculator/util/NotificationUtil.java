package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.ContractResponse;
import org.egov.digit.expense.calculator.web.models.PurchaseBillRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NotificationUtil {

    @Autowired
    private ExpenseCalculatorConfiguration config;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ContractUtils contractUtils;

    public static final String REQUEST_INFO = "RequestInfo";
    public static final String TENANT_ID = "tenantId";
    public static final String ORG_ID = "orgId";
    public static final String SEARCH_CRITERIA = "SearchCriteria";
    public static final String ORG_NAME_PATH = "$.organisations.*.name";
    public static final String CONTACT_NAME_PATH = "$.organisations.*.contactDetails.*.contactName";
    public static final String CONTACT_MOBILE_NUMBER_PATH = "$.organisations.*.contactDetails.*.contactMobileNumber";
    public static final String ORG_NAME = "orgName";
    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_MOBILE_NUMBER = "contactMobileNumber";


    public Map<String, String> getCBOContactPersonDetails(PurchaseBillRequest purchaseBillRequest){
        String orgId = fetchOrgId(purchaseBillRequest);
        Map<String, String> CBODetails = fetchCBODetails(purchaseBillRequest, orgId);
        return CBODetails;
    }

    public String fetchOrgId(PurchaseBillRequest purchaseBillRequest){
        ContractResponse contractResponse = contractUtils.fetchContract(purchaseBillRequest.getRequestInfo(),
                purchaseBillRequest.getBill().getTenantId(), purchaseBillRequest.getBill().getContractNumber());

        return contractResponse.getContracts().get(0).getOrgId();
    }

    private Map<String,String> fetchCBODetails(PurchaseBillRequest purchaseBillRequest, String orgId){
        StringBuilder url = getCBODetailsFromOrgUrl();
        Object orgSearchRequest = getCBODetailsRequest(purchaseBillRequest, orgId);

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

    private Object getCBODetailsRequest(PurchaseBillRequest purchaseBillRequest, String orgId){
        RequestInfo requestInfo = purchaseBillRequest.getRequestInfo();
        String tenantId = purchaseBillRequest.getBill().getTenantId();

        ObjectNode orgSearchRequestNode = mapper.createObjectNode();

        ObjectNode orgObjNode = mapper.createObjectNode();
        orgObjNode.put(TENANT_ID, tenantId);
        orgObjNode.put(ORG_ID, orgId);

        orgSearchRequestNode.putPOJO(REQUEST_INFO, requestInfo);
        orgSearchRequestNode.putPOJO(SEARCH_CRITERIA,orgObjNode);

        return orgSearchRequestNode;
    }

}
