package ec.edu.ups.dao;

import ec.edu.ups.modelo.Preguntas;

import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Preguntas.
 * Establece las operaciones que deben ser implementadas por cualquier clase que gestione
 * la persistencia de las preguntas de seguridad.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public interface CuestionarioDAO {

    /**
     * Persiste un nuevo objeto Preguntas en el sistema de almacenamiento.
     *
     * @param preguntas La pregunta a ser creada.
     */
    void crear(Preguntas preguntas);

    /**
     * Recupera una lista de todas las preguntas de seguridad disponibles.
     *
     * @return Una lista que contiene todos los objetos Preguntas.
     */
    List<Preguntas> listarPreguntas();

    /**
     * Recupera una lista de todas las preguntas de seguridad disponibles.
     *
     * @return Una lista que contiene todos los objetos Preguntas.
     */
    List<Preguntas> listarPreguntasEnunciado();
}