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

    /**
     * Crea una nueva instancia de {@code LoginView}.
     * Configura la ventana principal de inicio de sesión y sus componentes iniciales.
     *
     * @param mi El manejador de internacionalización para los textos de la interfaz.
     */
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

    /**
     * Obtiene el campo de texto para el nombre de usuario.
     * @return El {@link JTextField} del nombre de usuario.
     */
    public JTextField getTxtUsername() { return txtUsername; }

    /**
     * Obtiene el campo de texto para la contraseña.
     * @return El {@link JTextField} de la contraseña.
     */
    public JTextField getTxtContraseña() { return txtContraseña; }

    /**
     * Obtiene el botón de iniciar sesión.
     * @return El {@link JButton} de iniciar sesión.
     */
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }

    /**
     * Obtiene el botón de registrarse.
     * @return El {@link JButton} de registrarse.
     */
    public JButton getBtnRegistrarse() { return btnRegistrarse; }

    /**
     * Obtiene el panel principal de la vista.
     * @return El {@link JPanel} principal.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }

    /**
     * Obtiene el botón de "Olvidar Contraseña".
     * @return El {@link JButton} de olvidar contraseña.
     */
    public JButton getBtnOlvidar() { return btnOlvidar; }

    /**
     * Obtiene el botón de salir.
     * @return El {@link JButton} de salir.
     */
    public JButton getBtnSalir() { return btnSalir; }

    /**
     * Obtiene el ComboBox para seleccionar el idioma.
     * @return El {@link JComboBox} de idiomas.
     */
    public JComboBox<String> getCbxIdiomas() { return cbxIdiomas; }

    /**
     * Obtiene la etiqueta para el nombre de usuario.
     * @return El {@link JLabel} de usuario.
     */
    public JLabel getLblUsuario() { return lblUsuario; }

    /**
     * Obtiene la etiqueta para la contraseña.
     * @return El {@link JLabel} de contraseña.
     */
    public JLabel getLblContraseña() { return lblContraseña; }

    /**
     * Obtiene la etiqueta del título "Iniciar Sesión".
     * @return El {@link JLabel} del título de inicio de sesión.
     */
    public JLabel getLblIniciarSesion() { return lblIniciarSesion; }

    /**
     * Establece la carpeta seleccionada para la persistencia de datos.
     * @param carpetaSeleccionada El {@link File} que representa la carpeta.
     */
    public void setCarpetaSeleccionada(File carpetaSeleccionada) { this.carpetaSeleccionada = carpetaSeleccionada; }

    /**
     * Obtiene el manejador de internacionalización.
     * @return El {@link MensajeInternacionalizacionHandler} actual.
     */
    public MensajeInternacionalizacionHandler getMi() { return mi; }

    /**
     * Obtiene la carpeta seleccionada para la persistencia de datos.
     * @return El {@link File} de la carpeta seleccionada.
     */
    public File getCarpetaSeleccionada() { return carpetaSeleccionada; }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     * @param mensaje El texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Muestra un cuadro de diálogo de confirmación con opciones Sí/No.
     * @param mensaje La pregunta a mostrar.
     * @return {@code true} si el usuario selecciona "Sí", {@code false} en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Limpia los campos de texto de usuario y contraseña.
     */
    public void limpiarCampos() {
        txtUsername.setText("");
        txtContraseña.setText("");
    }

    /**
     * Inicializa los componentes de la GUI, como la adición de idiomas al ComboBox.
     */
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem("Español");
        cbxIdiomas.addItem("English");
        cbxIdiomas.addItem("Français");

        actualizarTextos(mi);
    }

    /**
     * Actualiza todos los textos de la interfaz de usuario basándose en el manejador de internacionalización.
     *
     * @param mi El {@link MensajeInternacionalizacionHandler} con los textos actualizados.
     */
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

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
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