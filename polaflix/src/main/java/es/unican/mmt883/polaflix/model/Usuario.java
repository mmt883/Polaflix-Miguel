package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "usuario")
    private List<Factura> facturas = new ArrayList<Factura>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<RegistroSerieUsuario> registro = new ArrayList<>();

    @ManyToMany
    private Set<Capitulo> capitulosVistos = new HashSet<>();

    public void seleccionarSerie(Serie s) {
    }

    public void agregarSerieAPendiente(Serie s) {
    }

    public Factura comprobarFacturaActual() {
        return null;
    }

    public List<Factura> marcarCapituloComoVisto(Capitulo c) {
        return new ArrayList<>();
    }
}