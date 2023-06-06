package com.bib.entities;
import java.util.List;
import lombok.*;
import jakarta.persistence.Entity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Leitor {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Usuario usuario;
    private List<Emprestimo> emprestimos;
}
