package FusionadorDeComunidades.Entidades;

import io.javalin.openapi.OpenApiExample;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SugerenciaRequest{

    /*
    {
  "comunidades": [
    {
      "id": 1,
      "establecimientos": [1, 2, 3,4],
      "servicios": [1],
      "usuarios": [1],
      "incidentes": [401, 402, 403],
      "propuestasAnteriores": [
        {
          "idComunidad": 2,
          "fecha": "2023-01-13"
        },
        {
          "idComunidad": 1002,
          "fecha": "2023-06-25"
        }
      ],
      "gradoConfianza": 0.95
    },
    {
      "id": 2,
      "establecimientos": [1, 2, 3,5],
      "servicios": [1],
      "usuarios": [1],
      "incidentes": [404, 405, 406],
      "propuestasAnteriores": [
        {
          "idComunidad": 1,
          "fecha": "2023-01-10"
        }
      ],
      "gradoConfianza": 0.95
    }
    ]
    }
     */

    private List<Comunidad> comunidades;
    public SugerenciaRequest(){
        this.comunidades = new ArrayList<>();
    }
}
