package org.bib.entities;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String login;
    private String senha;

    @OneToOne(mappedBy = "usuario")
    private Leitor leitor;
}
