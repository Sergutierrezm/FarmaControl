package com.farmacontrol.service;

import com.farmacontrol.dao.LogActividadDAO;
import com.farmacontrol.dao.ProveedorDAO;
import com.farmacontrol.model.LogActividad;
import com.farmacontrol.model.Proveedor;
import com.farmacontrol.model.Usuario;
import com.farmacontrol.util.Sesion;

import java.util.ArrayList;

public class ProveedorService {

    private final ProveedorDAO dao = new ProveedorDAO();
    private final LogActividadDAO logDAO = new LogActividadDAO();

    public boolean registrarProveedor(Proveedor p) {

        if (p == null || p.getNombre() == null || p.getNombre().isBlank()) {
            return false;
        }

        dao.insertar(p);
        registrarLog("CREAR_PROVEEDOR", "Proveedor creado: " + p.getNombre());
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
        registrarLog("EDITAR_PROVEEDOR", "Proveedor editado: " + p.getNombre());
    }

    public void eliminarProveedor(int id) {
        dao.eliminar(id);
        registrarLog("ELIMINAR_PROVEEDOR", "Proveedor eliminado con ID: " + id);
    }

    private void registrarLog(String tipoAccion, String descripcion) {

        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario == null) return;

        LogActividad log = new LogActividad();
        log.setTipoAccion(tipoAccion);
        log.setDescripcion(descripcion);
        log.setUsuario(usuario);

        try {
            logDAO.insertar(log);
        } catch (Exception e) {
            System.err.println("No se pudo registrar el log: " + e.getMessage());
        }
    }
}
