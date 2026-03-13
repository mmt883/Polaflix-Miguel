package Clases;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Persona {

    private int idPersona;
    
    private String nombre;
    private String fechaNacimiento;
    private String dni;
}