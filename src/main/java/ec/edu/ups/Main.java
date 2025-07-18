package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;
import ec.edu.ups.vista.AdministracionView.CuestionarioRecuperarView;
import ec.edu.ups.vista.AdministracionView.CuestionarioView;
import ec.edu.ups.vista.AdministracionView.LoginView;
import ec.edu.ups.vista.AdministracionView.RegistrarView;
import ec.edu.ups.vista.CarritoView.CarritoAnadirView;
import ec.edu.ups.vista.CarritoView.CarritoEliminarView;
import ec.edu.ups.vista.CarritoView.CarritoListarView;
import ec.edu.ups.vista.CarritoView.CarritoModificarView;
import ec.edu.ups.vista.ProductoView.ProductoActualizarView;
import ec.edu.ups.vista.ProductoView.ProductoAnadirView;
import ec.edu.ups.vista.ProductoView.ProductoEliminarView;
import ec.edu.ups.vista.ProductoView.ProductoListaView;
import ec.edu.ups.vista.SeleccionArchivos.InicializarAplicacion;
import ec.edu.ups.vista.UsuarioView.UsuarioCrearView;
import ec.edu.ups.vista.UsuarioView.UsuarioEliminarView;
import ec.edu.ups.vista.UsuarioView.UsuarioListarView;
import ec.edu.ups.vista.UsuarioView.UsuarioModificarView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

