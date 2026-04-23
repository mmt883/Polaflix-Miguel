package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporadaDTO {
    private Long idTemporada;
    private int numeroTemporada;
    private Set<CapituloDTO> capitulos;
}