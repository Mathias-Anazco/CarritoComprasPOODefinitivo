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
 *
 * Autor: Mathias Añazco
 * Fecha: 18/07/2025
 */
public class UsuarioDAOArchivo implements UsuarioDAO {

    private final File archivo;

    /**
     * Constructor que recibe un archivo de texto donde se almacenan los usuarios.
     * Si el archivo no existe, se crea automáticamente.
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

            // Verifica si ya existe un usuario admin
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

    private void crearUsuarioAdminPorDefecto() {
        Usuario admin = new Usuario("0107533689", "Admin123@", Rol.ADMINISTRADOR);
        admin.setNombreCompleto("Administrador General");
        admin.setCorreo("admin@gmail.com");
        admin.setCelular("0999999999");
        admin.setFechaNacimiento("1/Enero/1990");

        System.out.println("→ Escribiendo admin por defecto...");
        crear(admin); // escribe en archivo
        System.out.println("→ Admin por defecto escrito exitosamente.");
    }


    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(usuario.toArchivoTexto());
            pw.flush(); // Asegura que se escriba inmediatamente
        } catch (IOException e) {
            System.err.println("Error al escribir usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarTodos(usuarios);
    }

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

    private void guardarTodos(List<Usuario> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : lista) {
                pw.println(u.toArchivoTexto());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar todos los usuarios: " + e.getMessage());
        }
    }

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
