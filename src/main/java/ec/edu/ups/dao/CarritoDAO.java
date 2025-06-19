package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import java.util.List;

public interface CarritoDAO {

    void crear(Producto producto, int cantidad);
    void eliminar(int codigoProducto);
    void vaciarCarrito();
    double calcularTotal();
    List<ItemCarrito> obtenerItems();
    boolean estaVacio();
    void guardarCarrito(Carrito carrito);

}
