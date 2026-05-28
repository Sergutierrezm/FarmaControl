package com.farmacontrol.controller;

import com.farmacontrol.model.Proveedor;
import com.farmacontrol.service.ProveedorService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProveedorController {

    // =========================
    // TABLA
    // =========================

    @FXML private TableView<Proveedor> tablaProveedores;

    @FXML private TableColumn<Proveedor, Integer> colId;
    @FXML private TableColumn<Proveedor, String>  colNombre;
    @FXML private TableColumn<Proveedor, String>  colTelefono;
    @FXML private TableColumn<Proveedor, String>  colDireccion;

    // =========================
    // CAMPOS
    // =========================

    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    // =========================
    // SERVICE
    // =========================

    private final ProveedorService service = new ProveedorService();

    // =========================
    // INIT
    // =========================

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cargarProveedores();

        tablaProveedores.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, p) -> {
                    if (p != null) {
                        txtNombre.setText(p.getNombre());
                        txtTelefono.setText(p.getTelefono());
                        txtDireccion.setText(p.getDireccion());
                    }
                });
    }

    // =========================
    // CARGAR
    // =========================

    private void cargarProveedores() {

        ObservableList<Proveedor> lista =
                FXCollections.observableArrayList(service.obtenerProveedores());

        tablaProveedores.setItems(lista);
    }

    // =========================
    // GUARDAR (crear o editar)
    // =========================

    @FXML
    public void guardarProveedor() {

        try {

            Proveedor seleccionado =
                    tablaProveedores.getSelectionModel().getSelectedItem();

            Proveedor p = (seleccionado != null) ? seleccionado : new Proveedor();

            p.setNombre(txtNombre.getText());
            p.setTelefono(txtTelefono.getText());
            p.setDireccion(txtDireccion.getText());

            if (seleccionado == null) {
                boolean ok = service.registrarProveedor(p);
                if (!ok) {
                    new Alert(Alert.AlertType.WARNING, "El nombre del proveedor es obligatorio").showAndWait();
                    return;
                }
            } else {
                service.actualizarProveedor(p);
            }

            limpiarCampos();
            cargarProveedores();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            e.printStackTrace();
        }
    }

    // =========================
    // ELIMINAR
    // =========================

    @FXML
    public void eliminarProveedor() {

        Proveedor seleccionado =
                tablaProveedores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un proveedor").showAndWait();
            return;
        }

        service.eliminarProveedor(seleccionado.getIdProveedor());

        limpiarCampos();
        cargarProveedores();
    }

    // =========================
    // LIMPIAR
    // =========================

    private void limpiarCampos() {

        txtNombre.clear();
        txtTelefono.clear();
        txtDireccion.clear();

        tablaProveedores.getSelectionModel().clearSelection();
    }

    // =========================
    // VOLVER
    // =========================

    @FXML
    public void volverMenu() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/view/menu.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) tablaProveedores.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Menú principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
