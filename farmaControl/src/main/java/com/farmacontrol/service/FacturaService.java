package com.farmacontrol.service;

import com.farmacontrol.dao.FacturaDAO;
import com.farmacontrol.dao.ProductoDAO;
import com.farmacontrol.dao.DetalleFacturaDAO;
import com.farmacontrol.model.*;

import java.util.ArrayList;

public class FacturaService {

    private final FacturaDAO facturaDAO;
    private final ProductoDAO productoDAO;
    private final DetalleFacturaDAO detalleDAO;

    public FacturaService() {
        this.facturaDAO = new FacturaDAO();
        this.productoDAO = new ProductoDAO();
        this.detalleDAO = new DetalleFacturaDAO();
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
    // AÑADIR PRODUCTO
    // =========================
    public void agregarProducto(Factura factura, Producto producto, int cantidad) {

        if (producto.getStock() < cantidad) {
            System.out.println("❌ Stock insuficiente");
            return;
        }

        DetalleFactura detalle = new DetalleFactura(producto, cantidad);
        factura.agregarDetalle(detalle);

        // 🔥 ACTUALIZAR STOCK EN BD (IMPORTANTE)
        int nuevoStock = producto.getStock() - cantidad;
        producto.setStock(nuevoStock);

        productoDAO.actualizarStock(producto.getIdProducto(), nuevoStock);
    }

    // =========================
    // GUARDAR FACTURA COMPLETA
    // =========================
    public void guardarFactura(Factura factura) {

        if (factura.getDetalles().isEmpty()) {
            System.out.println("❌ Factura vacía");
            return;
        }

        // recalcular total
        factura.calcularTotal();

        // 1. guardar cabecera
        facturaDAO.insertar(factura);

        // 2. guardar detalles
        for (DetalleFactura d : factura.getDetalles()) {
            detalleDAO.insertar(d, factura.getIdFactura());
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