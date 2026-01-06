package com.timeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Scene scene = new Scene(root, 450, 500);

            // Сделаем сцену прозрачной
            scene.setFill(Color.TRANSPARENT);

            // Настроим прозрачное окно
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Time Calculator - Dark Mode");
            primaryStage.setScene(scene);

            // Добавим возможность перетаскивания окна
            final double[] xOffset = new double[1];
            final double[] yOffset = new double[1];
            root.setOnMousePressed(event -> {
                xOffset[0] = primaryStage.getX() - event.getScreenX();
                yOffset[0] = primaryStage.getY() - event.getScreenY();
            });
            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() + xOffset[0]);
                primaryStage.setY(event.getScreenY() + yOffset[0]);
            });

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки загрузки FXML
        }
    }
}