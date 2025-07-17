package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivo implements ProductoDAO {

    private File archivo;

    public ProductoDAOArchivo(File archivo) {
        this.archivo = archivo;
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Producto producto) {
        List<Producto> productos = listarTodos();
        productos.add(producto);
        guardarTodos(productos);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return listarTodos().stream().filter(p -> p.getCodigo() == codigo).findFirst().orElse(null);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : listarTodos()) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
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
        guardarTodos(productos);
    }

    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarTodos(productos);
    }

    @Override
    public List<Producto> listarTodos() {
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Producto>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarTodos(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
