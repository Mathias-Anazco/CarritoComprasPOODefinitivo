package ec.edu.ups.vista.CarritoView;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.Locale;

public class CarritoEliminarView extends JInternalFrame {
    private JTable table1;
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblEliminar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public CarritoEliminarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle(mi.get("carrito.eliminar.titulo"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {
                mi.get("carrito.eliminar.columna.codigo"),
                mi.get("carrito.eliminar.columna.nombre"),
                mi.get("carrito.eliminar.columna.precio"),
                mi.get("carrito.eliminar.columna.cantidad"),
                mi.get("carrito.eliminar.columna.subtotal"),
                mi.get("carrito.eliminar.columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        table1.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    public void cambiarIdioma() {
        setTitle(mi.get("carrito.eliminar.titulo"));
        lblEliminar.setText(mi.get("carrito.eliminar.etiqueta"));
        lblCodigo.setText(mi.get("carrito.eliminar.etiqueta.codigo"));
        btnBuscar.setText(mi.get("carrito.eliminar.boton.buscar"));
        btnEliminar.setText(mi.get("carrito.eliminar.boton.eliminar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("carrito.eliminar.columna.codigo"),
                mi.get("carrito.eliminar.columna.nombre"),
                mi.get("carrito.eliminar.columna.precio"),
                mi.get("carrito.eliminar.columna.cantidad"),
                mi.get("carrito.eliminar.columna.subtotal"),
                mi.get("carrito.eliminar.columna.total")
        });
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }
    public JLabel getLblEliminar() {
        return lblEliminar;
    }
    public void setLblEliminar(JLabel lblEliminar) {
        this.lblEliminar = lblEliminar;
    }

    public void cargarDatos(Carrito carrito) {
        modelo.setRowCount(0);

        for (ItemCarrito itemCarrito : carrito.obtenerItems()) {
            Locale locale = mi.getLocale();
            Producto producto = itemCarrito.getProducto();
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale),
                    FormateadorUtils.formatearMoneda(itemCarrito.getCantidad(),locale),
                    FormateadorUtils.formatearMoneda(itemCarrito.getSubtotal(),locale),
                    FormateadorUtils.formatearMoneda(itemCarrito.getTotal(), locale)
            };
            modelo.addRow(fila);
        }
    }

    public void limpiarCampos() {
        modelo.setRowCount(0);
        txtCodigo.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void iconos() {
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            ImageIcon icono = new ImageIcon(botonBuscar);
            btnBuscar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonEliminarCarrito = LoginView.class.getClassLoader().getResource("imagenes/EliminarTodo.svg.png");
        if (botonEliminarCarrito != null) {
            ImageIcon icono = new ImageIcon(botonEliminarCarrito);
            btnEliminar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
