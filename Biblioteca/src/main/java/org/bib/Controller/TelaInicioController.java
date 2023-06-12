package org.bib.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicioController {

    // Botao
    @FXML
    private Button btnLeitor;
    @FXML
    private Button btnLivro;
    @FXML
    private Button btnUsuario;
    @FXML
    private Button btnEmprestimo;
    // Fim - Botao 

    // AnchorPane
    @FXML
    private AnchorPane leitoranchorPane;
    @FXML
    private AnchorPane livroanchorPane;
    @FXML
    private AnchorPane usuarioanchorPane;
    @FXML
    private AnchorPane emprestimoanchorPane;

    @FXML
    private AnchorPane telaincioanchor;
    // Fim - AnchorPane

    @FXML
    public void leitor_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Leitor.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void livro_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Livro.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) telaincioanchor.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void usuario_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Usuario.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) telaincioanchor.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void emprestimo_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Emprestimo.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) telaincioanchor.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
