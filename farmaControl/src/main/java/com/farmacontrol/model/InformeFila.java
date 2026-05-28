package com.farmacontrol.model;

public class InformeFila {

    private String concepto;
    private String valor;

    public InformeFila(String concepto, String valor) {
        this.concepto = concepto;
        this.valor = valor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}