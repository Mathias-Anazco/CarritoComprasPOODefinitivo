package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private UsuarioCrearView usuarioCrearView;
    private UsuarioListarView usuarioListarView;
    private UsuarioEliminarView usuarioEliminarView;
    private UsuarioModificarView usuarioModificarView;
    private CuestionarioDAO cuestionarioDAO;
    private final MensajeInternacionalizacionHandler mi;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, CuestionarioDAO cuestionarioDAO, MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.cuestionarioDAO = cuestionarioDAO;
        this.mi = mi;
        this.usuario = null;
        configurarEventosEnVistas();
    }
    public UsuarioController(UsuarioDAO usuarioDAO, UsuarioCrearView usuarioCrearView,
                             UsuarioListarView usuarioListarView, UsuarioEliminarView usuarioEliminarView,
                             UsuarioModificarView usuarioModificarView, MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioListarView = usuarioListarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.mi = mi;
        configurarEventosUsuarios();
    }

    private void configurarEventosEnVistas() {
        loginView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuario();
            }
        });
        loginView.getBtnOlvidar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperar();
            }
        });
        loginView.getBtnSalir() . addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        loginView.getCbxIdiomas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma();
            }
        });
    }



    private void configurarEventosUsuarios(){
        usuarioCrearView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        usuarioListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuario();
            }
        });
        usuarioListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarUsuarios();
            }
        });
        usuarioEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarioParaEliminar();
            }
        });
        usuarioEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        usuarioModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuarioParaModificar();
            }
        });
        usuarioModificarView.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarUsuario();
            }
        });

    }
    private void cambiarIdioma() {
        String seleccion = (String) loginView.getCbxIdiomas().getSelectedItem();

        if (seleccion != null) {
            switch (seleccion) {
                case "Español":
                    mi.setLenguaje("es", "EC");
                    break;
                case "English":
                    mi.setLenguaje("en", "US");
                    break;
                case "Français":
                    mi.setLenguaje("fr", "FR");
                    break;
            }

            // Actualiza la interfaz con el nuevo idioma
            loginView.actualizarTextos(mi);
        }

    }
    private void salir(){
        loginView.dispose();
        System.exit(0);
    }
    private void recuperar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_recuperar"));
        if (confirmado) {
            String username = loginView.getTxtUsername().getText().trim();

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario == null) {
                loginView.mostrarMensaje(mi.get("login.mensaje.usuario_no_encontrado"));
                return;
            }

            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_no_disponible_admin"));
                return;
            }

            Cuestionario cuestionario = cuestionarioDAO.buscarPorUsername(username);
            if (cuestionario == null || cuestionario.getRespuestas().isEmpty()) {
                loginView.mostrarMensaje(mi.get("login.mensaje.sin_preguntas"));
                return;
            }

            CuestionarioRecuperarView recuperarView = new CuestionarioRecuperarView(mi);
            CuestionarioController controller = new CuestionarioController(
                    recuperarView, cuestionarioDAO, username, usuario.getContrasenia(), mi
            );

            recuperarView.setVisible(true);
            loginView.setVisible(false);

            recuperarView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            recuperarView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loginView.setVisible(true);
                }
            });

        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_cancelada"));
        }
    }


    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsername().getText();
        String contrasenia = usuarioModificarView.getTxtContrasenia().getText();
        String nombre = usuarioModificarView.getTxtName().getText();

        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty()) {
            usuarioModificarView.mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }

        Usuario usuario1 = usuarioDAO.buscarPorUsername(nombre);
        if (usuario1 == null) {
            usuarioModificarView.mostrarMensaje("Usuario no encontrado.");
            return;
        }

        usuario1.setUsername(username);
        usuario1.setContrasenia(contrasenia);
        usuarioDAO.actualizar(usuario1);

        usuarioModificarView.mostrarMensaje("Usuario modificado exitosamente: " + username);
        usuarioModificarView.limpiarCampos();
    }

    private void buscarUsuarioParaModificar() {
        String username = usuarioModificarView.getTxtName().getText();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
        if(usuario1 != null) {
            usuarioModificarView.getTxtUsername().setText(usuario1.getUsername());
            usuarioModificarView.getTxtContrasenia().setText(usuario1.getContrasenia());
        } else  {
            usuarioModificarView.mostrarMensaje("Usuario no encontrado.");
            usuarioModificarView.getTxtName().setText("");
        }
    }

    private void buscarUsuarioParaEliminar() {
        usuarioEliminarView.getModelo().setRowCount(0);
        String usernameBuscado = usuarioEliminarView.getTxtUsuario().getText().trim();
        boolean encontrado = false;

        for (Usuario usuario : usuarioDAO.listarTodos()) {
            if (usuario.getUsername().equals(usernameBuscado)) {
                Object[] fila = {
                        usuario.getUsername(),
                        usuario.getContrasenia(),
                        usuario.getRol().toString()
                };
                usuarioEliminarView.getModelo().addRow(fila);
                encontrado = true;
                break; // ya no necesitamos seguir buscando
            }
        }

        if (!encontrado) {
            usuarioEliminarView.mostrarMensaje("Usuario no encontrado.");
            usuarioEliminarView.getTxtUsuario().setText("");
        }
    }

    private void eliminarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText();
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje("Usuario no encontrado.");
            return;
        }
        usuarioDAO.eliminar(username);
        usuarioEliminarView.mostrarMensaje("Usuario eliminado exitosamente: " + username);
        usuarioEliminarView.getTxtUsuario().setText("");
    }

    private void listarUsuarios() {
        usuarioListarView.getModelo().setRowCount(0);
        for (Usuario usuario : usuarioDAO.listarTodos()) {
            Object[] fila = new Object[3];
            fila[0] = usuario.getUsername();
            fila[1] = usuario.getContrasenia();
            fila[2] = usuario.getRol().toString();
            usuarioListarView.getModelo().addRow(fila);
        }
        usuarioListarView.mostrarMensaje("Usuarios listados exitosamente.");
    }

    private void buscarUsuario() {
        usuarioListarView.getModelo().setRowCount(0);
        for(Usuario usuario1: usuarioDAO.listarTodos()) {
            if (usuario1.getUsername().equals(usuarioListarView.getTxtUsuario().getText())) {
                Object[] fila = new Object[3];
                fila[0] = usuario1.getUsername();
                fila[1] = usuario1.getContrasenia();
                fila[2] = usuario1.getRol().toString();
                usuarioListarView.getModelo().addRow(fila);

            }
        }

    }

    private void registrarUsuario() {
        String username = usuarioCrearView.getTxtUsername().getText();
        String contrasenia = usuarioCrearView.getTxtPassword().getText();
        Rol rol = usuarioCrearView.getRolSeleccionado();

        if (usuarioCrearView.getTxtUsername().getText().isEmpty() || usuarioCrearView.getTxtPassword().getText().isEmpty() || rol == null) {
            usuarioCrearView.mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioCrearView.mostrarMensaje("Ese nombre de usuario ya está en uso.");
            return;
        }

        Usuario usuario = new Usuario(username, contrasenia, rol);
        usuarioDAO.crear(usuario);

        usuarioCrearView.mostrarMensaje("Usuario creado exitosamente: " + username);
        usuarioCrearView.limpiarCampos();
    }


    private void autenticar() {
        String username = loginView.getTxtUsername().getText().trim();
        String contrasenia = loginView.getTxtContraseña().getText().trim();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos");
        } else {
            Cuestionario cuestionario = cuestionarioDAO.buscarPorUsername(username);
            if (cuestionario == null || cuestionario.getRespuestas().size() < 3) {
                loginView.mostrarMensaje("Completa el cuestionario para iniciar sesión");

                CuestionarioView cuestionarioView = new CuestionarioView();
                CuestionarioController controller = new CuestionarioController(
                        cuestionarioView, cuestionarioDAO, username, mi
                );
                cuestionarioView.setVisible(true);
                loginView.setVisible(false);

                cuestionarioView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cuestionarioView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loginView.setVisible(true);
                    }
                });

            } else {
                loginView.dispose();
            }
        }
    }

    public void crearUsuario() {
        boolean confirmado = loginView.mostrarMensajePregunta("¿Desea crear el usuario?");
        if (confirmado) {
            String username = loginView.getTxtUsername().getText().trim();
            String contrasenia = loginView.getTxtContraseña().getText().trim();

            if (usuarioDAO.buscarPorUsername(username) != null) {
                loginView.mostrarMensaje("Error: El nombre de usuario ya está en uso");
                return;
            }
            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(nuevoUsuario);
            loginView.mostrarMensaje("Usuario creado");

            CuestionarioView cuestionarioView = new CuestionarioView();
            CuestionarioController cuestionarioController = new CuestionarioController(cuestionarioView,
                    cuestionarioDAO, username, mi);
            cuestionarioView.setVisible(true);

            loginView.setVisible(false);

            cuestionarioView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cuestionarioView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e){
                    loginView.setVisible(true);
                }
            });
        } else {
            loginView.mostrarMensaje("Creación cancelada");
        }
    }



    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

}
