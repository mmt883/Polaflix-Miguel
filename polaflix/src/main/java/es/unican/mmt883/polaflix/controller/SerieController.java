package es.unican.mmt883.polaflix.controller;

import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.model.Temporada;
import es.unican.mmt883.polaflix.model.Persona;
import es.unican.mmt883.polaflix.dto.SerieDTO;
import es.unican.mmt883.polaflix.dto.TemporadaDTO;
import es.unican.mmt883.polaflix.dto.CapituloDTO;
import es.unican.mmt883.polaflix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> getAllSeries() {
        return serieService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieDTO> getSerieById(@PathVariable Long id) {
        return serieService.findById(id)
                .map(s -> ResponseEntity.ok(toDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SerieDTO createSerie(@RequestBody Serie serie) {
        Serie saved = serieService.save(serie);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SerieDTO> updateSerie(@PathVariable Long id, @RequestBody Serie serie) {
        if (!serieService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serie.setIdSerie(id);
        Serie updated = serieService.save(serie);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable Long id) {
        if (!serieService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{serieId}/temporadas/{numTemporada}/capitulos/{numCapitulo}")
    public ResponseEntity<CapituloDTO> getCapitulo(@PathVariable Long serieId, @PathVariable int numTemporada, @PathVariable int numCapitulo) {
        Capitulo capitulo = serieService.getCapitulo(serieId, numTemporada, numCapitulo);
        if (capitulo != null) {
            CapituloDTO dto = new CapituloDTO(capitulo.getIdCapitulo(), capitulo.getNombreCapitulo(), capitulo.getDescripcion(), capitulo.getNumeroCapitulo(), capitulo.getEnlace());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{serieId}/temporadas")
    public ResponseEntity<SerieDTO> addTemporada(@PathVariable Long serieId, @RequestBody Temporada temporada) {
        try {
            Serie serie = serieService.addTemporada(serieId, temporada);
            return ResponseEntity.ok(toDTO(serie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{serieId}/temporadas/{numTemporada}/capitulos")
    public ResponseEntity<CapituloDTO> addCapituloToTemporada(@PathVariable Long serieId, @PathVariable int numTemporada, @RequestBody Capitulo capitulo) {
        try {
            Serie serie = serieService.addCapituloToTemporada(serieId, numTemporada, capitulo);
            Temporada temporada = serie.getTemporadaByNumero(numTemporada);
            Capitulo capituloAgregado = temporada.getCapituloByNumero(capitulo.getNumeroCapitulo());
            CapituloDTO dto = new CapituloDTO(capituloAgregado.getIdCapitulo(), capituloAgregado.getNombreCapitulo(), capituloAgregado.getDescripcion(), capituloAgregado.getNumeroCapitulo(), capituloAgregado.getEnlace());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private SerieDTO toDTO(Serie serie) {
        Set<Persona> actores = serie.getActores().stream()
                .map(a -> new Persona(a.getIdPersona(), a.getNombre(), a.getPrimerApellido(), a.getSegundoApellido()))
                .collect(Collectors.toSet());
        Set<Persona> creadores = serie.getCreadores().stream()
                .map(c -> new Persona(c.getIdPersona(), c.getNombre(), c.getPrimerApellido(), c.getSegundoApellido()))
                .collect(Collectors.toSet());
        Set<TemporadaDTO> temporadas = serie.getTemporadas().stream()
                .map(this::temporadaToDTO)
                .collect(Collectors.toSet());

        return new SerieDTO(serie.getIdSerie(), serie.getNombreSerie(), serie.getDescripcion(),
                serie.getCategoria(), actores, creadores, temporadas);
    }

    private TemporadaDTO temporadaToDTO(Temporada temporada) {
        Set<CapituloDTO> capitulos = temporada.getCapitulos().stream()
                .map(c -> new CapituloDTO(c.getIdCapitulo(), c.getNombreCapitulo(), c.getDescripcion(), c.getNumeroCapitulo(), c.getEnlace()))
                .collect(Collectors.toSet());
        return new TemporadaDTO(temporada.getIdTemporada(),temporada.getNumeroTemporada(), capitulos);
    }
}