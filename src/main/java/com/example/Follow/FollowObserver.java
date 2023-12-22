package com.example.Follow;

import javafx.scene.control.Button;

import java.sql.SQLException;

public interface FollowObserver {
    void updateFollowStatus( Button followButton);
}
