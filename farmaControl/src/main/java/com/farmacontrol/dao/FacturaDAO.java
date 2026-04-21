package com.farmacontrol.dao;

import com.farmacontrol.model.*;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class FacturaDAO {

    private ArrayList<Factura> listaFacturas = new ArrayList<>();

    // =========================
    // MÉTODOS EN MEMORIA (PRUEBAS)
    // =========================

    public void agregarFactura(Factura f) {
        listaFacturas.add(f);
    }

    public ArrayList<Factura> obtenerFacturas() {
        return listaFacturas;
    }

    public Factura buscarFacturaPorId(int id) {
        for (Factura f : listaFacturas) {
            if (f.getIdFactura() == id) {
                return f;
            }
        }
        return null;
    }

    public void eliminarFactura(int id) {
        listaFacturas.removeIf(f -> f.getIdFactura() == id);
    }

    // =========================
    // MÉTODOS BASE DE DATOS (IMPORTANTE)
    // =========================

    public void guardarEnBD(Factura factura) {

        String sql = "INSERT INTO Factura (fecha, total, id_cliente, id_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(factura.getFecha()));
            stmt.setDouble(2, factura.calcularTotal());
            stmt.setInt(3, factura.getCliente().getIdCliente());
            stmt.setInt(4, factura.getUsuario().getIdUsuario());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                factura.setIdFactura(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Factura> obtenerTodasBD() {

        ArrayList<Factura> facturas = new ArrayList<>();

        String sql = "SELECT * FROM Factura";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

                // OJO: aquí luego conectarías ClienteDAO y UsuarioDAO
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                f.setCliente(c);

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                f.setUsuario(u);

                facturas.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facturas;
    }
}