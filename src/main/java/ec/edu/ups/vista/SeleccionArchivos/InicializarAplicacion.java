package ec.edu.ups.vista.SeleccionArchivos;

import ec.edu.ups.dao.*;
import ec.edu.ups.dao.DAOArchivos.CarritoDAOArchivo;
import ec.edu.ups.dao.DAOArchivos.CuestionarioDAOArchivo;
import ec.edu.ups.dao.DAOArchivos.ProductoDAOArchivo;
import ec.edu.ups.dao.DAOArchivos.UsuarioDAOArchivo;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministracionView.LoginView;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InicializarAplicacion {

    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;
    private CuestionarioDAO cuestionarioDAO;

    public void inicializarDAOs(LoginView loginView, MensajeInternacionalizacionHandler mi) {
        String modoSeleccionado = loginView.getCbxArchivo().getSelectedItem().toString().toUpperCase();

        if (modoSeleccionado.equals(mi.get("login.memoria").toUpperCase())) {
            this.usuarioDAO = new UsuarioDAOMemoria(new CuestionarioDAOMemoria(mi));
            this.productoDAO = new ProductoDAOMemoria();
            this.carritoDAO = new CarritoDAOMemoria();
            this.cuestionarioDAO = new CuestionarioDAOMemoria(mi);

        } else if (modoSeleccionado.equals(mi.get("login.archivo").toUpperCase())) {

            File carpeta = loginView.getCarpetaSeleccionada();
            if (carpeta == null || !carpeta.isDirectory()) {
                JOptionPane.showMessageDialog(null, mi.get("login.archivo.no_seleccionado"));
                System.exit(0);
            }
            if (!carpeta.exists()) {
                carpeta.mkdirs(); // ← CREA la carpeta si no existe
            }


            // Crear archivos concretos dentro de la carpeta seleccionada
            File archivoUsuarios = new File(carpeta, "usuarios.txt");
            File archivoProductos = new File(carpeta, "productos.dat");
            File archivoCarritos = new File(carpeta, "carritos.txt");
            File archivoCuestionario = new File(carpeta, "cuestionario.dat");

            // Inicializar DAOs con archivos específicos
            this.usuarioDAO = new UsuarioDAOArchivo(archivoUsuarios);
            this.productoDAO = new ProductoDAOArchivo(archivoProductos);

            // Para Carrito necesitas las listas de usuarios y productos ya cargadas
            List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
            List<Producto> listaProductos = productoDAO.listarTodos();

            this.carritoDAO = new CarritoDAOArchivo(archivoCarritos, listaUsuarios, listaProductos);
            this.cuestionarioDAO = new CuestionarioDAOArchivo(archivoCuestionario);

        } else {
            JOptionPane.showMessageDialog(null, "Modo de almacenamiento no reconocido.");
            System.exit(0);
        }
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    public CarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    public CuestionarioDAO getCuestionarioDAO() {
        return cuestionarioDAO;
    }
}
