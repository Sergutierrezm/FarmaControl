package com.farmacontrol.model;

import java.math.BigDecimal;

public class DetalleFactura {

    private int idDetalle;
    private Factura factura;
    private Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleFactura() {
    }

    // Constructor simplificado
    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();

        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    // Constructor completo
    public DetalleFactura(int idDetalle, Factura factura, Producto producto,
                          int cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.idDetalle = idDetalle;
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        recalcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        recalcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    private void recalcularSubtotal() {
        if (precioUnitario != null) {
            this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }
}