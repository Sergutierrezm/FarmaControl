package com.farmacontrol.controller;

import com.farmacontrol.model.Rol;
import com.farmacontrol.model.Usuario;
import com.farmacontrol.service.UsuarioService;
import com.farmacontrol.dao.RolDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UsuarioController {

    // ================= TABLE =================
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, String> colRol;

    // ================= FORM =================
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<Rol> cbRol;

    private final UsuarioService usuarioService = new UsuarioService();
    private final RolDAO rolDAO = new RolDAO();

    @FXML
    public void initialize() {

        // columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colRol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getRol().getNombre()
                )
        );

        cargarUsuarios();
        cargarRoles();

        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, u) -> {
                    if (u != null) {
                        txtNombre.setText(u.getNombre());
                        txtEmail.setText(u.getEmail());
                        txtPassword.setText(u.getContrasena());
                        cbRol.setValue(u.getRol());
                    }
                }
        );
    }

    // ================= CARGAR =================

    private void cargarUsuarios() {
        ArrayList<Usuario> lista = usuarioService.obtenerUsuarios();
        tablaUsuarios.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarRoles() {
        ArrayList<Rol> roles = rolDAO.obtenerTodos();
        cbRol.setItems(FXCollections.observableArrayList(roles));
    }

    // ================= GUARDAR =================

    @FXML
    public void guardarUsuario() {

        try {

            Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            Usuario u = (seleccionado != null) ? seleccionado : new Usuario();

            u.setNombre(txtNombre.getText());
            u.setEmail(txtEmail.getText());
            u.setContrasena(txtPassword.getText());
            u.setRol(cbRol.getValue());

            if (seleccionado == null) {
                usuarioService.registrarUsuario(u);
            } else {
                usuarioService.actualizarUsuario(u);
            }

            limpiarCampos();
            cargarUsuarios();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Datos inválidos").show();
        }
    }

    // ================= ELIMINAR =================

    @FXML
    public void eliminarUsuario() {

        Usuario u = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (u == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un usuario").show();
            return;
        }

        usuarioService.eliminarUsuario(u.getIdUsuario());
        limpiarCampos();
        cargarUsuarios();
    }

    // ================= LIMPIAR =================

    @FXML
    public void limpiarCampos() {
        txtNombre.clear();
        txtEmail.clear();
        txtPassword.clear();
        cbRol.getSelectionModel().clearSelection();
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    // ================= VOLVER =================

    @FXML
    public void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tablaUsuarios.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}