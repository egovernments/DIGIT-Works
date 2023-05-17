package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.*;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillCriteria;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.BillSearchRequest;
import org.egov.digit.expense.calculator.web.models.Contract;
import org.egov.digit.expense.calculator.web.models.ContractCriteria;
import org.egov.digit.expense.calculator.web.models.ContractResponse;
import org.egov.digit.expense.calculator.web.models.LineItem;
import org.egov.digit.expense.calculator.web.models.LineItems;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollResponse;
import org.egov.digit.expense.calculator.web.models.Order;
import org.egov.digit.expense.calculator.web.models.Pagination;
import org.egov.tracer.model.CustomException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.MUSTER_ROLL_ID_JSON_PATH;

@Component
@Slf4j
public class ExpenseCalculatorUtil {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    @Autowired
    private ExpenseCalculatorRepository expenseCalculatorRepository;

    public List<String> fetchListOfMusterRollIds(RequestInfo requestInfo, String tenantId, List<String> musterRollId, boolean onlyApproved) {
        StringBuilder url = null;
        if(onlyApproved){
            url = getApprovedMusterRollURI(tenantId,musterRollId);
        } else {
            url = getMusterRollURI(tenantId, musterRollId);
        }
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        List<String> fetchedMusterRollIds = null;
        try {
            fetchedMusterRollIds = JsonPath.read(responseObj, MUSTER_ROLL_ID_JSON_PATH);
        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse muster roll response");
        }
        return fetchedMusterRollIds;
    }

    public List<MusterRoll> fetchMusterRollByIds(RequestInfo requestInfo, String tenantId, List<String> musterRollId, boolean onlyApproved) {
        StringBuilder url = null;
        if(onlyApproved){
            url = getApprovedMusterRollURI(tenantId, musterRollId);
        } else{
            url = getMusterRollURI(tenantId, musterRollId);
        }
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        MusterRollResponse response = mapper.convertValue(responseObj, MusterRollResponse.class);
        return response.getMusterRolls();
    }



