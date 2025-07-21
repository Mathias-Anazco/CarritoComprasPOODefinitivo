package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link ProductoDAO} que gestiona la persistencia
 * de objetos {@link Producto} en un archivo binario utilizando {@link RandomAccessFile}.
 * Cada producto se guarda con una estructura de tamaño fijo que incluye un código
 * entero, un nombre de longitud fija (20 caracteres) y un precio de tipo double.
 * Si el archivo no existe o está vacío al inicializar, se crea un producto por defecto.
 *
 * @author Mathias Añazco
 * @since 18/07/2025
 */
public class ProductoDAOArchivo implements ProductoDAO {

    /**
     * El objeto {@link File} que representa el archivo binario donde se almacenan los productos.
     */
    private final File archivo;
    /**
     * La longitud fija en caracteres que ocupará el nombre de cada producto en el archivo.
     */
    private final int NOMBRE_LENGTH = 20; // caracteres
    /**
     * El número de bytes que ocupa cada registro de producto en el archivo.
     * Calculado como: 4 bytes (para el int del código) + (longitud del nombre * 2 bytes por caracter Unicode) + 8 bytes (para el double del precio).
     */
    private final int BYTES_POR_REGISTRO = 4 + (NOMBRE_LENGTH * 2) + 8; // int + String + double

    /**
     * Construye una nueva instancia de {@code ProductoDAOArchivo}.
     * Si el archivo especificado no existe, se crea. Si el archivo existe pero está vacío,
     * se inicializa con un producto por defecto ("Pollo Broaster" con código 1 y precio 5.99).
     *
     * @param archivo El objeto {@link File} que apunta al archivo de almacenamiento de productos.
     */
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

    /**
     * Crea e inserta un producto por defecto en el archivo.
     * Este método se llama durante la inicialización si el archivo está vacío o no existe.
     */
    private void crearProductoPorDefecto() {
        Producto productoPorDefecto = new Producto(1, "Pollo Broaster", 5.99);
        crear(productoPorDefecto);
    }

