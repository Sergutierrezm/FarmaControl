package com.farmacontrol.service;

import com.farmacontrol.dao.FacturaDAO;
import com.farmacontrol.dao.ProductoDAO;
import com.farmacontrol.model.*;

import java.util.ArrayList;

public class FacturaService {

    private final FacturaDAO facturaDAO;
    private final ProductoDAO productoDAO;

    public FacturaService() {
        this.facturaDAO = new FacturaDAO();
        this.productoDAO = new ProductoDAO();
    }

    // =========================
    // CREAR FACTURA
    // =========================
    public Factura crearFactura(Cliente cliente, Usuario usuario) {

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setUsuario(usuario);

        return factura;
    }

    // =========================
    // AÑADIR PRODUCTO (solo lógica en memoria)
    // =========================
    public void agregarProducto(Factura factura, Producto producto, int cantidad) {

        if (producto.getStock() < cantidad) {
            System.out.println("❌ Stock insuficiente");
            return;
        }

        DetalleFactura detalle = new DetalleFactura(producto, cantidad);
        factura.agregarDetalle(detalle);
    }

    // =========================
    // GUARDAR FACTURA COMPLETA
    // =========================
    public void guardarFactura(Factura factura) {

        if (factura.getDetalles().isEmpty()) {
            System.out.println("❌ Factura vacía");
            return;
        }

        // recalcular total antes de guardar
        factura.calcularTotal();

        // guardar todo (cabecera + detalles)
        facturaDAO.insertar(factura);

        // actualizar stock en BD
        for (DetalleFactura d : factura.getDetalles()) {

            Producto p = d.getProducto();
            int nuevoStock = p.getStock() - d.getCantidad();

            p.setStock(nuevoStock);
            productoDAO.actualizarStock(p.getIdProducto(), nuevoStock);
        }

        System.out.println("✅ Factura guardada correctamente");
    }

    // =========================
    // OBTENER FACTURAS
    // =========================
    public ArrayList<Factura> obtenerFacturas() {
        return facturaDAO.obtenerTodas();
    }

    // =========================
    // BUSCAR FACTURA
    // =========================
    public Factura buscarFactura(int id) {
        return facturaDAO.buscarPorId(id);
    }
}