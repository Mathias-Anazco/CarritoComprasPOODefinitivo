package ec.edu.ups.vista.AdministracionView;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * Representa la ventana de la interfaz gráfica (GUI) donde el usuario
 * establece sus preguntas y respuestas de seguridad después del registro.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class CuestionarioView extends JFrame {
    private JComboBox<Preguntas> cbxPreguntas;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JPanel panelPrincipal;
    private JButton btnTerminar;
    private JLabel lblTitulo;
    private JLabel lblPregunta;
    private MensajeInternacionalizacionHandler mi;
    private CuestionarioDAO cuestionarioDAO;

    /**
     * Constructor para la vista del cuestionario de seguridad.
     *
     * @param mi              El manejador de internacionalización para los textos de la UI.
     * @param cuestionarioDAO El DAO para acceder a la lista de preguntas disponibles.
     */
    public CuestionarioView( MensajeInternacionalizacionHandler mi, CuestionarioDAO cuestionarioDAO) {
        this.mi = mi;
        this.cuestionarioDAO = cuestionarioDAO;
        setContentPane(panelPrincipal);
        setTitle("Cuestionario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);
        icono();
        cargarPreguntas();
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JComboBox<Preguntas> getCbxPreguntas() { return cbxPreguntas; }
    public void setCbxPreguntas(JComboBox<Preguntas> cbxPreguntas) { this.cbxPreguntas = cbxPreguntas; }
    public JTextField getTxtRespuesta() { return txtRespuesta; }
    public void setTxtRespuesta(JTextField txtRespuesta) { this.txtRespuesta = txtRespuesta; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public void setBtnGuardar(JButton btnGuardar) { this.btnGuardar = btnGuardar; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JButton getBtnTerminar() { return btnTerminar; }
    public void setBtnTerminar(JButton btnTerminar) { this.btnTerminar = btnTerminar; }
    public JLabel getLblTitulo() { return lblTitulo; }
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }
    public JLabel getLblPregunta() { return lblPregunta; }
    public void setLblPregunta(JLabel lblPregunta) { this.lblPregunta = lblPregunta; }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga y establece los íconos para los botones desde los recursos del proyecto.
     */
    private void icono() {
        URL botonGuardarCuestionario = LoginView.class.getClassLoader().getResource("imagenes/Guardar.svg.png");
        if (botonGuardarCuestionario != null) {
            btnGuardar.setIcon(new ImageIcon(botonGuardarCuestionario));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonTerminarCuestionario = LoginView.class.getClassLoader().getResource("imagenes/Terminar.svg.png");
        if (botonTerminarCuestionario != null) {
            btnTerminar.setIcon(new ImageIcon(botonTerminarCuestionario));
        } else {
            System.err.println("Icono no encontrado");
        }
    }

    /**
     * Limpia los campos de entrada de la vista para permitir al usuario
     * ingresar una nueva pregunta y respuesta. Remueve la pregunta ya seleccionada
     * del JComboBox para evitar que se responda dos veces.
     */
    public void limpiarCampos() {
        int selectedIndex = cbxPreguntas.getSelectedIndex();
        if (selectedIndex != -1){
            cbxPreguntas.removeItemAt(selectedIndex);
        }
        txtRespuesta.setText("");
    }

    /**
     * Carga las preguntas de seguridad desde el DAO y las añade al JComboBox.
     */
    public void cargarPreguntas() {
        cbxPreguntas.removeAllItems();
        for (Preguntas pregunta : cuestionarioDAO.listarPreguntas()) {
            cbxPreguntas.addItem(pregunta);
        }
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma especificado.
     *
     * @param mi El manejador de internacionalización con el idioma deseado.
     */
    public void actualizarTextos(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        lblTitulo.setText(mi.get("cuestionario.titulo"));
        lblPregunta.setText(mi.get("cuestionario.pregunta"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnTerminar.setText(mi.get("cuestionario.boton.terminar"));
    }
}