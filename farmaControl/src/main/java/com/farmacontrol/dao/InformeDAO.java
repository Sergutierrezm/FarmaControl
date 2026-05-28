package com.farmacontrol.dao;

import com.farmacontrol.model.InformeFila;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InformeDAO {

    // =========================
    // CLIENTES
    // =========================
    public List<InformeFila> listarClientes() {

        List<InformeFila> lista = new ArrayList<>();

        String sql = "SELECT nombre, telefono, email FROM Cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(new InformeFila(
                        rs.getString("nombre"),
                        rs.getString("telefono") + " | " + rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    // =========================
    // PRODUCTOS
    // =========================
    public List<InformeFila> listarProductos() {

        List<InformeFila> lista = new ArrayList<>();

        String sql = "SELECT nombre, precio, stock FROM Producto";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(new InformeFila(
                        rs.getString("nombre"),
                        "Precio: " + rs.getBigDecimal("precio") +
                                " | Stock: " + rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    // =========================
    // FACTURAS
    // =========================
    public List<InformeFila> listarFacturas() {

        List<InformeFila> lista = new ArrayList<>();

        String sql = """
            SELECT f.id_factura, f.fecha, f.total, c.nombre
            FROM Factura f
            JOIN Cliente c ON f.id_cliente = c.id_cliente
            ORDER BY f.id_factura DESC
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(new InformeFila(
                        "Factura #" + rs.getInt("id_factura"),
                        "Cliente: " + rs.getString("nombre") +
                                " | Total: " + rs.getBigDecimal("total") +
                                " | Fecha: " + rs.getTimestamp("fecha")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}