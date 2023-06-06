package com.bib.entities;
import java.util.List;
import lombok.*;
import jakarta.persistence.Entity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Livro {
    private Long id;
    private String nome;
    private Integer ano;
    private String edicao;
    private List<Copia> copias;
    private List<Autor> autores;
    private Genero genero;
}
