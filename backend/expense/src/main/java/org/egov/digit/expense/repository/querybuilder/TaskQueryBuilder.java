package org.egov.digit.expense.repository.querybuilder;

import org.egov.digit.expense.web.models.TaskDetailsRequest;
import org.springframework.stereotype.Component;

import static org.egov.digit.expense.config.Constants.*;

@Component
public class TaskQueryBuilder {

    public String getTaskQuery(String taskId){
        return TASK_SEARCH_QUERY.replace("{id}",taskId);
    }

    public String getTaskDetailsQuery(TaskDetailsRequest taskDetailsRequest){
        String query = TASK_DETAILS_SEARCH_QUERY.replace("{taskId}",taskDetailsRequest.getTaskId());

        query = query.replace("{billId}", taskDetailsRequest.getBillId());

        query = query.replace("{billDetailsId}",taskDetailsRequest.getBillDetailsId());

        return query;
    }
    public String getTaskDetailsByTaskIdQuery(String taskId){
        return TASK_DETAILS_BY_TASK_ID_SEARCH_QUERY.replace("{taskId}",taskId);
    }

}
