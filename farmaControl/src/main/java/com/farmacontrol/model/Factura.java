package com.farmacontrol.model;

import java.time.LocalDateTime;

public class Factura {

    private int idFactura;
    private LocalDateTime fecha;
    private double total;
    private Cliente cliente;  // Relación con Cliente
    private Usuario usuario;  // Relación con Usuario

    // Constructor vacío
    public Factura() {
    }

    // Constructor completo
    public Factura(int idFactura, LocalDateTime fecha, double total, Cliente cliente, Usuario usuario) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
