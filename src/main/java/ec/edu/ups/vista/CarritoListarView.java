package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtCarrito;
    private JButton btnMostrarDetalle;
    private JButton btnMostrar;
    private JButton btnListar;
    private DefaultTableModel modelo;

    public CarritoListarView(){
        super("Listar los Carritos de Compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        modelo = new DefaultTableModel(new Object[]{"Code", "Fecha", "SubTotal", "Iva", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductos.setModel(modelo);

    }
    public void cargarDatos(List<Carrito> carritos) {
        modelo.setRowCount(0);
        for (Carrito carrito : carritos) {
            modelo.addRow(new Object[]{
                    carrito.getCodigo(),
                    carrito.getFechaFormateada(),
                    carrito.calcularTotal(),
                    carrito.calcularIVA(),
                    carrito.calcularTotalConIVA()
            });
        }
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    public JButton getBtnMostrarDetalle() {
        return btnMostrarDetalle;
    }

    public void setBtnMostrarDetalle(JButton btnMostrarDetalle) {
        this.btnMostrarDetalle = btnMostrarDetalle;
    }

    public JTextField getTxtCarrito() {
        return txtCarrito;
    }

    public void setTxtCarrito(JTextField txtCarrito) {
        this.txtCarrito = txtCarrito;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void limpiarCampos() {
        txtCarrito.setText("");
        modelo.setNumRows(0);
    }
}
