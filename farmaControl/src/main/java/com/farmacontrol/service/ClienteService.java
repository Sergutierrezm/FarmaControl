package com.farmacontrol.service;

import com.farmacontrol.dao.ClienteDAO;
import com.farmacontrol.model.Cliente;

import java.util.ArrayList;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    // =========================
    // REGISTRAR CLIENTE
    // =========================
    public void registrarCliente(Cliente c) {

        if (c == null) return;

        if (c.getNombre() == null || c.getNombre().isEmpty()) {
            System.out.println("❌ Nombre obligatorio");
            return;
        }

        if (c.getEmail() == null || !c.getEmail().contains("@")) {
            System.out.println("❌ Email inválido");
            return;
        }

        clienteDAO.insertar(c);
        System.out.println("✅ Cliente registrado correctamente");
    }

    // =========================
    // LISTAR CLIENTES
    // =========================
    public ArrayList<Cliente> obtenerClientes() {
        return clienteDAO.obtenerTodos();
    }

    // =========================
    // BUSCAR CLIENTE
    // =========================
    public Cliente buscarCliente(int id) {
        return clienteDAO.buscarPorId(id);
    }

    // =========================
    // ELIMINAR CLIENTE
    // =========================
    public void eliminarCliente(int id) {
        clienteDAO.eliminar(id);
    }

    // =========================
    // ACTUALIZAR CLIENTE
    // =========================
    public void actualizarCliente(Cliente c) {
        clienteDAO.actualizar(c);
    }
}