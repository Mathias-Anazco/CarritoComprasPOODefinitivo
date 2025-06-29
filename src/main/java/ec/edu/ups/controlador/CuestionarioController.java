package ec.edu.ups.controlador;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.vista.CuestionarioRecuperarView;
import ec.edu.ups.vista.CuestionarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CuestionarioController {
    private final CuestionarioView cuestionarioView;
    private final CuestionarioRecuperarView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private final Cuestionario cuestionario;
    private List<Respuesta> preguntasAleatorias;
    private List<Respuesta> respuestasCorrectas;


    public CuestionarioController(CuestionarioView vista, CuestionarioDAO dao, String username) {
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.cuestionario = new Cuestionario(username);
        this.recuperarView = null;

        this.preguntasAleatorias = cuestionario.preguntasPorDefecto();
        Collections.shuffle(preguntasAleatorias);
        if (preguntasAleatorias.size() > 6) {
            preguntasAleatorias = preguntasAleatorias.subList(0, 6);
        }

        cargarComboPreguntas();
        configurarEventosCuestionario();
    }
    public CuestionarioController(CuestionarioRecuperarView recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia){
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.cuestionario = cuestionarioDAO.buscarPorUsername(username);
        this.recuperarView = recuperarView;
        this.respuestasCorrectas = new ArrayList<>();

        if (cuestionario == null) {
            recuperarView.mostrarMensaje("No hay preguntas guardadas para este usuario");
            recuperarView.dispose();
            return;
        }

        this.preguntasAleatorias = cuestionario.getRespuestas();

        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            recuperarView.getCbxPreguntas().addItem("Pregunta " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            recuperarView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }

        configurarEventosRecuperar(contrasenia);

    }

    private void configurarEventosCuestionario() {
        cuestionarioView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasCuestionario();
            }
        });

        cuestionarioView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        cuestionarioView.getBtnTerminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });
    }

    private void configurarEventosRecuperar(String contrasenia){
        recuperarView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasRecuperar();
            }
        });

        recuperarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestasRecuperar();
            }
        });

        recuperarView.getTerminarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarRecuperar(contrasenia);
            }
        });
    }

    private void preguntasRecuperar(){
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(index);
            recuperarView.getLblPregunta().setText(r.getEnunciado());

            if (respuestasCorrectas.contains(r)) {
                recuperarView.getTxtRespuesta().setText(r.getRespuesta());
            } else {
                recuperarView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardarRespuestasRecuperar() {
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index < 0 || index >= preguntasAleatorias.size()) {
            recuperarView.mostrarMensaje("Selecciona una pregunta válida");
            return;
        }

        Respuesta r = preguntasAleatorias.get(index);
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje("La respuesta no puede estar vacía");
            return;
        }

        if (respuestaUsuario.equalsIgnoreCase(r.getRespuesta())) {
            if (!respuestasCorrectas.contains(r)) {
                respuestasCorrectas.add(r);
                recuperarView.mostrarMensaje("Respuesta correcta");
                r.setRespuesta(respuestaUsuario);
            } else {
                recuperarView.mostrarMensaje("Ya respondiste correctamente esta pregunta");
            }
        } else {
            recuperarView.mostrarMensaje("Respuesta incorrecta. Intenta de nuevo");
        }
    }


    private void finalizarRecuperar(String contrasenia) {
        if (respuestasCorrectas.size() >= 3) {
            recuperarView.mostrarMensaje("¡Contraseña recuperada!: " + contrasenia);
            recuperarView.dispose();
        } else {
            recuperarView.mostrarMensaje("Debes responder correctamente al menos 3 preguntas");
        }
    }


    private void preguntasCuestionario(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0) {
            Respuesta r = preguntasAleatorias.get(index);
            cuestionarioView.getLblPregunta().setText(r.getEnunciado());

            Respuesta respondido = cuestionario.buscarRespuestaPorId(r.getId());
            if (respondido != null) {
                cuestionarioView.getTxtRespuesta().setText(respondido.getRespuesta());
            } else {
                cuestionarioView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardar(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index < 0) return;

        String texto = cuestionarioView.getTxtRespuesta().getText().trim();
        if (texto.isEmpty()) {
            cuestionarioView.mostrarMensaje("La respuesta no puede estar vacía");
            return;
        }

        Respuesta seleccionada = preguntasAleatorias.get(index);
        seleccionada.setRespuesta(texto);

        if (cuestionario.buscarRespuestaPorId(seleccionada.getId()) == null) {
            cuestionario.agregarRespuesta(seleccionada);
        }

        cuestionarioView.mostrarMensaje("Respuesta guardada");
    }

    private void finalizar(){
        if (cuestionario.getRespuestas().size() < 3) {
            cuestionarioView.mostrarMensaje("Debes responder al menos 3 preguntas");
            return;
        }

        cuestionarioDAO.guardar(cuestionario);
        cuestionarioView.mostrarMensaje("¡Cuestionario guardado exitosamente!");
        cuestionarioView.dispose();
    }

    private void cargarComboPreguntas() {
        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            cuestionarioView.getCbxPreguntas().addItem("Pregunta " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            cuestionarioView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }
    }
}
