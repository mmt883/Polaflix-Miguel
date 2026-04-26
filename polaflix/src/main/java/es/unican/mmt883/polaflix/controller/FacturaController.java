package es.unican.mmt883.polaflix.controller;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.model.Temporada;
import es.unican.mmt883.polaflix.model.Usuario;
import es.unican.mmt883.polaflix.model.Visualizacion;
import es.unican.mmt883.polaflix.dto.CapituloDTO;
import es.unican.mmt883.polaflix.dto.FacturaDTO;
import es.unican.mmt883.polaflix.dto.SerieDTO;
import es.unican.mmt883.polaflix.dto.UsuarioDTO;
import es.unican.mmt883.polaflix.dto.VisualizacionDTO;
import es.unican.mmt883.polaflix.dto.Vistas;
import es.unican.mmt883.polaflix.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/facturas")
@Tag(name = "Facturación", description = "Gestión del sistema de cobros y desglose de visualizaciones mensuales")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Listar historial de facturas", description = "Obtiene un listado resumido de todas las facturas emitidas en el sistema.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Facturas recuperadas exitosamente") })
    @GetMapping
    @JsonView(Vistas.FacturaResumen.class)
    public List<FacturaDTO> getAllFacturas() {
        return facturaService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener detalle de factura", description = "Recupera la factura completa incluyendo el desglose de todas sus visualizaciones.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "La factura no existe")
    })
    @GetMapping("/{id}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Long id) {
        return facturaService.findById(id)
                .map(f -> ResponseEntity.ok(toDTO(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear factura manualmente", description = "Crea un registro de factura manual asociado a un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Factura creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. total negativo)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Vistas.FacturaCompleto.class)
    public FacturaDTO createFactura(@Valid @RequestBody FacturaDTO facturaDTO) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(facturaDTO.getUsuario().getIdUsuario());
        Factura factura = new Factura();
        factura.setMes(facturaDTO.getMes());
        factura.setTotal(facturaDTO.getTotal());
        factura.setUsuario(usuario);
        Factura saved = facturaService.save(factura);
        return toDTO(saved);
    }

    @Operation(summary = "Modificar factura", description = "Actualiza los metadatos o el total de una factura existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "La factura no existe")
    })
    @PutMapping("/{id}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> updateFactura(@PathVariable Long id, @Valid @RequestBody FacturaDTO facturaDTO) {
        if (!facturaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(facturaDTO.getUsuario().getIdUsuario());
        Factura factura = new Factura();
        factura.setIdFactura(id);
        factura.setMes(facturaDTO.getMes());
        factura.setTotal(facturaDTO.getTotal());
        factura.setUsuario(usuario);
        Factura updated = facturaService.save(factura);
        return ResponseEntity.ok(toDTO(updated));
    }

    @Operation(summary = "Eliminar factura", description = "Elimina un registro de factura del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Factura eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La factura no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        if (!facturaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desglose de visualizaciones", description = "Obtiene la lista aislada de capítulos vistos que componen el total de una factura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visualizaciones recuperadas exitosamente"),
            @ApiResponse(responseCode = "404", description = "La factura no existe")
    })
    @GetMapping("/{id}/visualizaciones")
    @JsonView(Vistas.VisualizacionCompleto.class)
    public ResponseEntity<Set<VisualizacionDTO>> getVisualizaciones(@PathVariable Long id) {
        try {
            List<Visualizacion> visualizaciones = facturaService.getVisualizaciones(id);
            Set<VisualizacionDTO> dtos = visualizaciones.stream()
                    .map(this::visualizacionToDTO)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Añadir visualización a factura", description = "Registra una visualización manualmente dentro de una factura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visualización añadida correctamente y total recalculado"),
            @ApiResponse(responseCode = "400", description = "Validación fallida"),
            @ApiResponse(responseCode = "404", description = "La factura no existe")
    })
    @PostMapping("/{id}/visualizaciones")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> addVisualizacion(@PathVariable Long id, @Valid @RequestBody VisualizacionDTO visualizacionDTO) {
        try {
            Serie serie = new Serie();
            serie.setIdSerie(visualizacionDTO.getSerie().getIdSerie());
            Visualizacion visualizacion = new Visualizacion();
            visualizacion.setFechaVisualizacion(visualizacionDTO.getFechaVisualizacion());
            visualizacion.setNumCapitulo(visualizacionDTO.getCapitulo().getNumCapitulo());
            visualizacion.setNumTemporada(visualizacionDTO.getNumTemporada());
            visualizacion.setPrecioCobrado(visualizacionDTO.getPrecioCobrado());
            visualizacion.setSerie(serie);
            Factura factura = facturaService.addVisualizacion(id, visualizacion);
            return ResponseEntity.ok(toDTO(factura));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar visualización", description = "Retira una visualización de una factura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visualización retirada correctamente y total recalculado"),
            @ApiResponse(responseCode = "404", description = "La factura o visualización no existen")
    })
    @DeleteMapping("/{facturaId}/visualizaciones/{visualizacionId}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> removeVisualizacion(@PathVariable Long facturaId, @PathVariable Long visualizacionId) {
        try {
            Factura factura = facturaService.removeVisualizacion(facturaId, visualizacionId);
            return ResponseEntity.ok(toDTO(factura));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private FacturaDTO toDTO(Factura factura) {
        Set<VisualizacionDTO> visualizaciones = factura.getVisualizaciones().stream()
                .map(this::visualizacionToDTO)
                .collect(Collectors.toSet());
        UsuarioDTO usuarioResumen = new UsuarioDTO();
        usuarioResumen.setIdUsuario(factura.getUsuario().getIdUsuario());
        usuarioResumen.setNombreUsuario(factura.getUsuario().getNombreUsuario());
        usuarioResumen.setContraseña(factura.getUsuario().getContraseña());
        usuarioResumen.setCuentaBancaria(factura.getUsuario().getCuentaBancaria());
        usuarioResumen.setTipo(factura.getUsuario().getTipo());
        return new FacturaDTO(factura.getIdFactura(), factura.getMes(), factura.getTotal(),
                usuarioResumen, visualizaciones);
    }

    private VisualizacionDTO visualizacionToDTO(Visualizacion v) {
        Capitulo capitulo = v.getSerie().encontrarCapituloenTemporada(v.getNumCapitulo(), v.getNumTemporada());
        CapituloDTO capituloDTO = new CapituloDTO(capitulo.getIdCapitulo(), capitulo.getNombreCapitulo(), capitulo.getNumeroCapitulo(), capitulo.getDescripcion(), capitulo.getEnlace());
        SerieDTO serieResumen = new SerieDTO(
                v.getSerie().getIdSerie(),
                v.getSerie().getNombreSerie(),
                null,
                v.getSerie().getCategoria(),
                null,
                null,
                null
        );
        return new VisualizacionDTO(v.getIdVisualizacion(), v.getFechaVisualizacion(),
                capituloDTO, v.getNumTemporada(), v.getPrecioCobrado(),
                serieResumen);
    }
}


