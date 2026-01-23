package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.TransactionReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PDFServiceUtil {

    private final ServiceRequestRepository restRepo;

    private final Configuration config;

    private final ObjectMapper mapper;
    public static final String PDF_RESPONSE_FILESTORE_ID_JSONPATH = "$.filestoreIds";



    @Autowired
    public PDFServiceUtil(ServiceRequestRepository restRepo, Configuration config, ObjectMapper mapper) {
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
    public String createPDF(TransactionReportRequest request, String tenantId, String pdfKey) {
        Object result = null;
        StringBuilder uri = new StringBuilder();
        String filestoreId = null;
        List<String> filestoreids = null;
        uri.append(config.getPdfServiceHost()).append(config.getPdfServiceCreateEndpoint())
                .append("?tenantId=" + tenantId)
                .append("&key=" + pdfKey);
        try {
            result = restRepo.fetchResult(uri, request);
            filestoreids = JsonPath.read(result, PDF_RESPONSE_FILESTORE_ID_JSONPATH);
        } catch (Exception e) {
            log.error("Exception while creating PDF: " + e);

        }
        if (null != filestoreids && filestoreids.size() > 0) {
            filestoreId = filestoreids.get(0);
        }
        return filestoreId;
    }


}

