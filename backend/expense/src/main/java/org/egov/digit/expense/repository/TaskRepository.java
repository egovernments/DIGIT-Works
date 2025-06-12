package org.egov.digit.expense.repository;

import org.egov.digit.expense.repository.querybuilder.TaskQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.TaskDetailsRowMapper;
import org.egov.digit.expense.repository.rowmapper.TaskRowMapper;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskDetails;
import org.egov.digit.expense.web.models.TaskDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final TaskQueryBuilder taskQueryBuilder;

    private final TaskRowMapper taskRowMapper;

    private final TaskDetailsRowMapper taskDetailsRowMapper;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate, TaskQueryBuilder queryBuilder, TaskRowMapper taskRowMapper, TaskDetailsRowMapper taskDetailsRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskQueryBuilder = queryBuilder;
        this.taskRowMapper = taskRowMapper;
        this.taskDetailsRowMapper = taskDetailsRowMapper;
    }

    public Task searchTask(String taskId){
        String query = taskQueryBuilder.getTaskQuery(taskId);
        return jdbcTemplate.queryForObject(query, taskRowMapper);
    }

    public TaskDetails searchTaskDetails(TaskDetailsRequest taskDetailsRequest){
        String query = taskQueryBuilder.getTaskDetailsQuery(taskDetailsRequest);
        return jdbcTemplate.queryForObject(query, taskDetailsRowMapper);
    }

    public List<TaskDetails> searchTaskDetailsByTaskId(String taskId){
        String query = taskQueryBuilder.getTaskDetailsByTaskIdQuery(taskId);
        return jdbcTemplate.query(query, taskDetailsRowMapper);
    }
}
