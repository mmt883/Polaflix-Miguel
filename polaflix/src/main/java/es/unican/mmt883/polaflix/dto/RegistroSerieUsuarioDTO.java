package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistroSerieUsuarioDTO {
    @JsonView(Vistas.RegistroCompleto.class)
    private Long idRegistro;

    @JsonView(Vistas.RegistroCompleto.class)
    private CapituloDTO ultimoCapitulo;

    @JsonView(Vistas.RegistroCompleto.class)
    private UsuarioDTO usuario;

    @JsonView(Vistas.RegistroCompleto.class)
    private SerieDTO serie;
}


