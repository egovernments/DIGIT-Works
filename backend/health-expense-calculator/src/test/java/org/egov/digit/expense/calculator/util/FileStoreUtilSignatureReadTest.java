package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Covers the signature read path on filestore, and the isolation it depends on.
 *
 * Signature images are fetched inline while a report is generated, and the shared RestTemplate has
 * no timeout, so these reads use a separate timeout-bound client. The upload path must keep using
 * the shared one — a generated voucher is a much larger request whose behaviour must not change.
 * That split is asserted here, because getting it wrong silently re-times-out the upload.
 */
class FileStoreUtilSignatureReadTest {

	private static final String TENANT_ID = "bednet";

	private RestTemplate sharedRestTemplate;
	private RestTemplate signatureRestTemplate;
	private ExpenseCalculatorConfiguration config;
	private FileStoreUtil fileStoreUtil;

	@BeforeEach
	void setUp() {
		sharedRestTemplate = mock(RestTemplate.class);
		signatureRestTemplate = mock(RestTemplate.class);
		config = mock(ExpenseCalculatorConfiguration.class);

		when(config.getFileStoreHost()).thenReturn("http://filestore");
		when(config.getFileStoreEndpoint()).thenReturn("/filestore/v1/files");

		fileStoreUtil = new FileStoreUtil(
				sharedRestTemplate, new ObjectMapper(), config, signatureRestTemplate);
	}

	@Test
	@DisplayName("downloading a signature uses the timeout-bound client, never the shared one")
	void signatureDownloadIsIsolatedFromTheSharedClient() {
		byte[] image = { 1, 2, 3 };
		when(signatureRestTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class)))
				.thenReturn(ResponseEntity.ok(image));

		assertEquals(image, fileStoreUtil.downloadFile(TENANT_ID, "fs-1"));
		verifyNoInteractions(sharedRestTemplate);
	}

	@Test
	@DisplayName("uploading keeps the shared client, so its behaviour is unchanged")
	void uploadStillUsesTheSharedClient() {
		when(sharedRestTemplate.postForObject(anyString(), any(), eq(Object.class)))
				.thenReturn(Map.of("files", List.of(Map.of("fileStoreId", "new-id"))));

		assertEquals("new-id", fileStoreUtil.uploadFileAndGetFileStoreId(TENANT_ID,
				new ByteArrayResource("voucher".getBytes())));
		verify(signatureRestTemplate, never()).postForObject(anyString(), any(), eq(Object.class));
	}

	@Test
	@DisplayName("an empty body is rejected rather than passed on as a broken image")
	void rejectsAnEmptyDownload() {
		when(signatureRestTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class)))
				.thenReturn(ResponseEntity.ok(new byte[0]));

		assertThrows(RuntimeException.class, () -> fileStoreUtil.downloadFile(TENANT_ID, "fs-empty"));
	}

	@Test
	@DisplayName("the download url carries tenant and file store id")
	void buildsTheDownloadUrl() {
		when(signatureRestTemplate.exchange(contains("/filestore/v1/files/id"), eq(HttpMethod.GET),
				any(HttpEntity.class), eq(byte[].class)))
				.thenReturn(ResponseEntity.ok(new byte[] { 9 }));

		fileStoreUtil.downloadFile(TENANT_ID, "fs-1");

		verify(signatureRestTemplate).exchange(
				eq("http://filestore/filestore/v1/files/id?tenantId=bednet&fileStoreId=fs-1"),
				eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class));
	}

	@Test
	@DisplayName("a filestore failure surfaces as an exception for the caller to absorb")
	void propagatesFailureSoCallersCanSkipTheImage() {
		when(signatureRestTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(byte[].class)))
				.thenThrow(new RuntimeException("read timed out"));

		assertThrows(RuntimeException.class, () -> fileStoreUtil.downloadFile(TENANT_ID, "fs-slow"));
	}
}
