package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad que proporciona métodos estáticos para formatear datos.
 * Incluye funcionalidades para convertir valores numéricos a formato de moneda
 * y objetos de fecha a cadenas de texto, de acuerdo a una localización específica.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class FormateadorUtils {

    /**
     * Formatea una cantidad numérica como una cadena de texto de moneda,
     * utilizando el formato específico de la localización (locale) proporcionada.
     *
     * @param cantidad El valor monetario a formatear.
     * @param locale   La localización que define el símbolo de moneda, separadores, etc.
     * @return Una cadena de texto con el valor formateado como moneda.
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea un objeto Date como una cadena de texto, utilizando el formato
     * de fecha mediano (MEDIUM) de la localización (locale) proporcionada.
     *
     * @param fecha  El objeto Date a formatear.
     * @param locale La localización que define el formato de la fecha.
     * @return Una cadena de texto con la fecha formateada.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }

}