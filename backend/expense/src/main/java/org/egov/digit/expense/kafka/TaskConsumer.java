package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskConsumer {

    private final ObjectMapper objectMapper;
    private final List<PaymentProviderService> paymentProviderServices;

    @Autowired
    public TaskConsumer(ObjectMapper objectMapper, List<PaymentProviderService> paymentProviderServices) {
        this.objectMapper = objectMapper;
        this.paymentProviderServices = paymentProviderServices;
    }

    @KafkaListener(topicPattern = "(${expense.kafka.tenant.id.pattern})${expense.bill.task}")
    public void listen(final Map<String, Object> message) {
        log.info("Consuming message from kafka task topic");
        TaskRequest taskRequest = objectMapper.convertValue(message, TaskRequest.class);

        Set<String> providers = extractProviders(taskRequest.getBill());
        log.info("Task for bill={} providers={}", taskRequest.getTask().getBillId(), providers);

        // Call each service at most once — only if at least one provider in this bill is supported.
        // Each service then handles all its matching details internally in a single pass.
        paymentProviderServices.stream()
                .filter(s -> providers.stream().anyMatch(s::supports))
                .forEach(s -> {
                    log.info("Dispatching task to {}", s.getClass().getSimpleName());
                    s.executeTask(taskRequest);
                });
    }

    /**
     * Extracts the distinct set of paymentProvider values from the bill's details.
     * Null is included when any detail has no provider configured, so that
     * {@link org.egov.digit.expense.service.MTNService} (which handles null) can mark them FAILED.
     */
    private Set<String> extractProviders(Bill bill) {
        if (bill == null || CollectionUtils.isEmpty(bill.getBillDetails())) {
            return Set.of((String) null);
        }
        return bill.getBillDetails().stream()
                .map(d -> {
                    Party payee = d.getPayee();
                    return (payee != null && org.springframework.util.StringUtils.hasText(payee.getPaymentProvider()))
                            ? payee.getPaymentProvider().toUpperCase()
                            : null;
                })
                .collect(Collectors.toSet());
    }
}
