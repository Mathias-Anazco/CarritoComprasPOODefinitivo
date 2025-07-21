package ec.edu.ups.modelo;

/**
 * Modela la respuesta a una pregunta de seguridad.
 * Esta clase es un contenedor simple para el texto de la respuesta
 * proporcionada por un usuario.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/2025
 */
public class Respuesta {
    private String texto;

    /**
     * Construye una nueva respuesta con el texto especificado.
     *
     * @param texto El contenido de la respuesta.
     */
    public Respuesta(String texto){
        this.texto = texto;
    }

    /**
     * Obtiene el texto de la respuesta.
     *
     * @return El contenido de la respuesta.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el texto de la respuesta.
     *
     * @param texto El nuevo contenido para la respuesta.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Devuelve una representación en formato de cadena de la respuesta,
     * que es simplemente el texto de la misma.
     *
     * @return El texto de la respuesta.
     */
    @Override
    public String toString(){
        return texto;
    }
}