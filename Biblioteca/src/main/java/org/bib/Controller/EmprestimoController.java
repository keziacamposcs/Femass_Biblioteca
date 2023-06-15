package org.bib.Controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import org.bib.dao.CopiaDao;
import org.bib.dao.EmprestimoDao;
import org.bib.dao.LeitorDao;
import org.bib.entities.Aluno;
import org.bib.entities.Copia;
import org.bib.entities.Emprestimo;
import org.bib.entities.Leitor;
import org.bib.entities.Livro;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmprestimoController {

    @FXML
    private AnchorPane emprestimoanchorPane;

    private EmprestimoDao daoEmprestimo;
    private CopiaDao daoCopia;
    private LeitorDao daoLeitor;


    private static final int MAX_EMPRESTIMOS_ATIVOS = 5;
    private static final int PRAZO_DEVOLUCAO_ALUNO = 5;  
    private static final int PRAZO_DEVOLUCAO_PROFESSOR = 30;


    // Tabela
    @FXML
    private TableView<Emprestimo> TableEmprestimo;
    @FXML
    private TableColumn<Emprestimo, LocalDate> dataColumn;
    @FXML
    private TableColumn<Emprestimo, LocalDate> dataPrevistaColumn;
    @FXML
    private TableColumn<Emprestimo, LocalDate> dataEntregaColumn;
    @FXML
    private TableColumn<Emprestimo, String> copiaColumn;
    @FXML
    private TableColumn<Emprestimo, String> leitorColumn;
    @FXML
    private TableColumn<Emprestimo, String> statusColumn;
    // Fim - Tabela

    // Campos
    @FXML
    private DatePicker txtData;
    @FXML
    private DatePicker txtDataPrevista;
    @FXML
    private DatePicker txtDataEntrega;
    @FXML
    private ComboBox<Copia> cboCopia;
    @FXML
    private ComboBox<Leitor> cboLeitor;
    // Fim - Campos

    // Botões
    @FXML
    private Button btnEmprestimo_salvar;
    @FXML
    private Button btnEmprestimo_excluir;
    // Fim - Botões


    public void initialize() {
        this.daoEmprestimo = new EmprestimoDao(Emprestimo.class);
        this.daoCopia = new CopiaDao(Copia.class);
        this.daoLeitor = new LeitorDao(Leitor.class);
            
        cboCopia.setItems(FXCollections.observableArrayList(daoCopia.findAll()));
        cboLeitor.setItems(FXCollections.observableArrayList(daoLeitor.findAll()));

        configurarTabela();
    }

    private void configurarTabela()
    {
        dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        dataPrevistaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataPrevistaEntrega()));
        dataEntregaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataEntrega()));
        
        copiaColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCopia() != null) {
                return new SimpleStringProperty(cellData.getValue().getCopia().getLivro().getNomeLivro());
            } else {
                return new SimpleStringProperty("");
            }
        });

        leitorColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getLeitor() != null) {
                return new SimpleStringProperty(cellData.getValue().getLeitor().getNomeLeitor());
            } else {
                return new SimpleStringProperty("");
            }
        });
        
        // Carrega empréstimos na tabela
        TableEmprestimo.setItems(FXCollections.observableArrayList(daoEmprestimo.findAll()));
    }


    @FXML
    private void On_Key_Pressed_TableEmprestimo() {
        exibirDados();
    }
    
    @FXML
    private void On_Mouse_Clicked_TableEmprestimo() {
        exibirDados();
    }

    private void exibirDados()
    {
        Emprestimo emprestimoSelecionado = TableEmprestimo.getSelectionModel().getSelectedItem();
        if (emprestimoSelecionado != null)
        {
            txtData.setValue(emprestimoSelecionado.getData());
            txtDataPrevista.setValue(emprestimoSelecionado.getDataPrevistaEntrega());
            txtDataEntrega.setValue(emprestimoSelecionado.getDataEntrega());
            cboCopia.setValue(emprestimoSelecionado.getCopia());
            cboLeitor.setValue(emprestimoSelecionado.getLeitor());
        }
    }
/*
    @FXML
    private void btnEmprestimo_salvar(ActionEvent event)
    {
        Emprestimo emprestimo;
        Emprestimo emprestimoSelecionado = TableEmprestimo.getSelectionModel().getSelectedItem();
        
        if (emprestimoSelecionado == null)
        {
            emprestimo = new Emprestimo();
        }
        else
        {
            emprestimo = emprestimoSelecionado;
        }

        emprestimo.setData(txtData.getValue());
        emprestimo.setDataPrevistaEntrega(txtDataPrevista.getValue());
        if(txtDataEntrega.getValue() != null)
        {
            emprestimo.setDataEntrega(txtDataEntrega.getValue());
        }

        emprestimo.setCopia(cboCopia.getValue());
        emprestimo.setLeitor(cboLeitor.getValue());

        daoEmprestimo.create(emprestimo);

        TableEmprestimo.setItems(FXCollections.observableArrayList(daoEmprestimo.findAll()));
        clearFields();
    }
*/

    @FXML
    private void btnEmprestimo_salvar(ActionEvent event) {
        Emprestimo emprestimo;
        Emprestimo emprestimoSelecionado = TableEmprestimo.getSelectionModel().getSelectedItem();
        
        if (emprestimoSelecionado == null) {
            emprestimo = new Emprestimo();
        } else {
            emprestimo = emprestimoSelecionado;
        }

        Leitor leitor = cboLeitor.getValue();
        if (leitor == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Leitor não selecionado");
            alert.setContentText("Por favor, selecione um leitor antes de realizar um empréstimo.");
            alert.showAndWait();
            return;
        }

        List<Emprestimo> emprestimosAtivos = leitor.getEmprestimos();
        if (emprestimosAtivos != null && emprestimosAtivos.size() >= MAX_EMPRESTIMOS_ATIVOS)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Limite de empréstimos atingido");
            alert.setContentText("Este leitor já tem 5 empréstimos ativos.");
            alert.showAndWait();
            return;
        }

        emprestimo.setData(txtData.getValue());

        int prazoDevolucao = leitor instanceof Aluno ? PRAZO_DEVOLUCAO_ALUNO : PRAZO_DEVOLUCAO_PROFESSOR;
        emprestimo.setDataPrevistaEntrega(txtData.getValue().plusDays(prazoDevolucao));

        if(txtDataEntrega.getValue() != null) {
            emprestimo.setDataEntrega(txtDataEntrega.getValue());
        }

        emprestimo.setCopia(cboCopia.getValue());
        emprestimo.setLeitor(leitor);

        daoEmprestimo.create(emprestimo);

        TableEmprestimo.setItems(FXCollections.observableArrayList(daoEmprestimo.findAll()));
        clearFields();
    }


    @FXML
    private void btnEmprestimo_excluir(ActionEvent event)
    {
        Emprestimo emprestimoSelecionado = TableEmprestimo.getSelectionModel().getSelectedItem();
        if (emprestimoSelecionado != null)
        {
            daoEmprestimo.delete(emprestimoSelecionado.getIdEmprestimo());
            TableEmprestimo.setItems(FXCollections.observableArrayList(daoEmprestimo.findAll()));
        }
    }

    private void clearFields() {
        txtData.setValue(null);
        txtDataPrevista.setValue(null);
        txtDataEntrega.setValue(null);
        cboCopia.setValue(null);
        cboLeitor.setValue(null);
    }
}
