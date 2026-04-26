package es.unican.mmt883.polaflix.service;

import es.unican.mmt883.polaflix.model.Serie;
import es.unican.mmt883.polaflix.model.Temporada;
import es.unican.mmt883.polaflix.model.Capitulo;
import es.unican.mmt883.polaflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Transactional(readOnly = true)
    public List<Serie> findAll() {
        return serieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Serie> findById(Long id) {
        return serieRepository.findById(id);
    }

    @Transactional
    public Serie save(Serie serie) {
        return serieRepository.save(serie);
    }

    @Transactional
    public void deleteById(Long id) {
        serieRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Capitulo getCapitulo(Long serieId, int numTemporada, int numCapitulo) {
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Serie not found"));
        return serie.encontrarCapituloenTemporada(numCapitulo, numTemporada);
    }

    @Transactional
    public Serie addTemporada(Long serieId, Temporada temporada) {
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Serie not found"));
        temporada.setSerie(serie);
        serie.getTemporadas().add(temporada);
        return serieRepository.save(serie);
    }

    @Transactional
    public Serie addCapituloToTemporada(Long serieId, int numTemporada, Capitulo capitulo) {
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new RuntimeException("Serie not found"));
        Temporada temporada = serie.getTemporadaByNumero(numTemporada);
        if (temporada == null) {
            throw new RuntimeException("Temporada not found");
        }
        capitulo.setTemporada(temporada);
        temporada.getCapitulos().add(capitulo);
        return serieRepository.save(serie);
    }
}


