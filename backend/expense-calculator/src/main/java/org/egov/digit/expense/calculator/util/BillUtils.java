package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillCalculatorRequestInfoWrapper;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.MusterRollResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.MUSTER_ROLL_ID_JSON_PATH;

@Component
@Slf4j
public class BillUtils {

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    @Autowired
    private ObjectMapper mapper;

    public List<Bill> postBills(RequestInfo requestInfo, List<Bill> bills) {
        StringBuilder url = getBillCreateURI();
        BillCalculatorRequestInfoWrapper requestInfoWrapper = BillCalculatorRequestInfoWrapper.builder()
                                                                .requestInfo(requestInfo)
                                                                .bills(bills)
                                                                .build();

        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        BillResponse billResponse = mapper.convertValue(responseObj, BillResponse.class);
        return billResponse.getBill();
    }

    private StringBuilder getBillCreateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillCreateEndPoint());
        return builder;
    }
}
