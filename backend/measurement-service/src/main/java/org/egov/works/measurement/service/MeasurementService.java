package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.kafka.Producer;
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
    @Autowired
    public MeasurementService(MdmsUtil mdmsUtil, IdgenUtil idgenUtil, Producer producer) {
        this.mdmsUtil = mdmsUtil;
        this.idgenUtil = idgenUtil;
        this.producer = producer;
    }

    /**
     * create one or more measurements entries
     */
    public ResponseEntity<MeasurementResponse> createMeasurement(MeasurementRequest request){
        System.out.println("Create Measurement service is called");

        String tenantId = request.getMeasurements().get(0).getTenantId(); // each measurement should have same tenantId otherwise this will fail
        String idName = "mb.reference.number";
        String idFormat = "MB/[fy:yyyy-yy]/[SEQ_MEASUREMENT_NUM]";
//        String moduleName = "common-masters"; // shift this to config
//        List<String> masterNameList = new ArrayList<>();
//        masterNameList.add("uom");
//        Map<String, Map<String, JSONArray>> mdmsData =  mdmsUtil.fetchMdmsData(request.getRequestInfo(),tenantId,moduleName,masterNameList);
//        JSONArray uomList = mdmsData.get("common-masters").get("uom");
//        for (int i = 0; i < uomList.size(); i++) {
//            Object item = uomList.get(i);
//            System.out.println(item);
//        }

        // validate req params
        // System.out.println(uomList);
        // req & validate for mdms data
        //---------------------------------
        MeasurementResponse response = new MeasurementResponse();
        List<Measurement> measurementList = new ArrayList<>();

        request.getMeasurements().forEach(measurement -> {
            Measurement measurement1 = new Measurement();
            for (Measure measure : measurement.getMeasures()) {

                // check all the docs;
                int isValidDocs = 1;
                for (Document document : measure.getDocuments()) {
                    System.out.println(document.getDocumentUid());
                    if (!isValidDocuments(document.getDocumentUid())) {
                        isValidDocs = 0;
                        throw new Error("No Documents found with the given Ids");
                    } else {
                        // enrich the req
                        // fetch the ids
//                        System.out.println(tenantId);
//                        List<String> idList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, idName, idFormat, 1);
//                        System.out.println(idList);
//                        measure.setReferenceId(idList.get(0));
                    }
                }
                if(isValidDocs == 1){
                    measure.setId(UUID.randomUUID());
                }
            }
            measurement1.setMeasures(measurement.getMeasures());
            List<String> idList = idgenUtil.getIdList(request.getRequestInfo(), tenantId, idName, idFormat, 1);
            System.out.println(idList.get(0));
            measurement1.setMeasurementNumber(idList.get(0)); // enrich IdGen
            measurement1.setId(UUID.randomUUID());            // enrich UUID
            measurementList.add(measurement1);
        });
        response.setMeasurements(measurementList);
        // FIXME: add audit details
        producer.push("save-measurement-details",response);  // FIXME: shift this to constants
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
