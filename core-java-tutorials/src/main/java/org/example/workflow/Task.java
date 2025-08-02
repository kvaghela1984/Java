package org.example.workflow;

import java.util.concurrent.CompletableFuture;

public interface Task {
    CompletableFuture<Void> execute();
    String getName();
}