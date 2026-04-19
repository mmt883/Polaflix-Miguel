package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizacionDTO {
    private Long idVisualizacion;
    private Date fechaVisualizacion;
    private int numCapitulo;
    private int numTemporada;
    private float precioCobrado;
    private Long serieId;
}