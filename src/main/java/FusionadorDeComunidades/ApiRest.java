package FusionadorDeComunidades;

import FusionadorDeComunidades.controladores.AnalizarFusionController;
import FusionadorDeComunidades.controladores.FusionarComunidadesController;
import io.javalin.Javalin;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.redoc.ReDocConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

public class ApiRest {
    public static void main( String[] args )
    {
        Integer port = Integer.parseInt(System.getProperty("port", "8082"));
        Javalin app = Javalin.create(
                config -> {
                    OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
                    openApiConfiguration.getInfo().setTitle("Javalin OpenAPI example");
                    config.plugins.register(new OpenApiPlugin(openApiConfiguration));
                    config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
                    config.plugins.register(new ReDocPlugin(new ReDocConfiguration()));
                }).start(port);

        app.post("/api/analizar-fusion", new AnalizarFusionController());
        app.post("/api/fusionar-comunidades", new FusionarComunidadesController());
    }
}
