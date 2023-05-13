package org.egov.digit.expense.calculator.repository.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ExpenseCalculatorProjectRowMapper implements ResultSetExtractor<List<String>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<String> project_numbers = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("project_number");
            project_numbers.add(id);
        }
        return  project_numbers;
    }


}
