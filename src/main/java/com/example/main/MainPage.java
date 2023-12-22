package com.example.main;

import com.example.CurrentTime.CurrentTime;
import com.example.Follow.FollowUs;
import com.example.controllers.DatabaseHelper;
import com.example.controllers.LoginController;
import com.example.controllers.UserSettings;
import com.example.navigation.NavigationController;
import com.example.navigation.logOut;
import com.example.task.TaskFacade;
import com.example.timer.Settings;
import com.example.timer.TimerFacade;
import com.example.weather.Weather;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.awt.Desktop;
import java.net.URI;

public class MainPage {
    public Pane aboutWin;
    public Pane followUsWin;
    public Pane followUsPane;
    public ImageView goBackFollow;
    private UserSettings userSettings = new UserSettings();
    private FollowUs followUs = new FollowUs();
    @FXML
    private Pane Notice;
    @FXML
    private Button subscribe;
    public Text userNametext;
    public Text userEmailtext;
    public Text idtextHale;
    public Circle circle2;
    public AnchorPane BreatherPane;
    public Pane premiumPane;
    public Text Premium;
    public Text fortunaText;
    public ImageView HideModeFortuna;
    public Pane FartunePane;
    public CheckBox auto_Timer;
    public Button Tags;
    public Text Premium1;
    public Pane Fartune;
    public Pane Breather;
    public ImageView hideBreath;
    public ImageView goBackAbout;
    public Pane About;

    // Weather
    @FXML
    private Label temp;
    @FXML
    private Label state;
    @FXML
    private ImageView icon;
    private Weather weather;

    //============================================================//
    // Premium
    @FXML
    ImageView goBack;
    @FXML
    private Pane premiumWin;
    @FXML
    private Pane premiumNotes;

    //=============================================================//
    // Timer
    @FXML
    private ImageView hideTimer;

    @FXML
    private Button Pause;

    @FXML
    private Button Start;

    @FXML
    private TextField setLBreak;

    @FXML
    private ImageView settings;

    @FXML
    private TextField setPomodoro;

    @FXML
    private TextField setSBreak;

    @FXML
    private Text minutes;

    @FXML
    private ImageView restart;

    @FXML
    private Text choosenMode;

    @FXML
    private Text seconds;

    @FXML
    private Line setLine;
    @FXML
    private Text Pomodoro;
    @FXML
    private Pane settingPane;

    @FXML
    private TextField defaultTimer = new TextField("01");

    @FXML
    private Text SBreak;

    @FXML
    private Text LBreak;

    @FXML
    private Line lineSetting;

    protected TimerFacade timerFacade;


    //=============================================================//
    //Navigation
    @FXML
    private Pane Task;
    @FXML
    private Pane Calender;
    @FXML
    private Pane Sounds;
    @FXML
    private Pane Timer;
    @FXML
    private Pane logOutPane;
    @FXML
    private Pane taskPane;
    @FXML
    private Pane weatherPane;
    private final NavigationController navigationController;

    public MainPage() {
        this.navigationController = new NavigationController();
    }

    //============================================================================//
    // getter for attributes
    public Button getStartButton() {
        return Start;
    }

    public Button getPauseButton() {
        return Pause;
    }

    public Text getMinutes() {
        return minutes;
    }

    public Text getSeconds() {
        return seconds;
    }

    public TextField getSetPomodoro() {
        return setPomodoro;
    }

    public TextField getSetSBreak() {
        return setSBreak;
    }

    public TextField getSetLBreak() {
        return setLBreak;
    }

    public TextField getDefaultTimer() {
        return defaultTimer;
    }

    public Text getChoosenMode() {
        return choosenMode;
    }

    @FXML
    private void switchPaneVisibility(MouseEvent event) {
        if (event.getSource() instanceof Pane clickedPane) {
            switch (clickedPane.getId()) {
                case "Task" -> navigationController.updateConfVisibility(taskPane, clickedPane);
                case "Timer" -> navigationController.updateConfVisibility(TimerPane, clickedPane);
                case "Properties" -> navigationController.updateConfVisibility(logOutPane, clickedPane);
                case "premiumNotes" -> navigationController.updateConfVisibility(premiumWin,clickedPane);
                case "Calender" -> navigationController.updateConfVisibility(premiumWin,clickedPane);
                case "Sounds" -> navigationController.updateConfVisibility(premiumWin,clickedPane);
                case "Fartune" -> navigationController.updateConfVisibility(FartunePane,clickedPane);
                case "About" -> navigationController.updateConfVisibility(aboutWin, clickedPane);
                case "Dashboard" -> navigationController.updateConfVisibility(weatherPane, clickedPane);
                case "Notice" -> navigationController.updateConfVisibility(followUsWin, clickedPane);
                case "Breather" -> {
                    navigationController.updateConfVisibility(BreatherPane,clickedPane);
                    BreatherOpen();
                }
            }
        }
    }



