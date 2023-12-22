module com.example.gittest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires java.prefs;
    requires jbcrypt;
    requires javafx.media;
    requires java.desktop;
    requires org.json;

    opens com.example.controllers to javafx.fxml;
    exports com.example.controllers;
    exports com.example.CurrentTime;
    opens com.example.CurrentTime to javafx.fxml;
    exports com.example.navigation;
    opens com.example.navigation to javafx.fxml;
    exports com.example.task;
    opens com.example.task to javafx.fxml;
    exports com.example.timer;
    opens com.example.timer to javafx.fxml;
    exports com.example.main;
    opens com.example.main to javafx.fxml;

}