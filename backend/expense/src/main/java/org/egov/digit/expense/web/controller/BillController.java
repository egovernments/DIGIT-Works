package org.egov.digit.expense.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.egov.digit.expense.service.BillDetailTemplateService;
import org.egov.digit.expense.service.BillService;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/bill/v1/")
public class BillController {

	private final BillService service;
	private final ResponseInfoFactory responseInfoFactory;
	private final BillDetailTemplateService templateService;

	@Autowired
	public BillController(BillService service, ResponseInfoFactory responseInfoFactory,
						  BillDetailTemplateService templateService) {
		this.service         = service;
		this.responseInfoFactory = responseInfoFactory;
		this.templateService = templateService;
	}

	@PostMapping(value = "_create")
	public ResponseEntity<BillResponse> create(@Valid @RequestBody BillRequest billRequest) {
		BillResponse response = service.create(billRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "_search")
	public ResponseEntity<BillResponse> search(@Valid @RequestBody BillSearchRequest billSearchRequest) {
		BillResponse billResponse = service.search(billSearchRequest, true);
		return new ResponseEntity<>(billResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "_update")
	public ResponseEntity<BillResponse> update(@Valid @RequestBody BillRequest billRequest) {
		BillResponse response = service.update(billRequest);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "_updatereportmeta")
	public ResponseEntity<BillResponse> updateReportMeta(@Valid @RequestBody ReportMetaUpdateRequest request) {
		BillResponse response = service.updateReportMeta(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "search/_calculated")
	public ResponseEntity<BillResponse> searchCalculatedBills(@Valid @RequestBody BillSearchRequest billSearchRequest) {
		BillResponse billResponse = service.searchCalculatedBills(billSearchRequest, true);
		return new ResponseEntity<>(billResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "_bulkupdatestatus")
	public ResponseEntity<BulkBillStatusUpdateResponse> bulkUpdateStatus(@Valid @RequestBody BulkBillStatusUpdateRequest bulkRequest) {
		BulkBillStatusUpdateResponse response = service.bulkUpdateStatus(bulkRequest);
		boolean hasErrors = response.getErrors() != null && !response.getErrors().isEmpty();
		boolean hasSuccess = response.getBills() != null && !response.getBills().isEmpty();
		HttpStatus status = hasErrors ? (hasSuccess ? HttpStatus.MULTI_STATUS : HttpStatus.UNPROCESSABLE_ENTITY) : HttpStatus.ACCEPTED;
		return new ResponseEntity<>(response, status);
	}

	@PostMapping(value = "billdetails/_update")
	public ResponseEntity<BillDetailUpdateResponse> partialUpdateBillDetails(
			@Valid @RequestBody BillDetailUpdateRequest request) {
		BillDetailUpdateResponse response = service.partialUpdateBillDetails(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "billdetails/_generateTemplate")
	public void generateBillDetailTemplate(
			@Valid @RequestBody BillTemplateRequest request,
			HttpServletResponse response) throws IOException {
		byte[] bytes = templateService.generateTemplateBytes(request);
		String filename = "bill-details-template-" + request.getBillId() + ".xlsx";
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		response.setContentLength(bytes.length);
		try (OutputStream out = response.getOutputStream()) {
			out.write(bytes);
		}
	}

	@PostMapping(value = "billdetails/_uploadTemplate")
	public ResponseEntity<BillDetailUpdateResponse> uploadBillDetailTemplate(
			@Valid @RequestBody BillTemplateUploadRequest request) {
		BillDetailUpdateResponse response = templateService.processUpload(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
