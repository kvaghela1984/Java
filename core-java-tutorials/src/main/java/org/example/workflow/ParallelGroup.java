package org.example.workflow;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelGroup extends TaskGroup {
    private final ExecutorService executor;

    public ParallelGroup(String name, List<Task> tasks, ExecutorService executor) {
        super(name, tasks);
        this.executor = executor;
    }

    @Override
    public CompletableFuture<Void> execute() {

        return CompletableFuture.allOf(
                tasks.stream()
                .map(task -> CompletableFuture.runAsync(() -> {
                            try {
                                task.execute().join();
                            } catch (Exception e) {
                                System.err.println("Error in " + task.getName() + ": " + e.getMessage());
                            }
                        }, executor)
                ).collect(Collectors.toList())
                                .toArray(new CompletableFuture[0]));


    }
}