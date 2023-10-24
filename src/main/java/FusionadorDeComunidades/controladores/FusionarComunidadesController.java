package FusionadorDeComunidades.controladores;

import FusionadorDeComunidades.Entidades.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.openapi.*;
import org.jetbrains.annotations.NotNull;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class FusionarComunidadesController implements Handler {

    @OpenApi(
            summary = "Realizar fusion de comunidad",
            operationId = "fusionarComunidades",
            path = "/api/fusionar-comunidades",
            methods = HttpMethod.POST,
            tags = {"Fusion de comunidades"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = FusionRequest.class)}),
            responses = {
                    @OpenApiResponse(status = "201", content = @OpenApiContent(from = ApiResponse.class)),
                    @OpenApiResponse(status = "400", content = @OpenApiContent(from = ApiResponse.class)),
                    @OpenApiResponse(status = "500", content = @OpenApiContent(from = ApiResponse.class))
            }
    )
    @Override
    public void handle(@NotNull Context context) throws Exception {
        ApiResponse<Comunidad> respuesta = new ApiResponse<>();
        try {
            FusionRequest request = context.bodyAsClass(FusionRequest.class);
            Comunidad comunidadFusionada = new Comunidad();

            Comunidad comunidad1 = request.getComunidad1();
            Comunidad comunidad2 = request.getComunidad2();

            comunidadFusionada.setEstablecimientos(fusionarListas(comunidad1.getEstablecimientos(), comunidad2.getEstablecimientos()));
            comunidadFusionada.setServicios(fusionarListas(comunidad1.getServicios(), comunidad2.getServicios()));
            /*comunidadFusionada.setPropuestasAnteriores(fusionarListas(comunidad1.getPropuestasAnteriores(), comunidad2.getPropuestasAnteriores()));*/
            comunidadFusionada.setGradoConfianza(comunidad1.getGradoConfianza());
            comunidadFusionada.setUsuarios(fusionarListas(comunidad1.getUsuarios(), comunidad2.getUsuarios()));
            comunidadFusionada.setIncidentes(fusionarListas(comunidad1.getIncidentes(), comunidad2.getIncidentes()));

            respuesta.setResultado(comunidadFusionada);
            context.status(HttpStatus.OK);
            respuesta.setExito(true);
            respuesta.setCodigoDeEstado(HttpStatus.OK.getCode());
            context.result("Fusion realizada");
        } catch (Exception e) {
            respuesta.setExito(false);
            respuesta.setError(e.getMessage());
            respuesta.setCodigoDeEstado(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
            context.status(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        }
        context.json(respuesta);
    }

    private List<Long> fusionarListas(List<Long> lista1, List<Long> lista2) {
        List<Long> listaFusionada = new ArrayList<>(lista1);
        for (Long elemento : lista2) {
            if (!lista1.contains(elemento)) {
                listaFusionada.add(elemento);
            }
        }
        return listaFusionada;
    }


/*    private <T> List<T> fusionarListas(List<T> lista1, List<T> lista2) {
        List<T> listaFusionada = new ArrayList<>(lista1);
        for (T elemento : lista2) {
            if (!contieneElemento(elemento, listaFusionada)) {
                System.out.println(elemento);
                listaFusionada.add(elemento);
            }
        }
        return listaFusionada;
    }


    private <T> boolean contieneElemento(T elemento, List<T> listaFusionada) {
        return elemento instanceof PropuestaAnterior?
                listaFusionada.stream().anyMatch(elem->((PropuestaAnterior)elem).soyEquivalenteA((PropuestaAnterior)elemento)):
                listaFusionada.contains(elemento);
    }*/
}
