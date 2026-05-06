package com.farmacontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/login.fxml")
        );

        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Farmacontrol - Gestión Farmacéutica");
        primaryStage.setScene(scene);

        primaryStage.setResizable(false); // opcional pero recomendable
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
