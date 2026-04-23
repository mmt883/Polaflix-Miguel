package es.unican.mmt883.polaflix.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idRegistro")
public class RegistroSerieUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRegistro;

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