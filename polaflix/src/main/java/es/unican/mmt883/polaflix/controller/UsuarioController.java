package es.unican.mmt883.polaflix.controller;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.model.Usuario;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.dto.UsuarioDTO;
import es.unican.mmt883.polaflix.dto.VisualizacionDTO;
import es.unican.mmt883.polaflix.model.RegistroSerieUsuario;
import es.unican.mmt883.polaflix.dto.CapituloDTO;
import es.unican.mmt883.polaflix.dto.FacturaDTO;
import es.unican.mmt883.polaflix.dto.RegistroSerieUsuarioDTO;
import es.unican.mmt883.polaflix.dto.SerieDTO;
import es.unican.mmt883.polaflix.dto.Vistas;
import es.unican.mmt883.polaflix.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de perfiles de usuario, listas de series y marcadores de lectura")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener usuario", description = "Devuelve la información de usuario con solo los campos esenciales: id, nombre, tipo y listas de series.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "El usuario especificado no existe")
    })
    @GetMapping("/{id}")
    @JsonView(Vistas.UsuarioResumen.class)
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(u -> ResponseEntity.ok(toDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Añadir serie pendiente", description = "Añade una serie al listado de 'Para ver después' del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie añadida correctamente a pendientes"),
            @ApiResponse(responseCode = "404", description = "El usuario o la serie no existen")
    })
    @PostMapping("/{usuarioId}/series-pendientes/{serieId}")
    @JsonView(Vistas.UsuarioResumen.class)
    public ResponseEntity<UsuarioDTO> addSeriePendiente(@PathVariable Long usuarioId, @PathVariable Long serieId) {
        try {
            Usuario usuario = usuarioService.addSeriePendiente(usuarioId, serieId);
            return ResponseEntity.ok(toDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Consultar facturas de usuario por mes", description = "Devuelve las facturas del usuario para el mes especificado en la URI. Formato esperado: yyyy-MM (ej: 2026-05).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas recuperadas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}/facturas/{mes}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<Set<FacturaDTO>> getFacturasPorMes(@PathVariable Long id, @PathVariable String mes) {
        try {
            List<Factura> facturas = usuarioService.getFacturas(id, mes);
            Set<FacturaDTO> dtos = facturas.stream()
                    .map(this::facturaToDTO)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        Set<SerieDTO> pendientes = usuario.getSeriesPendientes().stream()
                .map(s -> new SerieDTO(s.getIdSerie(), s.getNombreSerie(), s.getDescripcion(), s.getCategoria(), null, null, null))
                .collect(Collectors.toSet());
        Set<SerieDTO> terminadas = usuario.getSeriesTerminadas().stream()
                .map(s -> new SerieDTO(s.getIdSerie(), s.getNombreSerie(), s.getDescripcion(), s.getCategoria(), null, null, null))
                .collect(Collectors.toSet());
        Set<SerieDTO> empezadas = usuario.getSeriesEmpezadas().stream()
                .map(s -> new SerieDTO(s.getIdSerie(), s.getNombreSerie(), s.getDescripcion(), s.getCategoria(), null, null, null))
                .collect(Collectors.toSet());
        Set<CapituloDTO> capitulosVistos = usuario.getCapitulosVistos().stream()
            .map(c -> new CapituloDTO(c.getIdCapitulo(), c.getNombreCapitulo(), c.getNumeroCapitulo(), c.getDescripcion(), c.getEnlace()))
            .collect(Collectors.toSet());

        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setTipo(usuario.getTipo());
        dto.setSeriesPendientes(pendientes);
        dto.setSeriesTerminadas(terminadas);
        dto.setSeriesEmpezadas(empezadas);
        dto.setCapitulosVistos(capitulosVistos);
        return dto;
    }

    //Operacion para marcar un capítulo como visto, actualizando el registro de la serie para el usuario
    @Operation(summary = "Simular visualización (Actualizar marcador)", description = "Marca un capítulo como visto, actualizando el registro de 'Continuar viendo' y generando el cargo en la factura si procede.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado y visualización cobrada correctamente"),
            @ApiResponse(responseCode = "404", description = "El usuario, serie o capítulo especificados no existen")
    })
    @PutMapping("/{usuarioId}/registros/{serieId}/temporadas/{numTemporada}/capitulos/{numCapitulo}")
    @JsonView(Vistas.RegistroCompleto.class)
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
        Capitulo ultimoCapitulo = registro.getUltimoCapituloVisto();
        CapituloDTO ultimoCapituloDTO = new CapituloDTO(ultimoCapitulo.getIdCapitulo(), ultimoCapitulo.getNombreCapitulo(), ultimoCapitulo.getNumeroCapitulo(), ultimoCapitulo.getDescripcion(), ultimoCapitulo.getEnlace());
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(registro.getUsuario().getIdUsuario());
        usuarioDTO.setNombreUsuario(registro.getUsuario().getNombreUsuario());
        usuarioDTO.setTipo(registro.getUsuario().getTipo());
        SerieDTO serieDTO = new SerieDTO(registro.getSerie().getIdSerie(), registro.getSerie().getNombreSerie(), null,
                registro.getSerie().getCategoria(), null, null, null);
        return new RegistroSerieUsuarioDTO(registro.getIdRegistro(), ultimoCapituloDTO, usuarioDTO, serieDTO);
    }

    private FacturaDTO facturaToDTO(Factura factura) {
        Set<VisualizacionDTO> visualizaciones = factura.getVisualizaciones().stream()
                .map(v -> {
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
                    return new VisualizacionDTO(v.getIdVisualizacion(), v.getFechaVisualizacion(), capituloDTO, v.getNumTemporada(), v.getPrecioCobrado(), serieResumen);
                })
                .collect(Collectors.toSet());
        return new FacturaDTO(factura.getIdFactura(), factura.getMes(), factura.getTotal(), visualizaciones);
    }
}


