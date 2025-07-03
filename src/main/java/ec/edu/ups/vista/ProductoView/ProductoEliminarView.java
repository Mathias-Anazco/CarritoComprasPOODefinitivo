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
        Object[] columnas = {
                mi.get("producto.eliminar.columna.codigo"),
                mi.get("producto.eliminar.columna.nombre"),
                mi.get("producto.eliminar.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
        table1.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    public void cambiarIdioma() {
        setTitle(mi.get("producto.eliminar.titulo"));

        lblCodigo.setText(mi.get("producto.eliminar.etiqueta.codigo"));
        lblEliminar.setText(mi.get("producto.eliminar.etiqueta.eliminar"));

        buscarButton.setText(mi.get("producto.eliminar.boton.buscar"));
        eliminarButton.setText(mi.get("producto.eliminar.boton.eliminar"));

        // Actualizar encabezados de tabla
        modelo.setColumnIdentifiers(new Object[]{
                mi.get("producto.eliminar.columna.codigo"),
                mi.get("producto.eliminar.columna.nombre"),
                mi.get("producto.eliminar.columna.precio")
        });
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JPanel getPanelEliminar() {
        return panelEliminar;
    }

    public void setPanelEliminar(JPanel panelEliminar) {
        this.panelEliminar = panelEliminar;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public void setBuscarButton(JButton buscarButton) {
        this.buscarButton = buscarButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public void setEliminarButton(JButton eliminarButton) {
        this.eliminarButton = eliminarButton;
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        textField1.setText("");
        modelo.setNumRows(0);
    }
    public void iconos() {
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            ImageIcon icono = new ImageIcon(botonBuscar);
            buscarButton.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonEliminar = LoginView.class.getClassLoader().getResource("imagenes/EliminarTodo.svg.png");
        if (botonEliminar != null) {
            ImageIcon icono = new ImageIcon(botonEliminar);
            eliminarButton.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
