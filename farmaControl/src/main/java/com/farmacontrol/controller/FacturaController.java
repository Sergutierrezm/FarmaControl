package com.farmacontrol.controller;

import com.farmacontrol.model.*;
import com.farmacontrol.service.FacturaService;
import com.farmacontrol.service.ClienteService;
import com.farmacontrol.service.UsuarioService;
import com.farmacontrol.service.ProductoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FacturaController {

    @FXML private TableView<Factura> tablaFacturas;
    @FXML private TableColumn<Factura, Integer> colId;
    @FXML private TableColumn<Factura, String> colFecha;
    @FXML private TableColumn<Factura, String> colCliente;
    @FXML private TableColumn<Factura, String> colTotal;

    @FXML private ComboBox<Cliente> comboClientes;
    @FXML private ComboBox<Usuario> comboUsuarios;

    @FXML private TableView<DetalleFactura> tablaDetalles;
    @FXML private TableColumn<DetalleFactura, String> colProducto;
    @FXML private TableColumn<DetalleFactura, Integer> colCantidad;
    @FXML private TableColumn<DetalleFactura, String> colSubtotal;

    private FacturaService facturaService = new FacturaService();
    private ClienteService clienteService = new ClienteService();
    private UsuarioService usuarioService = new UsuarioService();
    private ProductoService productoService = new ProductoService();

    private Factura facturaActual;

    @FXML
    public void initialize() {

        // ============================
        // CONFIG TABLA FACTURAS
        // ============================
        colId.setCellValueFactory(new PropertyValueFactory<>("idFactura"));
        colFecha.setCellValueFactory(f ->
                new javafx.beans.property.SimpleStringProperty(f.getValue().getFecha().toString())
        );
        colCliente.setCellValueFactory(f ->
                new javafx.beans.property.SimpleStringProperty(
                        f.getValue().getCliente().getNombre()
                )
        );
        colTotal.setCellValueFactory(f ->
                new javafx.beans.property.SimpleStringProperty(
                        f.getValue().getTotal().toString()
                )
        );

        // ============================
        // CONFIG TABLA DETALLES
        // ============================
        colProducto.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getProducto().getNombre()
                )
        );
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(
                        d.getValue().getSubtotal().toString()
                )
        );

        cargarFacturas();
        cargarClientes();
        cargarUsuarios();

        // ============================
        // SELECCIÓN DE FACTURA
        // ============================
        tablaFacturas.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, facturaSel) -> {
                    if (facturaSel != null) {
                        facturaActual = facturaSel;
                        tablaDetalles.setItems(
                                FXCollections.observableArrayList(facturaSel.getDetalles())
                        );
                        comboClientes.setValue(facturaSel.getCliente());
                        comboUsuarios.setValue(facturaSel.getUsuario());
                    }
                }
        );

        // ============================
        // COMBO USUARIOS
        // ============================
        comboUsuarios.setConverter(new javafx.util.StringConverter<Usuario>() {
            @Override
            public String toString(Usuario usuario) {
                if (usuario == null) return "";
                return usuario.getNombre() + " (" + usuario.getRol().getNombre() + ")";
            }

            @Override
            public Usuario fromString(String string) {
                return null;
            }
        });

        comboUsuarios.setCellFactory(listView -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario usuario, boolean empty) {
                super.updateItem(usuario, empty);
                if (empty || usuario == null) {
                    setText(null);
                } else {
                    setText(usuario.getNombre() + " (" + usuario.getRol().getNombre() + ")");
                }
            }
        });

        // ============================
        // COMBO CLIENTES
        // ============================
        comboClientes.setConverter(new javafx.util.StringConverter<Cliente>() {
            @Override
            public String toString(Cliente c) {
                if (c == null) return "";
                return c.getNombre();
            }

            @Override
            public Cliente fromString(String s) {
                return null;
            }
        });

        comboClientes.setCellFactory(listView -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                if (empty || c == null) {
                    setText(null);
                } else {
                    setText(c.getNombre());
                }
            }
        });
    }

    private void cargarFacturas() {
        tablaFacturas.setItems(
                FXCollections.observableArrayList(
                        facturaService.obtenerFacturas()
                )
        );
    }

    private void cargarClientes() {
        comboClientes.setItems(
                FXCollections.observableArrayList(
                        clienteService.obtenerClientes()
                )
        );
    }

    private void cargarUsuarios() {
        comboUsuarios.setItems(
                FXCollections.observableArrayList(
                        usuarioService.obtenerUsuarios()
                )
        );
    }

    // ============================
    // AÑADIR PRODUCTO
    // ============================
    @FXML
    public void agregarProducto() {

        if (facturaActual == null) {

            if (comboClientes.getValue() == null || comboUsuarios.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Faltan datos");
                alert.setContentText("Selecciona un cliente y un usuario antes de añadir productos.");
                alert.show();
                return;
            }

            facturaActual = facturaService.crearFactura(
                    comboClientes.getValue(),
                    comboUsuarios.getValue()
            );
        }

        Producto p = seleccionarProducto();
        if (p == null) return;

        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setHeaderText("Cantidad para " + p.getNombre());
        int cantidad = Integer.parseInt(dialog.showAndWait().get());

        facturaActual.agregarDetalle(new DetalleFactura(p, cantidad));

        tablaDetalles.setItems(
                FXCollections.observableArrayList(facturaActual.getDetalles())
        );
    }

    // ============================
    // CHOICEDIALOG PRODUCTOS
    // ============================
    private Producto seleccionarProducto() {

        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Seleccionar producto");
        dialog.setHeaderText("Elige un producto");

        ButtonType okButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        ComboBox<Producto> combo = new ComboBox<>();
        combo.setItems(FXCollections.observableArrayList(productoService.obtenerProductos()));

        combo.setConverter(new javafx.util.StringConverter<Producto>() {
            @Override
            public String toString(Producto p) {
                if (p == null) return "";
                return p.getNombre();
            }

            @Override
            public Producto fromString(String s) {
                return null;
            }
        });

        combo.setCellFactory(listView -> new ListCell<Producto>() {
            @Override
            protected void updateItem(Producto p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setText(null);
                } else {
                    setText(p.getNombre());
                }
            }
        });

        dialog.getDialogPane().setContent(combo);

        dialog.setResultConverter(button -> {
            if (button == okButton) {
                return combo.getValue();
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }



    // ============================
    // GUARDAR FACTURA
    // ============================
    @FXML
    public void guardarFactura() {

        if (facturaActual == null) {
            facturaActual = facturaService.crearFactura(
                    comboClientes.getValue(),
                    comboUsuarios.getValue()
            );
        }

        facturaActual.setCliente(comboClientes.getValue());
        facturaActual.setUsuario(comboUsuarios.getValue());

        facturaService.guardarFactura(facturaActual);

        cargarFacturas();
        facturaActual = null;
        tablaDetalles.getItems().clear();
    }

    // ============================
    // ELIMINAR FACTURA
    // ============================
    @FXML
    public void eliminarFactura() {

        Factura f = tablaFacturas.getSelectionModel().getSelectedItem();

        if (f == null) return;

        facturaService.eliminarFactura(f.getIdFactura());

        cargarFacturas();
        tablaDetalles.getItems().clear();
        facturaActual = null;
    }

    // ============================
    // VOLVER
    // ============================
    @FXML
    public void volver() {
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/view/menu.fxml"));

            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage =
                    (javafx.stage.Stage) tablaFacturas.getScene().getWindow();

            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Menú principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
