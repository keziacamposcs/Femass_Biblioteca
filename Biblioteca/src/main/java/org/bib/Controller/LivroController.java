package org.bib.Controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.bib.dao.Dao;
import org.bib.entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LivroController {
    @FXML
    private AnchorPane livroanchorPane;

    private Dao<Livro> daoLivro;
    private Dao<Genero> daoGenero;
    private Dao<Autor> daoAutor;

    @FXML
    private TableView<Livro> TableLivro;
    @FXML
    private TableColumn<Livro, Long> idColumn;
    @FXML
    private TableColumn<Livro, String> nomeColumn;
    @FXML
    private TableColumn<Livro, String> anoColumn;
    @FXML
    private TableColumn<Livro, String> edicaoColumn;
    @FXML
    private TableColumn<Livro, String> generoColumn;
    @FXML
    private TableColumn<Livro, String> autorColumn;
    @FXML
    private TableColumn<Livro, String> copiaColumn;

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

    public void initialize() {
        daoLivro = new Dao<>(Livro.class);
        daoGenero = new Dao<>(Genero.class);
        daoAutor = new Dao<>(Autor.class);

        // ComboBox Genero
        cboGenero.setItems(FXCollections.observableArrayList(daoGenero.findAll()));

        cboGenero.setConverter(new StringConverter<Genero>() {
            @Override
            public String toString(Genero genero) {
                return genero == null ? null : genero.getNomeGenero();
            }

            @Override
            public Genero fromString(String string) {
                return daoGenero.findAll().stream().filter(genero ->
                        genero.getNomeGenero().equals(string)).findFirst().orElse(null);
            }
        });
        // Fim - ComboBox Genero

        // ComboBox Autor
        cboAutor.setItems(FXCollections.observableArrayList(daoAutor.findAll()));

        cboAutor.setConverter(new StringConverter<Autor>() {
            @Override
            public String toString(Autor autor) {
                return autor == null ? null : autor.getNomeAutor();
            }

            @Override
            public Autor fromString(String string) {
                return daoAutor.findAll().stream().filter(autor ->
                        autor.getNomeAutor().equals(string)).findFirst().orElse(null);
            }
        });
        // Fim - ComboBox Autor

        // Configuração das colunas da tabela
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIdLivro()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeLivro()));
        anoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAno().toString()));
        edicaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdicao()));
        generoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero().getNomeGenero()));
        autorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutores().stream().map(Autor::getNomeAutor).collect(Collectors.joining(", "))));
        copiaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCopias().size())));

        // Preenchimento da tabela
        TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
    }

    @FXML
    protected void btnLivro_salvar(ActionEvent event) {
        Livro livroSelecionado = TableLivro.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            String nome = txtNome.getText();
            Integer ano = Integer.parseInt(txtAno.getText());
            String edicao = txtEdicao.getText();
            Genero genero = cboGenero.getSelectionModel().getSelectedItem();
            Autor autor = cboAutor.getSelectionModel().getSelectedItem();
            List<Copia> copias = new ArrayList<>(); // Aqui, você pode precisar implementar um meio de adicionar/copiar cópias ao livro
    
            livroSelecionado.setNomeLivro(nome);
            livroSelecionado.setAno(ano);
            livroSelecionado.setEdicao(edicao);
            livroSelecionado.setGenero(genero);
            livroSelecionado.setAutores(Arrays.asList(autor)); // Isso é simplificado, considere uma maneira de permitir múltiplos autores
            livroSelecionado.setCopias(copias);
    
            daoLivro.update(livroSelecionado);
    
            // Limpar campos após a atualização
            clearFields();
    
            // Atualizar tabela
            TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
        }
    }
    

    @FXML
    protected void btnLivro_excluir(ActionEvent event) {
        Livro livroSelecionado = TableLivro.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            daoLivro.delete(livroSelecionado);
            TableLivro.getItems().remove(livroSelecionado);
            TableLivro.refresh();
        }
    }

    @FXML
    protected void On_Key_Pressed_TableLivro() {
        exibirDados();
    }

    @FXML
    protected void On_Mouse_Clicked_TableLivro() {
        exibirDados();
    }

    private void exibirDados() {
        Livro livro = TableLivro.getSelectionModel().getSelectedItem();
        if (livro == null) return;   
        txtNome.setText(livro.getNomeLivro());
        txtAno.setText(livro.getAno().toString());
        txtEdicao.setText(livro.getEdicao());
        cboGenero.setValue(livro.getGenero());
        cboAutor.setValue(livro.getAutores().get(0));
        txtCopia.setText(Integer.toString(livro.getCopias().size()));
    }

    //Botão cadastrar Autores
    @FXML
    private void autor_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Autor.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Fim - Botão cadastrar Autores

    //Botão cadastrar Genero
    @FXML
    private void genero_onAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Genero.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Fim - Botão cadastrar Genero

    private void clearFields() {
        txtNome.clear();
        txtAno.clear();
        txtEdicao.clear();
        cboGenero.getSelectionModel().clearSelection();
        cboAutor.getSelectionModel().clearSelection();
        txtCopia.clear();
    }
}
