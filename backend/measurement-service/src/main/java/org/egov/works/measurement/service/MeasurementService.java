package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.util.IdgenUtil;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.egov.works.measurement.web.models.MeasurementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MeasurementService {
    private final MdmsUtil mdmsUtil;
    private final IdgenUtil idgenUtil;
    @Autowired
    public MeasurementService(MdmsUtil mdmsUtil, IdgenUtil idgenUtil) {
        this.mdmsUtil = mdmsUtil;
        this.idgenUtil = idgenUtil;
    }

    /**
     * create one or more measurements entries
     */
    public void createMeasurement(MeasurementRequest request){
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
        response.getResponseInfo().setApiId(request.getRequestInfo().getApiId());
        response.getResponseInfo().setMsgId(request.getRequestInfo().getMsgId());
//        response.getResponseInfo().setStatus(request.getRequestInfo());
//        response.getResponseInfo().setTs(request.get);
//        response.getResponseInfo().setVer();
        request.getMeasurements().forEach(measurement -> {

            for (Measure measure : measurement.getMeasures()) {

                measure.getDocuments().forEach(document -> {
                    System.out.println(document.getDocumentUid());
                    if(!isValidDocuments(document.getDocumentUid())){
                        throw new Error("No Documents found with the given Ids");
                    }
                    else{
                        // enrich the req
                        // fetch the ids
                        System.out.println(tenantId);
                        List<String> idList =  idgenUtil.getIdList(request.getRequestInfo(),tenantId,idName,idFormat,1);

                        System.out.println(idList);
                        response.getMeasurements();
                    }
                });
            }


        });
        // req & validate documents
        // enrich ( IdGen)
        // enrich UUID, audit details
        // push to kafka
    }

    public boolean isValidDocuments(String documentId){
        return true;
        // return !getDocuments(documentId).isEmpty(); // complete this method
    }
    public List<?> getDocuments(String documentId){
            return new ArrayList<>();
    }
}