    private final HideWin hideWin = new HideWin();

    @FXML
    public EventHandler<MouseEvent> clickHide() {
        return event -> {
            Node source = (Node) event.getSource();
            Pane parentPane = (Pane) source.getParent();
            switch (parentPane.getId()) {
                case "taskPane" -> hideWin.hide(parentPane, Task);
                case "TimerPane" -> hideWin.hide(parentPane, Timer);
                case "BreatherPane" -> hideWin.hide(parentPane, Breather);
                case "FartunePane" -> hideWin.hide(parentPane, Fartune);
                case "aboutPane" -> hideWin.hide(aboutWin, About);
                case "followUsPane" -> hideWin.hide(followUsWin, Notice);
                case "premiumPane" -> {
                    hideWin.hide(premiumWin, premiumNotes);
                    hideWin.hide(premiumWin, Calender);
                    hideWin.hide(premiumWin, Sounds);
                }
            }
        };
    }


    //========================================================================//
    //login out

    @FXML
    private void logOut(ActionEvent event) {
        logOut logOut = new logOut();
        logOut.loginOut(event);
    }

    //=============================================================//
    //Tasks

    @FXML
    private ImageView hideTask;
    @FXML
    private VBox listTasks;

    @FXML
    private void crateTasks(MouseEvent event) {
        TaskFacade facade = new TaskFacade();
        facade.createAndShowPane(listTasks);
    }

    //=============================================================//
    //Timer

    @FXML
    private Pane TimerPane;
    @FXML
    private Pane MainPane;
    private double xOffset = 0;
    private double yOffset = 0;

    // движения Pane
    @FXML
    private void handleMousePressed(MouseEvent event) {
        if (event.getSource() instanceof Pane pane) {
            xOffset = event.getSceneX() - pane.getLayoutX();
            yOffset = event.getSceneY() - pane.getLayoutY();
            pane.toFront();
        }
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        if (event.getSource() instanceof Pane pane) {
            double x = event.getSceneX() - xOffset;
            double y = event.getSceneY() - yOffset;
            if (x >= 0 && x + pane.getWidth() <= MainPane.getWidth() &&
                    y >= 0 && y + pane.getHeight() <= MainPane.getHeight()) {
                pane.setLayoutX(x);
                pane.setLayoutY(y);
            }
        }
    }


    //================================================================================//

    public EventHandler<MouseEvent> clickedSetting() {
        return event -> {
            Settings settings = new Settings();
            Node source = (Node) event.getSource();
            Pane parentPane = (Pane) source.getParent();
            if (parentPane.getPrefHeight() == 200) {
                settings.SettingShow(parentPane);
                settingPane.setVisible(true);
                lineSetting.setVisible(true);
            } else {
                settings.SettingHide(parentPane);
                settingPane.setVisible(false);
                lineSetting.setVisible(false);
            }
        };
    }

    protected void updateMinutesText(String value) {
        minutes.setText(value);
    }

