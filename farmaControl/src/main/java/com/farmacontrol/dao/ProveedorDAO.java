package com.farmacontrol.dao;

import com.farmacontrol.model.Proveedor;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class ProveedorDAO {

    public ArrayList<Proveedor> obtenerTodos() {

        ArrayList<Proveedor> lista = new ArrayList<>();

        String sql = "SELECT * FROM Proveedor";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener proveedores", e);
        }

        return lista;
    }

    public void insertar(Proveedor p) {

        String sql = "INSERT INTO Proveedor (nombre, telefono, direccion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTelefono());
            stmt.setString(3, p.getDireccion());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setIdProveedor(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar proveedor", e);
        }
    }

    public Proveedor buscarPorId(int id) {

        String sql = "SELECT * FROM Proveedor WHERE id_proveedor = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                return p;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedor", e);
        }

        return null;
    }

    public void actualizar(Proveedor p) {

        String sql = "UPDATE Proveedor SET nombre=?, telefono=?, direccion=? WHERE id_proveedor=?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTelefono());
            stmt.setString(3, p.getDireccion());
            stmt.setInt(4, p.getIdProveedor());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar proveedor", e);
        }
    }

    public void eliminar(int id) {

        String sql = "DELETE FROM Proveedor WHERE id_proveedor = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar proveedor", e);
        }
    }
}