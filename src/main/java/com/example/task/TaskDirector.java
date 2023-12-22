package com.example.task;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TaskDirector {
    private final TaskBuilder taskBuilder;

    public TaskDirector() {
        this.taskBuilder = new TaskBuilder();
    }

    public void createAndShowTaskPane(VBox tasks) {
        Pane taskPane = taskBuilder.addTextField().build();
        tasks.getChildren().add(taskPane);
        tasks.setPrefHeight(tasks.getPrefHeight() + 33 * tasks.getChildren().size());
    }
}
