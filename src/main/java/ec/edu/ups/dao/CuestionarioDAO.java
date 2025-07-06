package ec.edu.ups.dao;

import ec.edu.ups.modelo.Preguntas;

import java.util.List;

public interface CuestionarioDAO {
    void crear(Preguntas preguntas);
    List<Preguntas> listarPreguntas();
    List<Preguntas> listarPreguntasEnunciado();
}
