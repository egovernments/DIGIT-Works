package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.MUSTER_ROLL_ID_JSON_PATH;

@Component
@Slf4j
public class MusterRollUtils {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public List<String> fetchMusterRollIdsList(RequestInfo requestInfo, String tenantId, List<String> musterRollId) {
        StringBuilder url = getMusterRollURI(tenantId, musterRollId);
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

    public List<MusterRoll> fetchMusterRollByIds(RequestInfo requestInfo, String tenantId, List<String> musterRollId) {
        StringBuilder url = getApprovedMusterRollURI(tenantId, musterRollId);
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


}