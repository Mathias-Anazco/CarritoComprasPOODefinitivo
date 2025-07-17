package ec.edu.ups.modelo;

/**
 * Representa una pregunta de seguridad en el sistema.
 * Esta clase encapsula el identificador y el texto de una pregunta.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class Preguntas {
    private String id;
    private String enunciado;

    /**
     * Construye una nueva pregunta with un ID y un texto específicos.
     *
     * @param id        El identificador único de la pregunta.
     * @param enunciado El texto completo de la pregunta.
     */
    public Preguntas( String id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
    }

    /**
     * Obtiene el ID de la pregunta.
     *
     * @return El identificador de la pregunta.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID de la pregunta.
     *
     * @param id El nuevo identificador para la pregunta.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el texto de la pregunta.
     *
     * @return El texto de la pregunta.
     */
    public String getEnunciado() {
        return enunciado;
    }

    /**
     * Establece el texto de la pregunta.
     *
     * @param enunciado El nuevo texto para la pregunta.
     */
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * Devuelve el texto del enunciado de la pregunta.
     * Este formato es ideal para mostrar la pregunta en componentes de UI como JComboBox.
     *
     * @return El texto de la pregunta.
     */
    @Override
    public String toString() {
        return enunciado;
    }
}