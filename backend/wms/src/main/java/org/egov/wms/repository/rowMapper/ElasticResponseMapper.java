package org.egov.wms.repository.rowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.wms.web.model.AggsResponse;
import org.egov.wms.web.model.ProjectPaymentDetails;
import org.egov.wms.web.model.Total;
import org.egov.wms.web.model.PaymentDetailsByBillType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ElasticResponseMapper {

    private final ObjectMapper objectMapper;

    public ElasticResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AggsResponse mapElasticResponse(Object elasticResponse) throws JsonProcessingException {
//        String stringResponse = objectMapper.writeValueAsString(elasticResponse);

        JsonNode rootNode = objectMapper.convertValue(elasticResponse, JsonNode.class);

        // Initialize the response object
        AggsResponse aggsResponse = new AggsResponse();

        // Map Total Aggregation
        Total total = mapTotalAggregation(rootNode.get("aggregations").get("Total"));
        aggsResponse.setTotal(total);

        // Map Projects Aggregation
        List<ProjectPaymentDetails> projectPaymentDetailsList = mapProjectsAggregation(rootNode.get("aggregations").get("Projects").get("buckets"));
        aggsResponse.setProjectPaymentDetails(projectPaymentDetailsList);

        // Set afterKey
        if (rootNode.get("aggregations").get("Projects").has("after_key"))
            aggsResponse.setAfterKey(rootNode.get("aggregations").get("Projects").get("after_key").get("project").asText());

        return aggsResponse;
    }

    private Total mapTotalAggregation(JsonNode totalNode) {
        Total total = new Total();
        List<PaymentDetailsByBillType> paymentDetailsList = new ArrayList<>();

        // Iterate over the "buckets" array in the total aggregation
        JsonNode buckets = totalNode.get("buckets");
        for (JsonNode bucket : buckets) {
            PaymentDetailsByBillType paymentDetails = new PaymentDetailsByBillType();
            paymentDetails.setBillType(bucket.get("key").asText());
            paymentDetails.setTotal(bucket.get("total_amount").get("value").asDouble());
            paymentDetails.setPaidAmount(bucket.get("paid_amount").get("value").asDouble());
            paymentDetails.setRemainingAmount(bucket.get("failed_amount").get("value").asDouble());

            paymentDetailsList.add(paymentDetails);
        }

        total.setPaymentDetailsByBillType(paymentDetailsList);
        return total;
    }

    private List<ProjectPaymentDetails> mapProjectsAggregation(JsonNode projectBucketsNode) {
        List<ProjectPaymentDetails> projectPaymentDetailsList = new ArrayList<>();

        for (JsonNode projectNode : projectBucketsNode) {
            ProjectPaymentDetails projectDetails = new ProjectPaymentDetails();
            projectDetails.setProjectNumber(projectNode.get("key").get("project").asText());

            // Map billType aggregation
            List<PaymentDetailsByBillType> paymentDetailsList = new ArrayList<>();
            JsonNode billTypeBuckets = projectNode.get("billType").get("buckets");
            double totalSum = 0;

            for (JsonNode billTypeNode : billTypeBuckets) {
                PaymentDetailsByBillType paymentDetails = new PaymentDetailsByBillType();
                paymentDetails.setBillType(billTypeNode.get("key").asText());
                double totalAmount = billTypeNode.get("total_amount").get("value").asDouble();
                paymentDetails.setTotal(totalAmount);
                paymentDetails.setPaidAmount(billTypeNode.get("paid_amount").get("value").asDouble());
                paymentDetails.setRemainingAmount(billTypeNode.get("failed_amount").get("value").asDouble());

                totalSum += totalAmount;

                paymentDetailsList.add(paymentDetails);
            }

            projectDetails.setTotal(totalSum);
            projectDetails.setPaymentDetails(paymentDetailsList);
            projectPaymentDetailsList.add(projectDetails);
        }

        return projectPaymentDetailsList;
    }
}

