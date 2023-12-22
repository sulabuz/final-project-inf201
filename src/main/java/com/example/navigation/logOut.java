package com.example.navigation;

import com.example.controllers.StartApplication;
import javafx.event.ActionEvent;

public class logOut {
    StartApplication application = new StartApplication();
    public void loginOut(ActionEvent event){
        application.openPage(event,"login.fxml");
    }
}
