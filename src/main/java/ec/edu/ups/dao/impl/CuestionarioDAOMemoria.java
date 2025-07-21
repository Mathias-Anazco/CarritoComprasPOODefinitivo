package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementación en memoria de la interfaz CuestionarioDAO.
 * Gestiona una lista predefinida de preguntas de seguridad, cuyo texto
 * es internacionalizado a través de un {@link MensajeInternacionalizacionHandler}.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CuestionarioDAOMemoria implements CuestionarioDAO {

    private List<Preguntas> preguntas;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Construye una nueva instancia del DAO y carga las preguntas iniciales.
     *
     * @param mi El manejador de internacionalización para traducir las preguntas.
     */
    public CuestionarioDAOMemoria(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.preguntas = new ArrayList<>();
        cargarPreguntas();
    }

    /**
     * Carga una lista predefinida de preguntas, obteniendo el texto
     * correspondiente del manejador de internacionalización.
     */
    public void cargarPreguntas() {
        preguntas.clear();
        preguntas.add(new Preguntas("1", mi.get("pregunta.color_favorito")));
        preguntas.add(new Preguntas("2", mi.get("pregunta.instrumento_musical_favorito")));
        preguntas.add(new Preguntas("3", mi.get("pregunta.comida_favorita")));
        preguntas.add(new Preguntas("4", mi.get("pregunta.pais_que_visitaste_por_primera_vez")));
        preguntas.add(new Preguntas("5", mi.get("pregunta.segundo_nombre_de_tu_padre")));
        preguntas.add(new Preguntas("6", mi.get("pregunta.cancion_favorita")));
        preguntas.add(new Preguntas("7", mi.get("pregunta.tu_libro_favorito")));
    }

    /**
     * Añade una nueva pregunta a la lista en memoria.
     *
     * @param pregunta La pregunta a ser creada.
     */
    @Override
    public void crear(Preguntas pregunta) {
        preguntas.add(pregunta);
    }

    /**
     * Devuelve la lista de preguntas de seguridad disponibles.
     *
     * @return Una lista de objetos Preguntas.
     */
    @Override
    public List<Preguntas> listarPreguntas() {
        return preguntas;
    }

    /**
     * Devuelve la lista de preguntas de seguridad disponibles.
     *
     * @return Una lista de objetos Preguntas.
     */
    @Override
    public List<Preguntas> listarPreguntasEnunciado() {
        return preguntas;
    }

    /**
     * Actualiza el manejador de idioma y recarga las preguntas en el nuevo idioma.
     *
     * @param nuevoMi El nuevo manejador de internacionalización a utilizar.
     */
    public void actualizarIdioma(MensajeInternacionalizacionHandler nuevoMi) {
        this.mi = nuevoMi;
        cargarPreguntas();
    }
}