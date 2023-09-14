package org.egov.works.measurement.validator;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MeasurementServiceValidator {
    public Measurement validateMeasurementExistence(Measurement measurement){
        return measurement;
    }

}
