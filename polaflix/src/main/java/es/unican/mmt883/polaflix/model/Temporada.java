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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idTemporada")
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Vistas.SerieCompleto.class)
    private Long idTemporada;
    
    @Column(nullable = false)
    @JsonView(Vistas.SerieCompleto.class)
    private String nombreTemporada;
    
    @Column(nullable = false)
    @JsonView(Vistas.SerieCompleto.class)
    private int numeroTemporada;
    
    @Column(nullable = false)
    @JsonView(Vistas.SerieCompleto.class)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    @JsonIgnore
    private Serie serie;

    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL)
    @JsonView(Vistas.SerieCompleto.class)
    private List<Capitulo> capitulos = new ArrayList<>();

    public Capitulo getCapituloByNumero(int numeroCapitulo) {
        return capitulos.stream()
                .filter(c -> c.getNumeroCapitulo() == numeroCapitulo)
                .findFirst()
                .orElse(null);
    }
}


