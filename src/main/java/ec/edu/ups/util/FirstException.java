package ec.edu.ups.util;

/**
 * Excepción personalizada de tipo no comprobada (unchecked) que se lanza cuando
 * una contraseña no cumple con los requisitos de seguridad establecidos.
 * <p>
 * Se utiliza para señalar que una contraseña es inválida por razones como
 * longitud insuficiente, falta de mayúsculas o caracteres especiales.
 * Al extender de {@link RuntimeException}, no es obligatorio capturarla o declararla.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class FirstException extends RuntimeException {

    /**
     * Construye una nueva FirstException con un mensaje de detalle específico.
     *
     * @param message El mensaje de detalle que describe por qué la contraseña es inválida.
     */
    public FirstException(String message) {
        super(message);
    }
}