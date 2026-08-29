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
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.List;

import static org.egov.util.AttendanceServiceConstants.FACE_AUTH_FILESTORE_MODULE;
import static org.egov.util.AttendanceServiceConstants.FILESTORE_UPLOAD_FAILED_FLAG;

@Service
@Slf4j
public class FaceAuthEventService {

    private final FaceAuthEventValidator validator;
    private final FaceAuthEventEnrichment enrichment;
    private final FaceAuthEventRepository repository;
    private final Producer producer;
    private final AttendanceServiceConfiguration config;
    private final ResponseInfoFactory responseInfoFactory;
    private final FileStoreService fileStoreService;

    @Autowired
    public FaceAuthEventService(FaceAuthEventValidator validator, FaceAuthEventEnrichment enrichment,
                                FaceAuthEventRepository repository, Producer producer,
                                AttendanceServiceConfiguration config, ResponseInfoFactory responseInfoFactory,
                                FileStoreService fileStoreService) {
        this.validator = validator;
        this.enrichment = enrichment;
        this.repository = repository;
        this.producer = producer;
        this.config = config;
        this.responseInfoFactory = responseInfoFactory;
        this.fileStoreService = fileStoreService;
    }

    /**
     * API entry point: validate + enrich, then enqueue onto the internal bulk topic. The face image
     * is still inline base64 here; the upload to filestore happens asynchronously in the consumer so
     * the request stays fast for bulk syncs.
     */
    public FaceAuthEventResponse createFaceAuthEvents(FaceAuthEventRequest request) {
        String tenantId = CommonUtils.getTenantId(request.getFaceAuthEvents());
        validator.validateCreateRequest(request);
        enrichment.enrichCreateRequest(request);
        producer.push(tenantId, config.getBulkCreateFaceAuthEventTopic(), request);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        log.info("Face auth events enqueued for processing: {} events", request.getFaceAuthEvents().size());
        return FaceAuthEventResponse.builder()
                .responseInfo(responseInfo)
                .faceAuthEvents(request.getFaceAuthEvents())
                .build();
    }

    /**
     * Consumer entry point: offload each event's base64 face image to filestore, replace it with the
     * returned fileStoreId, then hand off to the persister topic. Upload failure is non-blocking — the
     * verification record is never dropped; it is persisted without an image and flagged.
     */
    public void processFaceAuthEvents(FaceAuthEventRequest request) {
        String tenantId = CommonUtils.getTenantId(request.getFaceAuthEvents());
        // Validate here too: events produced straight to the bulk topic never passed the API stage,
        // and re-validating API-staged events is a harmless no-op. Guards the persister from bad data
        // (missing NOT NULL fields / invalid enums) that would otherwise fail the INSERT.
        validator.validateCreateRequest(request);
        // Fill id/audit only when absent — no-op for API-staged events, safety net for raw bulk events.
        enrichment.enrichMissingIdentifiers(request);
        for (FaceAuthEvent event : request.getFaceAuthEvents()) {
            offloadFaceImageToFileStore(event, tenantId);
        }
        producer.push(tenantId, config.getSaveFaceAuthEventTopic(), request);
        log.info("Face auth events persisted for {} events (tenantId={})", request.getFaceAuthEvents().size(), tenantId);
    }

    private void offloadFaceImageToFileStore(FaceAuthEvent event, String tenantId) {
        // Redelivery-safe: if a previous processing attempt already uploaded this event's image, don't
        // re-upload (which would mint a new fileStoreId and orphan the prior filestore object).
        if (StringUtils.hasText(event.getFaceImageFileStoreId())) {
            event.setFaceImage(null);
            return;
        }
        if (!StringUtils.hasText(event.getFaceImage())) {
            return;
        }
        try {
            // MIME decoder tolerates line-wrapped base64 (e.g. Android Base64.DEFAULT wraps at 76 chars)
            // and ignores non-alphabet whitespace, which the strict RFC4648 decoder rejects outright.
            byte[] imageBytes = Base64.getMimeDecoder().decode(stripDataUrlPrefix(event.getFaceImage()));
            String fileName = "faceauth-" + event.getId() + ".jpg";
            String fileStoreId = fileStoreService.uploadFile(imageBytes, tenantId, fileName, FACE_AUTH_FILESTORE_MODULE);
            event.setFaceImageFileStoreId(fileStoreId);
        } catch (Exception e) {
            // Non-blocking: keep the verification event, drop only the image, and flag it so it is auditable.
            log.error("Face image filestore upload failed for event {} (tenantId={}); persisting without image: {}",
                    event.getId(), tenantId, e.getMessage());
            event.setAnomalyFlags(appendFlag(event.getAnomalyFlags(), FILESTORE_UPLOAD_FAILED_FLAG));
        } finally {
            // Never let base64 reach the DB — the image now lives in filestore (or was intentionally dropped).
            event.setFaceImage(null);
        }
    }

    private String stripDataUrlPrefix(String base64) {
        int comma = base64.indexOf(',');
        return base64.startsWith("data:") && comma > 0 ? base64.substring(comma + 1) : base64;
    }

    private String appendFlag(String existing, String flag) {
        return StringUtils.hasText(existing) ? existing + "," + flag : flag;
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
