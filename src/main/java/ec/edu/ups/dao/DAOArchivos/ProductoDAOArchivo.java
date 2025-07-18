package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de ProductoDAO usando archivo binario mediante RandomAccessFile.
 * Guarda productos con estructura fija: int (código), String (nombre fijo), double (precio).
 *
 * Autor: Mathias Añazco
 * Fecha: 18/07/2025
 */
public class ProductoDAOArchivo implements ProductoDAO {

    private final File archivo;
    private final int NOMBRE_LENGTH = 20; // caracteres
    private final int BYTES_POR_REGISTRO = 4 + (NOMBRE_LENGTH * 2) + 8; // int + String + double

    public ProductoDAOArchivo(File archivo) {
        this.archivo = archivo;
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
                crearProductoPorDefecto(); // Si no existe, lo crea y lo inicializa
            } else if (archivo.length() == 0) {
                crearProductoPorDefecto(); // Si existe pero está vacío
            }
        } catch (IOException e) {
            System.err.println("Error al crear archivo de productos: " + e.getMessage());
        }
    }

    private void crearProductoPorDefecto() {
        Producto productoPorDefecto = new Producto(1, "Pollo Broaster", 5.99);
        crear(productoPorDefecto);
    }

    @Override
    public void crear(Producto producto) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length());
            raf.writeInt(producto.getCodigo());
            escribirStringFijo(raf, producto.getNombre());
            raf.writeDouble(producto.getPrecio());
        } catch (IOException e) {
            System.err.println("Error al escribir producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int cod = raf.readInt();
                String nombre = leerStringFijo(raf);
                double precio = raf.readDouble();
                if (cod == codigo) {
                    return new Producto(cod, nombre, precio);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombreBuscado) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto p : listarTodos()) {
            if (p.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = listarTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                break;
            }
        }
        sobrescribirArchivo(productos);
    }

    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        sobrescribirArchivo(productos);
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int codigo = raf.readInt();
                String nombre = leerStringFijo(raf);
                double precio = raf.readDouble();
                lista.add(new Producto(codigo, nombre, precio));
            }
        } catch (IOException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    private void sobrescribirArchivo(List<Producto> productos) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0); // Borrar todo
            for (Producto p : productos) {
                raf.writeInt(p.getCodigo());
                escribirStringFijo(raf, p.getNombre());
                raf.writeDouble(p.getPrecio());
            }
        } catch (IOException e) {
            System.err.println("Error al sobrescribir productos: " + e.getMessage());
        }
    }

    private void escribirStringFijo(RandomAccessFile raf, String texto) throws IOException {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(NOMBRE_LENGTH);
        raf.writeChars(sb.toString());
    }

    private String leerStringFijo(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NOMBRE_LENGTH; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }
}
