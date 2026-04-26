package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaDTO {
    @JsonView(Vistas.FacturaResumen.class)
    private Long idFactura;

    @JsonView(Vistas.FacturaResumen.class)
    @NotBlank(message = "El mes de la factura no puede estar vacío")
    private String mes;

    @JsonView(Vistas.FacturaResumen.class)
    @NotNull(message = "El total de la factura es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor que 0")
    private Float total;

    @JsonView(Vistas.FacturaCompleto.class)
    @NotNull(message = "El usuario es obligatorio")
    private UsuarioDTO usuario;

    @JsonView(Vistas.FacturaCompleto.class)
    private Set<VisualizacionDTO> visualizaciones;
}



