package com.farmacontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga la interfaz principal (menu.fxml) desde resources
        Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Farmacontrol - Gestión Farmacéutica");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {

        launch(args); // Lanza la aplicación JavaFX


        }
    }
