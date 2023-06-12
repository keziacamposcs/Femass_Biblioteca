package org.bib.dao;
import org.bib.entities.Genero;

public class GeneroDao extends Dao<Genero>
{
    public  GeneroDao(Class<Genero> entity)
    {
        super(entity);
    }
    public GeneroDao()
    {
        super(Genero.class);
    }    
}
