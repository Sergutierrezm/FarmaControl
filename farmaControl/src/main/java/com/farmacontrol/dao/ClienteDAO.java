package com.farmacontrol.dao;

import com.farmacontrol.model.Cliente;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    // =========================
    // INSERTAR CLIENTE
    // =========================
    public void insertar(Cliente c) {

        String sql = "INSERT INTO Cliente (nombre, telefono, email, direccion) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getTelefono());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getDireccion());

            stmt.executeUpdate();

            // Recuperar ID autogenerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                c.setIdCliente(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar cliente");
            e.printStackTrace();
        }
    }

    // =========================
    // LISTAR TODOS
    // =========================
    public ArrayList<Cliente> obtenerTodos() {

        ArrayList<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM Cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Cliente c = new Cliente();

                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));

                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar clientes");
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Cliente buscarPorId(int id) {

        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Cliente c = new Cliente();

                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));

                return c;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar cliente");
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // ELIMINAR CLIENTE
    // =========================
    public void eliminar(int id) {

        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar cliente");
            e.printStackTrace();
        }
    }

    // =========================
    // ACTUALIZAR CLIENTE
    // =========================
    public void actualizar(Cliente c) {

        String sql = "UPDATE Cliente SET nombre=?, telefono=?, email=?, direccion=? WHERE id_cliente=?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getTelefono());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getDireccion());
            stmt.setInt(5, c.getIdCliente());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar cliente");
            e.printStackTrace();
        }
    }
}