package es.unican.mmt883.polaflix.controller;

import es.unican.mmt883.polaflix.model.Usuario;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.dto.UsuarioDTO;
import es.unican.mmt883.polaflix.dto.UsuarioResumenDTO;
import es.unican.mmt883.polaflix.dto.VisualizacionDTO;
import es.unican.mmt883.polaflix.model.RegistroSerieUsuario;
import es.unican.mmt883.polaflix.dto.CapituloDTO;
import es.unican.mmt883.polaflix.dto.FacturaDTO;
import es.unican.mmt883.polaflix.dto.RegistroSerieUsuarioDTO;
import es.unican.mmt883.polaflix.dto.SerieResumenDTO;
import es.unican.mmt883.polaflix.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(u -> ResponseEntity.ok(toDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO createUsuario(@RequestBody Usuario usuario) {
        Usuario saved = usuarioService.save(usuario);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (!usuarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuario.setIdUsuario(id);
        Usuario updated = usuarioService.save(usuario);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (!usuarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{usuarioId}/series-pendientes/{serieId}")
    public ResponseEntity<UsuarioDTO> addSeriePendiente(@PathVariable Long usuarioId, @PathVariable Long serieId) {
        try {
            Usuario usuario = usuarioService.addSeriePendiente(usuarioId, serieId);
            return ResponseEntity.ok(toDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{usuarioId}/series-pendientes/{serieId}")
    public ResponseEntity<UsuarioDTO> removeSeriePendiente(@PathVariable Long usuarioId, @PathVariable Long serieId) {
        try {
            Usuario usuario = usuarioService.removeSeriePendiente(usuarioId, serieId);
            return ResponseEntity.ok(toDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/facturas")
    public ResponseEntity<Set<FacturaDTO>> getFacturas(@PathVariable Long id) {
        try {
            List<Factura> facturas = usuarioService.getFacturas(id);
            Set<FacturaDTO> dtos = facturas.stream()
                    .map(this::facturaToDTO)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        Set<Long> pendientes = usuario.getSeriesPendientes().stream()
                .map(Serie::getIdSerie)
                .collect(Collectors.toSet());
        Set<Long> terminadas = usuario.getSeriesTerminadas().stream()
                .map(Serie::getIdSerie)
                .collect(Collectors.toSet());
        Set<Long> empezadas = usuario.getSeriesEmpezadas().stream()
                .map(Serie::getIdSerie)
                .collect(Collectors.toSet());
        Set<Long> facturas = usuario.getFacturas().stream()
                .map(Factura::getIdFactura)
                .collect(Collectors.toSet());
        return new UsuarioDTO(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getTipo(),
                pendientes, terminadas, empezadas, facturas);
    }

    @GetMapping("/{id}/registros")
    public ResponseEntity<List<RegistroSerieUsuarioDTO>> getRegistros(@PathVariable Long id) {
        try {
            List<RegistroSerieUsuario> registros = usuarioService.getRegistros(id);
            List<RegistroSerieUsuarioDTO> dtos = registros.stream()
                    .map(this::registroToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{usuarioId}/registros/{serieId}/temporadas/{numTemporada}/capitulos/{numCapitulo}")
    public ResponseEntity<RegistroSerieUsuarioDTO> updateRegistro(
            @PathVariable Long usuarioId,
            @PathVariable Long serieId,
            @PathVariable int numTemporada,
            @PathVariable int numCapitulo) {
        try {
            Usuario usuario = usuarioService.marcarCapituloComoVisto(usuarioId, serieId, numTemporada, numCapitulo);
            
            RegistroSerieUsuario registros = null;
            for (RegistroSerieUsuario r : usuario.getRegistros()) {
                if (r.getSerie().getIdSerie().equals(serieId)) {
                    registros = r;
                    break;
                }
            }
            if (registros == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(registroToDTO(registros));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private RegistroSerieUsuarioDTO registroToDTO(RegistroSerieUsuario registro) {
        UsuarioResumenDTO usuarioResumen = new UsuarioResumenDTO(
                registro.getUsuario().getIdUsuario(),
                registro.getUsuario().getNombreUsuario(),
                registro.getUsuario().getTipo()
        );
        Set<Integer> temporadasIds = registro.getSerie().getTemporadas().keySet();
        SerieResumenDTO serieResumen = new SerieResumenDTO(
                registro.getSerie().getIdSerie(),
                registro.getSerie().getNombreSerie(),
                temporadasIds,
                registro.getSerie().getCategoria()
        );
        return new RegistroSerieUsuarioDTO(
                registro.getId(),
                registro.getUltimoCapituloVisto().getTemporada().getNumeroTemporada(),
                registro.getUltimoCapituloVisto().getNumeroCapitulo(),
                usuarioResumen,
                serieResumen
        );
    }

    private FacturaDTO facturaToDTO(Factura factura) {
        Set<VisualizacionDTO> visualizaciones = factura.getVisualizaciones().stream()
                .map(v -> {
                    Capitulo capitulo = v.getSerie().encontrarCapituloenTemporada(v.getNumCapitulo(), v.getNumTemporada());
                    CapituloDTO capituloDTO = new CapituloDTO(capitulo.getNombreCapitulo(), capitulo.getDescripcion(), capitulo.getNumeroCapitulo(), capitulo.getEnlace());
                    Set<Integer> temporadasIds = v.getSerie().getTemporadas().keySet();
                    SerieResumenDTO serieResumen = new SerieResumenDTO(
                            v.getSerie().getIdSerie(),
                            v.getSerie().getNombreSerie(),
                            temporadasIds,
                            v.getSerie().getCategoria()
                    );
                    return new VisualizacionDTO(v.getIdVisualizacion(), v.getFechaVisualizacion(), capituloDTO, v.getNumTemporada(), v.getPrecioCobrado(), serieResumen);
                })
                .collect(Collectors.toSet());
        UsuarioResumenDTO usuarioDTO = new UsuarioResumenDTO(factura.getUsuario().getIdUsuario(), factura.getUsuario().getNombreUsuario(), factura.getUsuario().getTipo());
        return new FacturaDTO(factura.getIdFactura(), factura.getMes(), factura.getTotal(),
                usuarioDTO, visualizaciones);
    }
}