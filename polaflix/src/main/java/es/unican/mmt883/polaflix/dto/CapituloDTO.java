package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapituloDTO {
    @JsonView(Vistas.Public.class)
    private Long idCapitulo;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El nombre del capítulo no puede estar vacío")
    private String titulo;

    @JsonView(Vistas.Public.class)
    @NotNull(message = "El número del capítulo es obligatorio")
    @Min(value = 1, message = "El número del capítulo debe ser mayor que 0")
    private int numCapitulo;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "La descripción del capítulo no puede estar vacía")
    private String descripcion;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El enlace del capítulo no puede estar vacío")
    private String enlace;
}


