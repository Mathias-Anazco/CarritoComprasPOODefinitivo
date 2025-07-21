package ec.edu.ups.vista.CarritoView;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * Representa la interfaz gráfica de usuario (GUI) para crear un carrito de compras.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite buscar productos, añadirlos a una tabla, ver los totales y guardar el carrito.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CarritoAnadirView extends JInternalFrame {
    private JButton btnBuscar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox<String> cbxCantidad;
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JLabel lblCarritoAñadir;
    private DefaultTableModel modelo;
    private Carrito carrito;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de añadir al carrito.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public CarritoAnadirView(MensajeInternacionalizacionHandler mi) {
        super("Carrito de Compras", false, true, false, true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cargarDatos();

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    /**
     * Carga los datos iniciales en los componentes, como las opciones del JComboBox de cantidad.
     */
    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 0; i < 20; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario y al modelo de datos.
     */
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JButton getBtnAnadir() { return btnAnadir; }
    public JTable getTblProductos() { return tblProductos; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIva() { return txtIva; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JComboBox<String> getCbxCantidad() { return cbxCantidad; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }
    public JLabel getLblNombre() { return lblNombre; }
    public void setLblNombre(JLabel lblNombre) { this.lblNombre = lblNombre; }
    public JLabel getLblPrecio() { return lblPrecio; }
    public void setLblPrecio(JLabel lblPrecio) { this.lblPrecio = lblPrecio; }
    public JLabel getLblCantidad() { return lblCantidad; }
    public void setLblCantidad(JLabel lblCantidad) { this.lblCantidad = lblCantidad; }
    public JLabel getLblSubtotal() { return lblSubtotal; }
    public void setLblSubtotal(JLabel lblSubtotal) { this.lblSubtotal = lblSubtotal; }
    public JLabel getLblIVA() { return lblIVA; }
    public void setLblIVA(JLabel lblIVA) { this.lblIVA = lblIVA; }
    public JLabel getLblTotal() { return lblTotal; }
    public void setLblTotal(JLabel lblTotal) { this.lblTotal = lblTotal; }
    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
    public JLabel getLblCarritoAñadir() { return lblCarritoAñadir; }
    public void setLblCarritoAñadir(JLabel lblCarritoAñadir) { this.lblCarritoAñadir = lblCarritoAñadir; }


    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia todos los campos de entrada y la tabla, reiniciando la vista a su estado inicial.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        cbxCantidad.setSelectedIndex(0);
        modelo.setRowCount(0);
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");
    }

    /**
     * Actualiza todos los textos visibles en la ventana (etiquetas, botones, encabezados de tabla)
     * al idioma actualmente configurado.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        lblCarritoAñadir.setText(mi.get("carrito.añadir.titulo"));
        lblCodigo.setText(mi.get("carrito.añadir.codigo"));
        lblNombre.setText(mi.get("carrito.añadir.nombre"));
        lblPrecio.setText(mi.get("carrito.añadir.precio"));
        lblCantidad.setText(mi.get("carrito.añadir.cantidad"));
        lblSubtotal.setText(mi.get("carrito.añadir.subtotal"));
        lblIVA.setText(mi.get("carrito.añadir.iva"));
        lblTotal.setText(mi.get("carrito.añadir.total"));
        btnBuscar.setText(mi.get("carrito.añadir.boton.buscar"));
        btnAnadir.setText(mi.get("carrito.añadir.boton.anadir"));
        btnGuardar.setText(mi.get("carrito.añadir.boton.guardar"));
        btnLimpiar.setText(mi.get("carrito.añadir.boton.limpiar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("carrito.añadir.tabla.codigo"),
                mi.get("carrito.añadir.tabla.nombre"),
                mi.get("carrito.añadir.tabla.precio"),
                mi.get("carrito.añadir.tabla.cantidad"),
                mi.get("carrito.añadir.tabla.subtotal")
        });
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz
     * desde los recursos del proyecto.
     */
    private void iconos() {
        URL botonGuardar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonGuardar != null) {
            btnBuscar.setIcon(new ImageIcon(botonGuardar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonAnadir = LoginView.class.getClassLoader().getResource("imagenes/Añadir.svg.png");
        if (botonAnadir != null) {
            btnAnadir.setIcon(new ImageIcon(botonAnadir));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonLimpiar = LoginView.class.getClassLoader().getResource("imagenes/LimpiarTodo.svg.png");
        if (botonLimpiar != null) {
            btnLimpiar.setIcon(new ImageIcon(botonLimpiar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonGuardarCarrito = LoginView.class.getClassLoader().getResource("imagenes/Guardar.svg.png");
        if (botonGuardarCarrito != null) {
            btnGuardar.setIcon(new ImageIcon(botonGuardarCarrito));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}