package com.example.task;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TaskFacade {
    public void createAndShowPane(VBox tasks) {
        TaskBuilder paneBuilder = new TaskBuilder();
        Pane pane = paneBuilder.addTextField().build();
        tasks.getChildren().add(pane);
        tasks.setPrefHeight(tasks.getPrefHeight() + 33 * tasks.getChildren().size());
    }
}
