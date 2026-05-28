package es.unican.mmt883.polaflix.model;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.vistas.Vistas;
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
    @JsonView(Vistas.RegistroCompleto.class)
    private Long idRegistro;

    @ManyToOne
    @JoinColumn(name = "capitulo_id", nullable = false)
    @JsonView(Vistas.RegistroCompleto.class)
    private Capitulo ultimoCapituloVisto;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonView(Vistas.RegistroCompleto.class)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    @JsonView(Vistas.RegistroCompleto.class)
    private Serie serie;
}


