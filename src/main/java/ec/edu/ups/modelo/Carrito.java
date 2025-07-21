package ec.edu.ups.modelo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Representa un carrito de compras en el sistema.
 * Esta clase contiene una colección de {@link ItemCarrito}, se asocia a un {@link Usuario},
 * y proporciona métodos para gestionar su contenido, calcular totales y
 * serializar/deserializar su estado para persistencia en archivos de texto.
 *
 * @author Mathias Añazco
 * @version 1.0
 * @since 18/07/2025
 */
public class Carrito {

    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    /**
     * Constructor por defecto.
     * Inicializa un carrito vacío con la fecha de creación actual.
     */
    public Carrito() {
        this.items = new ArrayList<>();
        this.fechaCreacion = new GregorianCalendar();
    }

    /**
     * Serializa el objeto Carrito a un formato de cadena de texto para su almacenamiento.
     * El formato es: codigo;fecha;username;codProducto,cantidad|codProducto,cantidad...
     *
     * @return Una cadena de texto que representa el estado del carrito.
     */
    public String toArchivoTexto() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        builder.append(codigo).append(";");
        builder.append(sdf.format(fechaCreacion.getTime())).append(";");
        builder.append(usuario != null ? usuario.getUsername() : "").append(";");
        for (ItemCarrito item : items) {
            builder.append(item.getProducto().getCodigo())
                    .append(",").append(item.getCantidad()).append("|");
        }
        return builder.toString();
    }

    /**
     * Método de fábrica estático que deserializa un Carrito desde una cadena de texto.
     * Reconstruye el objeto y sus relaciones con productos y usuarios.
     *
     * @param linea     La línea de texto del archivo que representa un carrito.
     * @param usuarios  La lista completa de usuarios del sistema para enlazar al propietario.
     * @param productos La lista completa de productos del sistema para enlazar los items.
     * @return una nueva instancia de {@code Carrito}, o {@code null} si ocurre un error en el parseo.
     */
    public static Carrito fromArchivoTexto(String linea, List<Usuario> usuarios, List<Producto> productos) {
        try {
            String[] partes = linea.split(";");
            if (partes.length < 4) return null;

            Carrito carrito = new Carrito();
            carrito.codigo = Integer.parseInt(partes[0]);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(partes[1]);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(fecha);
            carrito.fechaCreacion = calendar;

            String username = partes[2];
            carrito.usuario = usuarios.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst().orElse(null);

            String[] items = partes[3].split("\\|");
            for (String item : items) {
                if (item.isEmpty()) continue;
                String[] detalle = item.split(",");
                int codProducto = Integer.parseInt(detalle[0]);
                int cantidad = Integer.parseInt(detalle[1]);

                Producto producto = productos.stream()
                        .filter(p -> p.getCodigo() == codProducto)
                        .findFirst().orElse(null);

                if (producto != null) {
                    carrito.agregarProducto(producto, cantidad);
                }
            }

            return carrito;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene el usuario propietario del carrito.
     *
     * @return El objeto Usuario asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna un usuario al carrito.
     *
     * @param usuario El objeto Usuario a asociar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el código único del carrito.
     *
     * @return El código del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código único del carrito.
     *
     * @param codigo El nuevo código para el carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha y hora en que se creó el carrito.
     *
     * @return Un objeto GregorianCalendar con la fecha de creación.
     */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del carrito.
     *
     * @param fechaCreacion La nueva fecha de creación.
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Agrega un producto al carrito. Si el producto ya existe, actualiza su cantidad.
     *
     * @param producto El producto a agregar.
     * @param cantidad La cantidad del producto a agregar.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito basándose en su código.
     *
     * @param codigoProducto El código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        items.removeIf(item -> item.getProducto().getCodigo() == codigoProducto);
    }

    /**
     * Elimina todos los items del carrito, dejándolo vacío.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Calcula el subtotal del carrito (suma de precios de items sin impuestos).
     *
     * @return El subtotal del carrito.
     */
    public double calcularTotal() {
        return items.stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
    }

    /**

     * Obtiene la lista de todos los items contenidos en el carrito.
     *
     * @return Una lista de objetos {@code ItemCarrito}.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito no contiene items.
     *
     * @return {@code true} si el carrito está vacío, {@code false} en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula el monto del IVA (12%) sobre el subtotal del carrito.
     *
     * @return El valor del IVA calculado.
     */
    public double calcularIVA() {
        double total = calcularTotal();
        return total * 0.12;
    }

    /**
     * Calcula el monto total a pagar, incluyendo el subtotal más el IVA.
     *
     * @return El valor total final del carrito.
     */
    public double calcularTotalConIVA() {
        return calcularTotal() + calcularIVA();
    }

    /**
     * Crea una copia superficial de este carrito.
     *
     * @return Una nueva instancia de Carrito con los mismos datos.
     */
    public Carrito copiar(){
        Carrito copia = new Carrito();
        copia.setFechaCreacion(this.fechaCreacion);
        copia.setCodigo(this.codigo);
        copia.setUsuario(this.usuario);
        for (ItemCarrito item : this.items) {
            Producto producto = item.getProducto();
            int cantidad = item.getCantidad();
            copia.agregarProducto(producto, cantidad);
        }
        return copia;
    }
}