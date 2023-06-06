package com.bib.entities;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Genero {
    private Long id;
    private String nome;
}
