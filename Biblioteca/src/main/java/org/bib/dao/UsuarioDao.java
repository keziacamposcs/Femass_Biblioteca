package org.bib.dao;

import org.bib.entities.Usuario;
import jakarta.persistence.*;

public class UsuarioDao extends Dao<Usuario> {
    
    public UsuarioDao(Class<Usuario> entity) {
        super(entity);
    }
    
    public UsuarioDao() {
        super(Usuario.class);
    }

    public boolean isEmpty() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_exemplo");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM Usuario u");
            Long count = (Long) query.getSingleResult();
            return count == 0;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

}
