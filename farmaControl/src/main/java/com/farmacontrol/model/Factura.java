package com.farmacontrol.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Factura {

    private int idFactura;
    private LocalDateTime fecha;
    private Cliente cliente;  // Relación con Cliente
    private Usuario usuario;  // Relación con Usuario
    private ArrayList<DetalleFactura> detalles = new ArrayList<>(); // Lista de productos

    // Constructor vacío
    public Factura() {
        this.fecha = LocalDateTime.now(); // Fecha automática al crear
    }

    // Constructor completo
    public Factura(int idFactura, LocalDateTime fecha, Cliente cliente, Usuario usuario) {
        this.idFactura = idFactura;
        this.fecha = fecha;
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

    // Agregar un detalle (producto + cantidad)
    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
    }

    // Calcular total automáticamente
    public double calcularTotal() {
        double total = 0;
        for (DetalleFactura d : detalles) {
            total += d.getSubtotal();
        }
        return total;
    }

    // Mostrar detalles de la factura
    public void mostrarFactura() {
        System.out.println("Factura ID: " + idFactura + " - Fecha: " + fecha);
        System.out.println("Cliente: " + (cliente != null ? cliente.getNombre() : "No asignado"));
        System.out.println("Usuario: " + (usuario != null ? usuario.getNombre() : "No asignado"));
        System.out.println("Detalles:");
        for (DetalleFactura d : detalles) {
            System.out.println("  Producto: " + d.getProducto().getNombre() +
                    " | Cantidad: " + d.getCantidad() +
                    " | Subtotal: " + d.getSubtotal());
        }
        System.out.println("TOTAL: " + calcularTotal());
    }
}
