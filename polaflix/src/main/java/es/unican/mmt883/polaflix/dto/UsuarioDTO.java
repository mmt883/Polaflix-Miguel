package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.unican.mmt883.polaflix.model.TipoSuscripcion;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private TipoSuscripcion tipo;
    private Set<Long> seriesPendientesIds;
    private Set<Long> seriesTerminadasIds;
    private Set<Long> seriesEmpezadasIds;
    private Set<Long> facturasIds;
}