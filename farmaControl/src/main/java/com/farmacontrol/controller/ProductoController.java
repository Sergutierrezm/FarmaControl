package com.farmacontrol.controller;

import com.farmacontrol.model.Producto;
import com.farmacontrol.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;


import com.farmacontrol.model.Proveedor;





import javafx.scene.control.ComboBox;



public class ProductoController {

    // =========================
    // TABLA
    // =========================

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, BigDecimal> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private ComboBox<Proveedor> cbProveedor;






    // =========================
    // CAMPOS
    // =========================

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    // =========================
    // SERVICE
    // =========================

    private ProductoService productoService = new ProductoService();

    // =========================
    // INITIALIZE
    // =========================

    @FXML
    public void initialize() {

        // conectar columnas
        colId.setCellValueFactory(
                new PropertyValueFactory<>("idProducto"));

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio"));

        colStock.setCellValueFactory(
                new PropertyValueFactory<>("stock"));

        // cargar productos
        cargarProductos();

        // seleccionar fila
        tablaProductos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, productoSeleccionado) -> {

                    if (productoSeleccionado != null) {

                        txtNombre.setText(productoSeleccionado.getNombre());
                        txtDescripcion.setText(productoSeleccionado.getDescripcion());
                        txtPrecio.setText(productoSeleccionado.getPrecio().toString());
                        txtStock.setText(String.valueOf(productoSeleccionado.getStock()));
                    }
                });
    }

    // =========================
    // CARGAR PRODUCTOS
    // =========================

    private void cargarProductos() {

        ObservableList<Producto> lista =
                FXCollections.observableArrayList(
                        productoService.obtenerProductos()
                );

        tablaProductos.setItems(lista);
    }

    // =========================
    // GUARDAR PRODUCTO
    // =========================

    @FXML
    public void guardarProducto() {

        try {

            Producto productoSeleccionado =
                    tablaProductos.getSelectionModel().getSelectedItem();

            Producto p;

            // EDITAR
            if (productoSeleccionado != null) {

                p = productoSeleccionado;

            } else {

                // NUEVO
                p = new Producto();
            }

            p.setNombre(txtNombre.getText());
            p.setDescripcion(txtDescripcion.getText());

            p.setPrecio(
                    new BigDecimal(txtPrecio.getText())
            );

            p.setStock(
                    Integer.parseInt(txtStock.getText())
            );

            // INSERTAR o ACTUALIZAR
            if (productoSeleccionado == null) {

                productoService.registrarProducto(p);

            } else {

                productoService.actualizarProducto(p);
            }

            limpiarCampos();
            cargarProductos();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Datos inválidos");

            alert.showAndWait();

            e.printStackTrace();
        }
    }

    // =========================
    // ELIMINAR PRODUCTO
    // =========================

    @FXML
    public void eliminarProducto() {

        Producto productoSeleccionado =
                tablaProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un producto");

            alert.showAndWait();

            return;
        }

        productoService.eliminarProducto(
                productoSeleccionado.getIdProducto()
        );

        limpiarCampos();
        cargarProductos();
    }

    // =========================
    // LIMPIAR CAMPOS
    // =========================

    private void limpiarCampos() {

        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStock.clear();

        tablaProductos.getSelectionModel().clearSelection();
    }

    // =========================
    // VOLVER AL MENÚ
    // =========================

    @FXML
    public void volverMenu() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/view/menu.fxml"));

            Parent root = loader.load();

            Stage stage =
                    (Stage) tablaProductos.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Menú principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}