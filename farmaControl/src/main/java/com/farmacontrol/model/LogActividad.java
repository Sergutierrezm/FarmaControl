package com.farmacontrol.model;

import java.time.LocalDateTime;

public class LogActividad {

    private int idLog;
    private LocalDateTime fecha;
    private String tipoAccion;
    private String descripcion;
    private Usuario usuario; // relación con Usuario

    // Constructor vacío
    public LogActividad() {
    }

    // Constructor completo
    public LogActividad(int idLog, LocalDateTime fecha, String tipoAccion, String descripcion, Usuario usuario) {
        this.idLog = idLog;
        this.fecha = fecha;
        this.tipoAccion = tipoAccion;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}