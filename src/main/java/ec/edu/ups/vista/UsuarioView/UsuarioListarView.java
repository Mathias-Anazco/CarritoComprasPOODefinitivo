package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * Representa la interfaz gráfica (GUI) para que un administrador liste y busque usuarios.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite listar todos los usuarios del sistema o buscar un usuario específico por su cédula.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class UsuarioListarView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField TxtUsuario;
    private JButton BtnBuscar;
    private JButton BtnListar;
    private JTable tblUsuario;
    private JLabel lblListar;
    private JLabel lblUser;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de listado de usuarios.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public UsuarioListarView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Listar Usuarios");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel(new Object[]{}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuario.setModel(modelo);
        cambiarIdioma();
        iconos();
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.listar.titulo"));
        lblListar.setText(mi.get("usuario.listar.tituloTabla"));
        lblUser.setText(mi.get("usuario.listar.usuario"));
        BtnBuscar.setText(mi.get("usuario.listar.boton.buscar"));
        BtnListar.setText(mi.get("usuario.listar.boton.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("usuario.listar.columna.nombre"),
                mi.get("usuario.listar.columna.usuario"),
                mi.get("usuario.listar.columna.contrasena"),
                mi.get("usuario.listar.columna.correo"),
                mi.get("usuario.listar.columna.celular"),
                mi.get("usuario.listar.columna.fechaNacimiento"),
                mi.get("usuario.listar.columna.rol")
        });
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JTextField getTxtUsuario() { return TxtUsuario; }
    public void setTxtUsuario(JTextField txtUsuario) { TxtUsuario = txtUsuario; }
    public JButton getBtnBuscar() { return BtnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.BtnBuscar = btnBuscar; }
    public JButton getBtnListar() { return BtnListar; }
    public void setBtnListar(JButton btnListar) { this.BtnListar = btnListar; }
    public JTable getTblUsuario() { return tblUsuario; }
    public void setTblUsuario(JTable tblUsuario) { this.tblUsuario = tblUsuario; }
    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
    public JLabel getLblListar() { return lblListar; }
    public void setLblListar(JLabel lblListar) { this.lblListar = lblListar; }
    public JLabel getLblUser() { return lblUser; }
    public void setLblUser(JLabel lblUser) { this.lblUser = lblUser; }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga y establece los íconos para los botones de la interfaz.
     */
    public void iconos(){
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            BtnBuscar.setIcon(new ImageIcon(botonBuscar));
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonListar = LoginView.class.getClassLoader().getResource("imagenes/ListarTodo.svg.png");
        if (botonListar != null) {
            BtnListar.setIcon(new ImageIcon(botonListar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}