package com.farmacontrol.dao;

import com.farmacontrol.model.Rol;
import com.farmacontrol.model.Usuario;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    // =========================
    // INSERTAR USUARIO
    // =========================
    public void insertar(Usuario u) {

        String sql = "INSERT INTO Usuario (nombre, email, contrasena, id_rol) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getContrasena());
            stmt.setInt(4, u.getRol().getIdRol());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                u.setIdUsuario(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar usuario");
            e.printStackTrace();
        }
    }

    // =========================
    // LOGIN (MUY IMPORTANTE)
    // =========================
    public Usuario login(String email, String contrasena) {

        String sql = "SELECT u.*, r.nombre as rol_nombre, r.id_rol " +
                "FROM Usuario u " +
                "JOIN Rol r ON u.id_rol = r.id_rol " +
                "WHERE u.email = ? AND u.contrasena = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("rol_nombre"));

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rol);

                return u;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en login");
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // LISTAR TODOS
    // =========================
    public ArrayList<Usuario> obtenerTodos() {

        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = "SELECT u.*, r.id_rol, r.nombre as rol_nombre FROM Usuario u " +
                "JOIN Rol r ON u.id_rol = r.id_rol";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("rol_nombre"));

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rol);

                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar usuarios");
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Usuario buscarPorId(int id) {

        String sql = "SELECT u.*, r.id_rol, r.nombre as rol_nombre " +
                "FROM Usuario u JOIN Rol r ON u.id_rol = r.id_rol " +
                "WHERE u.id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombre(rs.getString("rol_nombre"));

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rol);

                return u;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar usuario");
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // ELIMINAR
    // =========================
    public void eliminar(int id) {

        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar usuario");
            e.printStackTrace();
        }
    }

    // =========================
    // ACTUALIZAR
    // =========================
    public void actualizar(Usuario u) {

        String sql = "UPDATE Usuario SET nombre=?, email=?, contrasena=?, id_rol=? WHERE id_usuario=?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getContrasena());
            stmt.setInt(4, u.getRol().getIdRol());
            stmt.setInt(5, u.getIdUsuario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar usuario");
            e.printStackTrace();
        }
    }
}