package org.egov.digit.expense.repository;

import org.egov.digit.expense.repository.querybuilder.TaskQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.TaskDetailsRowMapper;
import org.egov.digit.expense.repository.rowmapper.TaskRowMapper;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.TaskDetails;
import org.egov.digit.expense.web.models.TaskDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public Task searchTask(Task task){
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTaskQuery(task,preparedStatementValues);
        List<Task> tasks = jdbcTemplate.query(query, preparedStatementValues.toArray(), taskRowMapper);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    public TaskDetails searchTaskDetails(TaskDetailsRequest taskDetailsRequest){
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTaskDetailsQuery(taskDetailsRequest, preparedStatementValues);
        return jdbcTemplate.queryForObject(query, preparedStatementValues.toArray(), taskDetailsRowMapper);
    }

    public List<TaskDetails> searchTaskDetailsByTaskId(String taskId){
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTaskDetailsByTaskIdQuery(taskId, preparedStatementValues);
        return jdbcTemplate.query(query, preparedStatementValues.toArray(), taskDetailsRowMapper);
    }

    public List<Task> getInProgressTasks(String seconds,String type){
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = taskQueryBuilder.getTasksInProgressQuery(seconds, type, preparedStatementValues);
        return jdbcTemplate.query(query, preparedStatementValues.toArray(), taskRowMapper);
    }
}
