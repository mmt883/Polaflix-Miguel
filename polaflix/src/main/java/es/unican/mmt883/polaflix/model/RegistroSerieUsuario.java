package es.unican.mmt883.polaflix.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroSerieUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación que apunta al último capítulo que ha visto el usuario de una serie
    @ManyToOne
    @JoinColumn(name = "capitulo_id", nullable = false)
    private Capitulo ultimoCapituloVisto;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;
}