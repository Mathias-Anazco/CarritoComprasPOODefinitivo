package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;

public class Respuesta {
    private int id;
    private String enunciado;
    private String respuesta;

    public Respuesta(int id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
