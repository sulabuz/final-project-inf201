package com.example.navigation;

import javafx.scene.layout.Pane;

public class Configurations {
    public void hideConf(Pane pane){
        pane.setVisible(false);
        pane.setDisable(true);
    }
    public void showConf(Pane pane){
        pane.setVisible(true);
        pane.setDisable(false);
    }
}
