package com.farmacontrol.controller;

import com.farmacontrol.model.Usuario;
import com.farmacontrol.service.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void login(ActionEvent event) {

        String email = txtEmail.getText();
        String pass = txtPassword.getText();

        if (email == null || email.isBlank() ||
                pass == null || pass.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setContentText("Rellena todos los campos");
            alert.showAndWait();
            return;
        }

        Usuario usuario = usuarioService.login(email, pass);

        if (usuario != null) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) txtEmail.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Menú principal");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Credenciales incorrectas");
            alert.showAndWait();
        }
    }
}