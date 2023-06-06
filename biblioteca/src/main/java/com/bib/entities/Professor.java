package com.bib.entities;
import lombok.*;
import jakarta.persistence.Entity;


import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Professor extends Leitor {
    private String formacao;
}
