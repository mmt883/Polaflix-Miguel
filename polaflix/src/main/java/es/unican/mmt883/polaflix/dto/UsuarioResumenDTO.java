package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.unican.mmt883.polaflix.model.TipoSuscripcion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResumenDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private TipoSuscripcion tipo;
}
