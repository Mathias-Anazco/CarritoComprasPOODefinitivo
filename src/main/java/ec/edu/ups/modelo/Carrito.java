package ec.edu.ups.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {

    private int codigo;

    private GregorianCalendar fechaCreacion;

    //private int contador = 1;

    private List<ItemCarrito> items;

    private Usuario usuario;


    public Carrito() {
        //this.codigo = contador++;
        this.items = new ArrayList<>();
        this.fechaCreacion = new GregorianCalendar();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }


    public double calcularIVA() {
        double total = calcularTotal();
        return total * 0.12; // Asumiendo un IVA del 12%
    }
    public double calcularTotalConIVA() {
        return calcularTotal() + calcularIVA();
    }

    public String getFechaFormateada() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formato.format(fechaCreacion.getTime());
    }
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

