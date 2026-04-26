package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemporadaDTO {
    @JsonView(Vistas.Public.class)
    private Long idTemporada;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El nombre de la temporada no puede estar vacío")
    private String nombreTemporada;

    @JsonView(Vistas.Public.class)
    @NotNull(message = "El número de la temporada es obligatorio")
    @Min(value = 1, message = "El número de la temporada debe ser mayor que 0")
    private int numeroTemporada;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "La descripción de la temporada no puede estar vacía")
    private String descripcion;

    @JsonView(Vistas.Public.class)
    private Set<CapituloDTO> capitulos;
}


