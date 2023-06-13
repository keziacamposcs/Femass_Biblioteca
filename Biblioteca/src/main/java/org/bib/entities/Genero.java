package org.bib.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenero;
    private String nomeGenero;

    @OneToMany(mappedBy = "genero")
    private List<Livro> livros;
}
