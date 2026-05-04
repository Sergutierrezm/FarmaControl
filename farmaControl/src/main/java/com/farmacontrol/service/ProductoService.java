package com.farmacontrol.service;

import com.farmacontrol.dao.ProductoDAO;
import com.farmacontrol.model.Producto;

import java.util.ArrayList;

public class ProductoService {

    private final ProductoDAO dao = new ProductoDAO();

    // =========================
    // REGISTRAR PRODUCTO
    // =========================
    public boolean registrarProducto(Producto p) {

        if (p == null) return false;

        if (p.getPrecio() == null || p.getPrecio().doubleValue() <= 0) {
            System.out.println("❌ Precio inválido");
            return false;
        }

        if (p.getStock() < 0) {
            System.out.println("❌ Stock inválido");
            return false;
        }

        dao.insertar(p);
        return true;
    }

    // =========================
    // LISTAR PRODUCTOS
    // =========================
    public ArrayList<Producto> obtenerProductos() {
        return dao.obtenerTodos();
    }

    // =========================
    // BUSCAR PRODUCTO
    // =========================
    public Producto buscarProducto(int id) {
        return dao.buscarPorId(id);
    }

    // =========================
    // ACTUALIZAR PRODUCTO
    // =========================
    public void actualizarProducto(Producto p) {
        dao.actualizar(p);
    }

    // =========================
    // ELIMINAR PRODUCTO
    // =========================
    public void eliminarProducto(int id) {
        dao.eliminar(id);
    }
}