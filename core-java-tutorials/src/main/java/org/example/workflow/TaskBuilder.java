package org.example.workflow;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class TaskBuilder {
    private final ExecutorService executor;

    public TaskBuilder(ExecutorService executor) {
        this.executor = executor;
    }

    public Task buildTask(TaskDefinition def) {
        return switch (def.type.toLowerCase()) {
            case "simple" -> new SimpleTask(def.name, sleep(def.sleep, def.name));
            case "sequential" -> new SequentialGroup(def.name,
                    def.tasks.stream().map(this::buildTask).toList());
            case "parallel" -> new ParallelGroup(def.name,
                    def.tasks.stream().map(this::buildTask).toList(), executor);
            default -> throw new IllegalArgumentException("Unknown task type: " + def.type);
        };
    }

    private static Runnable sleep(long millis, String name) {
        return () -> {
            try {
                Thread.sleep(millis);
                System.out.println("Task Completed " + name);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
}