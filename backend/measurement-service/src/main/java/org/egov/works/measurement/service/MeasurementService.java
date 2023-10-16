package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MeasurementService {

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public MeasurementService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<Measurement> searchMeasurements(MeasurementCriteria searchCriteria) {
        // You can perform any necessary validation of the search criteria here.


        List<Measurement> measurements = serviceRequestRepository.getMeasurements(searchCriteria);
        // Call the repository to get the measurements based on the search criteria.
        return measurements;
    }
}
