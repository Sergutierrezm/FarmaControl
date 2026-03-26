package com.farmacontrol.model;

public class DetalleFactura {

    private int idDetalle;
    private Factura factura;     // Relación con Factura
    private Producto producto;   // Relación con Producto
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Constructor vacío
    public DetalleFactura() {
    }

    // Constructor completo
    public DetalleFactura(int idDetalle, Factura factura, Producto producto, int cantidad, double precioUnitario, double subtotal) {
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
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
