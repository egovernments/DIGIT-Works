package org.egov.service;

import org.egov.common.producer.Producer;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.FaceAuthEventEnrichment;
import org.egov.repository.FaceAuthEventRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.FaceAuthEventValidator;
import org.egov.web.models.FaceAuthEvent;
import org.egov.web.models.FaceAuthEventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.Collections;

import static org.egov.util.AttendanceServiceConstants.FILESTORE_UPLOAD_FAILED_FLAG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FaceAuthEventServiceTest {

    @InjectMocks
    private FaceAuthEventService service;

    @Mock private FaceAuthEventValidator validator;
    @Mock private FaceAuthEventEnrichment enrichment;
    @Mock private FaceAuthEventRepository repository;
    @Mock private Producer producer;
    @Mock private AttendanceServiceConfiguration config;
    @Mock private ResponseInfoFactory responseInfoFactory;
    @Mock private FileStoreService fileStoreService;

    private static final String SAVE_TOPIC = "save-face-auth-event";
    private static final String TENANT = "bednet";
    private final String base64Jpeg = Base64.getEncoder().encodeToString(new byte[]{1, 2, 3, 4});

    @BeforeEach
    void stubTopic() {
        when(config.getSaveFaceAuthEventTopic()).thenReturn(SAVE_TOPIC);
    }

    private FaceAuthEventRequest requestWith(FaceAuthEvent event) {
        event.setTenantId(TENANT);
        return FaceAuthEventRequest.builder()
                .faceAuthEvents(Collections.singletonList(event))
                .build();
    }

    @Test
    @DisplayName("uploads base64 image to filestore, sets fileStoreId, and strips base64 before persisting")
    void offloadsImageOnHappyPath() throws Exception {
        when(fileStoreService.uploadFile(any(byte[].class), eq(TENANT), anyString(), anyString())).thenReturn("fs-123");
        FaceAuthEvent event = FaceAuthEvent.builder().id("e1").faceImage(base64Jpeg).build();
        FaceAuthEventRequest request = requestWith(event);

        service.processFaceAuthEvents(request);

        assertEquals("fs-123", event.getFaceImageFileStoreId());
        assertNull(event.getFaceImage(), "base64 must never reach the DB");
        verify(fileStoreService).uploadFile(any(byte[].class), eq(TENANT), eq("faceauth-e1.jpg"), eq("attendance-face-auth"));
        verify(producer).push(eq(TENANT), eq(SAVE_TOPIC), eq(request));
    }

    @Test
    @DisplayName("upload failure is non-blocking: event kept, flagged, still persisted without image")
    void uploadFailureIsNonBlocking() throws Exception {
        when(fileStoreService.uploadFile(any(byte[].class), eq(TENANT), anyString(), anyString()))
                .thenThrow(new CustomException("FILE_STORE_SERVICE_ERROR", "down"));
        FaceAuthEvent event = FaceAuthEvent.builder().id("e2").faceImage(base64Jpeg).build();
        FaceAuthEventRequest request = requestWith(event);

        service.processFaceAuthEvents(request);

        assertNull(event.getFaceImageFileStoreId());
        assertNull(event.getFaceImage());
        assertTrue(event.getAnomalyFlags().contains(FILESTORE_UPLOAD_FAILED_FLAG));
        verify(producer).push(eq(TENANT), eq(SAVE_TOPIC), eq(request));
    }

    @Test
    @DisplayName("redelivery-safe: skips re-upload when fileStoreId is already set")
    void skipsReuploadWhenAlreadyUploaded() throws Exception {
        FaceAuthEvent event = FaceAuthEvent.builder().id("e4").faceImage(base64Jpeg)
                .faceImageFileStoreId("fs-existing").build();
        FaceAuthEventRequest request = requestWith(event);

        service.processFaceAuthEvents(request);

        verify(fileStoreService, never()).uploadFile(any(), anyString(), anyString(), anyString());
        assertEquals("fs-existing", event.getFaceImageFileStoreId());
        assertNull(event.getFaceImage());
        verify(producer).push(eq(TENANT), eq(SAVE_TOPIC), eq(request));
    }

    @Test
    @DisplayName("event without a face image skips filestore and is persisted as-is")
    void skipsUploadWhenNoImage() throws Exception {
        FaceAuthEvent event = FaceAuthEvent.builder().id("e3").faceImage(null).build();
        FaceAuthEventRequest request = requestWith(event);

        service.processFaceAuthEvents(request);

        verify(fileStoreService, never()).uploadFile(any(), anyString(), anyString(), anyString());
        assertNull(event.getFaceImageFileStoreId());
        verify(producer).push(eq(TENANT), eq(SAVE_TOPIC), eq(request));
    }
}
