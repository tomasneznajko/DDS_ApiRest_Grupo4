package FusionadorDeComunidades.controladores;

import FusionadorDeComunidades.Entidades.*;
import FusionadorDeComunidades.criterios.Criterio;
import FusionadorDeComunidades.criterios.CriterioCoincidencia;
import FusionadorDeComunidades.criterios.CriterioGradoDeConfiabilidad;
import FusionadorDeComunidades.criterios.CriterioPropuestaAnterior;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.openapi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnalizarFusionController implements Handler {
    List<Criterio> criterios;
    public AnalizarFusionController(){
        this.criterios = new ArrayList<>();
        criterios.add(new CriterioCoincidencia());
        criterios.add(new CriterioGradoDeConfiabilidad());
        criterios.add(new CriterioPropuestaAnterior());
    }

    @OpenApi(
            summary = "Sugerir fusiones de comunidades",
            operationId = "analizarFusionDeComunidades",
            path = "/api/analizar-fusion",
            methods = HttpMethod.POST,
            tags = {"Sugerencia de fusiones"},
            pathParams = {@OpenApiParam(name = "userId", type = SugerenciaRequest.class, description = "The user ID")},
            requestBody = @OpenApiRequestBody(
                    content = {@OpenApiContent(from = SugerenciaRequest.class)}
            ),
            responses = {
                    @OpenApiResponse(status = "201", content = @OpenApiContent(from = ApiResponse.class)),
                    @OpenApiResponse(status = "400", content = @OpenApiContent(from = ApiResponse.class)),
                    @OpenApiResponse(status = "500", content = @OpenApiContent(from = ApiResponse.class))
            }
    )




    @Override
    public void handle(
            Context context) throws Exception {

        ApiResponse respuesta = new ApiResponse();
        try{

            SugerenciaRequest request = context.bodyAsClass(SugerenciaRequest.class);
            List<Comunidad> comunidades = request.getComunidades();
            List<ComunidadSugerencia> sugerencias = new ArrayList<>();
            Set<Comunidad> comunidadesYaPropuestas = new HashSet<>(); // Conjunto de comunidades ya sugeridas

            for(int i = 0; i < comunidades.size(); i++){
                Comunidad comunidad1 = comunidades.get(i);
                for(int j = i + 1; j < comunidades.size(); j++){
                    Comunidad comunidad2 = comunidades.get(j);

                    if(!comunidadesYaPropuestas.contains(comunidad1) && !comunidadesYaPropuestas.contains(comunidad2)
                            && cumplenCriterios(comunidad1, comunidad2)){

                        ComunidadSugerencia sugerencia = new ComunidadSugerencia();
                        sugerencia.setParComunidad(comunidad1, comunidad2);
                        sugerencias.add(sugerencia);
                        comunidadesYaPropuestas.add(comunidad1);
                        comunidadesYaPropuestas.add(comunidad2);

                    }
                }
            }
            respuesta.setExito(true);
            respuesta.setCodigoDeEstado(HttpStatus.OK.getCode());
            respuesta.setResultado(sugerencias);
            context.status(HttpStatus.OK.getCode());
            context.result("Análisis realizado");
        }catch(Exception e){
            respuesta.setExito(false);
            respuesta.setError(e.getMessage());
            respuesta.setCodigoDeEstado(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
            context.status(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        }

        context.json(respuesta);
    }

    private boolean cumplenCriterios(Comunidad comunidad1, Comunidad comunidad2){
        return criterios.stream().allMatch(criterio ->{
            return criterio.validarCriterio(comunidad1,comunidad2);
        });
    }
}
