package FusionadorDeComunidades.criterios;

import FusionadorDeComunidades.Entidades.Comunidad;
import FusionadorDeComunidades.Entidades.PropuestaAnterior;

import java.time.LocalDate;
import java.util.List;

public class CriterioPropuestaAnterior implements Criterio{
    private static final Integer MIN_DIFERENCIA_PROPUESTA_MESES = 6;

    @Override
    public Boolean validarCriterio(Comunidad comunidad1, Comunidad comunidad2) {
        return corroboroPropuesta(comunidad1.getPropuestasAnteriores(),comunidad2.getId())
                && corroboroPropuesta(comunidad2.getPropuestasAnteriores(),comunidad1.getId());
    }
    private Boolean corroboroPropuesta(List<PropuestaAnterior> propuestas, Long idBuscado){
        for (PropuestaAnterior propuestaAnterior : propuestas) {
            if (propuestaAnterior.getIdComunidad() == idBuscado) {
                return distanciaMesesEntre(propuestaAnterior.getFechaComoLocalDate(),LocalDate.now()) >= MIN_DIFERENCIA_PROPUESTA_MESES;
            }
        }
        return true;
    }

    private Integer distanciaMesesEntre(LocalDate fecha1, LocalDate fecha2) {
        int diferenciaAnios = fecha1.getYear() - fecha2.getYear();
        int diferenciaMeses = fecha1.getMonthValue() - fecha2.getMonthValue();
        return Math.abs(diferenciaAnios * 12 + diferenciaMeses);
    }

}
