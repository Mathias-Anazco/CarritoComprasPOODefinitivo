package ec.edu.ups.dao.DAOArchivos;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Preguntas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CuestionarioDAOArchivo implements CuestionarioDAO {

    private File archivo;

    public CuestionarioDAOArchivo(File archivo) {
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
    public void crear(Preguntas preguntas) {
        List<Preguntas> lista = listarPreguntas();
        lista.add(preguntas);
        guardar(lista);
    }

    @Override
    public List<Preguntas> listarPreguntas() {
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Preguntas>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Preguntas> listarPreguntasEnunciado() {
        return listarPreguntas(); // O ajusta si necesitas solo los enunciados
    }

    private void guardar(List<Preguntas> preguntas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(preguntas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
