package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.CuestionarioRecuperarView;
import ec.edu.ups.vista.CuestionarioView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CuestionarioController {

    private final CuestionarioView cuestionarioView;
    private final CuestionarioRecuperarView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private final Cuestionario cuestionario;
    private List<Respuesta> preguntasAleatorias;
    private final MensajeInternacionalizacionHandler mi;
    private String contraseniaUsuario;


    public CuestionarioController(CuestionarioView vista, CuestionarioDAO dao, String username,
                                  MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.cuestionario = new Cuestionario(username);
        this.recuperarView = null;
        this.contraseniaUsuario = null;

        this.cuestionario.aplicarIdioma(mi);

        List<Respuesta> todasLasPreguntas = cuestionario.preguntasPorDefecto();
        preguntasAleatorias = new ArrayList<>();

        boolean[] usadas = new boolean[todasLasPreguntas.size()];
        int cantidadDeseada = 6;
        int cantidadActual = 0;
        Random random = new Random();

        while (cantidadActual < cantidadDeseada) {
            int indice = random.nextInt(todasLasPreguntas.size());
            if (!usadas[indice]) {
                preguntasAleatorias.add(todasLasPreguntas.get(indice));
                usadas[indice] = true;
                cantidadActual++;
            }
        }

        cargarComboPreguntas();
        configurarEventosCuestionario();
    }


    public CuestionarioController(CuestionarioRecuperarView recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.recuperarView = recuperarView;
        this.contraseniaUsuario = contrasenia;

        this.cuestionario = cuestionarioDAO.buscarPorUsername(username);
        if (cuestionario == null) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas"));
            recuperarView.dispose();
            return;
        }

        preguntasAleatorias = cuestionario.getRespuestas();

        if (preguntasAleatorias.size() < 3) {
            recuperarView.mostrarMensaje("No hay suficientes preguntas para recuperación.");
            recuperarView.dispose();
            return;
        }


        recuperarView.getLblPregunta1().setText(preguntasAleatorias.get(0).getEnunciado());
        recuperarView.getLblPregunta2().setText(preguntasAleatorias.get(1).getEnunciado());
        recuperarView.getLblPregunta3().setText(preguntasAleatorias.get(2).getEnunciado());

        configurarEventosRecuperar();
    }

    private void configurarEventosCuestionario() {
        cuestionarioView.getCbxPreguntas().addActionListener(e -> preguntasCuestionario());
        cuestionarioView.getBtnGuardar().addActionListener(e -> guardar());
        cuestionarioView.getBtnTerminar().addActionListener(e -> finalizar());
    }

    private void configurarEventosRecuperar() {
        recuperarView.getBtnEnviar().addActionListener(e -> comprobarTodasRespuestas());
        recuperarView.getTerminarButton().addActionListener(e -> finalizarRecuperar());
    }

    private void comprobarTodasRespuestas() {
        String respuesta1 = recuperarView.getTxtRespuesta1().getText().trim();
        String respuesta2 = recuperarView.getTxtRespuesta2().getText().trim();
        String respuesta3 = recuperarView.getTxtRespuesta3().getText().trim();

        if (respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        boolean r1Correcta = respuesta1.equalsIgnoreCase(preguntasAleatorias.get(0).getRespuesta());
        boolean r2Correcta = respuesta2.equalsIgnoreCase(preguntasAleatorias.get(1).getRespuesta());
        boolean r3Correcta = respuesta3.equalsIgnoreCase(preguntasAleatorias.get(2).getRespuesta());

        if (r1Correcta && r2Correcta && r3Correcta) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.recuperada") + contraseniaUsuario);
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.incorrecta"));
        }
    }

    private void finalizarRecuperar() {
        recuperarView.dispose();
    }

    private void preguntasCuestionario() {
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

    private void guardar() {
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index < 0) return;

        String texto = cuestionarioView.getTxtRespuesta().getText().trim();
        if (texto.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = preguntasAleatorias.get(index);

        Respuesta yaRespondida = cuestionario.buscarRespuestaPorId(seleccionada.getId());
        if (yaRespondida != null) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.yaRespondida")); // Debes tener esta clave en tu archivo de mensajes
            return;
        }

        seleccionada.setRespuesta(texto);
        cuestionario.agregarRespuesta(seleccionada);
        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar() {
        if (cuestionario.getRespuestas().size() < 3) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.minimo"));
            return;
        }

        cuestionarioDAO.guardar(cuestionario);
        cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.ok"));
        cuestionarioView.dispose();
    }

    private void cargarComboPreguntas() {
        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            String etiqueta = mi.get("cuestionario.pregunta");
            cuestionarioView.getCbxPreguntas().addItem(etiqueta + " " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            cuestionarioView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }
    }
}
