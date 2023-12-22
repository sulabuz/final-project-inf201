package com.example.task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
public class TaskBuilder {
    private final Pane pane;
    private final CheckBox complete;
    private final TextField textField;


    public TaskBuilder() {
        this.pane = new Pane();
        this.complete = new CheckBox();
        this.textField = new TextField();
    }

    public TaskBuilder addTextField() {
        complete.setLayoutX(8);
        complete.setLayoutY(8);
        textField.setPromptText("Add task");
        textField.setStyle("-fx-background-color: transparent");
        textField.setLayoutX(43);
        textField.setLayoutY(4);
        textField.requestFocus();
        pane.setPrefHeight(33);
        pane.setStyle("-fx-border-color: #bcbdc1; -fx-border-radius: 8");
        pane.getChildren().addAll(textField, complete);
        return this;
    }

    public Pane build() {
        return pane;
    }
}

