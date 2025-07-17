package ec.edu.ups.modelo;

/**
 * Representa un producto en el sistema de inventario o tienda.
 * Esta clase es un modelo de datos simple que contiene los atributos
 * fundamentales de un producto: código, nombre y precio.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class Producto {
    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor por defecto.
     */
    public Producto() {
    }

    /**
     * Construye un nuevo producto con todos sus atributos.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo El nuevo código del producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return El código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Devuelve una representación en formato de cadena del producto,
     * mostrando su nombre y precio.
     *
     * @return Una cadena que representa al producto.
     */
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}