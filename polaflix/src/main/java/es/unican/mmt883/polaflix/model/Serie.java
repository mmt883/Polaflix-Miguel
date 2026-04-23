package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
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
    private Long idSerie;
    
    @Column(nullable = false)
    private String nombreSerie;
    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaSerie categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Temporada> temporadas = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Persona> actores = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
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
}