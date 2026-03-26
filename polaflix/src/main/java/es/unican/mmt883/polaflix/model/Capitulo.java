package es.unican.mmt883.polaflix.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCapitulo;
    
    private String nombreCapitulo;
    private int numeroCapitulo;
    private String descripcion;
    private String enlace;

    @ManyToOne
    @JoinColumn(name = "temporada_id")
    private Temporada temporada;

    @Enumerated(EnumType.STRING)
    private EstadoVisualizacion estado;
}