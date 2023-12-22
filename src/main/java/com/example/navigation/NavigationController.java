package com.example.navigation;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class NavigationController {
    private final Configurations conf;
    public NavigationController() {
        this.conf = new Configurations();
    }

    public void updateConfVisibility(Pane pane, Pane clickedPane) {
        boolean isTransparent = isBackgroundTransparent(clickedPane);
        String styleVisible = "-fx-background-color: transparent; -fx-background-radius: 8";
        String styleNotVisible = "-fx-background-color: #ffaf4d; -fx-background-radius: 8";

        if (isTransparent) {
            clickedPane.setStyle(styleNotVisible);
            System.out.println("Show " + pane.getId());
            conf.showConf(pane);
        } else {
            clickedPane.setStyle(styleVisible);
            System.out.println("Hide " + pane.getId());
            conf.hideConf(pane);
        }
    }

    private boolean isBackgroundTransparent(Pane pane) {
        Background background = pane.getBackground();
        if (background != null) {
            BackgroundFill firstFill = background.getFills().get(0);
            return firstFill.getFill().equals(Color.TRANSPARENT);
        }
        return false;
    }
}

