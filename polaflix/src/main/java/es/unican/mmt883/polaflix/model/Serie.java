package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Persona> actores = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Persona> creador = new ArrayList<>();

    public Capitulo encontrarCapituloenTemporada(int numCapitulo, int numTemporada) {
        return null;
    }
}