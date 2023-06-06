package com.bib.entities;
import java.time.LocalDate;
import lombok.*;
import jakarta.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emprestimo {
    private LocalDate data;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntrega;
    private Copia copia;
    private Leitor leitor;
}
