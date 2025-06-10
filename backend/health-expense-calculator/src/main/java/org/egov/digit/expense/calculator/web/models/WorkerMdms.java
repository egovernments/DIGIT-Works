package org.egov.digit.expense.calculator.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerMdms {

    private String campaignId;
    private String eventType;
    private String currency;
    private List<WorkerRate> rates;

}
