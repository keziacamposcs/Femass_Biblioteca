package org.bib.Controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaInicioController
{
    @FXML
    private AnchorPane telaincioanchor;

    @FXML
    private AnchorPane leitoranchorPane;

    @FXML
    private void leitor_onAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Leitor.fxml"));
            Parent root = loader.load();            
            Scene scene = new Scene(root);
            Stage stage = (Stage) leitoranchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void livro_onAction(ActionEvent event) {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Livro.fxml"));
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Livro");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }



    @FXML
    private void usuario_onAction(ActionEvent event) {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Usuario.fxml"));
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Usuário");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    
    @FXML
    private void emprestimo_onAction(ActionEvent event) {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Emprestimo.fxml"));
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Emprestimo");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
