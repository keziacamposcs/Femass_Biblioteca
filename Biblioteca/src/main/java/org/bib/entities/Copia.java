package org.bib.entities;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCopia;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

}
