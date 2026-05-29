package com.farmacontrol.controller;

import com.farmacontrol.model.Proveedor;
import com.farmacontrol.service.ProveedorService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

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

    @FXML private TextField txtBuscar;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    // =========================
    // SERVICE Y LISTAS
    // =========================

    private final ProveedorService service = new ProveedorService();

    private ObservableList<Proveedor> listaCompleta;
    private FilteredList<Proveedor>   listaFiltrada;

    // =========================
    // INIT
    // =========================

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        listaCompleta = FXCollections.observableArrayList(service.obtenerProveedores());
        listaFiltrada = new FilteredList<>(listaCompleta, p -> true);
        tablaProveedores.setItems(listaFiltrada);

        // filtro en tiempo real
        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> {
            listaFiltrada.setPredicate(p -> {
                if (newVal == null || newVal.isBlank()) return true;
                String filtro = newVal.toLowerCase();
                return p.getNombre().toLowerCase().contains(filtro)
                    || (p.getTelefono() != null && p.getTelefono().toLowerCase().contains(filtro))
                    || (p.getDireccion() != null && p.getDireccion().toLowerCase().contains(filtro));
            });
        });

        // rellenar formulario al seleccionar fila
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
        listaCompleta.setAll(service.obtenerProveedores());
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

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar el proveedor \"" + seleccionado.getNombre() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmar eliminación");

        Optional<ButtonType> resultado = confirm.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.YES) return;

        service.eliminarProveedor(seleccionado.getIdProveedor());

        limpiarCampos();
        cargarProveedores();
    }

    // =========================
    // LIMPIAR
    // =========================

    private void limpiarCampos() {

        txtBuscar.clear();
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
