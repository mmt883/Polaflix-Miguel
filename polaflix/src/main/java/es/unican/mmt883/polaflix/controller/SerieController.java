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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
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

    @Operation(summary = "Crear nueva serie", description = "Añade una nueva serie al catálogo de Polaflix.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serie creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Validación fallida (ej. datos obligatorios ausentes)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Vistas.SerieCompleto.class)
    public SerieDTO createSerie(@Valid @RequestBody SerieDTO serieDTO) {
        Serie serie = new Serie();
        serie.setNombreSerie(serieDTO.getNombreSerie());
        serie.setDescripcion(serieDTO.getDescripcion());
        serie.setCategoria(serieDTO.getCategoria());

        // Mapeo de Actores desde el DTO a la Entidad
        if (serieDTO.getActores() != null) {
            Set<Persona> actores = serieDTO.getActores().stream().map(a -> {
                Persona p = new Persona();
                p.setIdPersona(a.getId());
                p.setNombre(a.getNombre());
                p.setPrimerApellido(a.getPrimerApellido());
                p.setSegundoApellido(a.getSegundoApellido());
                return p;
            }).collect(Collectors.toSet());
            serie.setActores(actores);
        }

        if (serieDTO.getCreadores() != null) {
            Set<Persona> creadores = serieDTO.getCreadores().stream().map(c -> {
                Persona p = new Persona();
                p.setIdPersona(c.getId());
                p.setNombre(c.getNombre());
                p.setPrimerApellido(c.getPrimerApellido());
                p.setSegundoApellido(c.getSegundoApellido());
                return p;
            }).collect(Collectors.toSet());
            serie.setCreadores(creadores);
        }

        Serie saved = serieService.save(serie);
        return toDTO(saved);
    }

    @Operation(summary = "Actualizar información de serie", description = "Modifica los metadatos de una serie existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Validación fallida"),
            @ApiResponse(responseCode = "404", description = "La serie no existe")
    })
    @PutMapping("/{id}")
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<SerieDTO> updateSerie(@PathVariable Long id, @Valid @RequestBody SerieDTO serieDTO) {
        if (!serieService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Serie serie = new Serie();
        serie.setIdSerie(id);
        serie.setNombreSerie(serieDTO.getNombreSerie());
        serie.setDescripcion(serieDTO.getDescripcion());
        serie.setCategoria(serieDTO.getCategoria());
        Serie updated = serieService.save(serie);
        return ResponseEntity.ok(toDTO(updated));
    }

    @Operation(summary = "Eliminar serie", description = "Elimina permanentemente una serie del catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Serie eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La serie no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable Long id) {
        if (!serieService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener detalle de un capítulo", description = "Recupera la información y el enlace de un capítulo específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capítulo encontrado"),
            @ApiResponse(responseCode = "404", description = "La serie, temporada o capítulo no existen")
    })
    @GetMapping("/{serieId}/temporadas/{numTemporada}/capitulos/{numCapitulo}")
    public ResponseEntity<CapituloDTO> getCapitulo(@PathVariable Long serieId, @PathVariable int numTemporada, @PathVariable int numCapitulo) {
        Capitulo capitulo = serieService.getCapitulo(serieId, numTemporada, numCapitulo);
        if (capitulo != null) {
            CapituloDTO dto = new CapituloDTO(capitulo.getIdCapitulo(), capitulo.getNombreCapitulo(), capitulo.getNumeroCapitulo(), capitulo.getDescripcion(), capitulo.getEnlace());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Añadir temporada a serie", description = "Crea y asocia una nueva temporada a una serie existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada añadida correctamente"),
            @ApiResponse(responseCode = "400", description = "Validación de temporada fallida"),
            @ApiResponse(responseCode = "404", description = "La serie especificada no existe")
    })
    @PostMapping("/{serieId}/temporadas")
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<SerieDTO> addTemporada(@PathVariable Long serieId, @Valid @RequestBody TemporadaDTO temporadaDTO) {
        try {
            Temporada temporada = new Temporada();
            temporada.setNombreTemporada(temporadaDTO.getNombreTemporada());
            temporada.setNumeroTemporada(temporadaDTO.getNumeroTemporada());
            temporada.setDescripcion(temporadaDTO.getDescripcion());
            Serie serie = serieService.addTemporada(serieId, temporada);
            return ResponseEntity.ok(toDTO(serie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Añadir capítulo a temporada", description = "Crea y añade un nuevo capítulo a una temporada concreta de una serie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capítulo añadido correctamente"),
            @ApiResponse(responseCode = "400", description = "Validación de capítulo fallida"),
            @ApiResponse(responseCode = "404", description = "La serie o la temporada no existen")
    })
    @PostMapping("/{serieId}/temporadas/{numTemporada}/capitulos")
    @JsonView(Vistas.Public.class)
    public ResponseEntity<CapituloDTO> addCapituloToTemporada(@PathVariable Long serieId, @PathVariable int numTemporada, @Valid @RequestBody CapituloDTO capituloDTO) {
        try {
            Capitulo capitulo = new Capitulo();
            capitulo.setNombreCapitulo(capituloDTO.getTitulo());
            capitulo.setNumeroCapitulo(capituloDTO.getNumCapitulo());
            capitulo.setDescripcion(capituloDTO.getDescripcion());
            capitulo.setEnlace(capituloDTO.getEnlace());
            Serie serie = serieService.addCapituloToTemporada(serieId, numTemporada, capitulo);
            Temporada temporada = serie.getTemporadaByNumero(numTemporada);
            Capitulo capituloAgregado = temporada.getCapituloByNumero(capitulo.getNumeroCapitulo());
            CapituloDTO dto = new CapituloDTO(capituloAgregado.getIdCapitulo(), capituloAgregado.getNombreCapitulo(), capituloAgregado.getNumeroCapitulo(), capituloAgregado.getDescripcion(), capituloAgregado.getEnlace());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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


