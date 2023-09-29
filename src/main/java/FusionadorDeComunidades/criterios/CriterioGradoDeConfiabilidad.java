package FusionadorDeComunidades.criterios;

import FusionadorDeComunidades.Entidades.Comunidad;

public class CriterioGradoDeConfiabilidad implements Criterio{
    @Override
    public Boolean validarCriterio(Comunidad comunidad1, Comunidad comunidad2) {
        //BORRAR
        boolean respuesta = comunidad1.getGradoConfianza() == comunidad2.getGradoConfianza();
        //BORRAR
        return comunidad1.getGradoConfianza() == comunidad2.getGradoConfianza();
    }
}
