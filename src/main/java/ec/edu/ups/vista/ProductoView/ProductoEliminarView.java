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
 * Representa la interfaz gráfica (GUI) para eliminar un producto existente.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite buscar un producto por su código, visualizarlo para confirmación y luego eliminarlo.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ProductoEliminarView extends JInternalFrame {
    private JTable table1;
    private JPanel panelEliminar;
    private JTextField textField1;
    private JButton buscarButton;
    private JButton eliminarButton;
    private JLabel lblCodigo;
    private JLabel lblEliminar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de eliminación de productos.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelEliminar);
        setTitle(mi.get("producto.eliminar.titulo"));
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
        setTitle(mi.get("producto.eliminar.titulo"));

        lblCodigo.setText(mi.get("producto.eliminar.etiqueta.codigo"));
        lblEliminar.setText(mi.get("producto.eliminar.etiqueta.eliminar"));

        buscarButton.setText(mi.get("producto.eliminar.boton.buscar"));
        eliminarButton.setText(mi.get("producto.eliminar.boton.eliminar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("producto.eliminar.columna.codigo"),
                mi.get("producto.eliminar.columna.nombre"),
                mi.get("producto.eliminar.columna.precio")
        });
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JTable getTable1() { return table1; }
    public void setTable1(JTable table1) { this.table1 = table1; }
    public JPanel getPanelEliminar() { return panelEliminar; }
    public void setPanelEliminar(JPanel panelEliminar) { this.panelEliminar = panelEliminar; }
    public JTextField getTextField1() { return textField1; }
    public void setTextField1(JTextField textField1) { this.textField1 = textField1; }
    public JButton getBuscarButton() { return buscarButton; }
    public void setBuscarButton(JButton buscarButton) { this.buscarButton = buscarButton; }
    public JButton getEliminarButton() { return eliminarButton; }
    public void setEliminarButton(JButton eliminarButton) { this.eliminarButton = eliminarButton; }
    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }
    public JLabel getLblEliminar() { return lblEliminar; }
    public void setLblEliminar(JLabel lblEliminar) { this.lblEliminar = lblEliminar; }

    /**
     * Carga los datos de una lista de productos en la tabla de la vista.
     *
     * @param listaProductos La lista de productos a mostrar.
     */
    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);
        Locale locale = mi.getLocale();
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
        URL botonEliminar = LoginView.class.getClassLoader().getResource("imagenes/EliminarTodo.svg.png");
        if (botonEliminar != null) {
            eliminarButton.setIcon(new ImageIcon(botonEliminar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}