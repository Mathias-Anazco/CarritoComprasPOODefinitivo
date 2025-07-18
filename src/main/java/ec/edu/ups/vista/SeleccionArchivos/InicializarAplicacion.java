package ec.edu.ups.vista.SeleccionArchivos;

import ec.edu.ups.dao.*;
import ec.edu.ups.dao.DAOArchivos.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Preguntas;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * Clase que se encarga de inicializar los DAO de la aplicación dependiendo del modo de almacenamiento seleccionado:
 * Memoria o Archivo.
 */
public class InicializarAplicacion {

    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;
    private CuestionarioDAO cuestionarioDAO;

    public void inicializarDAOs(MensajeInternacionalizacionHandler mi, String modoSeleccionado) {
        modoSeleccionado = modoSeleccionado.toLowerCase().trim();
        System.out.println("Modo seleccionado: " + modoSeleccionado); // DEBUG

        if (modoSeleccionado.contains("memoria")) {
            System.out.println("→ Inicializando en modo MEMORIA");
            this.usuarioDAO = new UsuarioDAOMemoria(new CuestionarioDAOMemoria(mi));
            this.productoDAO = new ProductoDAOMemoria();
            this.carritoDAO = new CarritoDAOMemoria();
            this.cuestionarioDAO = new CuestionarioDAOMemoria(mi);

        } else if (modoSeleccionado.contains("archivo")) {
            System.out.println("→ Inicializando en modo ARCHIVO");

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int opcion = chooser.showOpenDialog(null);
            if (opcion != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, mi.get("login.archivo.no_seleccionado"));
                System.exit(0);
            }

            File carpeta = chooser.getSelectedFile();
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivoUsuarios = new File(carpeta, "usuarios.txt");
            File archivoProductos = new File(carpeta, "productos.dat");
            File archivoCarritos = new File(carpeta, "carritos.txt");
            File archivoCuestionario = new File(carpeta, "cuestionario.dat");

            // Crear DAOs
            this.usuarioDAO = new UsuarioDAOArchivo(archivoUsuarios);
            this.productoDAO = new ProductoDAOArchivo(archivoProductos);
            this.cuestionarioDAO = new CuestionarioDAOArchivo(archivoCuestionario, mi);

            // Crear usuario admin por defecto si está vacío
            if (usuarioDAO.listarTodos().isEmpty()) {
                Usuario admin1 = new Usuario("0106628175", "Admin123@", Rol.ADMINISTRADOR);
                admin1.setNombreCompleto("Administrador");
                admin1.setCorreo("admin1@gmail.com");
                admin1.setCelular("0969606158");
                admin1.setFechaNacimiento("1/Enero/1990");
                usuarioDAO.crear(admin1);
                System.out.println("Usuario admin1 creado por defecto.");
            }

            // Crear producto por defecto si está vacío
            if (productoDAO.listarTodos().isEmpty()) {
                Producto p = new Producto(1, "Combo KFC", 7.99);
                productoDAO.crear(p);
                System.out.println("Producto por defecto creado.");
            }

            // Cargar preguntas si está vacío
            if (cuestionarioDAO.listarPreguntas().isEmpty()) {
                cargarPreguntasIniciales(mi, cuestionarioDAO);
                System.out.println("Preguntas de seguridad cargadas.");
            }

            // Inicializar carrito con usuarios y productos
            List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
            List<Producto> listaProductos = productoDAO.listarTodos();
            this.carritoDAO = new CarritoDAOArchivo(archivoCarritos, listaUsuarios, listaProductos);

        } else {
            JOptionPane.showMessageDialog(null, "Modo de almacenamiento no reconocido: " + modoSeleccionado);
            System.exit(0);
        }
    }

    private void cargarPreguntasIniciales(MensajeInternacionalizacionHandler mi, CuestionarioDAO dao) {
        dao.crear(new Preguntas("1", mi.get("pregunta.color_favorito")));
        dao.crear(new Preguntas("2", mi.get("pregunta.instrumento_musical_favorito")));
        dao.crear(new Preguntas("3", mi.get("pregunta.comida_favorita")));
        dao.crear(new Preguntas("4", mi.get("pregunta.pais_que_visitaste_por_primera_vez")));
        dao.crear(new Preguntas("5", mi.get("pregunta.segundo_nombre_de_tu_padre")));
        dao.crear(new Preguntas("6", mi.get("pregunta.cancion_favorita")));
        dao.crear(new Preguntas("7", mi.get("pregunta.tu_libro_favorito")));
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
