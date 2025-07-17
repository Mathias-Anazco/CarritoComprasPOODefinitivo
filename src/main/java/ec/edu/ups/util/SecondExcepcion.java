package ec.edu.ups.util;

/**
 * Excepción personalizada de tipo no comprobada (unchecked) que se lanza cuando
 * un nombre de usuario (cédula) no cumple con los criterios de validación.
 * <p>
 * Se utiliza para señalar errores específicos en el formato o el algoritmo de
 * validación de la cédula ecuatoriana. Al extender de {@link RuntimeException},
 * no es obligatorio capturarla o declararla.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class SecondExcepcion extends RuntimeException {

    /**
     * Construye una nueva SecondExcepcion con un mensaje de detalle específico.
     *
     * @param message El mensaje de detalle que describe el error de validación de la cédula.
     */
    public SecondExcepcion(String message) {
        super(message);
    }
}