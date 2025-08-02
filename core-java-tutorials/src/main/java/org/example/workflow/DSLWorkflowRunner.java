package org.example.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DSLWorkflowRunner {
    public static void main(String[] args) throws Exception {
        System.out.println(new File(".").getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TaskDefinition rootDef = mapper.readValue(new File("src/main/resources/workflow.yaml"), TaskDefinition.class);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        TaskBuilder builder = new TaskBuilder(executor);

        Task rootTask = builder.buildTask(rootDef);
        rootTask.execute().complete(null);
        executor.shutdown();
        System.out.println("Workflow Completed");
    }
}