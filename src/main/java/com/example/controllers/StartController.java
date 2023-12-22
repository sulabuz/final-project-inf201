package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartController {

    static StartApplication startApplication = new StartApplication();

    @FXML
    public void buttonOpenLogin(ActionEvent event) {
        startApplication.openPage(event, "login.fxml");
    }


}