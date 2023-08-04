package org.egov.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class HelperUtil {

    @Autowired
    private ObjectMapper mapper;

    public PGobject getPGObject(Object additionalDetails) {

        String value = null;
        try {
            value = mapper.writeValueAsString(additionalDetails);
        } catch (JsonProcessingException e) {
            throw new CustomException();
        }

        PGobject json = new PGobject();
        json.setType("jsonb");
        try {
            json.setValue(value);
        } catch (SQLException e) {
            throw new CustomException("", "");
        }
        return json;
    }

    public String getFinancialYear(String startDateString) {
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ISO_DATE);
        int startYear = startDate.getYear();
        int endYear = startYear + 1;

        String financialYear = String.format("%d-%02d", startYear, endYear % 100);
        System.out.println("Financial Year: " + financialYear);
        return financialYear;
    }

    public Long getEpochTimeSeconds(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        Long timestamp =  date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        return timestamp;
    }

    public String getFormattedTimeFromTimestamp(Long timestamp, String dateFormat) {
        String formattedDateTime = null;
        try {
            if (timestamp != null && dateFormat != null) {
                // Convert timestamp to LocalDateTime
                LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, java.time.ZoneOffset.UTC);
                // Create a formatter for the desired format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                // Format the LocalDateTime to the desired format
                formattedDateTime = dateTime.format(formatter);
            }
        } catch (Exception e) {
            log.error("Exception occurred in getFormattedTimeFromTimestamp from Helper util: ", e);
        }
        return formattedDateTime;
    }
}
