package org.bib;

//Dao
import org.bib.dao.UsuarioDao;

//Entities
import org.bib.entities.Usuario;


public class Main {
    public static void main(String[] args) {
        EntryPoint.main(args);
        verificarEInserirUsuario();
    }

    public static void verificarEInserirUsuario() {
        UsuarioDao usuarioDao = new UsuarioDao();
        
        // Verificar se a tabela está vazia
        if (usuarioDao.isEmpty()) {
            // Criar o usuário
            Usuario usuario = new Usuario();
            usuario.setLogin("admin");
            usuario.setSenha("123");
            usuarioDao.create(usuario);
        }
    }
}

