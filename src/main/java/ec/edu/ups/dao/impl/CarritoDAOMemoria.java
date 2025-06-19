package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private final List<ItemCarrito> items;
    private final List<Carrito> carritos = new ArrayList<>();

    public CarritoDAOMemoria() {
        this.items = new ArrayList<>();
    }

    @Override
    public void crear(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    @Override
    public void eliminar(int codigoProducto) {
        Iterator<ItemCarrito> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProducto().getCodigo() == codigoProducto) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void vaciarCarrito() {
        items.clear();
    }

    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    @Override
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    @Override
    public boolean estaVacio() {
        return items.isEmpty();
    }

    @Override
    public void guardarCarrito(Carrito carrito) {
        carritos.add(carrito);
    }
}