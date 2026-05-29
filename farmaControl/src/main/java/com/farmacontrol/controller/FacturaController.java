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

    @FXML
    public void agregarProducto() {

        // Si no hay factura creada, la creamos
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

        // Seleccionar producto
        Producto p = seleccionarProducto();
        if (p == null) return;

        // Pedir cantidad
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setHeaderText("Cantidad para " + p.getNombre());
        int cantidad = Integer.parseInt(dialog.showAndWait().get());

        // Agregar detalle
        facturaActual.agregarDetalle(new DetalleFactura(p, cantidad));

        // Actualizar tabla
        tablaDetalles.setItems(
                FXCollections.observableArrayList(facturaActual.getDetalles())
        );
    }


    private Producto seleccionarProducto() {

        ObservableList<Producto> productos =
                FXCollections.observableArrayList(productoService.obtenerProductos());

        ChoiceDialog<Producto> dialog = new ChoiceDialog<>(null, productos);
        dialog.setHeaderText("Selecciona un producto");

        // Personalizar cómo se muestran los productos
        dialog.getDialogPane().lookup(".list-view").setStyle("-fx-font-size: 14px;");

        // Cambiar el texto mostrado en la lista
        ((ListView<Producto>) dialog.getDialogPane().lookup(".list-view"))
                .setCellFactory(list -> new ListCell<Producto>() {
                    @Override
                    protected void updateItem(Producto p, boolean empty) {
                        super.updateItem(p, empty);
                        if (empty || p == null) {
                            setText(null);
                        } else {
                            setText(p.getNombre() + " (" + p.getPrecio() + "€)");
                        }
                    }
                });

        return dialog.showAndWait().orElse(null);
    }



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

    @FXML
    public void eliminarFactura() {

        Factura f = tablaFacturas.getSelectionModel().getSelectedItem();

        if (f == null) return;

        facturaService.buscarFactura(f.getIdFactura());

        cargarFacturas();
    }
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
