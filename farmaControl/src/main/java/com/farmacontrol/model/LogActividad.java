package com.farmacontrol.model;

import java.time.LocalDateTime;

public class LogActividad {

    private int idLog;
    private LocalDateTime fecha;
    private String tipoAccion;
    private String descripcion;
    private Usuario usuario;

    // Constructor vacío (pone fecha automática)
    public LogActividad() {
        this.fecha = LocalDateTime.now();
    }

    // Constructor sin ID (para INSERT en BD)
    public LogActividad(LocalDateTime fecha, String tipoAccion, String descripcion, Usuario usuario) {
        this.fecha = fecha;
        this.tipoAccion = tipoAccion;
        this.descripcion = descripcion;
        this.usuario = usuario;
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