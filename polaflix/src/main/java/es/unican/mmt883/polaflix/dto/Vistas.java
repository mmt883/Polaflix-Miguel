package es.unican.mmt883.polaflix.dto;

public final class Vistas {

    private Vistas() {}

    public interface Public {}

    public interface UsuarioResumen extends Public {}

    public interface SerieResumen extends Public {}
    public interface SerieCompleto extends SerieResumen {}

    public interface FacturaCompleto extends Public, VisualizacionCompleto{}

    public interface VisualizacionCompleto extends Public, SerieResumen {}

    public interface RegistroCompleto extends UsuarioResumen, SerieResumen {}
}