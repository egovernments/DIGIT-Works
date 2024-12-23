package org.egov.digit.expense.calculator.repository.rowmapper;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseCalculatorMusterRowMapper implements ResultSetExtractor<List<String>> {



    @Override
    public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<String> musterIds = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("musterroll_number");
            musterIds.add(id);
        }
        return  musterIds;
    }


}
