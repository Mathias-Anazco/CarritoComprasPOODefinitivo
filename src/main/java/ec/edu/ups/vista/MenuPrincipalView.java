package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.net.URL;

/**
 * Representa la ventana principal de la aplicación.
 * Esta clase actúa como el contenedor principal (MDI - Multiple Document Interface)
 * que alberga todas las demás vistas internas (JInternalFrame). Proporciona una
 * barra de menús para navegar a las diferentes funcionalidades del sistema.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
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

    /**
     * Constructor para la ventana del menú principal.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
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

    /**
     * Métodos de acceso a los componentes del menú para que los controladores
     * puedan añadir listeners y gestionar eventos.
     */
    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }
    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) { this.menuItemCrearProducto = menuItemCrearProducto; }
    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) { this.menuItemEliminarProducto = menuItemEliminarProducto; }
    public JMenuItem getMenuItemActualizarProducto() { return menuItemActualizarProducto; }
    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) { this.menuItemActualizarProducto = menuItemActualizarProducto; }
    public JMenuItem getMenuItemBuscarProducto() { return menuItemBuscarProducto; }
    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) { this.menuItemBuscarProducto = menuItemBuscarProducto; }
    public JMenu getMenuProducto() { return menuProducto; }
    public void setMenuProducto(JMenu menuProducto) { this.menuProducto = menuProducto; }
    public JMenu getMenuCarrito() { return menuCarrito; }
    public void setMenuCarrito(JMenu menuCarrito) { this.menuCarrito = menuCarrito; }
    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) { this.menuItemCrearCarrito = menuItemCrearCarrito; }
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public JMenuItem getMenuItemBuscarCarrito() { return menuItemBuscarCarrito; }
    public void setMenuItemBuscarCarrito(JMenuItem menuItemBuscarCarrito) { this.menuItemBuscarCarrito = menuItemBuscarCarrito; }
    public JMenu getMenuUsuario() { return menuUsuario; }
    public void setMenuUsuario(JMenu menuUsuario) { this.menuUsuario = menuUsuario; }
    public JMenu getMenuSalir() { return menuSalir; }
    public void setMenuSalir(JMenu menuSalir) { this.menuSalir = menuSalir; }
    public JMenuItem getMenuItemCrearUsuario() { return menuItemCrearUsuario; }
    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) { this.menuItemCrearUsuario = menuItemCrearUsuario; }
    public JMenuItem getMenuItemListarUsuario() { return menuItemListarUsuario; }
    public void setMenuItemListarUsuario(JMenuItem menuItemBuscarUsuario) { this.menuItemListarUsuario = menuItemBuscarUsuario; }
    public JMenuItem getMenuItemActualizarUsuario() { return menuItemActualizarUsuario; }
    public void setMenuItemActualizarUsuario(JMenuItem menuItemActualizarUsuario) { this.menuItemActualizarUsuario = menuItemActualizarUsuario; }
    public JMenuItem getMenuItemEliminarUsuario() { return menuItemEliminarUsuario; }
    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) { this.menuItemEliminarUsuario = menuItemEliminarUsuario; }
    public JMenuItem getMenuItemCerrarSesion() { return menuItemCerrarSesion; }
    public void setMenuItemCerrarSesion(JMenuItem menuItemCerrarSesion) { this.menuItemCerrarSesion = menuItemCerrarSesion; }
    public JMenuItem getMenuItemModificarCarrito() { return menuItemModificarCarrito; }
    public void setMenuItemModificarCarrito(JMenuItem menuItemModificarCarrito) { this.menuItemModificarCarrito = menuItemModificarCarrito; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) { this.menuItemEliminarCarrito = menuItemEliminarCarrito; }
    public JMenuItem getMenuItemSalir() { return menuItemSalir; }
    public void setMenuItemSalir(JMenuItem menuItemSalir) { this.menuItemSalir = menuItemSalir; }
    public JMenu getMenuIdioma() { return menuIdioma; }
    public void setMenuIdioma(JMenu menuIdioma) { this.menuIdioma = menuIdioma; }
    public JMenuItem getMenuItemEspanol() { return menuItemEspanol; }
    public void setMenuItemEspanol(JMenuItem menuItemEspanol) { this.menuItemEspanol = menuItemEspanol; }
    public JMenuItem getMenuItemIngles() { return menuItemIngles; }
    public void setMenuItemIngles(JMenuItem menuItemIngles) { this.menuItemIngles = menuItemIngles; }
    public JMenuItem getMenuItemFrances() { return menuItemFrances; }
    public void setMenuItemFrances(JMenuItem menuItemFrances) { this.menuItemFrances = menuItemFrances; }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Muestra un diálogo de confirmación (Sí/No) al usuario.
     *
     * @param mensaje El texto de la pregunta a mostrar.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Deshabilita los menús de gestión de productos y usuarios, restringiendo
     * el acceso a funcionalidades exclusivas para administradores.
     */
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

    /**
     * Actualiza todos los textos e iconos de la barra de menús al idioma actual.
     */
    public void cambiarIdioma() {
        if (mi == null) return;

        menuProducto.setText(mi.get("menu.producto"));
        menuCarrito.setText(mi.get("menu.carrito"));
        menuUsuario.setText(mi.get("menu.usuario"));
        menuIdioma.setText(mi.get("menu.idioma"));
        menuSalir.setText(mi.get("menu.salir"));

        menuItemCrearProducto.setText(mi.get("menu.producto.crear"));
        menuItemEliminarProducto.setText(mi.get("menu.producto.eliminar"));
        menuItemActualizarProducto.setText(mi.get("menu.producto.actualizar"));
        menuItemBuscarProducto.setText(mi.get("menu.producto.buscar"));

        menuItemCrearCarrito.setText(mi.get("menu.carrito.crear"));
        menuItemBuscarCarrito.setText(mi.get("menu.carrito.buscar"));
        menuItemModificarCarrito.setText(mi.get("menu.carrito.actualizar"));
        menuItemEliminarCarrito.setText(mi.get("menu.carrito.eliminar"));

        menuItemCrearUsuario.setText(mi.get("menu.usuario.crear"));
        menuItemListarUsuario.setText(mi.get("menu.usuario.buscar"));
        menuItemActualizarUsuario.setText(mi.get("menu.usuario.actualizar"));
        menuItemEliminarUsuario.setText(mi.get("menu.usuario.eliminar"));

        menuItemEspanol.setText(mi.get("Español"));
        menuItemIngles.setText(mi.get("Inglés"));
        menuItemFrances.setText(mi.get("Frances"));

        menuItemCerrarSesion.setText(mi.get("menu.salir.cerrar"));
        menuItemSalir.setText(mi.get("menu.salir.salir"));

        setTitle(mi.get("titulo.ventana"));

        URL EcuadorURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Ecuador.svg.png");
        if (EcuadorURL != null) menuItemEspanol.setIcon(new ImageIcon(EcuadorURL));

        URL USAURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/EstadosUnidos.svg.png");
        if (USAURL != null) menuItemIngles.setIcon(new ImageIcon(USAURL));

        URL FranceURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Francia.svg.png");
        if (FranceURL != null) menuItemFrances.setIcon(new ImageIcon(FranceURL));

        URL iconoMenuCarritoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Carrito.svg.png");
        if (iconoMenuCarritoURL != null) menuCarrito.setIcon(new ImageIcon(iconoMenuCarritoURL));

        URL iconoMenuProductoURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Producto.svg.png");
        if (iconoMenuProductoURL != null) menuProducto.setIcon(new ImageIcon(iconoMenuProductoURL));

        URL iconoMenuUsuarioURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Usuario.svg.png");
        if (iconoMenuUsuarioURL != null) menuUsuario.setIcon(new ImageIcon(iconoMenuUsuarioURL));

        URL iconoMenuSalirURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Salir.svg.png");
        if (iconoMenuSalirURL != null) menuSalir.setIcon(new ImageIcon(iconoMenuSalirURL));

        URL iconoMenuIdiomaURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Idioma.svg.png");
        if (iconoMenuIdiomaURL != null) menuIdioma.setIcon(new ImageIcon(iconoMenuIdiomaURL));

        URL iconoMenuCrearURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Crear.svg.png");
        if (iconoMenuCrearURL != null) {
            menuItemCrearCarrito.setIcon(new ImageIcon(iconoMenuCrearURL));
            menuItemCrearProducto.setIcon(new ImageIcon(iconoMenuCrearURL));
            menuItemCrearUsuario.setIcon(new ImageIcon(iconoMenuCrearURL));
        }

        URL iconoMenuBuscarURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Buscar.svg.png");
        if (iconoMenuBuscarURL != null) {
            menuItemBuscarCarrito.setIcon(new ImageIcon(iconoMenuBuscarURL));
            menuItemBuscarProducto.setIcon(new ImageIcon(iconoMenuBuscarURL));
            menuItemListarUsuario.setIcon(new ImageIcon(iconoMenuBuscarURL));
        }

        URL iconoMenuEliminarURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Eliminar.svg.png");
        if (iconoMenuEliminarURL != null) {
            menuItemEliminarCarrito.setIcon(new ImageIcon(iconoMenuEliminarURL));
            menuItemEliminarProducto.setIcon(new ImageIcon(iconoMenuEliminarURL));
            menuItemEliminarUsuario.setIcon(new ImageIcon(iconoMenuEliminarURL));
        }

        URL iconoMenuActualizarURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (iconoMenuActualizarURL != null) {
            menuItemModificarCarrito.setIcon(new ImageIcon(iconoMenuActualizarURL));
            menuItemActualizarProducto.setIcon(new ImageIcon(iconoMenuActualizarURL));
            menuItemActualizarUsuario.setIcon(new ImageIcon(iconoMenuActualizarURL));
        }

        URL iconoMenuCerrarSesionURL = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Salir.svg.png");
        if (iconoMenuCerrarSesionURL != null) menuItemCerrarSesion.setIcon(new ImageIcon(iconoMenuCerrarSesionURL));

        URL iconoMenuItemURl = MenuPrincipalView.class.getClassLoader().getResource("imagenes/Exit.svg.png");
        if (iconoMenuItemURl != null) menuItemSalir.setIcon(new ImageIcon(iconoMenuItemURl));
    }
}