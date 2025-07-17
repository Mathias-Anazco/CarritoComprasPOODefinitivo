package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz ProductoDAO.
 * Gestiona una lista de productos de forma volátil, lo que significa que los datos
 * se perderán cuando la aplicación se cierre.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista de productos en memoria
     * y añade un producto de ejemplo por defecto.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
        crear(new Producto(1, "Kentucky BBQ", 5.99));
    }

    /**
     * Añade un nuevo producto a la lista en memoria.
     *
     * @param producto El producto a ser creado.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca un producto en la lista por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto si se encuentra, de lo contrario, retorna null.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos en la lista cuyos nombres comiencen con el texto proporcionado.
     *
     * @param nombre El prefijo del nombre del producto a buscar.
     * @return Una lista de productos que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().startsWith(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente en la lista, localizándolo por su código.
     *
     * @param producto El producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }

    /**
     * Elimina un producto de la lista basándose en su código.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve la lista completa de todos los productos almacenados en memoria.
     *
     * @return Una lista de todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}