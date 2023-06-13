package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import org.bib.dao.Dao;
import org.bib.entities.Usuario;

public class UsuarioController {
    @FXML
    private AnchorPane usuarioanchorPane;
    
    private Dao<Usuario> daoUsuario;

    @FXML 
    private TableView<Usuario> TableUsuario;
    @FXML 
    private TableColumn<Usuario, Long> idColumn;
    @FXML 
    private TableColumn<Usuario, String> loginColumn;
    @FXML 
    private TableColumn<Usuario, String> senhaColumn;

    @FXML 
    private TextField txtLogin;
    @FXML 
    private TextField txtSenha;

    public void initialize() {
        daoUsuario = new Dao<>(Usuario.class);

        // Configuração das colunas da tabela
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdUsuario()));
        loginColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        senhaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSenha()));

        // Carregar dados na tabela
        TableUsuario.setItems(FXCollections.observableArrayList(daoUsuario.findAll()));
    }

    @FXML
    private void btnUsuario_salvar(ActionEvent event) {
        String login = txtLogin.getText();
        String senha = txtSenha.getText();

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        daoUsuario.create(usuario);

        // Limpar campos após salvar
        clearFields();

        // Atualizar tabela
        TableUsuario.setItems(FXCollections.observableArrayList(daoUsuario.findAll()));
    }

    @FXML
    private void btnUsuario_excluir(ActionEvent event) {
        Usuario usuarioSelecionado = TableUsuario.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado != null) {
            daoUsuario.delete(usuarioSelecionado.getIdUsuario());
            TableUsuario.setItems(FXCollections.observableArrayList(daoUsuario.findAll()));
        }
    }

    @FXML
    private void btnLeitor_onAction(ActionEvent event) {
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
    private void On_Key_Pressed_TableUsuario() {
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableUsuario() {
        exibirDados();
    }
    
    private void exibirDados() {
        txtLogin.setText(TableUsuario.getSelectionModel().getSelectedItem().getLogin());
        txtSenha.setText(TableUsuario.getSelectionModel().getSelectedItem().getSenha());
    }

    private void clearFields() {
        txtLogin.clear();
        txtSenha.clear();
    }
}
