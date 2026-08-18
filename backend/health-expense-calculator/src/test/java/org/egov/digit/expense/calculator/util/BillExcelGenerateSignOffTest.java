package org.egov.digit.expense.calculator.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the payment voucher's sign-off area: that a captured signature reaches the generated
 * workbook as a real embedded image alongside the signer's printed name, that images are fetched
 * economically, and that neither a missing nor an unreachable signature breaks voucher generation.
 *
 * Filestore is mocked throughout, so these run with no database, kafka or filestore available.
 */
class BillExcelGenerateSignOffTest {

	private static final String TENANT_ID = "bednet";
	private static final String LOCALE = "en_IN";
	private static final String LOCALIZATION_MODULE = "rainmaker-common";

	/** A genuine 1x1 PNG — POI stores the bytes as given, so the header is what matters. */
	private static final byte[] PNG_BYTES = Base64.getDecoder().decode(
			"iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/58BAwAI/AL+p5qOfwAAAABJRU5ErkJggg==");

	private LocalizationUtil localizationUtil;
	private ExpenseCalculatorConfiguration config;
	private FileStoreUtil fileStoreUtil;
	private BillExcelGenerate generator;
	private byte[] lastWorkbookBytes;

	@BeforeEach
	void setUp() {
		localizationUtil = mock(LocalizationUtil.class);
		config = mock(ExpenseCalculatorConfiguration.class);
		fileStoreUtil = mock(FileStoreUtil.class);
		generator = new BillExcelGenerate(localizationUtil, config, fileStoreUtil);

		Map<String, Map<String, String>> messages = new HashMap<>();
		messages.put(LOCALE + "|" + TENANT_ID, new HashMap<>());

		when(config.getStateLevelTenantId()).thenReturn(TENANT_ID);
		when(config.getReportLocalizationModuleName()).thenReturn(LOCALIZATION_MODULE);
		when(config.getReportDateTimeFormat()).thenReturn("dd/MM/yyyy HH:mm:ss");
		when(config.getReportDateTimeZone()).thenReturn("Asia/Kolkata");
		when(localizationUtil.getLocalCode(any())).thenReturn(LOCALE);
		when(localizationUtil.getLocalisedMessages(any(), anyString(), anyString(), anyString()))
				.thenReturn(messages);
		when(fileStoreUtil.uploadFileAndGetFileStoreId(anyString(), any(Resource.class)))
				.thenReturn("generated-excel-id");
	}