    private StringBuilder getApprovedMusterRollURI(String tenantId, List<String> musterRollId) {
        StringBuilder builder = new StringBuilder(configs.getMusterRollHost());
        builder.append(configs.getMusterRollEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&musterRollStatus=");
        builder.append("APPROVED");
        builder.append("&ids=");
        builder.append(String.join(",",musterRollId));

        return builder;
    }

    private StringBuilder getMusterRollURI(String tenantId, List<String> musterRollId) {
        StringBuilder builder = new StringBuilder(configs.getMusterRollHost());
        builder.append(configs.getMusterRollEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&ids=");
        builder.append(String.join(",",musterRollId));

        return builder;
    }

    private StringBuilder getMusterRollURI(String tenantId, String contractId) {
        StringBuilder builder = new StringBuilder(configs.getMusterRollHost());
        builder.append(configs.getMusterRollEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&referenceId=");
        builder.append(contractId);

        return builder;
    }

    public List<String> fetchMusterByContractId(RequestInfo requestInfo, String tenantId, String contractId) {
        StringBuilder url = getMusterRollURI(tenantId, contractId);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        MusterRollResponse response = mapper.convertValue(responseObj, MusterRollResponse.class);
        List<String> musterrollIds = new ArrayList<>();
        if (response != null && !CollectionUtils.isEmpty(response.getMusterRolls())) {
            musterrollIds = response.getMusterRolls().stream().map(muster -> muster.getMusterRollNumber()).collect(Collectors.toList());
        }
        return musterrollIds;
    }

    public List<Contract> fetchContract(RequestInfo requestInfo, String tenantId, String contractId) {
        StringBuilder url = searchURI(configs.getContractHost(), configs.getContractSearchEndPoint());
        Pagination pagination = Pagination.builder().limit(100).build();
        ContractCriteria searchCriteria = ContractCriteria.builder().requestInfo(requestInfo).tenantId(tenantId)
                .contractNumber(contractId).pagination(pagination).build();
        Object responseObj = restRepo.fetchResult(url, searchCriteria);
        ContractResponse response = mapper.convertValue(responseObj, ContractResponse.class);
        return response != null ? response.getContracts() : null;
    }
    
   /**
    * TODO:This needs to be revisited May 2023.
    * We need to send the project name, ward, locality etc.. to the billing indexer. The way this has been 
    * done is via additionalDetails object of the bill. The contract additionalDetails object holds all this info
    * and we are now sending it onward to the Billing service. But if the UI implementation changes and these
    * values are not sent via additionalDetails, then the Inbox/Search etc.. will fail. 
    * @param requestInfo
    * @param tenantId
    * @param contractId
    * @return
    */
    public Object getContractAdditionalDetails(RequestInfo requestInfo, String tenantId, String contractId){
    	List<Contract> contracts = fetchContract(requestInfo,tenantId,contractId);
    	Contract contract = contracts.get(0);
    	Object additional = null;
    	if(contract!=null) {
    		additional = contract.getAdditionalDetails();
    	}
    	return additional;
    }
    
    public List<Bill> fetchBillsByProject(RequestInfo requestInfo, String tenantId, List<String> projects) {
    	//log.info("Fetching unique project numbers from the calculator repository for contractId " + contractId);
    	// fetch the bill id from the calculator DB
        List<String> billIds = expenseCalculatorRepository.getBillsByProjectNumber(tenantId, projects);
        StringBuilder url = searchURI(configs.getBillHost(), configs.getExpenseBillSearchEndPoint());
        Pagination pagination = Pagination.builder().limit(configs.getDefaultLimit()).offSet(configs.getDefaultOffset()).order(Order.ASC).build();
        
        //Only fetch active bills
        BillCriteria billCriteria = BillCriteria.builder()
        		.tenantId(tenantId)
        		.status("ACTIVE")
                .ids(new HashSet<>(billIds))
                .build();
        BillSearchRequest billSearchRequest = BillSearchRequest.builder().requestInfo(requestInfo)
                .billCriteria(billCriteria).tenantId(tenantId).pagination(pagination).build();
        log.info("Calling expense service search for billIds. Request is: " + billSearchRequest.toString());
        Object responseObj = restRepo.fetchResult(url, billSearchRequest);
        log.info("Response from billing service is: " + responseObj.toString());
        BillResponse response = mapper.convertValue(responseObj, BillResponse.class);
        return response != null ? response.getBills() : null;

    }

    

    public List<Bill> fetchBills(RequestInfo requestInfo, String tenantId, String contractId) {
    	log.info("Fetching bills from the calculator repository for contractId " + contractId);
    	// fetch the bill id from the calculator DB
        List<String> billIds = expenseCalculatorRepository.getBills(contractId, tenantId);
        if(billIds.isEmpty()) {     
        	log.info(String.format("There are 0 bills in the calculator for contractId %s and tenantId %s" ,contractId,tenantId));
        	throw new CustomException("SUPERVISION_BILL_NOT_GENERATED",
					"No bills present for which supervision bill can be created");
        }
        StringBuilder url = searchURI(configs.getBillHost(), configs.getExpenseBillSearchEndPoint());
        Pagination pagination = Pagination.builder().limit(configs.getDefaultLimit()).offSet(configs.getDefaultOffset()).order(Order.ASC).build();
        
        //Only fetch active bills
        BillCriteria billCriteria = BillCriteria.builder()
        		.tenantId(tenantId)
        		.status("ACTIVE")
                .ids(new HashSet<>(billIds))
                .build();
        BillSearchRequest billSearchRequest = BillSearchRequest.builder().requestInfo(requestInfo)
                .billCriteria(billCriteria).tenantId(tenantId).pagination(pagination).build();
        log.info("Calling expense service search for billIds. Request is: " + billSearchRequest.toString());
        Object responseObj = restRepo.fetchResult(url, billSearchRequest);
        log.info("Response from billing service is: " + responseObj.toString());
        BillResponse response = mapper.convertValue(responseObj, BillResponse.class);
        return response != null ? response.getBills() : null;

    }

    public List<Bill> fetchBillsWithBillIds(RequestInfo requestInfo, String tenantId, List<String> billIds) {

        StringBuilder url = searchURI(configs.getBillHost(), configs.getExpenseBillSearchEndPoint());
        List<Bill> bills=new ArrayList<>();

        Pagination pagination = Pagination.builder().limit(configs.getDefaultLimit()).offSet(configs.getDefaultOffset()).order(Order.ASC).build();
        BillCriteria billCriteria = BillCriteria.builder().tenantId(tenantId)
                    .ids(new HashSet<>(billIds)).build();
            BillSearchRequest billSearchRequest = BillSearchRequest.builder().requestInfo(requestInfo)
                    .billCriteria(billCriteria).tenantId(tenantId).pagination(pagination).build();
            Object responseObj = restRepo.fetchResult(url, billSearchRequest);

        BillResponse response = mapper.convertValue(responseObj, BillResponse.class);

        if(response!=null) {
            bills.addAll(response.getBills());
        }
        else {
            throw new CustomException("Bill_Search_Error","Error in bill search");
        }

        return bills;
    }

    private StringBuilder searchURI(String host, String endpoint) {
        StringBuilder builder = new StringBuilder(host);
        builder.append(endpoint);
        return builder;
    }



}