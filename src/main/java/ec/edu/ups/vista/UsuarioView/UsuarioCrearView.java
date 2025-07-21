package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Representa la interfaz gráfica (GUI) para que un administrador cree un nuevo usuario.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Proporciona un formulario completo para registrar todos los datos de un usuario,
 * incluyendo la asignación de un rol.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class UsuarioCrearView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField TxtUsername;
    private JTextField TxtPassword;
    private JComboBox<String> CbxRol;
    private JButton BtnRegistrar;
    private JButton BtnLimpiar;
    private JLabel lblNuevoUsuario;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JTextField TxtCorreo;
    private JTextField TxtCelular;
    private JTextField TxtNombreCompleto;
    private JLabel lblNombreC;
    private JLabel lblCorreo;
    private JLabel lblCelular;
    private JLabel lblFechaN;
    private JComboBox<Integer> cbxDia;
    private JComboBox<String> cbxMes;
    private JComboBox<Integer> cbxAño;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de creación de usuarios.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public UsuarioCrearView ( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Crear Usuario");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        cargarRoles();
        iconos();

        BtnLimpiar.addActionListener(e -> limpiarCampos());
        for (int i = 1; i <= 31; i++) cbxDia.addItem(i);
        for (int i = 1980; i <= 2025; i++) cbxAño.addItem(i);

        cambiarIdioma();
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.crear.titulo"));
        lblNuevoUsuario.setText(mi.get("usuario.crear.tituloEtiqueta"));
        lblUsuario.setText(mi.get("usuario.crear.usuario"));
        lblContraseña.setText(mi.get("usuario.crear.contrasena"));
        lblRol.setText(mi.get("usuario.crear.rol"));
        lblCorreo.setText(mi.get("usuario.crear.correo"));
        lblCelular.setText(mi.get("usuario.crear.celular"));
        lblNombreC.setText(mi.get("usuario.crear.nombreCompleto"));
        lblFechaN.setText(mi.get("usuario.crear.fechaNacimiento"));
        BtnRegistrar.setText(mi.get("usuario.crear.boton.registrar"));
        BtnLimpiar.setText(mi.get("usuario.crear.boton.limpiar"));

        cbxMes.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            cbxMes.addItem(mi.get("mes." + i));
        }

        CbxRol.removeAllItems();
        CbxRol.addItem(mi.get("rol.administrador"));
        CbxRol.addItem(mi.get("rol.usuario"));
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JTextField getTxtUsername() { return TxtUsername; }
    public void setTxtUsername(JTextField txtUsername) { TxtUsername = txtUsername; }
    public JTextField getTxtPassword() { return TxtPassword; }
    public void setTxtPassword(JTextField txtPassword) { TxtPassword = txtPassword; }
    public JComboBox<String> getCbxRol() { return CbxRol; }
    public void setCbxRol(JComboBox<String> cbxRol) { CbxRol = cbxRol; }
    public JButton getBtnRegistrar() { return BtnRegistrar; }
    public void setBtnRegistrar(JButton btnRegistrar) { BtnRegistrar = btnRegistrar; }
    public JButton getBtnLimpiar() { return BtnLimpiar; }
    public void setBtnLimpiar(JButton btnLimpiar) { BtnLimpiar = btnLimpiar; }
    public JLabel getLblNuevoUsuario() { return lblNuevoUsuario; }
    public void setLblNuevoUsuario(JLabel lblNuevoUsuario) { this.lblNuevoUsuario = lblNuevoUsuario; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public void setLblUsuario(JLabel lblUsuario) { this.lblUsuario = lblUsuario; }
    public JLabel getLblContraseña() { return lblContraseña; }
    public void setLblContraseña(JLabel lblContraseña) { this.lblContraseña = lblContraseña; }
    public JLabel getLblRol() { return lblRol; }
    public void setLblRol(JLabel lblRol) { this.lblRol = lblRol; }
    public JTextField getTxtCorreo() { return TxtCorreo; }
    public void setTxtCorreo(JTextField txtCorreo) { TxtCorreo = txtCorreo; }
    public JTextField getTxtCelular() { return TxtCelular; }
    public void setTxtCelular(JTextField txtCelular) { TxtCelular = txtCelular; }
    public JTextField getTxtNombreCompleto() { return TxtNombreCompleto; }
    public void setTxtNombreCompleto(JTextField txtNombreCompleto) { TxtNombreCompleto = txtNombreCompleto; }
    public JLabel getLblNombreC() { return lblNombreC; }
    public void setLblNombreC(JLabel lblNombreC) { this.lblNombreC = lblNombreC; }
    public JLabel getLblCorreo() { return lblCorreo; }
    public void setLblCorreo(JLabel lblCorreo) { this.lblCorreo = lblCorreo; }
    public JLabel getLblCelular() { return lblCelular; }
    public void setLblCelular(JLabel lblCelular) { this.lblCelular = lblCelular; }
    public JLabel getLblFechaN() { return lblFechaN; }
    public void setLblFechaN(JLabel lblFechaN) { this.lblFechaN = lblFechaN; }
    public JComboBox<Integer> getCbxDia() { return cbxDia; }
    public void setCbxDia(JComboBox<Integer> cbxDia) { this.cbxDia = cbxDia; }
    public JComboBox<String> getCbxMes() { return cbxMes; }
    public void setCbxMes(JComboBox<String> cbxMes) { this.cbxMes = cbxMes; }
    public JComboBox<Integer> getCbxAño() { return cbxAño; }
    public void setCbxAño(JComboBox<Integer> cbxAño) { this.cbxAño = cbxAño; }
    public MensajeInternacionalizacionHandler getMi() { return mi; }
    public void setMi(MensajeInternacionalizacionHandler mi) { this.mi = mi; }

    /**
     * Limpia todos los campos del formulario, restableciéndolos a su estado inicial.
     */
    public void limpiarCampos() {
        TxtUsername.setText("");
        TxtPassword.setText("");
        TxtCorreo.setText("");
        TxtCelular.setText("");
        TxtNombreCompleto.setText("");
        CbxRol.setSelectedIndex(0);
        cbxDia.setSelectedIndex(0);
        cbxMes.setSelectedIndex(0);
        cbxAño.setSelectedIndex(0);
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
     * Carga las opciones de roles en el JComboBox correspondiente.
     */
    public void cargarRoles() {
        CbxRol.removeAllItems();
        CbxRol.addItem("ADMINISTRADOR");
        CbxRol.addItem("USUARIO");
    }

    /**
     * Obtiene el rol seleccionado por el usuario en el JComboBox.
     *
     * @return El {@link Rol} correspondiente a la selección.
     */
    public Rol getRolSeleccionado() {
        String seleccionado = (String) CbxRol.getSelectedItem();
        if (seleccionado == null) return null;

        if (seleccionado.equalsIgnoreCase("ADMINISTRADOR") || seleccionado.equals(mi.get("rol.administrador"))) {
            return Rol.ADMINISTRADOR;
        } else {
            return Rol.USUARIO;
        }
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
    public void iconos() {
        URL botonLimpiar = LoginView.class.getClassLoader().getResource("imagenes/LimpiarTodo.svg.png");
        if (botonLimpiar != null) {
            BtnLimpiar.setIcon(new ImageIcon(botonLimpiar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonRegistrar = LoginView.class.getClassLoader().getResource("imagenes/Register.svg.png");
        if (botonRegistrar != null) {
            BtnRegistrar.setIcon(new ImageIcon(botonRegistrar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}