package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Long idFactura;
    private String mes;
    private Float total;
    private UsuarioResumenDTO usuario;
    private Set<Long> visualizacionesIds;
}
