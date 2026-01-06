package com.timeapp;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Controller {
    @FXML
    private Label comeToWork;
    @FXML
    private Label goToLunch;
    @FXML
    private Label comebackFromLunch;
    @FXML
    private Label quitMyJob;
    @FXML
    private TextField timeComeToWork;
    @FXML
    private TextField timeGoToLunch;
    @FXML
    private TextField timeComebackFromLunch;
    @FXML
    private TextField  timeQuitMyJob;
    @FXML
    private Button calculaterLunchEnd;

    @FXML
    private void initialize() {
        inputTime(timeComeToWork);
        inputTime(timeGoToLunch);
        inputTime(timeComebackFromLunch);
        inputTime(timeQuitMyJob);
    }

    //Маска для ввода времени
    private void inputTime(TextField field) {
        field.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isContentChange()) {  // Ctrl+C, Ctrl+V, выделение и т.д.
                return change;  // Разрешаем
            }
            if (change.isDeleted()) return change;
            return change;
        }));

        field.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) return;
            //проверка на числа
           if (!newValue.matches("[0-9:]*")) {
                field.setText(oldValue);
                return;
            }
            //проверка длины
            if (newValue.length() > 5) {
                field.setText(oldValue);
                return;
            }
            //не больше 23 часов
            if (oldValue.isEmpty() && Integer.parseInt(newValue.split(":")[0]) > 2 && !newValue.contains(":")){
                field.setText(oldValue);
                return;
            }
            //не больше 59 минут
            if (oldValue.length() == 3 && Integer.parseInt(newValue.substring(newValue.length()-1)) > 5){
                field.setText(oldValue);
                return;
            }
            // автодобавление ":"
            if (oldValue != null && oldValue.length() < newValue.length() &&
                    newValue.length() == 2 && newValue.matches("\\d{2}")) {
                javafx.application.Platform.runLater(() -> {
                    field.setText(newValue + ":");
                    field.positionCaret(3);
                });
            }

        });
    }
    @FXML
    private void calculaterLunchEnd(){
        LocalTime arrival = parseTime(timeComeToWork.getText());
        LocalTime lunchStart = parseTime(timeGoToLunch.getText());
        LocalTime lunchEnd = parseTime(timeComebackFromLunch.getText());
        LocalTime quit = parseTime(timeQuitMyJob.getText());
        if (arrival == null || lunchStart == null || quit == null) {
            showInfo("Заполните поля");
            return;
        }else {
            timeComebackFromLunch.setText(String.valueOf(quit.minusMinutes(
                            480 - Duration.between(arrival, lunchStart).toMinutes()).
                    format(DateTimeFormatter.ofPattern("HH:mm"))));
        }
    }
    private void showInfo(String message) {
        Tooltip tooltip = new Tooltip(message);

        // Показываем tooltip
        tooltip.show(
                timeComebackFromLunch.getScene().getWindow(),
                timeComebackFromLunch.localToScreen(timeComebackFromLunch.getBoundsInLocal()).getMinX(),
                timeComebackFromLunch.localToScreen(timeComebackFromLunch.getBoundsInLocal()).getMaxY() + 5
        );

        // Скрываем через 2 секунды с помощью Thread
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2000 миллисекунд = 2 секунды
                javafx.application.Platform.runLater(() -> {
                    tooltip.hide();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }

        try {
            // Добавляем секунды если нужно
            if (timeStr.length() == 4) { // "09:00" -> 5 символов
                timeStr = "0" + timeStr; // "9:00" -> "09:00"
            }

            if (timeStr.length() == 5 && timeStr.contains(":")) {
                return LocalTime.parse(timeStr);
            }

            return null;
        } catch (Exception e) {
            System.err.println("Ошибка парсинга времени: " + timeStr);
            return null;
        }
    }

}