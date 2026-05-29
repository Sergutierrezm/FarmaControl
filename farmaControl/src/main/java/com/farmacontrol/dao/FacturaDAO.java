package com.farmacontrol.dao;

import com.farmacontrol.model.*;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class FacturaDAO {

    private final DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();

    // =========================
    // INSERTAR FACTURA
    // =========================
    public void insertar(Factura factura) {

        String sql = "INSERT INTO Factura (fecha, total, id_cliente, id_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            factura.calcularTotal();

            stmt.setTimestamp(1, Timestamp.valueOf(factura.getFecha()));
            stmt.setBigDecimal(2, factura.getTotal());
            stmt.setInt(3, factura.getCliente().getIdCliente());
            stmt.setInt(4, factura.getUsuario().getIdUsuario());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                factura.setIdFactura(rs.getInt(1));
            }

            for (DetalleFactura d : factura.getDetalles()) {
                detalleDAO.insertar(d, factura.getIdFactura());
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Error al insertar factura", e);
        }
    }

    // =========================
    // OBTENER TODAS
    // =========================
    public ArrayList<Factura> obtenerTodas() {

        ArrayList<Factura> lista = new ArrayList<>();

        String sql = "SELECT * FROM Factura";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                f.setCliente(c);

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                f.setUsuario(u);

                f.setDetalles(detalleDAO.obtenerPorFactura(f.getIdFactura()));
                f.calcularTotal();

                lista.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Error al obtener facturas", e);
        }

        return lista;
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Factura buscarPorId(int id) {

        String sql = "SELECT * FROM Factura WHERE id_factura = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                f.setCliente(c);

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                f.setUsuario(u);

                f.setDetalles(detalleDAO.obtenerPorFactura(f.getIdFactura()));
                f.calcularTotal();

                return f;
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Error al buscar factura", e);
        }

        return null;
    }

    // =========================
    // ELIMINAR
    // =========================
    public void eliminar(int id) {

        String sql = "DELETE FROM Factura WHERE id_factura = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("❌ Error al eliminar factura", e);
        }
    }
}