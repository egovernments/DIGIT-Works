package org.egov.works.measurement.web.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class ServiceIds {
    private List<String> ids;
    private List<String> measurementNumbers;
}

