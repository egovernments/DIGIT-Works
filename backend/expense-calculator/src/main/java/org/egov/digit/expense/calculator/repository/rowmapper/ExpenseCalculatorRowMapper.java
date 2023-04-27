package org.egov.digit.expense.calculator.repository.rowmapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseCalculatorRowMapper implements ResultSetExtractor<List<String>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<String> ids = new ArrayList<>();
        while (rs.next()) {
            String id = StringUtils.isNotBlank(rs.getString("musterroll_num")) ? rs.getString("musterroll_num") : rs.getString("bill_id");
            ids.add(id);
        }
        return  ids;
    }


}
