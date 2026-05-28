package es.unican.mmt883.polaflix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.vistas.Vistas;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idFactura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Vistas.FacturaCompleto.class)
    private Long idFactura;
    
    @Column(nullable = false)
    @JsonView(Vistas.FacturaCompleto.class)
    private String mes;
    
    @Column(nullable = false)
    @JsonView(Vistas.FacturaCompleto.class)
    private Float total;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    @JsonView(Vistas.FacturaCompleto.class)
    private List<Visualizacion> visualizaciones = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void actualizarTotal() {
        this.total = calcularTotal();
    }

    public Float calcularTotal() {
        if (usuario != null && usuario.getTipo() == TipoSuscripcion.CUOTAFIJA) {
            return 20.0f;
        }
        float total = 0.0f;
        for (Visualizacion visualizacion : visualizaciones) {
            if (visualizacion != null) {
                total += visualizacion.getPrecioCobrado();
            }
        }
        return total;
    }

    public void addVisualizacion(Visualizacion v) {
        if (v != null) {
            this.visualizaciones.add(v);
            this.total += v.getPrecioCobrado(); 
        }
    }

    public void removeVisualizacion(Visualizacion v) {
        if (v != null && this.visualizaciones.remove(v)) {
            this.total -= v.getPrecioCobrado();
        }
    }
}


