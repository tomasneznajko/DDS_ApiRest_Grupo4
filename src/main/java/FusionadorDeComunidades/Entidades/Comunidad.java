package FusionadorDeComunidades.Entidades;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Comunidad {
    private Long id;
    private List<Long> establecimientos;
    private List<Long> servicios;
    private List<Long> usuarios;
    private List<Long> incidentes;
    private List<PropuestaAnterior> propuestasAnteriores;
    private double gradoConfianza;

    public Comunidad(){
        this.establecimientos = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.incidentes = new ArrayList<>();
        this.propuestasAnteriores = new ArrayList<>();
    }
}
