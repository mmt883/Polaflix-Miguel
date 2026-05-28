package es.unican.mmt883.polaflix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.vistas.Vistas;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCapitulo")
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({Vistas.UsuarioResumen.class, Vistas.SerieCompleto.class, Vistas.RegistroCompleto.class})
    private Long idCapitulo;
    
    @Column(nullable = false)
    @JsonView({Vistas.UsuarioResumen.class, Vistas.SerieCompleto.class, Vistas.RegistroCompleto.class})
    private String nombreCapitulo;
    
    @Column(nullable = false)
    @JsonView({Vistas.UsuarioResumen.class, Vistas.SerieCompleto.class, Vistas.RegistroCompleto.class})
    private int numeroCapitulo;
    
    @Column(nullable = false)
    @JsonView({Vistas.UsuarioResumen.class, Vistas.SerieCompleto.class, Vistas.RegistroCompleto.class})
    private String descripcion;
    
    @Column(nullable = false)
    @JsonView({Vistas.UsuarioResumen.class, Vistas.SerieCompleto.class, Vistas.RegistroCompleto.class})
    private String enlace;

    @ManyToOne
    @JoinColumn(name = "temporada_id", nullable = false)
    @JsonIgnore
    private Temporada temporada;

    
}


