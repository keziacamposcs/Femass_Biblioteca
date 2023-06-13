package org.bib.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import org.bib.entities.Professor;
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
        dao = new Dao<>(Professor.class);

        formacaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormacao()));
        
        TableProfessor.setItems(FXCollections.observableArrayList(dao.findAll()));
    }

    @FXML
    private void btnProfessor_salvar(ActionEvent event) {
        String formacao = txtFormacao.getText();
        
        Professor professorSelecionado = TableProfessor.getSelectionModel().getSelectedItem();
        
        if (professorSelecionado == null) {
            // Nenhum professor selecionado, criar um novo objeto
            Professor novoProfessor = new Professor();
            novoProfessor.setFormacao(formacao);
            dao.create(novoProfessor);
        } else {
            // Professor selecionado, atualizar o objeto existente
            professorSelecionado.setFormacao(formacao);
            dao.update(professorSelecionado);
        }
        
        clearFields();

        // Atualizar tabela
        TableProfessor.setItems(FXCollections.observableArrayList(dao.findAll()));
        TableProfessor.refresh();
    }



    @FXML
    private void btnProfessor_excluir(ActionEvent event)
    {
        Professor professorSelecionado = TableProfessor.getSelectionModel().getSelectedItem();
        if (professorSelecionado != null) {
            dao.delete(professorSelecionado.getIdLeitor());
            TableProfessor.setItems(FXCollections.observableArrayList(dao.findAll()));
        }
    }

    @FXML
    private void On_Key_Pressed_TableProfessor()
    {
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableProfessor()
    {
        exibirDados();
    }

    private void exibirDados()
    {
        Professor professor = TableProfessor.getSelectionModel().getSelectedItem();
        if (professor == null) return;
        txtFormacao.setText(professor.getFormacao());
    }

    private void clearFields()
    {
        txtFormacao.clear();
    }
}
