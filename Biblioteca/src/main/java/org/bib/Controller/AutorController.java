package org.bib.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import org.bib.dao.AutorDao;
import org.bib.entities.Autor;

public class AutorController {

    private AutorDao dao;

    @FXML
    private TableView<Autor> TableAutor;
    @FXML
    private TableColumn<Autor, Long> idColumn;
    @FXML
    private TableColumn<Autor, String> nomeColumn;
    @FXML
    private TableColumn<Autor, String> sobrenomeColumn;
    
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtSobrenome;
    @FXML
    private Button btnAutor_salvar;
    @FXML
    private Button btnAutor_excluir;

    public void initialize() {
        this.dao = new AutorDao(Autor.class);
        configurarTabela();
        atualizarTabela();
    }

    private void configurarTabela() {
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIdAutor()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeAutor()));
        sobrenomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSobrenomeAutor()));
    }

    private void atualizarTabela() {
        TableAutor.getItems().setAll(dao.findAll());
    }

    @FXML
    private void btnAutor_salvar(ActionEvent event)
    {
        Autor autorSelecionado = TableAutor.getSelectionModel().getSelectedItem();
        if (autorSelecionado != null)
        {
            String nome = txtNome.getText();
            String sobrenome = txtSobrenome.getText();

            autorSelecionado.setNomeAutor(nome);
            autorSelecionado.setSobrenomeAutor(sobrenome);
            dao.update(autorSelecionado);

            clearFields();
            atualizarTabela();
        }
    }

    @FXML
    private void btnAutor_excluir() {
        Autor autor = TableAutor.getSelectionModel().getSelectedItem();
        if (autor != null) {
            dao.delete(autor);
            atualizarTabela();
        }
    }

    @FXML
    private void On_Mouse_Clicked_TableAutor() {
        exibirDados();
    }

    @FXML
    private void On_Key_Pressed_TableAutor() {
        exibirDados();
    }

    private void exibirDados() {
        Autor autor = TableAutor.getSelectionModel().getSelectedItem();
        if (autor != null) {
            txtNome.setText(autor.getNomeAutor());
            txtSobrenome.setText(autor.getSobrenomeAutor());
        }
    }

    private void clearFields() {
        txtNome.clear();
        txtSobrenome.clear();
    }
}
