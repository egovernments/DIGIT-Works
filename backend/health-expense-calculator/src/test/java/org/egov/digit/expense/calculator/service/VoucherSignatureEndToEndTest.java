package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.BillExcelGenerate;
import org.egov.digit.expense.calculator.util.FileStoreUtil;
import org.egov.digit.expense.calculator.util.LocalizationUtil;
import org.egov.digit.expense.calculator.util.RateFieldConfigConstants;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportSignature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The whole voucher signature path, end to end and offline: a bill carrying the sign-off data the
 * expense service writes, through slot resolution, into a generated workbook whose bytes are then
 * unzipped and inspected.
 *
 * This is the local stand-in for "download the voucher and look at it". The final assertions are
 * deliberately the same checks used on the real UAT file — that xl/media and xl/drawings entries
 * exist — so a pass here means the same inspection on a deployed build would find the image.
 *
 * Filestore is mocked, so no database, kafka, filestore or DIGIT service is needed.
 */
class VoucherSignatureEndToEndTest {

	private static final String TENANT_ID = "bednet";
	private static final String LOCALE = "en_IN";

	/**
	 * A drawn, visibly signature-like PNG rather than a 1x1 pixel, so the workbook this test writes
	 * to target/ can actually be opened and looked at. Generated rather than checked in, to keep a
	 * real person's signature out of the repository.
	 */
	private static final byte[] PNG_BYTES = signatureImage();

