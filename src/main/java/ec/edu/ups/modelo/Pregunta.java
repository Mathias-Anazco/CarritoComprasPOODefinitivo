package ec.edu.ups.modelo;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

public enum Pregunta {
    COLOR_FAVORITO("pregunta.color_favorito"),
    INSTRUMENTO_FAVORITO("pregunta.instrumento_musical_favorito"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    PAIS_VISITADO_POR_PRIMERA_VEZ("pregunta.pais_que_visitaste_por_primera_vez"),
    SEGUNDO_NOMBRE_PADRE("pregunta.segundo_nombre_de_tu_padre"),
    CANCION_FAVORITA("pregunta.cancion_favorita"),
    NOMBRE_LIBRO_FAVORITO("pregunta.tu_libro_favorito"),;


    private String enunciado;
    private MensajeInternacionalizacionHandler mi;

    Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }
    Pregunta() {}

    public void setMensajeIdioma(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    public String getEnunciado() {
        if (mi != null) {
            return mi.get(enunciado);
        } else {
            return enunciado;
        }
    }

}
