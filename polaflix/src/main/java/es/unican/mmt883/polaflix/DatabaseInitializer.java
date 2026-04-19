package es.unican.mmt883.polaflix;

import es.unican.mmt883.polaflix.model.*;
import es.unican.mmt883.polaflix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class DatabaseInitializer implements CommandLineRunner {

	@Autowired
	protected SerieRepository serieRepository;
	@Autowired
	protected UsuarioRepository usuarioRepository;
	@Autowired
	protected FacturaRepository facturaRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (serieRepository.count() > 0) {
			return; // ya inicializado
		}

		feedSeries();
		feedUsuarios();

		System.out.println("Database initialized");
	}

	private void feedSeries() {
		Persona actor = new Persona();
		actor.setNombre("Ana");
		actor.setPrimerApellido("Martinez");
		actor.setSegundoApellido("Lopez");

		Persona creador = new Persona();
		creador.setNombre("Carlos");
		creador.setPrimerApellido("Sanchez");
		creador.setSegundoApellido("Ruiz");

		Capitulo capitulo1 = new Capitulo();
		capitulo1.setNombreCapitulo("Nacimiento de una estrella");
		capitulo1.setNumeroCapitulo(1);
		capitulo1.setDescripcion("Cómo se forman las estrellas en la nebulosa.");
		capitulo1.setEnlace("http://example.com/video/1");

		Temporada temporada1 = new Temporada();
		temporada1.setNombreTemporada("Estrellas antiguas");
		temporada1.setNumeroTemporada(1);
		temporada1.setDescripcion("Temporada introductoria.");
		temporada1.getCapitulos().put(capitulo1.getNumeroCapitulo(), capitulo1);
		capitulo1.setTemporada(temporada1);

		Serie serie1 = new Serie();
		serie1.setNombreSerie("Misterios del Cosmos");
		serie1.setDescripcion("Serie documental sobre astronomía.");
		serie1.setCategoria(CategoriaSerie.ESTANDAR);
		serie1.getActores().add(actor);
		serie1.getCreadores().add(creador);
		serie1.getTemporadas().put(temporada1.getNumeroTemporada(), temporada1);
		temporada1.setSerie(serie1);

		Persona actor2 = new Persona();
		actor2.setNombre("Laura");
		actor2.setPrimerApellido("Gomez");
		actor2.setSegundoApellido("Pérez");

		Persona creador2 = new Persona();
		creador2.setNombre("Diego");
		creador2.setPrimerApellido("Ruiz");
		creador2.setSegundoApellido("Ortiz");

		Capitulo capitulo2 = new Capitulo();
		capitulo2.setNombreCapitulo("Código en la sombra");
		capitulo2.setNumeroCapitulo(1);
		capitulo2.setDescripcion("Un thriller tecnológico con giros inesperados.");
		capitulo2.setEnlace("http://example.com/video/2");

		Temporada temporada2 = new Temporada();
		temporada2.setNombreTemporada("Primera Temporada");
		temporada2.setNumeroTemporada(1);
		temporada2.setDescripcion("Suspense y espionaje en la era digital.");
		temporada2.getCapitulos().put(capitulo2.getNumeroCapitulo(), capitulo2);
		capitulo2.setTemporada(temporada2);

		Serie serie2 = new Serie();
		serie2.setNombreSerie("Código Híbrido");
		serie2.setDescripcion("Una trama de hacking y conspiración.");
		serie2.setCategoria(CategoriaSerie.SILVER);
		serie2.getActores().add(actor2);
		serie2.getCreadores().add(creador2);
		serie2.getTemporadas().put(temporada2.getNumeroTemporada(), temporada2);
		temporada2.setSerie(serie2);

		Persona actor3 = new Persona();
		actor3.setNombre("Miguel");
		actor3.setPrimerApellido("Hernandez");
		actor3.setSegundoApellido("Lopez");

		Persona creador3 = new Persona();
		creador3.setNombre("Elena");
		creador3.setPrimerApellido("Núñez");
		creador3.setSegundoApellido("Morales");

		Capitulo capitulo3 = new Capitulo();
		capitulo3.setNombreCapitulo("Ascenso al dominio");
		capitulo3.setNumeroCapitulo(1);
		capitulo3.setDescripcion("Una serie épica de fantasía con batallas y magia.");
		capitulo3.setEnlace("http://example.com/video/3");

		Temporada temporada3 = new Temporada();
		temporada3.setNombreTemporada("El Legado de Fuego");
		temporada3.setNumeroTemporada(1);
		temporada3.setDescripcion("Un reino en guerra y un viaje por la corona.");
		temporada3.getCapitulos().put(capitulo3.getNumeroCapitulo(), capitulo3);
		capitulo3.setTemporada(temporada3);

		Serie serie3 = new Serie();
		serie3.setNombreSerie("Tronos de Ceniza");
		serie3.setDescripcion("Una saga fantástica llena de intriga y traición.");
		serie3.setCategoria(CategoriaSerie.GOLD);
		serie3.getActores().add(actor3);
		serie3.getCreadores().add(creador3);
		serie3.getTemporadas().put(temporada3.getNumeroTemporada(), temporada3);
		temporada3.setSerie(serie3);

		serieRepository.save(serie1);
		serieRepository.save(serie2);
		serieRepository.save(serie3);
	}

	private void feedUsuarios() {
		Serie serieEstándar = null;
		Serie serieSilver = null;
		Serie serieGold = null;
		for (Serie serie : serieRepository.findAll()) {
			if (serie.getCategoria() == CategoriaSerie.ESTANDAR) {
				serieEstándar = serie;
			} else if (serie.getCategoria() == CategoriaSerie.SILVER) {
				serieSilver = serie;
			} else if (serie.getCategoria() == CategoriaSerie.GOLD) {
				serieGold = serie;
			}
		}

		if (serieEstándar == null || serieSilver == null || serieGold == null) {
			return;
		}

		Usuario usuarioFijo = new Usuario();
		usuarioFijo.setNombreUsuario("maria");
		usuarioFijo.setContraseña("pass123");
		usuarioFijo.setCuentaBancaria("ES0021001234567890123456");
		usuarioFijo.setTipo(TipoSuscripcion.CUOTAFIJA);
		usuarioFijo.getSeriesEmpezadas().add(serieEstándar);

		Factura facturaFija = new Factura();
		facturaFija.setMes("2026-04");
		facturaFija.setUsuario(usuarioFijo);

		Temporada temporadaEstandar = serieEstándar.getTemporadas().get(1);
		Capitulo capituloEstandar = temporadaEstandar.getCapitulos().get(1);
		usuarioFijo.getCapitulosVistos().add(capituloEstandar);

		Visualizacion visualizacionFija = new Visualizacion();
		visualizacionFija.setFechaVisualizacion(new Date());
		visualizacionFija.setNumCapitulo(capituloEstandar.getNumeroCapitulo());
		visualizacionFija.setNumTemporada(temporadaEstandar.getNumeroTemporada());
		visualizacionFija.setSerie(serieEstándar);

		facturaFija.getVisualizaciones().add(visualizacionFija);
		usuarioFijo.getFacturas().add(facturaFija);

		RegistroSerieUsuario registroFijo = new RegistroSerieUsuario();
		registroFijo.setUsuario(usuarioFijo);
		registroFijo.setSerie(serieEstándar);
		registroFijo.setUltimoCapituloVisto(capituloEstandar);
		usuarioFijo.getRegistros().add(registroFijo);

		usuarioRepository.save(usuarioFijo);
		facturaRepository.save(facturaFija);

		Usuario usuarioPago = new Usuario();
		usuarioPago.setNombreUsuario("juan");
		usuarioPago.setContraseña("pass456");
		usuarioPago.setCuentaBancaria("ES0012345678901234567890");
		usuarioPago.setTipo(TipoSuscripcion.PAGOPORVISUALIZACION);
		usuarioPago.getSeriesEmpezadas().add(serieSilver);
		usuarioPago.getSeriesEmpezadas().add(serieGold);

		Factura facturaPago = new Factura();
		facturaPago.setMes("2026-04");
		facturaPago.setUsuario(usuarioPago);

		Temporada temporadaSilver = serieSilver.getTemporadas().get(1);
		Capitulo capituloSilver = temporadaSilver.getCapitulos().get(1);
		usuarioPago.getCapitulosVistos().add(capituloSilver);

		Visualizacion visualizacionSilver = new Visualizacion();
		visualizacionSilver.setFechaVisualizacion(new Date());
		visualizacionSilver.setNumCapitulo(capituloSilver.getNumeroCapitulo());
		visualizacionSilver.setNumTemporada(temporadaSilver.getNumeroTemporada());
		visualizacionSilver.setSerie(serieSilver);

		Temporada temporadaGold = serieGold.getTemporadas().get(1);
		Capitulo capituloGold = temporadaGold.getCapitulos().get(1);
		usuarioPago.getCapitulosVistos().add(capituloGold);

		Visualizacion visualizacionGold = new Visualizacion();
		visualizacionGold.setFechaVisualizacion(new Date());
		visualizacionGold.setNumCapitulo(capituloGold.getNumeroCapitulo());
		visualizacionGold.setNumTemporada(temporadaGold.getNumeroTemporada());
		visualizacionGold.setSerie(serieGold);

		facturaPago.getVisualizaciones().add(visualizacionSilver);
		facturaPago.getVisualizaciones().add(visualizacionGold);
		usuarioPago.getFacturas().add(facturaPago);

		RegistroSerieUsuario registroPago = new RegistroSerieUsuario();
		registroPago.setUsuario(usuarioPago);
		registroPago.setSerie(serieGold);
		registroPago.setUltimoCapituloVisto(capituloGold);
		usuarioPago.getRegistros().add(registroPago);

		usuarioRepository.save(usuarioPago);
		facturaRepository.save(facturaPago);
	}

}
