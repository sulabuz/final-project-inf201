package com.example.timer;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Settings {
    static Duration duration = Duration.seconds(0.2);
    public void SettingHide(Pane pane){
        double originalHeight = pane.getPrefHeight();
        double newHeight = 200;
        Transition heightTransition = new Transition() {
            {
                setCycleDuration(duration);
            }

            @Override
            protected void interpolate(double frac) {
                double height = originalHeight + (newHeight - originalHeight) * frac;
                pane.setPrefHeight(height);
            }
        };

        // Включаем анимацию
        heightTransition.play();
    }
    public void SettingShow(Pane pane){
        double originalHeight = pane.getPrefHeight();
        double newHeight = 335;
        Transition heightTransition = new Transition() {
            {
                setCycleDuration(duration);
            }

            @Override
            protected void interpolate(double frac) {
                double height = originalHeight + (newHeight - originalHeight) * frac;
                pane.setPrefHeight(height);
            }
        };
        heightTransition.play();
    }
}
