package com.farmacontrol.dao;

import com.farmacontrol.model.DetalleFactura;
import com.farmacontrol.model.Producto;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;

public class DetalleFacturaDAO {

    // =========================
    // INSERTAR DETALLE
    // =========================
    public void insertar(DetalleFactura d, int idFactura) {

        String sql = "INSERT INTO DetalleFactura (id_factura, id_producto, cantidad, precio_unitario, subtotal) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idFactura);
            stmt.setInt(2, d.getProducto().getIdProducto());
            stmt.setInt(3, d.getCantidad());
            stmt.setBigDecimal(4, d.getPrecioUnitario());
            stmt.setBigDecimal(5, d.getSubtotal());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                d.setIdDetalle(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar detalle de factura");
            e.printStackTrace();
        }
    }

    // =========================
    // LISTAR DETALLES POR FACTURA
    // =========================
    public ArrayList<DetalleFactura> obtenerPorFactura(int idFactura) {

        ArrayList<DetalleFactura> lista = new ArrayList<>();

        String sql = "SELECT df.*, p.nombre, p.precio " +
                "FROM DetalleFactura df " +
                "JOIN Producto p ON df.id_producto = p.id_producto " +
                "WHERE df.id_factura = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFactura);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));

                DetalleFactura d = new DetalleFactura();
                d.setIdDetalle(rs.getInt("id_detalle"));
                d.setProducto(p);
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                d.setSubtotal(rs.getBigDecimal("subtotal"));

                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener detalles de factura");
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // ELIMINAR DETALLE
    // =========================
    public void eliminar(int idDetalle) {

        String sql = "DELETE FROM DetalleFactura WHERE id_detalle = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar detalle");
            e.printStackTrace();
        }
    }
}