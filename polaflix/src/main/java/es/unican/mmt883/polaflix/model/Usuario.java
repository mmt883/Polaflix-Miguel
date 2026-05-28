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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Vistas.UsuarioResumen.class)
    private Long idUsuario;
    
    @Column(nullable = false)
    @JsonView(Vistas.UsuarioResumen.class)
    private String nombreUsuario;
    
    @Column(nullable = false)
    @JsonIgnore
    private String contraseña;
    
    @Column(nullable = false)
    @JsonIgnore
    private String cuentaBancaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonView(Vistas.UsuarioResumen.class)
    private TipoSuscripcion tipo;

    @ManyToMany
    @JsonView(Vistas.UsuarioResumen.class)
    private Set<Serie> seriesPendientes = new HashSet<>();

    @ManyToMany
    @JsonView(Vistas.UsuarioResumen.class)
    private Set<Serie> seriesTerminadas = new HashSet<>();

    @ManyToMany
    @JsonView(Vistas.UsuarioResumen.class)
    private Set<Serie> seriesEmpezadas = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Factura> facturas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RegistroSerieUsuario> registros = new ArrayList<>();

    @ManyToMany
    @JsonView(Vistas.UsuarioResumen.class)
    private Set<Capitulo> capitulosVistos = new HashSet<>();

    public void agregarSerieAPendiente(Serie s) {

        if (seriesEmpezadas.contains(s)) {
            throw new IllegalArgumentException("La serie ya está empezada");
        }
        if (seriesTerminadas.contains(s)) {
            throw new IllegalArgumentException("La serie ya está terminada");
        }

        seriesPendientes.add(s);
    }

    public void eliminarSeriePendiente(Serie s) {
        this.seriesPendientes.remove(s);
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

    public RegistroSerieUsuario marcarCapituloComoVisto(Capitulo c) {
        if (capitulosVistos.contains(c)) {
            throw new IllegalArgumentException("El capítulo ya está marcado como visto");
        }
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

        if (!seriesTerminadas.contains(serie) && !seriesEmpezadas.contains(serie)) {
            seriesPendientes.remove(serie);
            seriesEmpezadas.add(serie);
        }

        if (serie.esUltimoCapitulo(c)) {
            if (seriesEmpezadas.contains(serie)) {
                seriesEmpezadas.remove(serie);
            } else {
                seriesPendientes.remove(serie);
            }
            seriesTerminadas.add(serie);
        }
        return reg;
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


