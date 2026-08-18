package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.calculator.util.FileStoreUtil;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.report.ReportSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the step that reads sign-offs off a bill and places them in the voucher's slots.
 *
 * The additionalDetails payloads below are the real shape the expense service writes — its
 * EnrichmentUtil does {@code details.set("signatures", valueToTree(bill.getSignatures()))}, so the
 * array holds serialised BillSignature objects with printedName, fileStoreId, action, role,
 * signedBy and signedTime. Getting that shape wrong is the most likely way this quietly returns
 * nothing, which is why it is asserted against literal json rather than a hand-built object.
 */
class HealthBillReportGeneratorSignatureTest {

	private static final String TENANT_ID = "bednet";

	/** A genuine 1x1 PNG, so the resulting data uri is a real one. */
	private static final byte[] PNG_BYTES = Base64.getDecoder().decode(
			"iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/58BAwAI/AL+p5qOfwAAAABJRU5ErkJggg==");

	private static final byte[] JPEG_BYTES = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, 0x11, 0x22 };

	private ObjectMapper objectMapper;
	private FileStoreUtil fileStoreUtil;
	private HealthBillReportGenerator generator;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		fileStoreUtil = mock(FileStoreUtil.class);

		// resolveSignatures only collaborates with the object mapper and filestore; the rest of the
		// report pipeline is irrelevant here and is left null deliberately.
		generator = new HealthBillReportGenerator(
				null, null, null, null, null, null, null, null, null,
				objectMapper, null, null, fileStoreUtil);
	}

	@Test
	@DisplayName("each workflow action lands in its own voucher slot")
	void mapsEachActionToItsSlot() {
		when(fileStoreUtil.downloadFile(anyString(), anyString())).thenReturn(PNG_BYTES);

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[
				  {"id":"1","printedName":"Ada Editor","fileStoreId":"fs-1","action":"SEND_FOR_REVIEW",
				   "role":"PAYMENT_EDITOR","signedBy":"uuid-1","signedTime":1755000000000},
				  {"id":"2","printedName":"Bem Reviewer","fileStoreId":"fs-2","action":"SEND_FOR_APPROVAL",
				   "role":"PAYMENT_REVIEWER","signedBy":"uuid-2","signedTime":1755100000000},
				  {"id":"3","printedName":"Chidi Approver","fileStoreId":"fs-3","action":"PAYMENT_INITIATION",
				   "role":"PAYMENT_APPROVER","signedBy":"uuid-3","signedTime":1755200000000}
				]"""));

		Map<String, ReportSignature> bySlot = signatures.stream()
				.collect(Collectors.toMap(ReportSignature::getSlot, s -> s));

		assertEquals(3, signatures.size());
		assertEquals("Ada Editor", bySlot.get(ReportSignature.SLOT_PREPARED_BY).getPrintedName());
		assertEquals("Bem Reviewer", bySlot.get(ReportSignature.SLOT_VERIFIED_BY).getPrintedName());
		assertEquals("Chidi Approver", bySlot.get(ReportSignature.SLOT_APPROVED_BY).getPrintedName());

		ReportSignature approver = bySlot.get(ReportSignature.SLOT_APPROVED_BY);
		assertEquals("fs-3", approver.getFileStoreId());
		assertEquals("PAYMENT_APPROVER", approver.getRole());
		assertEquals("PAYMENT_INITIATION", approver.getAction());
		assertEquals(1755200000000L, approver.getSignedTime());
	}

	@Test
	@DisplayName("VERIFY also signs the prepared-by slot")
	void treatsVerifyAsAPreparerAction() {
		when(fileStoreUtil.downloadFile(anyString(), anyString())).thenReturn(PNG_BYTES);

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[{"printedName":"Ada Editor","fileStoreId":"fs-1","action":"VERIFY","role":"PAYMENT_EDITOR"}]"""));

		assertEquals(1, signatures.size());
		assertEquals(ReportSignature.SLOT_PREPARED_BY, signatures.get(0).getSlot());
	}

	@Test
	@DisplayName("re-signing an action replaces the earlier signature rather than duplicating the slot")
	void latestSignaturePerSlotWins() {
		when(fileStoreUtil.downloadFile(anyString(), anyString())).thenReturn(PNG_BYTES);

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[
				  {"printedName":"First Attempt","fileStoreId":"fs-old","action":"PAYMENT_INITIATION"},
				  {"printedName":"Final Approver","fileStoreId":"fs-new","action":"PAYMENT_INITIATION"}
				]"""));

		assertEquals(1, signatures.size(), "a re-signed action must not produce two entries for one slot");
		assertEquals("Final Approver", signatures.get(0).getPrintedName());
		assertEquals("fs-new", signatures.get(0).getFileStoreId());
	}

	@Test
	@DisplayName("actions that do not sign a voucher slot are ignored")
	void ignoresNonSigningActions() {
		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[{"printedName":"Someone","fileStoreId":"fs-x","action":"CREATE"}]"""));

		assertTrue(signatures.isEmpty());
		verify(fileStoreUtil, never()).downloadFile(anyString(), anyString());
	}

	@Test
	@DisplayName("a bill with no sign-off data yields nothing rather than failing")
	void toleratesBillsWithoutSignatures() {
		assertTrue(generator.resolveSignatures(null).isEmpty(), "null bill");

		assertTrue(generator.resolveSignatures(Bill.builder().id("b1").tenantId(TENANT_ID).build())
				.isEmpty(), "no additionalDetails at all");

		assertTrue(generator.resolveSignatures(billWithAdditionalDetails("{\"someOtherKey\":123}"))
				.isEmpty(), "additionalDetails present but carries no signatures key");

		assertTrue(generator.resolveSignatures(billWithSignatures("[]")).isEmpty(), "empty signatures array");

		verify(fileStoreUtil, never()).downloadFile(anyString(), anyString());
	}

	@Test
	@DisplayName("blank and json-null fields are treated as absent, not as the text \"null\"")
	void treatsBlankFieldsAsAbsent() {
		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[{"printedName":null,"fileStoreId":"","action":"PAYMENT_INITIATION","role":null}]"""));

		assertEquals(1, signatures.size());
		assertNull(signatures.get(0).getPrintedName());
		assertNull(signatures.get(0).getFileStoreId());
		assertNull(signatures.get(0).getRole());
		verify(fileStoreUtil, never()).downloadFile(anyString(), anyString());
	}

	@Test
	@DisplayName("the image becomes a base64 data uri for the pdf template")
	void attachesABase64DataUri() {
		when(fileStoreUtil.downloadFile(TENANT_ID, "fs-3")).thenReturn(PNG_BYTES);

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[{"printedName":"Chidi Approver","fileStoreId":"fs-3","action":"PAYMENT_INITIATION"}]"""));

		assertEquals("data:image/png;base64," + Base64.getEncoder().encodeToString(PNG_BYTES),
				signatures.get(0).getImageDataUri());
	}

	@Test
	@DisplayName("the media type is taken from the bytes, not assumed to be png")
	void detectsJpegFromTheBytes() {
		when(fileStoreUtil.downloadFile(TENANT_ID, "fs-jpg")).thenReturn(JPEG_BYTES);

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[{"printedName":"Chidi Approver","fileStoreId":"fs-jpg","action":"PAYMENT_INITIATION"}]"""));

		assertTrue(signatures.get(0).getImageDataUri().startsWith("data:image/jpeg;base64,"),
				"a jpeg signature must not be labelled as png");
	}

	@Test
	@DisplayName("an image reused across slots is fetched only once")
	void fetchesEachDistinctImageOnce() {
		when(fileStoreUtil.downloadFile(anyString(), eq("fs-shared"))).thenReturn(PNG_BYTES);

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[
				  {"printedName":"Bem Reviewer","fileStoreId":"fs-shared","action":"SEND_FOR_APPROVAL"},
				  {"printedName":"Bem Reviewer","fileStoreId":"fs-shared","action":"PAYMENT_INITIATION"}
				]"""));

		assertEquals(2, signatures.size());
		signatures.forEach(s -> assertTrue(s.getImageDataUri().startsWith("data:image/png;base64,")));
		verify(fileStoreUtil, times(1)).downloadFile(TENANT_ID, "fs-shared");
	}

	@Test
	@DisplayName("a filestore outage still yields the signatures, just without images")
	void survivesFilestoreFailure() {
		when(fileStoreUtil.downloadFile(anyString(), anyString()))
				.thenThrow(new RuntimeException("filestore unavailable"));

		List<ReportSignature> signatures = generator.resolveSignatures(billWithSignatures("""
				[{"printedName":"Chidi Approver","fileStoreId":"fs-3","action":"PAYMENT_INITIATION"}]"""));

		assertEquals(1, signatures.size(), "an image failure must not lose the sign-off");
		assertEquals("Chidi Approver", signatures.get(0).getPrintedName());
		assertNull(signatures.get(0).getImageDataUri());
	}

	// ---------- helpers ----------

	private Bill billWithSignatures(String signaturesJson) {
		return billWithAdditionalDetails("{\"signatures\":" + signaturesJson + "}");
	}

	private Bill billWithAdditionalDetails(String additionalDetailsJson) {
		try {
			return Bill.builder()
					.id("bill-1")
					.tenantId(TENANT_ID)
					.additionalDetails(objectMapper.readTree(additionalDetailsJson))
					.build();
		} catch (Exception e) {
			throw new IllegalStateException("bad test fixture", e);
		}
	}
}
