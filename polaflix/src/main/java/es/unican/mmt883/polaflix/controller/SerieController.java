package es.unican.mmt883.polaflix.controller;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.vistas.Vistas;
import es.unican.mmt883.polaflix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
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
    public List<Serie> getAllSeries() {
        return serieService.findAll();
    }

    @Operation(summary = "Obtener detalle de serie", description = "Recupera la información completa de la serie, incluyendo temporadas, capítulos, actores y creadores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie encontrada"),
            @ApiResponse(responseCode = "404", description = "La serie especificada no existe")
    })
    @GetMapping("/{id}")
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<Serie> getSerieById(@PathVariable Long id) {
        return serieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


