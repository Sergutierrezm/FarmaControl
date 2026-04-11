package com.farmacontrol.service;

import com.farmacontrol.dao.FacturaDAO;
import com.farmacontrol.model.DetalleFactura;
import com.farmacontrol.model.Factura;
import com.farmacontrol.model.Producto;
import com.farmacontrol.model.Proveedor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FacturaServiceTest {

    private FacturaDAO facturaDAO;
    private FacturaService facturaService;
    private Proveedor proveedor;
    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        // Inicializamos los objetos reales antes de cada prueba
        facturaDAO = new FacturaDAO();
        facturaService = new FacturaService(facturaDAO);

        // Crear datos base requeridos para las facturas (replicando TestFactura.java)
        proveedor = new Proveedor(1, "Proveedor Test", "111111111", "Zaragoza");
        producto1 = new Producto(1, "Paracetamol", "Dolor", new BigDecimal("5.00"), 100, proveedor);
        producto2 = new Producto(2, "Ibuprofeno", "Inflamación", new BigDecimal("8.00"), 50, proveedor);
    }

    @Test
    void testCrearFacturaYAlmacenar() {
        // Pruebas: Creación y recuperación exitosa (Prueba 1)
        
        // Ejecución: Creamos una factura con ID 10
        Factura factura = facturaService.crearFactura(10);

        // Verificamos estado del objeto
        assertNotNull(factura, "La factura instanciada no debería ser nula");
        assertEquals(10, factura.getIdFactura(), "El ID de la factura asignada debería ser el correcto");
        
        // Verificamos que se haya almacenado en la persistencia temporal (el DAO)
        Factura facturaAlmacenada = facturaService.buscarFactura(10);
        assertNotNull(facturaAlmacenada, "La factura debería estar correctamente almacenada y poder recuperarse");
        assertEquals(factura, facturaAlmacenada, "La instancia recuperada debe ser la misma que la creada");
    }

    @Test
    void testCalcularTotalFacturaVariosProductos() {
        // Pruebas: Cálculo de totales sumando varios subtotales (Prueba 2)
        
        // Preparación
        Factura factura = facturaService.crearFactura(20);

        // Ejecución: Agregamos productos simulando el comportamiento de TestFactura empírico original (5.0*3 + 8.0*2 = 31.0)
        facturaService.agregarProducto(factura, producto1, 3); // 5.00 * 3 = 15.00
        facturaService.agregarProducto(factura, producto2, 2); // 8.00 * 2 = 16.00

        // Verificación delegada con assertEquals
        double totalCalculado = facturaService.calcularTotal(factura);
        assertEquals(31.0, totalCalculado, "El total de 3 uds de Paracetamol y 2 uds de Ibuprofeno debe sumar exactamente 31.0");
    }

    @Test
    void testRecalculoParcialAlModificarCantidad() {
        // Pruebas: Comportamiento dinámico (re-cálculo) ante modificaciones de la cantidad (Prueba 3)
        
        // Preparación
        Factura factura = facturaService.crearFactura(30);
        facturaService.agregarProducto(factura, producto1, 2); // 5.00 * 2 = 10.00
        
        // Validar el estado inicial y asegurar las bases antes de mutar
        assertEquals(10.0, facturaService.calcularTotal(factura), "El total inicial calculado debe ser 10.0");

        // Ejecución modificadora en caliente: Obtenemos el detalle existente y le alteramos su cantidad
        DetalleFactura detalleModificado = factura.getDetalles().get(0);
        detalleModificado.setCantidad(5); // Setter diseñado para recalcular el subtotal: 5.00 * 5 = 25.00
        
        // Verificación posterior para demostrar mutabilidad controlada y buen diseño encapsulado
        assertEquals(25.0, facturaService.calcularTotal(factura), "El total debe ser exactamente 25.0 tras modificarse la cantidad interna del Detalle a 5 uds");
    }
}