    /**
     * Guarda un nuevo producto en el archivo.
     * El producto se añade al final del archivo, manteniendo la estructura de tamaño fijo.
     *
     * @param producto El objeto {@link Producto} a ser guardado.
     */
    @Override
    public void crear(Producto producto) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length()); // Posiciona el puntero al final del archivo
            raf.writeInt(producto.getCodigo()); // Escribe el código del producto
            escribirStringFijo(raf, producto.getNombre()); // Escribe el nombre con longitud fija
            raf.writeDouble(producto.getPrecio()); // Escribe el precio
        } catch (IOException e) {
            System.err.println("Error al escribir producto: " + e.getMessage());
        }
    }

    /**
     * Busca un producto en el archivo por su código.
     * Recorre el archivo secuencialmente hasta encontrar un producto con el código especificado.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@link Producto} si se encuentra, o {@code null} si no existe un producto con ese código.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) { // Itera mientras no se llegue al final del archivo
                int cod = raf.readInt(); // Lee el código
                String nombre = leerStringFijo(raf); // Lee el nombre
                double precio = raf.readDouble(); // Lee el precio
                if (cod == codigo) {
                    return new Producto(cod, nombre, precio); // Retorna el producto si el código coincide
                }
            }
        } catch (IOException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el producto
    }

    /**
     * Busca productos en el archivo por una subcadena en su nombre (búsqueda insensible a mayúsculas/minúsculas).
     * Este método lee todos los productos y filtra aquellos cuyos nombres contienen la cadena buscada.
     *
     * @param nombreBuscado La subcadena del nombre a buscar.
     * @return Una {@link List} de objetos {@link Producto} que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombreBuscado) {
        List<Producto> resultados = new ArrayList<>();
        // Reutiliza listarTodos() para obtener la lista completa y luego filtrar
        for (Producto p : listarTodos()) {
            if (p.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    /**
     * Actualiza la información de un producto existente en el archivo.
     * Para actualizar, se lee todo el archivo, se modifica el producto en la lista en memoria
     * y luego se sobrescribe el archivo completo con la lista actualizada.
     *
     * @param producto El objeto {@link Producto} con la información actualizada.
     */
    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = listarTodos(); // Lee todos los productos
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto); // Actualiza el producto en la lista
                break;
            }
        }
        sobrescribirArchivo(productos); // Sobrescribe el archivo con la lista modificada
    }

    /**
     * Elimina un producto del archivo por su código.
     * Similar a la actualización, se leen todos los productos, se elimina el producto deseado de la lista en memoria,
     * y luego se sobrescribe el archivo.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos(); // Lee todos los productos
        productos.removeIf(p -> p.getCodigo() == codigo); // Elimina el producto de la lista si el código coincide
        sobrescribirArchivo(productos); // Sobrescribe el archivo con la lista modificada
    }

    /**
     * Lista todos los productos almacenados en el archivo.
     * Recorre el archivo binario, lee cada registro de producto y los añade a una lista.
     *
     * @return Una {@link List} de todos los objetos {@link Producto} en el archivo.
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) { // Itera mientras no se llegue al final del archivo
                int codigo = raf.readInt(); // Lee el código
                String nombre = leerStringFijo(raf); // Lee el nombre
                double precio = raf.readDouble(); // Lee el precio
                lista.add(new Producto(codigo, nombre, precio)); // Añade el producto a la lista
            }
        } catch (IOException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Sobrescribe completamente el archivo binario con una nueva lista de productos.
     * Este método es utilizado internamente por las operaciones de {@link #actualizar(Producto)}
     * y {@link #eliminar(int)} para reconstruir el archivo después de una modificación.
     *
     * @param productos La {@link List} de objetos {@link Producto} con la que se sobrescribirá el archivo.
     */
    private void sobrescribirArchivo(List<Producto> productos) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0); // Trunca el archivo a 0 bytes, borrando todo su contenido
            for (Producto p : productos) {
                raf.writeInt(p.getCodigo()); // Escribe el código
                escribirStringFijo(raf, p.getNombre()); // Escribe el nombre con longitud fija
                raf.writeDouble(p.getPrecio()); // Escribe el precio
            }
        } catch (IOException e) {
            System.err.println("Error al sobrescribir productos: " + e.getMessage());
        }
    }

    /**
     * Escribe una cadena de texto en un {@link RandomAccessFile} asegurando que ocupe
     * una longitud fija predefinida ({@code NOMBRE_LENGTH}).
     * Si la cadena es más corta, se rellena con espacios. Si es más larga, se trunca.
     *
     * @param raf El {@link RandomAccessFile} en el que se va a escribir.
     * @param texto La cadena de texto a escribir.
     * @throws IOException Si ocurre un error de E/S durante la escritura.
     */
    private void escribirStringFijo(RandomAccessFile raf, String texto) throws IOException {
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(NOMBRE_LENGTH); // Ajusta la longitud de la cadena
        raf.writeChars(sb.toString()); // Escribe la cadena de caracteres Unicode
    }

    /**
     * Lee una cadena de texto de longitud fija desde un {@link RandomAccessFile}.
     * Se leen {@code NOMBRE_LENGTH} caracteres y el resultado se recorta ({@code trim()})
     * para eliminar los espacios en blanco de relleno.
     *
     * @param raf El {@link RandomAccessFile} del que se va a leer.
     * @return La cadena de texto leída, sin espacios de relleno al final.
     * @throws IOException Si ocurre un error de E/S durante la lectura.
     */
    private String leerStringFijo(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NOMBRE_LENGTH; i++) {
            sb.append(raf.readChar()); // Lee caracter por caracter
        }
        return sb.toString().trim(); // Retorna la cadena resultante sin espacios al final
    }
}