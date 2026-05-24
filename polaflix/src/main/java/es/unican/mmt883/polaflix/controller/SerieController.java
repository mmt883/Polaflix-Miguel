package es.unican.mmt883.polaflix.controller;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.model.Temporada;
import es.unican.mmt883.polaflix.model.Persona;
import es.unican.mmt883.polaflix.dto.SerieDTO;
import es.unican.mmt883.polaflix.dto.TemporadaDTO;
import es.unican.mmt883.polaflix.dto.CapituloDTO;
import es.unican.mmt883.polaflix.dto.PersonaDTO;
import es.unican.mmt883.polaflix.dto.Vistas;
import es.unican.mmt883.polaflix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/series")
@Tag(name = "Catálogo de Series", description = "Operaciones CRUD sobre las series, temporadas, capítulos y elenco")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @Operation(summary = "Listar catálogo de series", description = "Devuelve todas las series disponibles en un formato resumido (sin temporadas).")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Catálogo recuperado exitosamente") })
    @GetMapping
    @JsonView(Vistas.SerieResumen.class)
    public List<SerieDTO> getAllSeries() {
        return serieService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener detalle de serie", description = "Recupera la información completa de la serie, incluyendo temporadas, capítulos, actores y creadores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie encontrada"),
            @ApiResponse(responseCode = "404", description = "La serie especificada no existe")
    })
    @GetMapping("/{id}")
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<SerieDTO> getSerieById(@PathVariable Long id) {
        return serieService.findById(id)
                .map(s -> ResponseEntity.ok(toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    private SerieDTO toDTO(Serie serie) {
        Set<PersonaDTO> actores = serie.getActores().stream()
                .map(a -> new PersonaDTO(a.getIdPersona(), a.getNombre(), a.getPrimerApellido(), a.getSegundoApellido()))
                .collect(Collectors.toSet());
        Set<PersonaDTO> creadores = serie.getCreadores().stream()
                .map(c -> new PersonaDTO(c.getIdPersona(), c.getNombre(), c.getPrimerApellido(), c.getSegundoApellido()))
                .collect(Collectors.toSet());
        Set<TemporadaDTO> temporadas = serie.getTemporadas().stream()
                .map(this::temporadaToDTO)
                .collect(Collectors.toSet());

        return new SerieDTO(serie.getIdSerie(), serie.getNombreSerie(), serie.getDescripcion(),
                serie.getCategoria(), actores, creadores, temporadas);
    }

    private TemporadaDTO temporadaToDTO(Temporada temporada) {
        Set<CapituloDTO> capitulos = temporada.getCapitulos().stream()
                .map(c -> new CapituloDTO(c.getIdCapitulo(), c.getNombreCapitulo(), c.getNumeroCapitulo(), c.getDescripcion(), c.getEnlace()))
                .collect(Collectors.toSet());
        return new TemporadaDTO(temporada.getIdTemporada(), temporada.getNombreTemporada(), temporada.getNumeroTemporada(), temporada.getDescripcion(), capitulos);
    }
}


