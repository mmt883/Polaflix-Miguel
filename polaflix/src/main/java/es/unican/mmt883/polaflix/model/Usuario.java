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
    
    private String nombreUsuario;
    private String contraseña;
    private String cuentaBancaria;

    @Enumerated(EnumType.STRING)
    private TipoSuscripcion tipo;

    @OneToMany
    private List<Serie> seriesPendientes = new ArrayList<>();

    @OneToMany
    private List<Serie> seriesTerminadas = new ArrayList<>();

    @OneToMany
    private List<Serie> seriesEmpezadas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Factura> facturas = new ArrayList<Factura>();


    public void seleccionarSerie(int serieID) {
    }

    public void agregarSerieAPendiente(int serieID) {
    }

    public Factura comprobarFacturaActual() {
        return null;
    }

    public List<Factura> comprobarFacturasHistoricas() {
        return new ArrayList<>();
    }
}