package com.farmacontrol.service;

import com.farmacontrol.dao.UsuarioDAO;
import com.farmacontrol.model.Usuario;

import java.util.ArrayList;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // =========================
    // LOGIN
    // =========================
    public Usuario login(String email, String contrasena) {

        if (email == null || contrasena == null) {
            System.out.println("❌ Email o contraseña vacíos");
            return null;
        }

        Usuario u = usuarioDAO.login(email, contrasena);

        if (u == null) {
            System.out.println("❌ Credenciales incorrectas");
            return null;
        }

        System.out.println("✅ Bienvenido " + u.getNombre());
        return u;
    }

    // =========================
    // REGISTRAR USUARIO
    // =========================
    public void registrarUsuario(Usuario u) {

        if (u == null) return;

        if (u.getEmail() == null || u.getEmail().isEmpty()) {
            System.out.println("❌ Email inválido");
            return;
        }

        usuarioDAO.insertar(u);
        System.out.println("✅ Usuario registrado correctamente");
    }

    // =========================
    // LISTAR USUARIOS
    // =========================
    public ArrayList<Usuario> obtenerUsuarios() {
        return usuarioDAO.obtenerTodos();
    }

    // =========================
    // BUSCAR USUARIO
    // =========================
    public Usuario buscarUsuario(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    // =========================
    // ELIMINAR USUARIO
    // =========================
    public void eliminarUsuario(int id) {
        usuarioDAO.eliminar(id);
    }

    // =========================
    // ACTUALIZAR USUARIO
    // =========================
    public void actualizarUsuario(Usuario u) {
        usuarioDAO.actualizar(u);
    }
}