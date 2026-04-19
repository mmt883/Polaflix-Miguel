package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSerie;
    
    @Column(nullable = false)
    private String nombreSerie;
    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaSerie categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "numeroTemporada")
    private Map<Integer, Temporada> temporadas = new HashMap<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Persona> actores = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Persona> creadores = new HashSet<>();

    public Capitulo encontrarCapituloenTemporada(int numCapitulo, int numTemporada) {
        Temporada temporada = temporadas.get(numTemporada);
        if (temporada != null) {
            return temporada.getCapitulos().get(numCapitulo);
        }
        return null;
    }
}