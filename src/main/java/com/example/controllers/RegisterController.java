package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.*;

public class RegisterController {
    @FXML
    private TextField inputLoginField;
    @FXML
    private Text idnotification;
    @FXML
    private TextField inputPasswordField;
    @FXML
    private TextField inputEmailField;
    @FXML
    private TextField inputPasswordFieldAgain;

    static StartApplication startApplication = new StartApplication();
    static Animations animations = new Animations();
    @FXML
    public void buttonCancelRegister(ActionEvent event) {
        startApplication.openPage(event, "login.fxml");
    }

    @FXML
    void buttonCreateAccount() {
        try {
            if (!(inputPasswordField.getText().equals(inputPasswordFieldAgain.getText()))) {
                errorMessage("Passwords do not match.");
            } else if (inputLoginField.getText().isEmpty() || inputPasswordField.getText().isEmpty() ||
                    inputPasswordFieldAgain.getText().isEmpty() || inputEmailField.getText().isEmpty()) {
                errorMessage("Login or Email or Password is empty");
            } else if (!inputEmailField.getText().contains("@gmail.com")) {
                errorMessage("Email is not correct! EX: ...@gmail.com");
            } else {
                register(inputLoginField.getText(), inputEmailField.getText(), inputPasswordField.getText());
            }
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }
    }

    private void register(String userName, String email, String userPassword) {

        try {
            if (!DatabaseHelper.databaseExists()) {
                DatabaseHelper.createDatabase();
                System.out.println("Database successfully created");
            }

            try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.getUrl() + DatabaseHelper.getDBName(), DatabaseHelper.getUser(), DatabaseHelper.getPassword())) {
                System.out.println("Successfully connected to the database");

                if (!DatabaseHelper.tableExists(dbConnection)) {
                    DatabaseHelper.createTable(dbConnection);
                    System.out.println("Table 'users' successfully created");
                }

                if (DatabaseHelper.isUsernameExists(dbConnection, userName)) {
                    errorMessage("User with this username already exists.");
                } else {
                    try (PreparedStatement pst = dbConnection.prepareStatement("INSERT INTO users(login, email, password) VALUES(?, ?, ?)")) {
                        pst.setString(1, userName);
                        pst.setString(2, email);
                        pst.setString(3, userPassword);
                        pst.executeUpdate();
                        System.out.println("New user: [" + inputLoginField.getText() + ", " +
                                inputEmailField.getText() + ", " +
                                inputPasswordField.getText() + "]");
                        idnotification.setFill(Color.GREEN);
                        idnotification.setText("Successfully created user.");
                    }
                }
            } catch (SQLException ex) {
                errorMessage("Error connecting to the database: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            errorMessage("Error connecting to the default database: " + ex.getMessage());
        }
    }



    @FXML
    void buttonOpenLoginIn(ActionEvent event) {
        startApplication.openPage(event, "login.fxml");
    }

    void errorMessage(String err) {
        idnotification.setFill(Color.RED);
        idnotification.setText(err);
        animations.AnimationFade(idnotification);
        animations.PlayAnimFade();
    }

}
