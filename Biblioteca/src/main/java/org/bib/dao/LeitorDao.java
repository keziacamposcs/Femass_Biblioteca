package org.bib.dao;
import org.bib.entities.Leitor;

public class LeitorDao extends Dao<Leitor>
{
    public  LeitorDao(Class<Leitor> entity)
    {
        super(entity);
    }
    public LeitorDao()
    {
        super(Leitor.class);
    }
}