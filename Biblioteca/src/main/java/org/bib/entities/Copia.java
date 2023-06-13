package org.bib.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

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

    @OneToMany(mappedBy = "copia")
    private List<Emprestimo> emprestimos;
}