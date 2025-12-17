package org.egov.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.IdGenerationResponse;
import org.apache.poi.ss.usermodel.Row;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.Mdms;
import org.egov.web.models.MdmsCriteriaReqV2;
import org.egov.web.models.MdmsRequest;
import org.egov.web.models.MdmsResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BulkUploadUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Configuration config;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ResourceLoader resourceLoader;


    public Supplier<Stream<Row>> getStreamSupplier(Iterable<Row> rows){

        return () -> getStream(rows);

    }

    public <T> Stream<T> getStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(),false);
    }


    public Supplier<Stream<Integer>> cellIteratorSupplier(int end) {
        return () -> numberStream(end);
    }

    public Stream<Integer> numberStream(int end) {
        return IntStream.range(0, end).boxed();
    }

    public MdmsRequest getMdmsRequest(String tenantId, String location) throws IOException {
        MdmsRequest mdmsRequest;
        Resource classPathResource = resourceLoader.getResource(location);
        mdmsRequest=objectMapper.readValue(classPathResource.getInputStream(),MdmsRequest.class);
        mdmsRequest.getMdms().setTenantId(tenantId);
        return mdmsRequest;
    }

    public MdmsCriteriaReqV2 getMdmsV2Request(String mdmsCriteriaReqV2){

        MdmsCriteriaReqV2 mdmsCriteriaReqV21;
        try {
            mdmsCriteriaReqV21 = objectMapper.readValue(mdmsCriteriaReqV2, MdmsCriteriaReqV2.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return mdmsCriteriaReqV21;
    }

    public MdmsResponseV2 getMdmsV2Response(Object response){

//        Parse data of reponse to MdmsResponseV2
        MdmsResponseV2 responseV2 = objectMapper.convertValue(response, MdmsResponseV2.class);
        return responseV2;
    }


    public Object create(MdmsRequest mdmsRequest, String schemaCode){

        Object response= null;
        StringBuilder uri=new StringBuilder();
        String endpoint= config.getMdmsSorEndPoint().replace("{schemaCode}",schemaCode);
        uri.append(config.getMdmsHostForSor()).append(endpoint);
        response = repository.fetchResult(uri, mdmsRequest);
        return response;

    }

    public MdmsResponseV2 search(MdmsCriteriaReqV2 mdmsCriteriaReqV2){

        Object response = null;
        StringBuilder uri = new StringBuilder();
        String endPoint = config.getMdmsSorSearchEndPoint();
        uri.append(config.getMdmsHostForSor()).append(endPoint);
        response = repository.fetchResult(uri, mdmsCriteriaReqV2);
        return getMdmsV2Response(response);
    }

    public MdmsResponseV2 update(MdmsRequest mdmsRequest){

        Object response= null;
        StringBuilder uri=new StringBuilder();
        String schemaCode = mdmsRequest.getMdms().getSchemaCode();
        String endpoint= config.getMdmsSorUpdateEndPoint().replace("{schemaCode}",schemaCode);
        uri.append(config.getMdmsHostForSor()).append(endpoint);
        response = repository.fetchResult(uri, mdmsRequest);
        return getMdmsV2Response(response);

    }
}
