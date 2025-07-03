package ec.edu.ups.vista.AdministracionView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;


public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JTextField txtContraseña;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JPanel panelPrincipal;
    private JButton btnOlvidar;
    private JButton btnSalir;
    private JComboBox cbxIdiomas;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblIniciarSesion;
    private MensajeInternacionalizacionHandler mi;

    public LoginView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        iconos();
    }



    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnOlvidar() {
        return btnOlvidar;
    }

    public void setBtnOlvidar(JButton btnOlvidar) {
        this.btnOlvidar = btnOlvidar;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
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

    public JLabel getLblIniciarSesion() {
        return lblIniciarSesion;
    }

    public void setLblIniciarSesion(JLabel lblIniciarSesion) {
        this.lblIniciarSesion = lblIniciarSesion;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContraseña.setText("");
    }
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem("Español");
        cbxIdiomas.addItem("English");
        cbxIdiomas.addItem("Français");
        actualizarTextos(mi);
    }

    public void actualizarTextos(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;

        lblIniciarSesion.setText(mi.get("login.titulo"));
        lblUsuario.setText(mi.get("login.usuario"));
        lblContraseña.setText(mi.get("login.contrasenia"));

        btnIniciarSesion.setText(mi.get("login.iniciar"));
        btnRegistrarse.setText(mi.get("login.registrar"));
        btnOlvidar.setText(mi.get("login.olvidar"));
        btnSalir.setText(mi.get("login.salir"));

        setTitle(mi.get("login.titulo"));
    }
    private void iconos() {
        URL botonIniciarSesion = LoginView.class.getClassLoader().getResource("imagenes/Login.svg.png");
        if (botonIniciarSesion != null) {
            ImageIcon icono = new ImageIcon(botonIniciarSesion);
            btnIniciarSesion.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonRegistrarse = LoginView.class.getClassLoader().getResource("imagenes/Login.svg.png");
        if (botonRegistrarse != null) {
            ImageIcon icono = new ImageIcon(botonRegistrarse);
            btnRegistrarse.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonOlvidar = LoginView.class.getClassLoader().getResource("imagenes/Olvidarrr.svg.png");
        if (botonOlvidar != null) {
            ImageIcon icono = new ImageIcon(botonOlvidar);
            btnOlvidar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonSalir = LoginView.class.getClassLoader().getResource("imagenes/Salir.svg.png");
        if (botonSalir != null) {
            ImageIcon icono = new ImageIcon(botonSalir);
            btnSalir.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
