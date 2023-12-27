package org.digit.exchange.models.fiscal;

import lombok.*;

import java.time.ZonedDateTime;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotNull;

import org.digit.exchange.utils.ZonedDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class AuditDetails {
    @NotNull
    @JsonProperty("created_by")
    @NotNull
    private String createdBy;
    @JsonProperty("created_on")  
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime createdOn;

    public AuditDetails(){}
}
