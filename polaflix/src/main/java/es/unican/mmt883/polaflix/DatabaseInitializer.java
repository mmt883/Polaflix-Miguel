package es.unican.mmt883.polaflix;

import es.unican.mmt883.polaflix.model.*;
import es.unican.mmt883.polaflix.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final SerieRepository serieRepository;
    private final UsuarioRepository usuarioRepository;
    private final FacturaRepository facturaRepository;
    private final RegistroSerieUsuarioRepository registroRepository;

    public DatabaseInitializer(SerieRepository serieRepository,
                               UsuarioRepository usuarioRepository,
                               FacturaRepository facturaRepository,
                               RegistroSerieUsuarioRepository registroRepository) {
        this.serieRepository = serieRepository;
        this.usuarioRepository = usuarioRepository;
        this.facturaRepository = facturaRepository;
        this.registroRepository = registroRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (serieRepository.count() > 0) {
            return; // ya inicializado
        }

        Persona actor = new Persona();
        actor.setNombre("Ana");
        actor.setPrimerApellido("Martinez");
        actor.setSegundoApellido("Lopez");

        Persona creador = new Persona();
        creador.setNombre("Carlos");
        creador.setPrimerApellido("Sanchez");
        creador.setSegundoApellido("Ruiz");

        Capitulo capitulo = new Capitulo();
        capitulo.setNombreCapitulo("Nacimiento de una estrella");
        capitulo.setNumeroCapitulo(1);
        capitulo.setDescripcion("Cómo se forman las estrellas en la nebulosa.");
        capitulo.setEnlace("http://example.com/video/1");

        Temporada temporada = new Temporada();
        temporada.setNombreTemporada("Estrellas antiguas");
        temporada.setNumeroTemporada(1);
        temporada.setDescripcion("Temporada introductoria.");
        temporada.getCapitulos().add(capitulo);
        capitulo.setTemporada(temporada);

        Serie serie = new Serie();
        serie.setNombreSerie("Misterios del Cosmos");
        serie.setDescripcion("Serie documental sobre astronomía.");
        serie.setCategoria(CategoriaSerie.ESTANDAR);
        serie.getActores().add(actor);
        serie.getCreador().add(creador);
        serie.getTemporadas().add(temporada);
        temporada.setSerie(serie);

        serieRepository.save(serie); // persist serie + temporada + capitulo + personas (cascade)

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("maria");
        usuario.setContraseña("pass123");
        usuario.setCuentaBancaria("ES0021001234567890123456");
        usuario.setTipo(TipoSuscripcion.CUOTAFIJA);
        usuario.getSeriesEmpezadas().add(serie);

        Factura factura = new Factura();
        factura.setMes("2026-03");
        factura.setTotal(9.99f);
        factura.setUsuario(usuario);

        Visualizacion visualizacion = new Visualizacion();
        visualizacion.setFechaVisualizacion(new Date());
        visualizacion.setIdCapitulo(capitulo.getIdCapitulo());
        visualizacion.setIdTemporada(temporada.getIdTemporada());
        visualizacion.setSerie(serie);

        factura.getVisualizaciones().add(visualizacion);
        usuario.getFacturas().add(factura);

        usuarioRepository.save(usuario); // persist usuario + factura + visualizacion

        RegistroSerieUsuario registro = new RegistroSerieUsuario();
        registro.setUsuario(usuario);
        registro.setSerie(serie);
        registro.setUltimoCapituloVisto(capitulo);

        registroRepository.save(registro);
    }
}
