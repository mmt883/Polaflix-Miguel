package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import es.unican.mmt883.polaflix.model.CategoriaSerie;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieResumenDTO {
    private Long idSerie;
    private String nombreSerie;
    private Set<Integer> temporadasIds;
    private CategoriaSerie categoria;
}