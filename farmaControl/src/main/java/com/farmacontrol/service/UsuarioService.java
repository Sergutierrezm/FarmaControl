package com.farmacontrol.service;

import com.farmacontrol.dao.UsuarioDAO;
import com.farmacontrol.model.Usuario;

import java.util.ArrayList;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String email, String contrasena) {

        if (email == null || email.isEmpty() ||
                contrasena == null || contrasena.isEmpty()) {
            return null;
        }

        return usuarioDAO.login(email, contrasena);
    }

    public void registrarUsuario(Usuario u) {

        if (u == null) return;

        if (u.getEmail() == null || u.getEmail().isEmpty()) {
            return;
        }

        usuarioDAO.insertar(u);
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.obtenerTodos();
    }

    public Usuario buscarUsuario(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.eliminar(id);
    }

    public void actualizarUsuario(Usuario u) {
        usuarioDAO.actualizar(u);
    }
}