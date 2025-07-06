package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.*;
import ec.edu.ups.vista.UsuarioView.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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


    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, MensajeInternacionalizacionHandler mi,
                             CuestionarioDAO cuestionarioDAO, CuestionarioView cuestionarioView, CuestionarioRecuperarView cuestionarioRecuperarView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.mi = mi;
        this.cuestionarioDAO = cuestionarioDAO; // <--- asigna el DAO aquí
        this.cuestionarioView = cuestionarioView;
        this.cuestionarioRecuperarView = cuestionarioRecuperarView;
        this.usuario = null;
        this.registrarView = new RegistrarView(mi);
        this.registrarView.cambiarIdioma(mi);
        configurarEventosEnVistas();
        configurarEventosPreguntas();
        configurarEventosRespuestas();
    }


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

    //login
    private void configurarEventosEnVistas() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(false);
                registrarView.setVisible(true);
            }
        });
        loginView.getBtnSalir().addActionListener(e -> salir());
        loginView.getCbxIdiomas().addActionListener(e -> cambiarIdioma());
    }



    //Usuario
    private void configurarEventosUsuarios() {
        usuarioCrearView.getBtnRegistrar().addActionListener(e -> registrarUsuario());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuario());
        usuarioListarView.getBtnListar().addActionListener(e -> listarUsuarios());
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaModificar());
        usuarioModificarView.getBtnEditar().addActionListener(e -> modificarUsuario());
    }
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
        if (!celular.matches("\\d+")) {
            usuarioCrearView.mostrarMensaje(mi.get("mensaje.error.celular_numerico"));
            return;
        }
        if (!correo.matches("^[\\w.-]+@gmail\\.com$")) {
            registrarView.mostrarMensaje("mensaje.correo.invalido");
            return;
        }
        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.nombre.en.uso"));
            return;
        }

        String fechaNacimiento = dia + "/" + mes + "/" + año;
        Rol rol = usuarioCrearView.getRolSeleccionado();
        Usuario nuevoUsuario = new Usuario(username, contrasenia, rol);
        nuevoUsuario.setNombreCompleto(nombreCompleto);
        nuevoUsuario.setCelular(celular);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setFechaNacimiento(fechaNacimiento);

        usuarioDAO.crear(nuevoUsuario);
        usuarioCrearView.mostrarMensaje(mi.get("usuario.creado") + ": " + username);
        usuarioCrearView.limpiarCampos();
    }
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
        if (!correo.matches("^[\\w.-]+@gmail\\.com$")) {
            registrarView.mostrarMensaje("mensaje.correo.invalido");
            return;
        }
        if (!celular.matches("\\d{10}")) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.celular.invalido"));
            return;
        }

        String fechaNacimiento = dia + "/" + mes + "/" + año;
        usuario.setUsername(username);
        usuario.setContrasenia(contrasenia);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setCorreo(correo);
        usuario.setCelular(celular);
        usuario.setFechaNacimiento(fechaNacimiento);

        usuarioDAO.actualizar(usuario);
        usuarioModificarView.mostrarMensaje(mi.get("usuario.modificado") + ": " + username);
        usuarioModificarView.limpiarCampos();
    }



    //Preguntas
    private void configurarEventosPreguntas() {
        registrarView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuario();
                registrarView.setVisible(false);
                cuestionarioView.setVisible(true);
                System.out.println("llego hasta aqui");
            }
        });
        cuestionarioView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerRespuesta();
                cuestionarioView.limpiarCampos();

                if (preguntasRes.size() == 3){
                    cuestionarioView.getBtnTerminar().setEnabled(true);
                }
            }
        });
        cuestionarioView.getBtnTerminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cuestionarioView.getBtnTerminar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (preguntasRes.size() < 3) {
                            cuestionarioView.mostrarMensaje(mi.get("mensaje.minimo.tres.preguntas")); // Añade esta clave en tus properties
                            return;
                        }

                        obtenerPregunta();
                        cuestionarioView.setVisible(false);
                        loginView.setVisible(true);
                    }
                });

            }
        });
    }
    public void obtenerPregunta(){
        if (userRegistrar != null){
            userRegistrar.agregarPreguntas(preguntasRes);
            usuarioDAO.actualizar(userRegistrar);
        }
    }
    public void obtenerRespuesta(){
        Preguntas preguntas = (Preguntas) cuestionarioView.getCbxPreguntas().getSelectedItem();
        Respuesta respuesta = new Respuesta(cuestionarioView.getTxtRespuesta().getText());
        PreguntasRespuestas preguntasRespuestas = new PreguntasRespuestas(preguntas, respuesta);
        preguntasRes.add(preguntasRespuestas);
        cuestionarioView.mostrarMensaje(mi.get("mensaje.respuesta.guardada") + ": " + preguntasRespuestas.getRespuesta());
    }


    private void configurarEventosRespuestas(){
        loginView.getBtnOlvidar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(false);
                cuestionarioRecuperarView.setVisible(true);
            }
        });
        cuestionarioRecuperarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                // Limpiar el combo por si tenía algo antes
                JComboBox<Preguntas> cbx = cuestionarioRecuperarView.getCbxPreguntas();
                cbx.removeAllItems();

                // Llenar el combo con las preguntas del usuario
                for (PreguntasRespuestas pr : preguntasUsuario) {
                    cbx.addItem(pr.getPreguntas());
                }

                // Guardar temporalmente el usuario para la validación posterior
                usuario = usuarioEncontrado;

                // Habilitar botón de enviar si es necesario
                cuestionarioRecuperarView.getBtnEnviar().setEnabled(true);
            }
        });
        cuestionarioRecuperarView.getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Preguntas preguntaSeleccionada = (Preguntas) cuestionarioRecuperarView.getCbxPreguntas().getSelectedItem();
                String respuestaIngresada = cuestionarioRecuperarView.getTxtRespuesta1().getText().trim();

                if (preguntaSeleccionada == null || respuestaIngresada.isEmpty()) {
                    cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.campos.obligatorios"));
                    return;
                }

                // Buscar usuario por nombre ingresado (debe haberse hecho antes)
                if (usuario == null) {
                    cuestionarioRecuperarView.mostrarMensaje(mi.get("usuario.no.encontrado"));
                    return;
                }

                // Verificar si la respuesta es correcta
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

                // Crear campo de contraseña
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

                    if (nuevaContrasenia.isEmpty()) {
                        cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.contrasena.invalida"));
                        return;
                    }

                    usuario.setContrasenia(nuevaContrasenia);
                    usuarioDAO.actualizar(usuario);
                    cuestionarioRecuperarView.mostrarMensaje(mi.get("mensaje.contrasena.actualizada"));
                    cuestionarioRecuperarView.setVisible(false);
                    loginView.setVisible(true);
                }
            }
        });



    }


    private void cambiarIdioma() {
        String seleccion = (String) loginView.getCbxIdiomas().getSelectedItem();
        if (seleccion != null) {
            switch (seleccion) {
                case "Español": mi.setLenguaje("es", "EC"); break;
                case "English": mi.setLenguaje("en", "US"); break;
                case "Français": mi.setLenguaje("fr", "FR"); break;
            }

            // Actualizar textos de TODAS las vistas
            loginView.actualizarTextos(mi);
            registrarView.cambiarIdioma(mi);
            cuestionarioView.actualizarTextos(mi);
            cuestionarioRecuperarView.actualizarTextos(mi);

            // Actualizar preguntas en DAO y recargar en la vista
            if (cuestionarioDAO instanceof CuestionarioDAOMemoria) {
                ((CuestionarioDAOMemoria) cuestionarioDAO).actualizarIdioma(mi);
            }
            cuestionarioView.cargarPreguntas();
        }
    }



    private void salir() {
        loginView.dispose();
        System.exit(0);
    }

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

    private void crearUsuario() {
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
            return;
        }

        if (!celular.matches("\\d+")) {
            registrarView.mostrarMensaje(mi.get("usuario.celular.invalido"));
            return;
        }
        if (!correo.matches("^[\\w.-]+@gmail\\.com$")) {
            registrarView.mostrarMensaje("mensaje.correo.invalido");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            registrarView.mostrarMensaje(mi.get("usuario.nombre.en.uso"));
            return;
        }

        String fechaNacimiento = dia + "/" + mes + "/" + año;
        userRegistrar= new Usuario(username, contrasenia, Rol.USUARIO, nombreCompleto, fechaNacimiento, celular, correo);
        usuarioDAO.crear(userRegistrar);

        registrarView.mostrarMensaje(mi.get("usuario.creado"));

    }
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

}
