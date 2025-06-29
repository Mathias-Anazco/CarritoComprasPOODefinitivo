package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final UsuarioCrearView usuarioCrearView;
    private final UsuarioListarView usuarioListarView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioModificarView usuarioModificarView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, UsuarioCrearView usuarioCrearView, UsuarioListarView usuarioListarView, UsuarioEliminarView usuarioEliminarView, UsuarioModificarView usuarioModificarView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioListarView = usuarioListarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuario = null;
        configurarEventosEnVistas();
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
                if (loginView.getTxtUsername().getText().isEmpty() && loginView.getTxtContraseña().getText().isEmpty()) {
                    loginView.mostrarMensaje("Debe ingresar un nombre de usuario y una contraseña.");
                    return;
                } else {
                    crearUsuario();
                    loginView.mostrarMensaje("Usuario creado exitosamente.");
                }

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
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContraseña().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        } else {
            loginView.dispose();
        }
    }

    public void crearUsuario() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContraseña().getText();

        Usuario usuario = new Usuario (username, contrasenia, Rol.USUARIO);
        usuarioDAO.crear(usuario);

    }

    public void cerrarSesion() {
        usuario = null;
        loginView.setVisible(true);
    }

    public Usuario getUsuarioAutenticado(){
        return usuario;
    }
}
