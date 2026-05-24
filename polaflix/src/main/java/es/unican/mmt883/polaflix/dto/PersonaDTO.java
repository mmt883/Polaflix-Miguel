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
    
    @JsonView(Vistas.Public.class)
    private Long id;
    
    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El nombre de la persona no puede estar vacío")
    private String nombre;
    
    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;
    
    @JsonView(Vistas.Public.class)
    private String segundoApellido;
}