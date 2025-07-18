package ec.edu.ups.vista.AdministracionView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.io.File;
import java.net.URL;

/**
 * Representa la ventana principal de inicio de sesión de la aplicación.
 * Esta clase gestiona la interfaz gráfica (GUI) donde los usuarios ingresan
 * sus credenciales, seleccionan el idioma y el modo de persistencia de datos
 * (memoria o archivo).
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class LoginView extends JFrame {

    private JTextField txtUsername;
    private JTextField txtContraseña;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JPanel panelPrincipal;
    private JButton btnOlvidar;
    private JButton btnSalir;
    private JComboBox<String> cbxIdiomas;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblIniciarSesion;
    private File carpetaSeleccionada;
    private MensajeInternacionalizacionHandler mi;

    public LoginView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        inicializarComponentes();
        iconos();
    }

    public JTextField getTxtUsername() { return txtUsername; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JButton getBtnOlvidar() { return btnOlvidar; }
    public JButton getBtnSalir() { return btnSalir; }
    public JComboBox<String> getCbxIdiomas() { return cbxIdiomas; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public JLabel getLblContraseña() { return lblContraseña; }
    public JLabel getLblIniciarSesion() { return lblIniciarSesion; }
    public void setCarpetaSeleccionada(File carpetaSeleccionada) { this.carpetaSeleccionada = carpetaSeleccionada; }
    public MensajeInternacionalizacionHandler getMi() { return mi; }
    public File getCarpetaSeleccionada() { return carpetaSeleccionada; }

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
        if (botonIniciarSesion != null) btnIniciarSesion.setIcon(new ImageIcon(botonIniciarSesion));

        URL botonRegistrarse = LoginView.class.getClassLoader().getResource("imagenes/Login.svg.png");
        if (botonRegistrarse != null) btnRegistrarse.setIcon(new ImageIcon(botonRegistrarse));

        URL botonOlvidar = LoginView.class.getClassLoader().getResource("imagenes/Olvidarrr.svg.png");
        if (botonOlvidar != null) btnOlvidar.setIcon(new ImageIcon(botonOlvidar));

        URL botonSalir = LoginView.class.getClassLoader().getResource("imagenes/Salir.svg.png");
        if (botonSalir != null) btnSalir.setIcon(new ImageIcon(botonSalir));
    }
}
