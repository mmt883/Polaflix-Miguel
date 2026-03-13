package Clases;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Capitulo {

    private int idCapitulo;
    
    private String nombreCapitulo;
    private int numeroCapitulo;
    private String descripcion;
    private String enlace;

    private Temporada temporada;
    private EstadoVisualizacion estado;
}