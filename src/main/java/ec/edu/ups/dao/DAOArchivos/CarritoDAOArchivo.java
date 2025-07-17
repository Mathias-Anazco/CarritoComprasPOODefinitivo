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

    public CarritoDAOArchivo(File archivo, List<Usuario> usuarios, List<Producto> productos) {
        this.archivo = archivo;
        this.usuarios = usuarios;
        this.productos = productos;

        try {
            if (!archivo.exists()) {
                archivo.createNewFile(); // ← CREA el archivo vacío si no existe
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void crear(Carrito carrito) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(carrito.toArchivoTexto());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return listarTodos().stream().filter(c -> c.getCodigo() == codigo).findFirst().orElse(null);
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito c : listarTodos()) {
            if (c.getUsuario().getUsername().equals(usuario.getUsername())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = listarTodos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
        guardarTodos(carritos);
    }

    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarTodos(carritos);
    }

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Carrito c = Carrito.fromArchivoTexto( linea, usuarios, productos);
                if (c != null) lista.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void guardarTodos(List<Carrito> carritos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Carrito c : carritos) {
                pw.println(c.toArchivoTexto());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
