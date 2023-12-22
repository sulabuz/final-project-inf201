package com.example.Follow;

import com.example.controllers.DatabaseHelper;
import com.example.controllers.LoginController;
import javafx.application.Platform;
import javafx.scene.control.Button;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class FollowUs {

    private void notifyObservers() {
        try (Connection dbConnection = DriverManager.getConnection(
                DatabaseHelper.getUrl() + DatabaseHelper.getDBName(),
                DatabaseHelper.getUser(),
                DatabaseHelper.getPassword())) {

            if (DatabaseHelper.tableExistsFollower(dbConnection)) {
                List<String> followerEmails = DatabaseHelper.getFollowerEmails(dbConnection);

                // Assuming you have a method to send messages, replace the following loop with your message sending logic
                for (String email : followerEmails) {
                    sendMessage(email, "Thanks for subscribe", "The Efficient day team is grateful to you and will notify you of the latest news on the development and improvement of the application");
                }
            } else {
                System.out.println("Table 'followers' does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void sendMessage(String to, String subject, String body) {
        final String from = "romanProj04@gmail.com";
        String host = "smtp.gmail.com";
        String smtpPort = "465";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "frxyqykbsuxvtiip");
            }
        });
        session.setDebug(true);

        Platform.runLater(() ->  {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from, "Efficient Day"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body);
                Transport.send(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
    private final FollowObserverImpl followObserver = new FollowObserverImpl();

    public void addFollowers(Button followButton) throws SQLException {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.getUrl() + DatabaseHelper.getDBName(), DatabaseHelper.getUser(), DatabaseHelper.getPassword())) {

            if (!DatabaseHelper.tableExistsFollower(dbConnection)) {
                DatabaseHelper.createTableFollower(dbConnection);
                System.out.println("Table 'users' successfully created");
            }
            if (DatabaseHelper.isUsernameExistsFollower(dbConnection, LoginController.getUserName())) {
                followButton.setText("You have followed");
                followButton.setLayoutX(100);
                followButton.setDisable(true);
            } else {
                try (PreparedStatement pst = dbConnection.prepareStatement("INSERT INTO followers(login) VALUES(?)")) {
                    pst.setString(1, LoginController.getUserName());
                    pst.executeUpdate();
                    followObserver.updateFollowStatus(followButton);
                    System.out.println("New follower: [" + LoginController.getUserName() + "]");
                }
                notifyObservers();
            }
        }
    }
}
