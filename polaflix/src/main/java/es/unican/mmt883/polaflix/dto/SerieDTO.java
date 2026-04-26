package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.unican.mmt883.polaflix.model.CategoriaSerie;
import es.unican.mmt883.polaflix.model.Persona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerieDTO {
    @JsonView(Vistas.SerieResumen.class)
    private Long idSerie;

    @JsonView(Vistas.SerieResumen.class)
    @NotBlank(message = "El nombre de la serie no puede estar vacío")
    private String nombreSerie;

    @JsonView(Vistas.SerieCompleto.class)
    @NotBlank(message = "La descripción de la serie no puede estar vacía")
    private String descripcion;

    @JsonView(Vistas.SerieResumen.class)
    @NotNull(message = "La categoría de la serie es obligatoria")
    private CategoriaSerie categoria;

    @JsonView(Vistas.SerieCompleto.class)
    private Set<PersonaDTO> actores;

    @JsonView(Vistas.SerieCompleto.class)
    private Set<PersonaDTO> creadores;

    @JsonView(Vistas.SerieCompleto.class)
    private Set<TemporadaDTO> temporadas;
}


