package es.unican.mmt883.polaflix.dto;

public final class Vistas {

    private Vistas() {}

    public interface Public {}

    public interface UsuarioResumen extends Public {}
    public interface UsuarioCompleto extends FacturaCompleto, RegistroCompleto {}

    public interface SerieResumen extends Public {}
    public interface SerieCompleto extends SerieResumen {}

    public interface FacturaResumen extends Public {}
    public interface FacturaCompleto extends FacturaResumen, UsuarioResumen, VisualizacionCompleto{}

    public interface VisualizacionResumen extends Public {}
    public interface VisualizacionCompleto extends VisualizacionResumen, SerieResumen {}

    public interface RegistroCompleto extends UsuarioResumen, SerieResumen {}
}