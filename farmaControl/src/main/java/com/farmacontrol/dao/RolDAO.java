package com.farmacontrol.dao;

import com.farmacontrol.model.Rol;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class RolDAO {

    // =========================
    // OBTENER TODOS LOS ROLES
    // =========================
    public ArrayList<Rol> obtenerTodos() {

        ArrayList<Rol> lista = new ArrayList<>();

        String sql = "SELECT * FROM Rol";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));

                lista.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener roles", e);
        }

        return lista;
    }

    // =========================
    // BUSCAR POR ID (OPCIONAL)
    // =========================
    public Rol buscarPorId(int id) {

        String sql = "SELECT * FROM Rol WHERE id_rol = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));

                return r;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar rol", e);
        }

        return null;
    }
}