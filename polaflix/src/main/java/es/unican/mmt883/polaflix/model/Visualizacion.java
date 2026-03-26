package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVisualizacion;

    private String fechaVisualizacion;
    private int idCapitulo;
    private int idTemporada;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    public Float calcularPrecioVisualizacion(Serie s) {
        return 0.0f;
    }

    public Capitulo encontrarCapituloTemporadaenSerie(Serie s, int idCapitulo, int idTemporada) {
        return null;
    }
}