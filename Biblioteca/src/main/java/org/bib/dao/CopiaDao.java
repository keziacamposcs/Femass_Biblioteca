package org.bib.dao;
import org.bib.entities.Copia;

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
    
}
