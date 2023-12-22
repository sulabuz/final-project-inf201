package com.example.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {
    private TranslateTransition tt;
    FadeTransition transition;

    public void AnimationShake(Node node) {
        tt = new TranslateTransition(Duration.millis(90), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
        tt.setOnFinished(event -> resetNodePosition(node));
    }
    private void resetNodePosition(Node node) {
        node.setTranslateX(0);
    }
    public void PlayAnimShake() {
        tt.playFromStart();
    }

    public void AnimationFade(Node node) {
        transition = new FadeTransition(Duration.seconds(1), node);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.setAutoReverse(true);
        transition.setCycleCount(FadeTransition.INDEFINITE);
    }

    public void PlayAnimFade() {
        transition.playFromStart();
    }


}
