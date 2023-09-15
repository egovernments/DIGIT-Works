package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.ContractUtil;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MeasurementService {
    private final MdmsUtil mdmsUtil;
    private final IdgenUtil idgenUtil;
    private final Producer producer;
    private final ContractService contractService;
    private final ContractUtil contractUtil;
    private final Configuration configuration;

    @Autowired
    public MeasurementService(MdmsUtil mdmsUtil, IdgenUtil idgenUtil, Producer producer, ContractService contractService, ContractUtil contractUtil, Configuration configuration) {
        this.mdmsUtil = mdmsUtil;
        this.idgenUtil = idgenUtil;
        this.producer = producer;
        this.contractService = contractService;
        this.contractUtil = contractUtil;
        this.configuration = configuration;
    }

    /**
     * create one or more measurements entries
     */
    public ResponseEntity<MeasurementResponse> createMeasurement(MeasurementRequest request){
        System.out.println("Create Measurement service is called");
        String tenantId = request.getMeasurements().get(0).getTenantId(); // each measurement should have same tenantId otherwise this will fail
        String idName = "mb.reference.number";
        String idFormat = "MB/[fy:yyyy-yy]/[SEQ_MEASUREMENT_NUM]";

        MeasurementResponse response = new MeasurementResponse();
        List<Measurement> measurementList = new ArrayList<>();

        // TODO: Shift to Utils
        request.getMeasurements().forEach(measurement -> {

            // enrich UUID
            measurement.setId(UUID.randomUUID());

            // validate contracts
            Boolean isValidContract = contractUtil.validContract(measurement,request.getRequestInfo());

            if (!isValidContract) {
                // FIXME: handle the Error here ??
                throw new CustomException();
            }

            // TODO: check the docs for each measure in measurement
            for (Measure measure : measurement.getMeasures()) {

                // check all the docs;
                int isValidDocs = 1;
                for (Document document : measure.getDocuments()) {
                    if (!isValidDocuments(document.getFileStore())) {
                        isValidDocs = 0;
                        throw new Error("No Documents found with the given Ids");
                    } else {
                    }
                }
                if(isValidDocs == 1){
                    measure.setId(UUID.randomUUID());
                    measure.setReferenceId(measurement.getId().toString());
                }
            }

            // fetch ids from IdGen
            List<String> idList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, idName, idFormat, 1);

            // enrich IdGen
            measurement.setMeasurementNumber(idList.get(0));

            // set audit details
            AuditDetails auditDetails = AuditDetails.builder().createdBy(request.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedTime(System.currentTimeMillis()).build();
            measurement.setAuditDetails(auditDetails);
            // add the measurement to measurementList
            measurementList.add(measurement);
        });
        response.setMeasurements(measurementList);

        // push to kafka topic
        producer.push(configuration.getCreateMeasurementTopic(),response);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public boolean isValidDocuments(String documentId){
        return true;
        // return !getDocuments(documentId).isEmpty(); // complete this method
    }
    public List<?> getDocuments(String documentId){
            return new ArrayList<>();
    }
}
