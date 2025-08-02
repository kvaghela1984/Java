package org.example.workflow;

import java.util.List;

public class TaskDefinition {
    public String name;
    public String type; // simple | sequential | parallel
    public int sleep;
    public List<TaskDefinition> tasks; // for groups
}