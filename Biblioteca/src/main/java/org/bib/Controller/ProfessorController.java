package org.bib.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import org.bib.entities.Professor;
import org.bib.dao.ProfessorDao;
import org.bib.dao.Dao;

public class ProfessorController {
    
    private Dao<Professor> dao;
    @FXML private TableView<Professor> TableProfessor;
    @FXML private TableColumn<Professor, String> nomeColumn;
    @FXML private TextField txtNome;

    public void initialize() {
        this.dao = new ProfessorDao(Professor.class);
        configurarTabela();
        atualizarTabela();
    }

    private void configurarTabela() {
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormacao()));
    }

    private void atualizarTabela(){
        TableProfessor.getItems().setAll(dao.findAll());
    }

    @FXML
    private void btnProfessor_salvar() {
        Professor professor = new Professor();
        professor.setNome(txtNome.getText());
        dao.create(professor);

        txtNome.clear();
        atualizarTabela();
    }

    @FXML
    private void btnProfessor_excluir() {
        Professor professor = TableProfessor.getSelectionModel().getSelectedItem();
        if (professor != null) {
            dao.delete(professor);
            atualizarTabela();
        }
    }
}
