package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz que define el Contrato de Acceso a Datos (DAO) para la entidad Producto.
 * Establece las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) que deben
 * ser implementadas por cualquier clase que gestione la persistencia de los productos.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public interface ProductoDAO {

    /**
     * Persiste un nuevo objeto Producto en el sistema de almacenamiento.
     *
     * @param producto El producto a ser creado.
     */
    void crear(Producto producto);

    /**
     * Busca y recupera un Producto por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto encontrado, o null si no existe un producto con ese código.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca y recupera una lista de productos cuyos nombres coinciden con el criterio de búsqueda.
     *
     * @param nombre El nombre o parte del nombre del producto a buscar.
     * @return Una lista de productos que coinciden con la búsqueda.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza la información de un producto existente en el sistema de almacenamiento.
     *
     * @param producto El objeto Producto con los datos modificados.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto del sistema de almacenamiento utilizando su código.
     *
     * @param codigo El código del producto a ser eliminado.
     */
    void eliminar(int codigo);

    /**
     * Recupera una lista de todos los productos existentes en el sistema.
     *
     * @return Una lista que contiene todos los productos.
     */
    List<Producto> listarTodos();

}