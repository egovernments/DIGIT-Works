package org.egov.digit.expense.service.scheduler;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SchedulerWakeupEvent extends ApplicationEvent {

    private final String tenantId;
    private final long delayMs;

    public SchedulerWakeupEvent(Object source, String tenantId, long delayMs) {
        super(source);
        this.tenantId = tenantId;
        this.delayMs = delayMs;
    }
}
