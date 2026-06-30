package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.utils.CommonUtils;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.FaceAuthEventEnrichment;
import org.egov.common.producer.Producer;
import org.egov.repository.FaceAuthEventRepository;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.FaceAuthEventValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FaceAuthEventService {

    private final FaceAuthEventValidator validator;
    private final FaceAuthEventEnrichment enrichment;
    private final FaceAuthEventRepository repository;
    private final Producer producer;
    private final AttendanceServiceConfiguration config;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public FaceAuthEventService(FaceAuthEventValidator validator, FaceAuthEventEnrichment enrichment,
                                FaceAuthEventRepository repository, Producer producer,
                                AttendanceServiceConfiguration config, ResponseInfoFactory responseInfoFactory) {
        this.validator = validator;
        this.enrichment = enrichment;
        this.repository = repository;
        this.producer = producer;
        this.config = config;
        this.responseInfoFactory = responseInfoFactory;
    }

    public FaceAuthEventResponse createFaceAuthEvents(FaceAuthEventRequest request) {
        String tenantId = CommonUtils.getTenantId(request.getFaceAuthEvents());
        validator.validateCreateRequest(request);
        enrichment.enrichCreateRequest(request);
        producer.push(tenantId, config.getSaveFaceAuthEventTopic(), request);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        log.info("Face auth events created successfully for {} events", request.getFaceAuthEvents().size());
        return FaceAuthEventResponse.builder()
                .responseInfo(responseInfo)
                .faceAuthEvents(request.getFaceAuthEvents())
                .build();
    }

    public FaceAuthEventResponse searchFaceAuthEvents(RequestInfoWrapper requestInfoWrapper, FaceAuthEventSearchCriteria searchCriteria) {
        validator.validateSearchRequest(requestInfoWrapper, searchCriteria);
        enrichment.enrichSearchRequest(searchCriteria);
        List<FaceAuthEvent> events = repository.getFaceAuthEvents(searchCriteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        log.info("Found {} face auth events", events.size());
        return FaceAuthEventResponse.builder()
                .responseInfo(responseInfo)
                .faceAuthEvents(events)
                .build();
    }
}
