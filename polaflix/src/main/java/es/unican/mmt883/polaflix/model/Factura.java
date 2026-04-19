package es.unican.mmt883.polaflix.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;
    
    @Column(nullable = false)
    private String mes;
    @Column(nullable = false)
    private Float total;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
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
}