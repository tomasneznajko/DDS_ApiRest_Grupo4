package FusionadorDeComunidades.criterios;

import FusionadorDeComunidades.Entidades.Comunidad;

public interface Criterio {
    Boolean validarCriterio(Comunidad comunidad1, Comunidad comunidad2);
}
