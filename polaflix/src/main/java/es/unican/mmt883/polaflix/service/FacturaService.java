package es.unican.mmt883.polaflix.service;

import es.unican.mmt883.polaflix.model.Factura;
import es.unican.mmt883.polaflix.model.Visualizacion;
import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.repository.FacturaRepository;
import es.unican.mmt883.polaflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Transactional(readOnly = true)
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Factura> findById(Long id) {
        return facturaRepository.findById(id);
    }

    @Transactional
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Transactional
    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Set<Visualizacion> getVisualizaciones(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId).orElseThrow(() -> new RuntimeException("Factura not found"));
        return factura.getVisualizaciones();
    }

    @Transactional
    public Factura addVisualizacion(Long facturaId, Visualizacion visualizacion) {
        Factura factura = facturaRepository.findById(facturaId).orElseThrow(() -> new RuntimeException("Factura not found"));
        // Validar que la serie existe
        if (visualizacion.getSerie() != null && visualizacion.getSerie().getIdSerie() != null) {
            Serie serie = serieRepository.findById(visualizacion.getSerie().getIdSerie()).orElseThrow(() -> new RuntimeException("Serie not found"));
            visualizacion.setSerie(serie);
        }
        factura.getVisualizaciones().add(visualizacion);
        return facturaRepository.save(factura);
    }

    @Transactional
    public Factura removeVisualizacion(Long facturaId, Long visualizacionId) {
        Factura factura = facturaRepository.findById(facturaId).orElseThrow(() -> new RuntimeException("Factura not found"));
        Visualizacion toRemove = factura.getVisualizaciones().stream()
                .filter(v -> v.getIdVisualizacion().equals(visualizacionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Visualizacion not found in factura"));
        factura.getVisualizaciones().remove(toRemove);
        return facturaRepository.save(factura);
    }
}