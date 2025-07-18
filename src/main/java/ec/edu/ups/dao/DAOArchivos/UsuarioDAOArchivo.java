package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de UsuarioDAO que utiliza archivo de texto plano para persistencia.
 * Cada línea del archivo representa un usuario serializado mediante toArchivoTexto().
 * Soporta operaciones CRUD y asegura que siempre exista un administrador por defecto.
 *
 * @author Mathias Añazco
 * @since 18/07/2025
 */
public class UsuarioDAOArchivo implements UsuarioDAO {

    private final File archivo;

    /**
     * Constructor que recibe un archivo de texto donde se almacenan los usuarios.
     * Si el archivo no existe, se crea automáticamente. Además, verifica que exista
     * al menos un usuario administrador, y si no existe, crea uno por defecto.
     *
     * @param archivo Archivo de texto para almacenamiento de usuarios.
     */
    public UsuarioDAOArchivo(File archivo) {
        this.archivo = archivo;
        try {
            File carpeta = archivo.getParentFile();
            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs();
            }

            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            boolean existeAdmin = false;
            for (Usuario u : listarTodos()) {
                if (u.getRol() == Rol.ADMINISTRADOR) {
                    existeAdmin = true;
                    break;
                }
            }

            if (!existeAdmin) {
                System.out.println("→ No hay administrador. Creando admin por defecto...");
                crearUsuarioAdminPorDefecto();
            }

        } catch (IOException e) {
            System.err.println("Error al crear archivo de usuarios: " + e.getMessage());
        }
    }

    /**
     * Crea un usuario administrador por defecto y lo guarda en el archivo.
     */
    private void crearUsuarioAdminPorDefecto() {
        Usuario admin = new Usuario("0107533689", "Admin123@", Rol.ADMINISTRADOR);
        admin.setNombreCompleto("Administrador General");
        admin.setCorreo("admin@gmail.com");
        admin.setCelular("0999999999");
        admin.setFechaNacimiento("1/Enero/1990");

        System.out.println("→ Escribiendo admin por defecto...");
        crear(admin);
        System.out.println("→ Admin por defecto escrito exitosamente.");
    }

    /**
     * Autentica un usuario comparando su username y contraseña.
     *
     * @param username     Nombre de usuario.
     * @param contrasenia  Contraseña.
     * @return Usuario autenticado o null si no coincide.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Guarda un nuevo usuario en el archivo.
     *
     * @param usuario Usuario a crear.
     */
    @Override
    public void crear(Usuario usuario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(usuario.toArchivoTexto());
            pw.flush();
        } catch (IOException e) {
            System.err.println("Error al escribir usuario: " + e.getMessage());
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarTodos(usuarios);
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param usuario Usuario con datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarTodos(usuarios);
    }

    /**
     * Devuelve una lista con todos los usuarios almacenados en el archivo.
     *
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario u = Usuario.fromArchivoTexto(linea);
                if (u != null) {
                    lista.add(u);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda toda la lista de usuarios en el archivo (sobrescribe el archivo).
     *
     * @param lista Lista de usuarios a guardar.
     */
    private void guardarTodos(List<Usuario> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : lista) {
                pw.println(u.toArchivoTexto());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar todos los usuarios: " + e.getMessage());
        }
    }

    /**
     * Busca usuarios cuyo nombre de usuario contenga el texto proporcionado.
     *
     * @param username Substring del username a buscar.
     * @return Lista de usuarios que contienen ese username.
     */
    @Override
    public List<Usuario> listarPorUsername(String username) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : listarTodos()) {
            if (u.getUsername().contains(username)) {
                resultado.add(u);
            }
        }
        return resultado;
    }
}
