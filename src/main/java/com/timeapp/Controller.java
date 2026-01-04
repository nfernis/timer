package com.timeapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

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
    private void initialize() {
        inputTime(timeComeToWork);
        inputTime(timeGoToLunch);
        inputTime(timeComebackFromLunch);
        inputTime(timeQuitMyJob);
    }

    //Маска для ввода времени
    private void inputTime(TextField field) {
        field.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isDeleted()) return change;
            return change;
        }));

        field.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) return;
            //проверка на числа
            if (!newValue.matches("^\\d{0,2}:?\\d{0,2}$")) {
                field.setText(oldValue);
                return;
            }
            //проверка длины
            if (newValue.length() > 5) {
                field.setText(oldValue);
                return;
            }
            //не больше 23 часов
            if (oldValue.length() == 0 && Integer.parseInt(newValue) > 2){
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
}