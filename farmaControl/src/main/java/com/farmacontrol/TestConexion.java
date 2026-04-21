package com.farmacontrol;

import com.farmacontrol.util.ConexionBD;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {

        try (Connection conn = ConexionBD.getConnection()) {

            if (conn != null) {
                System.out.println("✅ CONEXIÓN OK a FarmaControl");
            } else {
                System.out.println("❌ Conexión null");
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR DE CONEXIÓN");
            e.printStackTrace();
        }
    }
}