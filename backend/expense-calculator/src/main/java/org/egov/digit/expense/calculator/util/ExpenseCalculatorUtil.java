package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillCriteria;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.BillSearchRequest;
import org.egov.digit.expense.calculator.web.models.Contract;
import org.egov.digit.expense.calculator.web.models.ContractCriteria;
import org.egov.digit.expense.calculator.web.models.ContractResponse;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollResponse;
import org.egov.digit.expense.calculator.web.models.Order;
import org.egov.digit.expense.calculator.web.models.Pagination;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
        Pagination pagination = Pagination.builder().limit(100d).build();
        ContractCriteria searchCriteria = ContractCriteria.builder().requestInfo(requestInfo).tenantId(tenantId)
                .contractNumber(contractId).pagination(pagination).build();
        Object responseObj = restRepo.fetchResult(url, searchCriteria);
        ContractResponse response = mapper.convertValue(responseObj, ContractResponse.class);
        return response != null ? response.getContracts() : null;
    }

    public List<Bill> fetchBills(RequestInfo requestInfo, String tenantId, String contractId) {
        StringBuilder url = searchURI(configs.getBillHost(), configs.getExpenseBillSearchEndPoint());
        Pagination pagination = Pagination.builder().limit(100d).build();
        pagination.setOrder(Order.ASC);
        BillCriteria billCriteria = BillCriteria.builder().tenantId(tenantId)
                .referenceIds(new HashSet<>(Arrays.asList(contractId))).build();
        BillSearchRequest billSearchRequest = BillSearchRequest.builder().requestInfo(requestInfo)
                .billCriteria(billCriteria).tenantId(tenantId).pagination(pagination).build();
        Object responseObj = restRepo.fetchResult(url, billSearchRequest);
        BillResponse response = mapper.convertValue(responseObj, BillResponse.class);
        return response != null ? response.getBills() : null;

    }

    private StringBuilder searchURI(String host, String endpoint) {
        StringBuilder builder = new StringBuilder(host);
        builder.append(endpoint);
        return builder;
    }



}