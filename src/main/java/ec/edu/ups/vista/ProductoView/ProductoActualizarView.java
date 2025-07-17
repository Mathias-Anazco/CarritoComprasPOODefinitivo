package ec.edu.ups.vista.ProductoView;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Representa la interfaz gráfica (GUI) para actualizar la información de un producto existente.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite buscar un producto por su código, visualizar sus datos y modificarlos.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ProductoActualizarView extends JInternalFrame {
    private JPanel panelActualizar;
    private JButton buscarButton;
    private JTextField textField1;
    private JTable table1;
    private JButton actualizarButton;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblActualizar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de actualización de productos.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public ProductoActualizarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelActualizar);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        table1.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("producto.actualizar.titulo"));
        lblActualizar.setText(mi.get("producto.actualizar.encabezado"));
        lblCodigo.setText(mi.get("producto.actualizar.etiqueta.codigo"));
        lblNombre.setText(mi.get("producto.actualizar.etiqueta.nombre"));
        lblPrecio.setText(mi.get("producto.actualizar.etiqueta.precio"));
        buscarButton.setText(mi.get("producto.actualizar.boton.buscar"));
        actualizarButton.setText(mi.get("producto.actualizar.boton.actualizar"));

        String[] columnas = {
                mi.get("producto.actualizar.columna.codigo"),
                mi.get("producto.actualizar.columna.nombre"),
                mi.get("producto.actualizar.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JPanel getPanelActualizar() { return panelActualizar; }
    public JButton getBuscarButton() { return buscarButton; }
    /**
     * Obtiene el campo de texto para buscar un producto por su código.
     * @return El JTextField para el código de búsqueda.
     */
    public JTextField getTextField1() { return textField1; }
    public JTable getTable1() { return table1; }
    public JButton getActualizarButton() { return actualizarButton; }
    /**
     * Obtiene el campo de texto para editar el nombre del producto.
     * @return El JTextField para el nombre del producto.
     */
    public JTextField getTextField2() { return textField2; }
    /**
     * Obtiene el campo de texto para editar el precio del producto.
     * @return El JTextField para el precio del producto.
     */
    public JTextField getTextField3() { return textField3; }
    public DefaultTableModel getModelo() { return modelo; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public JLabel getLblNombre() { return lblNombre; }
    public JLabel getLblPrecio() { return lblPrecio; }
    public JLabel getLblActualizar() { return lblActualizar; }

    /**
     * Carga los datos de una lista de productos en la tabla de la vista.
     *
     * @param listaProductos La lista de productos a mostrar.
     */
    public void cargarDatos(List<Producto> listaProductos) {
        Locale locale = mi.getLocale();
        modelo.setNumRows(0);
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia el campo de búsqueda y la tabla de resultados.
     */
    public void limpiarCampos() {
        textField1.setText("");
        modelo.setNumRows(0);
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
    public void iconos() {
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            buscarButton.setIcon(new ImageIcon(botonBuscar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonActualizar = LoginView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (botonActualizar != null) {
            actualizarButton.setIcon(new ImageIcon(botonActualizar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}