package com.farmacontrol;

import com.farmacontrol.dao.FacturaDAO;
import com.farmacontrol.model.*;
import com.farmacontrol.service.FacturaService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestFactura {

    public static void main(String[] args) {

        System.out.println("=== TEST FUNCIONALIDADES FACTURA ===");

        // Crear DAO y Service
        FacturaDAO facturaDAO = new FacturaDAO();
        FacturaService facturaService = new FacturaService(facturaDAO);

        // Crear proveedor y productos
        Proveedor prov = new Proveedor(1, "Proveedor1", "123456789", "Madrid");
        Producto p1 = new Producto(1, "Paracetamol", "Dolor de cabeza", new BigDecimal("5.00"), 10, prov);
        Producto p2 = new Producto(2, "Ibuprofeno", "Inflamación", new BigDecimal("8.00"), 5, prov);

        // Crear factura
        Factura factura = facturaService.crearFactura(1);

        // Asignar cliente y usuario (simulados)
        Cliente cliente = new Cliente(1, "Juan Pérez", "123456789", "sergio@m.com", "Calle Juan 123");

        Rol rolAdmin = new Rol(1, "Administrador", "Usuario con todos los permisos");
        Usuario usuario = new Usuario(1, "admin", "perez@m.com", "contraseña", rolAdmin);

        factura.setCliente(cliente);
        factura.setUsuario(usuario);
        factura.setFecha(LocalDateTime.now());

        // Prueba 1: Crear factura y almacenar
        System.out.println("\n--- Prueba 1: Crear factura y almacenar ---");
        if (facturaService.buscarFactura(1) != null) {
            System.out.println("Prueba 1 OK: Factura creada y almacenada correctamente");
        } else {
            System.out.println("Prueba 1 FALLIDA");
        }

        // Prueba 2: Agregar productos y calcular total
        System.out.println("\n--- Prueba 2: Agregar productos y calcular total ---");
        facturaService.agregarProducto(factura, p1, 3); // 5*3 = 15
        facturaService.agregarProducto(factura, p2, 2); // 8*2 = 16

        double totalCalculado = facturaService.calcularTotal(factura);
        System.out.println("Total calculado de la factura: " + totalCalculado);

        if (totalCalculado == 31.0) {
            System.out.println("Prueba 2 OK: Total calculado correctamente");
        } else {
            System.out.println("Prueba 2 FALLIDA");
        }

        // Mostrar todas las facturas
        System.out.println("\n--- Mostrar facturas guardadas ---");
        facturaService.mostrarFacturas();
    }
}