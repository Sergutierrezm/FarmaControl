package com.farmacontrol.controller;

import com.farmacontrol.model.Cliente;
import com.farmacontrol.service.ClienteService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClienteController {

    // =========================
    // TABLA
    // =========================

    @FXML private TableView<Cliente> tablaClientes;

    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colDireccion;

    // =========================
    // CAMPOS
    // =========================

    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDireccion;

    // =========================
    // SERVICE
    // =========================

    private final ClienteService clienteService = new ClienteService();

    // =========================
    // INIT
    // =========================

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cargarClientes();

        tablaClientes.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, cliente) -> {

                    if (cliente != null) {
                        txtNombre.setText(cliente.getNombre());
                        txtTelefono.setText(cliente.getTelefono());
                        txtEmail.setText(cliente.getEmail());
                        txtDireccion.setText(cliente.getDireccion());
                    }
                });
    }

    // =========================
    // CARGAR CLIENTES
    // =========================

    private void cargarClientes() {

        ObservableList<Cliente> lista =
                FXCollections.observableArrayList(
                        clienteService.obtenerClientes()
                );

        tablaClientes.setItems(lista);
    }

    // =========================
    // GUARDAR
    // =========================

    @FXML
    public void guardarCliente() {

        try {

            Cliente seleccionado =
                    tablaClientes.getSelectionModel().getSelectedItem();

            Cliente c = (seleccionado != null) ? seleccionado : new Cliente();

            c.setNombre(txtNombre.getText());
            c.setTelefono(txtTelefono.getText());
            c.setEmail(txtEmail.getText());
            c.setDireccion(txtDireccion.getText());

            if (seleccionado == null) {
                clienteService.registrarCliente(c);
            } else {
                clienteService.actualizarCliente(c);
            }

            limpiarCampos();
            cargarClientes();

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
    public void eliminarCliente() {

        Cliente seleccionado =
                tablaClientes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecciona un cliente");
            alert.showAndWait();
            return;
        }

        clienteService.eliminarCliente(seleccionado.getIdCliente());

        limpiarCampos();
        cargarClientes();
    }

    // =========================
    // LIMPIAR
    // =========================

    private void limpiarCampos() {

        txtNombre.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();

        tablaClientes.getSelectionModel().clearSelection();
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
                    (Stage) tablaClientes.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Menú principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
