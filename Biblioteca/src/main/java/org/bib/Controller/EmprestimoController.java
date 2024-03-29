package org.bib.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import org.bib.dao.CopiaDao;
import org.bib.dao.EmprestimoDao;
import org.bib.dao.LeitorDao;
import org.bib.entities.Aluno;
import org.bib.entities.Copia;
import org.bib.entities.Emprestimo;
import org.bib.entities.Leitor;
import org.bib.entities.Livro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoController {

    @FXML
    private AnchorPane emprestimoanchorPane;

    private EmprestimoDao daoEmprestimo;
    private CopiaDao daoCopia;
    private LeitorDao daoLeitor;


    private static final int MAX_EMPRESTIMOS_ATIVOS = 5;
    private static final int PRAZO_DEVOLUCAO_ALUNO = 5;  
    private static final int PRAZO_DEVOLUCAO_PROFESSOR = 30;
    private static final int MIN_COPIAS_DISPONIVEIS = 1;


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
    private TableColumn<Emprestimo, String> tipoColumn;
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

        List<Copia> todasAsCopias = daoCopia.findAll();
        List<Copia> copiasDisponiveis = new ArrayList<>();

        for (Copia copia : todasAsCopias) {
            List<Emprestimo> emprestimosAtivos = daoEmprestimo.findByCopiaAndStatusAtivo(copia);
            if (emprestimosAtivos.isEmpty()) {
                copiasDisponiveis.add(copia);
            }
        }
        cboCopia.setItems(FXCollections.observableArrayList(copiasDisponiveis));

        //cboCopia.setItems(FXCollections.observableArrayList(daoCopia.findAll()));
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

        tipoColumn.setCellValueFactory(cellData -> {
            Leitor leitor = cellData.getValue().getLeitor();
            if (leitor != null) {
                return new SimpleStringProperty(leitor instanceof Aluno ? "Aluno" : "Professor");
            } else {
                return new SimpleStringProperty("");
            }
        });
        
        statusColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDataEntrega() == null &&
                cellData.getValue().getDataPrevistaEntrega().isBefore(LocalDate.now())) {
                return new SimpleStringProperty("Atrasado");
            } else {
                return new SimpleStringProperty("No prazo");
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
    private void btnEmprestimo_salvar(ActionEvent event) {
        Emprestimo emprestimo;
        Emprestimo emprestimoSelecionado = TableEmprestimo.getSelectionModel().getSelectedItem();
        
        if (emprestimoSelecionado == null) {
            emprestimo = new Emprestimo();
        } else {
            emprestimo = emprestimoSelecionado;
        }

        Leitor leitor = cboLeitor.getValue();
        if (leitor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Leitor não selecionado");
            alert.setContentText("Por favor, selecione um leitor antes de realizar um empréstimo.");
            alert.showAndWait();
            return;
        }

        List<Emprestimo> emprestimosAtivos = leitor.getEmprestimos();
        if (emprestimosAtivos != null && emprestimosAtivos.size() >= MAX_EMPRESTIMOS_ATIVOS) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Limite de empréstimos atingido");
            alert.setContentText("Este leitor já tem 5 empréstimos ativos.");
            alert.showAndWait();
            return;
        }

        Copia copia = cboCopia.getValue();
        Livro livro = copia.getLivro();

        int numCopias = daoCopia.countByLivro(livro);
        if (copia == null || numCopias <= MIN_COPIAS_DISPONIVEIS) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cópia indisponível");
            alert.setContentText("Não é possível realizar o empréstimo, pois não há cópias disponíveis.");
            alert.showAndWait();
            return;
        }

        emprestimo.setData(txtData.getValue());

        if(leitor instanceof Aluno) {
            emprestimo.setDataPrevistaEntrega(txtData.getValue().plusDays(PRAZO_DEVOLUCAO_ALUNO));
        } else {
            emprestimo.setDataPrevistaEntrega(txtData.getValue().plusDays(PRAZO_DEVOLUCAO_PROFESSOR));
        }

        if(txtDataEntrega.getValue() != null) {
            emprestimo.setDataEntrega(txtDataEntrega.getValue());
        }

        emprestimo.setCopia(cboCopia.getValue());
        emprestimo.setLeitor(leitor);

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
        if (leitor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Leitor não selecionado");
            alert.setContentText("Por favor, selecione um leitor antes de realizar um empréstimo.");
            alert.showAndWait();
            return;
        }

        List<Emprestimo> emprestimosAtivos = leitor.getEmprestimos();
        if (emprestimosAtivos != null && emprestimosAtivos.size() >= MAX_EMPRESTIMOS_ATIVOS) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Limite de empréstimos atingido");
            alert.setContentText("Este leitor já tem 5 empréstimos ativos.");
            alert.showAndWait();
            return;
        }

        Copia copia = cboCopia.getValue();
        if (copia == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cópia não selecionada");
            alert.setContentText("Por favor, selecione uma cópia antes de realizar um empréstimo.");
            alert.showAndWait();
            return;
        }

        Livro livro = copia.getLivro();
        int numCopias = daoCopia.countByLivro(livro);
        if (numCopias <= MIN_COPIAS_DISPONIVEIS) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cópia indisponível");
            alert.setContentText("Não é possível realizar o empréstimo, pois não há cópias disponíveis.");
            alert.showAndWait();
            return;
        }

        emprestimo.setData(txtData.getValue());

        if (leitor instanceof Aluno) {
            emprestimo.setDataPrevistaEntrega(txtData.getValue().plusDays(PRAZO_DEVOLUCAO_ALUNO));
        } else {
            emprestimo.setDataPrevistaEntrega(txtData.getValue().plusDays(PRAZO_DEVOLUCAO_PROFESSOR));
        }

        if (txtDataEntrega.getValue() != null) {
            emprestimo.setDataEntrega(txtDataEntrega.getValue());
        }

        emprestimo.setCopia(copia);
        emprestimo.setLeitor(leitor);

        if (emprestimoSelecionado == null) {
            // Criar novo empréstimo
            daoEmprestimo.create(emprestimo);
        } else {
            // Atualizar empréstimo existente
            daoEmprestimo.update(emprestimo);
        }

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