/**
 * Clase principal que sirve como punto de entrada para la aplicación del sistema de compras.
 * Se encarga de instanciar y conectar todos los componentes de la arquitectura
 * MVC (Modelo-Vista-Controlador), gestionando el flujo desde el inicio de sesión
 * hasta la ventana principal de la aplicación.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class Main {
    /**
     * Punto de entrada de la aplicación.
     * Inicia la interfaz gráfica en el Event Dispatch Thread de Swing, orquesta la
     * secuencia de inicio de sesión y, tras una autenticación exitosa, construye
     * la interfaz principal con todas sus vistas, controladores y listeners.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Asegura que la GUI se ejecute en el hilo de despacho de eventos de Swing.
        java.awt.EventQueue.invokeLater(() -> {
            // Inicializa el manejador de internacionalización con el idioma español por defecto.
            MensajeInternacionalizacionHandler mi = new MensajeInternacionalizacionHandler("es", "EC");

            // Permite al usuario seleccionar el modo de almacenamiento (Memoria o Archivo).
            String[] opciones = {mi.get("login.memoria"), mi.get("login.archivo")};
            String modoSeleccionado = (String) JOptionPane.showInputDialog(
                    null,
                    mi.get("login.seleccionar_modo"),
                    "Modo de almacenamiento",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            // Si el usuario cancela la selección del modo, la aplicación se cierra.
            if (modoSeleccionado == null) {
                System.exit(0);
            }

            // Inicializa los DAOs (Data Access Objects) según el modo seleccionado.
            InicializarAplicacion inicializador = new InicializarAplicacion();
            inicializador.inicializarDAOs(mi, modoSeleccionado);

            // Obtiene las instancias de los DAOs inicializados.
            UsuarioDAO usuarioDAO = inicializador.getUsuarioDAO();
            ProductoDAO productoDAO = inicializador.getProductoDAO();
            CarritoDAO carritoDAO = inicializador.getCarritoDAO();
            CuestionarioDAO cuestionarioDAO = inicializador.getCuestionarioDAO();

            // Configura e muestra la ventana de inicio de sesión.
            LoginView loginView = new LoginView(mi);
            loginView.setVisible(true);

            // Inicializa las vistas relacionadas con el cuestionario de seguridad.
            CuestionarioView cuestionarioView = new CuestionarioView(mi, cuestionarioDAO);
            CuestionarioRecuperarView cuestionarioRecuperarView = new CuestionarioRecuperarView(mi);

            // Crea el controlador de usuario principal para la autenticación y registro.
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, mi, cuestionarioDAO, cuestionarioView, cuestionarioRecuperarView);

            // Agrega un listener para manejar el cierre de la ventana de login.
            loginView.addWindowListener(new WindowAdapter( ) {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Si un usuario ha sido autenticado, se procede a iniciar la aplicación principal.
                    Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAuntenticado != null) {
                        // Inicializa la ventana principal y todas las vistas internas.
                        MenuPrincipalView principalView = new MenuPrincipalView(mi);
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mi);
                        ProductoListaView productoListaView = new ProductoListaView(mi);
                        ProductoActualizarView productoActualizarView = new ProductoActualizarView(mi);
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mi);
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mi);
                        CarritoListarView carritoListarView = new CarritoListarView(mi);
                        CarritoModificarView carritoModificarView = new CarritoModificarView(mi);
                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mi);
                        UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mi);
                        UsuarioListarView usuarioListarView = new UsuarioListarView(mi);
                        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mi);
                        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mi);
                        RegistrarView registrarView = new RegistrarView(mi);

                        // Crea los controladores para gestionar las operaciones de productos, carritos y usuarios.
                        ProductoController productoController = new ProductoController(productoDAO, productoAnadirView, productoListaView, carritoAnadirView, productoEliminarView, productoActualizarView, mi );
                        CarritoController carritoController = new CarritoController(carritoDAO, carritoAnadirView, productoDAO, carritoListarView, usuarioAuntenticado, carritoModificarView, carritoEliminarView, mi);
                        UsuarioController adminUsuarioController = new UsuarioController(usuarioDAO, usuarioCrearView, usuarioListarView, usuarioEliminarView, usuarioModificarView, mi, registrarView);

                        // Muestra un mensaje de bienvenida y ajusta la visibilidad de los menús según el rol del usuario.
                        principalView.mostrarMensaje("Bienvenido: " + usuarioAuntenticado.getUsername());
                        if (usuarioAuntenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        // Configura los ActionListeners para los elementos del menú de la ventana principal.
                        principalView.getMenuItemCrearProducto().addActionListener(e1 -> {
                            if (!productoAnadirView.isVisible()) {
                                productoAnadirView.setVisible(true);
                                principalView.getjDesktopPane().add(productoAnadirView);
                            }
                        });

                        principalView.getMenuItemBuscarProducto().addActionListener(e1 -> {
                            if (!productoListaView.isVisible()) {
                                productoListaView.setVisible(true);
                                principalView.getjDesktopPane().add(productoListaView);
                            }
                        });

                        principalView.getMenuItemCrearCarrito().addActionListener(e1 -> {
                            if (!carritoAnadirView.isVisible()) {
                                carritoAnadirView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoAnadirView);
                            }
                        });

                        principalView.getMenuItemEliminarProducto().addActionListener(e1 -> {
                            if (!productoEliminarView.isVisible()) {
                                productoEliminarView.setVisible(true);
                                principalView.getjDesktopPane().add(productoEliminarView);
                            }
                        });

                        principalView.getMenuItemActualizarProducto().addActionListener(e1 -> {
                            if (!productoActualizarView.isVisible()) {
                                productoActualizarView.setVisible(true);
                                principalView.getjDesktopPane().add(productoActualizarView);
                            }
                        });

                        principalView.getMenuItemBuscarCarrito().addActionListener(e1 -> {
                            if (!carritoListarView.isVisible()) {
                                carritoListarView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoListarView);
                            }
                        });

                        principalView.getMenuItemCerrarSesion().addActionListener(e1 -> {
                            if (principalView.mostrarMensajePregunta(mi.get("login.main_cerrarSesion"))) {
                                principalView.dispose(); // Cierra la ventana principal
                                loginView.setVisible(true); // Vuelve a mostrar la ventana de login
                                loginView.limpiarCampos(); // Limpia los campos de login
                            }
                        });

                        principalView.getMenuItemCrearUsuario().addActionListener(e1 -> {
                            if (!usuarioCrearView.isVisible()) {
                                usuarioCrearView.setVisible(true);
                                principalView.getjDesktopPane().add(usuarioCrearView);
                            }
                        });

                        principalView.getMenuItemListarUsuario().addActionListener(e1 -> {
                            if (!usuarioListarView.isVisible()) {
                                usuarioListarView.setVisible(true);
                                principalView.getjDesktopPane().add(usuarioListarView);
                            }
                        });

                        principalView.getMenuItemEliminarUsuario().addActionListener(e1 -> {
                            if (!usuarioEliminarView.isVisible()) {
                                usuarioEliminarView.setVisible(true);
                                principalView.getjDesktopPane().add(usuarioEliminarView);
                            }
                        });

                        principalView.getMenuItemModificarCarrito().addActionListener(e1 -> {
                            if (!carritoModificarView.isVisible()) {
                                carritoModificarView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoModificarView);
                            }
                        });

                        principalView.getMenuItemEliminarCarrito().addActionListener(e1 -> {
                            if (!carritoEliminarView.isVisible()) {
                                carritoEliminarView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoEliminarView);
                            }
                        });

                        principalView.getMenuItemSalir().addActionListener(e1 -> {
                            if(principalView.mostrarMensajePregunta(mi.get("login.main_salir"))) {
                                System.exit(0); // Cierra la aplicación completamente
                            }
                        });

                        principalView.getMenuItemActualizarUsuario().addActionListener(e1 -> {
                            if (!usuarioModificarView.isVisible()) {
                                usuarioModificarView.setVisible(true);
                                principalView.getjDesktopPane().add(usuarioModificarView);
                            }
                        });

                        // Listeners para cambiar el idioma de la aplicación.
                        principalView.getMenuItemEspanol().addActionListener(e1 -> {
                            mi.setLenguaje( "es", "EC");
                            principalView.cambiarIdioma();
                            carritoAnadirView.cambiarIdioma();
                            carritoEliminarView.cambiarIdioma();
                            carritoListarView.cambiarIdioma();
                            carritoModificarView.cambiarIdioma();
                            productoActualizarView.cambiarIdioma();
                            productoAnadirView.cambiarIdioma();
                            productoEliminarView.cambiarIdioma();
                            productoListaView.cambiarIdioma();
                            usuarioModificarView.cambiarIdioma();
                            usuarioEliminarView.cambiarIdioma();
                            usuarioCrearView.cambiarIdioma();
                            usuarioListarView.cambiarIdioma();
                        });

                        principalView.getMenuItemIngles().addActionListener(e1 -> {
                            mi.setLenguaje("en", "US");
                            principalView.cambiarIdioma();
                            carritoAnadirView.cambiarIdioma();
                            carritoEliminarView.cambiarIdioma();
                            carritoListarView.cambiarIdioma();
                            carritoModificarView.cambiarIdioma();
                            productoActualizarView.cambiarIdioma();
                            productoAnadirView.cambiarIdioma();
                            productoEliminarView.cambiarIdioma();
                            productoListaView.cambiarIdioma();
                            usuarioModificarView.cambiarIdioma();
                            usuarioEliminarView.cambiarIdioma();
                            usuarioCrearView.cambiarIdioma();
                            usuarioListarView.cambiarIdioma();
                        });

                        principalView.getMenuItemFrances().addActionListener(e1 -> {
                            mi.setLenguaje("fr", "FR");
                            principalView.cambiarIdioma();
                            carritoAnadirView.cambiarIdioma();
                            carritoEliminarView.cambiarIdioma();
                            carritoListarView.cambiarIdioma();
                            carritoModificarView.cambiarIdioma();
                            productoActualizarView.cambiarIdioma();
                            productoAnadirView.cambiarIdioma();
                            productoEliminarView.cambiarIdioma();
                            productoListaView.cambiarIdioma();
                            usuarioModificarView.cambiarIdioma();
                            usuarioEliminarView.cambiarIdioma();
                            usuarioCrearView.cambiarIdioma();
                            usuarioListarView.cambiarIdioma();
                        });
                    }
                }
            });
        });
    }
}