package com.farmacontrol.dao;

import com.farmacontrol.model.Producto;
import com.farmacontrol.model.Proveedor;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductoDAO {

    // =========================
    // INSERTAR PRODUCTO
    // =========================
    public void insertar(Producto p) {

        String sql = "INSERT INTO Producto (nombre, descripcion, precio, stock, id_proveedor) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setBigDecimal(3, p.getPrecio());
            stmt.setInt(4, p.getStock());

            if (p.getProveedor() != null) {
                stmt.setInt(5, p.getProveedor().getIdProveedor());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setIdProducto(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar producto", e);
        }
    }

    // =========================
    // OBTENER TODOS
    // =========================
    public ArrayList<Producto> obtenerTodos() {

        ArrayList<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM Producto";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));

                Proveedor prov = new Proveedor();
                prov.setIdProveedor(rs.getInt("id_proveedor"));
                p.setProveedor(prov);

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener productos", e);
        }

        return lista;
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Producto buscarPorId(int id) {

        String sql = "SELECT * FROM Producto WHERE id_producto = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));

                Proveedor prov = new Proveedor();
                prov.setIdProveedor(rs.getInt("id_proveedor"));
                p.setProveedor(prov);

                return p;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto", e);
        }

        return null;
    }

    // =========================
    // ACTUALIZAR PRODUCTO
    // =========================
    public void actualizar(Producto p) {

        String sql = "UPDATE Producto SET nombre=?, descripcion=?, precio=?, stock=?, id_proveedor=? WHERE id_producto=?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setBigDecimal(3, p.getPrecio());
            stmt.setInt(4, p.getStock());

            if (p.getProveedor() != null) {
                stmt.setInt(5, p.getProveedor().getIdProveedor());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, p.getIdProducto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    // =========================
    // ACTUALIZAR STOCK (CLAVE PARA TU SISTEMA)
    // =========================
    public void actualizarStock(int idProducto, int nuevoStock) {

        String sql = "UPDATE Producto SET stock=? WHERE id_producto=?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idProducto);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar stock", e);
        }
    }

    // =========================
    // ELIMINAR PRODUCTO
    // =========================
    public void eliminar(int id) {

        String sql = "DELETE FROM Producto WHERE id_producto = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }
}