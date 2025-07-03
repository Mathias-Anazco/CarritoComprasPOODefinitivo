package ec.edu.ups.vista.CarritoView;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;

public class CarritoDetalleView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JLabel lblDetalle;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public CarritoDetalleView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle(mi.get("carrito.detalle.titulo"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cambiarIdioma();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    public JLabel getLblIVA() {
        return lblIVA;
    }

    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JLabel getLblDetalle() {
        return lblDetalle;
    }

    public void setLblDetalle(JLabel lblDetalle) {
        this.lblDetalle = lblDetalle;
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cargarDatos(Carrito carrito) {
        modelo.setRowCount(0);

        for (ItemCarrito itemCarrito : carrito.obtenerItems()) {
            Locale locale = mi.getLocale();
            Producto producto = itemCarrito.getProducto();
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(),locale),
                    itemCarrito.getCantidad()
            };
            modelo.addRow(fila);
        }
    }

    public void cambiarIdioma() {
        setTitle(mi.get("carrito.detalle.titulo"));
        lblDetalle.setText(mi.get("carrito.detalle.etiqueta"));
        lblSubtotal.setText(mi.get("carrito.detalle.subtotal"));
        lblIVA.setText(mi.get("carrito.detalle.iva"));
        lblTotal.setText(mi.get("carrito.detalle.total"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("carrito.detalle.columna.codigo"),
                mi.get("carrito.detalle.columna.nombre"),
                mi.get("carrito.detalle.columna.precio"),
                mi.get("carrito.detalle.columna.cantidad")
        });
    }
}
