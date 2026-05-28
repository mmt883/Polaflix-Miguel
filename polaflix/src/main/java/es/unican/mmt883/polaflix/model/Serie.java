package es.unican.mmt883.polaflix.model;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.vistas.Vistas;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idSerie")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({Vistas.SerieResumen.class, Vistas.UsuarioResumen.class})
    private Long idSerie;
    
    @Column(nullable = false)
    @JsonView({Vistas.SerieResumen.class, Vistas.UsuarioResumen.class})
    private String nombreSerie;
    
    @Column(nullable = false)
    @JsonView({Vistas.SerieResumen.class, Vistas.UsuarioResumen.class})
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonView({Vistas.SerieResumen.class, Vistas.UsuarioResumen.class})
    private CategoriaSerie categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    @JsonView(Vistas.SerieCompleto.class)
    private List<Temporada> temporadas = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonView(Vistas.SerieResumen.class)
    private Set<Persona> actores = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonView(Vistas.SerieResumen.class)
    private Set<Persona> creadores = new HashSet<>();

    public Temporada getTemporadaByNumero(int numeroTemporada) {
        return temporadas.stream()
                .filter(t -> t.getNumeroTemporada() == numeroTemporada)
                .findFirst()
                .orElse(null);
    }

    public Capitulo encontrarCapituloenTemporada(int numCapitulo, int numTemporada) {
        Temporada temporada = getTemporadaByNumero(numTemporada);
        if (temporada != null) {
            return temporada.getCapituloByNumero(numCapitulo);
        }
        return null;
    }

    public Temporada getUltimaTemporada() {
        return temporadas.stream()
                .max(Comparator.comparingInt(Temporada::getNumeroTemporada))
                .orElse(null);
    }

    public boolean esUltimoCapitulo(Capitulo capitulo) {
        Temporada ultimaTemporada = getUltimaTemporada();
        if (ultimaTemporada == null || capitulo == null || capitulo.getTemporada() == null) {
            return false;
        }
        if (!ultimaTemporada.equals(capitulo.getTemporada())) {
            return false;
        }
        return ultimaTemporada.getCapitulos().stream()
                .max(Comparator.comparingInt(Capitulo::getNumeroCapitulo))
                .map(c -> c.equals(capitulo))
                .orElse(false);
    }

    public void addTemporada(Temporada temporada) {
        if (temporada == null) return;
        temporada.setSerie(this);
        this.temporadas.add(temporada);
    }

    public void addCapituloToTemporada(int numeroTemporada, Capitulo capitulo) {
        Temporada temporada = getTemporadaByNumero(numeroTemporada);
        if (temporada == null) {
            throw new IllegalArgumentException("Temporada not found: " + numeroTemporada);
        }
        capitulo.setTemporada(temporada);
        temporada.getCapitulos().add(capitulo);
    }
}


