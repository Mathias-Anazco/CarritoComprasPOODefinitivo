package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.net.URL;

public class UsuarioModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnEditar;
    private JTextField txtUsername;
    private JTextField txtContrasenia;
    private JTextField txtName;
    private JButton btnBuscar;
    private JLabel lblActualizar;
    private JTextField txtNombreCompleto;
    private JTextField txtCorreo;
    private JTextField txtCelular;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JComboBox cbxAño;
    private JLabel lblFechaN;
    private JLabel lblCelular;
    private JLabel lblCorreo;
    private JLabel lblNombreC;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblUser;
    private MensajeInternacionalizacionHandler mi;

    public UsuarioModificarView( MensajeInternacionalizacionHandler mi) {

        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Modificar Usuario");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        for (int i = 1; i <= 31; i++) cbxDia.addItem(i);
        for (int i = 1980; i <= 2025; i++) cbxAño.addItem(i);

        cambiarIdioma();
        iconos();
    }
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.modificar.titulo"));
        lblUser.setText(mi.get("usuario.modificar.usuario_buscar"));
        lblActualizar.setText(mi.get("usuario.modificar.actualizar"));
        lblUsuario.setText(mi.get("usuario.modificar.usuario"));
        lblContraseña.setText(mi.get("usuario.modificar.contrasena"));
        lblNombreC.setText(mi.get("usuario.modificar.nombre_completo"));
        lblCorreo.setText(mi.get("usuario.modificar.correo"));
        lblCelular.setText(mi.get("usuario.modificar.celular"));
        lblFechaN.setText(mi.get("usuario.modificar.fecha_nacimiento"));
        btnBuscar.setText(mi.get("boton.usuario.modificar.buscar"));
        btnEditar.setText(mi.get("boton.usuario.modificar.editar"));

        // Actualizar ComboBox de meses
        cbxMes.removeAllItems(); // Limpiar meses actuales

        for (int i = 1; i <= 12; i++) {
            cbxMes.addItem(mi.get("mes." + i));
        }
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblActualizar() {
        return lblActualizar;
    }

    public void setLblActualizar(JLabel lblActualizar) {
        this.lblActualizar = lblActualizar;
    }

    public JTextField getTxtNombreCompleto() {
        return txtNombreCompleto;
    }

    public void setTxtNombreCompleto(JTextField txtNombreCompleto) {
        this.txtNombreCompleto = txtNombreCompleto;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public JTextField getTxtCelular() {
        return txtCelular;
    }

    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }

    public JComboBox getCbxDia() {
        return cbxDia;
    }

    public void setCbxDia(JComboBox cbxDia) {
        this.cbxDia = cbxDia;
    }

    public JComboBox getCbxMes() {
        return cbxMes;
    }

    public void setCbxMes(JComboBox cbxMes) {
        this.cbxMes = cbxMes;
    }

    public JComboBox getCbxAño() {
        return cbxAño;
    }

    public void setCbxAño(JComboBox cbxAño) {
        this.cbxAño = cbxAño;
    }

    public JLabel getLblFechaN() {
        return lblFechaN;
    }

    public void setLblFechaN(JLabel lblFechaN) {
        this.lblFechaN = lblFechaN;
    }

    public JLabel getLblCelular() {
        return lblCelular;
    }

    public void setLblCelular(JLabel lblCelular) {
        this.lblCelular = lblCelular;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    public JLabel getLblNombreC() {
        return lblNombreC;
    }

    public void setLblNombreC(JLabel lblNombreC) {
        this.lblNombreC = lblNombreC;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblContraseña() {
        return lblContraseña;
    }

    public void setLblContraseña(JLabel lblContraseña) {
        this.lblContraseña = lblContraseña;
    }

    public MensajeInternacionalizacionHandler getMi() {
        return mi;
    }

    public void setMi(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    public JLabel getLblUser() {
        return lblUser;
    }

    public void setLblUser(JLabel lblUser) {
        this.lblUser = lblUser;
    }

    public void limpiarCampos() {
        txtName.setText("");
        txtUsername.setText("");
        txtContrasenia.setText("");
        txtNombreCompleto.setText("");
        txtCorreo.setText("");
        txtCelular.setText("");
        cbxDia.setSelectedIndex(0);
        cbxMes.setSelectedIndex(0);
        cbxAño.setSelectedIndex(0);

    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    public void iconos() {
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            ImageIcon icono = new ImageIcon(botonBuscar);
            btnBuscar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonEditar = LoginView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (botonEditar != null) {
            ImageIcon icono = new ImageIcon(botonEditar);
            btnEditar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
