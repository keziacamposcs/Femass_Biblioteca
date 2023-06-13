package org.bib.Controller;

import java.util.ArrayList;
import java.util.List;

import org.bib.dao.Dao;
import org.bib.entities.Autor;
import org.bib.entities.Copia;
import org.bib.entities.Genero;
import org.bib.entities.Livro;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LivroController {

    @FXML
    private TableView<Livro> TableLivro;
    @FXML
    private TableColumn<Livro, Long> idColumn;
    @FXML
    private TableColumn<Livro, String> nomeColumn;
    @FXML
    private TableColumn<Livro, Integer> anoColumn;
    @FXML
    private TableColumn<Livro, Integer> edicaoColumn;
    @FXML
    private TableColumn<Livro, Genero> generoColumn;
    @FXML
    private TableColumn<Livro, Autor> autorColumn;
    @FXML
    private TableColumn<Livro, Integer> copiaColumn;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtEdicao;
    @FXML
    private ComboBox<Genero> cboGenero;
    @FXML
    private ComboBox<Autor> cboAutor;
    @FXML
    private TextField txtCopia;

    private Dao<Livro> daoLivro;
    private Dao<Genero> daoGenero;
    private Dao<Autor> daoAutor;

    public void initialize()
    {
        daoLivro = new Dao<>(Livro.class);
        daoGenero = new Dao<>(Genero.class);
        daoAutor = new Dao<>(Autor.class);

        // Configurar colunas da tabela
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdLivro()));
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeLivro()));
        anoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAno()));
        generoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGenero()));

        
        // Carregar dados na tabela
        TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
    
        // Carregar dados no ComboBox de Gênero
        cboGenero.setItems(FXCollections.observableArrayList(daoGenero.findAll()));
    
        // Carregar dados no ComboBox de Autor
        cboAutor.setItems(FXCollections.observableArrayList(daoAutor.findAll()));
    }

    @FXML
    private void btnLivro_salvar() {
        String nome = txtNome.getText();
        int ano = Integer.parseInt(txtAno.getText());
        String edicao = txtEdicao.getText();
        Genero genero = cboGenero.getSelectionModel().getSelectedItem();
        Autor autor = cboAutor.getSelectionModel().getSelectedItem();
        int copia = Integer.parseInt(txtCopia.getText());

        Livro livro = new Livro();
        livro.setNomeLivro(nome);
        livro.setAno(ano);
        livro.setEdicao(edicao);
        livro.setGenero(genero);
        livro.setAutores(List.of(autor));
        livro.setCopias(new ArrayList<>());

        for (int i = 0; i < copia; i++) {
            Copia novaCopia = new Copia();
            novaCopia.setLivro(livro);
            livro.getCopias().add(novaCopia);
                }

        daoLivro.create(livro);

        clearFields();
        TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
    }

    @FXML
    private void btnLivro_excluir() {
        Livro livroSelecionado = TableLivro.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            daoLivro.delete(livroSelecionado.getIdLivro());
            TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
        }
    }

    @FXML
    private void On_Key_Pressed_TableLivro() {
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableLeitor() {
        exibirDados();
    }

    private void exibirDados() {
        Livro livro = TableLivro.getSelectionModel().getSelectedItem();
        if (livro == null) return;

        txtNome.setText(livro.getNomeLivro());
        txtAno.setText(Integer.toString(livro.getAno()));
        txtEdicao.setText(livro.getEdicao());
        cboGenero.getSelectionModel().select(livro.getGenero());
        cboAutor.getSelectionModel().select(livro.getAutores().get(0));
        txtCopia.setText(Integer.toString(livro.getCopias().size()));
    }

    private void clearFields() {
        txtNome.clear();
        txtAno.clear();
        txtEdicao.clear();
        cboGenero.getSelectionModel().clearSelection();
        cboAutor.getSelectionModel().clearSelection();
        txtCopia.clear();
    }

}
