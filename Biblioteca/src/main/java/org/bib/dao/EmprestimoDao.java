package org.bib.dao;
import java.util.List;

import org.bib.entities.Copia;
import org.bib.entities.Emprestimo;

import jakarta.persistence.TypedQuery;

public class EmprestimoDao extends Dao<Emprestimo>
{
    public  EmprestimoDao(Class<Emprestimo> entity)
    {
        super(entity);
    }
    public EmprestimoDao()
    {
        super(Emprestimo.class);
    }

    public List<Emprestimo> findByCopiaAndStatusAtivo(Copia copia) {
    TypedQuery<Emprestimo> query = em.createQuery("SELECT e FROM Emprestimo e WHERE e.copia = :copia AND e.dataEntrega IS NULL", Emprestimo.class);
    query.setParameter("copia", copia);
    return query.getResultList();
    }


}
