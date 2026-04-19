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
    private CapituloDTO capitulo;
    private int numTemporada;
    private float precioCobrado;
    private SerieResumenDTO serie;
}