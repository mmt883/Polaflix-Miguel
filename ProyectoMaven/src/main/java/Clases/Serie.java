package Clases;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Serie {

    private int idSerie;
    private String nombreSerie;
    private String descripcion;

    private CategoriaSerie categoria;
    private List<Temporada> temporadas = new ArrayList<>();
    private List<Persona> actores = new ArrayList<>();
    private List<Persona> creador = new ArrayList<>();
}