package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.*;
import java.sql.*;

public class LoginController {
    private static String nameuser;
    public ImageView yourImageView;
    public ImageView yourImageView2;
    public TextField inputPasstextfeild;

    @FXML
    private CheckBox chekRemeber;
    @FXML
    private Text idnotification;
    @FXML
    private TextField inputLoginField;
    @FXML
    private PasswordField inputPasswordField;
    static StartApplication startApplication = new StartApplication();
    static Animations animations = new Animations();
    private static final String SETTINGS_FILE_PATH = "user_settings.dat";

    private boolean passfieldIsOpen = false;

    @FXML
    void initialize() {
        loadPreferences();
    }
    public static String getUserName() {
        return nameuser;
    }

    private void savePreferences() {
        if (chekRemeber.isSelected()) {
            UserSettings userSettings = new UserSettings();
            userSettings.setLogin(inputLoginField.getText());
            userSettings.setPassword(inputPasswordField.getText());
            userSettings.setRemember(true);

            saveObjectToFile(userSettings, SETTINGS_FILE_PATH);
        } else {
            File file = new File(SETTINGS_FILE_PATH);
            if (file.exists()) {
                boolean delete = file.delete();
            }
        }
    }

    private void loadPreferences() {
        File file = new File(SETTINGS_FILE_PATH);

        if (file.exists()) {
            UserSettings userSettings = (UserSettings) loadObjectFromFile(SETTINGS_FILE_PATH);

            if (userSettings != null) {
                inputLoginField.setText(userSettings.getLogin());
                inputPasswordField.setText(userSettings.getPassword());
                chekRemeber.setSelected(userSettings.isRemember());
            }
        }
    }
    public static void saveObjectToFile(Object object, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadObjectFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void buttonOpenMain(ActionEvent event) {
        try {
            if (isValidUser(inputLoginField.getText(), inputPasswordField.getText())) {
                nameuser = inputLoginField.getText();
                startApplication.openPage(event, "main_page.fxml");
                savePreferences();
                System.out.println("Login: " + inputLoginField.getText() + " успешно зашел!");
            } else {
                animations.AnimationShake(inputLoginField);
                animations.PlayAnimShake();

                animations.AnimationShake(inputPasswordField);
                animations.PlayAnimShake();

                animations.AnimationShake(inputPasstextfeild);
                animations.PlayAnimShake();

                idnotification.setFill(Color.RED);
                errorMessage("Логин и пароль не верны. Вход запрещен.");
            }
        } catch (Exception e) {
            errorMessage(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void imageBtnShowPass(MouseEvent event) {
        passfieldIsOpen = true;

        inputPasstextfeild.setText(inputPasswordField.getText());

        inputPasstextfeild.setDisable(false);
        inputPasstextfeild.setVisible(true);

        inputPasswordField.setDisable(true);
        inputPasswordField.setVisible(false);

        yourImageView.setVisible(false);
        yourImageView.setDisable(true);
        yourImageView2.setDisable(false);
        yourImageView2.setVisible(true);
    }

    @FXML
    void imageBtnClosePass(MouseEvent event) {
        passfieldIsOpen = false;

        inputPasswordField.setText(inputPasstextfeild.getText());

        inputPasstextfeild.setDisable(true);
        inputPasstextfeild.setVisible(false);

        inputPasswordField.setDisable(false);
        inputPasswordField.setVisible(true);

        yourImageView.setVisible(true);
        yourImageView.setDisable(false);
        yourImageView2.setDisable(true);
        yourImageView2.setVisible(false);
    }

    @FXML
    void setPass(){

        if(!passfieldIsOpen)
            inputPasstextfeild.setText(inputPasswordField.getText());
        else
            inputPasswordField.setText(inputPasstextfeild.getText());

    }
    public static boolean isValidUser(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DatabaseHelper.getUrl() + DatabaseHelper.getDBName(), DatabaseHelper.getUser(), DatabaseHelper.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    void buttonOpenForget(ActionEvent event) {
        startApplication.openPage(event, "forget.fxml");
    }

    @FXML
    public void buttonCancelLogin(ActionEvent event) {
        startApplication.openPage(event, "start.fxml");
    }

    @FXML
    void buttonOpenRegister(ActionEvent event) {
        startApplication.openPage(event, "register.fxml");
    }

    void errorMessage(String err) {
        idnotification.setFill(Color.RED);
        idnotification.setText(err);
        animations.AnimationFade(idnotification);
        animations.PlayAnimFade();
    }
}
