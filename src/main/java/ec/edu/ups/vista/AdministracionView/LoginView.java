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
    private JComboBox<String> cbxArchivo;
    private File carpetaSeleccionada;
    private JButton BtnSeleccionar;
    private JLabel lblArchivo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor de la vista de inicio de sesión.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
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
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JTextField getTxtUsername() { return txtUsername; }
    public void setTxtUsername(JTextField txtUsername) { this.txtUsername = txtUsername; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public void setTxtContraseña(JTextField txtContraseña) { this.txtContraseña = txtContraseña; }
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public void setBtnIniciarSesion(JButton btnIniciarSesion) { this.btnIniciarSesion = btnIniciarSesion; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public void setBtnRegistrarse(JButton btnRegistrarse) { this.btnRegistrarse = btnRegistrarse; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JButton getBtnOlvidar() { return btnOlvidar; }
    public void setBtnOlvidar(JButton btnOlvidar) { this.btnOlvidar = btnOlvidar; }
    public JButton getBtnSalir() { return btnSalir; }
    public void setBtnSalir(JButton btnSalir) { this.btnSalir = btnSalir; }
    public JComboBox<String> getCbxIdiomas() { return cbxIdiomas; }
    public void setCbxIdiomas(JComboBox<String> cbxIdiomas) { this.cbxIdiomas = cbxIdiomas; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public void setLblUsuario(JLabel lblUsuario) { this.lblUsuario = lblUsuario; }
    public JLabel getLblContraseña() { return lblContraseña; }
    public void setLblContraseña(JLabel lblContraseña) { this.lblContraseña = lblContraseña; }
    public JLabel getLblIniciarSesion() { return lblIniciarSesion; }
    public void setLblIniciarSesion(JLabel lblIniciarSesion) { this.lblIniciarSesion = lblIniciarSesion; }
    public JComboBox<String> getCbxArchivo() { return cbxArchivo; }
    public void setCbxArchivo(JComboBox<String> cbxArchivo) { this.cbxArchivo = cbxArchivo; }
    public void setCarpetaSeleccionada(File carpetaSeleccionada) { this.carpetaSeleccionada = carpetaSeleccionada; }
    public JButton getBtnSeleccionar() { return BtnSeleccionar; }
    public void setBtnSeleccionar(JButton btnSeleccionar) { BtnSeleccionar = btnSeleccionar; }
    public JLabel getLblArchivo() { return lblArchivo; }
    public void setLblArchivo(JLabel lblArchivo) { this.lblArchivo = lblArchivo; }
    public MensajeInternacionalizacionHandler getMi() { return mi; }
    public void setMi(MensajeInternacionalizacionHandler mi) { this.mi = mi; }
    public File getCarpetaSeleccionada() { return carpetaSeleccionada; }

    /**
     * Muestra un mensaje emergente de información en la ventana.
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
     * Limpia los campos de texto de usuario y contraseña.
     */
    public void limpiarCampos() {
        txtUsername.setText("");
        txtContraseña.setText("");
    }

    /**
     * Inicializa los componentes de la vista con sus valores por defecto y
     * configura los listeners de eventos internos.
     */
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem("Español");
        cbxIdiomas.addItem("English");
        cbxIdiomas.addItem("Français");

        cbxArchivo.removeAllItems();
        cbxArchivo.addItem(mi.get("login.memoria"));
        cbxArchivo.addItem(mi.get("login.archivo"));
        cbxArchivo.setSelectedIndex(0);

        BtnSeleccionar.setEnabled(false);

        cbxArchivo.addActionListener(e -> {
            String seleccion = (String) cbxArchivo.getSelectedItem();
            boolean esArchivo = seleccion != null && seleccion.equalsIgnoreCase(mi.get("login.archivo"));
            BtnSeleccionar.setEnabled(esArchivo);
        });

        BtnSeleccionar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int opcion = chooser.showOpenDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                carpetaSeleccionada = chooser.getSelectedFile();
                mostrarMensaje(mi.get("login.archivo.seleccionado") + ": " + carpetaSeleccionada.getAbsolutePath());
            }
        });

        actualizarTextos(mi);
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma especificado.
     *
     * @param mi El manejador de internacionalización con el idioma deseado.
     */
    public void actualizarTextos(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;

        lblIniciarSesion.setText(mi.get("login.titulo"));
        lblUsuario.setText(mi.get("login.usuario"));
        lblArchivo.setText(mi.get("login.archivo.seleccionado"));
        lblContraseña.setText(mi.get("login.contrasenia"));

        btnIniciarSesion.setText(mi.get("login.iniciar"));
        btnRegistrarse.setText(mi.get("login.registrar"));
        btnOlvidar.setText(mi.get("login.olvidar"));
        btnSalir.setText(mi.get("login.salir"));
        BtnSeleccionar.setText(mi.get("login.seleccionar"));

        int indiceSeleccionado = cbxArchivo.getSelectedIndex();
        cbxArchivo.removeAllItems();
        cbxArchivo.addItem(mi.get("login.memoria"));
        cbxArchivo.addItem(mi.get("login.archivo"));
        if (indiceSeleccionado >= 0 && indiceSeleccionado < cbxArchivo.getItemCount()) {
            cbxArchivo.setSelectedIndex(indiceSeleccionado);
        }

        setTitle(mi.get("login.titulo"));
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz
     * desde los recursos del proyecto.
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

        URL botonSeleccionar = LoginView.class.getClassLoader().getResource("imagenes/Crear.svg.png");
        if (botonSeleccionar != null) BtnSeleccionar.setIcon(new ImageIcon(botonSeleccionar));
    }
}