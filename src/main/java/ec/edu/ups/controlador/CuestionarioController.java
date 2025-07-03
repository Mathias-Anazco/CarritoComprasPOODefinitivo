package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.CuestionarioRecuperarView;
import ec.edu.ups.vista.AdministracionView.CuestionarioView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CuestionarioController {

    private final CuestionarioView cuestionarioView;
    private final CuestionarioRecuperarView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private final Cuestionario cuestionario;
    private List<Respuesta> preguntasAleatorias;
    private final MensajeInternacionalizacionHandler mi;
    private String contraseniaUsuario;
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;

    public CuestionarioController(CuestionarioView vista, CuestionarioDAO dao, String username,
                                  MensajeInternacionalizacionHandler mi, UsuarioDAO usuarioDAO) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.cuestionario = new Cuestionario(username);
        this.usuarioDAO = usuarioDAO;
        this.recuperarView = null;
        this.contraseniaUsuario = null;

        this.cuestionario.aplicarIdioma(mi);

        List<Respuesta> todasLasPreguntas = cuestionario.preguntasPorDefecto();
        preguntasAleatorias = new ArrayList<>();

        for (int i = 0; i < 3 && i < todasLasPreguntas.size(); i++) {
            preguntasAleatorias.add(todasLasPreguntas.get(i));
        }


        cargarComboPreguntas();
        configurarEventosCuestionario();
    }


    public CuestionarioController(CuestionarioRecuperarView recuperarView, CuestionarioDAO dao, UsuarioDAO usuarioDAO, Usuario usuario,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.usuarioDAO = usuarioDAO;
        this.cuestionarioView = null;
        this.recuperarView = recuperarView;
        this.contraseniaUsuario = contrasenia;

        this.usuario = usuarioDAO.buscarPorUsername(username);

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
            int opcion = JOptionPane.showConfirmDialog(
                    recuperarView,
                    mi.get("cuestionario.recuperar.confirmarCambio"),
                    mi.get("cuestionario.recuperar.tituloCambio"),
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                JPasswordField nuevaContrasena = new JPasswordField();
                int resultado = JOptionPane.showConfirmDialog(
                        recuperarView,
                        nuevaContrasena,
                        mi.get("cuestionario.recuperar.ingreseNueva"),
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (resultado == JOptionPane.OK_OPTION) {
                    String nueva = new String(nuevaContrasena.getPassword()).trim();
                    if (nueva.isEmpty()) {
                        recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.campoVacio"));
                    } else {
                        // Simulación de actualización de contraseña
                        recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.exito"));
                        usuario.setContrasenia(nueva);
                        usuarioDAO.actualizar(usuario);
                    }
                }
            }

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
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.yaRespondida"));
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
        int cantidadPreguntas = preguntasAleatorias.size();

        for (int i = 0; i < cantidadPreguntas; i++) {
            String enunciado = preguntasAleatorias.get(i).getEnunciado();
            cuestionarioView.getCbxPreguntas().addItem(enunciado);
        }

        if (cantidadPreguntas > 0) {
            cuestionarioView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }
    }
}
