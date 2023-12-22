package com.example.timer;

import com.example.main.MainPage;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TimerFacade {

    private final MainPage mainPage;
    private final Map<Integer, String> numberMap;
    private int currentSeconds;
    private Thread timerThread;
    private final MediaPlayer mediaPlayer;


    public Thread getTimerThread() {
        return timerThread;
    }

    public TimerFacade(MainPage mainPage) {
        Media sound = new Media(getClass().getResource("/sound/countDown.mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        this.mainPage = mainPage;
        this.numberMap = createNumberMap();
        this.currentSeconds = 0;
        this.timerThread = null;
    }

    private Map<Integer, String> createNumberMap() {
        return IntStream.rangeClosed(0, 60)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> String.format("%02d", i)));
    }

    public void startTimer() {
        if (timerThread == null || !timerThread.isAlive()) {
            currentSeconds = msToSeconds(Integer.parseInt(mainPage.getMinutes().getText()), Integer.parseInt(mainPage.getSeconds().getText()));
            mainPage.getStartButton().setVisible(false);
            mainPage.getPauseButton().setVisible(true);
            countdown();
        }
    }

    public void pauseTimer() {
        mainPage.getStartButton().setVisible(true);
        mainPage.getPauseButton().setVisible(false);
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    public void restartTimer() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
        setInitialTime(getSelectedModeTime());
        pauseTimer();
    }

    private TextField getSelectedModeTime() {
        return switch (mainPage.getChoosenMode().getId()) {
            case "Pomodoro" -> mainPage.getSetPomodoro();
            case "SBreak" -> mainPage.getSetSBreak();
            case "LBreak" -> mainPage.getSetLBreak();
            default -> mainPage.getDefaultTimer(); // Default time, change it according to your requirements
        };
    }

    private void setInitialTime(TextField time) {
        Platform.runLater(() -> {
            mainPage.setTimer(time);
            mainPage.getSeconds().setText("00");
            currentSeconds = msToSeconds(Integer.parseInt(mainPage.getMinutes().getText()), Integer.parseInt(mainPage.getSeconds().getText()));
        });
    }

    protected void countdown() {
        timerThread = new Thread(() -> {
            try {
                while (true) {
                    showCountdown();
                    Thread.sleep(1000);
                    if (currentSeconds == 0) {
                        timerThread.interrupt();
                        break;
                    }
                    currentSeconds--;
                }
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted");
            }
        });
        timerThread.start();
    }

    private void showCountdown() {
        LinkedList<Integer> currentTime = secondsToMs(currentSeconds);
        Platform.runLater(() -> {
            mainPage.getMinutes().setText(numberMap.get(currentTime.get(0)));
            mainPage.getSeconds().setText(numberMap.get(currentTime.get(1)));
            if (currentSeconds == 3) {
                playSound();
            }
        });
    }

    private void playSound() {
        // Проверить, что звук не воспроизводится уже (это может быть важно для воспроизведения звука только один раз)
        if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.play();
        }
    }
    private LinkedList<Integer> secondsToMs(int currentSeconds) {
        int minutes = currentSeconds / 60;
        currentSeconds = currentSeconds % 60;
        int seconds = currentSeconds;
        LinkedList<Integer> ans = new LinkedList<>();
        ans.add(minutes);
        ans.add(seconds);
        return ans;
    }

    private int msToSeconds(int m, int s) {
        int mToSeconds = m * 60;
        return mToSeconds + s;
    }
}
