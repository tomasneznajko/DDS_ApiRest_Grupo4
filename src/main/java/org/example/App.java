package org.example;
import FusionadorDeComunidades.controladores.*;

import io.javalin.Javalin;


public class App
{
    public static void main( String[] args )
    {
        Integer port = Integer.parseInt(System.getProperty("port", "8082"));
        Javalin app = Javalin.create(
                /*config -> {
                    OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
                    openApiConfiguration.getInfo().setTitle("Javalin OpenAPI example");
                    config.plugins.register(new OpenApiPlugin(openApiConfiguration));
                    config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
                    config.plugins.register(new ReDocPlugin(new ReDocConfiguration()));
                }*/).start(port);
        app.post("/api/analizar-fusion", new AnalizarFusionController());
        app.post("/api/fusionar-comunidades", new FusionarComunidadesController());
    }
}
