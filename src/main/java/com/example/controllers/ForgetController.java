package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Properties;

public class ForgetController {

    @FXML
    private Text idnotification;

    @FXML
    private TextField inputLoginField;

    static StartApplication startApplication = new StartApplication();
    static Animations animations = new Animations();
    @FXML
    void buttonSendPassword() {
        String login = inputLoginField.getText();

        try {
            if (!DatabaseHelper.databaseExists()) {
                DatabaseHelper.createDatabase();
                System.out.println("Database successfully created");
            }
        } catch (SQLException e) {
            errorMessage(e.getMessage());
        }

        // Проверка наличия логина в базе данных
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.getUrl() + DatabaseHelper.getDBName(), DatabaseHelper.getUser(), DatabaseHelper.getPassword())) {

            if (!(DatabaseHelper.isUsernameExists(dbConnection, login))) {
                errorMessage("Login does not exist.");
            } if (inputLoginField.getText().isEmpty()) {
                errorMessage("Login is emty");
            } else {
                // Если логин существует, отправляем пароль на почту
                sendEmail(dbConnection, login);
                idnotification.setFill(Color.GREEN);
                idnotification.setText("Password sent to your email.");
            }
        } catch (SQLException ex) {
            errorMessage("Error connecting to the database: " + ex.getMessage());
        }
    }

    private void sendEmail(Connection dbConnection, String login) {
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

        String userPassword = DatabaseHelper.selectPasswordByLogin(dbConnection, login);
        String email = DatabaseHelper.selectEmailByLogin(dbConnection, login);

        if (userPassword != null && email != null) {
            Thread thread = new Thread(() -> {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from, "Efficient Day"));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    message.setSubject("Recovery password");
                    message.setText("Your password: " + userPassword);
                    Transport.send(message);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            thread.start();
        } else {
            System.out.println("Login does not exist.!!!!");
        }

    }

    @FXML
    void buttonCancelLogin(ActionEvent event) {
        startApplication.openPage(event, "login.fxml");
    }

    void errorMessage(String err) {
        idnotification.setFill(Color.RED);
        idnotification.setText(err);
        animations.AnimationFade(idnotification);
        animations.PlayAnimFade();
    }
}
