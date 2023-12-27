package org.digit.exchange.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String> {
    @Override
    public String convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        if(zonedDateTime != null){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            return zonedDateTime.format(formatter);
        }
        return null;
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dbData) {
        if(dbData != null && !dbData.isBlank()){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            return ZonedDateTime.parse(dbData, formatter);
        }
        return null;          
    }
}