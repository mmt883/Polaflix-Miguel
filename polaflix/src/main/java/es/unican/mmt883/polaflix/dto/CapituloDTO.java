package es.unican.mmt883.polaflix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapituloDTO {
    private String titulo;
    private String descripcion;
    private int numCapitulo;
    private String enlace;
}