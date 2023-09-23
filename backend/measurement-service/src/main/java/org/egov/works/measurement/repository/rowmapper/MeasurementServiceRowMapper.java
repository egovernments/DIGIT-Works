package org.egov.works.measurement.repository.rowmapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.web.models.AuditDetails;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class MeasurementServiceRowMapper implements ResultSetExtractor<ArrayList<MeasurementService>> {


    @Override
    public ArrayList<MeasurementService> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, MeasurementService> measurementMap = new LinkedHashMap<>();

        return new ArrayList<>(measurementMap.values());
    }
}
