package ec.edu.ups.vista.AdministracionView;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class CuestionarioView extends JFrame {
    private JComboBox cbxPreguntas;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JPanel panelPrincipal;
    private JButton btnTerminar;
    private JLabel lblTitulo;
    private JLabel lblPregunta;
    private MensajeInternacionalizacionHandler mi;
    private CuestionarioDAO cuestionarioDAO;
    private CuestionarioDAOMemoria cuestionarioDAOMemoria;


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

    public JComboBox getCbxPreguntas() {
        return cbxPreguntas;
    }

    public void setCbxPreguntas(JComboBox cbxPreguntas) {
        this.cbxPreguntas = cbxPreguntas;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnTerminar() {
        return btnTerminar;
    }

    public void setBtnTerminar(JButton btnTerminar) {
        this.btnTerminar = btnTerminar;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void icono() {
        URL botonGuardarCuestionario = LoginView.class.getClassLoader().getResource("imagenes/Guardar.svg.png");
        if (botonGuardarCuestionario != null) {
            ImageIcon icono = new ImageIcon(botonGuardarCuestionario);
            btnGuardar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonTerminarCuestionario = LoginView.class.getClassLoader().getResource("imagenes/Terminar.svg.png");
        if (botonTerminarCuestionario != null) {
            ImageIcon icono = new ImageIcon(botonTerminarCuestionario);
            btnTerminar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }

    public void limpiarCampos() {
        cbxPreguntas.setSelectedIndex(0);
        txtRespuesta.setText("");
    }
    public void cargarPreguntas() {
        cbxPreguntas.removeAllItems();
        for (Preguntas pregunta : cuestionarioDAO.listarPreguntas()) {
            cbxPreguntas.addItem(pregunta);
        }
    }
    public void actualizarTextos(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        lblTitulo.setText(mi.get("cuestionario.titulo"));
        lblPregunta.setText(mi.get("cuestionario.pregunta"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnTerminar.setText(mi.get("cuestionario.boton.terminar"));
    }

}
