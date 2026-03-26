package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
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
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSerie;
    
    private String nombreSerie;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private CategoriaSerie categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Temporada> temporadas = new ArrayList<>();

    @ManyToMany
    private List<Persona> actores = new ArrayList<>();

    @ManyToMany
    private List<Persona> creador = new ArrayList<>();

    public Capitulo encontrarCapituloenTemporada(int idCapitulo, int idTemporada) {
        return null;
    }
}