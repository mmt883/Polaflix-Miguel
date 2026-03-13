package Clases;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Visualizacion {

    private String fechaVisualizacion;
    private int idCapitulo;
    private int idTemporada;

    private Serie serie;
}