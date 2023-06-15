package org.bib.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;
    private String nomeAutor;
    private String sobrenomeAutor;

    @ManyToMany(mappedBy = "autores")
    private List<Livro> livros;

}