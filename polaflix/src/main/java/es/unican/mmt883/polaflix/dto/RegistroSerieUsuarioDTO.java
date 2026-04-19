package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSerieUsuarioDTO {
    private Long id;
    private int ultimoCapituloNumTemporada;
    private int ultimoCapituloNumCapitulo;
    private Long usuarioId;
    private Long serieId;
}