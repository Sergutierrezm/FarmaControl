package com.farmacontrol.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Factura {

    private int idFactura;
    private LocalDateTime fecha;
    private Cliente cliente;
    private Usuario usuario;
    private ArrayList<DetalleFactura> detalles = new ArrayList<>();
    private BigDecimal total;

    // Constructor vacío
    public Factura() {
        this.fecha = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    // Constructor completo
    public Factura(int idFactura, LocalDateTime fecha, Cliente cliente, Usuario usuario) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.usuario = usuario;
        this.total = BigDecimal.ZERO;
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

    public ArrayList<DetalleFactura> getDetalles() {
        return detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // =========================
    // LÓGICA DE NEGOCIO
    // =========================

    // Añadir detalle
    public void agregarDetalle(DetalleFactura detalle) {
        detalle.setFactura(this);
        detalles.add(detalle);
        calcularTotal();
    }

    // Calcular total (CORRECTO)
    public void calcularTotal() {
        BigDecimal totalCalculado = BigDecimal.ZERO;

        for (DetalleFactura d : detalles) {
            totalCalculado = totalCalculado.add(d.getSubtotal());
        }

        this.total = totalCalculado;
    }

    // Mostrar factura (debug)
    public void mostrarFactura() {
        System.out.println("Factura ID: " + idFactura + " - Fecha: " + fecha);

        System.out.println("Cliente: " +
                (cliente != null ? cliente.getNombre() : "No asignado"));

        System.out.println("Usuario: " +
                (usuario != null ? usuario.getNombre() : "No asignado"));

        System.out.println("Detalles:");

        for (DetalleFactura d : detalles) {

            String nombreProducto = (d.getProducto() != null)
                    ? d.getProducto().getNombre()
                    : "N/A";

            System.out.println("  Producto: " + nombreProducto +
                    " | Cantidad: " + d.getCantidad() +
                    " | Subtotal: " + d.getSubtotal());
        }

        System.out.println("TOTAL: " + total);
    }
}