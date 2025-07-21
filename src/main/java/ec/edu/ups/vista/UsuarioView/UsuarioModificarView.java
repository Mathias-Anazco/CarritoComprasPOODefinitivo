package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.net.URL;

/**
 * Representa la interfaz gráfica (GUI) para que un administrador modifique un usuario existente.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite buscar un usuario por su cédula, cargar sus datos en un formulario y guardarlos tras la edición.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
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
    private JComboBox<Integer> cbxDia;
    private JComboBox<String> cbxMes;
    private JComboBox<Integer> cbxAño;
    private JLabel lblFechaN;
    private JLabel lblCelular;
    private JLabel lblCorreo;
    private JLabel lblNombreC;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblUser;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de modificación de usuarios.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
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

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
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

        if (cbxMes != null) {
            cbxMes.removeAllItems();
            for (int i = 1; i <= 12; i++) {
                cbxMes.addItem(mi.get("mes." + i));
            }
        }
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JButton getBtnEditar() { return btnEditar; }
    public void setBtnEditar(JButton btnEditar) { this.btnEditar = btnEditar; }
    public JTextField getTxtUsername() { return txtUsername; }
    public void setTxtUsername(JTextField txtUsername) { this.txtUsername = txtUsername; }
    public JTextField getTxtContrasenia() { return txtContrasenia; }
    public void setTxtContrasenia(JTextField txtContrasenia) { this.txtContrasenia = txtContrasenia; }
    public JTextField getTxtName() { return txtName; }
    public void setTxtName(JTextField txtName) { this.txtName = txtName; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }
    public JLabel getLblActualizar() { return lblActualizar; }
    public void setLblActualizar(JLabel lblActualizar) { this.lblActualizar = lblActualizar; }
    public JTextField getTxtNombreCompleto() { return txtNombreCompleto; }
    public void setTxtNombreCompleto(JTextField txtNombreCompleto) { this.txtNombreCompleto = txtNombreCompleto; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public void setTxtCorreo(JTextField txtCorreo) { this.txtCorreo = txtCorreo; }
    public JTextField getTxtCelular() { return txtCelular; }
    public void setTxtCelular(JTextField txtCelular) { this.txtCelular = txtCelular; }
    public JComboBox<Integer> getCbxDia() { return cbxDia; }
    public void setCbxDia(JComboBox<Integer> cbxDia) { this.cbxDia = cbxDia; }
    public JComboBox<String> getCbxMes() { return cbxMes; }
    public void setCbxMes(JComboBox<String> cbxMes) { this.cbxMes = cbxMes; }
    public JComboBox<Integer> getCbxAño() { return cbxAño; }
    public void setCbxAño(JComboBox<Integer> cbxAño) { this.cbxAño = cbxAño; }
    public JLabel getLblFechaN() { return lblFechaN; }
    public void setLblFechaN(JLabel lblFechaN) { this.lblFechaN = lblFechaN; }
    public JLabel getLblCelular() { return lblCelular; }
    public void setLblCelular(JLabel lblCelular) { this.lblCelular = lblCelular; }
    public JLabel getLblCorreo() { return lblCorreo; }
    public void setLblCorreo(JLabel lblCorreo) { this.lblCorreo = lblCorreo; }
    public JLabel getLblNombreC() { return lblNombreC; }
    public void setLblNombreC(JLabel lblNombreC) { this.lblNombreC = lblNombreC; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public void setLblUsuario(JLabel lblUsuario) { this.lblUsuario = lblUsuario; }
    public JLabel getLblContraseña() { return lblContraseña; }
    public void setLblContraseña(JLabel lblContraseña) { this.lblContraseña = lblContraseña; }
    public MensajeInternacionalizacionHandler getMi() { return mi; }
    public void setMi(MensajeInternacionalizacionHandler mi) { this.mi = mi; }
    public JLabel getLblUser() { return lblUser; }
    public void setLblUser(JLabel lblUser) { this.lblUser = lblUser; }

    /**
     * Limpia todos los campos del formulario, restableciéndolos a su estado inicial.
     */
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

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Muestra un diálogo de confirmación (Sí/No) al usuario.
     *
     * @param mensaje El texto de la pregunta a mostrar.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
    public void iconos() {
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            btnBuscar.setIcon(new ImageIcon(botonBuscar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonEditar = LoginView.class.getClassLoader().getResource("imagenes/Actualizar.svg.png");
        if (botonEditar != null) {
            btnEditar.setIcon(new ImageIcon(botonEditar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}