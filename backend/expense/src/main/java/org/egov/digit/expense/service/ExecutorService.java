package org.egov.digit.expense.service;

import org.egov.digit.expense.web.models.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ExecutorService {

    public void scheduleTask(Runnable task,Integer delay,TimeUnit unit)
    {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Delay in seconds
        ScheduledFuture<?> future = scheduler.schedule(task, delay, unit);

        try {
            // Wait for the task to complete
            future.get(); // This will block until the task has executed
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            scheduler.shutdown();
        }
    }
}
