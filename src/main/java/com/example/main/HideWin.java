package com.example.main;

import javafx.scene.layout.Pane;

public class HideWin {
    public void hide(Pane pane, Pane confPane) {
        String styleVisible = "-fx-background-color: transparent; -fx-background-radius: 8";
        confPane.setStyle(styleVisible);
        pane.setVisible(false);
        pane.setDisable(false);
    }


}
