package com.farmacontrol.service;

import com.farmacontrol.dao.FacturaDAO;
import com.farmacontrol.model.Factura;
import com.farmacontrol.model.DetalleFactura;
import com.farmacontrol.model.Producto;

public class FacturaService {

    private FacturaDAO facturaDAO;

    public FacturaService(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    // Crear factura y añadir detalles
    public Factura crearFactura(int idFactura) {
        Factura f = new Factura();
        f.setIdFactura(idFactura);
        facturaDAO.agregarFactura(f);
        return f;
    }

    // Añadir producto a factura
    public void agregarProducto(Factura factura, Producto producto, int cantidad) {
        DetalleFactura detalle = new DetalleFactura(producto, cantidad);
        factura.agregarDetalle(detalle);
    }

    // Calcular total de factura
    public double calcularTotal(Factura factura) {
        return factura.calcularTotal();
    }

    // Buscar factura por ID
    public Factura buscarFactura(int id) {
        return facturaDAO.buscarFacturaPorId(id);
    }

    // Mostrar facturas
    public void mostrarFacturas() {
        facturaDAO.mostrarFacturas();
    }
}
