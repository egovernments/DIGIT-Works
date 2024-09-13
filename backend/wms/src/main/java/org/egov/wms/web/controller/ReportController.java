package org.egov.wms.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.wms.service.ReportService;
import org.egov.wms.util.ResponseInfoFactory;
import org.egov.wms.web.model.AggregationRequest;
import org.egov.wms.web.model.AggregationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

import static org.reflections.util.ConfigurationBuilder.build;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    private final ReportService reportService;
    private final ResponseInfoFactory responseInfoFactory;
    private final ObjectMapper mapper;

    public ReportController(ReportService reportService, ResponseInfoFactory responseInfoFactory, ObjectMapper mapper) {
        this.reportService = reportService;
        this.responseInfoFactory = responseInfoFactory;
        this.mapper = mapper;
    }


    @PostMapping(value = "/payment_tracker")
    public ResponseEntity<AggregationResponse> getPaymentTracker(@Valid @RequestBody AggregationRequest aggregationRequest) {


        AggregationResponse aggregationResponse = AggregationResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(aggregationRequest.getRequestInfo(), true))
                .aggsResponse(reportService.getPaymentTracker(aggregationRequest))
                .build();
        try {
            aggregationResponse = mapper.readValue("{\n" +
                    "  \"ResponseInfo\": {\n" +
                    "    \"apiId\": \"string\",\n" +
                    "    \"ver\": \"string\",\n" +
                    "    \"ts\": 0,\n" +
                    "    \"resMsgId\": \"string\",\n" +
                    "    \"msgId\": \"string\",\n" +
                    "    \"status\": \"SUCCESSFUL\"\n" +
                    "  },\n" +
                    "  \"aggsResponse\": {\n" +
                    "    \"total\": {\n" +
                    "      \"estimatedAmount\": 0,\n" +
                    "      \"paymentDetails\": [\n" +
                    "        {\n" +
                    "          \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    \"projects\": [\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000169\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000170\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000171\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000172\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000173\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000174\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000175\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000176\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000177\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000178\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000179\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"projectNumber\": \"PJ/2023-24/000180\",\n" +
                    "        \"estimatedAmount\": 2250000,\n" +
                    "        \"total\": 2250000,\n" +
                    "        \"paymentDetails\": [\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.PURCHASE\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "          \"billType\": \"EXPENSE.WAGE\",\n" +
                    "          \"total\": 1412312,\n" +
                    "          \"paidAmount\": 1410000,\n" +
                    "          \"remainingAmount\": 2312\n" +
                    "          },\n" +
                    "          {\n" +
                    "            \"billType\": \"EXPENSE.SUPERVISION\",\n" +
                    "            \"total\": 1412312,\n" +
                    "            \"paidAmount\": 1410000,\n" +
                    "            \"remainingAmount\": 2312\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}\n", AggregationResponse.class);
//            aggregationResponse = mapper.readValue(new File("../wms/src/main/resources/payment_tracker_sample.json"), AggregationResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(aggregationResponse, HttpStatus.OK);
    }


}
