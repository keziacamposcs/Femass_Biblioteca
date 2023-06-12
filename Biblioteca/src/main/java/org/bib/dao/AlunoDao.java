package org.bib.dao;
import org.bib.entities.Aluno;

public class AlunoDao extends Dao<Aluno>
{
    public  AlunoDao(Class<Aluno> entity)
    {
        super(entity);
    }
    public AlunoDao()
    {
        super(Aluno.class);
    }
    
}
