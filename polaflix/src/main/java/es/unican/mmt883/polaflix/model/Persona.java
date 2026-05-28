package es.unican.mmt883.polaflix.model;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.vistas.Vistas;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPersona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Vistas.Public.class)
    private Long idPersona;
    
    @Column(nullable = false)
    @JsonView(Vistas.Public.class)
    private String nombre;
    
    @Column(nullable = false)
    @JsonView(Vistas.Public.class)
    private String primerApellido;
    
    @JsonView(Vistas.Public.class)
    private String segundoApellido;
}


