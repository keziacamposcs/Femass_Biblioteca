package org.bib.Controller;

import org.bib.dao.AutorDao;
import org.bib.entities.Autor;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class AutorController {

    @FXML
    private TableView<Autor> TableAutor;
    @FXML
    private TableColumn<Autor, Long> idColumn;
    @FXML
    private TableColumn<Autor, String> nomeColumn;
    @FXML
    private TableColumn<Autor, String> sobrenomeColumn;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtSobrenome;

    private AutorDao autorDao;

    public AutorController() {
        autorDao = new AutorDao();
    }

    @FXML
    private void initialize() {

        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        sobrenomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSobreNome()));
        
        // Carregue os autores na tabela
        loadTableData();
    }

    @FXML
    private void btnAutor_salvar() {
        String nome = txtNome.getText();
        String sobrenome = txtSobrenome.getText();

        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setSobreNome(sobrenome);

        autorDao.create(autor);

        // Limpe o campo de texto
        txtNome.clear();
        txtSobrenome.clear();

        // Atualize os dados da tabela
        loadTableData();
    }

    @FXML
    private void btnAutor_excluir() {
        Autor selectedAutor = TableAutor.getSelectionModel().getSelectedItem();
        if (selectedAutor != null) {
            autorDao.delete(selectedAutor.getId());

            // Atualize os dados da tabela
            loadTableData();
        }
    }

    @FXML
    private void On_Key_Pressed_TableAutor() {
        exibirDados();
    }

    @FXML
    private void On_Mouse_Clicked_TableAutor() {
        exibirDados();
    }

    private void exibirDados() {
        Autor autor = TableAutor.getSelectionModel().getSelectedItem();
        if (autor == null) return;    
        txtNome.setText(autor.getNome());
        txtSobrenome.setText(autor.getSobreNome());
    }

    private void loadTableData() {
        ObservableList<Autor> autores = FXCollections.observableArrayList(autorDao.findAll());
        TableAutor.setItems(autores);
    }
}
