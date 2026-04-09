package com.farmacontrol.dao;

import com.farmacontrol.model.Factura;
import java.util.ArrayList;

public class FacturaDAO {

    private ArrayList<Factura> listaFacturas = new ArrayList<>();

    // Agregar factura
    public void agregarFactura(Factura f) {
        listaFacturas.add(f);
    }

    // Mostrar facturas
    public void mostrarFacturas() {
        for (Factura f : listaFacturas) {
            System.out.println("Factura ID: " + f.getIdFactura() +
                    " - Total: " + f.calcularTotal());
        }
    }

    // Buscar por ID
    public Factura buscarFacturaPorId(int id) {
        for (Factura f : listaFacturas) {
            if (f.getIdFactura() == id) {
                return f;
            }
        }
        return null;
    }

    // (Opcional) Eliminar factura
    public void eliminarFactura(int id) {
        listaFacturas.removeIf(f -> f.getIdFactura() == id);
    }
}
