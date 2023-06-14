package org.bib.entities;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;
    private LocalDate data;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntrega;

    @ManyToOne
    @JoinColumn(name = "copia_id")
    private Copia copia;

    @ManyToOne
    @JoinColumn(name = "leitor_idEmprestimo")
    private Leitor leitor;
}
