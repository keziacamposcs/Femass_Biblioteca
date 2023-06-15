package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

import org.bib.dao.Dao;
import org.bib.entities.Aluno;
import org.bib.entities.Leitor;
import org.bib.entities.Professor;
import org.bib.entities.Usuario;

public class LeitorController {

    private Dao<Leitor> daoLeitor;
    private Dao<Usuario> daoUsuario;
    private Dao<Professor> daoFormacao;

    @FXML
    private AnchorPane leitoranchorPane;
    @FXML
    private Button btnProfessor;

    @FXML 
    private TableView<Leitor> TableLeitor;
    @FXML 
    private TableColumn<Leitor, Long> idColumn;
    @FXML 
    private TableColumn<Leitor, String> nomeColumn;
    @FXML 
    private TableColumn<Leitor, String> telefoneColumn;
    @FXML 
    private TableColumn<Leitor, String> emailColumn;
    @FXML 
    private TableColumn<Leitor, String> usuarioColumn;

    @FXML 
    private TextField txtNome;
    @FXML 
    private TextField txtTelefone;
    @FXML 
    private TextField txtEmail;
    @FXML 
    private ComboBox<Usuario> cboUsuario;
    @FXML 
    private RadioButton radioProfessor;
    @FXML 
    private ComboBox<Professor> cboFormacao;
    @FXML 
    private RadioButton radioAluno;
    @FXML 
    private TextField txtMatricula;

    private ToggleGroup group;

    public void initialize() {
        daoLeitor = new Dao<>(Leitor.class);
        daoUsuario = new Dao<>(Usuario.class);
        daoFormacao = new Dao<>(Professor.class);

        // ComboBox Usuario
        cboUsuario.setItems(FXCollections.observableArrayList(daoUsuario.findAll()));

        cboUsuario.setConverter(new StringConverter<Usuario>() {
            @Override
            public String toString(Usuario usuario) {
                if (usuario == null) {
                    return null;
                } else {
                    return usuario.getLogin();
                }
            }
            @Override
            public Usuario fromString(String string) {
                return daoUsuario.findAll().stream().filter(user -> 
                    user.getLogin().equals(string)).findFirst().orElse(null);
            }
        });
        // Fim - ComboBox Usuario

        // ComboBox Formação
        cboFormacao.setItems(FXCollections.observableArrayList(daoFormacao.findAll()));

        cboFormacao.setConverter(new StringConverter<Professor>() {
            @Override
            public String toString(Professor professor) {
                if (professor == null) {
                    return null;
                } else {
                    return professor.getFormacao();
                }
            }
            @Override
            public Professor fromString(String string) {
                return daoFormacao.findAll().stream().filter(professor -> 
                    professor.getFormacao().equals(string)).findFirst().orElse(null);
            }
        });
        // Fim - ComboBox Formação


        // Radios
        group = new ToggleGroup();
        this.radioProfessor.setToggleGroup(group);
        this.radioAluno.setToggleGroup(group);
        radioProfessor.setOnAction(e -> {
            if(radioProfessor.isSelected()){
                cboFormacao.setDisable(false);
                txtMatricula.clear(); 
                txtMatricula.setDisable(true);
            }
        });
        radioAluno.setOnAction(e -> {
            if(radioAluno.isSelected()){
                cboFormacao.setDisable(true);
                cboFormacao.getSelectionModel().clearSelection();
                txtMatricula.setDisable(false);
            }
        });
        // Fim - Radios

        // Configuração das colunas da tabela
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdLeitor()));
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeLeitor()));
        telefoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        usuarioColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getUsuario() != null) {
                return new SimpleStringProperty(cellData.getValue().getUsuario().getLogin());
            } else {
                return new SimpleStringProperty("");
            }
        });
        
        // Carregar dados na tabela
        TableLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));
    }

    @FXML
    private void btnLeitor_salvar(ActionEvent event) {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        Usuario usuario = cboUsuario.getSelectionModel().getSelectedItem();

        if (radioProfessor.isSelected()) {
            Professor professor = TableLeitor.getSelectionModel().getSelectedItem() instanceof Professor
                    ? (Professor) TableLeitor.getSelectionModel().getSelectedItem()
                    : new Professor();
            professor.setNomeLeitor(nome);
            professor.setTelefone(telefone);
            professor.setEmail(email);
            professor.setUsuario(usuario);
            professor.setFormacao(cboFormacao.getSelectionModel().getSelectedItem().getFormacao());

            if (professor.getIdLeitor() != null) {
                daoLeitor.update(professor);
            } else {
                daoLeitor.create(professor);
            }
        } else if (radioAluno.isSelected()) {
            Aluno aluno = TableLeitor.getSelectionModel().getSelectedItem() instanceof Aluno
                    ? (Aluno) TableLeitor.getSelectionModel().getSelectedItem()
                    : new Aluno();
            aluno.setNomeLeitor(nome);
            aluno.setTelefone(telefone);
            aluno.setEmail(email);
            aluno.setUsuario(usuario);
            aluno.setMatricula(txtMatricula.getText());

            if (aluno.getIdLeitor() != null) {
                daoLeitor.update(aluno);
            } else {
                daoLeitor.create(aluno);
            }
        } else {
            // Se nenhum RadioButton estiver selecionado, não faça nada ou exiba uma mensagem de erro.
            return;
        }

        // Limpar campos após a atualização
        clearFields();

        // Atualizar tabela
        TableLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));
        TableLeitor.refresh();
    }


    @FXML
    private void btnLeitor_excluir(ActionEvent event) {
        Leitor leitorSelecionado = TableLeitor.getSelectionModel().getSelectedItem();
        if (leitorSelecionado != null) {
            daoLeitor.delete(leitorSelecionado.getIdLeitor());
            TableLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));
        }
    }

    // Botão para cadastrar mais Formações
    @FXML
    private void professor_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Professor.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Fim - Botão para cadastrar mais Formações
    

    @FXML
    private void On_Key_Pressed_TableLeitor() {
        exibirDados();
    }
    
    @FXML
    private void On_Mouse_Clicked_TableLeitor() {
        exibirDados();
    }
    

    private void exibirDados() {
        Leitor leitor = TableLeitor.getSelectionModel().getSelectedItem();
        if (leitor == null) return;    
        txtTelefone.setText(leitor.getTelefone());
        txtEmail.setText(leitor.getEmail());
        txtNome.setText(leitor.getNomeLeitor());
    
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


    private Professor getProfessorByFormacao(String formacao) {
        return cboFormacao.getItems().stream()
                .filter(professor -> professor.getFormacao().equals(formacao))
                .findFirst().orElse(null);
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
