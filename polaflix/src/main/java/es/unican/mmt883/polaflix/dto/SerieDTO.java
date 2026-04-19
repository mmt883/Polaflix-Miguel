package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.unican.mmt883.polaflix.model.CategoriaSerie;

import java.util.Set;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieDTO {
    private Long idSerie;
    private String nombreSerie;
    private String descripcion;
    private CategoriaSerie categoria;
    private Set<Long> actoresIds;
    private Set<Long> creadoresIds;
    private Map<Integer, TemporadaDTO> temporadas;
}