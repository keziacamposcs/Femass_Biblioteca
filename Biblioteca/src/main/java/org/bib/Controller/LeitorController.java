package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.bib.dao.Dao;
import org.bib.dao.LeitorDao;
import org.bib.dao.UsuarioDao;
import org.bib.entities.Aluno;
import org.bib.entities.Leitor;
import org.bib.entities.Professor;
import org.bib.entities.Usuario;

public class LeitorController {

    private Dao<Leitor> daoLeitor;
    private Dao<Usuario> daoUsuario;
    private Dao<Professor> daoFormacao;

    @FXML private TableView<Leitor> TableLeitor;
    @FXML private TableColumn<Leitor, Long> idColumn;
    @FXML private TableColumn<Leitor, String> nomeColumn;
    @FXML private TableColumn<Leitor, String> telefoneColumn;
    @FXML private TableColumn<Leitor, String> emailColumn;
    @FXML private TableColumn<Leitor, Usuario> usuarioColumn;

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<Usuario> cboUsuario;
    @FXML private RadioButton radioProfessor;
    @FXML private ComboBox<Professor> cboFormacao;
    @FXML private RadioButton radioAluno;
    @FXML private TextField txtMatricula;

    public void initialize() {
        daoLeitor = new Dao<>(Leitor.class);
        daoUsuario = new Dao<>(Usuario.class);
        daoFormacao = new Dao<>(Professor.class);

        // Configuração das colunas da tabela
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        telefoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        usuarioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsuario()));

        // Carregar dados na tabela
        TableLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));

        // Carregar dados no ComboBox de Usuário
        cboUsuario.setItems(FXCollections.observableArrayList(daoUsuario.findAll()));

        // Desativar ComboBox e TextField do Aluno no início
        cboFormacao.setDisable(true);
        txtMatricula.setDisable(true);
    }

    @FXML
    private void btnLeitor_salvar(ActionEvent event) {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        Usuario usuario = cboUsuario.getSelectionModel().getSelectedItem();
    
        if (radioProfessor.isSelected()) {
            Professor professor = new Professor();
            professor.setNome(nome);
            professor.setTelefone(telefone);
            professor.setEmail(email);
            professor.setUsuario(usuario);
            professor.setFormacao(cboFormacao.getSelectionModel().getSelectedItem().getFormacao());
            daoLeitor.create(professor);
        } else if (radioAluno.isSelected()) {
            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setTelefone(telefone);
            aluno.setEmail(email);
            aluno.setUsuario(usuario);
            aluno.setMatricula(txtMatricula.getText());
            daoLeitor.create(aluno);
        } else {
            Leitor leitor = new Leitor();
            leitor.setNome(nome);
            leitor.setTelefone(telefone);
            leitor.setEmail(email);
            leitor.setUsuario(usuario);
            daoLeitor.create(leitor);
        }
    
        // Limpar campos após salvar
        clearFields();
    
        // Atualizar tabela
        TableLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));
    }
    



    @FXML
    private void btnLeitor_excluir(ActionEvent event) {
        Leitor leitorSelecionado = TableLeitor.getSelectionModel().getSelectedItem();
        if (leitorSelecionado != null) {
            daoLeitor.delete(leitorSelecionado.getId());
            TableLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));
        }
    }

    @FXML
    private void radioProfessorSelected(ActionEvent event) {
        cboFormacao.setDisable(false);
        txtMatricula.setDisable(true);
    }

    @FXML
    private void radioAlunoSelected(ActionEvent event) {
        cboFormacao.setDisable(true);
        txtMatricula.setDisable(false);
    }

    @FXML
    private void btnProfessor(ActionEvent event) {
        // Navegar para a página Professor.fxml
        // Implemente essa lógica de acordo com o seu sistema
    }

    private void clearFields() {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        cboUsuario.getSelectionModel().clearSelection();
        radioProfessor.setSelected(false);
        cboFormacao.getSelectionModel().clearSelection();
        radioAluno.setSelected(false);
        txtMatricula.clear();
    }
}
