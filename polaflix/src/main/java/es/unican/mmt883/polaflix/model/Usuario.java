package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    
    @Column(nullable = false)
    private String nombreUsuario;
    @Column(nullable = false)
    private String contraseña;
    @Column(nullable = false)
    private String cuentaBancaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSuscripcion tipo;

    @OneToMany
    private List<Serie> seriesPendientes = new ArrayList<>();

    @OneToMany
    private List<Serie> seriesTerminadas = new ArrayList<>();

    @OneToMany
    private List<Serie> seriesEmpezadas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Factura> facturas = new ArrayList<Factura>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<RegistroSerieUsuario> registro = new ArrayList<>();


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