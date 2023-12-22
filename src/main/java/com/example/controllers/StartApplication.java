package com.example.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartApplication extends Application {
    public static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/start/logo.png")));

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.getIcons().add(icon);
        stage.setTitle("Efficient Day!");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }
    public static void main(String[] args) {
        launch();
    }

    public void openPage(ActionEvent event, String page) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page)));
            StartApplication.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            StartApplication.scene = new Scene(root);
            StartApplication.stage.setScene(StartApplication.scene);
            StartApplication.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}