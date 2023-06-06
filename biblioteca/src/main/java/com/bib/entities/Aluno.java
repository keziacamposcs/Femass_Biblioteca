package com.bib.entities;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluno extends Leitor{
    private String matricula;
}
