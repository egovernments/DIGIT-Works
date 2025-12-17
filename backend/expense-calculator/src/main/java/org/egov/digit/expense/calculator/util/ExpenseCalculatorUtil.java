package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.*;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.ApplicableCharge;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillCriteria;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.BillSearchRequest;
import org.egov.digit.expense.calculator.web.models.HeadCode;
import org.egov.digit.expense.calculator.web.models.LineItem;
import org.egov.digit.expense.calculator.web.models.Order;
import org.egov.digit.expense.calculator.web.models.Pagination;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.contract.ContractCriteria;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.egov.works.services.common.models.musterroll.MusterRollResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_APPLICABLE_CHARGES;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_HEAD_CODES;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.MDMS_APPLICABLE_CHARGES;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.MDMS_HEAD_CODES;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.MUSTER_ROLL_ID_JSON_PATH;

@Component
@Slf4j
public class ExpenseCalculatorUtil {

    private final ObjectMapper mapper;
    private final ServiceRequestRepository restRepo;
    
    private final MdmsUtils mdmsUtils;
    
    private final CommonUtil commonUtil;

    private final ExpenseCalculatorConfiguration configs;

    private final ExpenseCalculatorRepository expenseCalculatorRepository;
    private static final String TENANT_ID_CLAUSE = "?tenantId=";
    private static final String SERVICE_PLACEHOLDER = "] and service [";
    private static final String INVALID_HEAD_CODE = "INVALID_HEAD_CODE";
    private static final String INVALID_HEAD_CODE_PLACEHOLDER = "Invalid head code [";
    private static final String FOR_SERVICE_PLACEHOLDER = "] for service [";
    
    @Autowired
    public ExpenseCalculatorUtil(ObjectMapper mapper, ServiceRequestRepository restRepo, MdmsUtils mdmsUtils, CommonUtil commonUtil, ExpenseCalculatorConfiguration configs, ExpenseCalculatorRepository expenseCalculatorRepository) {
        this.mapper = mapper;
        this.restRepo = restRepo;
        this.mdmsUtils = mdmsUtils;
        this.commonUtil = commonUtil;
        this.configs = configs;
        this.expenseCalculatorRepository = expenseCalculatorRepository;
    }

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
        builder.append(TENANT_ID_CLAUSE);
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
        builder.append(TENANT_ID_CLAUSE);
        builder.append(tenantId);
        builder.append("&ids=");
        builder.append(String.join(",",musterRollId));

