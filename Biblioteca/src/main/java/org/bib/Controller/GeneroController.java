package org.bib.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import org.bib.dao.GeneroDao;
import org.bib.entities.Genero;

public class GeneroController {

    private GeneroDao dao;

    @FXML
    private TableView<Genero> TableGenero;
    @FXML
    private TableColumn<Genero, Long> idColumn;
    @FXML
    private TableColumn<Genero, String> nomeColumn;

    @FXML
    private TextField txtNome;

    @FXML
    private Button btnGenero_salvar;
    @FXML
    private Button btnGenero_excluir;

    public void initialize() {
        this.dao = new GeneroDao(Genero.class);
        configurarTabela();
        atualizarTabela();

        TableGenero.refresh();
    }
    private void atualizarTabela() {
        TableGenero.getItems().setAll(dao.findAll());
    }
    

    private void configurarTabela() {
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIdGenero()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeGenero()));
    }
    

    @FXML
    private void btnGenero_salvar(ActionEvent event) {
        Genero generoSelecionado = TableGenero.getSelectionModel().getSelectedItem();
        String nome = txtNome.getText();
        
        if (generoSelecionado != null) {
            generoSelecionado.setNomeGenero(nome);
            dao.update(generoSelecionado);
        } else {
            Genero novoGenero = new Genero();
            novoGenero.setNomeGenero(nome);
            dao.create(novoGenero);
        }
        
        clearFields();
        TableGenero.refresh();
    }


    @FXML
    private void btnGenero_excluir(ActionEvent event) {
        Genero genero = TableGenero.getSelectionModel().getSelectedItem();
        if (genero != null)
        {
            dao.delete(genero);
            TableGenero.refresh();
        }
    }

    @FXML
    private void On_Key_Pressed_TableGenero()
    {
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableGenero()
    {
        exibirDados();
    }

    private void exibirDados()
    {
        txtNome.setText(TableGenero.getSelectionModel().getSelectedItem().getNomeGenero());
    }

    private void clearFields()
    {
        txtNome.clear();
    }
}
