package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idUsuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(nullable = false)
    private String nombreUsuario;
    @Column(nullable = false)
    private String contraseña;
    @Column(nullable = false)
    private String cuentaBancaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSuscripcion tipo;

    @ManyToMany
    private Set<Serie> seriesPendientes = new HashSet<>();

    @ManyToMany
    private Set<Serie> seriesTerminadas = new HashSet<>();

    @ManyToMany
    private Set<Serie> seriesEmpezadas = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Factura> facturas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<RegistroSerieUsuario> registros = new ArrayList<>();

    @ManyToMany
    private Set<Capitulo> capitulosVistos = new HashSet<>();

    public void seleccionarSerie(Serie s) {
    }

    public void agregarSerieAPendiente(Serie s) {
        seriesPendientes.add(s);
    }

    public Factura comprobarFacturaActual() {
        // Formato "YYYY-MM", ej. "2026-04"
        String mesActual = String.format("%04d-%02d", 
            java.time.LocalDate.now().getYear(), 
            java.time.LocalDate.now().getMonthValue());
            
        for (Factura f : facturas) {
            if (f.getMes().equals(mesActual)) {
                return f;
            }
        }
        return null;
    }

    public boolean marcarCapituloComoVisto(Capitulo c) {
        capitulosVistos.add(c);
        Serie serie = c.getTemporada().getSerie();
        float precio = calcularPrecioCobrado(serie);
        Factura facturaActual = comprobarFacturaActual();
        if (facturaActual != null) {
            Visualizacion v = new Visualizacion();
            v.setFechaVisualizacion(new Date());
            v.setNumCapitulo(c.getNumeroCapitulo());
            v.setNumTemporada(c.getTemporada().getNumeroTemporada());
            v.setSerie(serie);
            v.setPrecioCobrado(precio);
            facturaActual.getVisualizaciones().add(v);
            facturaActual.setTotal(facturaActual.getTotal() + precio);
        } else {
            Factura nuevaFactura = new Factura();
            String mesActual = String.format("%04d-%02d", 
                java.time.LocalDate.now().getYear(), 
                java.time.LocalDate.now().getMonthValue());
            nuevaFactura.setMes(mesActual);
            nuevaFactura.setUsuario(this);
            Visualizacion v = new Visualizacion();
            v.setFechaVisualizacion(new Date());
            v.setNumCapitulo(c.getNumeroCapitulo());
            v.setNumTemporada(c.getTemporada().getNumeroTemporada());
            v.setSerie(serie);
            v.setPrecioCobrado(precio);
            nuevaFactura.getVisualizaciones().add(v);
            nuevaFactura.setTotal(precio);
            facturas.add(nuevaFactura);
        }
        
        RegistroSerieUsuario reg = null;
        for (RegistroSerieUsuario r : registros) {
            if (r.getSerie().equals(serie)) {
                reg = r;
                break;
            }
        }
        if (reg == null) {
            reg = new RegistroSerieUsuario();
            reg.setUsuario(this);
            reg.setSerie(serie);
            registros.add(reg);
        }
        reg.setUltimoCapituloVisto(c);
        return true;
    }

    public float calcularPrecioCobrado(Serie serie) {
        if (this.tipo == TipoSuscripcion.CUOTAFIJA) {
            return 0.0f; // Cuota fija mensual, visualizaciones gratis
        } else {
            switch (serie.getCategoria()) {
                case ESTANDAR:
                    return 0.50f;
                case SILVER:
                    return 0.75f;
                case GOLD:
                    return 1.50f;
                default:
                    return 0.50f;
            }
        }
    }
}