package com.timeapp;  // или com.timer если переименуете

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Таймер");
        primaryStage.setWidth(500);
        primaryStage.setHeight(400);

        /* это добавление иконки, но ее еще нужно сделать
        InputStream iconStream =
                getClass().getResourceAsStream("/images/someImage.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);*/
        Label comeToWork =  new Label("Пришла на работу: ");


        FlowPane root = new FlowPane(comeToWork);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}