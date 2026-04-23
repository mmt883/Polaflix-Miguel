package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.unican.mmt883.polaflix.model.CategoriaSerie;
import es.unican.mmt883.polaflix.model.Persona;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieDTO {
    private Long idSerie;
    private String nombreSerie;
    private String descripcion;
    private CategoriaSerie categoria;
    private Set<Persona> actores;
    private Set<Persona> creadores;
    private Set<TemporadaDTO> temporadas;
}