package org.example.workflow;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SequentialGroup extends TaskGroup {
    public SequentialGroup(String name, java.util.List<Task> tasks) {
        super(name, tasks);
    }

    @Override
    public CompletableFuture<Void> execute() {
        java.util.concurrent.CompletableFuture<Void> future = java.util.concurrent.CompletableFuture.completedFuture(null);
        for (Task task : tasks) {
            try {
                task.execute().get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return future;
    }
}