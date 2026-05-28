package com.farmacontrol.dao;

import com.farmacontrol.model.LogActividad;
import com.farmacontrol.util.ConexionBD;

import java.sql.*;

public class LogActividadDAO {

    public void insertar(LogActividad log) {

        String sql = "INSERT INTO LogActividad (fecha, tipo_accion, descripcion, id_usuario) " +
                     "VALUES (NOW(), ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, log.getTipoAccion());
            stmt.setString(2, log.getDescripcion());
            stmt.setInt(3, log.getUsuario().getIdUsuario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar log de actividad", e);
        }
    }
}
