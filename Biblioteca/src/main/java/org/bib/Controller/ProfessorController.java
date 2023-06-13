package org.bib.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import org.bib.entities.Professor;
import org.bib.dao.ProfessorDao;
import org.bib.dao.Dao;

public class ProfessorController {

    private Dao<Professor> dao;
    @FXML
    private TableView<Professor> TableProfessor;
    @FXML
    private TableColumn<Professor, String> formacaoColumn;
    @FXML
    private TextField txtFormacao;

    public void initialize() {
        this.dao = new ProfessorDao(Professor.class);
        configurarTabela();
        atualizarTabela();
    }

    private void configurarTabela() {
        formacaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormacao()));
    }

    private void atualizarTabela() {
        TableProfessor.getItems().setAll(dao.findAll());
    }

    @FXML
    private void btnProfessor_salvar() {
        Professor professor = new Professor();
        professor.setFormacao(txtFormacao.getText());
        dao.create(professor);

        txtFormacao.clear();
        atualizarTabela();
    }

    @FXML
    private void btnProfessor_excluir() {
        Professor professor = TableProfessor.getSelectionModel().getSelectedItem();
        if (professor != null) {
            dao.delete(professor.getIdLeitor());
            atualizarTabela();
        }
    }

    @FXML
    private void On_Key_Pressed_TableProfessor() {
        clearFields();
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableProfessor() {
        clearFields();
        exibirDados();
    }

    private void exibirDados() {
        Professor professor = TableProfessor.getSelectionModel().getSelectedItem();
        if (professor == null) return;
        txtFormacao.setText(professor.getFormacao());
    }

    private void clearFields() {
        txtFormacao.clear();
    }
}
