package org.bib.Controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.bib.dao.AutorDao;
import org.bib.dao.CopiaDao;
import org.bib.dao.GeneroDao;
import org.bib.dao.LivroDao;
import org.bib.entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LivroController {
    @FXML
    private AnchorPane livroanchorPane;

    private LivroDao daoLivro;
    private GeneroDao daoGenero;
    private AutorDao daoAutor;
    private CopiaDao daoCopia;

    // Tabela
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
    private TableColumn<Livro, List<Genero>> generoColumn;
    @FXML
    private TableColumn<Livro, List<Autor>> autorColumn;

    @FXML
    private TableColumn<Livro, String> copiaColumn;
    // Fim - Tabela

    // Campos
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtEdicao;
    @FXML
    private TextField txtCopia;
    // Fim - Campos

    // Genero e Autor
    @FXML
    private ListView<Genero> listGenero;
    @FXML
    private ListView<Autor> listAutor;
    // Fim - Genero e Autor

    // Botões
    @FXML
    private Button btnLivro_salvar;
    @FXML
    private Button btnLivro_excluir;
    // Fim - Botões


    public void initialize() {
        this.daoLivro = new LivroDao(Livro.class);
        this.daoGenero = new GeneroDao(Genero.class);
        this.daoAutor = new AutorDao(Autor.class);
        this.daoCopia = new CopiaDao(Copia.class);
        configurarTabela();

        // Lista Genero
        listGenero.setItems(FXCollections.observableArrayList(daoGenero.findAll()));
        listGenero.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listGenero.setCellFactory(param -> new ListCell<Genero>() {
            @Override
            protected void updateItem(Genero genero, boolean empty) {
                super.updateItem(genero, empty);
                if (empty || genero == null) {
                    setText(null);
                } else {
                    setText(genero.getNomeGenero());
                }
            }
        });
        // Fim - Lista Genero

        // Lista Autor
        listAutor.setItems(FXCollections.observableArrayList(daoAutor.findAll()));
        listAutor.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listAutor.setCellFactory(param -> new ListCell<Autor>() {
            @Override
            protected void updateItem(Autor autor, boolean empty) {
                super.updateItem(autor, empty);
                if (empty || autor == null) {
                    setText(null);
                } else {
                    setText(autor.getNomeAutor());
                }
            }
        });
        // Fim - Lista Autor

        TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
        TableLivro.refresh();
    }

    private void configurarTabela() {
        // Configuração das colunas da tabela
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getIdLivro()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeLivro()));
        anoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAno().toString()));
        edicaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdicao()));

        generoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGeneros()));
        generoColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(List<Genero> generos, boolean empty) {
                super.updateItem(generos, empty);

                if (generos == null || generos.isEmpty() || empty) {
                    setText(null);
                } else {
                    setText(getGenerosAsString(generos));
                }
            }
        });
        autorColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAutores()));
        autorColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(List<Autor> autores, boolean empty) {
                super.updateItem(autores, empty);

                if (autores == null || autores.isEmpty() || empty) {
                    setText(null);
                } else {
                    setText(getAutoresAsString(autores));
                }
            }
        });

        copiaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(getNumeroCopias(cellData.getValue()))));
    }

    private String getGenerosAsString(List<Genero> generos) {
        StringBuilder sb = new StringBuilder();
        for (Genero genero : generos) {
            sb.append(genero.getNomeGenero()).append(", ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length()); // Remove a última vírgula e espaço
        }
        return sb.toString();
    }

    private String getAutoresAsString(List<Autor> autores) {
        StringBuilder sb = new StringBuilder();
        for (Autor autor : autores) {
            sb.append(autor.getNomeAutor()).append(", ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length()); // Remove a última vírgula e espaço
        }
        return sb.toString();
    }

    private int getNumeroCopias(Livro livro) {
        List<Copia> copias = daoCopia.findByLivro(livro);
        return copias.size();
    }

    @FXML
    protected void btnLivro_salvar(ActionEvent event) 
    {
        Livro livroSelecionado = TableLivro.getSelectionModel().getSelectedItem();
        
        if (livroSelecionado == null) {
            // Criar um novo livro
            Livro novoLivro = new Livro();
            novoLivro.setNomeLivro(txtNome.getText());
            novoLivro.setAno(Integer.parseInt(txtAno.getText()));
            novoLivro.setEdicao(txtEdicao.getText());
            novoLivro.setGeneros(listGenero.getSelectionModel().getSelectedItems());
            novoLivro.setAutores(listAutor.getSelectionModel().getSelectedItems());
            
            // Salvando o livro
            daoLivro.create(novoLivro);

            int numeroDeCopias = Integer.parseInt(txtCopia.getText());
            for (int i = 0; i < numeroDeCopias; i++) {
                Copia copia = new Copia();
                copia.setLivro(novoLivro);
                daoCopia.create(copia);
            }
        }
        else 
        {
            // Atualizar um livro existente
            livroSelecionado.setNomeLivro(txtNome.getText());
            livroSelecionado.setAno(Integer.parseInt(txtAno.getText()));
            livroSelecionado.setEdicao(txtEdicao.getText());
            livroSelecionado.setGeneros(new ArrayList<>(listGenero.getSelectionModel().getSelectedItems()));
            livroSelecionado.setAutores(new ArrayList<>(listAutor.getSelectionModel().getSelectedItems()));


            int novoNumeroDeCopias = Integer.parseInt(txtCopia.getText());
            int atualNumeroDeCopias = daoCopia.countByLivro(livroSelecionado);
            
            if (novoNumeroDeCopias > atualNumeroDeCopias) {
                for (int i = 0; i < (novoNumeroDeCopias - atualNumeroDeCopias); i++) {
                    Copia copia = new Copia();
                    copia.setLivro(livroSelecionado);
                    daoCopia.create(copia);
                }
            }
            else if (novoNumeroDeCopias < atualNumeroDeCopias) 
            {
                for (int i = 0; i < (atualNumeroDeCopias - novoNumeroDeCopias); i++) {
                    List<Copia> copias = daoCopia.findByLivro(livroSelecionado);
                    if (!copias.isEmpty()) {
                        Copia copiaParaExcluir = copias.get(0);
                        daoCopia.delete(copiaParaExcluir);
                    }
                }
            }
            // Atualizar o livro
            daoLivro.updateLivro(livroSelecionado);
        }
        clearFields();
        TableLivro.setItems(FXCollections.observableArrayList(daoLivro.findAll()));
        TableLivro.refresh();
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
        if (livro != null) {
            txtNome.setText(livro.getNomeLivro());
            txtAno.setText(String.valueOf(livro.getAno()));
            txtEdicao.setText(livro.getEdicao());
            txtCopia.setText(String.valueOf(getNumeroCopias(livro)));

            List<Integer> generoIndices = new ArrayList<>();
            for (Genero genero : livro.getGeneros()) {
                generoIndices.add(listGenero.getItems().indexOf(genero));
            }
            listGenero.getSelectionModel().clearSelection();
            for (int index : generoIndices) {
                listGenero.getSelectionModel().selectIndices(index);
            }

            List<Integer> autorIndices = new ArrayList<>();
            for (Autor autor : livro.getAutores()) {
                autorIndices.add(listAutor.getItems().indexOf(autor));
            }
            listAutor.getSelectionModel().clearSelection();
            for (int index : autorIndices) {
                listAutor.getSelectionModel().selectIndices(index);
            }
        }
    }



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

    private void clearFields() {
        txtNome.clear();
        txtAno.clear();
        txtEdicao.clear();
        txtCopia.clear();
    }
}
