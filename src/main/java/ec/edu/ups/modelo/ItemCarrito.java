package ec.edu.ups.modelo;

/**
 * Representa un único artículo dentro de un carrito de compras.
 * Esta clase asocia un objeto {@link Producto} con una cantidad específica y
 * proporciona métodos para calcular los costos relacionados con ese ítem.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public ItemCarrito() {
    }

    /**
     * Construye un item de carrito con un producto y una cantidad específicos.
     *
     * @param producto El producto para este item.
     * @param cantidad La cantidad de unidades de dicho producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Establece el producto para este item del carrito.
     *
     * @param producto El objeto Producto.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Establece la cantidad de unidades para este item.
     *
     * @param cantidad La cantidad de productos.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a este item.
     *
     * @return El objeto Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad de unidades de este item.
     *
     * @return La cantidad como un entero.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula y devuelve el subtotal para este item (precio del producto * cantidad).
     *
     * @return El subtotal del item.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    /**
     * Calcula el IVA (12%) correspondiente al subtotal de este item.
     *
     * @return El monto del IVA para este item.
     */
    public double getIVA() {
        return getSubtotal() * 0.12;
    }

    /**
     * Calcula el costo total para este item, incluyendo el subtotal y el IVA.
     *
     * @return El costo total del item.
     */
    public double getTotal() {
        return getSubtotal() + getIVA();
    }

    /**
     * Devuelve una representación en formato de cadena de este item del carrito,
     * mostrando el producto, la cantidad y el subtotal.
     *
     * @return Una cadena formateada que describe el item.
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }
}