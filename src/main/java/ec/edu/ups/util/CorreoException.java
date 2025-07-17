package ec.edu.ups.util;

/**
 * Excepción personalizada de tipo no comprobada (unchecked) que se lanza cuando
 * una dirección de correo electrónico no cumple con los criterios de validación.
 * <p>
 * Se utiliza para señalar errores específicos en el formato de un correo electrónico.
 * Al extender de {@link RuntimeException}, no es obligatorio capturarla o declararla.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CorreoException extends RuntimeException {

    /**
     * Construye una nueva CorreoException con un mensaje de detalle específico.
     *
     * @param message El mensaje de detalle que describe el error de validación.
     */
    public CorreoException(String message) {
        super(message);
    }
}