package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.bib.dao.Dao;
import org.bib.dao.GeneroDao;
import org.bib.entities.Genero;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneroController implements Initializable {

    @FXML private TableView<Genero> TableGenero;
    @FXML private TableColumn<Genero, Integer> idColumn;
    @FXML private TableColumn<Genero, String> nomeColumn;
    
    @FXML private Button btnGenero_excluir;
    @FXML private Button btnGenero_salvar;

    @FXML private TextField txtNome;

    private Dao<Genero> dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dao = new GeneroDao(Genero.class);

        configurarTabela();
        atualizarTabela();
    }

    private void configurarTabela() {
        nomeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(cellData.getValue().getNomeGenero()));
    }


    private void atualizarTabela() {
        TableGenero.getItems().setAll(dao.findAll());
    }

    @FXML
    private void btnGenero_salvar() {
        Genero genero = new Genero();
        genero.setNomeGenero(txtNome.getText());
        dao.create(genero);

        txtNome.clear();
        atualizarTabela();
    }

    @FXML
    private void btnGenero_excluir() {
        Genero genero = TableGenero.getSelectionModel().getSelectedItem();
        if (genero != null) {
            dao.delete(genero);
            atualizarTabela();
        }
    }
}
