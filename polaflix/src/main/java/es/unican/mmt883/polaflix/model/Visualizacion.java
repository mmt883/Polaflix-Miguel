package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = "idVisualizacion")
public class Visualizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisualizacion;

    @Column(nullable = false)
    private Date fechaVisualizacion;

    @Column(nullable = false)
    private int numCapitulo;
    @Column(nullable = false)
    private int numTemporada;
    @Column(nullable = false)
    private float precioCobrado;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @PrePersist
    public void actualizarPrecioCobrado() {
        this.precioCobrado = calcularPrecioVisualizacion(this.serie);
    }

    public Float calcularPrecioVisualizacion(Serie s) {
        if (s == null || s.getCategoria() == null) {
            return 0.0f;
        }
        switch (s.getCategoria()) {
            case ESTANDAR:
                return 0.50f;
            case SILVER:
                return 0.75f;
            case GOLD:
                return 1.50f;
            default:
                return 0.0f;
        }
    }

    public Capitulo encontrarCapituloTemporadaenSerie(Serie s, int idCapitulo, int idTemporada) {
        return null;
    }
}