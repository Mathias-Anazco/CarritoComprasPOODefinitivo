package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Gestiona la internacionalización de la aplicación (i18n).
 * Esta clase encapsula {@link ResourceBundle} para cargar y proporcionar
 * mensajes de texto en diferentes idiomas, basados en archivos de propiedades.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Construye un nuevo manejador de internacionalización para un idioma y país específicos.
     * Carga el archivo de propiedades "mensajes" correspondiente a la localización.
     *
     * @param lenguaje El código de dos letras del idioma (ej. "es", "en").
     * @param pais     El código de dos letras del país (ej. "EC", "US").
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje de texto asociado a una clave específica en el idioma actual.
     *
     * @param key La clave del mensaje a recuperar del archivo de propiedades.
     * @return La cadena de texto traducida.
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia el idioma y el país del manejador y recarga el archivo de propiedades
     * correspondiente a la nueva localización.
     *
     * @param lenguaje El nuevo código de idioma.
     * @param pais     El nuevo código de país.
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene la localización (Locale) actualmente en uso.
     *
     * @return El objeto Locale activo.
     */
    public Locale getLocale() {
        return locale;
    }
}