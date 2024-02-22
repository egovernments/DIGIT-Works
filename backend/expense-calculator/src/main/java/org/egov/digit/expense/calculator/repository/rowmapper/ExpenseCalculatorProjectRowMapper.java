package org.egov.digit.expense.calculator.repository.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;


@Repository
public class ExpenseCalculatorProjectRowMapper implements ResultSetExtractor<List<String>> {

    @Override
    public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<String> projectNumbers = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("project_number");
            projectNumbers.add(id);
        }
        return  projectNumbers;
    }


}
