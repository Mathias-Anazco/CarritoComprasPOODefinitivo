package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

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

        cambiarIdioma(); // Traduce etiquetas y columnas al idioma actual
    }

    public void cambiarIdioma() {
        setTitle(mi.get("producto.actualizar.titulo"));
        lblActualizar.setText(mi.get("producto.actualizar.encabezado"));
        lblCodigo.setText(mi.get("producto.actualizar.etiqueta.codigo"));
        lblNombre.setText(mi.get("producto.actualizar.etiqueta.nombre"));
        lblPrecio.setText(mi.get("producto.actualizar.etiqueta.precio"));
        buscarButton.setText(mi.get("producto.actualizar.boton.buscar"));
        actualizarButton.setText(mi.get("producto.actualizar.boton.actualizar"));

        // Traducción de los encabezados de la tabla
        String[] columnas = {
                mi.get("producto.actualizar.columna.codigo"),
                mi.get("producto.actualizar.columna.nombre"),
                mi.get("producto.actualizar.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
    }

    public JPanel getPanelActualizar() {
        return panelActualizar;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTable getTable1() {
        return table1;
    }

    public JButton getActualizarButton() {
        return actualizarButton;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public JLabel getLblActualizar() {
        return lblActualizar;
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
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
}
