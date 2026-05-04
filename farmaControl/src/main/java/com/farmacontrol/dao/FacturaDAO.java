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

            // IMPORTANTE: asegurar total actualizado
            factura.calcularTotal();

            stmt.setTimestamp(1, Timestamp.valueOf(factura.getFecha()));
            stmt.setDouble(2, factura.getTotal());
            stmt.setInt(3, factura.getCliente().getIdCliente());
            stmt.setInt(4, factura.getUsuario().getIdUsuario());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                factura.setIdFactura(rs.getInt(1));
            }

            // =========================
            // INSERTAR DETALLES
            // =========================
            for (DetalleFactura d : factura.getDetalles()) {
                detalleDAO.insertar(d, factura.getIdFactura());
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar factura");
            e.printStackTrace();
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

                // 🔥 IMPORTANTE: cargar detalles reales
                f.setDetalles(detalleDAO.obtenerPorFactura(f.getIdFactura()));

                // recalcular total real desde detalles
                f.calcularTotal();

                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener facturas");
            e.printStackTrace();
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

                // 🔥 cargar detalles reales
                f.setDetalles(detalleDAO.obtenerPorFactura(f.getIdFactura()));
                f.calcularTotal();

                return f;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar factura");
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // ELIMINAR FACTURA
    // =========================
    public void eliminar(int id) {

        String sql = "DELETE FROM Factura WHERE id_factura = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar factura");
            e.printStackTrace();
        }
    }
}