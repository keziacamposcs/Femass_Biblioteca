package com.bib.entities;
import lombok.*;
import jakarta.persistence.Entity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    private Long id;
    private String login;
    private String senha;
}
