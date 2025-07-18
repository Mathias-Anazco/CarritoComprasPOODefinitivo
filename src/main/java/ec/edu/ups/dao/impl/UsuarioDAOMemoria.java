package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz UsuarioDAO.
 * Gestiona una lista de objetos Usuario de forma volátil. Los datos se
 * perderán al finalizar la ejecución del programa.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    private CuestionarioDAO cuestionarioDAO;

    /**
     * Constructor que inicializa la lista de usuarios en memoria y añade
     * un usuario administrador por defecto para pruebas y acceso inicial.
     *
     * @param cuestionarioDAO DAO para gestionar cuestionarios, inyectado como dependencia.
     */
    public UsuarioDAOMemoria( CuestionarioDAO cuestionarioDAO) {
        this.usuarios = new ArrayList<>();
        this.cuestionarioDAO = cuestionarioDAO;

        Usuario admin1 = new Usuario("", "", Rol.ADMINISTRADOR);
        admin1.setNombreCompleto("Administrador");
        admin1.setCorreo("admin1@gmail.com");
        admin1.setCelular("0969606158");
        admin1.setFechaNacimiento("1/Enero/1990");
        crear(admin1);

        Usuario admin = new Usuario("0706338340", "12345", Rol.ADMINISTRADOR);
        admin.setNombreCompleto("Administrador Principal");
        admin.setCorreo("admin@gmail.com");
        admin.setCelular("0999999999");
        admin.setFechaNacimiento("1/Enero/1990");
        crear(admin);
    }

    /**
     * Autentica a un usuario comparando el nombre de usuario y contraseña proporcionados.
     *
     * @param username El nombre de usuario que intenta iniciar sesión.
     * @param contrasenia La contraseña del usuario.
     * @return El objeto Usuario si la autenticación es exitosa, de lo contrario null.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if(usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)){
                return usuario;
            }
        }
        return null;
    }

    /**
     * Añade un nuevo usuario a la lista en memoria.
     *
     * @param usuario El objeto Usuario a ser creado.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario en la lista por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto Usuario encontrado, o null si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista en memoria basado en su nombre de usuario.
     *
     * @param username El nombre de usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Actualiza la información de un usuario existente en la lista.
     *
     * @param usuario El objeto Usuario con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for(int i = 0; i < usuarios.size(); i++){
            Usuario usuarioAux = usuarios.get(i);
            if(usuarioAux.getUsername().equals(usuario.getUsername())){
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    /**
     * Devuelve una lista con todos los usuarios almacenados en memoria.
     *
     * @return Una lista de todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    /**
     * Busca usuarios cuyos nombres de usuario comiencen con el texto proporcionado.
     *
     * @param username El prefijo del nombre de usuario a buscar.
     * @return Una lista de usuarios que coinciden con el criterio de búsqueda.
     */
    @Override
    public  List<Usuario> listarPorUsername(String username) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().startsWith(username)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}