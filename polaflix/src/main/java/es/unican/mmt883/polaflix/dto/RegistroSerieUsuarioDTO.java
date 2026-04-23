package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSerieUsuarioDTO {
    private Long idRegistro;
    private CapituloDTO ultimoCapitulo;
    private UsuarioResumenDTO usuario;
    private SerieResumenDTO serie;
}