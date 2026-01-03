package com.timeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Scene scene = new Scene(root, 600, 400);
             /* это добавление иконки, но ее еще нужно сделать
            InputStream iconStream =
            getClass().getResourceAsStream("/images/someImage.png");
            Image image = new Image(iconStream);
            primaryStage.getIcons().add(image);*/

            primaryStage.setTitle("Таймер");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки загрузки FXML
        }
    }
}