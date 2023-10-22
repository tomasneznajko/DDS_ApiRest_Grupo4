package FusionadorDeComunidades.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class PropuestaAnterior {
    private long idComunidad;
    private String fecha;

    public PropuestaAnterior(long id, String fecha){
        this.idComunidad = id;
        this.fecha = fecha;
    }
    public PropuestaAnterior(){}

    public Boolean soyEquivalenteA(PropuestaAnterior propuestaAnterior){
        return this.idComunidad == propuestaAnterior.getIdComunidad() &&
                this.fecha.equals(propuestaAnterior.getFecha());
    }

    @JsonIgnore
    public LocalDate getFechaComoLocalDate(){
        return LocalDate.parse(fecha);
    }
}
