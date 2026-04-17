package org.egov.digit.expense.service;

import org.egov.digit.expense.web.models.TaskRequest;

/**
 * Strategy interface for payment-provider-specific task execution.
 *
 * <p>Each implementation handles one payment provider (MTN, BANK, etc.).
 * {@link org.egov.digit.expense.kafka.TaskConsumer} collects all provider-values
 * present in the incoming bill's details and dispatches to every matching service.
 *
 * <p>To add a new provider: implement this interface, annotate with {@code @Service},
 * return the provider constant from {@link #supports}, and implement {@link #executeTask}.
 */
public interface PaymentProviderService {

    /**
     * Returns true if this service should handle the given payment provider value.
     *
     * @param paymentProvider the raw {@code payee.paymentProvider} string from a bill detail,
     *                        or {@code null} when no provider is configured.
     */
    boolean supports(String paymentProvider);

    /**
     * Executes the task (Verify or Transfer) for the bill details this service owns.
     * Implementations must internally skip details whose provider they do not own.
     */
    void executeTask(TaskRequest taskRequest);
}
