package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioListarView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField TxtUsuario;
    private JButton BtnBuscar;
    private JButton BtnListar;
    private JTable tblUsuario;
    private DefaultTableModel modelo;

    public UsuarioListarView(){
        setContentPane(panelPrincipal);
        setTitle("Listar Usuarios");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel(new Object[]{"Username", "Password", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuario.setModel(modelo);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtUsuario() {
        return TxtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        TxtUsuario = txtUsuario;
    }

    public JButton getBtnBuscar() {
        return BtnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        BtnBuscar = btnBuscar;
    }

    public JButton getBtnListar() {
        return BtnListar;
    }

    public void setBtnListar(JButton btnListar) {
        BtnListar = btnListar;
    }

    public JTable getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(JTable tblUsuario) {
        this.tblUsuario = tblUsuario;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
