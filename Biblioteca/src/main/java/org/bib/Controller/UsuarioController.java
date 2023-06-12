package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.bib.dao.Dao;
import org.bib.dao.UsuarioDao;
import org.bib.entities.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {

    @FXML private TableView<Usuario> TableUsuario;

    @FXML private TableColumn<Usuario, Integer> idColumn;
    @FXML private TableColumn<Usuario, String> loginColumn;
    @FXML private TableColumn<Usuario, String> senhaColumn;

    @FXML private Button btnUsuario_excluir;
    @FXML private Button btnUsuario_salvar;

    @FXML private TextField txtLogin;
    @FXML private TextField txtSenha;

    private Dao<Usuario> dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dao = new UsuarioDao(Usuario.class);

        configurarTabela();
        atualizarTabela();
    }

    private void configurarTabela() {
        loginColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(cellData.getValue().getLogin()));
        senhaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(cellData.getValue().getSenha()));
    }
    private void atualizarTabela() {
        TableUsuario.getItems().setAll(dao.findAll());
    }

    @FXML
    private void btnUsuario_salvar() {
        Usuario usuario = new Usuario();

        usuario.setLogin(txtLogin.getText());
        usuario.setSenha(txtSenha.getText());

        dao.create(usuario);

        txtLogin.clear();
        txtSenha.clear();

        atualizarTabela();
    }

    @FXML
    private void btnUsuario_excluir() {
        Usuario usuario = TableUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            dao.delete(usuario);
            atualizarTabela();
        }
    }
}