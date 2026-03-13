package Clases;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Temporada {

    private int idTemporada;
    
    private String nombreTemporada;
    private int numeroTemporada;
    private String descripcion;


    private Serie serie;
    private List<Capitulo> capitulos = new ArrayList<>();
}