package org.digit.exchange.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.digit.exchange.web.models.LanguageValue;

import java.io.IOException;
import java.util.List;

@Converter
public class LanguageValueListConverter implements AttributeConverter<List<LanguageValue>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<LanguageValue> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalStateException("Error converting list of LanguageValue to JSON", e);
        }
    }

    @Override
    public List<LanguageValue> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<LanguageValue>>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Error converting JSON to list of LanguageValue", e);
        }
    }
}
