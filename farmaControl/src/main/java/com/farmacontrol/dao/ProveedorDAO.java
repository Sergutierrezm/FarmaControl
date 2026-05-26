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
}