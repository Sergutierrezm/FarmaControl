package com.farmacontrol.controller;

import com.farmacontrol.model.Producto;
import com.farmacontrol.model.Proveedor;
import com.farmacontrol.service.ProductoService;
import com.farmacontrol.dao.ProveedorDAO;

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

public class ProductoController {

    // =========================
    // TABLA
    // =========================

    @FXML private TableView<Producto> tablaProductos;

    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, BigDecimal> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    // =========================
    // CAMPOS
    // =========================

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    @FXML private ComboBox<Proveedor> cbProveedor;
    @FXML
    private TableColumn<Producto, String> colProveedor;

    // =========================
    // SERVICE / DAO
    // =========================

    private final ProductoService productoService = new ProductoService();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    // =========================
    // INIT
    // =========================

    @FXML
    public void initialize() {

        // columnas tabla
        colProveedor.setCellValueFactory(cellData -> {

            if (cellData.getValue().getProveedor() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getProveedor().getNombre()
                );
            }

            return new javafx.beans.property.SimpleStringProperty("Sin proveedor");
        });

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // cargar datos
        cargarProductos();
        cargarProveedores();

        // seleccionar producto
        tablaProductos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, producto) -> {

                    if (producto != null) {

                        txtNombre.setText(producto.getNombre());
                        txtDescripcion.setText(producto.getDescripcion());
                        txtPrecio.setText(producto.getPrecio().toString());
                        txtStock.setText(String.valueOf(producto.getStock()));

                        cbProveedor.setValue(producto.getProveedor());
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
    // CARGAR PROVEEDORES
    // =========================

    private void cargarProveedores() {

        cbProveedor.setItems(
                FXCollections.observableArrayList(
                        proveedorDAO.obtenerTodos()
                )
        );
    }

    // =========================
    // GUARDAR
    // =========================

    @FXML
    public void guardarProducto() {

        try {

            Producto seleccionado =
                    tablaProductos.getSelectionModel().getSelectedItem();

            Producto p = (seleccionado != null) ? seleccionado : new Producto();

            p.setNombre(txtNombre.getText());
            p.setDescripcion(txtDescripcion.getText());

            p.setPrecio(new BigDecimal(txtPrecio.getText()));
            p.setStock(Integer.parseInt(txtStock.getText()));

            // 🔥 PROVEEDOR (CLAVE)
            Proveedor proveedor = cbProveedor.getValue();
            p.setProveedor(proveedor);

            if (seleccionado == null) {
                productoService.registrarProducto(p);
            } else {
                productoService.actualizarProducto(p);
            }

            limpiarCampos();
            cargarProductos();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error real");

            // 🔥 importante: ver error real
            alert.setContentText(e.getMessage());

            alert.showAndWait();

            e.printStackTrace();
        }
    }

    // =========================
    // ELIMINAR
    // =========================

    @FXML
    public void eliminarProducto() {

        Producto seleccionado =
                tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecciona un producto");
            alert.showAndWait();
            return;
        }

        productoService.eliminarProducto(seleccionado.getIdProducto());

        limpiarCampos();
        cargarProductos();
    }

    // =========================
    // LIMPIAR
    // =========================

    private void limpiarCampos() {

        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStock.clear();

        cbProveedor.setValue(null);
        tablaProductos.getSelectionModel().clearSelection();
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
                    (Stage) tablaProductos.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Menú principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}