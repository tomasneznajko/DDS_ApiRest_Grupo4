package testApi;
import FusionadorDeComunidades.Entidades.*;
import FusionadorDeComunidades.controladores.AnalizarFusionController;
import FusionadorDeComunidades.controladores.FusionarComunidadesController;
import FusionadorDeComunidades.serializadorMagico.LocalDateSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.*;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class ApiRestTest {
    private Javalin app;
    private Integer port = Integer.parseInt(System.getProperty("port", "8080"));
    private Comunidad comunidad1;
    private Comunidad comunidad2;
    private Comunidad comunidad3;
    private Comunidad comunidad4;
    private ComunidadSugerencia comunidadSugerencia1;
    private ComunidadSugerencia comunidadSugerencia2;
    private SugerenciaRequest sugerenciaRequest;
    @BeforeEach void init() {
        app = Javalin.create().start(port);
        app.post("/api/analizar-fusion", new AnalizarFusionController());
        app.post("/api/fusionar-comunidades", new FusionarComunidadesController());

        comunidad1 = new Comunidad();
        comunidad1.setId(1L);
        comunidad1.getUsuarios().addAll(Arrays.asList(1L,2L,27L));
        comunidad1.getEstablecimientos().addAll(Arrays.asList(1L,2L,27L));
        comunidad1.getServicios().addAll(Arrays.asList(1L,2L,27L));
        comunidad1.getPropuestasAnteriores().add(new PropuestaAnterior(2L, "2023-04-25"));
        comunidad1.getPropuestasAnteriores().add(new PropuestaAnterior(4L, "1999-04-25"));
        comunidad1.setGradoConfianza(3);

        comunidad2 = new Comunidad();
        comunidad2.setId(2L);
        comunidad2.getUsuarios().addAll(Arrays.asList(1L,2L,27L));
        comunidad2.getEstablecimientos().addAll(Arrays.asList(1L,2L,27L));
        comunidad2.getServicios().addAll(Arrays.asList(1L,2L,27L));
        comunidad2.setGradoConfianza(3);

        comunidad3 = new Comunidad();
        comunidad3.setId(3L);
        comunidad3.getUsuarios().addAll(Arrays.asList(1L,2L,27L));
        comunidad3.getEstablecimientos().addAll(Arrays.asList(1L,2L,27L));
        comunidad3.getServicios().addAll(Arrays.asList(1L,2L,27L));
        comunidad3.setGradoConfianza(3);

        comunidad4 = new Comunidad();
        comunidad4.setId(4L);
        comunidad4.getUsuarios().addAll(Arrays.asList(1L,2L,27L));
        comunidad4.getEstablecimientos().addAll(Arrays.asList(1L,2L,27L));
        comunidad4.getServicios().addAll(Arrays.asList(1L,2L,27L));
        comunidad4.setGradoConfianza(3);

        comunidadSugerencia1 = new ComunidadSugerencia();
        comunidadSugerencia2 = new ComunidadSugerencia();

        sugerenciaRequest = new SugerenciaRequest();
        sugerenciaRequest.getComunidades().addAll(Arrays.asList(comunidad1,comunidad2,comunidad3,comunidad4));
    }

    @AfterEach
    public void tearDown() {
        app.stop();
    }

    @Test
    public void testAnalizarFusionPropuestos(){

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(sugerenciaRequest)
                .when()
                .post("/api/analizar-fusion");

        comunidadSugerencia1.setParComunidad(comunidad1,comunidad3);
        comunidadSugerencia2.setParComunidad(comunidad2,comunidad4);
        List<ComunidadSugerencia> comunidadesSugerencias = new ArrayList<>();
        comunidadesSugerencias.addAll(Arrays.asList(comunidadSugerencia1,comunidadSugerencia2));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String jsonEsperado = gson.toJson(comunidadesSugerencias);


        String responseBody = response.getBody().asString();
        JsonElement resultadoJson = JsonParser.parseString(responseBody).getAsJsonObject().get("resultado");

        assertThat(resultadoJson.toString(), equalTo(jsonEsperado));
    }

    @Test
    public void testAnalizarFusionGrado() throws JsonProcessingException {
        comunidad1.setGradoConfianza(4);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(sugerenciaRequest)
                .when()
                .post("/api/analizar-fusion");

        comunidadSugerencia2.setParComunidad(comunidad2,comunidad3);
        List<ComunidadSugerencia> comunidadesSugerencias = new ArrayList<>();
        comunidadesSugerencias.addAll(Arrays.asList(comunidadSugerencia2));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String jsonEsperado = gson.toJson(comunidadesSugerencias);


        String responseBody = response.getBody().asString();
        JsonElement resultadoJson = JsonParser.parseString(responseBody).getAsJsonObject().get("resultado");

        assertThat(resultadoJson.toString(), equalTo(jsonEsperado));
    }

    @Test
    public void testAnalizarFusionCoincidentes() throws JsonProcessingException {

        comunidad1.setEstablecimientos(Arrays.asList(1L, 2L, 3L));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(sugerenciaRequest)
                .when()
                .post("/api/analizar-fusion");

        comunidadSugerencia1.setParComunidad(comunidad2, comunidad3);
        List<ComunidadSugerencia> comunidadesSugerencias = new ArrayList<>();
        comunidadesSugerencias.addAll(Arrays.asList(comunidadSugerencia1));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String jsonEsperado = gson.toJson(comunidadesSugerencias);



        // Extraer el campo "resultado" de la respuesta JSON
        String responseBody = response.getBody().asString();
        JsonElement resultadoJson = JsonParser.parseString(responseBody).getAsJsonObject().get("resultado");

        assertThat(resultadoJson.toString(), equalTo(jsonEsperado));
    }


    @Test
    public void testFusionarComunidadesEndpoint() {
        FusionRequest fusionRequest = new FusionRequest();
        fusionRequest.setComunidad1(comunidad1);
        fusionRequest.setComunidad2(comunidad3);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        comunidad3.setId(null);
        String jsonEsperado = gson.toJson(comunidad3);
        Response response=(given()
                .contentType(ContentType.JSON)
                .body(fusionRequest)
                .when()
                .post("/api/fusionar-comunidades"));
        String responseBody = response.getBody().asString();
        JsonObject respuestaJson = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonObject resultadoJsonObj = respuestaJson.getAsJsonObject("resultado");

        // Elimina el campo "id" del JSON de resultado para la comparación
        resultadoJsonObj.remove("id");
        assertThat(resultadoJsonObj.toString(), equalTo(jsonEsperado));
    }
}
