package es.unican.mmt883.polaflix.controller;

import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.model.Visualizacion;
import es.unican.mmt883.polaflix.dto.FacturaDTO;
import es.unican.mmt883.polaflix.dto.UsuarioResumenDTO;
import es.unican.mmt883.polaflix.dto.VisualizacionDTO;
import es.unican.mmt883.polaflix.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<FacturaDTO> getAllFacturas() {
        return facturaService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Long id) {
        return facturaService.findById(id)
                .map(f -> ResponseEntity.ok(toDTO(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaDTO createFactura(@RequestBody Factura factura) {
        Factura saved = facturaService.save(factura);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaDTO> updateFactura(@PathVariable Long id, @RequestBody Factura factura) {
        if (!facturaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        factura.setIdFactura(id);
        Factura updated = facturaService.save(factura);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        if (!facturaService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/visualizaciones")
    public ResponseEntity<Set<VisualizacionDTO>> getVisualizaciones(@PathVariable Long id) {
        try {
            Set<Visualizacion> visualizaciones = facturaService.getVisualizaciones(id);
            Set<VisualizacionDTO> dtos = visualizaciones.stream()
                    .map(this::visualizacionToDTO)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/visualizaciones")
    public ResponseEntity<FacturaDTO> addVisualizacion(@PathVariable Long id, @RequestBody Visualizacion visualizacion) {
        try {
            Factura factura = facturaService.addVisualizacion(id, visualizacion);
            return ResponseEntity.ok(toDTO(factura));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{facturaId}/visualizaciones/{visualizacionId}")
    public ResponseEntity<FacturaDTO> removeVisualizacion(@PathVariable Long facturaId, @PathVariable Long visualizacionId) {
        try {
            Factura factura = facturaService.removeVisualizacion(facturaId, visualizacionId);
            return ResponseEntity.ok(toDTO(factura));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private FacturaDTO toDTO(Factura factura) {
        Set<Long> visualizaciones = factura.getVisualizaciones().stream()
                .map(v -> v.getIdVisualizacion())
                .collect(Collectors.toSet());
        UsuarioResumenDTO usuarioResumen = new UsuarioResumenDTO(
                factura.getUsuario().getIdUsuario(),
                factura.getUsuario().getNombreUsuario(),
                factura.getUsuario().getTipo());
        return new FacturaDTO(factura.getIdFactura(), factura.getMes(), factura.getTotal(),
                usuarioResumen, visualizaciones);
    }

    private VisualizacionDTO visualizacionToDTO(Visualizacion v) {
        return new VisualizacionDTO(v.getIdVisualizacion(), v.getFechaVisualizacion(),
                v.getNumCapitulo(), v.getNumTemporada(), v.getPrecioCobrado(),
                v.getSerie().getIdSerie());
    }
}