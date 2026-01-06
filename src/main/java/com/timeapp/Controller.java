package com.timeapp;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Controller {
    @FXML
    private VBox infoBox;
    @FXML private VBox mainContainer;

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
    private void handleClose() {
        Stage stage = (Stage) infoBox.getScene().getWindow();
        stage.close();
    }

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
            if (change.isContentChange() || change.isDeleted()) {  // Ctrl+C, Ctrl+V, выделение и т.д.
                return change;  // Разрешаем
            }
            return change;
        }));

        field.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) return;
            //проверка на числа
           if (!newValue.matches("[0-9:]*") || //только числа и :
                   newValue.length() > 5 || //длина меньше пяти
                   (oldValue.isEmpty() && Integer.parseInt(newValue.split(":")[0]) > 2
                           && !newValue.contains(":")) || // <23 часов
                   (oldValue.length() == 3 && //меньше 60 минут
                           Integer.parseInt(newValue.substring(newValue.length()-1)) > 5)
           ) {
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
            if (arrival == null){showError(timeComeToWork,"Заполните поля");}
            if (lunchStart == null){showError(timeGoToLunch, "Заполните поля");}
            if (quit == null){timeQuitMyJob.insertText(0,"16:00");}
            return;
        }else {
            if (quit == null){quit = parseTime("16:00");}
            timeComebackFromLunch.setText(String.valueOf(quit.minusMinutes(
                            480 - Duration.between(arrival, lunchStart).toMinutes()).
                    format(DateTimeFormatter.ofPattern("HH:mm"))));
        }
    }

   /* private void showInfo(String message, TextField field) {
        Tooltip tooltip = new Tooltip(message);

        // Показываем tooltip
        tooltip.show(
                field.getScene().getWindow(),
                field.localToScreen(field.getBoundsInLocal()).getMinX(),
                field.localToScreen(field.getBoundsInLocal()).getMaxY() + 5
        );

        // Скрываем через 2 секунды с помощью Thread
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2000 миллисекунд = 2 секунды
                javafx.application.Platform.runLater(tooltip::hide);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }*/
   private void showError(TextField field, String message) {
       Tooltip tooltip = new Tooltip(message);
       tooltip.setStyle(
               "-fx-background-color: #ef4444;" +
                       "-fx-text-fill: white;" +
                       "-fx-font-size: 13px;" +
                       "-fx-background-radius: 6px;"
       );

       showTooltipWithAnimation(field, tooltip);
       field.setStyle("-fx-border-color: #ef4444; -fx-border-width: 2px;");
   }
    /**
     * Показ тултипа с анимацией
     */
    private void showTooltipWithAnimation(TextField field, Tooltip tooltip) {
        if (field.getScene() == null || field.getScene().getWindow() == null) {
            return;
        }

        // Рассчитываем позицию для тултипа
        double x = field.localToScreen(field.getBoundsInLocal()).getMinX();
        double y = field.localToScreen(field.getBoundsInLocal()).getMaxY() + 5;

        // Показываем тултип сразу
        tooltip.show(field.getScene().getWindow(), x, y);

        // Используем java.time.Duration для задержки скрытия
        java.time.Duration delayDuration = java.time.Duration.ofSeconds(2);

        new Thread(() -> {
            try {
                long delayMillis = delayDuration.toMillis();
                Thread.sleep(delayMillis);

                javafx.application.Platform.runLater(() -> {
                    // Просто скрываем без анимации (как в оригинале)
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