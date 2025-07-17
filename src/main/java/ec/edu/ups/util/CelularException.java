package ec.edu.ups.util;

/**
 * Excepción personalizada de tipo no comprobada (unchecked) que se lanza cuando
 * un número de celular no cumple con los criterios de validación.
 * <p>
 * Se utiliza para señalar errores específicos en el formato o longitud de un
 * número de celular. Al extender de {@link RuntimeException}, no es
 * obligatorio capturarla o declararla.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CelularException extends RuntimeException {

    /**
     * Construye una nueva CelularException con un mensaje de detalle específico.
     *
     * @param message El mensaje de detalle que describe el error de validación.
     */
    public CelularException(String message) {
        super(message);
    }
}