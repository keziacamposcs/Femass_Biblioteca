package org.bib.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Leitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLeitor;
    private String nomeLeitor;
    private String telefone;
    private String email;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "leitor")
    private List<Emprestimo> emprestimos;

    @Override
    public String toString() {
        return nomeLeitor; 
    }

}
