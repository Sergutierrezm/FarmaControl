package com.farmacontrol.controller;

import com.farmacontrol.model.Usuario;
import com.farmacontrol.service.UsuarioService;
import com.farmacontrol.util.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        // Validar campos vacíos
        if (email == null || email.isBlank() ||
                pass == null || pass.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Rellena todos los campos");
            alert.showAndWait();

            return;
        }

        // Intentar login
        Usuario usuario = usuarioService.login(email, pass);

        if (usuario != null) {

            // Guardar sesión
            Sesion.setUsuarioActual(usuario);

            try {

                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("/view/menu.fxml"));

                Parent root = loader.load();

                Stage stage =
                        (Stage) txtEmail.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("Menú principal");
                stage.show();

            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo cargar el menú");

                alert.showAndWait();

                e.printStackTrace();
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales incorrectas");

            alert.showAndWait();
        }
    }
}