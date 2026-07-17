package org.egov.digit.expense.repository;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.TaskQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.TaskDetailsRowMapper;
import org.egov.digit.expense.repository.rowmapper.TaskRowMapper;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskDetails;
import org.egov.digit.expense.web.models.TaskDetailsRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final TaskQueryBuilder taskQueryBuilder;

    private final TaskRowMapper taskRowMapper;

    private final TaskDetailsRowMapper taskDetailsRowMapper;

    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate, TaskQueryBuilder queryBuilder, TaskRowMapper taskRowMapper,
                          TaskDetailsRowMapper taskDetailsRowMapper, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskQueryBuilder = queryBuilder;
        this.taskRowMapper = taskRowMapper;
        this.taskDetailsRowMapper = taskDetailsRowMapper;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public Task searchTask(Task task) {
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTaskQuery(task, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, task.getTenantId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        List<Task> tasks = jdbcTemplate.query(query, preparedStatementValues.toArray(), taskRowMapper);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    public TaskDetails searchTaskDetails(TaskDetailsRequest taskDetailsRequest) {
        String tenantId = taskDetailsRequest.getRequestInfo().getUserInfo().getTenantId();
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTaskDetailsQuery(taskDetailsRequest, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, tenantId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        return jdbcTemplate.queryForObject(query, preparedStatementValues.toArray(), taskDetailsRowMapper);
    }

    public List<TaskDetails> searchTaskDetailsByTaskId(String taskId, String tenantId) {
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTaskDetailsByTaskIdQuery(taskId, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, tenantId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        return jdbcTemplate.query(query, preparedStatementValues.toArray(), taskDetailsRowMapper);
    }

}
