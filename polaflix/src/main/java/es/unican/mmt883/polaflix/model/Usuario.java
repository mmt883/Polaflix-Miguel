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
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

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
    private Set<Factura> facturas = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<RegistroSerieUsuario> registros = new HashSet<>();

    @ManyToMany
    private Set<Capitulo> capitulosVistos = new HashSet<>();

    public void seleccionarSerie(Serie s) {
    }

    public void agregarSerieAPendiente(Serie s) {
        seriesPendientes.add(s);
    }

    public Factura comprobarFacturaActual() {
        String mesActual = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        for (Factura f : facturas) {
            if (f.getMes().equals(mesActual)) {
                return f;
            }
        }
        return null;
    }

    public boolean marcarCapituloComoVisto(Capitulo c) {
        capitulosVistos.add(c);
        Factura facturaActual = comprobarFacturaActual();
        if (facturaActual != null) {
            Visualizacion v = new Visualizacion();
            v.setFechaVisualizacion(new Date());
            v.setNumCapitulo(c.getNumeroCapitulo());
            v.setNumTemporada(c.getTemporada().getNumeroTemporada());
            v.setSerie(c.getTemporada().getSerie());
            facturaActual.getVisualizaciones().add(v);
        } else {
            Factura nuevaFactura = new Factura();
            String mesActual = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            nuevaFactura.setMes(mesActual);
            nuevaFactura.setUsuario(this);
            Visualizacion v = new Visualizacion();
            v.setFechaVisualizacion(new Date());
            v.setNumCapitulo(c.getNumeroCapitulo());
            v.setNumTemporada(c.getTemporada().getNumeroTemporada());
            v.setSerie(c.getTemporada().getSerie());
            nuevaFactura.getVisualizaciones().add(v);
            facturas.add(nuevaFactura);
        }
        Serie serie = c.getTemporada().getSerie();
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
}