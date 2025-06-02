package org.egov.digit.expense.repository.querybuilder;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants.*;
import org.springframework.stereotype.Component;

import static org.egov.digit.expense.config.Constants.BILL_VERIFICATION_TASK_SEARCH_QUERY;

@Component
public class BillVerificationTaskQueryBuilder {

    public String getBillVerifcationTaskQuery(String taskId){
        return BILL_VERIFICATION_TASK_SEARCH_QUERY.replace("{id}",taskId);
    }
}
