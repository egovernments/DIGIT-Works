package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers the flat sign-off fields the pdf template reads, and specifically the stand-ins used when
 * a slot was never signed.
 *
 * This matters more than it looks. pdf-service resolves every template variable through
 * getValue(jp.query(...), "NA", path) and returns the literal string "NA" when a path matches
 * nothing. "NA" reaching an image element makes PDFMake throw, so a bill with no sign-off would
 * break pdf generation entirely — and that describes every bill approved before sign-off capture
 * existed. Every field must therefore always hold a value the template can render.
 */
class PdfSignatureFlatteningTest {

	private HealthBillReportGenerator generator;

	@BeforeEach
	void setUp() {
		generator = new HealthBillReportGenerator(
				null, null, null, null, null, null, null, null, null,
				new ObjectMapper(), null, null, null);
	}

	@Test
	@DisplayName("signed slots are copied into the flat fields the template reads")
	void copiesSignedSlots() {
		ReportBill bill = ReportBill.builder()
				.signatures(List.of(
						signature(ReportSignature.SLOT_PREPARED_BY, "Ada Editor", "data:image/png;base64,AAA"),
						signature(ReportSignature.SLOT_VERIFIED_BY, "Bem Reviewer", "data:image/png;base64,BBB"),
						signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", "data:image/png;base64,CCC")))
				.build();

		generator.flattenSignaturesForPdf(bill);

		assertEquals("Ada Editor", bill.getPreparedBySignatureName());
		assertEquals("data:image/png;base64,AAA", bill.getPreparedBySignatureImage());
		assertEquals("Bem Reviewer", bill.getVerifiedBySignatureName());
		assertEquals("data:image/png;base64,BBB", bill.getVerifiedBySignatureImage());
		assertEquals("Chidi Approver", bill.getApprovedBySignatureName());
		assertEquals("data:image/png;base64,CCC", bill.getApprovedBySignatureImage());
	}

	@Test
	@DisplayName("an unsigned bill still leaves every field renderable, so the pdf cannot break")
	void neverLeavesAFieldUnresolvable() {
		ReportBill unsigned = ReportBill.builder().signatures(Collections.emptyList()).build();
		ReportBill nullSignatures = ReportBill.builder().build();

		for (ReportBill bill : List.of(unsigned, nullSignatures)) {
			generator.flattenSignaturesForPdf(bill);

			for (String image : List.of(bill.getPreparedBySignatureImage(),
					bill.getVerifiedBySignatureImage(), bill.getApprovedBySignatureImage())) {
				assertNotNull(image, "an image field must never be null — pdf-service would send \"NA\"");
				assertTrue(image.startsWith("data:image/png;base64,"),
						"an unsigned slot must still hold a valid image");
			}

			for (String name : List.of(bill.getPreparedBySignatureName(),
					bill.getVerifiedBySignatureName(), bill.getApprovedBySignatureName())) {
				assertNotNull(name, "a name field must never be null");
				assertFalse(name.isEmpty(), "an empty string makes pdf-service print the text \"NA\"");
				assertTrue(name.isBlank(), "an unsigned slot should render as blank space, not visible text");
			}
		}
	}

	@Test
	@DisplayName("a partly signed bill fills only what was signed and blanks the rest")
	void handlesAPartlySignedBill() {
		ReportBill bill = ReportBill.builder()
				.signatures(List.of(
						signature(ReportSignature.SLOT_PREPARED_BY, "Ada Editor", "data:image/png;base64,AAA")))
				.build();

		generator.flattenSignaturesForPdf(bill);

		assertEquals("Ada Editor", bill.getPreparedBySignatureName());
		assertEquals("data:image/png;base64,AAA", bill.getPreparedBySignatureImage());

		assertTrue(bill.getApprovedBySignatureName().isBlank());
		assertTrue(bill.getApprovedBySignatureImage().startsWith("data:image/png;base64,"));
		assertFalse(bill.getApprovedBySignatureImage().endsWith("AAA"),
				"an unsigned slot must not borrow another slot's image");
	}

	@Test
	@DisplayName("a signature whose image could not be fetched still yields a renderable slot")
	void blanksTheImageWhenTheFetchFailed() {
		// resolveSignatures leaves imageDataUri null when filestore could not serve the image.
		ReportBill bill = ReportBill.builder()
				.signatures(List.of(signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", null)))
				.build();

		generator.flattenSignaturesForPdf(bill);

		assertEquals("Chidi Approver", bill.getApprovedBySignatureName(),
				"the printed name still records the sign-off");
		assertTrue(bill.getApprovedBySignatureImage().startsWith("data:image/png;base64,"));
	}

	private ReportSignature signature(String slot, String printedName, String imageDataUri) {
		return ReportSignature.builder()
				.slot(slot)
				.printedName(printedName)
				.imageDataUri(imageDataUri)
				.fileStoreId("fs-" + slot)
				.build();
	}
}
