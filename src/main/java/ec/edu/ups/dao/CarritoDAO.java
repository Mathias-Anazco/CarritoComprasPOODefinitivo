package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Carrito.
 * Establece las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) que deben
 * ser implementadas por cualquier clase que gestione la persistencia de los carritos.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public interface CarritoDAO {

    /**
     * Persiste un nuevo objeto Carrito en el sistema de almacenamiento.
     *
     * @param carrito El carrito de compras a ser creado.
     */
    void crear(Carrito carrito);

    /**
     * Busca y recupera un Carrito por su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto Carrito encontrado, o null si no existe un carrito con ese código.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Busca y recupera una lista de todos los carritos asociados a un usuario específico.
     *
     * @param usuario El objeto Usuario cuyos carritos se desean obtener.
     * @return Una lista de carritos pertenecientes al usuario.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);

    /**
     * Actualiza la información de un carrito existente en el sistema de almacenamiento.
     *
     * @param carrito El objeto Carrito con los datos modificados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito del sistema de almacenamiento utilizando su código.
     *
     * @param codigo El código del carrito a ser eliminado.
     */
    void eliminar(int codigo);

    /**
     * Recupera una lista de todos los carritos existentes en el sistema.
     *
     * @return Una lista que contiene todos los carritos.
     */
    List<Carrito> listarTodos();

}