        return builder;
    }

    private StringBuilder getMusterRollURI(String tenantId, String contractId) {
        StringBuilder builder = new StringBuilder(configs.getMusterRollHost());
        builder.append(configs.getMusterRollEndPoint());
        builder.append(TENANT_ID_CLAUSE);
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
            musterrollIds = response.getMusterRolls().stream().map(MusterRoll::getMusterRollNumber).collect(Collectors.toList());
        }
        return musterrollIds;
    }

    public List<Contract> fetchContract(RequestInfo requestInfo, String tenantId, String contractId) {
        StringBuilder url = searchURI(configs.getContractHost(), configs.getContractSearchEndPoint());
        org.egov.works.services.common.models.contract.Pagination pagination = org.egov.works.services.common.models.contract.Pagination.builder().limit(100).build();
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
    
    public LineItem buildPayableLineItem(BigDecimal amount, String tenantId, String headCode) {
        return LineItem.builder()
                 .amount(amount)
                 .paidAmount(BigDecimal.ZERO)
                 .tenantId(tenantId)
                 .isLineItemPayable(true)
                 .type(LineItem.TypeEnum.PAYABLE)
                 .headCode(headCode)
                 .build();
     }
    
    public String getCalculationType(String headCode, List<ApplicableCharge> applicableCharges, String businessService) {
        for(ApplicableCharge applicableCharge : applicableCharges) {
            if(applicableCharge.getCode().equalsIgnoreCase(headCode) && applicableCharge.getService().equals(businessService)) {
            	String calculationType = applicableCharge.getCalculationType();
                if (StringUtils.isBlank(calculationType)) {
                    log.error("CALCULATION_TYPE_MISSING","MDMS::calculationType missing for head code [" + headCode +SERVICE_PLACEHOLDER+ businessService +"]");
                    throw new CustomException("CALCULATION_TYPE_MISSING","MDMS::calculationType missing for head code [" + headCode +SERVICE_PLACEHOLDER+businessService+"]");
                } else {
                    return calculationType;
                }
            }
        }
        log.error(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+businessService+"]");
        throw new CustomException(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+businessService+"]");
    }
    
    public String getDeductionValue(String headCode, List<ApplicableCharge> applicableCharges) {
        for(ApplicableCharge applicableCharge : applicableCharges) {
            if(applicableCharge.getCode().equalsIgnoreCase(headCode)){
                return applicableCharge.getValue();
            }
        }
        log.error("MISSING_HEAD_CODE","HeadCode ["+headCode+"] missing in applicable charge MDMS");
        throw new CustomException("MISSING_HEAD_CODE","HeadCode ["+headCode+"] missing in applicable charge MDMS");
    }
    
    public String getHeadCodeCategory(String headCode, List<HeadCode> headCodes, String businessService) {
        for(HeadCode hCode : headCodes) {
            if(hCode.getCode().equalsIgnoreCase(headCode)){
                String category = hCode.getCategory();
                if (StringUtils.isBlank(category)) {
                    log.error("CATEGORY_MISSING","MDMS::category missing for head code [" + headCode +SERVICE_PLACEHOLDER+businessService+"]");
                    throw new CustomException("CATEGORY_MISSING","MDMS::category missing for head code [" + headCode +SERVICE_PLACEHOLDER+businessService+"]");
                } else {
                    return category;
                }
            }
        }
        log.error(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+businessService+"]");
        throw new CustomException(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+businessService+"]");
    }

    private StringBuilder searchURI(String host, String endpoint) {
        StringBuilder builder = new StringBuilder(host);
        builder.append(endpoint);
        return builder;
    }
    
    public List<HeadCode> fetchHeadCodesFromMDMSForService(RequestInfo requestInfo, String tenantId, String service) {
        List<HeadCode> headCodes = fetchMDMSDataForHeadCode(requestInfo, tenantId);
        return headCodes.stream()
                                                    .filter(e -> service.equalsIgnoreCase(e.getService())).collect(Collectors.toList());
    }
    
    public List<HeadCode> fetchMDMSDataForHeadCode(RequestInfo requestInfo, String tenantId) {
        log.info("Fetch head code list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmoduleWithFilter(requestInfo, tenantId, MDMS_HEAD_CODES);
        List<Object> headCodeListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_HEAD_CODES);
        List<HeadCode> headCodes = new ArrayList<>();
        for(Object obj : headCodeListJson){
            HeadCode headCode = mapper.convertValue(obj, HeadCode.class);
            headCodes.add(headCode);
        }
        log.info("Head codes fetched from MDMS");
        return headCodes;
    }

    public List<ApplicableCharge> fetchApplicableChargesFromMDMSForService(RequestInfo requestInfo, String tenantId, String service) {
        List<ApplicableCharge> applicableCharges = fetchMDMSDataForApplicableCharges(requestInfo, tenantId);
        return applicableCharges.stream()
                                                                            .filter(e -> service.equalsIgnoreCase(e.getService())).collect(Collectors.toList());
    }
    private List<ApplicableCharge> fetchMDMSDataForApplicableCharges(RequestInfo requestInfo, String tenantId) {
        log.info("Fetch head code list from MDMS");
        Object mdmsData = mdmsUtils.getExpenseFromMDMSForSubmoduleWithFilter(requestInfo, tenantId,MDMS_APPLICABLE_CHARGES);
        List<Object> applicableChargesListJson = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_APPLICABLE_CHARGES);
        List<ApplicableCharge> applicableCharges = new ArrayList<>();
        for(Object obj : applicableChargesListJson){
            ApplicableCharge applicableCharge = mapper.convertValue(obj, ApplicableCharge.class);
            applicableCharges.add(applicableCharge);
        }
        log.info("Head codes fetched from MDMS");
        return applicableCharges;
    }



}
