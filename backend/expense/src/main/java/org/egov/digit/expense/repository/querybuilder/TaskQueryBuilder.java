package org.egov.digit.expense.repository.querybuilder;

import org.egov.digit.expense.util.QueryBuilderUtils;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskDetailsRequest;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.egov.digit.expense.config.Constants.*;

@Component
public class TaskQueryBuilder {
    private final QueryBuilderUtils builderUtils;

    public TaskQueryBuilder(QueryBuilderUtils builderUtils) {
        this.builderUtils = builderUtils;
    }


//    public String getTaskQuery(Task task){
//        if (null != task.getId()) {
//            return TASK_SEARCH_BY_ID_QUERY.replace("{id}", task.getId());
//        }
//        return TASK_SEARCH_QUERY
//            .replace("{billId}", task.getBillId())
//            .replace("{type}", task.getType().toString());
//    }

    public String getTaskQuery(Task task, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(TASK_SEARCH_QUERY);

        if (task.getId() != null) {
            query.append(" WHERE id = ?");
            preparedStmtList.add(task.getId());
        } else {
            query.append(" WHERE bill_id = ? AND type = ? AND status = 'IN_PROGRESS'");
            preparedStmtList.add(task.getBillId());
            preparedStmtList.add(task.getType().toString());

            query.append(TASK_ORDER_BY_QUERY);
        }

        return query.toString();
    }

//    public String getTaskDetailsQuery(TaskDetailsRequest taskDetailsRequest){
//        String query = TASK_DETAILS_SEARCH_QUERY.replace("{taskId}",taskDetailsRequest.getTaskId());
//
//        query = query.replace("{billId}", taskDetailsRequest.getBillId());
//
//        query = query.replace("{billDetailsId}",taskDetailsRequest.getBillDetailsId());
//
//        return query;
//    }

    public String getTaskDetailsQuery(TaskDetailsRequest taskDetailsRequest, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(TASK_DETAILS_SEARCH_QUERY);

        if (taskDetailsRequest.getTaskId() != null) {
            builderUtils.addClauseIfRequired(preparedStmtList, query);
            query.append(" task_id = ? ");
            preparedStmtList.add(taskDetailsRequest.getTaskId());
        }

        if (taskDetailsRequest.getBillId() != null) {
            builderUtils.addClauseIfRequired(preparedStmtList, query);
            query.append(" bill_id = ? ");
            preparedStmtList.add(taskDetailsRequest.getBillId());
        }

        if (taskDetailsRequest.getBillDetailsId() != null) {
            builderUtils.addClauseIfRequired(preparedStmtList, query);
            query.append(" bill_details_id = ? ");
            preparedStmtList.add(taskDetailsRequest.getBillDetailsId());
        }

        return query.toString();
    }
//    public String getTaskDetailsByTaskIdQuery(String taskId){
//        return TASK_DETAILS_BY_TASK_ID_SEARCH_QUERY.replace("{taskId}",taskId);
//    }

    public String getTaskDetailsByTaskIdQuery(String taskId, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(TASK_DETAILS_BY_TASK_ID_SEARCH_QUERY);
        preparedStmtList.add(taskId);
        return query.toString();
    }


//    public String getTasksInProgressQuery(String seconds, String type){
//        return TASK_IN_PROGRESS_SEARCH_QUERY.replace("{seconds}",seconds)
//                .replace("{type}",type);
//    }
    public String getTasksInProgressQuery(String seconds, String type, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(TASK_IN_PROGRESS_SEARCH_QUERY);

        long millis = Long.parseLong(seconds) * 1000;
        preparedStmtList.add(millis);
        preparedStmtList.add(type);

        return query.toString();
    }
}
