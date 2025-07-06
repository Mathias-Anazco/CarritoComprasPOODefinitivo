package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;

public class Respuesta {
    private String texto;

    public Respuesta(String texto){
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    @Override
    public String toString(){
        return texto;
    }
}
