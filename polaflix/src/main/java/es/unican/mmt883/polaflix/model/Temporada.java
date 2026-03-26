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
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTemporada;
    
    private String nombreTemporada;
    private int numeroTemporada;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL)
    private List<Capitulo> capitulos = new ArrayList<>();
}