	@Test
	@DisplayName("every signed slot contributes an embedded image to the workbook")
	void embedsAnImagePerSignedSlot() throws Exception {
		when(fileStoreUtil.downloadFile(eq(TENANT_ID), anyString())).thenReturn(PNG_BYTES);

		ReportBill bill = baseBill().signatures(List.of(
				signature(ReportSignature.SLOT_PREPARED_BY, "Ada Editor", "fs-prepared"),
				signature(ReportSignature.SLOT_VERIFIED_BY, "Bem Reviewer", "fs-verified"),
				signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", "fs-approved"))).build();

		try (XSSFWorkbook workbook = generate(bill)) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			assertNotNull(sheet.getDrawingPatriarch(), "no drawing created — signature images were not embedded");
			assertEquals(3, sheet.getDrawingPatriarch().getShapes().size(),
					"expected one embedded signature image per signed slot");
		}
	}

	@Test
	@DisplayName("printed names are written into the sign-off area")
	void writesPrintedNames() throws Exception {
		when(fileStoreUtil.downloadFile(eq(TENANT_ID), anyString())).thenReturn(PNG_BYTES);

		ReportBill bill = baseBill().signatures(List.of(
				signature(ReportSignature.SLOT_PREPARED_BY, "Ada Editor", "fs-prepared"),
				signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", "fs-approved"))).build();

		try (XSSFWorkbook workbook = generate(bill)) {
			String text = sheetText(workbook.getSheetAt(0));
			assertTrue(text.contains("Ada Editor"), "preparer's printed name missing from the voucher");
			assertTrue(text.contains("Chidi Approver"), "approver's printed name missing from the voucher");
		}
	}

	@Test
	@DisplayName("a signature reused across slots is fetched from filestore only once")
	void fetchesEachDistinctImageOnce() throws Exception {
		when(fileStoreUtil.downloadFile(eq(TENANT_ID), anyString())).thenReturn(PNG_BYTES);

		ReportBill bill = baseBill().signatures(List.of(
				signature(ReportSignature.SLOT_VERIFIED_BY, "Bem Reviewer", "fs-shared"),
				signature(ReportSignature.SLOT_APPROVED_BY, "Bem Reviewer", "fs-shared"))).build();

		try (XSSFWorkbook workbook = generate(bill)) {
			assertEquals(2, workbook.getSheetAt(0).getDrawingPatriarch().getShapes().size(),
					"both slots should still render the shared signature");
		}
		verify(fileStoreUtil, times(1)).downloadFile(TENANT_ID, "fs-shared");
	}

	@Test
	@DisplayName("a bill with no captured sign-off renders exactly as it did before")
	void leavesSignOffAreaBlankWhenNothingSigned() throws Exception {
		ReportBill bill = baseBill().signatures(Collections.emptyList()).build();

		try (XSSFWorkbook workbook = generate(bill)) {
			assertNull(workbook.getSheetAt(0).getDrawingPatriarch(),
					"an unsigned bill must not add a drawing to the voucher");
		}
		verify(fileStoreUtil, never()).downloadFile(anyString(), anyString());
	}

	@Test
	@DisplayName("an unreachable image still leaves the printed name on the voucher")
	void survivesAnUnreachableImage() throws Exception {
		when(fileStoreUtil.downloadFile(eq(TENANT_ID), anyString()))
				.thenThrow(new RuntimeException("filestore unavailable"));

		ReportBill bill = baseBill().signatures(List.of(
				signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", "fs-missing"))).build();

		try (XSSFWorkbook workbook = generate(bill)) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			assertTrue(sheetText(sheet).contains("Chidi Approver"),
					"the printed name must survive a filestore failure");
			assertNull(sheet.getDrawingPatriarch(), "no image should be embedded when the fetch failed");
		}
	}

	@Test
	@DisplayName("the bill's own tenant is used to fetch signatures, not the state-level tenant")
	void fetchesFromTheBillsTenant() throws Exception {
		when(fileStoreUtil.downloadFile(anyString(), anyString())).thenReturn(PNG_BYTES);

		ReportBill bill = baseBill()
				.tenantId("bednet.borno")
				.signatures(List.of(signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", "fs-approved")))
				.build();

		generate(bill).close();

		verify(fileStoreUtil).downloadFile("bednet.borno", "fs-approved");
	}

	@Test
	@DisplayName("the uploaded file really carries the image parts, not just in-memory shapes")
	void uploadedWorkbookContainsImageParts() throws Exception {
		when(fileStoreUtil.downloadFile(eq(TENANT_ID), anyString())).thenReturn(PNG_BYTES);

		ReportBill bill = baseBill().signatures(List.of(
				signature(ReportSignature.SLOT_APPROVED_BY, "Chidi Approver", "fs-approved"))).build();

		generate(bill).close();

		List<String> entries = uploadedEntries();
		assertTrue(entries.stream().anyMatch(name -> name.startsWith("xl/media/")),
				"no xl/media entry — the signature image never reached the saved file: " + entries);
		assertTrue(entries.stream().anyMatch(name -> name.startsWith("xl/drawings/")),
				"no xl/drawings entry — the image has no anchor in the saved file: " + entries);
	}

	// ---------- helpers ----------

	private XSSFWorkbook generate(ReportBill reportBill) throws Exception {
		String fileStoreId = generator.generateExcel(new RequestInfo(), reportBill);
		assertEquals("generated-excel-id", fileStoreId);

		ArgumentCaptor<Resource> captor = ArgumentCaptor.forClass(Resource.class);
		verify(fileStoreUtil).uploadFileAndGetFileStoreId(anyString(), captor.capture());

		lastWorkbookBytes = ((ByteArrayResource) captor.getValue()).getByteArray();
		return new XSSFWorkbook(new ByteArrayInputStream(lastWorkbookBytes));
	}

	/** Zip entries of the workbook actually handed to filestore. */
	private List<String> uploadedEntries() throws Exception {
		List<String> entries = new java.util.ArrayList<>();
		try (java.util.zip.ZipInputStream zip =
					 new java.util.zip.ZipInputStream(new ByteArrayInputStream(lastWorkbookBytes))) {
			for (java.util.zip.ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry())
				entries.add(entry.getName());
		}
		return entries;
	}

	private ReportBill.ReportBillBuilder baseBill() {
		return ReportBill.builder()
				.tenantId(TENANT_ID)
				.campaignName("Test Campaign")
				.reportTitle("PAYMENT_TITLE")
				.totalAmount(new BigDecimal("1530.00"))
				.numberOfIndividuals(3)
				.createdBy("PR")
				.createdTime(System.currentTimeMillis())
				.billingPeriodLabel("Period 1")
				.billingPeriodDateRange("14/08/2026 - 16/08/2026")
				.reportBillDetails(Collections.emptyList())
				.fieldConfigs(RateFieldConfigConstants.DEFAULT_FIELD_CONFIGS);
	}

	private ReportSignature signature(String slot, String printedName, String fileStoreId) {
		return ReportSignature.builder()
				.slot(slot)
				.printedName(printedName)
				.fileStoreId(fileStoreId)
				.role("PAYMENT_APPROVER")
				.signedTime(System.currentTimeMillis())
				.build();
	}

	private String sheetText(Sheet sheet) {
		StringBuilder text = new StringBuilder();
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.STRING)
					text.append(cell.getStringCellValue()).append('\n');
			}
		}
		return text.toString();
	}
}
