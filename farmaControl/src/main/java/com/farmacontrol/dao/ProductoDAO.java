package com.farmacontrol.dao;

import com.farmacontrol.model.Producto;
import java.util.ArrayList;

public class ProductoDAO {

    private ArrayList<Producto> listaProductos = new ArrayList<>();

    // Añadir producto
    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }

    // Mostrar productos
    public void mostrarProductos() {
        for (Producto p : listaProductos) {
            System.out.println(p.getIdProducto() + " - " + p.getNombre() + " - " + p.getPrecio());
        }
    }

    // Buscar producto por ID
    public Producto buscarProductoPorId(int id) {
        for (Producto p : listaProductos) {
            if (p.getIdProducto() == id) {
                return p;
            }
        }
        return null;
    }

    public void eliminarProducto(int id) {
        listaProductos.removeIf(p -> p.getIdProducto() == id);
    }
}
