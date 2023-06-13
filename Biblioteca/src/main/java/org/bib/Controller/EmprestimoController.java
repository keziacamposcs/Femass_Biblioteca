package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import org.bib.dao.Dao;
import org.bib.dao.EmprestimoDao;
import org.bib.dao.LeitorDao;
import org.bib.dao.LivroDao;

import org.bib.entities.Emprestimo;
import org.bib.entities.Leitor;
import org.bib.entities.Livro;

import java.net.URL;
import java.util.ResourceBundle;

public class EmprestimoController implements Initializable {

    // Table
    @FXML 
    private TableView<Emprestimo> TableEmprestimo;

    @FXML 
    private TableColumn<Emprestimo, String> dataColumn;
    
    @FXML 
    private TableColumn<Emprestimo, String> dataPrevistaColumn;

    @FXML 
    private TableColumn<Emprestimo, String> dataEntregaColumn;

    @FXML 
    private TableColumn<Emprestimo, Livro> livroColumn;

    @FXML 
    private TableColumn<Emprestimo, Leitor> leitorColumn;
    // Fim - Table

    
    @FXML private Button btnEmprestimo_excluir;
    @FXML private Button btnEmprestimo_salvar;

    @FXML private ComboBox<Livro> cboLivro;
    @FXML private ComboBox<Leitor> cboLeitor;

    @FXML private DatePicker data;
    @FXML private DatePicker dataPrevista;
    @FXML private DatePicker dataEntrega;

    private Dao<Emprestimo> dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dao = new EmprestimoDao(Emprestimo.class);

        configurarTabela();
        atualizarTabela();
        configurarComboBoxes();
    }

    private void configurarTabela() {
        dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData().toString()));
        dataPrevistaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataPrevistaEntrega().toString()));
        dataEntregaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataEntrega().toString()));
        livroColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCopia().getLivro()));
        leitorColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLeitor()));
    }

    private void atualizarTabela() {
        TableEmprestimo.getItems().setAll(dao.findAll());
    }

    private void configurarComboBoxes() {
        cboLivro.getItems().setAll(new LivroDao(Livro.class).findAll());
        cboLeitor.getItems().setAll(new LeitorDao(Leitor.class).findAll());
    }

    @FXML
    private void btnEmprestimo_salvar() {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setData(data.getValue());
        emprestimo.setDataPrevistaEntrega(dataPrevista.getValue());
        emprestimo.setDataEntrega(dataEntrega.getValue());
        emprestimo.setLeitor(cboLeitor.getSelectionModel().getSelectedItem());
        // Necessário fazer a associação com cópia do livro
        dao.create(emprestimo);

        atualizarTabela();
    }

    @FXML
    private void btnEmprestimo_excluir() {
        Emprestimo emprestimo = TableEmprestimo.getSelectionModel().getSelectedItem();
        if (emprestimo != null) {
            dao.delete(emprestimo);
            atualizarTabela();
        }
    }

}
