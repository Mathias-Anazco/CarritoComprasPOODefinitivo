/**
 * Implementación del DAO de carrito que guarda los datos en un archivo de texto plano.
 * Esta clase permite la persistencia de objetos Carrito usando archivos.
 *
 * Autor: Mathias Añazco
 * Fecha: 18/07/2025
 */
package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOArchivo implements CarritoDAO {
    private File archivo;
    private List<Usuario> usuarios;
    private List<Producto> productos;

    /**
     * Constructor de la clase.
     *
     * @param archivo   Archivo donde se guardarán los carritos
     * @param usuarios  Lista de usuarios registrados
     * @param productos Lista de productos disponibles
     */
    public CarritoDAOArchivo(File archivo, List<Usuario> usuarios, List<Producto> productos) {
        this.archivo = archivo;
        this.usuarios = usuarios;
        this.productos = productos;

        try {
            if (!archivo.exists()) {
                archivo.createNewFile(); // Crea el archivo si no existe
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de carrito: " + e.getMessage());
        }
    }

    /**
     * Guarda un nuevo carrito en el archivo.
     *
     * @param carrito Carrito a guardar
     */
    @Override
    public void crear(Carrito carrito) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(carrito.toArchivoTexto());
        } catch (IOException e) {
            System.err.println("Error al guardar carrito: " + e.getMessage());
        }
    }

    /**
     * Busca un carrito por su código.
     *
     * @param codigo Código único del carrito
     * @return Carrito encontrado o null
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        try {
            return listarTodos().stream().filter(c -> c.getCodigo() == codigo).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Error al buscar carrito por código: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca todos los carritos de un usuario.
     *
     * @param usuario Usuario dueño de los carritos
     * @return Lista de carritos del usuario
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        try {
            for (Carrito c : listarTodos()) {
                if (c.getUsuario().getUsername().equals(usuario.getUsername())) {
                    resultado.add(c);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar carritos por usuario: " + e.getMessage());
        }
        return resultado;
    }

    /**
     * Actualiza un carrito existente.
     *
     * @param carrito Carrito con datos actualizados
     */
    @Override
    public void actualizar(Carrito carrito) {
        try {
            List<Carrito> carritos = listarTodos();
            for (int i = 0; i < carritos.size(); i++) {
                if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                    carritos.set(i, carrito);
                    break;
                }
            }
            guardarTodos(carritos);
        } catch (Exception e) {
            System.err.println("Error al actualizar carrito: " + e.getMessage());
        }
    }

    /**
     * Elimina un carrito por su código.
     *
     * @param codigo Código del carrito a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        try {
            List<Carrito> carritos = listarTodos();
            carritos.removeIf(c -> c.getCodigo() == codigo);
            guardarTodos(carritos);
        } catch (Exception e) {
            System.err.println("Error al eliminar carrito: " + e.getMessage());
        }
    }

    /**
     * Lista todos los carritos almacenados en el archivo.
     *
     * @return Lista de carritos
     */
    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    Carrito c = Carrito.fromArchivoTexto(linea, usuarios, productos);
                    if (c != null) lista.add(c);
                } catch (Exception e) {
                    System.err.println("Error al leer línea de carrito: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de carritos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda todos los carritos en el archivo, sobreescribiéndolo.
     *
     * @param carritos Lista de carritos a guardar
     */
    private void guardarTodos(List<Carrito> carritos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Carrito c : carritos) {
                pw.println(c.toArchivoTexto());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar todos los carritos: " + e.getMessage());
        }
    }
}
