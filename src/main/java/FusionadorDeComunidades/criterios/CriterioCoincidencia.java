package FusionadorDeComunidades.criterios;

import FusionadorDeComunidades.Entidades.Comunidad;

import java.util.List;

public class CriterioCoincidencia implements Criterio{
    private static final double MIN_COINCIDENCIA_ESTABLECIMIENTO = 0.75;
    private static final double MIN_COINCIDENCIA_SERVICIOS = 0.75;
    private static final double MIN_COINCIDENCIA_USUARIOS = 0.05;

    private double calcularPorcentajeCoincidencia(List<Long> lista1, List<Long> lista2){
        double coincidencias = lista1.stream()
                .filter(lista2::contains)
                .count();
        return coincidencias / Math.max(lista1.size(), lista2.size());
    }

    @Override
    public Boolean validarCriterio(Comunidad comunidad1, Comunidad comunidad2) {
        return calcularPorcentajeCoincidencia(comunidad1.getEstablecimientos(),comunidad2.getEstablecimientos())>=MIN_COINCIDENCIA_ESTABLECIMIENTO &&
                calcularPorcentajeCoincidencia(comunidad1.getServicios(),comunidad2.getServicios())>=MIN_COINCIDENCIA_SERVICIOS &&
                calcularPorcentajeCoincidencia(comunidad1.getUsuarios(),comunidad2.getUsuarios())>= MIN_COINCIDENCIA_USUARIOS;
    }
}