	private static byte[] signatureImage() {
		BufferedImage image = new BufferedImage(600, 160, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(0x1A, 0x23, 0x7E));
		g.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		GeneralPath stroke = new GeneralPath();
		stroke.moveTo(40, 110);
		stroke.curveTo(120, 20, 200, 150, 280, 70);
		stroke.curveTo(340, 15, 390, 135, 470, 75);
		stroke.curveTo(500, 50, 520, 95, 545, 85);
		g.draw(stroke);

		g.setStroke(new BasicStroke(2f));
		g.drawLine(40, 138, 560, 138);
		g.dispose();

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", out);
			return out.toByteArray();
		} catch (Exception e) {
			throw new IllegalStateException("could not build the test signature image", e);
		}
	}

	/** Exactly what the expense service stores: additionalDetails.signatures as BillSignature json. */
	private static final String SIGNED_ADDITIONAL_DETAILS = """
			{
			  "signatures": [
			    {"id":"s1","printedName":"Ada Editor","fileStoreId":"fs-prepared","action":"SEND_FOR_REVIEW",
			     "role":"PAYMENT_EDITOR","signedBy":"uuid-1","signedTime":1755000000000},
			    {"id":"s2","printedName":"Bem Reviewer","fileStoreId":"fs-verified","action":"SEND_FOR_APPROVAL",
			     "role":"PAYMENT_REVIEWER","signedBy":"uuid-2","signedTime":1755100000000},
			    {"id":"s3","printedName":"Chidi Approver","fileStoreId":"fs-approved","action":"PAYMENT_INITIATION",
			     "role":"PAYMENT_APPROVER","signedBy":"uuid-3","signedTime":1755200000000}
			  ]
			}""";

	@Test
	@DisplayName("a signed bill produces a voucher carrying all three signatures and names")
	void signedBillProducesVoucherWithSignatures() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		FileStoreUtil fileStoreUtil = mock(FileStoreUtil.class);

		when(fileStoreUtil.downloadFile(anyString(), anyString())).thenReturn(PNG_BYTES);
		when(fileStoreUtil.uploadFileAndGetFileStoreId(anyString(), any(Resource.class)))
				.thenReturn("voucher-id");

		// --- step 1: the bill as the expense service hands it over
		Bill bill = Bill.builder()
				.id("bill-1")
				.tenantId(TENANT_ID)
				.additionalDetails(objectMapper.readTree(SIGNED_ADDITIONAL_DETAILS))
				.build();

		// --- step 2: resolve the sign-offs onto voucher slots
		HealthBillReportGenerator reportGenerator = new HealthBillReportGenerator(
				null, null, null, null, null, null, null, null, null,
				objectMapper, null, null, fileStoreUtil);
		List<ReportSignature> signatures = reportGenerator.resolveSignatures(bill);
		assertEquals(3, signatures.size(), "all three sign-offs should reach the report");

		// --- step 3: generate the workbook
		byte[] voucher = generateVoucher(fileStoreUtil, signatures);

		// Drop the workbook next to the build output so it can be opened in Excel and eyeballed.
		// This is the local equivalent of downloading the voucher from UAT.
		dumpForInspection(voucher, "sample-voucher-signed.xlsx");

		// --- step 4: inspect the produced file the same way the UAT download was inspected
		List<String> entries = zipEntries(voucher);
		assertTrue(entries.stream().anyMatch(e -> e.startsWith("xl/media/")),
				"no xl/media entry — the signature images are not in the file: " + entries);
		assertTrue(entries.stream().anyMatch(e -> e.startsWith("xl/drawings/")),
				"no xl/drawings entry — the images have no anchor: " + entries);

		try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(voucher))) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			assertNotNull(sheet.getDrawingPatriarch());
			assertEquals(3, sheet.getDrawingPatriarch().getShapes().size(),
					"expected one signature image per slot");

			String text = sheetText(sheet);
			assertTrue(text.contains("Ada Editor"), "preparer name missing");
			assertTrue(text.contains("Bem Reviewer"), "reviewer name missing");
			assertTrue(text.contains("Chidi Approver"), "approver name missing");
		}
	}

	@Test
	@DisplayName("an unsigned bill still produces a valid voucher, with no image parts")
	void unsignedBillProducesVoucherWithoutSignatures() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		FileStoreUtil fileStoreUtil = mock(FileStoreUtil.class);
		when(fileStoreUtil.uploadFileAndGetFileStoreId(anyString(), any(Resource.class)))
				.thenReturn("voucher-id");

		HealthBillReportGenerator reportGenerator = new HealthBillReportGenerator(
				null, null, null, null, null, null, null, null, null,
				objectMapper, null, null, fileStoreUtil);

		// A bill approved before sign-off capture existed — the common case for historical bills.
		List<ReportSignature> signatures = reportGenerator.resolveSignatures(
				Bill.builder().id("old-bill").tenantId(TENANT_ID).build());
		assertTrue(signatures.isEmpty());

		byte[] voucher = generateVoucher(fileStoreUtil, signatures);
		dumpForInspection(voucher, "sample-voucher-unsigned.xlsx");

		assertTrue(zipEntries(voucher).stream().noneMatch(e -> e.startsWith("xl/media/")),
				"an unsigned bill must not add image parts");
		try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(voucher))) {
			assertNotNull(workbook.getSheetAt(0), "the voucher must still generate");
		}
	}

	// ---------- helpers ----------

	private byte[] generateVoucher(FileStoreUtil fileStoreUtil, List<ReportSignature> signatures) {
		LocalizationUtil localizationUtil = mock(LocalizationUtil.class);
		ExpenseCalculatorConfiguration config = mock(ExpenseCalculatorConfiguration.class);

		Map<String, Map<String, String>> messages = new HashMap<>();
		messages.put(LOCALE + "|" + TENANT_ID, new HashMap<>());

		when(config.getStateLevelTenantId()).thenReturn(TENANT_ID);
		when(config.getReportLocalizationModuleName()).thenReturn("rainmaker-common");
		when(config.getReportDateTimeFormat()).thenReturn("dd/MM/yyyy HH:mm:ss");
		when(config.getReportDateTimeZone()).thenReturn("Asia/Kolkata");
		when(localizationUtil.getLocalCode(any())).thenReturn(LOCALE);
		when(localizationUtil.getLocalisedMessages(any(), anyString(), anyString(), anyString()))
				.thenReturn(messages);

		BillExcelGenerate excelGenerator = new BillExcelGenerate(localizationUtil, config, fileStoreUtil);

		ReportBill reportBill = ReportBill.builder()
				.tenantId(TENANT_ID)
				.campaignName("Test13thUAT")
				.reportTitle("PAYMENT_TITLE")
				.totalAmount(new BigDecimal("1530.00"))
				.numberOfIndividuals(3)
				.createdBy("PR")
				.createdTime(System.currentTimeMillis())
				.billingPeriodLabel("Period 1")
				.billingPeriodDateRange("14/08/2026 - 16/08/2026")
				.reportBillDetails(Collections.emptyList())
				.fieldConfigs(RateFieldConfigConstants.DEFAULT_FIELD_CONFIGS)
				.signatures(signatures)
				.build();

		assertEquals("voucher-id", excelGenerator.generateExcel(new RequestInfo(), reportBill));

		ArgumentCaptor<Resource> captor = ArgumentCaptor.forClass(Resource.class);
		org.mockito.Mockito.verify(fileStoreUtil).uploadFileAndGetFileStoreId(anyString(), captor.capture());
		return ((ByteArrayResource) captor.getValue()).getByteArray();
	}

	/**
	 * Writes the generated voucher into target/ so it can be opened and looked at. Purely a
	 * convenience for local inspection — the assertions never depend on it, and a failure to write
	 * is ignored rather than failing the test.
	 */
	private void dumpForInspection(byte[] workbookBytes, String fileName) {
		try {
			java.nio.file.Path out = java.nio.file.Paths.get("target", fileName);
			java.nio.file.Files.write(out, workbookBytes);
			System.out.println("Wrote voucher for inspection: " + out.toAbsolutePath());
		} catch (Exception e) {
			System.out.println("Could not write " + fileName + " for inspection: " + e.getMessage());
		}
	}

	private List<String> zipEntries(byte[] workbookBytes) throws Exception {
		List<String> entries = new ArrayList<>();
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(workbookBytes))) {
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry())
				entries.add(entry.getName());
		}
		return entries;
	}

	private String sheetText(XSSFSheet sheet) {
		StringBuilder text = new StringBuilder();
		for (Row row : sheet)
			for (Cell cell : row)
				if (cell.getCellType() == CellType.STRING)
					text.append(cell.getStringCellValue()).append('\n');
		return text.toString();
	}
}
