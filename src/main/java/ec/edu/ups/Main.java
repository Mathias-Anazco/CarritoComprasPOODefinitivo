package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                //Iniciar sesión
                MensajeInternacionalizacionHandler mi = new MensajeInternacionalizacionHandler("es", "EC");

                ProductoDAO productoDAO = new ProductoDAOMemoria();
                CarritoDAO carritoDAO = new CarritoDAOMemoria();

                CuestionarioDAO cuestionarioDAO = new CuestionarioDAOMemoria();
                UsuarioDAO usuarioDAO = new UsuarioDAOMemoria(cuestionarioDAO);

                LoginView loginView = new LoginView(mi);
                loginView.setVisible(true);

                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView,  cuestionarioDAO, mi);

                loginView.addWindowListener(new WindowAdapter( ) {
                    @Override
                    public void windowClosed(WindowEvent e) {

                        Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                        if (usuarioAuntenticado != null) {
                            //instanciamos DAO (Singleton)

                            //instancio Vistas
                            MenuPrincipalView principalView = new MenuPrincipalView(mi);
                            ProductoAnadirView productoAnadirView = new ProductoAnadirView(mi);
                            ProductoListaView productoListaView = new ProductoListaView(mi);
                            ProductoActualizarView productoActualizarView = new ProductoActualizarView(mi);
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView(mi);

                            //instancio Vistas de Carrito
                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mi);
                            CarritoListarView carritoListarView = new CarritoListarView(mi);
                            CarritoModificarView carritoModificarView = new CarritoModificarView(mi);
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mi);


                            //instanciamos las vistas de Usuario
                            UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mi);
                            UsuarioListarView usuarioListarView = new UsuarioListarView(mi);
                            UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mi);
                            UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mi);

                            RegistrarView registrarView = new RegistrarView();



                            //instanciamos Controladores
                            ProductoController productoController = new ProductoController(productoDAO, productoAnadirView, productoListaView, carritoAnadirView, productoEliminarView, productoActualizarView, mi );
                            CarritoController carritoController = new CarritoController(carritoDAO, carritoAnadirView, productoDAO, carritoListarView, usuarioAuntenticado, carritoModificarView, carritoEliminarView, mi);
                            UsuarioController usuarioController = new UsuarioController(usuarioDAO, usuarioCrearView, usuarioListarView, usuarioEliminarView, usuarioModificarView, mi, registrarView);


                            principalView.mostrarMensaje("Bienvenido: " + usuarioAuntenticado.getUsername());
                            if (usuarioAuntenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }
                            principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoAnadirView.isVisible()) {
                                        productoAnadirView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoAnadirView);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoListaView.isVisible()) {
                                        productoListaView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoListaView);
                                    }
                                }
                            });

                            principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!carritoAnadirView.isVisible()) {
                                        carritoAnadirView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoAnadirView);
                                    }
                                }
                            });
                            principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoEliminarView.isVisible()) {
                                        productoEliminarView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoEliminarView);
                                    }
                                }
                            });
                            principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!productoActualizarView.isVisible()) {
                                        productoActualizarView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoActualizarView);
                                    }
                                }
                            });
                            principalView.getMenuItemBuscarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!carritoListarView.isVisible()) {
                                        carritoListarView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoListarView);
                                    }
                                }
                            });

                            principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    boolean confirmado = principalView.mostrarMensajePregunta("¿Desea Cerrar Sesión?");
                                    if (confirmado) {
                                        principalView.dispose();
                                        loginView.setVisible(true);
                                        loginView.limpiarCampos();
                                    }
                                }
                            });
                            principalView.getMenuItemCrearUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!usuarioCrearView.isVisible()) {
                                        usuarioCrearView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioCrearView);
                                    }
                                }
                            });
                            principalView.getMenuItemListarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!usuarioListarView.isVisible()) {
                                        usuarioListarView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioListarView);
                                    }
                                }
                            });
                            principalView.getMenuItemEliminarUsuario().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!usuarioEliminarView.isVisible()) {
                                        usuarioEliminarView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioEliminarView);
                                    }
                                }
                            });
                            principalView.getMenuItemModificarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!carritoModificarView.isVisible()) {
                                        carritoModificarView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoModificarView);
                                    }
                                }
                            });
                            principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!carritoEliminarView.isVisible()) {
                                        carritoEliminarView.setVisible(true);
                                        principalView.getjDesktopPane().add(carritoEliminarView);
                                    }
                                }
                            });
                            principalView.getMenuItemSalir().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    boolean confirmado = principalView.mostrarMensajePregunta("¿Desea Salir?");
                                    if(confirmado) {
                                        principalView.dispose();
                                        System.exit(0);
                                    }
                                }
                            });
                            principalView.getMenuItemActualizarUsuario().addActionListener( new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (!usuarioModificarView.isVisible()) {
                                        usuarioModificarView.setVisible(true);
                                        principalView.getjDesktopPane().add(usuarioModificarView);
                                    }
                                }
                            });
                            principalView.getMenuItemEspanol().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
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

                                }
                            });
                            principalView.getMenuItemIngles().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
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
                                }
                            });
                            principalView.getMenuItemFrances().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
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
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
