package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.unican.mmt883.polaflix.model.TipoSuscripcion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {
    @JsonView(Vistas.UsuarioResumen.class)
    private Long idUsuario;

    @JsonView(Vistas.UsuarioResumen.class)
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String nombreUsuario;

    @JsonView(Vistas.UsuarioResumen.class)
    @NotNull(message = "El tipo de suscripción es obligatorio")
    private TipoSuscripcion tipo;

    @JsonView(Vistas.UsuarioResumen.class)
    private Set<SerieDTO> seriesPendientes;

    @JsonView(Vistas.UsuarioResumen.class)
    private Set<SerieDTO> seriesTerminadas;

    @JsonView(Vistas.UsuarioResumen.class)
    private Set<SerieDTO> seriesEmpezadas;

    @JsonView(Vistas.UsuarioResumen.class)
    private Set<CapituloDTO> capitulosVistos;
}


