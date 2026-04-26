package es.unican.mmt883.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    
    @JsonView(Vistas.SerieCompleto.class)
    private Long id;
    
    @JsonView(Vistas.SerieCompleto.class)
    @NotBlank(message = "El nombre de la persona no puede estar vacío")
    private String nombre;
    
    @JsonView(Vistas.SerieCompleto.class)
    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;
    
    @JsonView(Vistas.SerieCompleto.class)
    private String segundoApellido;
}