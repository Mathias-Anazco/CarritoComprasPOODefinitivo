package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //Iniciar sesión
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                CarritoDAO carritoDAO = new CarritoDAOMemoria();

                //instancio Vistas de Usuario
                UsuarioCrearView usuarioCrearView = new UsuarioCrearView();
                UsuarioListarView usuarioListarView = new UsuarioListarView();
                UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView();

                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, usuarioCrearView, usuarioListarView, usuarioEliminarView);

                loginView.addWindowListener(new WindowAdapter( ) {
                    @Override
                    public void windowClosed(WindowEvent e) {

                        Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                        if (usuarioAuntenticado != null) {
                            //instanciamos DAO (Singleton)

                            //instancio Vistas
                            MenuPrincipalView principalView = new MenuPrincipalView();
                            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                            ProductoListaView productoListaView = new ProductoListaView();
                            ProductoActualizarView productoActualizarView = new ProductoActualizarView();
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView();

                            //instancio Vistas de Carrito
                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                            CarritoListarView carritoListarView = new CarritoListarView();


                            //instanciamos las vistas de Usuario
                            UsuarioCrearView usuarioCrearView = new UsuarioCrearView();
                            UsuarioListarView usuarioListarView = new UsuarioListarView();
                            UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView();



                            //instanciamos Controladores
                            ProductoController productoController = new ProductoController(productoDAO, productoAnadirView, productoListaView, carritoAnadirView, productoEliminarView, productoActualizarView );
                            CarritoController carritoController = new CarritoController(carritoDAO, carritoAnadirView, productoDAO, carritoListarView, usuarioAuntenticado);
                            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, usuarioCrearView, usuarioListarView, usuarioEliminarView);

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
                                    principalView.dispose();
                                    usuarioController.cerrarSesion();
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
                        }
                    }
                });
            }
        });
    }
}
