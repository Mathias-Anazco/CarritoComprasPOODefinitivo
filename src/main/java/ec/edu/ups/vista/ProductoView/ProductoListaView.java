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
 * Representa la interfaz gráfica (GUI) para listar y buscar productos.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite al usuario ver todos los productos o buscar productos específicos por nombre.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ProductoListaView extends JInternalFrame {

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JLabel lblLista;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de listado de productos.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public ProductoListaView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle(mi.get("producto.lista.titulo"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("producto.lista.titulo"));

        lblLista.setText(mi.get("producto.lista.etiqueta.lista"));
        lblNombre.setText(mi.get("producto.lista.etiqueta.nombre"));

        btnBuscar.setText(mi.get("producto.lista.boton.buscar"));
        btnListar.setText(mi.get("producto.lista.boton.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("producto.lista.columna.codigo"),
                mi.get("producto.lista.columna.nombre"),
                mi.get("producto.lista.columna.precio")
        });
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JTextField getTxtBuscar() { return txtBuscar; }
    public void setTxtBuscar(JTextField txtBuscar) { this.txtBuscar = txtBuscar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }
    public JTable getTblProductos() { return tblProductos; }
    public void setTblProductos(JTable tblProductos) { this.tblProductos = tblProductos; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JButton getBtnListar() { return btnListar; }
    public void setBtnListar(JButton btnListar) { this.btnListar = btnListar; }
    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
    public JLabel getLblLista() { return lblLista; }
    public void setLblLista(JLabel lblLista) { this.lblLista = lblLista; }
    public JLabel getLblNombre() { return lblNombre; }
    public void setLblNombre(JLabel lblNombre) { this.lblNombre = lblNombre; }

    /**
     * Carga los datos de una lista de productos en la tabla de la vista.
     *
     * @param listaProductos La lista de productos a mostrar.
     */
    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);

        for (Producto producto : listaProductos) {
            Locale locale = mi.getLocale();
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
    public void iconos(){
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            btnBuscar.setIcon(new ImageIcon(botonBuscar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonListar = LoginView.class.getClassLoader().getResource("imagenes/ListarTodo.svg.png");
        if (botonListar != null) {
            btnListar.setIcon(new ImageIcon(botonListar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}