package org.digit.exchange.utils;

import java.util.Currency;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CurrencyConverter implements AttributeConverter<Currency, String> {
    @Override
    public String convertToDatabaseColumn(Currency currency) {
        return (currency != null) ? currency.getCurrencyCode() : null;
    }

    @Override
    public Currency convertToEntityAttribute(String currencyCode) {
        return (currencyCode != null) ? Currency.getInstance(currencyCode) : null;
    }
}