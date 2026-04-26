package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisualizacionDTO {
    @JsonView(Vistas.VisualizacionResumen.class)
    private Long idVisualizacion;

    @JsonView(Vistas.VisualizacionResumen.class)
    @NotNull(message = "La fecha de visualización es obligatoria")
    private Date fechaVisualizacion;

    @JsonView(Vistas.VisualizacionCompleto.class)
    @NotNull(message = "El capítulo es obligatorio")
    private CapituloDTO capitulo;

    @JsonView(Vistas.VisualizacionResumen.class)
    @NotNull(message = "El número de la temporada es obligatorio")
    @Min(value = 1, message = "El número de la temporada debe ser mayor que 0")
    private int numTemporada;

    @JsonView(Vistas.VisualizacionResumen.class)
    @NotNull(message = "El precio cobrado es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio cobrado debe ser mayor o igual a 0")
    private float precioCobrado;

    @JsonView(Vistas.VisualizacionCompleto.class)
    @NotNull(message = "La serie es obligatoria")
    private SerieDTO serie;
}


