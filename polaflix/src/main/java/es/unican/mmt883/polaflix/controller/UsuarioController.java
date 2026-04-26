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
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de perfiles de usuario, listas de series y marcadores de lectura")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Obtiene un listado resumido de todos los usuarios registrados en el sistema.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente") })
    @GetMapping
    @JsonView(Vistas.UsuarioResumen.class)
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener detalle de usuario", description = "Devuelve la información completa del usuario, incluyendo sus series, facturas y registros de lectura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "El usuario especificado no existe")
    })
    @GetMapping("/{id}")
    @JsonView(Vistas.UsuarioCompleto.class)
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(u -> ResponseEntity.ok(toDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo usuario", description = "Registra un usuario nuevo en la plataforma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. campos vacíos)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Vistas.UsuarioCompleto.class)
    public UsuarioDTO createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setContraseña(usuarioDTO.getContraseña());
        usuario.setCuentaBancaria(usuarioDTO.getCuentaBancaria());
        usuario.setTipo(usuarioDTO.getTipo());
        Usuario saved = usuarioService.save(usuario);
        return toDTO(saved);
    }

    @Operation(summary = "Actualizar usuario", description = "Modifica los datos personales y de suscripción de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El usuario especificado no existe")
    })
    @PutMapping("/{id}")
    @JsonView(Vistas.UsuarioCompleto.class)
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        if (!usuarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setContraseña(usuarioDTO.getContraseña());
        usuario.setCuentaBancaria(usuarioDTO.getCuentaBancaria());
        usuario.setTipo(usuarioDTO.getTipo());
        Usuario updated = usuarioService.save(usuario);
        return ResponseEntity.ok(toDTO(updated));
    }

    @Operation(summary = "Eliminar usuario", description = "Borra un usuario y toda su información asociada del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El usuario especificado no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (!usuarioService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Añadir serie pendiente", description = "Añade una serie al listado de 'Para ver después' del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie añadida correctamente a pendientes"),
            @ApiResponse(responseCode = "404", description = "El usuario o la serie no existen")
    })
    @PostMapping("/{usuarioId}/series-pendientes/{serieId}")
    public ResponseEntity<UsuarioDTO> addSeriePendiente(@PathVariable Long usuarioId, @PathVariable Long serieId) {
        try {
            Usuario usuario = usuarioService.addSeriePendiente(usuarioId, serieId);
            return ResponseEntity.ok(toDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar serie pendiente", description = "Retira una serie de la lista de pendientes del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie retirada de pendientes correctamente"),
            @ApiResponse(responseCode = "404", description = "El usuario o la serie no existen en la lista")
    })
    @DeleteMapping("/{usuarioId}/series-pendientes/{serieId}")
    public ResponseEntity<UsuarioDTO> removeSeriePendiente(@PathVariable Long usuarioId, @PathVariable Long serieId) {
        try {
            Usuario usuario = usuarioService.removeSeriePendiente(usuarioId, serieId);
            return ResponseEntity.ok(toDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Consultar facturas de usuario", description = "Devuelve el historial completo de facturación mensual del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas recuperadas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}/facturas")
    @JsonView(Vistas.FacturaCompleto.class)
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
        Set<SerieDTO> pendientes = usuario.getSeriesPendientes().stream()
                .map(s -> new SerieDTO(s.getIdSerie(), s.getNombreSerie(), null, s.getCategoria(), null, null, null))
                .collect(Collectors.toSet());
        Set<SerieDTO> terminadas = usuario.getSeriesTerminadas().stream()
                .map(s -> new SerieDTO(s.getIdSerie(), s.getNombreSerie(), null, s.getCategoria(), null, null, null))
                .collect(Collectors.toSet());
        Set<SerieDTO> empezadas = usuario.getSeriesEmpezadas().stream()
                .map(s -> new SerieDTO(s.getIdSerie(), s.getNombreSerie(), null, s.getCategoria(), null, null, null))
                .collect(Collectors.toSet());
        Set<FacturaDTO> facturas = usuario.getFacturas().stream()
                .map(this::facturaToDTO)
                .collect(Collectors.toSet());
        Set<RegistroSerieUsuarioDTO> registros = usuario.getRegistros().stream()
                .map(this::registroToDTO)
                .collect(Collectors.toSet());

        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setContraseña(usuario.getContraseña());
        dto.setCuentaBancaria(usuario.getCuentaBancaria());
        dto.setTipo(usuario.getTipo());
        dto.setSeriesPendientes(pendientes);
        dto.setSeriesTerminadas(terminadas);
        dto.setSeriesEmpezadas(empezadas);
        dto.setFacturas(facturas);
        dto.setRegistrosSeries(registros);
        return dto;
    }

    @Operation(summary = "Consultar marcadores de lectura", description = "Devuelve los registros del último capítulo visto de cada serie por el usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros recuperados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}/registros")
    @JsonView(Vistas.RegistroCompleto.class)
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
        usuarioDTO.setContraseña(registro.getUsuario().getContraseña());
        usuarioDTO.setCuentaBancaria(registro.getUsuario().getCuentaBancaria());
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
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(factura.getUsuario().getIdUsuario());
        usuarioDTO.setNombreUsuario(factura.getUsuario().getNombreUsuario());
        usuarioDTO.setContraseña(factura.getUsuario().getContraseña());
        usuarioDTO.setCuentaBancaria(factura.getUsuario().getCuentaBancaria());
        usuarioDTO.setTipo(factura.getUsuario().getTipo());
        return new FacturaDTO(factura.getIdFactura(), factura.getMes(), factura.getTotal(),
                usuarioDTO, visualizaciones);
    }
}


