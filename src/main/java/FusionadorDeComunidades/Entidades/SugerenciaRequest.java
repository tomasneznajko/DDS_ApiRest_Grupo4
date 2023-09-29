package FusionadorDeComunidades.Entidades;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SugerenciaRequest {
    private List<Comunidad> comunidades;
    public SugerenciaRequest(){
        this.comunidades = new ArrayList<>();
    }
}
