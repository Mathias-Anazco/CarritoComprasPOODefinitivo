package ec.edu.ups.vista.AdministracionView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * Representa la ventana de la interfaz gráfica de usuario (GUI) para el proceso
 * de recuperación de contraseña. Permite al usuario buscar su perfil,
 * seleccionar una de sus preguntas de seguridad y proporcionar una respuesta
 * para verificar su identidad.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CuestionarioRecuperarView extends JFrame {
    private JButton btnEnviar;
    private JPanel panelPrincipal;
    private JButton terminarButton;
    private JLabel lblTitulo;
    private JTextField txtRespuesta1;
    private JLabel lblPregunta1;
    private JComboBox cbxPreguntas;
    private JLabel lblRespuesta;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JLabel lblUsuario;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor de la vista de recuperación de contraseña.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public CuestionarioRecuperarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Recuperar Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        icono();
    }

    /**
     * Actualiza todos los textos visibles en la ventana (título, etiquetas, botones)
     * al idioma especificado por el manejador.
     *
     * @param mi El manejador de internacionalización con el idioma deseado.
     */
    public void actualizarTextos(MensajeInternacionalizacionHandler mi) {
        setTitle(mi.get("recuperar.titulo"));

        lblTitulo.setText(mi.get("recuperar.titulo"));
        lblUsuario.setText(mi.get("recuperar.usuario"));
        lblPregunta1.setText(mi.get("recuperar.pregunta"));
        lblRespuesta.setText(mi.get("recuperar.respuesta"));

        btnBuscar.setText(mi.get("recuperar.boton.buscar"));
        btnEnviar.setText(mi.get("recuperar.boton.enviar"));
        terminarButton.setText(mi.get("recuperar.boton.terminar"));
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JButton getBtnEnviar() { return btnEnviar; }
    public void setBtnEnviar(JButton btnEnviar) { this.btnEnviar = btnEnviar; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JButton getTerminarButton() { return terminarButton; }
    public void setTerminarButton(JButton terminarButton) { this.terminarButton = terminarButton; }
    public JTextField getTxtRespuesta1() { return txtRespuesta1; }
    public void setTxtRespuesta1(JTextField txtRespuesta1) { this.txtRespuesta1 = txtRespuesta1; }
    public JLabel getLblPregunta1() { return lblPregunta1; }
    public void setLblPregunta1(JLabel lblPregunta1) { this.lblPregunta1 = lblPregunta1; }
    public MensajeInternacionalizacionHandler getMi() { return mi; }
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }
    public JLabel getLblTitulo() { return lblTitulo; }
    public JComboBox getCbxPreguntas() { return cbxPreguntas; }
    public void setCbxPreguntas(JComboBox cbxPreguntas) { this.cbxPreguntas = cbxPreguntas; }
    public JLabel getLblRespuesta() { return lblRespuesta; }
    public void setLblRespuesta(JLabel lblRespuesta) { this.lblRespuesta = lblRespuesta; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public void setTxtUsuario(JTextField txtUsuario) { this.txtUsuario = txtUsuario; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public void setLblUsuario(JLabel lblUsuario) { this.lblUsuario = lblUsuario; }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz
     * desde los recursos del proyecto.
     */
    public void icono() {
        URL botonTerminarCuestionario = LoginView.class.getClassLoader().getResource("imagenes/Terminar.svg.png");
        if (botonTerminarCuestionario != null) {
            terminarButton.setIcon(new ImageIcon(botonTerminarCuestionario));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonEnviarCuestionario = LoginView.class.getClassLoader().getResource("imagenes/Enviar.svg.png");
        if (botonEnviarCuestionario != null) {
            btnEnviar.setIcon(new ImageIcon(botonEnviarCuestionario));
        } else {
            System.err.println("Icono no encontrado");
        }
    }

    /**
     * Limpia los campos de entrada de la vista.
     */
    public void limpiarCampos() {
        // (Implementación futura para limpiar txtUsuario, txtRespuesta1, etc.)
    }
}