package ec.edu.ups.modelo;

/**
 * Modela la asociación entre una pregunta de seguridad y su correspondiente respuesta.
 * Esta clase actúa como un contenedor para vincular un objeto {@link Preguntas} con
 * un objeto {@link Respuesta}.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class PreguntasRespuestas {
    private Preguntas preguntas;
    private Respuesta respuesta;

    /**
     * Construye una nueva asociación de pregunta y respuesta.
     *
     * @param preguntas La pregunta de seguridad.
     * @param respuesta La respuesta correspondiente a la pregunta.
     */
    public PreguntasRespuestas( Preguntas preguntas, Respuesta respuesta) {
        this.preguntas = preguntas;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene la pregunta de esta asociación.
     *
     * @return El objeto Preguntas.
     */
    public Preguntas getPreguntas() {
        return preguntas;
    }

    /**
     * Establece la pregunta para esta asociación.
     *
     * @param preguntas El nuevo objeto Preguntas.
     */
    public void setPreguntas(Preguntas preguntas) {
        this.preguntas = preguntas;
    }

    /**
     * Obtiene la respuesta de esta asociación.
     *
     * @return El objeto Respuesta.
     */
    public Respuesta getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta para esta asociación.
     *
     * @param respuesta El nuevo objeto Respuesta.
     */
    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Devuelve una representación en formato de cadena del par pregunta-respuesta,
     * útil para depuración.
     *
     * @return Una cadena que representa el estado del objeto.
     */
    @Override
    public String toString() {
        return "PreguntasRespuestas{" +
                "preguntas=" + preguntas +
                ", respuesta=" + respuesta +
                '}';
    }
}