package ec.edu.ups.vista.ProductoView;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

/**
 * Representa la interfaz gráfica (GUI) para añadir un nuevo producto al sistema.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Proporciona campos para ingresar el código, nombre y precio de un nuevo producto.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblNuevoP;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de añadir producto.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        cambiarIdioma();
        iconos();

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("producto.anadir.titulo"));
        lblNuevoP.setText(mi.get("producto.anadir.encabezado"));
        lblCodigo.setText(mi.get("producto.anadir.etiqueta.codigo"));
        lblNombre.setText(mi.get("producto.anadir.etiqueta.nombre"));
        lblPrecio.setText(mi.get("producto.anadir.etiqueta.precio"));
        btnAceptar.setText(mi.get("producto.anadir.boton.aceptar"));
        btnLimpiar.setText(mi.get("producto.anadir.boton.limpiar"));
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public JLabel getLblNombre() { return lblNombre; }
    public JLabel getLblPrecio() { return lblPrecio; }
    public JLabel getLblNuevoP() { return lblNuevoP; }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia todos los campos de texto del formulario.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Imprime en la consola una lista de productos.
     * Este método es principalmente para propósitos de depuración.
     *
     * @param productos La lista de productos a mostrar en la consola.
     */
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
    public void iconos(){
        URL botonLimpiar = LoginView.class.getClassLoader().getResource("imagenes/LimpiarTodo.svg.png");
        if (botonLimpiar != null) {
            btnLimpiar.setIcon(new ImageIcon(botonLimpiar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonAceptar = LoginView.class.getClassLoader().getResource("imagenes/Añadir.svg.png");
        if (botonAceptar != null) {
            btnAceptar.setIcon(new ImageIcon(botonAceptar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}