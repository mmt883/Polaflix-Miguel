package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVisualizacion;

    @Column(nullable = false)
    private Date fechaVisualizacion;
    @Column(nullable = false)
    private int idCapitulo;
    @Column(nullable = false)
    private int idTemporada;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    public Capitulo encontrarCapituloTemporadaenSerie(Serie s, int numCapitulo, int numTemporada) {
        return null;
    }
}