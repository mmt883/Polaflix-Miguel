package es.unican.mmt883.polaflix.controller;

import com.fasterxml.jackson.annotation.JsonView;
import es.unican.mmt883.polaflix.model.Usuario;
import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.model.RegistroSerieUsuario;
import es.unican.mmt883.polaflix.vistas.Vistas;
import es.unican.mmt883.polaflix.service.UsuarioService;
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
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Añadir serie pendiente", description = "Añade una serie al listado de 'Para ver después' del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie añadida correctamente a pendientes"),
            @ApiResponse(responseCode = "400", description = "La serie ya está empezada o terminada"),
            @ApiResponse(responseCode = "404", description = "El usuario o la serie no existen")
    })
    @PostMapping("/{usuarioId}/series-pendientes/{serieId}")
    @JsonView(Vistas.UsuarioResumen.class)
    public ResponseEntity<Usuario> addSeriePendiente(@PathVariable Long usuarioId, @PathVariable Long serieId) {
        Usuario usuario = usuarioService.addSeriePendiente(usuarioId, serieId);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Consultar facturas de usuario por mes", description = "Devuelve las facturas del usuario para el mes especificado en la URI. Formato esperado: yyyy-MM (ej: 2026-05).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas recuperadas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}/facturas/{mes}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<List<Factura>> getFacturasPorMes(@PathVariable Long id, @PathVariable String mes) {
        List<Factura> facturas = usuarioService.getFacturas(id, mes);
        return ResponseEntity.ok(facturas);
    }

    @Operation(summary = "Simular visualización (Actualizar marcador)", description = "Marca un capítulo como visto, actualizando el registro de 'Continuar viendo' y generando el cargo en la factura si procede.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado y visualización cobrada correctamente"),
            @ApiResponse(responseCode = "400", description = "El capítulo ya está marcado como visto"),
            @ApiResponse(responseCode = "404", description = "El usuario, serie o capítulo especificados no existen"),
    })
    @PutMapping("/{usuarioId}/registros/{serieId}/temporadas/{numTemporada}/capitulos/{numCapitulo}")
    @JsonView(Vistas.RegistroCompleto.class)
    public ResponseEntity<RegistroSerieUsuario> updateRegistro(
            @PathVariable Long usuarioId,
            @PathVariable Long serieId,
            @PathVariable int numTemporada,
            @PathVariable int numCapitulo) {
        RegistroSerieUsuario registro = usuarioService.marcarCapituloComoVisto(usuarioId, serieId, numTemporada, numCapitulo);
        return ResponseEntity.ok(registro);
    }
}


