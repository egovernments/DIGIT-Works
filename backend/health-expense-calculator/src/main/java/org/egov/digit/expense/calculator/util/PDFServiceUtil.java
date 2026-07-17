package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.report.BillReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PDFServiceUtil {

    private final ServiceRequestRepository restRepo;

    private final ExpenseCalculatorConfiguration config;

    private final ObjectMapper mapper;
    public static final String PDF_RESPONSE_FILESTORE_ID_JSONPATH = "$.filestoreIds";



    @Autowired
    public PDFServiceUtil(ServiceRequestRepository restRepo, ExpenseCalculatorConfiguration config, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.config = config;
        this.mapper = mapper;
    }

    /**
     * Calls the pdf service to generate a pdf document.
     *
     * @param request    the request object containing the data to be printed
     * @param tenantId   the tenant id
     * @param pdfKey     the key to identify the pdf template
     * @return the file store id of the generated pdf file
     */
    private static final int MAX_PDF_RETRIES = 3;
    private static final long PDF_RETRY_BASE_DELAY_MS = 5000L;

    public String createPDF(BillReportRequest request, String tenantId, String pdfKey) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getPdfServiceHost()).append(config.getPdfServiceCreateEndpoint())
                .append("?tenantId=").append(tenantId)
                .append("&key=").append(pdfKey);

        log.info("Calling PDF Service - TenantId: {}, PDFKey: {}, URL: {}", tenantId, pdfKey, uri);
        if (log.isDebugEnabled()) {
            try { log.debug("PDF Service Request Payload: {}", mapper.writeValueAsString(request)); }
            catch (Exception ignored) {}
        }

        Exception lastException = null;
        for (int attempt = 1; attempt <= MAX_PDF_RETRIES; attempt++) {
            try {
                log.info("PDF generation attempt {}/{} for tenantId: {}, key: {}", attempt, MAX_PDF_RETRIES, tenantId, pdfKey);
                Object result = restRepo.fetchResult(uri, request);
                List<String> filestoreIds = JsonPath.read(result, PDF_RESPONSE_FILESTORE_ID_JSONPATH);
                if (filestoreIds != null && !filestoreIds.isEmpty()) {
                    return filestoreIds.get(0);
                }
                lastException = new RuntimeException("PDF service returned empty filestoreIds");
                log.error("PDF generation attempt {}/{} returned no filestoreIds", attempt, MAX_PDF_RETRIES);
            } catch (Exception e) {
                lastException = e;
                log.error("PDF generation attempt {}/{} failed: {}", attempt, MAX_PDF_RETRIES, e.getMessage());
            }
        }
        throw new RuntimeException("PDF generation failed after " + MAX_PDF_RETRIES + " attempts: " + lastException.getMessage(), lastException);
    }


}