    protected void timerFormat() {
        Map<Integer, String> numberMap = IntStream.rangeClosed(0, 60)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> String.format("%02d", i)));
    }

    protected TextFormatter<Integer> createTextFormatter() {
        return new TextFormatter<>(new IntegerStringConverter(), null,
                c -> {
                    String newText = c.getControlNewText();
                    if (newText.matches("\\d*") && (newText.isEmpty() || (newText.length() <= 2 && Integer.parseInt(newText) >= 0 && Integer.parseInt(newText) < 60))) {
                        return c;
                    } else {
                        return null;
                    }
                });
    }

    public void setTimer(TextField textField) {
        if (choosenMode != null && textField.getId() != null && textField.getText() != null && !textField.getText().isEmpty()) {
            String textFieldId = textField.getId();
            String inputValue = textField.getText();

            if (choosenMode == Pomodoro && textFieldId.equals("setPomodoro") ||
                    choosenMode == SBreak && textFieldId.equals("setSBreak") ||
                    choosenMode == LBreak && textFieldId.equals("setLBreak")) {
                String paddedValue = String.format("%02d", Integer.parseInt(inputValue));
                updateMinutesText(paddedValue);
            }
        }
    }

    private EventHandler<MouseEvent> createClickHandler(Line object, int toX, TextField textField, Text text) {
        return event -> {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), object);
            transition.setToX(toX);
            transition.setCycleCount(1);
            transition.play();

            choosenMode = text;

            TextField[] textFields = new TextField[]{setPomodoro, setSBreak, setLBreak};
            for (int i = 0; i < 3; i++) {
                if (textFields[i] == textField)
                    continue;
                if (textFields[i].getText().isEmpty())
                    textFields[i].setText("0");
            }

            setTimer(textField);
            textField.requestFocus();

            if (!timerFacade.getTimerThread().isInterrupted()) {
                timerFacade.restartTimer();
            }
        };
    }

    public void Start() {
        timerFacade.startTimer();
    }

    public void restartTo() {
        timerFacade.restartTimer();
    }

    public void Pause() {
        timerFacade.pauseTimer();
    }

    private CurrentTime currentTime;
    @FXML
    private Text currTime;

    //===================================================================//
    // current time
    private void updateTime(ActionEvent event) {
        currTime.setText(currentTime.getCurrentTime());
    }

    //===============================//
    // Opening link

    public void labelsulainsta(MouseEvent mouseEvent) {
        openLinkInBrowser("https://www.instagram.com/buzaubaevv/");
    }

    public void labelromainsta(MouseEvent mouseEvent) {
        openLinkInBrowser("https://www.instagram.com/romantursynbai/");
    }

    public void labelsulatele(MouseEvent mouseEvent) {
        openLinkInBrowser("https://t.me/sult1k_sw");
    }

    public void labelromatele(MouseEvent mouseEvent) {
        openLinkInBrowser("https://t.me/romanrtb");
    }
    public void openLinkSurvey(MouseEvent mouseEvent) {
        openLinkInBrowser("https://airtable.com/appnp1EK2JW79Uv1B/pagvU8GwKZH1fMqSd/form");
    }
    private void openLinkInBrowser(String url) {

        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Desktop not supported. Unable to open the link.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===============================//
    // Follower
    @FXML
    public void addFollowers(MouseEvent event) {
        String userName = userSettings.getLogin();

        try {
            followUs.addFollowers(subscribe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //===============================//
    // Backround Images
    @FXML
    private ImageView backgroundImageView;
    private Timeline backgroundAnimation;
    private int currentImageIndex = 0;
    private final Image[] backgroundImages = {
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon2.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon3.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon4.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon5.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon6.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon7.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/images/fon/fon8.jpg")).toExternalForm()),
    };

    private void setupBackgroundAnimation() {
        Duration duration = Duration.seconds(5);
        backgroundAnimation = new Timeline(new KeyFrame(duration, event -> changeBackgroundImage()));
        backgroundAnimation.setCycleCount(Timeline.INDEFINITE);
    }

    private void startBackgroundAnimation() {
        backgroundAnimation.play();
    }

    private void changeBackgroundImage() {
        int nextImageIndex = (currentImageIndex + 1) % backgroundImages.length;
        Image nextImage = backgroundImages[nextImageIndex];

        ImageView nextImageView = new ImageView(nextImage);
        nextImageView.setFitWidth(backgroundImageView.getFitWidth());
        nextImageView.setFitHeight(backgroundImageView.getFitHeight());
        nextImageView.setOpacity(0.0);

        MainPane.getChildren().add(0, nextImageView);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), backgroundImageView);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), nextImageView);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainPane.getChildren().remove(backgroundImageView);
                backgroundImageView = nextImageView;
                currentImageIndex = nextImageIndex;
            }
        });

        fadeOut.play();
        fadeIn.play();
    }

    //===============================//
    // Breather
    @FXML
    void BreatherOpen() {

        BreatherPane.setVisible(true);
        BreatherPane.setDisable(false);

        idtextHale.setText("inhale");

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(circle2);
        scaleTransition.setDuration(Duration.seconds(2.5));
        scaleTransition.setByX(0.4);
        scaleTransition.setByY(0.4);

        scaleTransition.setOnFinished(e -> {
            idtextHale.setText("exhale");

            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.6));

            ScaleTransition reverseTransition = new ScaleTransition();
            reverseTransition.setNode(circle2);
            reverseTransition.setDuration(Duration.seconds(2.5));
            reverseTransition.setByX(-0.4);
            reverseTransition.setByY(-0.4);

            reverseTransition.setOnFinished(e2 -> {
                idtextHale.setText("inhale");
                scaleTransition.playFromStart();
            });

            pauseTransition.setOnFinished(e3 -> {
                reverseTransition.play();
            });

            pauseTransition.play();
        });

        scaleTransition.play();
    }

    //===============================//
    // Fortuna
    private final String[] arrForuna = {
            "Creativity gives the possibility of some sort of achievement to everyone.",
            "Perseverance unlocks the doors of opportunity for those who dare to dream.",
            "Every setback is a setup for a comeback – resilience is the key to success.",
            "In the face of adversity, find the strength within to turn obstacles into stepping stones.",
            "Your potential is limitless; believe in yourself and the world will believe in you.",
            "Setbacks are temporary, but the lessons learned endure – let failure be your greatest teacher.",
            "Embrace change as a chance to evolve and transform into the person you aspire to be.",
            "Success is not a destination, but a journey fueled by passion and determination."};
    Random random = new Random();

    //=======================================================//
    // Weather
    public void updateWeather() {
        weather.getWeather();
    }

    @FXML
    public void initialize() {
        //=======================================================//
        // Weather
        Platform.runLater(() -> {
            weather = new Weather(temp,state,icon);
            updateWeather();
        });

        //========================================================//
        // background main
        setupBackgroundAnimation();
        startBackgroundAnimation();
        //=======================================================//
        // set text fortuna
        fortunaText.setText(arrForuna[random.nextInt(arrForuna.length)]);

        //set text username email
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.getUrl() + DatabaseHelper.getDBName(), DatabaseHelper.getUser(), DatabaseHelper.getPassword())) {
            String email = DatabaseHelper.selectEmailByLogin(dbConnection, LoginController.getUserName());

            userNametext.setText(LoginController.getUserName());
            userEmailtext.setText(email);
            System.out.println(LoginController.getUserName() + " email: " + email);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //=======================================================//
        // current time
        currentTime = new CurrentTime();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTime));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        //=======================================================//
        // Hide
        goBack.setOnMouseClicked(clickHide());
        hideTask.setOnMouseClicked(clickHide());
        goBackAbout.setOnMouseClicked(clickHide());
        hideBreath.setOnMouseClicked(clickHide());
        HideModeFortuna.setOnMouseClicked(clickHide());
        goBackFollow.setOnMouseClicked(clickHide());

        //=======================================================//
        //Timer
        timerFormat();
        setPomodoro.setTextFormatter(createTextFormatter());
        setSBreak.setTextFormatter(createTextFormatter());
        setLBreak.setTextFormatter(createTextFormatter());

        choosenMode = Pomodoro;

        setPomodoro.setText("20");
        setSBreak.setText("5");
        setLBreak.setText("15");

        settingPane.setVisible(false);
        lineSetting.setVisible(false);
        settings.setOnMouseClicked(clickedSetting());

        restart.setOnMouseClicked(event -> {
            restartTo();
        });

        setPomodoro.setOnKeyTyped(event -> {
            setTimer(setPomodoro);
        });

        setSBreak.setOnKeyTyped(event -> {
            setTimer(setSBreak);
        });

        setLBreak.setOnKeyTyped(event -> {
            setTimer(setLBreak);
        });

        hideTimer.setOnMouseClicked(clickHide());

        Pomodoro.setOnMouseClicked(createClickHandler(setLine, 0, setPomodoro, Pomodoro));
        SBreak.setOnMouseClicked(createClickHandler(setLine, 78, setSBreak, SBreak));
        LBreak.setOnMouseClicked(createClickHandler(setLine, 162, setLBreak, LBreak));

        timerFacade = new TimerFacade(this);
    }

}