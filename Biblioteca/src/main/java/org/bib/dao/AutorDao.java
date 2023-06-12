package org.bib.dao;
import org.bib.entities.Autor;

public class AutorDao extends Dao<Autor>
{
    public  AutorDao(Class<Autor> entity)
    {
        super(entity);
    }
    public AutorDao()
    {
        super(Autor.class);
    }
}
