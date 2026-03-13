package Clases;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Setter

public class Usuario {


    private int idUsuario;
    private String nombreUsuario;
    private String contraseña;
    private String cuentaBancaria;
    private TipoSuscripcion tipo;
    
    private List<Serie> seriesPendientes = new ArrayList<>();
    private List<Serie> seriesTerminadas = new ArrayList<>();
    private List<Serie> seriesEmpezadas = new ArrayList<>();
    private List<Factura> facturas = new ArrayList<>();
}