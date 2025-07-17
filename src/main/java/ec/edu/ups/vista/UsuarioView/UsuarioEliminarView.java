package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;

/**
 * Representa la interfaz gráfica (GUI) para que un administrador elimine un usuario.
 * Como JInternalFrame, está diseñada para ser mostrada dentro de una ventana principal.
 * Permite buscar un usuario por su cédula, visualizar sus datos y confirmar su eliminación.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class UsuarioEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField TxtUsuario;
    private JTable tblUser;
    private JButton BtnEliminar;
    private JButton BtnBuscar;
    private JLabel lblEliminar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista de eliminación de usuarios.
     *
     * @param mi El manejador de internacionalización para los textos de la UI.
     */
    public UsuarioEliminarView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Eliminar Usuario");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUser.setModel(modelo);
        cambiarIdioma();
        iconos();
    }

    /**
     * Actualiza todos los textos visibles en la ventana al idioma actual.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.eliminar.titulo"));
        lblEliminar.setText(mi.get("usuario.eliminar.tituloEtiqueta"));
        BtnBuscar.setText(mi.get("usuario.eliminar.boton.buscar"));
        BtnEliminar.setText(mi.get("usuario.eliminar.boton.eliminar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("usuario.eliminar.columna.nombre"),
                mi.get("usuario.eliminar.columna.usuario"),
                mi.get("usuario.eliminar.columna.contrasena"),
                mi.get("usuario.eliminar.columna.correo"),
                mi.get("usuario.eliminar.columna.celular"),
                mi.get("usuario.eliminar.columna.fechaNacimiento"),
                mi.get("usuario.eliminar.columna.rol")
        });
    }

    /**
     * Métodos de acceso a los componentes de la interfaz de usuario.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JTextField getTxtUsuario() { return TxtUsuario; }
    public void setTxtUsuario(JTextField txtUsuario) { TxtUsuario = txtUsuario; }
    public JTable getTblUser() { return tblUser; }
    public void setTblUser(JTable tblUser) { this.tblUser = tblUser; }
    public JButton getBtnEliminar() { return BtnEliminar; }
    public void setBtnEliminar(JButton btnEliminar) { this.BtnEliminar = btnEliminar; }
    public JButton getBtnBuscar() { return BtnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.BtnBuscar = btnBuscar; }
    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
    public JLabel getLblEliminar() { return lblEliminar; }
    public void setLblEliminar(JLabel lblEliminar) { this.lblEliminar = lblEliminar; }

    /**
     * Muestra un mensaje emergente en la ventana.
     *
     * @param mensaje El texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga los datos de una lista de usuarios en la tabla de la vista para confirmación.
     *
     * @param usuarios La lista de usuarios a mostrar (normalmente un solo usuario).
     */
    public void cargarUsuario(List<Usuario> usuarios) {
        modelo.setRowCount(0);

        for (Usuario usuario : usuarios) {
            Object[] fila = {
                    usuario.getNombreCompleto(),
                    usuario.getUsername(),
                    usuario.getContrasenia(),
                    usuario.getCorreo(),
                    usuario.getCelular(),
                    usuario.getFechaNacimiento(),
                    usuario.getRol()
            };
            modelo.addRow(fila);
        }
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
        URL botonEliminar = LoginView.class.getClassLoader().getResource("imagenes/EliminarTodo.svg.png");
        if (botonEliminar != null) {
            BtnEliminar.setIcon(new ImageIcon(botonEliminar));
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}