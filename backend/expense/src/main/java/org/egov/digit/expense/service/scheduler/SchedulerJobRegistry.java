package org.egov.digit.expense.service.scheduler;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight registry of tenant IDs that have active scheduler jobs.
 * Kept separate from {@code SchedulerService} so that {@code MTNService} can register
 * tenants without creating a circular Spring dependency.
 */
@Component
public class SchedulerJobRegistry {

    private final Set<String> activeTenants = ConcurrentHashMap.newKeySet();

    /**
     * Register a tenant so the scheduler starts polling for its jobs.
     * Safe to call from any thread — backed by a {@link ConcurrentHashMap}.
     */
    public void register(String tenantId) {
        activeTenants.add(tenantId);
    }

    /** Returns an unmodifiable view of the currently registered tenants. */
    public Set<String> getActiveTenants() {
        return Collections.unmodifiableSet(activeTenants);
    }
}
