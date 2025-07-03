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

public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCarrito;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnActualizar;
    private JComboBox cbxCantidad;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JPanel lblModificar;
    private JLabel lbl;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public CarritoModificarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle(mi.get("carrito.modificar.titulo"));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        cargarDatosCantidad();

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    public void cambiarIdioma() {
        setTitle(mi.get("carrito.modificar.titulo"));
        lbl.setText(mi.get("carrito.modificar.etiqueta"));
        lblCodigo.setText(mi.get("carrito.modificar.codigo"));
        lblCantidad.setText(mi.get("carrito.modificar.cantidad"));
        lblSubtotal.setText(mi.get("carrito.modificar.subtotal"));
        lblIVA.setText(mi.get("carrito.modificar.iva"));
        lblTotal.setText(mi.get("carrito.modificar.total"));
        btnBuscar.setText(mi.get("boton.buscar"));
        btnActualizar.setText(mi.get("boton.actualizar"));
        btnLimpiar.setText(mi.get("boton.limpiar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("carrito.columna.codigo"),
                mi.get("carrito.columna.nombre"),
                mi.get("carrito.columna.precio"),
                mi.get("carrito.columna.cantidad")
        });
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }

    public JTextField getTxtCarrito() { return txtCarrito; }
    public void setTxtCarrito(JTextField txtCarrito) { this.txtCarrito = txtCarrito; }

    public JButton getBtnBuscar() { return btnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }

    public JTable getTblProductos() { return tblProductos; }
    public void setTblProductos(JTable tblProductos) { this.tblProductos = tblProductos; }

    public JButton getBtnActualizar() { return btnActualizar; }
    public void setBtnActualizar(JButton btnActualizar) { this.btnActualizar = btnActualizar; }

    public JComboBox getCbxCantidad() { return cbxCantidad; }
    public void setCbxCantidad(JComboBox cbxCantidad) { this.cbxCantidad = cbxCantidad; }

    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }

    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public void setTxtSubtotal(JTextField txtSubtotal) { this.txtSubtotal = txtSubtotal; }

    public JTextField getTxtIVA() { return txtIVA; }
    public void setTxtIVA(JTextField txtIVA) { this.txtIVA = txtIVA; }

    public JTextField getTxtTotal() { return txtTotal; }
    public void setTxtTotal(JTextField txtTotal) { this.txtTotal = txtTotal; }

    public JButton getBtnLimpiar() { return btnLimpiar; }
    public void setBtnLimpiar(JButton btnLimpiar) { this.btnLimpiar = btnLimpiar; }

    public JLabel getLblCodigo() { return lblCodigo; }
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }

    public JLabel getLblCantidad() { return lblCantidad; }
    public void setLblCantidad(JLabel lblCantidad) { this.lblCantidad = lblCantidad; }

    public JLabel getLblSubtotal() { return lblSubtotal; }
    public void setLblSubtotal(JLabel lblSubtotal) { this.lblSubtotal = lblSubtotal; }

    public JLabel getLblIVA() { return lblIVA; }
    public void setLblIVA(JLabel lblIVA) { this.lblIVA = lblIVA; }

    public JLabel getLblTotal() { return lblTotal; }
    public void setLblTotal(JLabel lblTotal) { this.lblTotal = lblTotal; }

    public JPanel getLblModificar() { return lblModificar; }
    public void setLblModificar(JPanel lblModificar) { this.lblModificar = lblModificar; }

    public JLabel getLbl() { return lbl; }

    public void setLbl(JLabel lbl) { this.lbl = lbl; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        modelo.setRowCount(0);
        txtCarrito.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
    }

    private void cargarDatosCantidad() {
        cbxCantidad.removeAllItems();
        for (int i = 0; i < 20; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    public void cargarDatos(Carrito carrito) {
        Locale locale = mi.getLocale();
        modelo.setRowCount(0);
        for (ItemCarrito itemCarrito : carrito.obtenerItems()) {
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
    public void iconos(){
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            ImageIcon icono = new ImageIcon(botonBuscar);
            btnBuscar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonActualizar = LoginView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (botonActualizar != null) {
            ImageIcon icono = new ImageIcon(botonActualizar);
            btnActualizar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonLimpiar = LoginView.class.getClassLoader().getResource("imagenes/LimpiarTodo.svg.png");
        if (botonLimpiar != null) {
            ImageIcon icono = new ImageIcon(botonLimpiar);
            btnLimpiar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
