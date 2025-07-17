package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.*;
import ec.edu.ups.vista.AdministracionView.*;
import ec.edu.ups.vista.UsuarioView.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal para todas las operaciones relacionadas con el usuario.
 * Gestiona la lógica de negocio para el inicio de sesión, registro (tanto por el
 * usuario como por el administrador), recuperación de contraseñas y las operaciones
 * CRUD de usuarios por parte del administrador.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final MensajeInternacionalizacionHandler mi;

    private LoginView loginView;
    private RegistrarView registrarView;
    private UsuarioCrearView usuarioCrearView;
    private UsuarioListarView usuarioListarView;
    private UsuarioEliminarView usuarioEliminarView;
    private UsuarioModificarView usuarioModificarView;
    private CuestionarioView cuestionarioView;
    private CuestionarioRecuperarView cuestionarioRecuperarView;
    private List<PreguntasRespuestas> preguntasRes = new ArrayList<>();
    private Usuario userRegistrar;
    private CuestionarioDAO cuestionarioDAO;

    /**
     * Constructor para el flujo de interacción del usuario final (login, registro, recuperación).
     *
     * @param usuarioDAO DAO para el acceso a datos de usuario.
     * @param loginView Vista principal de inicio de sesión.
     * @param mi Manejador de internacionalización para mensajes.
     * @param cuestionarioDAO DAO para acceder a las preguntas del cuestionario.
     * @param cuestionarioView Vista para que el usuario establezca sus preguntas de seguridad.
     * @param cuestionarioRecuperarView Vista para que el usuario recupere su contraseña.
     */
    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, MensajeInternacionalizacionHandler mi,
                             CuestionarioDAO cuestionarioDAO, CuestionarioView cuestionarioView, CuestionarioRecuperarView cuestionarioRecuperarView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.mi = mi;
        this.cuestionarioDAO = cuestionarioDAO;
        this.cuestionarioView = cuestionarioView;
        this.cuestionarioRecuperarView = cuestionarioRecuperarView;
        this.usuario = null;
        this.registrarView = new RegistrarView(mi);
        this.registrarView.cambiarIdioma(mi);
        configurarEventosEnVistas();
        configurarEventosPreguntas();
        configurarEventosRespuestas();
    }

    /**
     * Constructor para el flujo de administración de usuarios (CRUD) por parte del administrador.
     *
     * @param usuarioDAO DAO para el acceso a datos de usuario.
     * @param usuarioCrearView Vista para crear usuarios como administrador.
     * @param usuarioListarView Vista para listar y buscar usuarios.
     * @param usuarioEliminarView Vista para eliminar usuarios.
     * @param usuarioModificarView Vista para modificar usuarios.
     * @param mi Manejador de internacionalización para mensajes.
     * @param registrarView Vista de registro (referencia necesaria).
     */
    public UsuarioController(UsuarioDAO usuarioDAO, UsuarioCrearView usuarioCrearView,
                             UsuarioListarView usuarioListarView, UsuarioEliminarView usuarioEliminarView,
                             UsuarioModificarView usuarioModificarView, MensajeInternacionalizacionHandler mi, RegistrarView registrarView) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioListarView = usuarioListarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.mi = mi;
        this.registrarView = registrarView;
        configurarEventosUsuarios();
    }

    /**
     * Configura los eventos para la ventana de login, registro y cambio de idioma.
     */
    private void configurarEventosEnVistas() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
        loginView.getBtnRegistrarse().addActionListener(e -> {
            loginView.setVisible(false);
            registrarView.setVisible(true);
        });
        loginView.getBtnSalir().addActionListener(e -> salir());
        loginView.getCbxIdiomas().addActionListener(e -> cambiarIdioma());
    }


    /**
     * Configura los eventos para las vistas de administración de usuarios (CRUD).
     */
    private void configurarEventosUsuarios() {
        usuarioCrearView.getBtnRegistrar().addActionListener(e -> registrarUsuario());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuario());
        usuarioListarView.getBtnListar().addActionListener(e -> listarUsuarios());
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaModificar());
        usuarioModificarView.getBtnEditar().addActionListener(e -> modificarUsuario());
    }

    /**
     * Registra un nuevo usuario desde la vista del administrador.
     * Valida los datos y gestiona las excepciones de negocio.
     */
    private void registrarUsuario() {
        String nombreCompleto = usuarioCrearView.getTxtNombreCompleto().getText().trim();
        String username = usuarioCrearView.getTxtUsername().getText().trim();
        String contrasenia = usuarioCrearView.getTxtPassword().getText().trim();
        String celular = usuarioCrearView.getTxtCelular().getText().trim();
        String correo = usuarioCrearView.getTxtCorreo().getText().trim();
        Object dia = usuarioCrearView.getCbxDia().getSelectedItem();
        Object mes = usuarioCrearView.getCbxMes().getSelectedItem();
        Object año = usuarioCrearView.getCbxAño().getSelectedItem();

        if (nombreCompleto.isEmpty() || username.isEmpty() || contrasenia.isEmpty()
                || celular.isEmpty() || correo.isEmpty() || dia == null || mes == null || año == null) {
            usuarioCrearView.mostrarMensaje(mi.get("mensaje.campos.obligatorios"));
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.nombre.en.uso"));
            return;
        }

        String fechaNacimiento = dia + "/" + mes + "/" + año;
        Rol rol = usuarioCrearView.getRolSeleccionado();

        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombreCompleto(nombreCompleto);
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setContrasenia(contrasenia);
            nuevoUsuario.setCelular(celular);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setFechaNacimiento(fechaNacimiento);
            nuevoUsuario.setRol(rol);

            usuarioDAO.crear(nuevoUsuario);
            usuarioCrearView.mostrarMensaje(mi.get("usuario.creado") + ": " + username);
            usuarioCrearView.limpiarCampos();

        } catch (SecondExcepcion | FirstException | CelularException | CorreoException e) {
            usuarioCrearView.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Busca un usuario por su nombre de usuario y lo muestra en la tabla de la vista de listado.
     */
    private void buscarUsuario() {
        usuarioListarView.getModelo().setRowCount(0);
        String username = usuarioListarView.getTxtUsuario().getText().trim();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            Object[] fila = {
                    usuario.getNombreCompleto(), usuario.getUsername(), usuario.getContrasenia(),
                    usuario.getCorreo(), usuario.getCelular(), usuario.getFechaNacimiento(), usuario.getRol().toString()
            };
            usuarioListarView.getModelo().addRow(fila);
        }
    }

    /**
     * Lista todos los usuarios existentes en la tabla de la vista de listado.
     */
    private void listarUsuarios() {
        usuarioListarView.getModelo().setRowCount(0);
        for (Usuario usuario : usuarioDAO.listarTodos()) {
            Object[] fila = {
                    usuario.getNombreCompleto(), usuario.getUsername(), usuario.getContrasenia(),
                    usuario.getCorreo(), usuario.getCelular(), usuario.getFechaNacimiento(), usuario.getRol().toString()
            };
            usuarioListarView.getModelo().addRow(fila);
        }
        usuarioListarView.mostrarMensaje(mi.get("usuario.listado.exito"));
    }

    /**
     * Busca un usuario por nombre de usuario para cargarlo en la vista de eliminación.
     */
    private void buscarUsuarioParaEliminar() {
        usuarioEliminarView.getModelo().setRowCount(0);
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            Object[] fila = {
                    usuario.getNombreCompleto(), usuario.getUsername(), usuario.getContrasenia(),
                    usuario.getCorreo(), usuario.getCelular(), usuario.getFechaNacimiento(), usuario.getRol().toString()
            };
            usuarioEliminarView.getModelo().addRow(fila);
        } else {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
            usuarioEliminarView.getTxtUsuario().setText("");
        }
    }

    /**
     * Elimina el usuario que ha sido buscado y cargado en la vista de eliminación.
     */
    private void eliminarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
            return;
        }
        usuarioDAO.eliminar(username);
        usuarioEliminarView.mostrarMensaje(mi.get("usuario.eliminado") + ": " + username);
        usuarioEliminarView.getTxtUsuario().setText("");
    }

    /**
     * Busca un usuario por nombre de usuario y carga sus datos en los campos de la vista de modificación.
     */
    private void buscarUsuarioParaModificar() {
        String usernameBusqueda = usuarioModificarView.getTxtName().getText().trim();
        Usuario usuario = usuarioDAO.buscarPorUsername(usernameBusqueda);
        if (usuario == null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
            usuarioModificarView.getTxtName().setText("");
            return;
        }

        usuarioModificarView.getTxtUsername().setText(usuario.getUsername());
        usuarioModificarView.getTxtContrasenia().setText(usuario.getContrasenia());
        usuarioModificarView.getTxtNombreCompleto().setText(usuario.getNombreCompleto());
        usuarioModificarView.getTxtCorreo().setText(usuario.getCorreo());
        usuarioModificarView.getTxtCelular().setText(usuario.getCelular());

        String[] fecha = usuario.getFechaNacimiento().split("/");
        if (fecha.length == 3) {
            usuarioModificarView.getCbxDia().setSelectedItem(Integer.parseInt(fecha[0]));
            usuarioModificarView.getCbxMes().setSelectedItem(fecha[1]);
            usuarioModificarView.getCbxAño().setSelectedItem(Integer.parseInt(fecha[2]));
        }
    }

    /**
     * Aplica las modificaciones realizadas a un usuario desde la vista del administrador.
     */
    private void modificarUsuario() {
        String nombreBusqueda = usuarioModificarView.getTxtName().getText().trim();
        Usuario usuario = usuarioDAO.buscarPorUsername(nombreBusqueda);

        if (usuario == null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
            return;
        }

        String username = usuarioModificarView.getTxtUsername().getText().trim();
        String contrasenia = usuarioModificarView.getTxtContrasenia().getText().trim();
        String nombreCompleto = usuarioModificarView.getTxtNombreCompleto().getText().trim();
        String correo = usuarioModificarView.getTxtCorreo().getText().trim();
        String celular = usuarioModificarView.getTxtCelular().getText().trim();
        Object dia = usuarioModificarView.getCbxDia().getSelectedItem();
        Object mes = usuarioModificarView.getCbxMes().getSelectedItem();
        Object año = usuarioModificarView.getCbxAño().getSelectedItem();

        if (username.isEmpty() || contrasenia.isEmpty() || nombreCompleto.isEmpty()
                || correo.isEmpty() || celular.isEmpty() || dia == null || mes == null || año == null) {
            usuarioModificarView.mostrarMensaje(mi.get("mensaje.campos.obligatorios"));
            return;
        }

        String fechaNacimiento = dia + "/" + mes + "/" + año;

        try {
            usuario.setMensajeInternacionalizacionHandler(mi);
            usuario.setUsername(username);
            usuario.setContrasenia(contrasenia);
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setCorreo(correo);
            usuario.setCelular(celular);
            usuario.setFechaNacimiento(fechaNacimiento);

            usuarioDAO.actualizar(usuario);
            usuarioModificarView.mostrarMensaje(mi.get("usuario.modificado") + ": " + username);
            usuarioModificarView.limpiarCampos();

        } catch (SecondExcepcion | FirstException | CorreoException | CelularException e) {
            usuarioModificarView.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Configura los eventos para el proceso de registro y configuración de preguntas de seguridad.
     */
    private void configurarEventosPreguntas() {
        registrarView.getBtnRegistrar().addActionListener(e -> {
            boolean exito = crearUsuario();
            if (exito) {
                registrarView.setVisible(false);
                cuestionarioView.setVisible(true);
            }
        });

        cuestionarioView.getBtnGuardar().addActionListener(e -> {
            obtenerRespuesta();
            cuestionarioView.limpiarCampos();
            if (preguntasRes.size() == 3){
                cuestionarioView.getBtnTerminar().setEnabled(true);
            }
        });

        cuestionarioView.getBtnTerminar().addActionListener(e -> {
            if (preguntasRes.size() < 3) {
                cuestionarioView.mostrarMensaje(mi.get("mensaje.minimo.tres.preguntas"));
                return;
            }
            obtenerPregunta();
            cuestionarioView.setVisible(false);
            loginView.setVisible(true);
        });
    }

    /**
     * Asocia las preguntas y respuestas de seguridad al usuario que se está registrando.
     */
    public void obtenerPregunta(){
        if (userRegistrar != null){
            userRegistrar.agregarPreguntas(preguntasRes);
            usuarioDAO.actualizar(userRegistrar);
        }
    }

    /**
     * Recoge una pregunta y respuesta de la vista del cuestionario y la añade a la lista temporal.
     */
    public void obtenerRespuesta(){
        Preguntas preguntas = (Preguntas) cuestionarioView.getCbxPreguntas().getSelectedItem();
        Respuesta respuesta = new Respuesta(cuestionarioView.getTxtRespuesta().getText());
        PreguntasRespuestas preguntasRespuestas = new PreguntasRespuestas(preguntas, respuesta);
        preguntasRes.add(preguntasRespuestas);
        cuestionarioView.mostrarMensaje(mi.get("mensaje.respuesta.guardada") + ": " + preguntasRespuestas.getRespuesta());
    }

    /**
     * Configura los eventos para el flujo de recuperación de contraseña.
     */
    private void configurarEventosRespuestas(){
        loginView.getBtnOlvidar().addActionListener(e -> {
            loginView.setVisible(false);
            cuestionarioRecuperarView.setVisible(true);
        });

        cuestionarioRecuperarView.getBtnBuscar().addActionListener(e -> {
            String username = cuestionarioRecuperarView.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.usuario.vacio"));
                return;
            }
            Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);
            if (usuarioEncontrado == null) {
                cuestionarioRecuperarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
                return;
            }
            List<PreguntasRespuestas> preguntasUsuario = usuarioEncontrado.getPreguntasRespuestas();
            if (preguntasUsuario == null || preguntasUsuario.isEmpty()) {
                cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.preguntas.no.registradas"));
                return;
            }
            JComboBox<Preguntas> cbx = cuestionarioRecuperarView.getCbxPreguntas();
            cbx.removeAllItems();
            for (PreguntasRespuestas pr : preguntasUsuario) {
                cbx.addItem(pr.getPreguntas());
            }
            usuario = usuarioEncontrado;
            cuestionarioRecuperarView.getBtnEnviar().setEnabled(true);
        });

        cuestionarioRecuperarView.getBtnEnviar().addActionListener(e -> {
            Preguntas preguntaSeleccionada = (Preguntas) cuestionarioRecuperarView.getCbxPreguntas().getSelectedItem();
            String respuestaIngresada = cuestionarioRecuperarView.getTxtRespuesta1().getText().trim();

            if (preguntaSeleccionada == null || respuestaIngresada.isEmpty()) {
                cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.campos.obligatorios"));
                return;
            }
            if (usuario == null) {
                cuestionarioRecuperarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
                return;
            }

            boolean esCorrecta = false;
            for (PreguntasRespuestas pr : usuario.getPreguntasRespuestas()) {
                if (pr.getPreguntas().getEnunciado().equals(preguntaSeleccionada.getEnunciado()) &&
                        pr.getRespuesta().getTexto().equalsIgnoreCase(respuestaIngresada)) {
                    esCorrecta = true;
                    break;
                }
            }

            if (!esCorrecta) {
                cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.respuesta.incorrecta"));
                return;
            }

            JPasswordField campoContraseña = new JPasswordField();
            int opcion = JOptionPane.showConfirmDialog(
                    cuestionarioRecuperarView,
                    campoContraseña,
                    mi.get("mensaje.contrasena.ingresar"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (opcion == JOptionPane.OK_OPTION) {
                String nuevaContrasenia = new String(campoContraseña.getPassword()).trim();
                try {
                    usuario.setMensajeInternacionalizacionHandler(mi);
                    usuario.setContrasenia(nuevaContrasenia);
                    usuarioDAO.actualizar(usuario);
                    cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.contrasena.actualizada"));
                    cuestionarioRecuperarView.setVisible(false);
                    loginView.setVisible(true);
                } catch (FirstException ex) {
                    cuestionarioRecuperarView.mostrarMensaje(ex.getMessage());
                }
            }
        });
    }

    /**
     * Cambia el idioma de la aplicación y actualiza los textos en todas las vistas relevantes.
     */
    private void cambiarIdioma() {
        String seleccion = (String) loginView.getCbxIdiomas().getSelectedItem();
        if (seleccion != null) {
            switch (seleccion) {
                case "Español": mi.setLenguaje("es", "EC"); break;
                case "English": mi.setLenguaje("en", "US"); break;
                case "Français": mi.setLenguaje("fr", "FR"); break;
            }

            loginView.actualizarTextos(mi);
            registrarView.cambiarIdioma(mi);
            cuestionarioView.actualizarTextos(mi);
            cuestionarioRecuperarView.actualizarTextos(mi);

            if (cuestionarioDAO instanceof CuestionarioDAOMemoria) {
                ((CuestionarioDAOMemoria) cuestionarioDAO).actualizarIdioma(mi);
            }
            cuestionarioView.cargarPreguntas();
        }
    }

    /**
     * Cierra la aplicación.
     */
    private void salir() {
        loginView.dispose();
        System.exit(0);
    }

    /**
     * Autentica a un usuario.
     */
    private void autenticar() {
        String username = loginView.getTxtUsername().getText().trim();
        String contrasenia = loginView.getTxtContraseña().getText().trim();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(mi.get("login.mensaje.usuario_o_contrasena_incorrectos"));
        } else {
            loginView.dispose();
        }
    }

    /**
     * Crea un nuevo usuario desde la vista de registro público.
     * @return {@code true} si el usuario fue creado exitosamente, {@code false} en caso contrario.
     */
    private boolean crearUsuario() {
        String nombreCompleto = registrarView.getTxtNombreCompleto().getText().trim();
        String username = registrarView.getTxtUsuario().getText().trim();
        String contrasenia = registrarView.getTxtContraseña().getText().trim();
        String celular = registrarView.getTxtCelular().getText().trim();
        String correo = registrarView.getTxtCorreo().getText().trim();
        Object dia = registrarView.getCbxDia().getSelectedItem();
        Object mes = registrarView.getCbxMes().getSelectedItem();
        Object año = registrarView.getCbxAño().getSelectedItem();

        if (nombreCompleto.isEmpty() || username.isEmpty() || contrasenia.isEmpty()
                || celular.isEmpty() || correo.isEmpty() || dia == null || mes == null || año == null) {
            registrarView.mostrarMensaje(mi.get("mensaje.campos.obligatorios"));
            return false;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            registrarView.mostrarMensaje(mi.get("usuario.nombre.en.uso"));
            return false;
        }

        String fechaNacimiento = dia + "/" + mes + "/" + año;

        try {
            userRegistrar = new Usuario();
            userRegistrar.setMensajeInternacionalizacionHandler(mi);
            userRegistrar.setNombreCompleto(nombreCompleto);
            userRegistrar.setUsername(username);
            userRegistrar.setContrasenia(contrasenia);
            userRegistrar.setCelular(celular);
            userRegistrar.setCorreo(correo);
            userRegistrar.setFechaNacimiento(fechaNacimiento);
            userRegistrar.setRol(Rol.USUARIO);

            usuarioDAO.crear(userRegistrar);
            registrarView.mostrarMensaje(mi.get("usuario.creado"));
            return true;

        } catch (SecondExcepcion | FirstException | CelularException | CorreoException e) {
            registrarView.mostrarMensaje(e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el usuario que ha sido autenticado exitosamente.
     * @return El usuario autenticado, o {@code null} si no hay sesión activa.
     */
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }
}