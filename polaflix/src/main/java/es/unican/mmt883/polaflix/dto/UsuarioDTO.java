package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonView(Vistas.UsuarioCompleto.class)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contraseña;

    @JsonView(Vistas.UsuarioCompleto.class)
    @NotBlank(message = "La cuenta bancaria no puede estar vacía")
    private String cuentaBancaria;

    @JsonView(Vistas.UsuarioResumen.class)
    @NotNull(message = "El tipo de suscripción es obligatorio")
    private TipoSuscripcion tipo;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<SerieDTO> seriesPendientes;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<SerieDTO> seriesTerminadas;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<SerieDTO> seriesEmpezadas;

    @JsonView(Vistas.UsuarioCompleto.class)
    @JsonIgnoreProperties("usuario")
    private Set<FacturaDTO> facturas;

    @JsonView(Vistas.UsuarioCompleto.class)
    @JsonIgnoreProperties("usuario")
    private Set<RegistroSerieUsuarioDTO> registrosSeries;
}


