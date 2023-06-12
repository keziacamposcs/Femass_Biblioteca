package org.bib.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Professor extends Leitor
{
    private String formacao;
}
