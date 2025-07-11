package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;

public class UsuarioEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField TxtUsuario;
    private JTable tblUser;
    private JButton BtnEliminar;
    private JButton BtnBuscar;
    private JLabel lblEliminar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public UsuarioEliminarView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setContentPane(panelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel(new Object[]{"Nombre", "Usuario", "Contraseña", "Correo", "Celular", "Fcha de Nacimiento", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUser.setModel(modelo);
        cambiarIdioma();
        iconos();
    }
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


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtUsuario() {
        return TxtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        TxtUsuario = txtUsuario;
    }

    public JTable getTblUser() {
        return tblUser;
    }

    public void setTblUser(JTable tblUser) {
        this.tblUser = tblUser;
    }

    public JButton getBtnEliminar() {
        return BtnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        BtnEliminar = btnEliminar;
    }

    public JButton getBtnBuscar() {
        return BtnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        BtnBuscar = btnBuscar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JLabel getLblEliminar() {
        return lblEliminar;
    }

    public void setLblEliminar(JLabel lblEliminar) {
        this.lblEliminar = lblEliminar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void cargarUsuario(List<Usuario> usuarios) {
        modelo.setRowCount(0);

        for (Usuario usuario : usuarios) {
            Object[] fila = {
                    usuario.getUsername(),
                    usuario.getContrasenia(),
                    usuario.getRol()
            };
            modelo.addRow(fila);
        }
    }
    public void iconos(){
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            ImageIcon icono = new ImageIcon(botonBuscar);
            BtnBuscar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonEliminar = LoginView.class.getClassLoader().getResource("imagenes/EliminarTodo.svg.png");
        if (botonEliminar != null) {
            ImageIcon icono = new ImageIcon(botonEliminar);
            BtnEliminar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
