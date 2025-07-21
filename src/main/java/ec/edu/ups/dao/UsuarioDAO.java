package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Usuario.
 * Establece las operaciones de autenticación y CRUD (Crear, Leer, Actualizar, Eliminar)
 * que deben ser implementadas por cualquier clase que gestione la persistencia de los usuarios.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public interface UsuarioDAO {

    /**
     * Verifica las credenciales de un usuario para permitir el acceso al sistema.
     *
     * @param username      El nombre de usuario que intenta iniciar sesión.
     * @param contrasenia La contraseña proporcionada por el usuario.
     * @return El objeto Usuario si las credenciales son correctas, de lo contrario null.
     */
    Usuario autenticar(String username, String contrasenia);

    /**
     * Persiste un nuevo objeto Usuario en el sistema de almacenamiento.
     *
     * @param usuario El usuario a ser creado.
     */
    void crear(Usuario usuario);

    /**
     * Busca y recupera un Usuario por su nombre de usuario único.
     *
     * @param username El nombre de usuario a buscar.
     * @return El objeto Usuario encontrado, o null si no existe un usuario con ese nombre.
     */
    Usuario buscarPorUsername(String username);

    /**
     * Elimina un usuario del sistema de almacenamiento utilizando su nombre de usuario.
     *
     * @param username El nombre de usuario a ser eliminado.
     */
    void eliminar(String username);

    /**
     * Actualiza la información de un usuario existente en el sistema de almacenamiento.
     *
     * @param usuario El objeto Usuario con los datos modificados.
     */
    void actualizar(Usuario usuario);

    /**
     * Recupera una lista de todos los usuarios existentes en el sistema.
     *
     * @return Una lista que contiene todos los usuarios.
     */
    List<Usuario> listarTodos();

    /**
     * Recupera una lista de usuarios que coinciden con un nombre de usuario específico.
     * Típicamente, esta lista contendrá un solo usuario o estará vacía.
     *
     * @param username El nombre de usuario a listar.
     * @return Una lista de usuarios que coinciden con el nombre de usuario proporcionado.
     */
    List<Usuario> listarPorUsername(String username);

}