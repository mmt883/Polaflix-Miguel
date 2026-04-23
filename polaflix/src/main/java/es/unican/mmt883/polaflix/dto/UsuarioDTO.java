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
    private Set<SerieResumenDTO> seriesPendientes;
    private Set<SerieResumenDTO> seriesTerminadas;
    private Set<SerieResumenDTO> seriesEmpezadas;
    private Set<FacturaDTO> facturas;
    private Set<RegistroSerieUsuarioDTO> registrosSeries;
}