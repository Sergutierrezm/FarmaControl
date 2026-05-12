package com.farmacontrol.controller;

import com.farmacontrol.model.Usuario;
import com.farmacontrol.util.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private Button btnUsuarios;

    @FXML
    public void initialize() {

        Usuario usuario = Sesion.getUsuarioActual();

        if (usuario != null) {

            lblBienvenida.setText(
                    "Bienvenido " + usuario.getNombre()
            );

            String rol = usuario.getRol().getNombre();

            // CONTROL POR ROL
            if (!rol.equalsIgnoreCase("ADMIN")) {

                btnUsuarios.setManaged(false);
                btnUsuarios.setVisible(false);
            }
        }
    }

    // =========================
    // CERRAR SESIÓN
    // =========================
    @FXML
    public void cerrarSesion(ActionEvent event) {

        try {

            // limpiar sesión
            Sesion.cerrarSesion();

            // volver al login
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/view/login.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) lblBienvenida.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}