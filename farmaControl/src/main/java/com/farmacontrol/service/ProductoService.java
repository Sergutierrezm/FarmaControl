package com.farmacontrol.service;

import com.farmacontrol.dao.ProductoDAO;
import com.farmacontrol.model.Producto;

public class ProductoService {

    private ProductoDAO dao = new ProductoDAO();

    public void registrarProducto(Producto p) {
        if (p.getPrecio().doubleValue() <= 0) {
            System.out.println("Precio inválido");
            return;
        }
        dao.agregarProducto(p);
    }

    public void mostrarProductos() {
        dao.mostrarProductos();
    }
}
