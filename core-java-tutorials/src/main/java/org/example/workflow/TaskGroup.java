package org.example.workflow;

import java.util.List;

public abstract class TaskGroup implements Task {
    protected final List<Task> tasks;
    protected final String name;

    public TaskGroup(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    @Override
    public String getName() {
        return name;
    }
}