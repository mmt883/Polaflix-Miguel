package es.unican.mmt883.polaflix.model;

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
    private Long idTemporada;
    
    @Column(nullable = false)
    private String nombreTemporada;
    
    @Column(nullable = false)
    private int numeroTemporada;
    
    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL)
    private List<Capitulo> capitulos = new ArrayList<>();

    public Capitulo getCapituloByNumero(int numeroCapitulo) {
        return capitulos.stream()
                .filter(c -> c.getNumeroCapitulo() == numeroCapitulo)
                .findFirst()
                .orElse(null);
    }
}


