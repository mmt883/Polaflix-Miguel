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
    
    @Column(nullable = false)
    private String nombreCapitulo;
    @Column(nullable = false)
    private int numeroCapitulo;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String enlace;

    @ManyToOne
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;
}