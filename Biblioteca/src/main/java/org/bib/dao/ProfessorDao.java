package org.bib.dao;
import org.bib.entities.Professor;

public class ProfessorDao extends Dao<Professor>
{
    public  ProfessorDao(Class<Professor> entity)
    {
        super(entity);
    }

    public ProfessorDao()
    {
        super(Professor.class);
    }
}
