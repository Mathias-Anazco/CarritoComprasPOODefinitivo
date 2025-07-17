package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivo implements UsuarioDAO {
    private File archivo;

    public UsuarioDAOArchivo(File archivo) {
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
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(usuario.toArchivoTexto());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarTodos(usuarios);
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarTodos(usuarios);
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario u = Usuario.fromArchivoTexto(linea);
                if (u != null) lista.add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void guardarTodos(List<Usuario> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : lista) {
                pw.println(u.toArchivoTexto());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> listarPorUsername(String username) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : listarTodos()) {
            if (u.getUsername().contains(username)) {
                resultado.add(u);
            }
        }
        return resultado;
    }
}
