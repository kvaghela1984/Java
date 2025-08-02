package org.example.workflow;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleTask implements Task {
    private final String name;
    private final Runnable action;

    public SimpleTask(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public CompletableFuture<Void> execute() {
        try {
            CompletableFuture.runAsync(() -> {
                action.run();
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return name;
    }
}