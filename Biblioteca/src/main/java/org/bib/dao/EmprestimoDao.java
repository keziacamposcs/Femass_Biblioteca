package org.bib.dao;
import org.bib.entities.Emprestimo;

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
}
