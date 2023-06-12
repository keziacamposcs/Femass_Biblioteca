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



    //Aqui
    @FXML
    private void On_Key_Pressed_TableLeitor() {
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableLeitor() {
        exibirDados();
    }
    private Professor getProfessorByFormacao(String formacao) {
        for (Professor professor : cboFormacao.getItems()) {
            if (professor.getFormacao().equals(formacao)) {
                return professor;
            }
        }
        return null;
    }
    

    private void exibirDados() {
        Leitor leitor = TableLeitor.getSelectionModel().getSelectedItem();
        if (leitor == null) return;    
        txtTelefone.setText(leitor.getTelefone());
        txtEmail.setText(leitor.getEmail());
        txtNome.setText(leitor.getNome());
    
        if (leitor instanceof Professor) {
            radioProfessor.setSelected(true);
            cboFormacao.setDisable(false);
            txtMatricula.setDisable(true);
            Professor professor = getProfessorByFormacao(((Professor) leitor).getFormacao());
            cboFormacao.getSelectionModel().select(professor);
        } else if (leitor instanceof Aluno) {
            radioAluno.setSelected(true);
            cboFormacao.setDisable(true);
            txtMatricula.setDisable(false);
            txtMatricula.setText(((Aluno) leitor).getMatricula());
        } else {
            radioProfessor.setSelected(false);
            cboFormacao.setDisable(true);
            txtMatricula.setDisable(true);
        }
    
        cboUsuario.getSelectionModel().select(leitor.getUsuario());
    }
}
