package org.egov.wms.repository.builder;

import lombok.extern.slf4j.Slf4j;
import org.egov.wms.web.model.AggregationRequest;
import org.egov.wms.web.model.WMSSearchRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ReportESQueryBuilder {


    private final WMSSearchQueryBuilder wmsSearchQueryBuilder;

    public ReportESQueryBuilder(WMSSearchQueryBuilder wmsSearchQueryBuilder) {
        this.wmsSearchQueryBuilder = wmsSearchQueryBuilder;
    }

    public Map<String, Object> getReportEsQuery(AggregationRequest aggregationRequest, WMSSearchRequest wmsSearchRequest, String module) {


        Map<String, Object> finalQueryBody = wmsSearchQueryBuilder.getESQuery(wmsSearchRequest, Boolean.TRUE, module);
        addAggregationQuery(aggregationRequest, finalQueryBody);

        return finalQueryBody;
    }

    private void addAggregationQuery(AggregationRequest aggregationRequest, Map<String, Object> finalQueryBody) {
        finalQueryBody.replace("size", 0);
        Object aggsQuery = createAggsQuery(aggregationRequest);
        finalQueryBody.put("aggs", aggsQuery);
    }

    private Object createAggsQuery(AggregationRequest aggregationRequest) {
        Map<String, Object> buckets_path = new HashMap<>();
        buckets_path.put("total_amount", "total_amount");
        buckets_path.put("paid_amount", "paid_amount");

        Map<String, Object> bucket_script = new HashMap<>();
        bucket_script.put("buckets_path", buckets_path);
        bucket_script.put("script", "params.total_amount - params.paid_amount");

        Map<String, Object> failed_amount = new HashMap<>();
        failed_amount.put("bucket_script", bucket_script);



        Map<String, Object> sumPaid = new HashMap<>();
        sumPaid.put("field", "Data.totalPaidAmount");

        Map<String, Object> paid_amount = new HashMap<>();
        paid_amount.put("sum", sumPaid);

        Map<String, Object> sumTotal = new HashMap<>();
        sumTotal.put("field", "Data.totalAmount");

        Map<String, Object> total_amount = new HashMap<>();
        total_amount.put("sum", sumTotal);

        Map<String, Object> innerAggs = new HashMap<>();
        innerAggs.put("failed_amount", failed_amount);
        innerAggs.put("paid_amount", paid_amount);
        innerAggs.put("total_amount", total_amount);

        Map<String, Object> terms = new HashMap<>();
        terms.put("field", "Data.businessService.keyword");
        terms.put("size", aggregationRequest.getAggregationSearchCriteria().getLimit());

        Map<String, Object> Total = new HashMap<>();
        Total.put("terms", terms);
        Total.put("aggs", innerAggs);




        // Creating sources for composite aggregation (projectId keyword field)
        Map<String, Object> projectField = new HashMap<>();
        projectField.put("field", "Data.additionalDetails.projectId.keyword");

        Map<String, Object> projectTerms = new HashMap<>();
        projectTerms.put("terms", projectField);

        Map<String, Object> projectSource = new HashMap<>();
        projectSource.put("project", projectTerms);

        List<Map<String, Object>> sources = new ArrayList<>();
        sources.add(projectSource);

// Composite aggregation definition
        Map<String, Object> composite = new HashMap<>();
        composite.put("size", aggregationRequest.getAggregationSearchCriteria().getLimit()); // Size limit for composite aggregation
        composite.put("sources", sources);

// Adding the 'after' key for pagination (optional, depending on requirement)
        Map<String, Object> after = new HashMap<>();
        Map<String, Object> afterTerms = new HashMap<>();
        afterTerms.put("terms", aggregationRequest.getAggregationSearchCriteria().getAfterKey());

        after.put("project", afterTerms); // Project ID after which results should be returned
        composite.put("after", after);

// Creating sub-aggregations for billType similar to the "Total" aggregation
        Map<String, Object> billTypeTerms = new HashMap<>();
        billTypeTerms.put("field", "Data.businessService.keyword");
        billTypeTerms.put("size", aggregationRequest.getAggregationSearchCriteria().getLimit()); // Limit the billType terms aggregation

//// Sub-aggregations for total_amount, paid_amount, failed_amount within billType
//        Map<String, Object> billTypeInnerAggs = new HashMap<>();
//        billTypeInnerAggs.put("total_amount", total_amount);  // Reusing total_amount from the "Total" section
//        billTypeInnerAggs.put("paid_amount", paid_amount);    // Reusing paid_amount from the "Total" section
//        billTypeInnerAggs.put("failed_amount", failed_amount); // Reusing failed_amount from the "Total" section

// Aggregating billType terms with sub-aggregations
        Map<String, Object> billType = new HashMap<>();
        billType.put("terms", billTypeTerms);
        billType.put("aggs", innerAggs);

// Adding the billType aggregation under the main Projects aggregation
        Map<String, Object> projectAggs = new HashMap<>();
        projectAggs.put("billType", billType);

// Combining composite aggregation with sub-aggregations
        Map<String, Object> projects = new HashMap<>();
        projects.put("composite", composite);
        projects.put("aggs", projectAggs);

// Final Projects object (as part of your final query)
        Map<String, Object> aggs = new HashMap<>();
        aggs.put("Projects", projects);
        aggs.put("Total", Total);


// You can now merge this `aggs` map with the Total object if needed, to complete your final aggregation query.



        return aggs;

    }



}
