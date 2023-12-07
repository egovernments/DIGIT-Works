package org.egov.util;

import digit.models.coremodels.IdGenerationResponse;
import org.apache.poi.ss.usermodel.Row;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.MdmsRequest;
import org.egov.web.models.MdmsResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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



    public Object create(MdmsRequest mdmsRequest, String schemaCode){
        Object response= null;
        StringBuilder uri=new StringBuilder();
        String endpoint= config.getMdmsSorEndPoint().replace("{schemaCode}",schemaCode);

        uri.append(config.getMdmsHostForSor()).append(endpoint);


        response = repository.fetchResult(uri, mdmsRequest);

        return response;

    }
}
