package es.unican.mmt883.polaflix;

import es.unican.mmt883.polaflix.model.*;
import es.unican.mmt883.polaflix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
        // ==========================================
        // SERIE 1: Misterios del Cosmos
        // ==========================================
        Persona actor1 = new Persona(); actor1.setNombre("Ana"); actor1.setPrimerApellido("Martinez"); actor1.setSegundoApellido("Lopez");
        Persona creador1 = new Persona(); creador1.setNombre("Carlos"); creador1.setPrimerApellido("Sanchez"); creador1.setSegundoApellido("Ruiz");
        
        Serie serie1 = new Serie();
        serie1.setNombreSerie("Misterios del Cosmos");
        serie1.setDescripcion("Serie documental sobre astronomía y exploración del universo.");
        serie1.setCategoria(CategoriaSerie.ESTANDAR);
        serie1.getActores().add(actor1);
        serie1.getCreadores().add(creador1);

        Temporada s1t1 = new Temporada(); s1t1.setNombreTemporada("Estrellas antiguas"); s1t1.setNumeroTemporada(1); s1t1.setDescripcion("Temporada introductoria."); s1t1.setSerie(serie1);
        Capitulo s1t1c1 = new Capitulo(); s1t1c1.setNombreCapitulo("Nacimiento de una estrella"); s1t1c1.setNumeroCapitulo(1); s1t1c1.setDescripcion("Cómo se forman las estrellas."); s1t1c1.setEnlace("http://video/s1t1c1"); s1t1c1.setTemporada(s1t1); s1t1.getCapitulos().add(s1t1c1);
        Capitulo s1t1c2 = new Capitulo(); s1t1c2.setNombreCapitulo("Tormenta de polvo"); s1t1c2.setNumeroCapitulo(2); s1t1c2.setDescripcion("Las tormentas estelares."); s1t1c2.setEnlace("http://video/s1t1c2"); s1t1c2.setTemporada(s1t1); s1t1.getCapitulos().add(s1t1c2);
        Capitulo s1t1c3 = new Capitulo(); s1t1c3.setNombreCapitulo("Viaje lunar"); s1t1c3.setNumeroCapitulo(3); s1t1c3.setDescripcion("Exploración de la luna."); s1t1c3.setEnlace("http://video/s1t1c3"); s1t1c3.setTemporada(s1t1); s1t1.getCapitulos().add(s1t1c3);
        serie1.getTemporadas().add(s1t1);

        Temporada s1t2 = new Temporada(); s1t2.setNombreTemporada("Galaxias ocultas"); s1t2.setNumeroTemporada(2); s1t2.setDescripcion("Exploración de nuevos mundos."); s1t2.setSerie(serie1);
        Capitulo s1t2c1 = new Capitulo(); s1t2c1.setNombreCapitulo("Mundos rebeldes"); s1t2c1.setNumeroCapitulo(1); s1t2c1.setDescripcion("Planetas lejanos."); s1t2c1.setEnlace("http://video/s1t2c1"); s1t2c1.setTemporada(s1t2); s1t2.getCapitulos().add(s1t2c1);
        Capitulo s1t2c2 = new Capitulo(); s1t2c2.setNombreCapitulo("Agujeros negros"); s1t2c2.setNumeroCapitulo(2); s1t2c2.setDescripcion("Misterios cósmicos."); s1t2c2.setEnlace("http://video/s1t2c2"); s1t2c2.setTemporada(s1t2); s1t2.getCapitulos().add(s1t2c2);
        Capitulo s1t2c3 = new Capitulo(); s1t2c3.setNombreCapitulo("Lentes gravitacionales"); s1t2c3.setNumeroCapitulo(3); s1t2c3.setDescripcion("Fenómenos relativistas."); s1t2c3.setEnlace("http://video/s1t2c3"); s1t2c3.setTemporada(s1t2); s1t2.getCapitulos().add(s1t2c3);
        serie1.getTemporadas().add(s1t2);

        Temporada s1t3 = new Temporada(); s1t3.setNombreTemporada("Fronteras del universo"); s1t3.setNumeroTemporada(3); s1t3.setDescripcion("Fronteras finales."); s1t3.setSerie(serie1);
        Capitulo s1t3c1 = new Capitulo(); s1t3c1.setNombreCapitulo("Planetas gemelos"); s1t3c1.setNumeroCapitulo(1); s1t3c1.setDescripcion("Sistemas binarios."); s1t3c1.setEnlace("http://video/s1t3c1"); s1t3c1.setTemporada(s1t3); s1t3.getCapitulos().add(s1t3c1);
        Capitulo s1t3c2 = new Capitulo(); s1t3c2.setNombreCapitulo("Civilizaciones perdidas"); s1t3c2.setNumeroCapitulo(2); s1t3c2.setDescripcion("Búsqueda de vida extraterrestre."); s1t3c2.setEnlace("http://video/s1t3c2"); s1t3c2.setTemporada(s1t3); s1t3.getCapitulos().add(s1t3c2);
        Capitulo s1t3c3 = new Capitulo(); s1t3c3.setNombreCapitulo("Horizonte de sucesos"); s1t3c3.setNumeroCapitulo(3); s1t3c3.setDescripcion("El fin del universo."); s1t3c3.setEnlace("http://video/s1t3c3"); s1t3c3.setTemporada(s1t3); s1t3.getCapitulos().add(s1t3c3);
        serie1.getTemporadas().add(s1t3);

        serieRepository.save(serie1);

        // ==========================================
        // SERIE 2: Código Híbrido
        // ==========================================
        Persona actor2 = new Persona(); actor2.setNombre("Laura"); actor2.setPrimerApellido("Gomez"); actor2.setSegundoApellido("Pérez");
        Persona creador2 = new Persona(); creador2.setNombre("Diego"); creador2.setPrimerApellido("Ruiz"); creador2.setSegundoApellido("Ortiz");
        
        Serie serie2 = new Serie();
        serie2.setNombreSerie("Código Híbrido");
        serie2.setDescripcion("Thriller tecnológico con secretos en la red.");
        serie2.setCategoria(CategoriaSerie.SILVER);
        serie2.getActores().add(actor2);
        serie2.getCreadores().add(creador2);

        Temporada s2t1 = new Temporada(); s2t1.setNombreTemporada("Primera temporada"); s2t1.setNumeroTemporada(1); s2t1.setDescripcion("Inicio de la conspiración."); s2t1.setSerie(serie2);
        Capitulo s2t1c1 = new Capitulo(); s2t1c1.setNombreCapitulo("Código en la sombra"); s2t1c1.setNumeroCapitulo(1); s2t1c1.setDescripcion("Un thriller tecnológico."); s2t1c1.setEnlace("http://video/s2t1c1"); s2t1c1.setTemporada(s2t1); s2t1.getCapitulos().add(s2t1c1);
        Capitulo s2t1c2 = new Capitulo(); s2t1c2.setNombreCapitulo("Perfil falso"); s2t1c2.setNumeroCapitulo(2); s2t1c2.setDescripcion("La identidad es engañosa."); s2t1c2.setEnlace("http://video/s2t1c2"); s2t1c2.setTemporada(s2t1); s2t1.getCapitulos().add(s2t1c2);
        Capitulo s2t1c3 = new Capitulo(); s2t1c3.setNombreCapitulo("Guía encriptada"); s2t1c3.setNumeroCapitulo(3); s2t1c3.setDescripcion("Mensajes ocultos."); s2t1c3.setEnlace("http://video/s2t1c3"); s2t1c3.setTemporada(s2t1); s2t1.getCapitulos().add(s2t1c3);
        serie2.getTemporadas().add(s2t1);

        Temporada s2t2 = new Temporada(); s2t2.setNombreTemporada("Intrusión crítica"); s2t2.setNumeroTemporada(2); s2t2.setDescripcion("El hacker despierta."); s2t2.setSerie(serie2);
        Capitulo s2t2c1 = new Capitulo(); s2t2c1.setNombreCapitulo("Bóveda comprometida"); s2t2c1.setNumeroCapitulo(1); s2t2c1.setDescripcion("La seguridad colapsa."); s2t2c1.setEnlace("http://video/s2t2c1"); s2t2c1.setTemporada(s2t2); s2t2.getCapitulos().add(s2t2c1);
        Capitulo s2t2c2 = new Capitulo(); s2t2c2.setNombreCapitulo("La amenaza interna"); s2t2c2.setNumeroCapitulo(2); s2t2c2.setDescripcion("El traidor entre nosotros."); s2t2c2.setEnlace("http://video/s2t2c2"); s2t2c2.setTemporada(s2t2); s2t2.getCapitulos().add(s2t2c2);
        Capitulo s2t2c3 = new Capitulo(); s2t2c3.setNombreCapitulo("Mentiras dobles"); s2t2c3.setNumeroCapitulo(3); s2t2c3.setDescripcion("Nadie es confiable."); s2t2c3.setEnlace("http://video/s2t2c3"); s2t2c3.setTemporada(s2t2); s2t2.getCapitulos().add(s2t2c3);
        serie2.getTemporadas().add(s2t2);

        Temporada s2t3 = new Temporada(); s2t3.setNombreTemporada("Contraataque"); s2t3.setNumeroTemporada(3); s2t3.setDescripcion("Cazador digital."); s2t3.setSerie(serie2);
        Capitulo s2t3c1 = new Capitulo(); s2t3c1.setNombreCapitulo("Datos robados"); s2t3c1.setNumeroCapitulo(1); s2t3c1.setDescripcion("El secreto sale a la luz."); s2t3c1.setEnlace("http://video/s2t3c1"); s2t3c1.setTemporada(s2t3); s2t3.getCapitulos().add(s2t3c1);
        Capitulo s2t3c2 = new Capitulo(); s2t3c2.setNombreCapitulo("Punto de retorno"); s2t3c2.setNumeroCapitulo(2); s2t3c2.setDescripcion("El punto de no retorno."); s2t3c2.setEnlace("http://video/s2t3c2"); s2t3c2.setTemporada(s2t3); s2t3.getCapitulos().add(s2t3c2);
        Capitulo s2t3c3 = new Capitulo(); s2t3c3.setNombreCapitulo("Cierre del cerrojo"); s2t3c3.setNumeroCapitulo(3); s2t3c3.setDescripcion("Final definitivo."); s2t3c3.setEnlace("http://video/s2t3c3"); s2t3c3.setTemporada(s2t3); s2t3.getCapitulos().add(s2t3c3);
        serie2.getTemporadas().add(s2t3);

        serieRepository.save(serie2);

        // ==========================================
        // SERIE 3: Tronos de Ceniza
        // ==========================================
        Persona actor3 = new Persona(); actor3.setNombre("Miguel"); actor3.setPrimerApellido("Hernandez"); actor3.setSegundoApellido("Lopez");
        Persona creador3 = new Persona(); creador3.setNombre("Elena"); creador3.setPrimerApellido("Núñez"); creador3.setSegundoApellido("Morales");
        
        Serie serie3 = new Serie();
        serie3.setNombreSerie("Tronos de Ceniza");
        serie3.setDescripcion("Saga fantástica llena de guerras, lealtades y magia.");
        serie3.setCategoria(CategoriaSerie.GOLD);
        serie3.getActores().add(actor3);
        serie3.getCreadores().add(creador3);

        Temporada s3t1 = new Temporada(); s3t1.setNombreTemporada("El legado de fuego"); s3t1.setNumeroTemporada(1); s3t1.setDescripcion("Un reino en guerra."); s3t1.setSerie(serie3);
        Capitulo s3t1c1 = new Capitulo(); s3t1c1.setNombreCapitulo("Ascenso al dominio"); s3t1c1.setNumeroCapitulo(1); s3t1c1.setDescripcion("Una serie épica de fantasía."); s3t1c1.setEnlace("http://video/s3t1c1"); s3t1c1.setTemporada(s3t1); s3t1.getCapitulos().add(s3t1c1);
        Capitulo s3t1c2 = new Capitulo(); s3t1c2.setNombreCapitulo("Sombras en el trono"); s3t1c2.setNumeroCapitulo(2); s3t1c2.setDescripcion("Conspiraciones políticas."); s3t1c2.setEnlace("http://video/s3t1c2"); s3t1c2.setTemporada(s3t1); s3t1.getCapitulos().add(s3t1c2);
        Capitulo s3t1c3 = new Capitulo(); s3t1c3.setNombreCapitulo("Llamas del destino"); s3t1c3.setNumeroCapitulo(3); s3t1c3.setDescripcion("La batalla comienza."); s3t1c3.setEnlace("http://video/s3t1c3"); s3t1c3.setTemporada(s3t1); s3t1.getCapitulos().add(s3t1c3);
        serie3.getTemporadas().add(s3t1);

        Temporada s3t2 = new Temporada(); s3t2.setNombreTemporada("La traición"); s3t2.setNumeroTemporada(2); s3t2.setDescripcion("La corona en peligro."); s3t2.setSerie(serie3);
        Capitulo s3t2c1 = new Capitulo(); s3t2c1.setNombreCapitulo("El juramento roto"); s3t2c1.setNumeroCapitulo(1); s3t2c1.setDescripcion("Las alianzas se quiebran."); s3t2c1.setEnlace("http://video/s3t2c1"); s3t2c1.setTemporada(s3t2); s3t2.getCapitulos().add(s3t2c1);
        Capitulo s3t2c2 = new Capitulo(); s3t2c2.setNombreCapitulo("Cuchillos en la corte"); s3t2c2.setNumeroCapitulo(2); s3t2c2.setDescripcion("Intriga en el poder."); s3t2c2.setEnlace("http://video/s3t2c2"); s3t2c2.setTemporada(s3t2); s3t2.getCapitulos().add(s3t2c2);
        Capitulo s3t2c3 = new Capitulo(); s3t2c3.setNombreCapitulo("Resurrección de viejos pactos"); s3t2c3.setNumeroCapitulo(3); s3t2c3.setDescripcion("El pasado vuelve."); s3t2c3.setEnlace("http://video/s3t2c3"); s3t2c3.setTemporada(s3t2); s3t2.getCapitulos().add(s3t2c3);
        serie3.getTemporadas().add(s3t2);

        Temporada s3t3 = new Temporada(); s3t3.setNombreTemporada("El último amanecer"); s3t3.setNumeroTemporada(3); s3t3.setDescripcion("La guerra final."); s3t3.setSerie(serie3);
        Capitulo s3t3c1 = new Capitulo(); s3t3c1.setNombreCapitulo("Tormenta de acero"); s3t3c1.setNumeroCapitulo(1); s3t3c1.setDescripcion("El combate final."); s3t3c1.setEnlace("http://video/s3t3c1"); s3t3c1.setTemporada(s3t3); s3t3.getCapitulos().add(s3t3c1);
        Capitulo s3t3c2 = new Capitulo(); s3t3c2.setNombreCapitulo("Reino de ceniza"); s3t3c2.setNumeroCapitulo(2); s3t3c2.setDescripcion("Todo se derrumba."); s3t3c2.setEnlace("http://video/s3t3c2"); s3t3c2.setTemporada(s3t3); s3t3.getCapitulos().add(s3t3c2);
        Capitulo s3t3c3 = new Capitulo(); s3t3c3.setNombreCapitulo("Renacimiento de los justos"); s3t3c3.setNumeroCapitulo(3); s3t3c3.setDescripcion("Un nuevo comienzo."); s3t3c3.setEnlace("http://video/s3t3c3"); s3t3c3.setTemporada(s3t3); s3t3.getCapitulos().add(s3t3c3);
        serie3.getTemporadas().add(s3t3);

        serieRepository.save(serie3);

        // ==========================================
        // SERIE 4: Risas de Oficina
        // ==========================================
        Persona actor4 = new Persona(); actor4.setNombre("Roberto"); actor4.setPrimerApellido("García"); actor4.setSegundoApellido("Sanz");
        Persona creador4 = new Persona(); creador4.setNombre("Carmen"); creador4.setPrimerApellido("Velasco"); creador4.setSegundoApellido("Díaz");
        
        Serie serie4 = new Serie();
        serie4.setNombreSerie("Risas de Oficina");
        serie4.setDescripcion("Comedia laboral con situaciones absurdas y personajes entrañables.");
        serie4.setCategoria(CategoriaSerie.ESTANDAR);
        serie4.getActores().add(actor4);
        serie4.getCreadores().add(creador4);

        Temporada s4t1 = new Temporada(); s4t1.setNombreTemporada("Comienzos"); s4t1.setNumeroTemporada(1); s4t1.setDescripcion("Llegada del nuevo equipo."); s4t1.setSerie(serie4);
        Capitulo s4t1c1 = new Capitulo(); s4t1c1.setNombreCapitulo("El primer día"); s4t1c1.setNumeroCapitulo(1); s4t1c1.setDescripcion("Día uno en la oficina."); s4t1c1.setEnlace("http://video/s4t1c1"); s4t1c1.setTemporada(s4t1); s4t1.getCapitulos().add(s4t1c1);
        Capitulo s4t1c2 = new Capitulo(); s4t1c2.setNombreCapitulo("Reunión desastrosa"); s4t1c2.setNumeroCapitulo(2); s4t1c2.setDescripcion("Todo sale mal en la presentación."); s4t1c2.setEnlace("http://video/s4t1c2"); s4t1c2.setTemporada(s4t1); s4t1.getCapitulos().add(s4t1c2);
        Capitulo s4t1c3 = new Capitulo(); s4t1c3.setNombreCapitulo("Tardes de café"); s4t1c3.setNumeroCapitulo(3); s4t1c3.setDescripcion("Charlas en la pausa."); s4t1c3.setEnlace("http://video/s4t1c3"); s4t1c3.setTemporada(s4t1); s4t1.getCapitulos().add(s4t1c3);
        serie4.getTemporadas().add(s4t1);

        Temporada s4t2 = new Temporada(); s4t2.setNombreTemporada("Crisis de presupuesto"); s4t2.setNumeroTemporada(2); s4t2.setDescripcion("El jefe exige resultados."); s4t2.setSerie(serie4);
        Capitulo s4t2c1 = new Capitulo(); s4t2c1.setNombreCapitulo("El plan secreto"); s4t2c1.setNumeroCapitulo(1); s4t2c1.setDescripcion("Una estrategia audaz."); s4t2c1.setEnlace("http://video/s4t2c1"); s4t2c1.setTemporada(s4t2); s4t2.getCapitulos().add(s4t2c1);
        Capitulo s4t2c2 = new Capitulo(); s4t2c2.setNombreCapitulo("La improvisación"); s4t2c2.setNumeroCapitulo(2); s4t2c2.setDescripcion("Sin plan, al improviso."); s4t2c2.setEnlace("http://video/s4t2c2"); s4t2c2.setTemporada(s4t2); s4t2.getCapitulos().add(s4t2c2);
        Capitulo s4t2c3 = new Capitulo(); s4t2c3.setNombreCapitulo("El informe imposible"); s4t2c3.setNumeroCapitulo(3); s4t2c3.setDescripcion("Números que no cuadran."); s4t2c3.setEnlace("http://video/s4t2c3"); s4t2c3.setTemporada(s4t2); s4t2.getCapitulos().add(s4t2c3);
        serie4.getTemporadas().add(s4t2);

        Temporada s4t3 = new Temporada(); s4t3.setNombreTemporada("Fiesta de empresa"); s4t3.setNumeroTemporada(3); s4t3.setDescripcion("El regalo equivocado."); s4t3.setSerie(serie4);
        Capitulo s4t3c1 = new Capitulo(); s4t3c1.setNombreCapitulo("El ascenso inesperado"); s4t3c1.setNumeroCapitulo(1); s4t3c1.setDescripcion("Cambios en la jerarquía."); s4t3c1.setEnlace("http://video/s4t3c1"); s4t3c1.setTemporada(s4t3); s4t3.getCapitulos().add(s4t3c1);
        Capitulo s4t3c2 = new Capitulo(); s4t3c2.setNombreCapitulo("El cliente difícil"); s4t3c2.setNumeroCapitulo(2); s4t3c2.setDescripcion("Negociación complicada."); s4t3c2.setEnlace("http://video/s4t3c2"); s4t3c2.setTemporada(s4t3); s4t3.getCapitulos().add(s4t3c2);
        Capitulo s4t3c3 = new Capitulo(); s4t3c3.setNombreCapitulo("La recta final"); s4t3c3.setNumeroCapitulo(3); s4t3c3.setDescripcion("Cierre de temporada."); s4t3c3.setEnlace("http://video/s4t3c3"); s4t3c3.setTemporada(s4t3); s4t3.getCapitulos().add(s4t3c3);
        serie4.getTemporadas().add(s4t3);

        serieRepository.save(serie4);

        // ==========================================
        // SERIE 5: Detectives del Pasado
        // ==========================================
        Persona actor5 = new Persona(); actor5.setNombre("Javier"); actor5.setPrimerApellido("Gutiérrez"); actor5.setSegundoApellido("Blanco");
        Persona creador5 = new Persona(); creador5.setNombre("Marta"); creador5.setPrimerApellido("Fernández"); creador5.setSegundoApellido("Rubio");
        
        Serie serie5 = new Serie();
        serie5.setNombreSerie("Detectives del Pasado");
        serie5.setDescripcion("Policías de época persiguen misterios sin resolver.");
        serie5.setCategoria(CategoriaSerie.SILVER);
        serie5.getActores().add(actor5);
        serie5.getCreadores().add(creador5);

        Temporada s5t1 = new Temporada(); s5t1.setNombreTemporada("Misterio en Londres"); s5t1.setNumeroTemporada(1); s5t1.setDescripcion("Investigación inicial."); s5t1.setSerie(serie5);
        Capitulo s5t1c1 = new Capitulo(); s5t1c1.setNombreCapitulo("El caso olvidado"); s5t1c1.setNumeroCapitulo(1); s5t1c1.setDescripcion("Un asesinato sin resolver."); s5t1c1.setEnlace("http://video/s5t1c1"); s5t1c1.setTemporada(s5t1); s5t1.getCapitulos().add(s5t1c1);
        Capitulo s5t1c2 = new Capitulo(); s5t1c2.setNombreCapitulo("Sombras en el muelle"); s5t1c2.setNumeroCapitulo(2); s5t1c2.setDescripcion("Pistas en la ciudad."); s5t1c2.setEnlace("http://video/s5t1c2"); s5t1c2.setTemporada(s5t1); s5t1.getCapitulos().add(s5t1c2);
        Capitulo s5t1c3 = new Capitulo(); s5t1c3.setNombreCapitulo("Voz de la noche"); s5t1c3.setNumeroCapitulo(3); s5t1c3.setDescripcion("Testigos emergen."); s5t1c3.setEnlace("http://video/s5t1c3"); s5t1c3.setTemporada(s5t1); s5t1.getCapitulos().add(s5t1c3);
        serie5.getTemporadas().add(s5t1);

        Temporada s5t2 = new Temporada(); s5t2.setNombreTemporada("La secuela"); s5t2.setNumeroTemporada(2); s5t2.setDescripcion("Años después."); s5t2.setSerie(serie5);
        Capitulo s5t2c1 = new Capitulo(); s5t2c1.setNombreCapitulo("El retorno del asesino"); s5t2c1.setNumeroCapitulo(1); s5t2c1.setDescripcion("El peligro vuelve."); s5t2c1.setEnlace("http://video/s5t2c1"); s5t2c1.setTemporada(s5t2); s5t2.getCapitulos().add(s5t2c1);
        Capitulo s5t2c2 = new Capitulo(); s5t2c2.setNombreCapitulo("Carta anónima"); s5t2c2.setNumeroCapitulo(2); s5t2c2.setDescripcion("Una pista misteriosa."); s5t2c2.setEnlace("http://video/s5t2c2"); s5t2c2.setTemporada(s5t2); s5t2.getCapitulos().add(s5t2c2);
        Capitulo s5t2c3 = new Capitulo(); s5t2c3.setNombreCapitulo("El entierro secreto"); s5t2c3.setNumeroCapitulo(3); s5t2c3.setDescripcion("La verdad enterrada."); s5t2c3.setEnlace("http://video/s5t2c3"); s5t2c3.setTemporada(s5t2); s5t2.getCapitulos().add(s5t2c3);
        serie5.getTemporadas().add(s5t2);

        Temporada s5t3 = new Temporada(); s5t3.setNombreTemporada("Última pista"); s5t3.setNumeroTemporada(3); s5t3.setDescripcion("La verdad oculta."); s5t3.setSerie(serie5);
        Capitulo s5t3c1 = new Capitulo(); s5t3c1.setNombreCapitulo("Testigo perdido"); s5t3c1.setNumeroCapitulo(1); s5t3c1.setDescripcion("Alguien falta."); s5t3c1.setEnlace("http://video/s5t3c1"); s5t3c1.setTemporada(s5t3); s5t3.getCapitulos().add(s5t3c1);
        Capitulo s5t3c2 = new Capitulo(); s5t3c2.setNombreCapitulo("La emboscada"); s5t3c2.setNumeroCapitulo(2); s5t3c2.setDescripcion("Trampa mortal."); s5t3c2.setEnlace("http://video/s5t3c2"); s5t3c2.setTemporada(s5t3); s5t3.getCapitulos().add(s5t3c2);
        Capitulo s5t3c3 = new Capitulo(); s5t3c3.setNombreCapitulo("Justicia tardía"); s5t3c3.setNumeroCapitulo(3); s5t3c3.setDescripcion("El final."); s5t3c3.setEnlace("http://video/s5t3c3"); s5t3c3.setTemporada(s5t3); s5t3.getCapitulos().add(s5t3c3);
        serie5.getTemporadas().add(s5t3);

        serieRepository.save(serie5);

        // ==========================================
        // SERIE 6: Héroes de Arena
        // ==========================================
        Persona actor6 = new Persona(); actor6.setNombre("Lucía"); actor6.setPrimerApellido("Martínez"); actor6.setSegundoApellido("Vidal");
        Persona creador6 = new Persona(); creador6.setNombre("Antonio"); creador6.setPrimerApellido("Ruiz"); creador6.setSegundoApellido("García");
        
        Serie serie6 = new Serie();
        serie6.setNombreSerie("Héroes de Arena");
        serie6.setDescripcion("Aventuras de superhéroes en un mundo dividido por el poder.");
        serie6.setCategoria(CategoriaSerie.GOLD);
        serie6.getActores().add(actor6);
        serie6.getCreadores().add(creador6);

        Temporada s6t1 = new Temporada(); s6t1.setNombreTemporada("Orígenes"); s6t1.setNumeroTemporada(1); s6t1.setDescripcion("El despertar."); s6t1.setSerie(serie6);
        Capitulo s6t1c1 = new Capitulo(); s6t1c1.setNombreCapitulo("La primera prueba"); s6t1c1.setNumeroCapitulo(1); s6t1c1.setDescripcion("El inicio de todo."); s6t1c1.setEnlace("http://video/s6t1c1"); s6t1c1.setTemporada(s6t1); s6t1.getCapitulos().add(s6t1c1);
        Capitulo s6t1c2 = new Capitulo(); s6t1c2.setNombreCapitulo("Poderes ocultos"); s6t1c2.setNumeroCapitulo(2); s6t1c2.setDescripcion("Descubriendo superpoderes."); s6t1c2.setEnlace("http://video/s6t1c2"); s6t1c2.setTemporada(s6t1); s6t1.getCapitulos().add(s6t1c2);
        Capitulo s6t1c3 = new Capitulo(); s6t1c3.setNombreCapitulo("El entrenamiento"); s6t1c3.setNumeroCapitulo(3); s6t1c3.setDescripcion("Preparación para la batalla."); s6t1c3.setEnlace("http://video/s6t1c3"); s6t1c3.setTemporada(s6t1); s6t1.getCapitulos().add(s6t1c3);
        serie6.getTemporadas().add(s6t1);

        Temporada s6t2 = new Temporada(); s6t2.setNombreTemporada("El círculo"); s6t2.setNumeroTemporada(2); s6t2.setDescripcion("Aliados inesperados."); s6t2.setSerie(serie6);
        Capitulo s6t2c1 = new Capitulo(); s6t2c1.setNombreCapitulo("La amenaza creciente"); s6t2c1.setNumeroCapitulo(1); s6t2c1.setDescripcion("Enemigos se multiplican."); s6t2c1.setEnlace("http://video/s6t2c1"); s6t2c1.setTemporada(s6t2); s6t2.getCapitulos().add(s6t2c1);
        Capitulo s6t2c2 = new Capitulo(); s6t2c2.setNombreCapitulo("Ruptura de confianza"); s6t2c2.setNumeroCapitulo(2); s6t2c2.setDescripcion("Las alianzas se quiebran."); s6t2c2.setEnlace("http://video/s6t2c2"); s6t2c2.setTemporada(s6t2); s6t2.getCapitulos().add(s6t2c2);
        Capitulo s6t2c3 = new Capitulo(); s6t2c3.setNombreCapitulo("La caída"); s6t2c3.setNumeroCapitulo(3); s6t2c3.setDescripcion("Todo se derrumba."); s6t2c3.setEnlace("http://video/s6t2c3"); s6t2c3.setTemporada(s6t2); s6t2.getCapitulos().add(s6t2c3);
        serie6.getTemporadas().add(s6t2);

        Temporada s6t3 = new Temporada(); s6t3.setNombreTemporada("Resurrección"); s6t3.setNumeroTemporada(3); s6t3.setDescripcion("El retorno del peligro."); s6t3.setSerie(serie6);
        Capitulo s6t3c1 = new Capitulo(); s6t3c1.setNombreCapitulo("Batalla en la arena"); s6t3c1.setNumeroCapitulo(1); s6t3c1.setDescripcion("La batalla final."); s6t3c1.setEnlace("http://video/s6t3c1"); s6t3c1.setTemporada(s6t3); s6t3.getCapitulos().add(s6t3c1);
        Capitulo s6t3c2 = new Capitulo(); s6t3c2.setNombreCapitulo("El sacrificio"); s6t3c2.setNumeroCapitulo(2); s6t3c2.setDescripcion("Alguien debe caer."); s6t3c2.setEnlace("http://video/s6t3c2"); s6t3c2.setTemporada(s6t3); s6t3.getCapitulos().add(s6t3c2);
        Capitulo s6t3c3 = new Capitulo(); s6t3c3.setNombreCapitulo("El nuevo amanecer"); s6t3c3.setNumeroCapitulo(3); s6t3c3.setDescripcion("Un nuevo comienzo."); s6t3c3.setEnlace("http://video/s6t3c3"); s6t3c3.setTemporada(s6t3); s6t3.getCapitulos().add(s6t3c3);
        serie6.getTemporadas().add(s6t3);

        serieRepository.save(serie6);
    }

    private void feedUsuarios() {
        // Recuperar series para asociar
        Serie serieEstandar1 = serieRepository.findAll().stream().filter(s -> s.getNombreSerie().equals("Misterios del Cosmos")).findFirst().orElse(null);
        Serie serieSilver1 = serieRepository.findAll().stream().filter(s -> s.getNombreSerie().equals("Código Híbrido")).findFirst().orElse(null);
        Serie serieGold1 = serieRepository.findAll().stream().filter(s -> s.getNombreSerie().equals("Tronos de Ceniza")).findFirst().orElse(null);
        Serie serieEstandar2 = serieRepository.findAll().stream().filter(s -> s.getNombreSerie().equals("Risas de Oficina")).findFirst().orElse(null);
        Serie serieSilver2 = serieRepository.findAll().stream().filter(s -> s.getNombreSerie().equals("Detectives del Pasado")).findFirst().orElse(null);
        Serie serieGold2 = serieRepository.findAll().stream().filter(s -> s.getNombreSerie().equals("Héroes de Arena")).findFirst().orElse(null);

        if (serieEstandar1 == null || serieSilver2 == null) return; // Seguridad

        String mesActual = new SimpleDateFormat("yyyy-MM").format(new Date());

        // ==========================================
        // USUARIO 0: JOHN NIEVE
        // ==========================================
        Usuario usuarioJohn = new Usuario(); usuarioJohn.setNombreUsuario("John Nieve"); usuarioJohn.setContraseña("john123"); usuarioJohn.setCuentaBancaria("ES4400190020961234567890"); usuarioJohn.setTipo(TipoSuscripcion.PAGOPORVISUALIZACION);
        usuarioJohn.getSeriesEmpezadas().add(serieEstandar1);
        usuarioJohn.getSeriesPendientes().add(serieSilver1);
        usuarioJohn.getSeriesTerminadas().add(serieGold1);
        usuarioJohn.getSeriesPendientes().add(serieGold2);

        Factura facturaJohn = new Factura(); facturaJohn.setMes(mesActual); facturaJohn.setUsuario(usuarioJohn);
        Temporada t1j = serieEstandar1.getTemporadaByNumero(1); Capitulo c1j = t1j.getCapituloByNumero(1);
        usuarioJohn.getCapitulosVistos().add(c1j);

        Visualizacion vJohn = new Visualizacion(); vJohn.setFechaVisualizacion(new Date()); vJohn.setNumCapitulo(c1j.getNumeroCapitulo()); vJohn.setNumTemporada(t1j.getNumeroTemporada()); vJohn.setSerie(serieEstandar1);
        facturaJohn.getVisualizaciones().add(vJohn); 

        RegistroSerieUsuario regJohnEmpezada = new RegistroSerieUsuario(); regJohnEmpezada.setUsuario(usuarioJohn); regJohnEmpezada.setSerie(serieEstandar1); regJohnEmpezada.setUltimoCapituloVisto(c1j);
        usuarioJohn.getRegistros().add(regJohnEmpezada);
        
        Temporada t3jGold = serieGold1.getTemporadaByNumero(3); Capitulo c3jGold = t3jGold.getCapituloByNumero(3);
        usuarioJohn.getCapitulosVistos().add(c3jGold);
        Visualizacion vJohnGold = new Visualizacion(); vJohnGold.setFechaVisualizacion(new Date()); vJohnGold.setNumCapitulo(c3jGold.getNumeroCapitulo()); vJohnGold.setNumTemporada(t3jGold.getNumeroTemporada()); vJohnGold.setSerie(serieGold1);
        RegistroSerieUsuario regJohnTerminada = new RegistroSerieUsuario(); regJohnTerminada.setUsuario(usuarioJohn); regJohnTerminada.setSerie(serieGold1); regJohnTerminada.setUltimoCapituloVisto(c3jGold);
        usuarioJohn.getRegistros().add(regJohnTerminada);
        facturaJohn.getVisualizaciones().add(vJohnGold); 
        usuarioJohn.getFacturas().add(facturaJohn);
        usuarioRepository.save(usuarioJohn); facturaRepository.save(facturaJohn);

        // ==========================================
        // USUARIO 1: MARIA (CUOTA FIJA) - Ve Serie Estándar 1
        // ==========================================
        Usuario usuarioMaria = new Usuario(); usuarioMaria.setNombreUsuario("maria"); usuarioMaria.setContraseña("pass123"); usuarioMaria.setCuentaBancaria("ES0021001234567890123456"); usuarioMaria.setTipo(TipoSuscripcion.CUOTAFIJA);
        usuarioMaria.getSeriesEmpezadas().add(serieEstandar1);

        Factura facturaMaria = new Factura(); facturaMaria.setMes(mesActual); facturaMaria.setUsuario(usuarioMaria);
        Temporada t1s1 = serieEstandar1.getTemporadaByNumero(1); Capitulo c1t1s1 = t1s1.getCapituloByNumero(1);
        usuarioMaria.getCapitulosVistos().add(c1t1s1);

        Visualizacion vMaria = new Visualizacion(); vMaria.setFechaVisualizacion(new Date()); vMaria.setNumCapitulo(c1t1s1.getNumeroCapitulo()); vMaria.setNumTemporada(t1s1.getNumeroTemporada()); vMaria.setSerie(serieEstandar1);
        facturaMaria.getVisualizaciones().add(vMaria); usuarioMaria.getFacturas().add(facturaMaria);

        RegistroSerieUsuario regMaria = new RegistroSerieUsuario(); regMaria.setUsuario(usuarioMaria); regMaria.setSerie(serieEstandar1); regMaria.setUltimoCapituloVisto(c1t1s1);
        usuarioMaria.getRegistros().add(regMaria);
        usuarioRepository.save(usuarioMaria); facturaRepository.save(facturaMaria);

        // ==========================================
        // USUARIO 2: JUAN (PAGO POR VISUALIZACION) - Ve Silver 1 y Gold 1
        // ==========================================
        Usuario usuarioJuan = new Usuario(); usuarioJuan.setNombreUsuario("juan"); usuarioJuan.setContraseña("pass456"); usuarioJuan.setCuentaBancaria("ES0012345678901234567890"); usuarioJuan.setTipo(TipoSuscripcion.PAGOPORVISUALIZACION);
        usuarioJuan.getSeriesEmpezadas().add(serieSilver1); usuarioJuan.getSeriesEmpezadas().add(serieGold1);

        Factura facturaJuan = new Factura(); facturaJuan.setMes(mesActual); facturaJuan.setUsuario(usuarioJuan);
        
        Temporada t1s2 = serieSilver1.getTemporadaByNumero(1); Capitulo c1t1s2 = t1s2.getCapituloByNumero(1);
        usuarioJuan.getCapitulosVistos().add(c1t1s2);
        Visualizacion vJuan1 = new Visualizacion(); vJuan1.setFechaVisualizacion(new Date()); vJuan1.setNumCapitulo(c1t1s2.getNumeroCapitulo()); vJuan1.setNumTemporada(t1s2.getNumeroTemporada()); vJuan1.setSerie(serieSilver1);
        
        Temporada t1s3 = serieGold1.getTemporadaByNumero(1); Capitulo c1t1s3 = t1s3.getCapituloByNumero(1);
        usuarioJuan.getCapitulosVistos().add(c1t1s3);
        Visualizacion vJuan2 = new Visualizacion(); vJuan2.setFechaVisualizacion(new Date(System.currentTimeMillis() + 10000)); vJuan2.setNumCapitulo(c1t1s3.getNumeroCapitulo()); vJuan2.setNumTemporada(t1s3.getNumeroTemporada()); vJuan2.setSerie(serieGold1);

        facturaJuan.getVisualizaciones().add(vJuan1); facturaJuan.getVisualizaciones().add(vJuan2);
        usuarioJuan.getFacturas().add(facturaJuan);

        RegistroSerieUsuario regJuan1 = new RegistroSerieUsuario(); regJuan1.setUsuario(usuarioJuan); regJuan1.setSerie(serieSilver1); regJuan1.setUltimoCapituloVisto(c1t1s2);
        RegistroSerieUsuario regJuan2 = new RegistroSerieUsuario(); regJuan2.setUsuario(usuarioJuan); regJuan2.setSerie(serieGold1); regJuan2.setUltimoCapituloVisto(c1t1s3);
        usuarioJuan.getRegistros().add(regJuan1); usuarioJuan.getRegistros().add(regJuan2);
        usuarioRepository.save(usuarioJuan); facturaRepository.save(facturaJuan);

        // ==========================================
        // USUARIO 3: LUCAS (PAGO POR VISUALIZACION) - Ve 2 caps de Estándar 2 y 1 de Silver 2
        // ==========================================
        Usuario usuarioLucas = new Usuario(); usuarioLucas.setNombreUsuario("lucas"); usuarioLucas.setContraseña("pass789"); usuarioLucas.setCuentaBancaria("ES1111222233334444555566"); usuarioLucas.setTipo(TipoSuscripcion.PAGOPORVISUALIZACION);
        usuarioLucas.getSeriesEmpezadas().add(serieEstandar2); usuarioLucas.getSeriesEmpezadas().add(serieSilver2);
        
        Factura facturaLucas = new Factura(); facturaLucas.setMes(mesActual); facturaLucas.setUsuario(usuarioLucas);
        
        Temporada t1s4 = serieEstandar2.getTemporadaByNumero(1); 
        Capitulo c1t1s4 = t1s4.getCapituloByNumero(1); Capitulo c2t1s4 = t1s4.getCapituloByNumero(2);
        usuarioLucas.getCapitulosVistos().add(c1t1s4); usuarioLucas.getCapitulosVistos().add(c2t1s4);
        
        Visualizacion vLucas1 = new Visualizacion(); vLucas1.setFechaVisualizacion(new Date()); vLucas1.setNumCapitulo(c1t1s4.getNumeroCapitulo()); vLucas1.setNumTemporada(t1s4.getNumeroTemporada()); vLucas1.setSerie(serieEstandar2);
        Visualizacion vLucas2 = new Visualizacion(); vLucas2.setFechaVisualizacion(new Date(System.currentTimeMillis() + 5000)); vLucas2.setNumCapitulo(c2t1s4.getNumeroCapitulo()); vLucas2.setNumTemporada(t1s4.getNumeroTemporada()); vLucas2.setSerie(serieEstandar2);
        
        Temporada t1s5 = serieSilver2.getTemporadaByNumero(1); Capitulo c1t1s5 = t1s5.getCapituloByNumero(1);
        usuarioLucas.getCapitulosVistos().add(c1t1s5);
        Visualizacion vLucas3 = new Visualizacion(); vLucas3.setFechaVisualizacion(new Date(System.currentTimeMillis() + 10000)); vLucas3.setNumCapitulo(c1t1s5.getNumeroCapitulo()); vLucas3.setNumTemporada(t1s5.getNumeroTemporada()); vLucas3.setSerie(serieSilver2);

        facturaLucas.getVisualizaciones().add(vLucas1); facturaLucas.getVisualizaciones().add(vLucas2); facturaLucas.getVisualizaciones().add(vLucas3);
        usuarioLucas.getFacturas().add(facturaLucas);

        RegistroSerieUsuario regLucas1 = new RegistroSerieUsuario(); regLucas1.setUsuario(usuarioLucas); regLucas1.setSerie(serieEstandar2); regLucas1.setUltimoCapituloVisto(c2t1s4);
        RegistroSerieUsuario regLucas2 = new RegistroSerieUsuario(); regLucas2.setUsuario(usuarioLucas); regLucas2.setSerie(serieSilver2); regLucas2.setUltimoCapituloVisto(c1t1s5);
        usuarioLucas.getRegistros().add(regLucas1); usuarioLucas.getRegistros().add(regLucas2);
        usuarioRepository.save(usuarioLucas); facturaRepository.save(facturaLucas);

        // ==========================================
        // USUARIO 4: CARMEN (CUOTA FIJA) - Ve de varias temporadas (Serie Silver 2)
        // ==========================================
        Usuario usuarioCarmen = new Usuario(); usuarioCarmen.setNombreUsuario("carmen"); usuarioCarmen.setContraseña("carmenpass"); usuarioCarmen.setCuentaBancaria("ES9999888877776666555544"); usuarioCarmen.setTipo(TipoSuscripcion.CUOTAFIJA);
        usuarioCarmen.getSeriesEmpezadas().add(serieSilver2);
        
        Factura facturaCarmen = new Factura(); facturaCarmen.setMes(mesActual); facturaCarmen.setUsuario(usuarioCarmen);
        
        // Ve el Cap 1 de la Temp 1 y el Cap 1 de la Temp 2 de la misma serie
        Temporada t2s5 = serieSilver2.getTemporadaByNumero(2); Capitulo c1t2s5 = t2s5.getCapituloByNumero(1);
        usuarioCarmen.getCapitulosVistos().add(c1t1s5); usuarioCarmen.getCapitulosVistos().add(c1t2s5);
        
        Visualizacion vCarmen1 = new Visualizacion(); vCarmen1.setFechaVisualizacion(new Date()); vCarmen1.setNumCapitulo(c1t1s5.getNumeroCapitulo()); vCarmen1.setNumTemporada(t1s5.getNumeroTemporada()); vCarmen1.setSerie(serieSilver2);
        Visualizacion vCarmen2 = new Visualizacion(); vCarmen2.setFechaVisualizacion(new Date(System.currentTimeMillis() + 8000)); vCarmen2.setNumCapitulo(c1t2s5.getNumeroCapitulo()); vCarmen2.setNumTemporada(t2s5.getNumeroTemporada()); vCarmen2.setSerie(serieSilver2);
        
        facturaCarmen.getVisualizaciones().add(vCarmen1); facturaCarmen.getVisualizaciones().add(vCarmen2);
        usuarioCarmen.getFacturas().add(facturaCarmen);
        
        RegistroSerieUsuario regCarmen = new RegistroSerieUsuario(); regCarmen.setUsuario(usuarioCarmen); regCarmen.setSerie(serieSilver2); regCarmen.setUltimoCapituloVisto(c1t2s5);
        usuarioCarmen.getRegistros().add(regCarmen);
        usuarioRepository.save(usuarioCarmen); facturaRepository.save(facturaCarmen);

        // ==========================================
        // USUARIO 5: PEDRO (CUOTA FIJA) - No ve nada, solo tiene series pendientes
        // ==========================================
        Usuario usuarioPedro = new Usuario(); usuarioPedro.setNombreUsuario("pedro"); usuarioPedro.setContraseña("pedropass"); usuarioPedro.setCuentaBancaria("ES0000000000000000000000"); usuarioPedro.setTipo(TipoSuscripcion.CUOTAFIJA);
        usuarioPedro.getSeriesPendientes().add(serieGold2); // Solo la guarda para verla luego
        
        Factura facturaPedro = new Factura(); facturaPedro.setMes(mesActual); facturaPedro.setUsuario(usuarioPedro);
        // Sin visualizaciones añadidas. Al ser CUOTA FIJA, el backend debería calcular 20€ igualmente
        usuarioPedro.getFacturas().add(facturaPedro);
        
        usuarioRepository.save(usuarioPedro); facturaRepository.save(facturaPedro);
    }
}


