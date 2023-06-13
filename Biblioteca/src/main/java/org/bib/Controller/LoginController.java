package org.bib.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bib.entities.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Persistence;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane loginanchorPane;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private void btnLogin(ActionEvent event) {
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        
        // Verificar as credenciais
        if (verificarCredenciais(login, senha)) {
            carregarTelaInicio();
        } else {
            // Exibir uma mensagem de erro ou fazer qualquer ação necessária para um login incorreto
        }
    }
    
    private void carregarTelaInicio() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/TelaInicio.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginanchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    
    private boolean verificarCredenciais(String login, String senha) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("jpa_exemplo").createEntityManager();
    
        try {
            Usuario usuario = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();
    
            // Se chegarmos até aqui, um usuário com o login fornecido foi encontrado.
            // Agora vamos verificar a senha.
            if (usuario.getSenha().equals(senha)) {
                // Senha correta
                return true;
            } else {
                // Senha incorreta
                return false;
            }
        } catch (NoResultException nre) {
            // Nenhum usuário com o login fornecido foi encontrado
            return false;
        } catch (NonUniqueResultException nure) {
            // Mais de um usuário com o login fornecido foi encontrado
            throw new IllegalStateException("Login não é exclusivo: " + login);
        } catch (Exception e) {
            // Alguma outra exceção ocorreu
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }
    
    
    /* 
    private boolean verificarCredenciais(String login, String senha) {
        if ("admin".equals(login) && "123".equals(senha)) {
            return true;
        } else {
            return false;
        }
    }
    */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

}
