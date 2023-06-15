package org.bib.dao;
import java.util.List;

import org.bib.entities.Copia;
import org.bib.entities.Livro;
import org.hibernate.HibernateException;
import jakarta.persistence.TypedQuery;

public class CopiaDao extends Dao<Copia>
{
    public  CopiaDao(Class<Copia> entity)
    {
        super(entity);
    }
    public CopiaDao()
    {
        super(Copia.class);
    }

    public List<Copia> findByLivro(Livro livro) {
        try {
            jakarta.persistence.TypedQuery<Copia> query = em.createQuery("SELECT c FROM Copia c WHERE c.livro = :livro", Copia.class);
            query.setParameter("livro", livro);
            return query.getResultList();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }

    public int countByLivro(Livro livro) {
    try {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Copia c WHERE c.livro = :livro", Long.class);
        query.setParameter("livro", livro);
        Long count = query.getSingleResult();
        return count != null ? count.intValue() : 0;
    } catch (HibernateException he) {
        throw new RuntimeException(he);
    }
}

}
