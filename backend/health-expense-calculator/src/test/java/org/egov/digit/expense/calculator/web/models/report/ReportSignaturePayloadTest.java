package org.egov.digit.expense.calculator.web.models.report;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the shape of the payload sent to the pdf service, because the voucher template addresses
 * these fields by path. A rename here silently stops the pdf rendering signatures without any
 * compile error, so the paths the template depends on are asserted rather than assumed.
 *
 * The paths the template uses:
 *   $.bill[0].signatures[*].slot           PREPARED_BY | VERIFIED_BY | APPROVED_BY
 *   $.bill[0].signatures[*].printedName    the signer's typed name
 *   $.bill[0].signatures[*].fileStoreUrl   fetchable image url, for the pdf image element
 *   $.bill[0].signatures[*].fileStoreId    the raw id, used by the excel path
 */
class ReportSignaturePayloadTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("signatures serialise onto the pdf payload at the paths the template expects")
	void exposesSignaturesAtTheExpectedPaths() {

		ReportSignature approver = ReportSignature.builder()
				.slot(ReportSignature.SLOT_APPROVED_BY)
				.printedName("Chidi Approver")
				.fileStoreId("fs-approved")
				.imageDataUri("data:image/png;base64,iVBORw0KGgo=")
				.role("PAYMENT_APPROVER")
				.action("PAYMENT_INITIATION")
				.signedTime(1755432000000L)
				.build();

		BillReportRequest request = BillReportRequest.builder()
				.requestInfo(new RequestInfo())
				.reportBill(Collections.singletonList(ReportBill.builder()
						.tenantId("bednet")
						.campaignName("Test Campaign")
						.totalAmount(new BigDecimal("1530.00"))
						.numberOfIndividuals(3)
						.reportBillDetails(Collections.emptyList())
						.signatures(List.of(approver))
						.build()))
				.build();

		JsonNode payload = objectMapper.valueToTree(request);
		JsonNode signature = payload.path("bill").path(0).path("signatures").path(0);

		assertTrue(payload.path("bill").isArray(), "the pdf payload must expose bill as an array");
		assertEquals("APPROVED_BY", signature.path("slot").asText());
		assertEquals("Chidi Approver", signature.path("printedName").asText());
		assertEquals("fs-approved", signature.path("fileStoreId").asText());
		assertEquals("data:image/png;base64,iVBORw0KGgo=", signature.path("imageDataUri").asText());
		assertEquals("PAYMENT_APPROVER", signature.path("role").asText());
		assertEquals("PAYMENT_INITIATION", signature.path("action").asText());
		assertEquals(1755432000000L, signature.path("signedTime").asLong());
	}

	@Test
	@DisplayName("the three voucher slot names stay stable — the template matches on them")
	void slotNamesAreStable() {
		assertEquals("PREPARED_BY", ReportSignature.SLOT_PREPARED_BY);
		assertEquals("VERIFIED_BY", ReportSignature.SLOT_VERIFIED_BY);
		assertEquals("APPROVED_BY", ReportSignature.SLOT_APPROVED_BY);
	}

	@Test
	@DisplayName("a bill with no sign-off still produces a valid payload")
	void unsignedBillSerialisesCleanly() {
		BillReportRequest request = BillReportRequest.builder()
				.requestInfo(new RequestInfo())
				.reportBill(Collections.singletonList(ReportBill.builder()
						.tenantId("bednet")
						.campaignName("Test Campaign")
						.totalAmount(BigDecimal.ZERO)
						.numberOfIndividuals(0)
						.reportBillDetails(Collections.emptyList())
						.signatures(Collections.emptyList())
						.build()))
				.build();

		JsonNode signatures = objectMapper.valueToTree(request).path("bill").path(0).path("signatures");
		assertTrue(signatures.isArray(), "signatures must remain an array so the template can iterate safely");
		assertEquals(0, signatures.size());
	}
}
