package org.bib.dao;
import org.bib.entities.Livro;

public class LivroDao extends Dao<Livro>
{
    public  LivroDao(Class<Livro> entity)
    {
        super(entity);
    }

    public LivroDao()
    {
        super(Livro.class);
    }    
}
