package Clases;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Factura {

    private int idFactura;
    
    private String mes;
    private Float total;

    private Usuario usuario;
    private List<Visualizacion> visualizaciones = new ArrayList<>();
}