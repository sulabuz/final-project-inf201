package com.example.Follow;
import javafx.scene.control.Button;

public class FollowObserverImpl implements FollowObserver{
    @Override
    public void  updateFollowStatus(Button followButton) {
        followButton.setText("Already Followed");
        followButton.setLayoutX(100);
        followButton.setDisable(true);
    }
}
