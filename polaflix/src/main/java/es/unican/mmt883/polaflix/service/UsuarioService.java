package es.unican.mmt883.polaflix.service;

import es.unican.mmt883.polaflix.model.Usuario;
import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.model.RegistroSerieUsuario;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.repository.UsuarioRepository;
import es.unican.mmt883.polaflix.repository.SerieRepository;
import es.unican.mmt883.polaflix.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Usuario addSeriePendiente(Long usuarioId, Long serieId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario not found"));
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Serie not found"));
        usuario.getSeriesPendientes().add(serie);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario removeSeriePendiente(Long usuarioId, Long serieId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario not found"));
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Serie not found"));
        usuario.getSeriesPendientes().remove(serie);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Factura> getFacturas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario not found"));
        return usuario.getFacturas();
    }

    @Transactional(readOnly = true)
    public List<RegistroSerieUsuario> getRegistros(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario not found"));
        return usuario.getRegistros();
    }

    @Transactional
    public Usuario marcarCapituloComoVisto(Long usuarioId, Long serieId, int numTemporada, int numCapitulo) {
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario not found"));
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Serie not found"));
        
        Capitulo capitulo = serie.encontrarCapituloenTemporada(numCapitulo, numTemporada);
        if (capitulo == null) {
            throw new RuntimeException("Capitulo not found");
        }

        usuario.marcarCapituloComoVisto(capitulo);

        return usuarioRepository.save(usuario);
}
}


