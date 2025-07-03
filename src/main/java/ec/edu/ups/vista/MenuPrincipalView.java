package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.net.URL;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuario;
    private JMenu menuIdioma;
    private JMenu menuSalir;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;

    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemBuscarCarrito;
    private JMenuItem menuItemModificarCarrito;
    private JMenuItem menuItemEliminarCarrito;


    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemListarUsuario;
    private JMenuItem menuItemActualizarUsuario;
    private JMenuItem menuItemEliminarUsuario;

    private JMenuItem menuItemEspanol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;


    private MiJdesktopPane jDesktopPane;
    private MensajeInternacionalizacionHandler mi;

    public MenuPrincipalView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;

        jDesktopPane = new MiJdesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");
        menuUsuario = new JMenu("Usuario");
        menuIdioma = new JMenu("Idioma");
        menuSalir = new JMenu("Salir");

        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");

        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemBuscarCarrito = new JMenuItem("Buscar Carrito");
        menuItemModificarCarrito = new JMenuItem("Actualizar Carrito");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");

        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemListarUsuario = new JMenuItem("Buscar Usuario");
        menuItemActualizarUsuario = new JMenuItem("Actualizar Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");

        menuItemEspanol = new JMenuItem("Español");
        menuItemIngles = new JMenuItem("Inglés");
        menuItemFrances = new JMenuItem("Francés");

        menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
        menuItemSalir = new JMenuItem("Salir");


        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemBuscarCarrito);
        menuCarrito.add(menuItemModificarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);

        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemListarUsuario);
        menuUsuario.add(menuItemActualizarUsuario);
        menuUsuario.add(menuItemEliminarUsuario);

        menuIdioma.add(menuItemEspanol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        menuSalir.add(menuItemCerrarSesion);
        menuSalir.add(menuItemSalir);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Carrito de Compras En Línea");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        cambiarIdioma();

    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) {
        this.menuItemCrearCarrito = menuItemCrearCarrito;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }



    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JMenuItem getMenuItemBuscarCarrito() {
        return menuItemBuscarCarrito;
    }

    public void setMenuItemBuscarCarrito(JMenuItem menuItemBuscarCarrito) {
        this.menuItemBuscarCarrito = menuItemBuscarCarrito;
    }

    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    public void setMenuUsuario(JMenu menuUsuario) {
        this.menuUsuario = menuUsuario;
    }

    public JMenu getMenuSalir() {
        return menuSalir;
    }

    public void setMenuSalir(JMenu menuSalir) {
        this.menuSalir = menuSalir;
    }

    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
    }

    public JMenuItem getMenuItemListarUsuario() {
        return menuItemListarUsuario;
    }

    public void setMenuItemListarUsuario(JMenuItem menuItemBuscarUsuario) {
        this.menuItemListarUsuario = menuItemBuscarUsuario;
    }

    public JMenuItem getMenuItemActualizarUsuario() {
        return menuItemActualizarUsuario;
    }

    public void setMenuItemActualizarUsuario(JMenuItem menuItemActualizarUsuario) {
        this.menuItemActualizarUsuario = menuItemActualizarUsuario;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public void setMenuItemCerrarSesion(JMenuItem menuItemCerrarSesion) {
        this.menuItemCerrarSesion = menuItemCerrarSesion;
    }

    public JMenuItem getMenuItemModificarCarrito() {
        return menuItemModificarCarrito;
    }

    public void setMenuItemModificarCarrito(JMenuItem menuItemModificarCarrito) {
        this.menuItemModificarCarrito = menuItemModificarCarrito;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public void setMenuItemSalir(JMenuItem menuItemSalir) {
        this.menuItemSalir = menuItemSalir;
    }

    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    public void setMenuIdioma(JMenu menuIdioma) {
        this.menuIdioma = menuIdioma;
    }

    public JMenuItem getMenuItemEspanol() {
        return menuItemEspanol;
    }

    public void setMenuItemEspanol(JMenuItem menuItemEspanol) {
        this.menuItemEspanol = menuItemEspanol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void deshabilitarMenusAdministrador() {
        getMenuItemCrearProducto().setEnabled(false);
        getMenuItemBuscarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemListarUsuario().setEnabled(false);
        getMenuItemActualizarUsuario().setEnabled(false);
        getMenuItemEliminarUsuario().setEnabled(false);
    }

    public void cambiarIdioma() {
        if (mi == null) return;

        // Menús principales
        menuProducto.setText(mi.get("menu.producto"));
        menuCarrito.setText(mi.get("menu.carrito"));
        menuUsuario.setText(mi.get("menu.usuario"));
        menuIdioma.setText(mi.get("menu.idioma"));
        menuSalir.setText(mi.get("menu.salir"));

        // Items de Producto
        menuItemCrearProducto.setText(mi.get("menu.producto.crear"));
        menuItemEliminarProducto.setText(mi.get("menu.producto.eliminar"));
        menuItemActualizarProducto.setText(mi.get("menu.producto.actualizar"));
        menuItemBuscarProducto.setText(mi.get("menu.producto.buscar"));

        // Items de Carrito
        menuItemCrearCarrito.setText(mi.get("menu.carrito.crear"));
        menuItemBuscarCarrito.setText(mi.get("menu.carrito.buscar"));
        menuItemModificarCarrito.setText(mi.get("menu.carrito.actualizar"));
        menuItemEliminarCarrito.setText(mi.get("menu.carrito.eliminar"));

        // Items de Usuario
        menuItemCrearUsuario.setText(mi.get("menu.usuario.crear"));
        menuItemListarUsuario.setText(mi.get("menu.usuario.buscar"));
        menuItemActualizarUsuario.setText(mi.get("menu.usuario.actualizar"));
        menuItemEliminarUsuario.setText(mi.get("menu.usuario.eliminar"));

        // Idiomas
        menuItemEspanol.setText(mi.get("Español"));
        menuItemIngles.setText(mi.get("Inglés"));
        menuItemFrances.setText(mi.get("Frances"));

        // Salir
        menuItemCerrarSesion.setText(mi.get("menu.salir.cerrar"));
        menuItemSalir.setText(mi.get("menu.salir.salir"));

        // Título
        setTitle(mi.get("titulo.ventana"));

        URL EcuadorURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Ecuador.svg.png");
        if (EcuadorURL != null) {
            ImageIcon iconoMenuItemEspañol = new ImageIcon(EcuadorURL);
            menuItemEspanol.setIcon(iconoMenuItemEspañol);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL USAURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/EstadosUnidos.svg.png");
        if (USAURL != null) {
            ImageIcon iconoMenuItemIngles = new ImageIcon(USAURL);
            menuItemIngles.setIcon(iconoMenuItemIngles);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL FranceURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Francia.svg.png");
        if (FranceURL != null) {
            ImageIcon iconoMenuItemFrances = new ImageIcon(FranceURL);
            menuItemFrances.setIcon(iconoMenuItemFrances);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL iconoMenuCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Carrito.svg.png");
        if (iconoMenuCarritoURL != null) {
            ImageIcon iconoMenuCarrito = new ImageIcon(iconoMenuCarritoURL);
            menuCarrito.setIcon(iconoMenuCarrito);
        } else {
            System.err.println("Error: No se ha cargado el icono de Carrito");
        }
        URL iconoMenuProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Producto.svg.png");
        if (iconoMenuProductoURL != null) {
            ImageIcon iconoMenuProducto = new ImageIcon(iconoMenuProductoURL);
            menuProducto.setIcon(iconoMenuProducto);
        } else {
            System.err.println("Error: No se ha cargado el icono de Producto");
        }
        URL iconoMenuUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Usuario.svg.png");
        if (iconoMenuUsuarioURL != null) {
            ImageIcon iconoMenuUsuario = new ImageIcon(iconoMenuUsuarioURL);
            menuUsuario.setIcon(iconoMenuUsuario);
        } else {
            System.err.println("Error: No se ha cargado el icono de Usuario");
        }
        URL iconoMenuSalirURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Salir.svg.png");
        if (iconoMenuSalirURL != null) {
            ImageIcon iconoMenuSalir = new ImageIcon(iconoMenuSalirURL);
            menuSalir.setIcon(iconoMenuSalir);
        } else {
            System.err.println("Error: No se ha cargado el icono de Salir");
        }
        URL iconoMenuIdiomaURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Idioma.svg.png");
        if (iconoMenuIdiomaURL != null) {
            ImageIcon iconoMenuIdioma = new ImageIcon(iconoMenuIdiomaURL);
            menuIdioma.setIcon(iconoMenuIdioma);
        } else {
            System.err.println("Error: No se ha cargado el icono de Idioma");
        }
        URL iconoMenuCrearCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Crear.svg.png");
        if (iconoMenuCrearCarritoURL != null) {
            ImageIcon iconoMenuCrearCarrito = new ImageIcon(iconoMenuCrearCarritoURL);
            menuItemCrearCarrito.setIcon(iconoMenuCrearCarrito);
        } else {
            System.err.println("Error: No se ha cargado el icono de Crear Carrito");
        }
        URL iconoMenuCrearProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Crear.svg.png");
        if (iconoMenuCrearProductoURL != null) {
            ImageIcon iconoMenuCrearProducto = new ImageIcon(iconoMenuCrearProductoURL);
            menuItemCrearProducto.setIcon(iconoMenuCrearProducto);
        } else {
            System.err.println("Error: No se ha cargado el icono de Crear Producto");
        }
        URL iconoMenuCrearUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Crear.svg.png");
        if (iconoMenuCrearUsuarioURL != null) {
            ImageIcon iconoMenuCrearUsuario = new ImageIcon(iconoMenuCrearUsuarioURL);
            menuItemCrearUsuario.setIcon(iconoMenuCrearUsuario);
        } else {
            System.err.println("Error: No se ha cargado el icono de Crear Usuario");
        }
        URL iconoMenuBuscarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Buscar.svg.png");
        if (iconoMenuBuscarCarritoURL != null) {
            ImageIcon iconoMenuBuscarCarrito = new ImageIcon(iconoMenuBuscarCarritoURL);
            menuItemBuscarCarrito.setIcon(iconoMenuBuscarCarrito);
        } else {
            System.err.println("Error: No se ha cargado el icono de Buscar Carrito");
        }
        URL iconoMenuBuscarProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Buscar.svg.png");
        if (iconoMenuBuscarProductoURL != null) {
            ImageIcon iconoMenuBuscarProducto = new ImageIcon(iconoMenuBuscarProductoURL);
            menuItemBuscarProducto.setIcon(iconoMenuBuscarProducto);
        } else {
            System.err.println("Error: No se ha cargado el icono de Buscar Producto");
        }
        URL iconoMenuBuscarUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Buscar.svg.png");
        if (iconoMenuBuscarUsuarioURL != null) {
            ImageIcon iconoMenuBuscarUsuario = new ImageIcon(iconoMenuBuscarUsuarioURL);
            menuItemListarUsuario.setIcon(iconoMenuBuscarUsuario);
        } else {
            System.err.println("Error: No se ha cargado el icono de Buscar Usuario");
        }
        URL iconoMenuEliminarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Eliminar.svg.png");
        if (iconoMenuEliminarCarritoURL != null) {
            ImageIcon iconoMenuEliminarCarrito = new ImageIcon(iconoMenuEliminarCarritoURL);
            menuItemEliminarCarrito.setIcon(iconoMenuEliminarCarrito);
        } else {
            System.err.println("Error: No se ha cargado el icono de Eliminar Carrito");
        }
        URL iconoMenuEliminarProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Eliminar.svg.png");
        if (iconoMenuEliminarProductoURL != null) {
            ImageIcon iconoMenuEliminarProducto = new ImageIcon(iconoMenuEliminarProductoURL);
            menuItemEliminarProducto.setIcon(iconoMenuEliminarProducto);
        } else {
            System.err.println("Error: No se ha cargado el icono de Eliminar Producto");
        }
        URL iconoMenuEliminarUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Eliminar.svg.png");
        if (iconoMenuEliminarUsuarioURL != null) {
            ImageIcon iconoMenuEliminarUsuario = new ImageIcon(iconoMenuEliminarUsuarioURL);
            menuItemEliminarUsuario.setIcon(iconoMenuEliminarUsuario);
        } else {
            System.err.println("Error: No se ha cargado el icono de Eliminar Usuario");
        }
        URL iconoMenuActualizarCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (iconoMenuActualizarCarritoURL != null) {
            ImageIcon iconoMenuActualizarCarrito = new ImageIcon(iconoMenuActualizarCarritoURL);
            menuItemModificarCarrito.setIcon(iconoMenuActualizarCarrito);
        } else {
            System.err.println("Error: No se ha cargado el icono de Actualizar Carrito");
        }
        URL iconoMenuActualizarProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (iconoMenuActualizarProductoURL != null) {
            ImageIcon iconoMenuActualizarProducto = new ImageIcon(iconoMenuActualizarProductoURL);
            menuItemActualizarProducto.setIcon(iconoMenuActualizarProducto);
        } else {
            System.err.println("Error: No se ha cargado el icono de Actualizar Producto");
        }
        URL iconoMenuActualizarUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (iconoMenuActualizarUsuarioURL != null) {
            ImageIcon iconoMenuActualizarUsuario = new ImageIcon(iconoMenuActualizarUsuarioURL);
            menuItemActualizarUsuario.setIcon(iconoMenuActualizarUsuario);
        } else {
            System.err.println("Error: No se ha cargado el icono de Actualizar Usuario");
        }
        URL iconoMenuCerrarSesionURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Salir.svg.png");
        if (iconoMenuCerrarSesionURL != null) {
            ImageIcon iconoMenuCerrarSesion = new ImageIcon(iconoMenuCerrarSesionURL);
            menuItemCerrarSesion.setIcon(iconoMenuCerrarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Cerrar Sesión");
        }
        URL iconoMenuItemURl = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Exit.svg.png");
        if (iconoMenuItemURl != null) {
            ImageIcon iconoMenuSalir = new ImageIcon(iconoMenuItemURl);
            menuItemSalir.setIcon(iconoMenuSalir);
        } else {
            System.err.println("Error: No se ha cargado el icono de Salir");
        }
    }

}
