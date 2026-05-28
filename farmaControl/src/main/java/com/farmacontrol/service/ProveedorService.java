package com.farmacontrol.service;

import com.farmacontrol.dao.ProveedorDAO;
import com.farmacontrol.model.Proveedor;

import java.util.ArrayList;

public class ProveedorService {

    private final ProveedorDAO dao = new ProveedorDAO();

    public boolean registrarProveedor(Proveedor p) {

        if (p == null || p.getNombre() == null || p.getNombre().isBlank()) {
            return false;
        }

        dao.insertar(p);
        return true;
    }

    public ArrayList<Proveedor> obtenerProveedores() {
        return dao.obtenerTodos();
    }

    public Proveedor buscarProveedor(int id) {
        return dao.buscarPorId(id);
    }

    public void actualizarProveedor(Proveedor p) {
        dao.actualizar(p);
    }

    public void eliminarProveedor(int id) {
        dao.eliminar(id);
    }
}
