package com.farmacontrol.controller;

import com.farmacontrol.dao.InformeDAO;
import com.farmacontrol.model.InformeFila;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.FileWriter;

import java.io.PrintWriter;

import java.util.List;
import javafx.scene.control.Alert;

public class InformesController {

    @FXML
    private TableView<InformeFila> tablaResultados;
    @FXML
    private TableColumn<InformeFila, String> col1;
    @FXML
    private TableColumn<InformeFila, String> col2;

    private final InformeDAO dao = new InformeDAO();

    @FXML
    public void initialize() {

        col1.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        col2.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    // =========================
    // CLIENTES
    // =========================
    @FXML
    public void informeClientes() {

        tablaResultados.setItems(
                FXCollections.observableArrayList(dao.listarClientes())
        );
    }

    // =========================
    // PRODUCTOS
    // =========================
    @FXML
    public void informeProductos() {

        tablaResultados.setItems(
                FXCollections.observableArrayList(dao.listarProductos())
        );
    }

    // =========================
    // FACTURAS
    // =========================
    @FXML
    public void informeFacturas() {

        tablaResultados.setItems(
                FXCollections.observableArrayList(dao.listarFacturas())
        );
    }


    // =========================
    // VOLVER
    // =========================
    @FXML
    public void volver() {

        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/view/menu.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) tablaResultados.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void descargarInforme() {

        try {

            FileWriter fileWriter = new FileWriter("informe.csv");
            PrintWriter pw = new PrintWriter(fileWriter);

            pw.println("Concepto,Valor");

            for (InformeFila fila : tablaResultados.getItems()) {
                pw.println(fila.getConcepto() + "," + fila.getValor());
            }

            pw.close();

            // ✅ MENSAJE EN PANTALLA
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Descarga completada");
            alert.setHeaderText(null);
            alert.setContentText("El informe se ha descargado correctamente como informe.csv");
            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo descargar el informe");
            alert.showAndWait();

            e.printStackTrace();
        }
    }

}