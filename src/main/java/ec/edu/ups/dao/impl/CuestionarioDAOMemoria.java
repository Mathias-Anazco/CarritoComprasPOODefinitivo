package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.util.List;
import java.util.ArrayList;

public class CuestionarioDAOMemoria implements CuestionarioDAO {

    private List<Preguntas> preguntas;
    private MensajeInternacionalizacionHandler mi;

    public CuestionarioDAOMemoria(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.preguntas = new ArrayList<>();
        cargarPreguntas();
    }

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

    @Override
    public void crear(Preguntas pregunta) {
        preguntas.add(pregunta);
    }

    @Override
    public List<Preguntas> listarPreguntas() {
        return preguntas;
    }

    @Override
    public List<Preguntas> listarPreguntasEnunciado() {
        return preguntas;
    }

    // Método para actualizar el handler de idioma y recargar preguntas
    public void actualizarIdioma(MensajeInternacionalizacionHandler nuevoMi) {
        this.mi = nuevoMi;
        cargarPreguntas();
    }
}

