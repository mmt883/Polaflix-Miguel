package es.unican.mmt883.polaflix.model;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.vistas.Vistas;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Vistas.VisualizacionCompleto.class)
    private Long idVisualizacion;

    @Column(nullable = false)
    @JsonView(Vistas.VisualizacionCompleto.class)
    private Date fechaVisualizacion;

    @Column(nullable = false)
    @JsonView(Vistas.VisualizacionCompleto.class)
    private int numCapitulo;
    
    @Column(nullable = false)
    @JsonView(Vistas.VisualizacionCompleto.class)
    private int numTemporada;
    
    @Column(nullable = false)
    @JsonView(Vistas.VisualizacionCompleto.class)
    private float precioCobrado;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    @JsonView(Vistas.SerieResumen.class)
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
        if (s != null) {
            return s.encontrarCapituloenTemporada(numCapitulo, numTemporada);
        }
        return null;
    }
}


