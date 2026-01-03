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
    }
    //Маска для ввода времени
    private void inputTime(TextField field){
        field.setTextFormatter(new TextFormatter<>(change -> {
            //TODO

            return change;


        }));
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 5) {
                field.setText(oldVal);
            }
        });
    }
}